package com.dbq.s3storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class S3StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3StorageApplication.class, args);
    }
}