package org.kosiuk.webApp.servletPaymentsApp.controller.dto;


import java.util.Objects;

public class PaymentSendingDto{

    private int senderMoneyAccId;
    private int receiverMoneyAccId;
    private long paymentNumber;
    private String payedSumString;
    private String totalString;

    public PaymentSendingDto(int senderMoneyAccId, int receiverMoneyAccId, long paymentNumber, String payedSumString, String totalString) {
        this.senderMoneyAccId = senderMoneyAccId;
        this.receiverMoneyAccId = receiverMoneyAccId;
        this.paymentNumber = paymentNumber;
        this.payedSumString = payedSumString;
        this.totalString = totalString;
    }

    public PaymentSendingDto() {
    }

    public int getSenderMoneyAccId() {
        return senderMoneyAccId;
    }

    public void setSenderMoneyAccId(int senderMoneyAccId) {
        this.senderMoneyAccId = senderMoneyAccId;
    }

    public int getReceiverMoneyAccId() {
        return receiverMoneyAccId;
    }

    public void setReceiverMoneyAccId(int receiverMoneyAccId) {
        this.receiverMoneyAccId = receiverMoneyAccId;
    }

    public long getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(long paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getPayedSumString() {
        return payedSumString;
    }

    public void setPayedSumString(String payedSumString) {
        this.payedSumString = payedSumString;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSendingDto guest = (PaymentSendingDto) o;
        return senderMoneyAccId == guest.getSenderMoneyAccId() &&
                receiverMoneyAccId == guest.getReceiverMoneyAccId() &&
                paymentNumber == guest.getPaymentNumber() &&
                payedSumString.equals(guest.getPayedSumString()) &&
                totalString.equals(guest.getTotalString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderMoneyAccId, receiverMoneyAccId, paymentNumber, payedSumString, totalString);
    }

    @Override
    public String toString() {
        return "PaymentSendingDto{" +
                "senderMoneyAccId=" + senderMoneyAccId +
                ", receiverCardNum=" + receiverMoneyAccId +
                ", paymentNumber=" + paymentNumber +
                ", payedSumString='" + payedSumString + '\'' +
                ", comissionString='" + totalString + '\'' +
                '}';
    }
}
