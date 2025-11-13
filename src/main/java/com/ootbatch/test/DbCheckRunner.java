package com.ootbatch.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbCheckRunner implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        System.out.println("MySQL 연결 결과: " + result); // 1이면 OK
    }
}
