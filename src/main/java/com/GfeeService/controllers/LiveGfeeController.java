package com.GfeeService.controllers;

import com.GfeeService.aws.dynamoDB.das.GfeeLiveDAS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/gfee/live")
public class LiveGfeeController {

    @Autowired
    GfeeLiveDAS gfeeLiveDas;

    @RequestMapping("/{sellerNo}")
    public Map<String, BigDecimal> getGfeeBySellerNo(@PathVariable("sellerNo") String sellerNo) {
        long start = System.currentTimeMillis();
        Map<String, BigDecimal> result = gfeeLiveDas.getGfeeBySellerNo(sellerNo);
        log.info("Time to retrieve seller's gfee (ms) " + sellerNo + ": " + (System.currentTimeMillis() - start));
        return result;
    }

    @RequestMapping("/{sellerNo}/{product}")
    public BigDecimal getProductGfeeForSellerNo(@PathVariable("sellerNo") String sellerNo, @PathVariable("product") int product) {
        long start = System.currentTimeMillis();
        BigDecimal result = gfeeLiveDas.getProductGfeeForSellerNo(sellerNo, product).getGfee();
        log.info(String.format("Time to retrieve gfee for seller %s and product %s: %d (ms) ", sellerNo, product, (System.currentTimeMillis() - start)));
        return result;
    }
}
