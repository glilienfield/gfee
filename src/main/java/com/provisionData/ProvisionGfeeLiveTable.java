package com.provisionData;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProvisionGfeeLiveTable {

    static void provisionTable(AmazonDynamoDB myAmazonDynamoDBClient) {

        String tableName = "Gfee_Live";
        try {
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("sellerNumber").withAttributeType(ScalarAttributeType.S))
                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("productCode").withAttributeType(ScalarAttributeType.N))
                    .withKeySchema(new KeySchemaElement().withAttributeName("sellerNumber").withKeyType(KeyType.HASH))
                    .withKeySchema(new KeySchemaElement().withAttributeName("productCode").withKeyType(KeyType.RANGE))
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(5L));

            TableUtils.createTableIfNotExists(myAmazonDynamoDBClient, createTableRequest);
            TableUtils.waitUntilActive(myAmazonDynamoDBClient, tableName);

            log.info(tableName + " table created");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
