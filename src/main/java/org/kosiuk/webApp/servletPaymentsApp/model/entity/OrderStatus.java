package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.NoSuchElementException;

public enum OrderStatus {
    ON_CHECK, CONFIRMED, REJECTED;

    public static OrderStatus getStatusByName (String statusName) {
        for (OrderStatus curStatus : OrderStatus.values()) {
            if (curStatus.name().equals(statusName)) {
                return curStatus;
            }
        }
        throw new NoSuchElementException();
    }
}