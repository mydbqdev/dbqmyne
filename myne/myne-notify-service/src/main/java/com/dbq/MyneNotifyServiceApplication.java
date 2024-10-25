package com.dbq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyneNotifyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyneNotifyServiceApplication.class, args);
	}

}
