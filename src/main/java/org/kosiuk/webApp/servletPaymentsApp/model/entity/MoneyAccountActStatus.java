package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.NoSuchElementException;

public enum MoneyAccountActStatus {
    ACTIVE, BLOCKED, UNLOCK_REQUESTED;

    public static MoneyAccountActStatus getActStatusByName (String actStatusName) {
        for (MoneyAccountActStatus curActStatus : MoneyAccountActStatus.values()) {
            if (curActStatus.name().equals(actStatusName)) {
                return curActStatus;
            }
        }
        throw new NoSuchElementException();
    }
}
