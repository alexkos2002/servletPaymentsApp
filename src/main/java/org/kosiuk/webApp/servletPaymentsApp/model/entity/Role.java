package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.NoSuchElementException;

public enum Role {
    USER, ADMIN;

    public static Role getRoleById(int id) {
        for (Role curRole : Role.values()) {
            if (curRole.ordinal() == id) {
                return curRole;
            }
        }
        throw new NoSuchElementException();
    }


}
