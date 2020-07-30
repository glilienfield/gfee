package com.GfeeService.aws.dynamoDB.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.math.BigInteger;

@DynamoDBTable(tableName = "Gfee_Hist")
public class Gfee_Hist {
    private String sellerProduct;
    private BigInteger endEpocTime;
    private BigInteger startEpocTime;
    private String name;
    private int productCode;
    private String sellerNumber;
    private BigDecimal histValue;

    public Gfee_Hist() {}

    public Gfee_Hist(String sellerProduct, BigInteger endEpocTime, BigInteger startEpocTime, String name, int productCode, String sellerNumber, BigDecimal histValue) {
        this.sellerProduct = sellerProduct;
        this.endEpocTime = endEpocTime;
        this.startEpocTime = startEpocTime;
        this.name = name;
        this.productCode = productCode;
        this.sellerNumber = sellerNumber;
        this.histValue = histValue;
    }

    @DynamoDBHashKey(attributeName = "sellerProduct")
    public String getSellerProduct() {
        return sellerProduct;
    }

    public void setSellerProduct(String sellerProduct) {
        this.sellerProduct = sellerProduct;
    }

    @DynamoDBRangeKey(attributeName = "endEpocTime")
    public BigInteger getEndEpocTime() {
        return endEpocTime;
    }

    public void setEndEpocTime(BigInteger endEpocTime) {
        this.endEpocTime = endEpocTime;
    }

    @DynamoDBAttribute(attributeName = "startEpocTime")
    public BigInteger getStartEpocTime() {
        return startEpocTime;
    }

    public void setStartEpocTime(BigInteger startEpocTime) {
        this.startEpocTime = startEpocTime;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "productCode")
    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @DynamoDBAttribute(attributeName = "sellerNumber")
    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    @DynamoDBAttribute(attributeName = "histValue")
    public BigDecimal getHistValue() {
        return histValue;
    }

    public void setHistValue(BigDecimal histValue) {
        this.histValue = histValue;
    }

    @Override
    public String toString() {
        return "sellerNumber="+this.sellerNumber+", productCode="+this.productCode+", name="+this.name+", startEpocTime="+this.startEpocTime+", endEpocTime="+this.endEpocTime+", value="+this.histValue;
    }
}
