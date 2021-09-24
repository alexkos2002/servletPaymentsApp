package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;


public class CreditCardConfirmationDto {

    private long number;
    private int cvv;
    private String expireDateString;
    private PaymentSystem paymentSystem;

    public CreditCardConfirmationDto(long number, int cvv, String expireDateString, PaymentSystem paymentSystem) {
        this.number = number;
        this.cvv = cvv;
        this.expireDateString = expireDateString;
        this.paymentSystem = paymentSystem;
    }

    public CreditCardConfirmationDto() {
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpireDateString() {
        return expireDateString;
    }

    public void setExpireDateString(String expireDateString) {
        this.expireDateString = expireDateString;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
