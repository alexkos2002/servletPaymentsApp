package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentStatus;

public class PaymentDetailsDto {
    private long number;
    private long payedSumInt;
    private int payedSumDec;
    private long comissionInt;
    private int comissionDec;
    private String assignment;
    private String timeString;
    private PaymentStatus paymentStatus;
    private long movedSumInt;
    private int movedSumDec;
    private String receiverName;
    private String senderName;

    public PaymentDetailsDto(long number, long payedSumInt, int payedSumDec, long comissionInt, int comissionDec,
                             String assignment, String timeString, PaymentStatus paymentStatus, long movedSumInt,
                             int movedSumDec, String receiverName, String senderName) {
        this.number = number;
        this.payedSumInt = payedSumInt;
        this.payedSumDec = payedSumDec;
        this.comissionInt = comissionInt;
        this.comissionDec = comissionDec;
        this.assignment = assignment;
        this.timeString = timeString;
        this.paymentStatus = paymentStatus;
        this.movedSumInt = movedSumInt;
        this.movedSumDec = movedSumDec;
        this.receiverName = receiverName;
        this.senderName = senderName;
    }

    public PaymentDetailsDto() {
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
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

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getMovedSumInt() {
        return movedSumInt;
    }

    public void setMovedSumInt(long movedSumInt) {
        this.movedSumInt = movedSumInt;
    }

    public int getMovedSumDec() {
        return movedSumDec;
    }

    public void setMovedSumDec(int movedSumDec) {
        this.movedSumDec = movedSumDec;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    @Override
    public String toString() {
        return "PaymentDetailsDto{" +
                "number=" + number +
                ", payedSumInt=" + payedSumInt +
                ", payedSumDec=" + payedSumDec +
                ", comissionInt=" + comissionInt +
                ", comissionDec=" + comissionDec +
                ", assignment='" + assignment + '\'' +
                ", timeString='" + timeString + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", movedSumInt=" + movedSumInt +
                ", movedSumDec=" + movedSumDec +
                ", receiverName='" + receiverName + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }
}
