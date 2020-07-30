package com.GfeeService.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.GfeeService", "com.provisionData"})
public class ProductsGfee {
	public static void main(String[] args) {
		SpringApplication.run(ProductsGfee.class, args);
	}
}
