package com.trombae.pathofbossingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PathOfBossingApiApp {
    public static void main(String[] args) {
        SpringApplication.run(PathOfBossingApiApp.class, args);
    }
}