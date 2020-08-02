package com.test.super_market_billing_system.enums;

/**
 * Product units with it's quantity
 */
public enum ProductItemUnit {

    KG("KG", 1),
    LT("LT", 1),
    DOZEN("DOZEN", 12);

    String name;
    int unitQuantity;

    ProductItemUnit(String name, int unitQuantity) {
        this.name = name;
        this.unitQuantity = unitQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int unitQuantity) {
        this.unitQuantity = unitQuantity;
    }
}
