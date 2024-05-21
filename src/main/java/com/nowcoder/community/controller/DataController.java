package com.nowcoder.community.controller;

import com.nowcoder.community.service.DataService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    // 统计页面
    @RequestMapping(path = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataPage() {
        return "/site/admin/data";
    }

    // 统计网站UV
    @RequestMapping(path = "/data/uv", method = RequestMethod.POST)
    @ResponseBody
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        if(start == null || end == null){
            return CommunityUtil.getJSONString(1, "日期不能为空！");
        }
        if(start.after(end)){
            return CommunityUtil.getJSONString(1, "开始日期不能晚于结束日期！");
        }
        long uv = dataService.calculateUV(start, end);
        Map<String, Object> map = new HashMap<>();
        map.put("uvResult", uv);
        map.put("uvStartDate", start);
        map.put("uvEndDate", end);
        return CommunityUtil.getJSONString(0, null, map);
    }

    //显示网站UV
    @RequestMapping(path = "/data/uv/list", method = RequestMethod.POST)
    @ResponseBody
    public String getUVList(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date end){
        if(start == null || end == null){
            return CommunityUtil.getJSONString(1, "日期不能为空！");
        }
        if(start.after(end)){
            return CommunityUtil.getJSONString(1, "开始日期不能晚于结束日期！");
        }
        Map<String, Object> ans = new HashMap<>();
        List<Map<String, String>> unionVisitor = dataService.calculateUVRecord(start, end);
        ans.put("unionVisitor", unionVisitor);
        return CommunityUtil.getJSONString(0, null, ans);
    }

    // 统计活跃用户
    @RequestMapping(path = "/data/dau", method = RequestMethod.POST)
    @ResponseBody
    public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        if(start == null || end == null){
            return CommunityUtil.getJSONString(1, "日期不能为空！");
        }
        if(start.after(end)){
            return CommunityUtil.getJSONString(1, "开始日期不能晚于结束日期！");
        }
        long dau = dataService.calculateDAU(start, end);
        Map<String, Object> map = new HashMap<>();
        map.put("dauResult", dau);
        map.put("dauStartDate", start);
        map.put("dauEndDate", end);
        return CommunityUtil.getJSONString(0, null, map);
    }

}
