package org.kosiuk.webApp.servletPaymentsApp.controller.wrappers;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MyServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> headerMap;
    Logger logger = Logger.getLogger(MyServletRequestWrapper.class);

    public MyServletRequestWrapper(HttpServletRequest request){
        super(request);
        headerMap = new HashMap();
    }

    public void addHeader(String name, String value){
        headerMap.put(name, value);
    }

    @Override
    public Enumeration getHeaderNames(){
        HttpServletRequest request = (HttpServletRequest)getRequest();
        List list = new ArrayList();
        for( Enumeration e = request.getHeaderNames() ;  e.hasMoreElements() ; )
            list.add(e.nextElement().toString());
        for( Iterator i = headerMap.keySet().iterator() ; i.hasNext() ; ){
            list.add(i.next());
        }
        return Collections.enumeration(list);
    }

    @Override
    public String getHeader(String name){
        String value;
        if((value = headerMap.get(name)) != null) {
            logger.info("Custom request header was requested by the server " + name + ":" + value);
            return value.toString();
        } else {
            value = ((HttpServletRequest) getRequest()).getHeader(name);
            logger.info("Default client request header was requested by the server " + name+ ":" + value);
            return value;
        }
    }


}
