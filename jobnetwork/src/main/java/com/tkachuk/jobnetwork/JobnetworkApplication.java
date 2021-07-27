package com.tkachuk.jobnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tkachuk"})
public class JobnetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobnetworkApplication.class, args);
    }

}
