package com.GfeeService.controllers;

import com.provisionData.ProvisionTables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProvisionTablesController {

    @Autowired
    ProvisionTables provisionTables;

    @RequestMapping("/provision")
    public String provisionTables() {
        new Thread(()->provisionTables.provisionTables()).start();
        return "Submitted table provisioning request";
    }
}
