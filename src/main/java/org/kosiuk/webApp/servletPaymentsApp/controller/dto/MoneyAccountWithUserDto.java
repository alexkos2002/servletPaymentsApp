package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccountActStatus;

public class MoneyAccountWithUserDto {

    private int userId;
    private String username;
    private int id;
    private long number;
    private String name;
    private MoneyAccountActStatus active;
    private long sumInt;
    private int sumDec;
    private boolean canBeLocked;

    public MoneyAccountWithUserDto(int userId, String username, int id, long number, String name, MoneyAccountActStatus active,
                                   long sumInt, int sumDec, boolean canBeLocked) {
        this.userId = userId;
        this.username = username;
        this.id = id;
        this.number = number;
        this.name = name;
        this.active = active;
        this.sumInt = sumInt;
        this.sumDec = sumDec;
        this.canBeLocked = canBeLocked;
    }

    public MoneyAccountWithUserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isCanBeLocked() {
        return canBeLocked;
    }

    public void setCanBeLocked(boolean canBeLocked) {
        this.canBeLocked = canBeLocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
