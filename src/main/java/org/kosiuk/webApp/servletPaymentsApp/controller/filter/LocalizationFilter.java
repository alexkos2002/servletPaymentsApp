package org.kosiuk.webApp.servletPaymentsApp.controller.filter;


import org.kosiuk.webApp.servletPaymentsApp.constants.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocalizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String langParam = httpServletRequest.getParameter("lang");

        if(langParam != null) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("lang", langParam);

            String requestURI = httpServletRequest.getRequestURI().
                    replaceFirst(httpServletRequest.getContextPath() + Path.APP_ROOT, "");

            if(requestURI.equals(Path.SHOW_ALL_USERS_PAGE_PATH)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +
                        Path.APP_ROOT + Path.SHOW_ALL_USERS_PATH);
                return;
            }

            if(requestURI.equals(Path.SHOW_ALL_ORDERS_PAGE_PATH)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +
                        Path.APP_ROOT + Path.SHOW_ALL_ORDERS_PATH);
                return;
            }

            if(requestURI.equals(Path.SHOW_ALL_MONEY_ACCOUNTS_PAGE_PATH)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +
                        Path.APP_ROOT + Path.SHOW_ALL_MONEY_ACCOUNTS_PATH);
                return;
            }

            if(requestURI.equals(Path.SHOW_ALL_USERS_CREDIT_CARDS_PAGE_PATH)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +
                        Path.APP_ROOT + Path.SHOW_ALL_USERS_CREDIT_CARDS_PATH);
                return;
            }


        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
