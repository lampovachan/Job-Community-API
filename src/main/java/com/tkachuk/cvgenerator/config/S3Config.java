package com.tkachuk.cvgenerator.config;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Config {
    public AmazonS3 configure() {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("127.0.0.1:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();
        return s3;
    }
}
