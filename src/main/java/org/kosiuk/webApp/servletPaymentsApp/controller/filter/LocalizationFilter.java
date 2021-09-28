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


        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
