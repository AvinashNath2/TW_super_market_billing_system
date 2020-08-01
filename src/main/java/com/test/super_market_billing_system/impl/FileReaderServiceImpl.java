package com.test.super_market_billing_system.impl;

import com.test.super_market_billing_system.dto.InputStringDto;
import com.test.super_market_billing_system.service.FileReaderService;
import com.test.super_market_billing_system.utils.ErrorMessageConstant;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class FileReaderServiceImpl implements FileReaderService {

    private static final Logger LOGGER = Logger
            .getLogger(String.valueOf(SuperMarketBillingSystem.class));

    /**
     * Read input data from file input.txt (for testing purpose you can change the data there)
     */
    @Override
    public InputStringDto getDataFromFile() {
        InputStringDto inputStringDtoDto = new InputStringDto();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            inputStringDtoDto.setCustomerInfo(reader.readLine());
            inputStringDtoDto.setCustomerCartItems(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.severe(ErrorMessageConstant.FILE_READING_EXCEPTION);
        }
        return inputStringDtoDto;
    }
}
