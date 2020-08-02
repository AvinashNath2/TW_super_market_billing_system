package com.test.super_market_billing_system;

import com.test.super_market_billing_system.dto.InputStringDto;
import com.test.super_market_billing_system.exception.InvalidInputDataException;
import com.test.super_market_billing_system.impl.FileReaderServiceImpl;
import com.test.super_market_billing_system.impl.SuperMarketBillingSystem;
import com.test.super_market_billing_system.service.FileReaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.test")
public class SuperMarketBillingSystemApplication {

    public static void main(String[] args) throws InvalidInputDataException {
        SpringApplication.run(SuperMarketBillingSystemApplication.class, args);
        //Get deafult input from file (resource/input.txt)
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        InputStringDto inputStringDto = fileReaderService.getDataFromFile();

        SuperMarketBillingSystem superMarketBillingSystem = new SuperMarketBillingSystem();
        superMarketBillingSystem.generateInvoice(inputStringDto);
    }

}
