package org.kosiuk.webApp.servletPaymentsApp.exception;

public class NoRequisitesByNumberException extends Exception{

    public NoRequisitesByNumberException() {
    }

    public NoRequisitesByNumberException(String message) {
        super(message);
    }

    public NoRequisitesByNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequisitesByNumberException(Throwable cause) {
        super(cause);
    }

    public NoRequisitesByNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
