package com.GfeeService.aws.dynamoDB.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyDynamoDBClient {

    private final AmazonDynamoDB myDynamoDBClient;
    private final DynamoDBMapper myDynamoDBMapperClient;

    private String accessKey;
    private String secretKey;

    public MyDynamoDBClient(@Value("${dynamodb.accessKey}") String accessKey,
                            @Value("${dynamodb.secrectKey}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.myDynamoDBClient = createMyDynamoDBClient();
        this.myDynamoDBMapperClient = new DynamoDBMapper(this.myDynamoDBClient);
    }

    private AmazonDynamoDB createMyDynamoDBClient() {
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSCredentialsProvider() {
                                     @Override
                                     public AWSCredentials getCredentials() {
                                         return new AWSCredentials() {
                                             @Override
                                             public String getAWSAccessKeyId() {
                                                 return accessKey;
                                             }

                                             @Override
                                             public String getAWSSecretKey() {
                                                 return secretKey;
                                             }
                                         };
                                     }

                                     @Override
                                     public void refresh() {
                                     }
                                 }
                );
        return clientBuilder.build();
    }

    @Bean
    public AmazonDynamoDB getMyDynamoDBClient() {
        return myDynamoDBClient;
    }

    @Bean
    public DynamoDBMapper getMyDynamoDBMapperClient() {
        return myDynamoDBMapperClient;
    }
}
