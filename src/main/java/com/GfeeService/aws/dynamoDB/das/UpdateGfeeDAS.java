package com.GfeeService.aws.dynamoDB.das;

import com.GfeeService.aws.dynamoDB.entities.Gfee_Hist;
import com.GfeeService.aws.dynamoDB.entities.Gfee_Live;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTransactionWriteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TransactionCanceledException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UpdateGfeeDAS {

    DynamoDBMapper myDynamoDBMapperClient;

    GfeeLiveDAS gfeeLiveDAS;
    GfeeHistDAS gfeeHistDAS;

    public UpdateGfeeDAS(DynamoDBMapper myDynamoDBMapperClient, GfeeLiveDAS gfeeLiveDAS, GfeeHistDAS gfeeHistDAS) {
        this.myDynamoDBMapperClient = myDynamoDBMapperClient;
        this.gfeeLiveDAS = gfeeLiveDAS;
        this.gfeeHistDAS = gfeeHistDAS;
    }

    public void updateSellerProduct(String sellerNumber, int productCode, BigDecimal value) {
        Gfee_Live gfee_live = gfeeLiveDAS.getProductGfeeForSellerNo(sellerNumber, productCode);
        Gfee_Hist gfee_hist = gfeeHistDAS.getProductGfeeForSellerNo(sellerNumber, productCode, new BigInteger("99999999999999"));
        Gfee_Hist gfee_hist_next = gfee_hist.duplicate();

        BigInteger new_epoc_time = BigInteger.valueOf((Instant.from(ZonedDateTime.now()).toEpochMilli())).add(BigInteger.valueOf(10000));

        Map<String, AttributeValue> conditionLiveExpressionValues = new HashMap<>();
        conditionLiveExpressionValues.put(":current_live_gfee_value", new AttributeValue().withN(gfee_live.getGfee().toString()));
        conditionLiveExpressionValues.put(":current_hist_gfee_value", new AttributeValue().withN(gfee_hist.getHistValue().toString()));

        gfee_live.setGfee(value);
        gfee_hist.setEndEpocTime(new_epoc_time);
        gfee_hist_next.setHistValue(value);
        gfee_hist_next.setStartEpocTime(new_epoc_time.add(BigInteger.valueOf(1)));

        DynamoDBTransactionWriteExpression gfeeLiveUnchanged = new DynamoDBTransactionWriteExpression()
                .withConditionExpression("attribute_exists(gfee)")
                .withConditionExpression(":current_live_gfee_value = :current_hist_gfee_value")
                .withExpressionAttributeValues(conditionLiveExpressionValues);

        DynamoDBTransactionWriteExpression gfeeHistUnchanged = new DynamoDBTransactionWriteExpression()
                .withConditionExpression("attribute_exists(histValue)")
                .withConditionExpression("attribute_exists(startEpocTime)");

        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest()
                .addUpdate(gfee_live, gfeeLiveUnchanged)
                .addUpdate(gfee_hist_next, gfeeHistUnchanged)
                .addPut(gfee_hist);

        executeTransactionWrite(transactionWriteRequest);
    }

    private void executeTransactionWrite(TransactionWriteRequest transactionWriteRequest) {
        try {
            myDynamoDBMapperClient.transactionWrite(transactionWriteRequest);
        } catch (DynamoDBMappingException ddbme) {
            System.err.println("Client side error in Mapper, fix before retrying. Error: " + ddbme.getMessage());
        } catch (ResourceNotFoundException rnfe) {
            System.err.println("One of the tables was not found, verify table exists before retrying. Error: " + rnfe.getMessage());
        } catch (InternalServerErrorException ise) {
            System.err.println("Internal Server Error, generally safe to retry with back-off. Error: " + ise.getMessage());
        } catch (TransactionCanceledException tce) {
            System.err.println("Transaction Canceled, implies a client issue, fix before retrying. Error: " + tce.getMessage());
        } catch (Exception ex) {
            System.err.println("An exception occurred, investigate and configure retry strategy. Error: " + ex.getMessage());
        }
    }
}
