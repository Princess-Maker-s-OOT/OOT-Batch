package com.ootbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = {
        "com.ootbatch",                     // 배치 서버 엔티티
        "com.ootcommon.recommendation"      // 공통 모듈 엔티티
})
@EnableJpaRepositories(basePackages = {
        "com.ootbatch",                     // 배치 서버 레포지토리
})
public class OotBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(OotBatchApplication.class, args);
    }
}