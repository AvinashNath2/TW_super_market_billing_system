package com.test.super_market_billing_system.enums;

/**
 * Product units
 */
public enum ProductItemUnit {
    KG("KG"),
    LT("LT"),
    DOZEN("DOZEN");
    String name;

    ProductItemUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
