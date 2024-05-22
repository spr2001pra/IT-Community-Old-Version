package com.nowcoder.community.service;

import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataService {

    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    private SimpleDateFormat dfShow = new SimpleDateFormat("yyyy-MM-dd");

    // 将指定的IP计入UV
    public void recordUV(String ip) {
        String redisKey = RedisKeyUtil.getUVKey(df.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(redisKey, ip);
        String redisKeyRecord = RedisKeyUtil.getUVKeyRecord(df.format(new Date()));
        redisTemplate.opsForSet().add(redisKeyRecord, ip);
    }

    // 统计指定日期范围内的UV
    public long calculateUV(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 整理该日期范围内的key
        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getUVKey(df.format(calendar.getTime()));
            keyList.add(key);
            calendar.add(Calendar.DATE, 1);
        }

        // 合并这些数据
        String redisKey = RedisKeyUtil.getUVKey(df.format(start), df.format(end));
        redisTemplate.opsForHyperLogLog().union(redisKey, keyList.toArray());

        // 返回统计的结果
        return redisTemplate.opsForHyperLogLog().size(redisKey);
    }

    // 显示指定时间范围内的UV的统计结果
    public List<Map<String, String>> calculateUVRecord(Date start, Date end){
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Map<String, String> latestUniqueVisitorMap = new HashMap<>();
        // 利用map完成每天的独立ip合并
        while (!calendar.getTime().after(end)){
            String dateStr = df.format(calendar.getTime());
            String dateStrShow = dfShow.format(calendar.getTime());
            String redisKey = RedisKeyUtil.getUVKeyRecord(dateStr);
            Set<Object> dailyVisitors = redisTemplate.opsForSet().members(redisKey);
            if(!dailyVisitors.isEmpty()){
                for (Object ips : dailyVisitors ){
                    String ip = ips.toString();
                    latestUniqueVisitorMap.put(ip, dateStrShow);
                }
            }
            calendar.add(Calendar.DATE, 1);
        }

        List<Map<String, String>> res = new ArrayList<>();
        for (Map.Entry<String, String> entry : latestUniqueVisitorMap.entrySet()){
            Map<String, String> uniqueVisitor = new HashMap<>();
            uniqueVisitor.put("ip", entry.getKey());
            uniqueVisitor.put("date", entry.getValue());
            res.add(uniqueVisitor);
        }

        return res;
    }

    // 将指定用户计入DAU
    public void recordDAU(int userId) {
        String redisKey = RedisKeyUtil.getDAUKey(df.format(new Date()));
        redisTemplate.opsForValue().setBit(redisKey, userId, true);
    }

    // 将指定用户名计入DAU，开发展示列表
    public void recordDAUUsername(String username){
        String redisKey = RedisKeyUtil.getDAUKeyRecord(df.format(new Date()));
        redisTemplate.opsForSet().add(redisKey, username);
    }

    // 显示指定时间范围内的DAU的统计结果
    public List<Map<String, String>> calculateDAURecord(Date start, Date end){
        if(start == null || end == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Map<String, String> dailyActiveUserMap = new HashMap<>();
        while(!calendar.getTime().after(end)){
            String dateStr = df.format(calendar.getTime());
            String dateStrShow = dfShow.format(calendar.getTime());
            String redisKey = RedisKeyUtil.getDAUKeyRecord(dateStr);
            Set<Object> dailyActiveUserSet = redisTemplate.opsForSet().members(redisKey);
            if(!dailyActiveUserSet.isEmpty()){
                for (Object names : dailyActiveUserSet){
                    dailyActiveUserMap.put(names.toString(), dateStrShow);
                }
            }
            calendar.add(Calendar.DATE, 1);
        }

        List<Map<String, String>> ans = new ArrayList<>();
        for (Map.Entry<String, String> dailyActiveUserEntry:dailyActiveUserMap.entrySet()){
            Map<String, String> dailyActiveUser = new HashMap<>();
            dailyActiveUser.put("username", dailyActiveUserEntry.getKey());
            dailyActiveUser.put("date", dailyActiveUserEntry.getValue());
            ans.add(dailyActiveUser);
        }
        return ans;
    }

    // 统计指定日期范围内的DAU
    public long calculateDAU(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 整理该日期范围内的key
        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getDAUKey(df.format(calendar.getTime()));
            keyList.add(key.getBytes());
            calendar.add(Calendar.DATE, 1);
        }

        // 进行OR运算
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                String redisKey = RedisKeyUtil.getDAUKey(df.format(start), df.format(end));
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), keyList.toArray(new byte[0][0]));
                return connection.bitCount(redisKey.getBytes());
            }
        });
    }

}
