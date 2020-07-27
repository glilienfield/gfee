package com.GfeeService.controllers;

import com.GfeeService.aws.DynamoDB.DAS.GfeeDAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/gfee")
public class GfeeController {

    @Autowired
    GfeeDAS gfeeDas;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/live/{sellerNo}")
    public Map<String, BigDecimal> getGfeeBySellerNo(@PathVariable("sellerNo") String sellerNo) {
        long start = System.currentTimeMillis();
        Map<String, BigDecimal> result = gfeeDas.getGfeeBySellerNo(sellerNo);
        logger.info("Time to retrieve seller's gfee (ms) " + sellerNo + ": " + (System.currentTimeMillis() - start));
        return result;
    }

    @RequestMapping("/live/{sellerNo}/{product}")
    public BigDecimal getProductGfeeForSellerNo(@PathVariable("sellerNo") String sellerNo, @PathVariable("product") int product) {
        long start = System.currentTimeMillis();
        BigDecimal result = gfeeDas.getProductGfeeForSellerNo(sellerNo, product);
        logger.info(String.format("Time to retrieve gfee for seller %s and product %s: %d (ms) ", sellerNo, product, (System.currentTimeMillis() - start)));
        return result;
    }
}
