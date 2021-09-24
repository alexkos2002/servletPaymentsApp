package org.kosiuk.webApp.servletPaymentsApp.controller.command.auth;

import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;

import javax.servlet.http.HttpServletRequest;

public class RegistrationFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return Path.REGISTRATION_JSP_PATH;
    }
}
