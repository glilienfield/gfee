package com.GfeeService.controllers;

import com.GfeeService.aws.dynamoDB.das.UpdateGfeeDAS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/gfee/update")
public class UpdateGfeeController {

    UpdateGfeeDAS updateGfeeDAS;

    public UpdateGfeeController(UpdateGfeeDAS updateGfeeDAS) {
        this.updateGfeeDAS = updateGfeeDAS;
    }

    @RequestMapping("/{sellerNumber}/{productCode}/{value}")
    public void updateSellerProduct(@PathVariable("sellerNumber") String sellerNumber,
                                    @PathVariable("productCode") int productCode,
                                    @PathVariable("value") BigDecimal value) {
        long start = System.currentTimeMillis();
        updateGfeeDAS.updateSellerProduct(sellerNumber, productCode, value);
        log.info("Time to update seller's gfee (ms) " + sellerNumber + ": " + (System.currentTimeMillis() - start));
    }
}
