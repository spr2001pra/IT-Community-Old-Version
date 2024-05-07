package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        // 有了连接工厂后才具备访问数据库的能力
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 配置Template主要是配置序列化的方式，因为程序是Java程序，数据是Java数据
        // 由于需要把它们存到Redis数据库中，所以要设置数据转化的方式，也就是序列化方式

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式,Value可能有复杂类型，建议设置成机构化数据Json
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        // 触发所有设置，令其生效
        template.afterPropertiesSet();
        return template;
    }

}
