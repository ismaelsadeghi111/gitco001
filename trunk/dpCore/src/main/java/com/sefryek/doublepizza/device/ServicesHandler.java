package com.sefryek.doublepizza.device;

import com.sefryek.doublepizza.device.handlers.BranchesHandler;
import com.sefryek.doublepizza.device.handlers.FeedBackHandler;
import com.sefryek.doublepizza.device.handlers.ImageDataHandler;
import com.sefryek.doublepizza.device.handlers.SubscribeHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Mar 12, 2012
 * Time: 10:30:32 AM
 */
public abstract class ServicesHandler {
    private final static Logger logger = Logger.getLogger(ServicesHandler.class);


    public static ServicesHandler getHandler(HttpServletRequest request, HttpServletResponse response) {

        String serviceName = request.getParameter(ServicesConstant.PARAMETER_METHOD );
        logger.debug("Method name : " + serviceName);

        ServicesHandler handler = null;
        if( serviceName != null ){
            if( serviceName.equals( ServicesConstant.METHOD_GET_BRANCHES )){
                handler = new BranchesHandler();

            } else if( serviceName.equals(ServicesConstant.METHOD_SET_FEEDBACK)){
                handler = new FeedBackHandler();

            } else if ( serviceName.equals( ServicesConstant.METHOD_SUBSCRIBE )){
                handler = new SubscribeHandler();

            } else if ( serviceName.equals(ServicesConstant.METHOD_IMAGE_DATE )){
                handler = new ImageDataHandler( response );
            }

        }

        return handler;
    }

    public abstract String handleRequest(HttpServletRequest request);
}
