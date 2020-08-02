package com.test.super_market_billing_system.dto;

/**
 * Object to represent customer cart item in details
 */
public class CartItem {
	private String itemName; // cart item name
	private int quantity; // quantity of single item added in cart
	private String unit; //unit of cart item (ProductItemUnit)
	private double unDiscountedAmount; //real cost of item
	private double discountedAmount; //cost of item after applied discount

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getUnDiscountedAmount() {
		return unDiscountedAmount;
	}

	public void setUnDiscountedAmount(double unDiscountedAmount) {
		this.unDiscountedAmount = unDiscountedAmount;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
}