package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

public class PaymentAndTransactionDto {

    int senderAccountId;
    int receiverAccountId;
    long payedSumInt;
    int payedSumDec;
    long comissionInt;
    int comissionDec;
    String assignment;

    public PaymentAndTransactionDto(int senderAccountId, int receiverAccountId, long payedSumInt, int payedSumDec,
                                    long comissionInt, int comissionDec, String assignment) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.payedSumInt = payedSumInt;
        this.payedSumDec = payedSumDec;
        this.comissionInt = comissionInt;
        this.comissionDec = comissionDec;
        this.assignment = assignment;
    }

    public PaymentAndTransactionDto() {
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public int getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(int receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
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

    public long getComissionInt() {
        return comissionInt;
    }

    public void setComissionInt(long comissionInt) {
        this.comissionInt = comissionInt;
    }

    public int getComissionDec() {
        return comissionDec;
    }

    public void setComissionDec(int comissionDec) {
        this.comissionDec = comissionDec;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }
}
