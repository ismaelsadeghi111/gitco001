package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.IFeedBackService;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.web.form.RegistrationForm;
import com.sefryek.doublepizza.web.form.LoginForm;
import com.sefryek.doublepizza.web.form.EmailForm;
import com.sefryek.doublepizza.web.form.FeedBackForm;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.common.config.ApplicationConfig;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class FeedBackAction extends DispatchAction {

    private IFeedBackService feedBackService;
    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        feedBackService = (IFeedBackService) context.getBean(IFeedBackService.BEAN_NAME);
    }

    public ActionForward makeFeedBack(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors warnings = new ActionErrors();
        FeedBackForm feedBackForm = (FeedBackForm)form;


//        User user = userService.findByEmail(email);
        FeedBack feedBack = new FeedBack();

        feedBack.setVersion(ApplicationConfig.versionShort);
        feedBack.setEmail(feedBackForm.getEmail());
        feedBack.setMessage(feedBackForm.getMessage());
        feedBack.setName(feedBackForm.getName());
        feedBack.setSource(FeedBack.Type.WEB);
        feedBack.setStoreNumber(Constant.FEEDBACK_STORENUMBER_VALUE);
        long creationFeedBackInMilis = Calendar.getInstance().getTimeInMillis();
        feedBack.setMilliSeconds("" + creationFeedBackInMilis);

        feedBackService.save(feedBack);
        return mapping.findForward("success");
    }

    public ActionForward logout(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().removeAttribute(Constant.USER_TRANSIENT);
        request.getAttribute(Constant.USER_TRANSIENT);
        String latestURL = request.getParameter(Constant.LATEST_USER_URL);

        if (latestURL.contains(Constant.OPERAION + "=" + Constant.REGISTERATION_OPERAION)) {
            //this state is to handle latest URLs that contains 'register operation'
            response.sendRedirect(request.getContextPath() + Constant.LOGIN_URL);

        } else {
            response.sendRedirect(request.getParameter(Constant.LATEST_USER_URL));

        }

        return null;
    }

//    public ActionForward  goToRegistrationPage(ActionMapping mapping, ActionForm form,
//                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        ActionErrors warnings = new ActionErrors();
//        EmailForm emailForm= (EmailForm)form;
//
//        String email=emailForm.getEmail();
//
//        User user = userService.findByEmail(email);
//
//        if(user==null){
//
//            return mapping.findForward("forwardToRegistrationPage");
//
//        }else{
//            warnings.add("duplicateUseremail",new ActionMessage("message.registration.duplicate.user"));
//            saveErrors(request,warnings);
//            return mapping.findForward("login");
//        }
//    }
//
//    public ActionForward register(ActionMapping mapping, ActionForm form,
//                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        ActionMessages messages = new ActionMessages();
//        ActionErrors warnings = new ActionErrors();
//        RegistrationForm userForm= (RegistrationForm)form;
//
//        String title= userForm.getTitle();
//
//        String email=userForm.getEmail();
//        String firstName=userForm.getFirstName();
//        String lastName=userForm.getLastName();
//        String password=userForm.getPassword();
//        String verifyPassword=userForm.getVerifyPassword();
//        String address1=userForm.getAddress1();
//        String address2=userForm.getAddress2();
//        String city=userForm.getCity();
//        String company=userForm.getCompany();
//        String cellPhone=userForm.getCellPhone();
//        String facebookUsername=userForm.getFacebookUsername();
//        String postalCode=userForm.getPostalCode();
//        String twitterUsername=userForm.getTwitterUsername();
//
//
//        if(password.equals(verifyPassword)){
//            User user=new User(User.Title.valueOf(title),email,firstName,lastName,password,city,address1,address2,company ,cellPhone,
//                    postalCode,facebookUsername,twitterUsername);
//
////            try{
////                userService.save(user);
////            }catch (DAOException e){
////                if(e.getType().equals(DataIntegrityViolationException.class)) {
////                    warnings.add("duplicateUseremail",new ActionMessage("message.registration.duplicate.user"));
////                }else{
////                    warnings.add("unknow",new ActionMessage("message.registration.unknow.error"));
////                }
////                saveErrors(request,warnings);
////                return mapping.findForward("register");
////            }
//
//            messages.add("successful",new ActionMessage("message.registration.is.successful"));
//            saveMessages(request,messages);
//            request.getSession().setAttribute(Constant.USER_TRANSIENT,user);
//
//            return mapping.findForward("success");
//        }else{
//            warnings.add("passNotMatch",new ActionMessage("errors.registration.pass.dose.not.match"));
//            saveErrors(request,warnings);
//            return mapping.findForward("register");
//        }
//    }
}