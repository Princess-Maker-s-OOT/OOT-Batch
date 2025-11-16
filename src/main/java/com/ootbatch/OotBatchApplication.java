package com.ootbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OotBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(OotBatchApplication.class, args);
    }

}
