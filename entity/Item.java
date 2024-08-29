package com.rsl.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itm_id")
    private int id; // Unique identifier for the item

    @NotBlank(message = "Item name is required")
    @Column(name = "itemName")
    private String itemName; // Name of the item

    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity")
    private int quantity; // Quantity of the item

    @Min(value = 0, message = "Price must be non-negative")
    @Column(name = "price")
    private int price; // Price of the item

    @Min(value = 0, message = "Total amount must be non-negative")
    @Column(name = "totalAmount")
    private int totalAmount; // Total amount for the item

    @Min(value = 0, message = "Discount must be non-negative")
    @Column(name = "discount")
    private int discount; // Discount amount for the item
    
    @Min(value = 0, message = "Payable amount must be non-negative")
    @Column(name = "payableAmount")
    private int payableAmount; // Payable amount after discount

    @Column(name = "miscellaneous")
    private String miscellaneous; // Miscellaneous information about the item

    // Getters and Setters
    // ...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }
}
