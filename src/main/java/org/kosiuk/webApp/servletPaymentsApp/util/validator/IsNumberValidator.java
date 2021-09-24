package org.kosiuk.webApp.servletPaymentsApp.util.validator;

import org.kosiuk.webApp.servletPaymentsApp.constants.Regex;

public class IsNumberValidator implements Validator<String>{

    private final String message;

    public IsNumberValidator(String message) {
        this.message = message;
    }

    @Override
    public Result validate(String numberString) {
        if(numberString.matches(Regex.NUMBER_REGEX)) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}
