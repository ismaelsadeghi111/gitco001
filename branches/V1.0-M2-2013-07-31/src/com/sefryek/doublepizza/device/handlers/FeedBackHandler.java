package com.sefryek.doublepizza.device.handlers;

import com.sefryek.doublepizza.device.ServicesHandler;
import com.sefryek.doublepizza.device.ServicesConstant;
import com.sefryek.doublepizza.service.implementation.FeedBackServiceImpl;
import com.sefryek.doublepizza.service.IFeedBackService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.model.FeedBack;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.common.util.serialize.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: vahid.s
 * Date: Mar 13, 2012
 */
public class FeedBackHandler extends ServicesHandler {
    private static Logger logger = Logger.getLogger(FeedBackHandler.class);

    public String handleRequest(HttpServletRequest request) {

        ServletContext servletContext = request.getSession().getServletContext();
        WebApplicationContext wappc = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        IFeedBackService  feedBackService = (IFeedBackService) wappc.getBean(IFeedBackService.BEAN_NAME);

        String result = ServicesConstant.SUCCESS;
        String version = request.getParameter(ServicesConstant.PARAMETER_VERSION );
        String name = request.getParameter(ServicesConstant.PARAMETER_NAME);
        String email = request.getParameter(ServicesConstant.PARAMETER_EMAIL);
        String reviewNote = request.getParameter(ServicesConstant.PARAMETER_REVIEW_NOTE);
        String storeId = request.getParameter(ServicesConstant.PARAMETER_ID);
        String source = request.getParameter(ServicesConstant.PARAMETER_SOURCE);
        long creationFeedBackInMilis = Calendar.getInstance().getTimeInMillis();
        FeedBack.Type sourceType =
                source.equals(ServicesConstant.FEEDBACK_SENDER_iPHONE_SOURCE) ? FeedBack.Type.iPhone : FeedBack.Type.Others;

        if ( email!= null &&  StringUtil.validateEmail(email)) {
            FeedBack feedBack =
                    new FeedBack(version, name, email, reviewNote, sourceType, storeId, "" + creationFeedBackInMilis);

            try {
                String error = feedBack.validateData( request );
                if( error == null )
                    feedBackService.save(feedBack);
                else
                    result = ServicesConstant.VALIDATE_ERROR + ";"+ error ;

            } catch (DAOException e) {
                result = ServicesConstant.UNKNOWN_ERROR;
                logger.info("Response " + ServicesConstant.METHOD_SET_FEEDBACK);

            } catch (ServiceException e) {
                result = ServicesConstant.UNKNOWN_ERROR;
                logger.info("Response " + ServicesConstant.METHOD_SET_FEEDBACK);

            }
        } else {
            result = ServicesConstant.INVALID_EMAIL;

        }

        return result;
    }
}
