package com.sefryek.common.util;

/**
 * Created by Nima Hashemi on 4/20/2014.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServiceFinder {

    private static ApplicationContext ctx = null;

    public static Object getBean(ServletRequest request, String beanName) {
        if (ctx == null) {
            if (!(request instanceof HttpServletRequest)) {
                throw new IllegalArgumentException(
                        "Can only process HttpServletRequest");
            }
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            ctx = getContext(httpRequest);
        }

        Object obj = new Object();
        return obj;

    }

    public static ApplicationContext getContext(HttpServletRequest httpRequest) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(
                httpRequest.getSession().getServletContext());
    }

    public static Object findBean(ServletContext servletContext, String beanName) {

        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Object o = appContext.getBean(beanName);

        return o;

    }

}
