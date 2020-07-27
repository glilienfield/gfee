package com.GfeeService.aws.DynamoDB.Entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "Gfee_Live")
public class Gfee_Live {
    private String sellerNumber;
    private String name;
    private BigDecimal value;
    private int productCode;

    public Gfee_Live() {}

    public Gfee_Live(String sellerNumber, String name, BigDecimal value, int productCode) {
        this.sellerNumber = sellerNumber;
        this.name = name;
        this.value = value;
        this.productCode = productCode;
    }

    @DynamoDBHashKey(attributeName = "sellerNumber")
    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    @DynamoDBRangeKey(attributeName = "productCode")
    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @DynamoDBAttribute(attributeName = "value")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SellerNo=" + this.sellerNumber + ", product=" + this.name + ", gfee=" + this.value + ", code=" + this.productCode;
    }
}
