package com.ootbatch.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCheckRunner implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) {
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        redisTemplate.opsForValue().set("test-key", "hello");
        String value = redisTemplate.opsForValue().get("test-key");
        System.out.println("Redis 연결 결과: ping=" + pong + ", value=" + value); // pong/hello면 OK
    }
}