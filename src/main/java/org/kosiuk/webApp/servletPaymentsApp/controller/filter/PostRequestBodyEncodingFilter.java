package org.kosiuk.webApp.servletPaymentsApp.controller.filter;

import org.kosiuk.webApp.servletPaymentsApp.controller.wrappers.MyAnotherRequestWrapper;
import org.kosiuk.webApp.servletPaymentsApp.controller.wrappers.MyServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Encodes http post request body.
 */
public class PostRequestBodyEncodingFilter implements Filter {

    /**
     * Encodes http post request body in proper way via changing Content-Type header.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getMethod().equals("POST")) {
            //MyServletRequestWrapper httpReqWrap = new MyServletRequestWrapper(httpServletRequest);
            MyAnotherRequestWrapper httpReqWrap = new MyAnotherRequestWrapper(httpServletRequest);
            httpReqWrap.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //System.err.println(httpServletRequest.getHeader("Content-Type"));
            chain.doFilter(httpReqWrap, response);
        } else {
            chain.doFilter(httpServletRequest, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
