package com.tkachuk.cvgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tkachuk"})
public class CVGenerator {
    public static void main(String[] args) {
        SpringApplication.run(CVGenerator.class, args);
    }
}
