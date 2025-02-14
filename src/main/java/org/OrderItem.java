package org;

import java.math.BigDecimal;


public class OrderItem {
    private Smartphone smartphone;
    private int quantity;
    public double UnitPrice;
    public double price;

    // Constructor
    public OrderItem() {
    }

    // Getters and Setters
    public Smartphone getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {  // oder double
        return UnitPrice;
    }

    public double getPrice() {
        return price;
    }

}