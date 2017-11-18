package com.sefryek.doublepizza.web.filter;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 14, 2012
 * Time: 2:09:22 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

///this class is a gateway to block users have invalid session and want access to some pages    
public class SessionControlFilter implements Filter{
    private FilterConfig config = null;
    private String redirectTo;
    private String requestPattern;
    private static Logger logger;

    private Boolean sessionIsValid(HttpSession session){
        return session != null && !session.isNew();
    }

    private String getUrl(HttpServletRequest req) {
        String reqUrl = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        if (queryString != null) {
            reqUrl += "?"+queryString;
        }
        return reqUrl;
    }

    private Boolean requestIsNotRequiredValidSession(HttpServletRequest req){
//        String url = getUrl(req);
//        Pattern pattern = Pattern.compile(requestPattern);
//        Matcher matcher = pattern.matcher(url);
//        Boolean validate = matcher.find();
//        return validate;
        return !req.getParameterNames().hasMoreElements();
    }
    

    public void init(javax.servlet.FilterConfig filterConfig)
            throws javax.servlet.ServletException{
        this.config = filterConfig;
        redirectTo = config.getInitParameter("redirectTo");
        logger = Logger.getLogger(SessionControlFilter.class);
//        requestPattern = config.getInitParameter("requestPattern");
    }

    private void redirect(HttpServletRequest req,ServletResponse servletResponse)
            throws IOException, ServletException{
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.addCookie(new Cookie("homeAlert", "fromInvalidRequest"));
        String isAjax = req.getHeader("isAjax");
        if (isAjax == null){
            res.sendRedirect(redirectTo);
//            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SESSION_TIMED_OUT");
        }
        else{
            res.sendError(512, "SESSION_TIMED_OUT");
        }
    }

    public void doFilter(javax.servlet.ServletRequest servletRequest,
                  javax.servlet.ServletResponse servletResponse, javax.servlet.FilterChain filterChain)
            throws java.io.IOException, javax.servlet.ServletException{

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        logger.debug("web request>>\t " + getUrl(req));
        HttpSession session = req.getSession(false);
        if (requestIsNotRequiredValidSession(req) || sessionIsValid(session)){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            redirect(req, servletResponse);
        }
    }

    public void destroy(){
        this.config = null;
    }
}
