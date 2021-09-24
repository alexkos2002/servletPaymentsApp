package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

public class CreditCardOrderWithUserDto {

    private int id;
    private OrderStatus orderStatus;
    private String message;
    private PaymentSystem paymentSystem;
    private Integer ownerId;
    private String ownerName;

    public CreditCardOrderWithUserDto(int id, OrderStatus orderStatus, String message,
                                      PaymentSystem paymentSystem, Integer ownerId, String ownerName) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.message = message;
        this.paymentSystem = paymentSystem;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public CreditCardOrderWithUserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
