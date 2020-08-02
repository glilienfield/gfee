package com.GfeeService.aws.dynamoDB.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "Gfee_Live")
public class Gfee_Live {
    private String sellerNumber;
    private String name;
    private BigDecimal gfee;
    private int productCode;

    public Gfee_Live() {}

    public Gfee_Live(String sellerNumber, String name, BigDecimal gfee, int productCode) {
        this.sellerNumber = sellerNumber;
        this.name = name;
        this.gfee = gfee;
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

    @DynamoDBAttribute(attributeName = "gfee")
    public BigDecimal getGfee() {
        return gfee;
    }

    public void setGfee(BigDecimal gfee) {
        this.gfee = gfee;
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
        return "SellerNumber=" + this.sellerNumber + ", name=" + this.name + ", gfee=" + this.gfee + ", productCode=" + this.productCode;
    }

    public Gfee_Live duplicate() {
        return new Gfee_Live(sellerNumber, name, gfee, productCode);
    }
}
