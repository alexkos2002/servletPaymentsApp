package org.kosiuk.webApp.servletPaymentsApp.controller.command.basic;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}

