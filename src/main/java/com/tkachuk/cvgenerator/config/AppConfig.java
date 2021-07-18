package com.tkachuk.cvgenerator.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Configuration
    @Profile("aws")
    public static class AwsConfig {
        private static final Regions REGION = Regions.US_EAST_1;

        @Bean
        @Primary
        public AmazonS3 amazonS3() {
            return AmazonS3ClientBuilder.standard()
                    .withRegion(REGION)
                    .build();
        }
    }
}