package com.test.super_market_billing_system.dto;

/**
 * output data to
 */
public class InvoiceAmountDto {

    private String totalUnDiscountedBilledAmount;
    private String totalDiscountedBilledAmount;
    private String savedAmount;

    public InvoiceAmountDto(){

    }


    public String getTotalUnDiscountedBilledAmount() {
        return totalUnDiscountedBilledAmount;
    }

    public void setTotalUnDiscountedBilledAmount(String totalUnDiscountedBilledAmount) {
        this.totalUnDiscountedBilledAmount = totalUnDiscountedBilledAmount;
    }

    public String getTotalDiscountedBilledAmount() {
        return totalDiscountedBilledAmount;
    }

    public void setTotalDiscountedBilledAmount(String totalDiscountedBilledAmount) {
        this.totalDiscountedBilledAmount = totalDiscountedBilledAmount;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }
}
