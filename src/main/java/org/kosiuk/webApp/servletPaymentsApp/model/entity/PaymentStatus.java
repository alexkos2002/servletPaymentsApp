package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.NoSuchElementException;

public enum PaymentStatus {
    PREPARED, SENT;

    public static PaymentStatus getStatusByName (String statusName) {
        for (PaymentStatus curStatus : PaymentStatus.values()) {
            if (curStatus.name().equals(statusName)) {
                return curStatus;
            }
        }
        throw new NoSuchElementException();
    }

}
