package com.test.super_market_billing_system.dto;

/**
 *  Object to represent product item present in inventory
 */
public class ProductItem {
   private int productPrice; //price of item
   private int categoryDiscount; //Category level discount
   private int subCategoryDiscount; //Sub category level discount
   private int productDiscount; //real discount on item, 0 when giving free items on purchase
   private boolean isPercentDiscount; //to check if discount is of percentage type or free item on purchase
   private int buyItem; //Number of item customer buy
   private int freeItem; //Number of item get for free
   private int itemDiscount; //The product item discount which the customer will get, which is maximum of the three

   public ProductItem(int productPrice, int categoryDiscount, int subCategoryDiscount, int productDiscount, boolean isPercentDiscount, int buyItem, int freeItem) {
	   this.productPrice = productPrice;
	   this.categoryDiscount = categoryDiscount;
	   this.subCategoryDiscount = subCategoryDiscount;
	   this.productDiscount = productDiscount;
	   this.isPercentDiscount = isPercentDiscount;
	   this.buyItem = buyItem;
	   this.freeItem = freeItem;
	   if (this.isPercentDiscount) {
		   setRealDiscount();
	   }
   }

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getCategoryDiscount() {
		return categoryDiscount;
	}

	public void setCategoryDiscount(int categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}

	public int getSubCategoryDiscount() {
		return subCategoryDiscount;
	}

	public void setSubCategoryDiscount(int subCategoryDiscount) {
		this.subCategoryDiscount = subCategoryDiscount;
	}

	public int getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(int productDiscount) {
		this.productDiscount = productDiscount;
	}

	public boolean isPercentDiscount() {
		return isPercentDiscount;
	}

	public void setPercentDiscount(boolean percentDiscount) {
		isPercentDiscount = percentDiscount;
	}

	public int getBuyItem() {
		return buyItem;
	}

	public void setBuyItem(int buyItem) {
		this.buyItem = buyItem;
	}

	public int getFreeItem() {
		return freeItem;
	}

	public void setFreeItem(int freeItem) {
		this.freeItem = freeItem;
	}

	public int getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(int itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	/**
    *  This method calculates maximum discount from category branch
    */
   private void setRealDiscount() {
	   if (this.productDiscount > this.subCategoryDiscount && this.productDiscount > this.categoryDiscount) {
		   this.itemDiscount = this.productDiscount;
	   } else if (this.subCategoryDiscount > this.categoryDiscount) {
		   this.itemDiscount = this.subCategoryDiscount;
	   } else {
		   this.itemDiscount = this.categoryDiscount;
	   }
   }
}