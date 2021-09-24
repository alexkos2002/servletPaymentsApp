package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

public class PaymentConfirmationDto {

    private int senderMoneyAccountId;
    private int receiverMoneyAccountId;
    private long receiverRequisitesNumber;
    private String receiverMoneyAccountName;
    private long paymentNumber;
    private String assignment;
    private long payedSumInt;
    private int payedSumDec;
    private long totalInt;
    private int totalDec;
    private long paymentComissionInt;
    private int paymentComissionDec;

    public PaymentConfirmationDto(int senderMoneyAccountId, int receiverMoneyAccountId, long receiverRequisitesNumber,
                                  String receiverMoneyAccountName, long paymentNumber, String assignment,
                                  long payedSumInt, int payedSumDec, long totalInt, int totalDec, long paymentComissionInt,
                                  int paymentComissionDec) {
        this.senderMoneyAccountId = senderMoneyAccountId;
        this.receiverMoneyAccountId = receiverMoneyAccountId;
        this.receiverRequisitesNumber = receiverRequisitesNumber;
        this.receiverMoneyAccountName = receiverMoneyAccountName;
        this.paymentNumber = paymentNumber;
        this.assignment = assignment;
        this.payedSumInt = payedSumInt;
        this.payedSumDec = payedSumDec;
        this.totalInt = totalInt;
        this.totalDec = totalDec;
        this.paymentComissionInt = paymentComissionInt;
        this.paymentComissionDec = paymentComissionDec;
    }

    public PaymentConfirmationDto() {
    }

    public int getSenderMoneyAccountId() {
        return senderMoneyAccountId;
    }

    public void setSenderMoneyAccountId(int senderMoneyAccountId) {
        this.senderMoneyAccountId = senderMoneyAccountId;
    }

    public long getReceiverRequisitesNumber() {
        return receiverRequisitesNumber;
    }

    public void setReceiverRequisitesNumber(long receiverRequisitesNumber) {
        this.receiverRequisitesNumber = receiverRequisitesNumber;
    }

    public String getReceiverMoneyAccountName() {
        return receiverMoneyAccountName;
    }

    public void setReceiverMoneyAccountName(String receiverMoneyAccountName) {
        this.receiverMoneyAccountName = receiverMoneyAccountName;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public long getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(long totalInt) {
        this.totalInt = totalInt;
    }

    public int getTotalDec() {
        return totalDec;
    }

    public void setTotalDec(int totalDec) {
        this.totalDec = totalDec;
    }

    public long getPayedSumInt() {
        return payedSumInt;
    }

    public void setPayedSumInt(long payedSumInt) {
        this.payedSumInt = payedSumInt;
    }

    public int getPayedSumDec() {
        return payedSumDec;
    }

    public void setPayedSumDec(int payedSumDec) {
        this.payedSumDec = payedSumDec;
    }

    public long getPaymentComissionInt() {
        return paymentComissionInt;
    }

    public void setPaymentComissionInt(long paymentComissionInt) {
        this.paymentComissionInt = paymentComissionInt;
    }

    public int getPaymentComissionDec() {
        return paymentComissionDec;
    }

    public void setPaymentComissionDec(int paymentComissionDec) {
        this.paymentComissionDec = paymentComissionDec;
    }

    public long getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(long paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public int getReceiverMoneyAccountId() {
        return receiverMoneyAccountId;
    }

    public void setReceiverMoneyAccountId(int receiverMoneyAccountId) {
        this.receiverMoneyAccountId = receiverMoneyAccountId;
    }
}

