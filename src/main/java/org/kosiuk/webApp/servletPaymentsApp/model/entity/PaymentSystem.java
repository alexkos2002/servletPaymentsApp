package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.NoSuchElementException;

public enum PaymentSystem {
    VISA, MASTERCARD;

    public static PaymentSystem getPaymentSystemByName (String psName) {
        for (PaymentSystem curPS : PaymentSystem.values()) {
            if (curPS.name().equals(psName)) {
                return curPS;
            }
        }
        throw new NoSuchElementException();
    }
}
