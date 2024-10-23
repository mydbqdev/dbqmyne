package com.dbq.userservice;

import com.dbq.userservice.enums.Role;
import com.dbq.userservice.model.User;
import com.dbq.userservice.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    public UserServiceApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        final String pass = "$2a$10$2529eBq3R6Y41t03Mku2I.2Nh3W0p25lt.s.85mG0kiAvxI4bsAHa";
//        var admin = User.builder()
//                .username("admin@fincentives.co")
//                .password(pass)
//                .roles(new HashSet<Role>(Arrays.asList(Role.ADMIN)))
//                .build();
//        if (userRepository.findByUsername("admin").isEmpty()) 
//        	userRepository.save(admin);
    }
}
