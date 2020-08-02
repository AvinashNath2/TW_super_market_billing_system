package com.test.super_market_billing_system.impl;

import com.test.super_market_billing_system.dto.CartItem;
import com.test.super_market_billing_system.dto.InputStringDto;
import com.test.super_market_billing_system.dto.InvoiceAmountDto;
import com.test.super_market_billing_system.dto.ProductItem;
import com.test.super_market_billing_system.enums.ProductItemUnit;
import com.test.super_market_billing_system.exception.InvalidInputDataException;
import org.springframework.http.HttpStatus;
import com.test.super_market_billing_system.utils.ApplicationConstant;
import com.test.super_market_billing_system.utils.ErrorMessageConstant;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This is an basic implementation of creating Super-Market BillingSystem please provide input in
 * input.txt file present in project
 *           Assumption
 *           1. There could not be any more unit than (KG, LT, DOZEN)
 *           2. The Input data will always be in the same template (resource/input.txt)
 *           3. In sample data set (provide by you) there was no case for category and sub-category discount so i added that in inventory item
 */
public class SuperMarketBillingSystem {

    private static final Logger LOGGER = Logger
            .getLogger(String.valueOf(SuperMarketBillingSystem.class));

    // Map for Super-Market Inventory
    private static Map<String, ProductItem> productItemMap = new HashMap<String, ProductItem>();

    public InvoiceAmountDto generateInvoice(InputStringDto inputStringDtoDto) throws InvalidInputDataException {
        poppulateInventory();
        //DecimalFormat to show amount in two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        List<CartItem> cartItems = new ArrayList<CartItem>();
        // get customer name
        String customerName = getCustomerName(inputStringDtoDto.getCustomerInfo());
        // generating customer invoice
        generateCustomerInvoice(inputStringDtoDto.getCustomerCartItems(), cartItems);

        InvoiceAmountDto invoiceAmountDto = new InvoiceAmountDto();
        // Total un-discounted billing amount
        double totalUnDiscountedBilledAmount = getTotalUnDiscountedBilledAmount(cartItems);
        invoiceAmountDto.setTotalUnDiscountedBilledAmount(
                decimalFormat.format(totalUnDiscountedBilledAmount));

        // Total amount to be paid by customer after discount
        double totalDiscountedBilledAmount = getTotalDiscountedBilledAmount(cartItems);
        invoiceAmountDto
                .setTotalDiscountedBilledAmount(decimalFormat.format(totalDiscountedBilledAmount));

        // Total discounted amount
        double savedAmount = getSavedAmount(totalUnDiscountedBilledAmount,
                totalDiscountedBilledAmount);
        invoiceAmountDto.setSavedAmount(decimalFormat.format(savedAmount));

        /**
         * Generate invoice in console
         */
        System.out.println("Customer:       " + customerName);
        System.out.println("Item \t\t     Qty \t\t Amount");
        for (CartItem item : cartItems) {
            System.out.println(
                    item.getItemName() + "\t\t     " + item.getQuantity() + item.getUnit() + " \t "
                            + decimalFormat.format(item.getDiscountedAmount()));
        }
        System.out.println("----------------------------------------------------------");
        System.out.println(
                "\nTotal Amount         " + decimalFormat.format(totalDiscountedBilledAmount)
                        + " Rs");
        System.out.println(
                "\nYou saved            " + decimalFormat.format(totalUnDiscountedBilledAmount)
                        + " - "
                        + decimalFormat.format(totalDiscountedBilledAmount) + " = " + decimalFormat
                        .format(savedAmount) + " Rs");

        return invoiceAmountDto;
    }

    /**
     * This method initialize list of product
     */
    private void poppulateInventory() {
        productItemMap.put("Apple", new ProductItem(50, 10, 18, 0, false, 3, 1));
        productItemMap.put("Orange", new ProductItem(80, 10, 18, 20, true, 0, 0));
        productItemMap.put("Potato", new ProductItem(30, 10, 5, 0, false, 5, 2));
        productItemMap.put("Tomato", new ProductItem(70, 10, 5, 10, true, 0, 0));
        productItemMap.put("Cow Milk", new ProductItem(50, 15, 20, 0, false, 3, 1));
        productItemMap.put("Soy Milk", new ProductItem(40, 15, 20, 10, true, 0, 0));
        productItemMap.put("Cheddar", new ProductItem(50, 15, 20, 0, false, 2, 1));
        productItemMap.put("Gouda", new ProductItem(80, 15, 20, 10, true, 0, 0));
        //Additional case where object unit is in dozen
        productItemMap.put("Banana", new ProductItem(5, 15, 20, 0, false, 0, 0));
        //Additional cases with no category discount or sub category discount
        productItemMap.put("Mango", new ProductItem(50, 10, 18, 0, false, 0, 0));
        productItemMap.put("ButterMilk", new ProductItem(50, 15, 0, 0, false, 0, 0));
    }

