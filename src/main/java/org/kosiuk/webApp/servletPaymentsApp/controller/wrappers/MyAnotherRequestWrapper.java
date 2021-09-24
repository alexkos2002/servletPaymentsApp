package org.kosiuk.webApp.servletPaymentsApp.controller.wrappers;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MyAnotherRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> customHeaderMap;
    Logger logger = Logger.getLogger(MyServletRequestWrapper.class);

    public MyAnotherRequestWrapper(HttpServletRequest request) {
        super(request);
        customHeaderMap = new HashMap<>();
    }

    public void addHeader(String name, String value){
        customHeaderMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if ("content-type".equalsIgnoreCase(name)){
            logger.info("Sets Header Content-Type as application/x-www-form-urlencoded; charset=UTF-8");
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }
        return header;
    }

    @Override
    public Enumeration getHeaders(String name) {
        List values = Collections.list(super.getHeaders(name));
        if(name.equalsIgnoreCase("content-type")) {
            values.add("application/x-www-form-urlencoded; charset=UTF-8");
        }
        return Collections.enumeration(values);
    }

    @Override
    public Enumeration getHeaderNames() {
        List names = Collections.list(super.getHeaderNames());
        names.addAll(Collections.list(super.getParameterNames()));
        String url = new String(((HttpServletRequest)super.getRequest()).getRequestURL());
        if(!names.contains("content-type"))
            names.add("content-type");
        return Collections.enumeration(names);
    }


    @Override
    public String getParameter(String name) {
        String paramValue = super.getParameter(name);
        if (paramValue == null) {
            paramValue = customHeaderMap.get(name);
        }
        return paramValue;
    }
}
