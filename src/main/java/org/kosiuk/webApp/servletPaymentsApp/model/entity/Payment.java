package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.Objects;

public class Payment {
    private long number;
    private long payedSumInt;
    private int payedSumDec;
    private long comissionInt;
    private int comissionDec;
    private String assignment;
    private String timeString;
    private PaymentStatus status;
    private int senderMoneyAccountId;

    public Payment(long number, long payedSumInt, int payedSumDec, long comissionInt, int comissionDec,
                   String assignment, PaymentStatus status, int senderMoneyAccountId) {
        this.number = number;
        this.payedSumInt = payedSumInt;
        this.payedSumDec = payedSumDec;
        this.comissionInt = comissionInt;
        this.comissionDec = comissionDec;
        this.assignment = assignment;
        this.status = status;
        this.senderMoneyAccountId = senderMoneyAccountId;
    }

    public Payment() {
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

    public void setPayedSumInt(long payedSumint) {
        this.payedSumInt = payedSumint;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public int getSenderMoneyAccountId() {
        return senderMoneyAccountId;
    }

    public void setSenderMoneyAccountId(int senderMoneyAccountId) {
        this.senderMoneyAccountId = senderMoneyAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return number == payment.getNumber() &&
                payedSumInt == payment.getPayedSumInt()&&
                payedSumDec == payment.getPayedSumDec() &&
                comissionInt == payment.getComissionInt() &&
                comissionDec == payment.getComissionDec() &&
                senderMoneyAccountId == payment.getSenderMoneyAccountId() &&
                (assignment != null && assignment.equals(payment.getAssignment())) &&
                timeString.equals(payment.getTimeString()) &&
                status == payment.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, payedSumInt, payedSumDec, comissionInt, comissionDec, assignment, timeString, status, senderMoneyAccountId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "number=" + number +
                ", payedSumint=" + payedSumInt +
                ", payedSumDec=" + payedSumDec +
                ", comissionInt=" + comissionInt +
                ", comissionDec=" + comissionDec +
                ", assignment='" + assignment + '\'' +
                ", timeString='" + timeString + '\'' +
                ", status=" + status.name() +
                ", senderMoneyAccountId=" + senderMoneyAccountId +
                '}';
    }
}
