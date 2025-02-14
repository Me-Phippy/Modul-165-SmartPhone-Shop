package org;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private BigDecimal total;

    // Constructor
    public Order() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Calculates the total amount of the order based on order items
     */
    public void calculateTotal() {
        for (OrderItem item : items) {
            if (item == null) {
                System.out.println("Fehler: Ein OrderItem ist null!");
            } else if (item.getUnitPrice() == null) {
                System.out.println("Fehler: unitPrice ist null für Item: " + item);
            }
        }

        this.total = BigDecimal.valueOf(
                items.stream()
                        .filter(item -> item != null && item.getUnitPrice() != null)
                        .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                        .sum()
        );
    }

    @Override
    public String toString() {
        return "Order ID: " + id +
                ", Customer: " + (customer != null ? customer.getFirstName() + " " + customer.getLastName() : "Unknown");
    }



    public BigDecimal getTotalPrice() {
        return total;
    }

}
