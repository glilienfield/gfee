package com.provisionData;

import com.GfeeService.aws.dynamoDB.entities.Gfee_Hist;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProvisionGfeeHistItems {
    static String[] sellerNumbers = {"111111111", "111111111", "111111111", "111111111"};
    static Integer[] productCode = {100, 100, 100, 100};
    static String[] values = {"50", "52", "54", "56"};
    static String[] names = {"FRM_30_YR", "FRM_30_YR", "FRM_30_YR", "FRM_30_YR"};
    static String[] startEpocTime = {"1588330800000", "1589540405000", "1591009205000", "1592218805000"};
    static String[] endEpocTime = {"1589540400000", "1591009200000", "1592218800000", "99999999999999"};


    static void provisionItems(DynamoDBMapper myDynamoDBMapperClient) {
        List<Gfee_Hist> gfeeList = new ArrayList<>();
        for(int i=0 ; i<sellerNumbers.length ; i++) {
            gfeeList.add(new Gfee_Hist(sellerNumbers[i]+"|"+productCode[i], new BigInteger(endEpocTime[i]), new BigInteger(startEpocTime[i]), names[i], productCode[i], sellerNumbers[i], new BigDecimal(values[i])));
        }
        myDynamoDBMapperClient.batchSave(gfeeList);
        log.info("Provisioned Hist Gfee values");
    }
}
