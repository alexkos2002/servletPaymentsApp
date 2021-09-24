package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CreditCardOrder {

    private int id;
    private PaymentSystem desPaymentSystem;
    private String message;
    private OrderStatus status;
    private int userId;

    public CreditCardOrder(int id, PaymentSystem desPaymentSystem, String message, OrderStatus status) {
        this.id = id;
        this.desPaymentSystem = desPaymentSystem;
        this.message = message;
        this.status = status;
    }

    public CreditCardOrder() {
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private int id;
        private PaymentSystem paymentSystem;
        private String message;
        private OrderStatus status;
        private int userId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder paymentSystem(PaymentSystem paymentSystem) {
            this.paymentSystem = paymentSystem;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(OrderStatus orderStatus) {
            this.status = orderStatus;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public CreditCardOrder build() {
            CreditCardOrder order = new CreditCardOrder();
            order.setId(id);
            order.setDesPaymentSystem(paymentSystem);
            order.setMessage(message);
            order.setStatus(status);
            order.setUserId(userId);
            return order;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentSystem getDesPaymentSystem() {
        return desPaymentSystem;
    }

    public void setDesPaymentSystem(PaymentSystem desPaymentSystem) {
        this.desPaymentSystem = desPaymentSystem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CreditCardOrder guest = (CreditCardOrder) o;
        return id == guest.getId() && desPaymentSystem == guest.getDesPaymentSystem() &&
                ((message == null && guest.getMessage() == null) || (message != null && message.equals(guest.getMessage())))
                && status == guest.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desPaymentSystem, message, status);
    }

    @Override
    public String toString() {
        return "CreditCardOrder{" +
                "id=" + id +
                ", desPaymentSystem=" + desPaymentSystem +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

}
