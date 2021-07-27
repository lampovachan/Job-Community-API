package com.tkachuk.jobnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.tkachuk"})
@EnableSwagger2
public class JobnetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobnetworkApplication.class, args);
    }

}
