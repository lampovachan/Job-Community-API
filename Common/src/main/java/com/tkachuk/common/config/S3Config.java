package com.tkachuk.common.config;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for using S3 as storage.
 *
 * @author Svitlana Tkachuk
 */

@Configuration
public class S3Config {
    @Bean
    public AmazonS3 configure() {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("localstack:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();
        return s3;
    }
}