package com.provisionData;

import com.GfeeService.aws.dynamoDB.entities.Gfee_Live;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProvisionGfeeLiveItems {
    static String[] sellerNumbers = {"111111111", "111111111", "111111111", "22222222", "22222222", "22222222"};
    static Integer[] productCode = {100, 101, 102, 100, 101, 102};
    static String[] values = {"40", "42", "44", "50", "52", "54"};
    static String[] names = {"FRM_30_YR", "FRM_30_YR_85k", "FRM_30_YR_125k", "FRM_30_YR", "FRM_30_YR_85k", "FRM_30_YR_125k"};


    static void provisionItems(DynamoDBMapper myDynamoDBMapperClient) {
        List<Gfee_Live> gfeeList = new ArrayList<>();
        for(int i=0 ; i<sellerNumbers.length ; i++) {
            gfeeList.add(new Gfee_Live(sellerNumbers[i], names[i], new BigDecimal(values[i]), productCode[i]));
        }
        myDynamoDBMapperClient.batchSave(gfeeList);
        log.info("Provisioned Live Gfee values");
    }
}
