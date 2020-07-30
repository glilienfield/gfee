package com.GfeeService.aws.dynamoDB.das;

import com.GfeeService.aws.dynamoDB.entities.Gfee_Hist;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GfeeHistDAS {

    DynamoDBMapper mapper;

    public GfeeHistDAS(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public BigDecimal getProductGfeeForSellerNo(String sellerNo, int productCode, long gfeePriceEpoc) {

        BigDecimal result = null;
        String sellerProduct = sellerNo + "|" + productCode;

        try {
            Map<String, AttributeValue> paramMap = new HashMap<>();
            paramMap.put(":sellerProduct", new AttributeValue().withS(sellerProduct));
            paramMap.put(":gfeePriceEpoc", new AttributeValue().withN(String.valueOf(gfeePriceEpoc)));

            DynamoDBQueryExpression<Gfee_Hist> queryExpression = new DynamoDBQueryExpression<Gfee_Hist>()
                    .withKeyConditionExpression("sellerProduct = :sellerProduct and endEpocTime >= :gfeePriceEpoc")
                    .withProjectionExpression("histValue")
                    .withFilterExpression("startEpocTime <= :gfeePriceEpoc")
                    .withExpressionAttributeValues(paramMap);

            List<Gfee_Hist> gfeeList = mapper.query(Gfee_Hist.class, queryExpression);

            if (gfeeList != null && gfeeList.size() == 1) {
                result = gfeeList.get(0).getHistValue();
                log.info(String.format("Gfee found for SellerNo %s and product %s and epocTime %s", sellerNo, productCode, gfeePriceEpoc));
                log.info("seller/product gfee info: " + result);
            } else if (gfeeList != null && gfeeList.size() > 1) {
                log.info(String.format("More than one Gfee value found for SellerNo %s and product %s and epocTime %s", sellerNo, productCode, gfeePriceEpoc));
            } else {
                log.info(String.format("Gfee not found for SellerNo %s and product %s and epocTime %s", sellerNo, productCode, gfeePriceEpoc));
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return result;
    }
}
