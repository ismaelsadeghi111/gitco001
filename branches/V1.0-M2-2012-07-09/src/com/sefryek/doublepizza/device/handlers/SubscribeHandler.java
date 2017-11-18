package com.sefryek.doublepizza.device.handlers;

import com.sefryek.doublepizza.device.ServicesHandler;
import com.sefryek.doublepizza.device.ServicesConstant;
import com.sefryek.doublepizza.service.ISubscriberService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.model.Subscriber;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.common.util.serialize.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: vahid.s
 * Date: Mar 14, 2012
 */
public class SubscribeHandler extends ServicesHandler {
    private static Logger logger = Logger.getLogger(SubscribeHandler.class);

    public String handleRequest(HttpServletRequest request) {

        /*ServletContext servletContext = request.getSession().getServletContext();
        WebApplicationContext wappc = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        IUserService userService = (IUserService) wappc.getBean(IUserService.BEAN_NAME);
        String result = ServicesConstant.SUCCESS;
        String version = request.getParameter(ServicesConstant.PARAMETER_VERSION);
        String udId= request.getParameter(ServicesConstant.PARAMETER_UDID);
        String deviceTocken = request.getParameter(ServicesConstant.PARAMETER_DEVICVE_TOCKEN );
        String email = request.getParameter(ServicesConstant.PARAMETER_EMAIL);
        String name = request.getParameter(ServicesConstant.PARAMETER_NAME);
        String gender = request.getParameter(ServicesConstant.PARAMETER_GENDER);

        User.Title genderTitle = gender != null && gender.equals(ServicesConstant.GENDER_MALE) ? User.Title.MALE :
                User.Title.FEMALE;

        if (email != null && StringUtil.validateEmail(email)) {


            try {

                User user = new User();
                user.setEmail(email);
                user.setTitle(genderTitle);
                if (name != null) {
                    String[] concatedName = name.split(" ");
                    user.setFirstName(concatedName[0]);
                    user.setLastName(concatedName[concatedName.length - 1]);
                }
                userService.save(user);

            } catch (DAOException e) {
                if (e.getType().equals(DataIntegrityViolationException.class))
                    result = ServicesConstant.INVALID_EMAIL;
                else
                    result = ServicesConstant.UNKNOWN_ERROR;
                logger.info("Response : " + request);

            } catch (ServiceException e) {
                result = ServicesConstant.UNKNOWN_ERROR;
                logger.info("Response : " + request);
            } catch (Exception e) {
                result = ServicesConstant.INVALID_EMAIL;
            }

        } else {
            result = ServicesConstant.INVALID_EMAIL;
        }
*/
        
        ServletContext servletContext = request.getSession().getServletContext();
        WebApplicationContext wappc = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ISubscriberService subscriberService = (ISubscriberService) wappc.getBean(ISubscriberService.BEAN_NAME);

        String result = ServicesConstant.SUCCESS;

        String version = request.getParameter(ServicesConstant.PARAMETER_VERSION);
        String udId = request.getParameter(ServicesConstant.PARAMETER_UDID);
        String deviceTocken = request.getParameter(ServicesConstant.PARAMETER_DEVICVE_TOCKEN);
        String source = request.getParameter(ServicesConstant.PARAMETER_SOURCE);
        String email = request.getParameter(ServicesConstant.PARAMETER_EMAIL);
        String name = request.getParameter(ServicesConstant.PARAMETER_NAME);
        String gender = request.getParameter(ServicesConstant.PARAMETER_GENDER);
        String latitude = request.getParameter(ServicesConstant.PARAMETER_LATITUDE);
        String longitude = request.getParameter(ServicesConstant.PARAMETER_LONITUDE);

        if (email != null && StringUtil.validateEmail(email)) {
            Subscriber.Gender userGender = gender != null && gender.equals(ServicesConstant.GENDER_MALE) ? Subscriber.Gender.MALE : Subscriber.Gender.FEMALE;
            Subscriber subscriber = new Subscriber(udId, deviceTocken, source, latitude, longitude, email, name, userGender, true);

            try {
                String error = subscriber.validateData( request );
                if( error == null )
                    subscriberService.subscribe(subscriber);
                else{
                    result = ServicesConstant.VALIDATE_ERROR+";"+ error;
                }

            } catch (DAOException e) {
                if (e.getMessage().equals("Duplication subscriber"))
                    result = ServicesConstant.DUPLICATE_SUBSCRIBE;
                else
                    result = ServicesConstant.UNKNOWN_ERROR;
            }

        } else {
            result = ServicesConstant.INVALID_EMAIL;
        }

        return result;
    }
}
