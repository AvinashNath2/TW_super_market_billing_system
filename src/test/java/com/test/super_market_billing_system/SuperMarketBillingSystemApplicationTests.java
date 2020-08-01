package com.test.super_market_billing_system;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.test.super_market_billing_system.dto.InputStringDto;
import com.test.super_market_billing_system.dto.InvoiceAmountDto;
import com.test.super_market_billing_system.exception.InvalidDataException;
import com.test.super_market_billing_system.impl.SuperMarketBillingSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class SuperMarketBillingSystemApplicationTests {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test to create and assert basic receipt provide in Task
     * @throws InvalidDataException
     */
    @Test
    void testBillAmountAndUnBillAmountAndSavedAmount() throws InvalidDataException {
        SuperMarketBillingSystem superMarketBillingSystem = new SuperMarketBillingSystem();
        InputStringDto inputStringDto = new InputStringDto();
        inputStringDto.setCustomerInfo("Customer Anish Kumar buys following items");
        inputStringDto.setCustomerCartItems(
                "Apple 6Kg, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg");
        InvoiceAmountDto result = superMarketBillingSystem.generateInvoice(inputStringDto);

        //check invoice amount should not be empty
        Assert.assertNotNull(result.getTotalUnDiscountedBilledAmount());
        Assert.assertNotNull(result.getTotalDiscountedBilledAmount());
        Assert.assertNotNull(result.getSavedAmount());

        //check invoice amount should not be equal to desired output
        Assert.assertEquals(String.valueOf(result.getSavedAmount()), "355.00");
        Assert.assertEquals(String.valueOf(result.getTotalDiscountedBilledAmount()), "1295.00");
        Assert.assertEquals(String.valueOf(result.getTotalUnDiscountedBilledAmount()), "1650.00");
    }

    /**
     * Test if customer is info is in correct format (line1 of input)
     */
    @Test
    void testInputTextForCustomerName() {
        SuperMarketBillingSystem superMarketBillingSystem = new SuperMarketBillingSystem();
        InputStringDto inputStringDto = new InputStringDto();
        inputStringDto.setCustomerInfo("Anish Kumar");
        inputStringDto.setCustomerCartItems(
                "Apple 6Kg, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> superMarketBillingSystem.generateInvoice(inputStringDto));
    }

    /**
     * Test if customer item has No unit (Apple 6)
     */
    @Test
    void testCartItemWithNoUnit() {
        SuperMarketBillingSystem superMarketBillingSystem = new SuperMarketBillingSystem();
        InputStringDto inputStringDto = new InputStringDto();
        inputStringDto.setCustomerInfo("Customer Anish Kumar buys following items");
        inputStringDto.setCustomerCartItems(
                "Apple 6, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg");
        assertThrows(InvalidDataException.class, () -> superMarketBillingSystem.generateInvoice(inputStringDto));
    }

}