    private void generateCustomerInvoice(String customerCartItemsStr, List<CartItem> cartItems)
            throws InvalidInputDataException {

        String[] cartItemsArray = customerCartItemsStr.split(",");
        // to get an item detail from item hashmap
        ProductItem currentProductItem;
        // to store details of bought items
        CartItem customerCartItem;
        // Loop through customer order and generating invoice data
        for (String cartItem : cartItemsArray) {
            // flag to check if unit is mentioned as dozen
            boolean unitTypeDozen = Boolean.FALSE;
            //remove extra spaces
            cartItem = cartItem.trim();
            customerCartItem = new CartItem();
            setCartItemNameQuantityAndUnit(customerCartItem, cartItem);

            // Checking if unit is non-null of null then throw exception else extract
            if (customerCartItem.getUnit() != null) {
                ProductItemUnit productItemUnit = ProductItemUnit
                        .valueOf(customerCartItem.getUnit().toUpperCase());
                //unit conversion to quantity
                switch (productItemUnit) {
                    case KG:
                    case LT:
                        break;
                    case DOZEN:
                        customerCartItem.setQuantity(customerCartItem.getQuantity() * productItemUnit.getUnitQuantity());
                        unitTypeDozen = true;
                        break;
                    default:
                        LOGGER.warning(ErrorMessageConstant.INVALID_UNIT);
                        break;
                }
            } else {
                throw new InvalidInputDataException(HttpStatus.BAD_REQUEST.value(), ErrorMessageConstant.ENTER_VALID_INPUT);
            }
            currentProductItem = productItemMap.get(customerCartItem.getItemName());
            customerCartItem.setUnDiscountedAmount(
                    customerCartItem.getQuantity() * currentProductItem.getProductPrice());

            if (currentProductItem.isPercentDiscount()) {
                //calculate billing amount by percentage discount
                customerCartItem.setDiscountedAmount(customerCartItem.getUnDiscountedAmount() * (100 - currentProductItem.getItemDiscount()) / 100);
            } else if(currentProductItem.getFreeItem() > 0){
                //calculate billing amount by item on item discount
                int noOfDiscountItems = customerCartItem.getQuantity() / (currentProductItem.getBuyItem() + currentProductItem.getFreeItem());
                int discountedQuantity = customerCartItem.getQuantity() - (noOfDiscountItems * currentProductItem.getFreeItem());
                customerCartItem.setDiscountedAmount(discountedQuantity * currentProductItem.getProductPrice());
            }else  if(currentProductItem.getSubCategoryDiscount() > 0){
                //apply category discount if no percentage discount or item discount -> use sub-category discount
                customerCartItem.setDiscountedAmount(customerCartItem.getUnDiscountedAmount() * (100 - currentProductItem.getSubCategoryDiscount()) / 100);
            }else if(currentProductItem.getCategoryDiscount() > 0){
                //apply category discount if no percentage discount or item discount & categoryDiscount  -> use category discount
                customerCartItem.setDiscountedAmount(customerCartItem.getUnDiscountedAmount() * (100 - currentProductItem.getCategoryDiscount()) / 100);
            }
            if (unitTypeDozen) {
                customerCartItem.setQuantity(customerCartItem.getQuantity() / 12);
            }
            cartItems.add(customerCartItem);
        }
    }

    private void setCartItemNameQuantityAndUnit(CartItem customerCartItem, String cartItem) {
        // separating item by last space, so we can get name, quantity and unit
        int pos = cartItem.lastIndexOf(" ");
        customerCartItem.setItemName(cartItem.substring(0, pos));
        String quantity = cartItem.substring(pos + 1);

        String[] quantityPart = quantity.split(ApplicationConstant.REGEX_FOR_GETTING_UNIT);
        customerCartItem.setQuantity(Integer.parseInt(quantityPart[0]));
        if (quantityPart.length > 1) {
            customerCartItem.setUnit(quantityPart[1]);
        }
    }

    //add total un-discounted present in each item
    private double getTotalUnDiscountedBilledAmount(List<CartItem> cartItems) {
        double totalUnDiscountedAmount = 0;
        for (CartItem item : cartItems) {
            totalUnDiscountedAmount += item.getUnDiscountedAmount();
        }
        return totalUnDiscountedAmount;
    }

    //add total discounted present in each item
    private double getTotalDiscountedBilledAmount(List<CartItem> cartItems) {
        double totalDiscountedAmount = 0;
        for (CartItem item : cartItems) {
            totalDiscountedAmount += item.getDiscountedAmount();
        }
        return totalDiscountedAmount;
    }

    private double getSavedAmount(double totalUnDiscountedBilledAmount,
            double totalDiscountedBilledAmount) {
        return totalUnDiscountedBilledAmount - totalDiscountedBilledAmount;
    }

    private String getCustomerName(String inputString) {
        String[] customerInfo = inputString.split(" ");
        StringBuilder customerName = new StringBuilder();
        customerName.append(customerInfo[1]);
        customerName.append(" ");
        customerName.append(customerInfo[2]);
        return customerName.toString();
    }
}


