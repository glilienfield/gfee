package com.provisionData;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProvisionTables {

    private final AmazonDynamoDB myAmazonDynamoDBClient;
    private final DynamoDBMapper myDynamoDBMapperClient;

    public ProvisionTables(AmazonDynamoDB myAmazonDynamoDBClient, DynamoDBMapper myDynamoDBMapperClient) {
        this.myAmazonDynamoDBClient = myAmazonDynamoDBClient;
        this.myDynamoDBMapperClient = myDynamoDBMapperClient;
    }

    private final String[] tableNameList = {"Gfee_Live", "Gfee_Hist"};

    public void provisionTables() {
        this.deleteAllTables();
        ProvisionGfeeLiveTable.provisionTable(myAmazonDynamoDBClient);
        ProvisionGfeeHistTable.provisionTable(myAmazonDynamoDBClient);
        ProvisionGfeeLiveItems.provisionItems(myDynamoDBMapperClient);
        ProvisionGfeeHistItems.provisionItems(myDynamoDBMapperClient);
    }

    private void deleteAllTables() {
        DeleteTableResult deleteTableResult;
        for (String table : tableNameList) {
            boolean wait = false;
            try {
                deleteTableResult = myAmazonDynamoDBClient.deleteTable(table);
                log.info("Deleted table result: " + deleteTableResult);
                wait = true;
            } catch (ResourceNotFoundException resourceNotFoundException) {
                log.info("Table does not exist to delete: " + table);
            } catch (ResourceInUseException resourceInUseException) {
                log.info("Table is in use, can't be deleted: " + table);
                throw resourceInUseException;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(wait) {
                    log.info("Sleeping following deleting table " + table + " to ensure it is deleted");
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
