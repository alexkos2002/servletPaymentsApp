package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard {
    private int id;
    private long number;
    private int cvv;
    private String expireDateString;
    private PaymentSystem paymentSystem;
    private long availableSumInt;
    private int availableSumDec;
    private int accountId;
    private int moneyAccountId;

    public CreditCard(int id, long number, int cvv, String expireDateString, PaymentSystem paymentSystem,
                      long availableSumInt, int availableSumDec, int accountId, int moneyAccountId) {
        this.id = id;
        this.number = number;
        this.cvv = cvv;
        this.expireDateString = expireDateString;
        this.paymentSystem = paymentSystem;
        this.availableSumInt = availableSumInt;
        this.availableSumDec = availableSumDec;
        this.accountId = accountId;
        this.moneyAccountId = moneyAccountId;
    }

    public CreditCard() {
    }

    public static CreditCard.Builder builder() {
        return new CreditCard.Builder();
    }

    public static class Builder {
        private int id;
        private long number;
        private int cvv;
        private String expireDateString;
        private PaymentSystem paymentSystem;
        private long availableSumInt;
        private int availableSumDec;
        private int accountId;
        private int moneyAccountId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder number(long number) {
            this.number = number;
            return this;
        }

        public Builder cvv(int cvv) {
            this.cvv = cvv;
            return this;
        }

        public Builder expireDateString(String expireDateString) {
            this.expireDateString = expireDateString;
            return this;
        }

        public Builder paymentSystem(PaymentSystem paymentSystem) {
            this.paymentSystem = paymentSystem;
            return this;
        }

        public Builder availableSumInt(long availableSumInt) {
            this.availableSumInt = availableSumInt;
            return this;
        }

        public Builder availableSumDec(int availableSumDec) {
            this.availableSumDec = availableSumDec;
            return this;
        }

        public Builder accountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder moneyAccountId(int moneyAccountId) {
            this.moneyAccountId = moneyAccountId;
            return this;
        }

        public CreditCard build() {
            CreditCard creditCard = new CreditCard();
            creditCard.setId(id);
            creditCard.setNumber(number);
            creditCard.setCvv(cvv);
            creditCard.setExpireDateString(expireDateString);
            creditCard.setPaymentSystem(paymentSystem);
            creditCard.setAvailableSumInt(availableSumInt);
            creditCard.setAvailableSumDec(availableSumDec);
            creditCard.setAccountId(accountId);
            creditCard.setMoneyAccountId(moneyAccountId);
            return creditCard;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getAvailableSumInt() {
        return availableSumInt;
    }

    public void setAvailableSumInt(long availableSumInt) {
        this.availableSumInt = availableSumInt;
    }

    public int getAvailableSumDec() {
        return availableSumDec;
    }

    public void setAvailableSumDec(int availableSumDec) {
        this.availableSumDec = availableSumDec;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMoneyAccountId() {
        return moneyAccountId;
    }

    public void setMoneyAccountId(int moneyAccountId) {
        this.moneyAccountId = moneyAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CreditCard guest = (CreditCard) o;
        return id == guest.getId() &&
                number == guest.getNumber() &&
                cvv == guest.getCvv() &&
                availableSumInt == guest.getAvailableSumInt() &&
                availableSumDec == guest.getAvailableSumDec() &&
                accountId == guest.getAccountId() &&
                moneyAccountId == guest.getMoneyAccountId() &&
                expireDateString.equals(guest.getExpireDateString())
                && paymentSystem == guest.getPaymentSystem();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, cvv, expireDateString, paymentSystem, availableSumInt, availableSumDec, accountId, moneyAccountId);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", number=" + number +
                ", cvv=" + cvv +
                ", expireDate=" + expireDateString +
                ", paymentSystem=" + paymentSystem +
                ", sumInt=" + availableSumInt +
                ", sumDec=" + availableSumDec +
                ", accountId=" + accountId +
                ", moneyAccountId=" + moneyAccountId +
                '}';
    }
}
