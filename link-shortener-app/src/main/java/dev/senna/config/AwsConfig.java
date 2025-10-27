package dev.senna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    private final String localStackUrl = "http://localstack-main:4566";

    @Bean
    @Profile("local")
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(localStackUrl))
                .region(Region.SA_EAST_1)
                .build();
    }

    @Bean
    @Profile("!local")
    public DynamoDbClient dynamoDbClientAwsProd() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }
}
