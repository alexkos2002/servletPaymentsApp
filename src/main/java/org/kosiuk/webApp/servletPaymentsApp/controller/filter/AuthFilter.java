package org.kosiuk.webApp.servletPaymentsApp.controller.filter;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AuthFilter implements Filter {

    Logger logger = Logger.getLogger(AuthFilter.class);

    private final List<String> adminPaths = Arrays.asList(
            Path.MAIN_PAGE_PATH,
            "/",
            Path.LOGOUT_PATH,
            Path.SHOW_ALL_USERS_PATH,
            Path.CHANGE_ROLES_PATH,
            Path.BAN_USER_PATH,
            Path.UNBAN_USER_PATH,
            Path.SHOW_ALL_USERS_PAGE_PATH,
            Path.DELETE_USER_PATH,
            Path.SHOW_PERSONAL_ROOM_PATH,
            Path.SHOW_ALL_ORDERS_PATH,
            Path.SHOW_ALL_ORDERS_PAGE_PATH,
            Path.NEW_ORDER_PATH,
            Path.ORDER_REJECTION_FORM_PATH,
            Path.REJECT_ORDER_PATH,
            Path.ORDER_CONFIRMATION_FORM_PATH,
            Path.CONFIRM_ORDER_PATH,
            Path.CANCEL_ORDER_CONFIRMATION_PATH,
            Path.SHOW_ALL_USERS_CREDIT_CARDS_PATH,
            Path.SHOW_ALL_USERS_CREDIT_CARDS_PAGE_PATH,
            Path.PUT_MONEY_FORM_PATH,
            Path.PUT_MONEY_PATH,
            Path.SHOW_ALL_MONEY_ACCOUNTS_PATH,
            Path.SHOW_ALL_MONEY_ACCOUNTS_PAGE_PATH,
            Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PATH,
            Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PAGE_PATH,
            Path.BLOCK_MONEY_ACCOUNT_PATH,
            Path.ASK_TO_UNLOCK_MONEY_ACCOUNT_PATH,
            Path.UNLOCK_MONEY_ACCOUNT_PATH,
            Path.SHOW_MONEY_ACCOUNT_PATH,
            Path.DELETE_MONEY_ACCOUNT_PATH,
            Path.SHOW_TO_CARD_PAYMENT_FORM_PATH,
            Path.PREPARE_TO_CARD_PAYMENT_PATH,
            Path.CANCEL_PAYMENT_PATH,
            Path.SEND_PAYMENT_PATH,
            Path.SHOW_TO_MONEY_ACCOUNT_PAYMENT_FORM_PATH,
            Path.PREPARE_TO_MONEY_ACCOUNT_PAYMENT_PATH,
            Path.SHOW_ALL_PAYMENTS_PATH,
            Path.SHOW_ALL_PAYMENTS_PAGE_PATH,
            Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PATH,
            Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PAGE_PATH,
            Path.SHOW_PAYMENT_DETAILS_PATH);

    /**
     * Paths that can visit user with USER authority
     */
    private final List<String> userPaths = Arrays.asList(
            Path.MAIN_PAGE_PATH,
            "/",
            Path.LOGOUT_PATH,
            Path.SHOW_PERSONAL_ROOM_PATH,
            Path.NEW_ORDER_PATH,
            Path.SHOW_ALL_ORDERS_PATH,
            Path.SHOW_ALL_USERS_CREDIT_CARDS_PATH,
            Path.SHOW_ALL_USERS_CREDIT_CARDS_PAGE_PATH,
            Path.PUT_MONEY_FORM_PATH,
            Path.PUT_MONEY_PATH,
            Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PATH,
            Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PAGE_PATH,
            Path.BLOCK_MONEY_ACCOUNT_PATH,
            Path.ASK_TO_UNLOCK_MONEY_ACCOUNT_PATH,
            Path.DELETE_MONEY_ACCOUNT_PATH,
            Path.SHOW_MONEY_ACCOUNT_PATH,
            Path.SHOW_TO_CARD_PAYMENT_FORM_PATH,
            Path.PREPARE_TO_CARD_PAYMENT_PATH,
            Path.CANCEL_PAYMENT_PATH,
            Path.SEND_PAYMENT_PATH,
            Path.SHOW_TO_MONEY_ACCOUNT_PAYMENT_FORM_PATH,
            Path.PREPARE_TO_MONEY_ACCOUNT_PAYMENT_PATH,
            Path.SHOW_ALL_PAYMENTS_PATH,
            Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PATH,
            Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PAGE_PATH,
            Path.SHOW_PAYMENT_DETAILS_PATH);


    /**
     * Paths that can visit unauthenticated user
     *
     */
    private final List<String> defaultPaths = Arrays.asList(
            Path.MAIN_PAGE_PATH,
            "/",
            Path.LOGIN_FORM_PATH,
            Path.LOGIN_PATH,
            Path.REGISTRATION_FORM_PATH,
            Path.REGISTRATION_PATH,
            Path.LOGIN_PATH);
    /**
     * Map with paths that user with various authorities can visit
     */
    private final Map<Role, List<String>> rolePaths = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        rolePaths.put(Role.USER, userPaths);
        rolePaths.put(Role.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI().replaceFirst(request.getContextPath() + Path.APP_ROOT, "");
        logger.info("Authorization checking for path: " + requestURI);

        User user = (User) session.getAttribute("authUser");

        if (user == null) {
            logger.info("Non-authorized user visits " + requestURI);
            if (defaultPaths.contains(requestURI)) {
                logger.info("Enough rights.");
                chain.doFilter(request, response);
            } else {
                logger.info("User with roles " + user.getRoles() + "visits " + requestURI);
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() +
                        "/login");
            }
            return;
        }

        List<String> paths = user.getRoles()
                .stream()
                .flatMap(role -> rolePaths.get(role).stream())
                .distinct()
                .collect(Collectors.toList());

        if (paths.contains(requestURI)) {
            logger.info("Enough rights.");
            chain.doFilter(request, response);
        } else {
            logger.warn("Not enough rights!");
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/error/403.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
