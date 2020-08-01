package com.test.super_market_billing_system.dto;

public class InputStringDto {
    private String customerInfo;
    private String customerCartItems;

    public InputStringDto(){

    }

    public InputStringDto(String customerInfo, String customerCartItems) {
        this.customerInfo = customerInfo;
        this.customerCartItems = customerCartItems;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getCustomerCartItems() {
        return customerCartItems;
    }

    public void setCustomerCartItems(String customerCartItems) {
        this.customerCartItems = customerCartItems;
    }
}
