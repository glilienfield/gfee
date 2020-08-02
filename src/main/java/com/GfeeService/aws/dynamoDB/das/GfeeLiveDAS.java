package com.GfeeService.aws.dynamoDB.das;


import com.GfeeService.aws.dynamoDB.entities.Gfee_Live;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GfeeLiveDAS {

    DynamoDBMapper mapper;

    public GfeeLiveDAS(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, BigDecimal> getGfeeBySellerNo(String sellerNumber) {

        Map<String, BigDecimal> result = null;
        try {

            Map<String, AttributeValue> paramMap = new HashMap<>();
            paramMap.put(":sellerNumber", new AttributeValue().withS(sellerNumber));

            DynamoDBQueryExpression<Gfee_Live> queryExpression = new DynamoDBQueryExpression<Gfee_Live>()
                    .withKeyConditionExpression("sellerNumber = :sellerNumber")
                    .withExpressionAttributeValues(paramMap);

            List<Gfee_Live> gfeeList = mapper.query(Gfee_Live.class, queryExpression);

            if (gfeeList != null) {
                log.info(String.format("Gfee for sellerNumber %s was found", sellerNumber));
                result = gfeeList.stream().collect(Collectors.toMap(Gfee_Live::getName, Gfee_Live::getGfee));
            } else {
                log.info(String.format("sellerNumber %s was not found", sellerNumber));
                result = Collections.EMPTY_MAP;
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    public Gfee_Live getProductGfeeForSellerNo(String sellerNumber, int productCode) {

        Gfee_Live item = null;

        try {
            item = mapper.load(Gfee_Live.class, sellerNumber, productCode);
            if (item != null) {
                log.info(String.format("Gfee found for sellerNumber %s and product code %s", sellerNumber, productCode));
                log.info("Gfee value: " + item.getGfee());
            } else {
                log.info(String.format("Gfee not found for sellerNumber %s and product code %s", sellerNumber, productCode));
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return item;
    }
}
