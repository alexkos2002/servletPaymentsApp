package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

public class PaymentPreparationDto {
    int senderMoneyAccountId;
    long receiverRequisitesNum;
    String payedSumString;
    String assignment;

    public PaymentPreparationDto(int senderMoneyAccountId, long receiverRequisitesNum, String payedSumString, String assignment) {
        this.senderMoneyAccountId = senderMoneyAccountId;
        this.receiverRequisitesNum = receiverRequisitesNum;
        this.payedSumString = payedSumString;
        this.assignment = assignment;
    }

    public PaymentPreparationDto() {
    }

    public int getSenderMoneyAccountId() {
        return senderMoneyAccountId;
    }

    public void setSenderMoneyAccountId(int senderMoneyAccountId) {
        this.senderMoneyAccountId = senderMoneyAccountId;
    }

    public long getReceiverRequisitesNum() {
        return receiverRequisitesNum;
    }

    public void setReceiverRequisitesNum(long receiverRequisitesNum) {
        this.receiverRequisitesNum = receiverRequisitesNum;
    }

    public String getPayedSumString() {
        return payedSumString;
    }

    public void setPayedSumString(String payedSumString) {
        this.payedSumString = payedSumString;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }
}
