package org.kosiuk.webApp.servletPaymentsApp.controller.command.auth;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserRegistrationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.exception.UsernameNotUniqueException;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {

    private final UserService userService;
    private ResourceBundle rb;
    Logger logger = Logger.getLogger(RegistrationCommand.class);

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

       rb = ResourceBundle.getBundle("i18n.messages",
                SessionLocalizationUtil.getLocaleFromSession(request));

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserRegistrationDto userRegDto = new UserRegistrationDto(username, email, password);

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userRegDto);

        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("userRegDto", userRegDto);
            request.setAttribute("errors", validationErrorsMap);
            return Path.REGISTRATION_JSP_PATH;
        }

        try {
            userService.registerUser(userRegDto);
        } catch (UsernameNotUniqueException e) {
            logger.warn(ExceptionMessages.NOT_UNIQUE_USERNAME);
            request.setAttribute("userRegDto", userRegDto);
            request.setAttribute("usernameNotUniqueError", rb.getString("verification.user.username.duplicate"));
            return Path.REGISTRATION_JSP_PATH;
        }

        return Path.LOGIN_JSP_PATH;
    }

    private Map<String, String[]> getValidationErrorsMap(UserRegistrationDto userRegDto) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> usernameValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.username.notBlank")),
                new SizeValidator(2, 30, rb.getString("validation.user.username.size"))
        );
        CompositeValidator<String> emailValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.email.notBlank")),
                new EmailAddressValidator(rb.getString("validation.user.email.invalid"))
        );
        CompositeValidator<String> passwordValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.password.notBlank")),
                new SizeValidator(4, 16, rb.getString("validation.user.password.size"))
        );

        Result result = usernameValidator.validate(userRegDto.getUsername());
        if (!result.isValid()) {
            validationErrorsMap.put("usernameErrors", result.getMessage().split("\n"));
        }
        result = emailValidator.validate(userRegDto.getEmail());
        if (!result.isValid()) {
            validationErrorsMap.put("emailErrors", result.getMessage().split("\n"));
        }
        result = passwordValidator.validate(userRegDto.getPassword());
        if (!result.isValid()) {
            validationErrorsMap.put("passwordErrors", result.getMessage().split("\n"));
        }

        return validationErrorsMap;
    }
}
