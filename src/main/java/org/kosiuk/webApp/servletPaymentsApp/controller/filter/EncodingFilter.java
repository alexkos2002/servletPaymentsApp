package org.kosiuk.webApp.servletPaymentsApp.controller.filter;

import org.kosiuk.webApp.servletPaymentsApp.controller.wrappers.MyServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Set encoding of server responses
 */
public class EncodingFilter implements Filter {
    /**
     * Set response content type and character encoding of get response and request.
     * Also alter content-type of POST-request to pass POST-request body in correct encoding.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}