package org.kosiuk.webApp.servletPaymentsApp.controller.command.auth;

import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private final UserService userService;
    private ResourceBundle rb;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            return Path.LOGIN_JSP_PATH;
        }

        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("loginMessage", rb.getString("verification.user.auth.invUsernameAndPass"));
            return Path.LOGIN_JSP_PATH;
        }

        User user = userOptional.get();

        if (!user.isActive()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("loginMessage", rb.getString("verification.user.auth.banned"));
            return Path.LOGIN_JSP_PATH;
        }

        if (user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("authUser", user);
            return Path.REDIRECT;
        } else {
            request.setAttribute("loginMessage", "Invalid username and password.");

            return Path.LOGIN_JSP_PATH;
        }
    }
}
