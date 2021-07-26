package com.tkachuk.cvgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"common"})
public class CVGenerator {
    public static void main(String[] args) {
        SpringApplication.run(CVGenerator.class, args);
    }
}
