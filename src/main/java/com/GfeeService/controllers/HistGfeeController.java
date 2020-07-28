package com.GfeeService.controllers;

import com.GfeeService.aws.dynamoDB.das.GfeeHistDAS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/gfee/hist")
public class HistGfeeController {

    @Autowired
    GfeeHistDAS gfeeHistDas;

    @RequestMapping("/{sellerNumber}/{productCode}/{gfeePriceEpoc}")
    public BigDecimal getGfeeBySellerNo(@PathVariable("sellerNumber") String sellerNumber,
                                                     @PathVariable("productCode") int productCode,
                                                     @PathVariable("gfeePriceEpoc") long gfeePriceEpoc) {
        long start = System.currentTimeMillis();
        BigDecimal result = gfeeHistDas.getProductGfeeForSellerNo(sellerNumber, productCode, gfeePriceEpoc);
        log.info("Time to retrieve seller's gfee (ms) " + sellerNumber + ": " + (System.currentTimeMillis() - start));
        return result;
    }
}
