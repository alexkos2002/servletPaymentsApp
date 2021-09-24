package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.Objects;

public class Transaction {

    private long movedSumInt;
    private int movedSumDec;
    private int receiverMoneyAccountId;
    private long paymentNumber;
    private int senderMoneyAccountId;

    public Transaction(long movedSumInt, int movedSumDec, int receiverMoneyAccountId,
                       long paymentNumber, int senderMoneyAccountId) {
        this.movedSumInt = movedSumInt;
        this.movedSumDec = movedSumDec;
        this.receiverMoneyAccountId = receiverMoneyAccountId;
        this.paymentNumber = paymentNumber;
        this.senderMoneyAccountId = senderMoneyAccountId;
    }

    public Transaction() {
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

    public int getReceiverMoneyAccountId() {
        return receiverMoneyAccountId;
    }

    public void setReceiverMoneyAccountId(int receiverMoneyAccountId) {
        this.receiverMoneyAccountId = receiverMoneyAccountId;
    }

    public long getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(long paymentNumber) {
        this.paymentNumber = paymentNumber;
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
        Transaction guest = (Transaction) o;
        return movedSumInt == guest.getMovedSumInt() &&
                movedSumDec == guest.getMovedSumDec() &&
                receiverMoneyAccountId == guest.getReceiverMoneyAccountId() &&
                paymentNumber == guest.getPaymentNumber() &&
                senderMoneyAccountId == guest.getSenderMoneyAccountId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(movedSumInt, movedSumDec, receiverMoneyAccountId, paymentNumber, senderMoneyAccountId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "movedSumInt=" + movedSumInt +
                ", movedSumDec=" + movedSumDec +
                ", receiverMMoneyAccountId=" + receiverMoneyAccountId +
                ", paymentNumber=" + paymentNumber +
                ", senderMoneyAccountId=" + senderMoneyAccountId +
                '}';
    }
}
