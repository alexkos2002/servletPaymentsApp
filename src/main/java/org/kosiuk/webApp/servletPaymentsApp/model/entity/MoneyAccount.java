package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.Objects;

public class MoneyAccount {

    private int id;
    private long number;
    private String name;
    private MoneyAccountActStatus active;
    private long sumInt;
    private int sumDec;
    private long curSumAvailableInt;
    private int curSumAvailableDec;
    private boolean canBeLocked;

    public MoneyAccount(long number, String name, MoneyAccountActStatus active, long sumInt,
                        int sumDec, long curSumAvailableInt, int curSumAvailableDec) {
        this.number = number;
        this.name = name;
        this.active = active;
        this.sumInt = sumInt;
        this.sumDec = sumDec;
        this.curSumAvailableInt = curSumAvailableInt;
        this.curSumAvailableDec = curSumAvailableDec;
    }

    public MoneyAccount() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoneyAccountActStatus getActive() {
        return active;
    }

    public void setActive(MoneyAccountActStatus active) {
        this.active = active;
    }

    public long getSumInt() {
        return sumInt;
    }

    public void setSumInt(long sumInt) {
        this.sumInt = sumInt;
    }

    public int getSumDec() {
        return sumDec;
    }

    public void setSumDec(int sumDec) {
        this.sumDec = sumDec;
    }

    public long getCurSumAvailableInt() {
        return curSumAvailableInt;
    }

    public void setCurSumAvailableInt(long curSumAvailableInt) {
        this.curSumAvailableInt = curSumAvailableInt;
    }

    public int getCurSumAvailableDec() {
        return curSumAvailableDec;
    }

    public void setCurSumAvailableDec(int curSumAvailableDec) {
        this.curSumAvailableDec = curSumAvailableDec;
    }

    public boolean isCanBeLocked() {
        return canBeLocked;
    }

    public void setCanBeLocked(boolean canBeLocked) {
        this.canBeLocked = canBeLocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MoneyAccount guest = (MoneyAccount) o;
        return id == guest.getId() &&
                number == guest.getNumber() &&
                active == guest.getActive() &&
                sumInt == guest.getSumInt() &&
                sumDec == guest.getSumDec() &&
                name.equals(guest.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, active, sumInt, sumDec, canBeLocked);
    }

    @Override
    public String toString() {
        return "MoneyAccount{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", sumInt=" + sumInt +
                ", sumDec=" + sumDec +
                ", canBeLocked=" + canBeLocked +
                '}';
    }
}
