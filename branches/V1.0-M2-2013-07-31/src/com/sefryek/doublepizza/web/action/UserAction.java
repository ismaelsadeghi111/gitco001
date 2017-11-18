package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.dao.DataIntegrityViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.web.form.RegistrationForm;
import com.sefryek.doublepizza.web.form.LoginForm;
import com.sefryek.doublepizza.web.form.EmailForm;
import com.sefryek.doublepizza.web.form.DeliveryAddressForm;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class UserAction extends DispatchAction {

    private IUserService userService;
    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
    }

    private DeliveryAddressForm userToDeliveryAddressForm(User user){
        
        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        deliveryAddressForm.setTitle(user.getTitle().toString());
        deliveryAddressForm.setEmail(user.getEmail());
        deliveryAddressForm.setLastName(user.getLastName());
        deliveryAddressForm.setCity(user.getCity());
        deliveryAddressForm.setCompany(user.getCompany());
        String phone = user.getCellPhone();
        if (phone != null){
            deliveryAddressForm.setCellPhone1(phone.substring(0, 3));
            deliveryAddressForm.setCellPhone2(phone.substring(3, 6));
            deliveryAddressForm.setCellPhone3(phone.substring(6, 10));
        }
        deliveryAddressForm.setExt(user.getExt());
        deliveryAddressForm.setStreetNo(user.getStreetNo());
        deliveryAddressForm.setSuiteApt(user.getSuiteApt());
        deliveryAddressForm.setBuilding(user.getBuilding());
        deliveryAddressForm.setDoorCode(user.getDoorCode());
        String postalCode = user.getPostalCode();
        deliveryAddressForm.setPostalCode1(postalCode.substring(0, 3));
        deliveryAddressForm.setPostalCode2(postalCode.substring(4, 7));
        deliveryAddressForm.setStreet(user.getStreetName());
        return deliveryAddressForm;
    }

    public ActionForward login(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors warnings = new ActionErrors();
        LoginForm loginForm = (LoginForm) form;

        ActionErrors errors = loginForm.validate(mapping, request);
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("login");
        }


        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        User user = userService.findByEmail(email);
//        user.setRegistered(true);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute(Constant.USER_TRANSIENT, user);

                Boolean isFromCheck = Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE));

                if (isFromCheck) {
                    user.setRegistered(true);
                    (request.getSession()).setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);
                    request.getSession().setAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT, new Boolean(true));
//                    return mapping.findForward("paymentInfo");
                    
                    DeliveryAddressForm deliveryAddressForm = userToDeliveryAddressForm(user);
                    
                    request.setAttribute("deliveryAddressForm", deliveryAddressForm);
                    return mapping.findForward("deliveryAddress");

                } else {
                    request.getSession(true).setAttribute(Constant.REGISTER_OR_LOGIN, "login");
                    return mapping.findForward("redirectRes");

                }


            } else {
                warnings.add("passwordNotFound", new ActionMessage("message.login.invalid.password"));
                saveErrors(request, warnings);
                return mapping.findForward("login");
            }
        } else {
            warnings.add("userEmailNotFound", new ActionMessage("message.login.invalid.usermail"));
            saveErrors(request, warnings);
            return mapping.findForward("login");
        }
    }

    public ActionForward logout(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().removeAttribute(Constant.USER_TRANSIENT);
        request.getSession().removeAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT);


        response.sendRedirect("frontend.do");

//        if (latestURL.contains(Constant.OPERAION + "=" + Constant.REGISTERATION_OPERAION)) {
//
//            //this state is to handle latest URLs that contains 'register operation'
//            response.sendRedirect(request.getContextPath() + Constant.LOGIN_URL);
//        } else {
//                response.sendRedirect(request.getParameter(Constant.LATEST_USER_URL));
//        }
        return null;
    }

    public ActionForward goToRegistrationPage(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors warnings = new ActionErrors();
        EmailForm emailForm = (EmailForm) form;

        String email = emailForm.getEmail();

        User user = userService.findByEmail(email);

        if (user == null) {

            return mapping.findForward("forwardToRegistrationPage");

        } else {
            warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));
            saveErrors(request, warnings);
            return mapping.findForward("login");
        }
    }

    public ActionForward register(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionMessages messages = new ActionMessages();
        ActionErrors warnings = new ActionErrors();
        RegistrationForm userForm = (RegistrationForm) form;

        ActionErrors errors = userForm.validate(mapping, request);
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("register");
        }


        String title = userForm.getTitle();

        String email = userForm.getEmail();
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();
        String password = userForm.getPassword();
        String verifyPassword = userForm.getVerifyPassword();
        String city = userForm.getCity();
        String postalCode = userForm.getPostalCode1() + " " + userForm.getPostalCode2();
        String building = userForm.getBuilding();
        String streetNo = userForm.getStreetNo();
        String streetName = userForm.getStreet();
        String suiteApt = userForm.getSuiteApt();
        String doorCode = userForm.getDoorCode();
        String company = userForm.getCompany();
        String cellPhone1 = userForm.getCellPhone1();
        String cellPhone2 = userForm.getCellPhone2();
        String cellPhone3 = userForm.getCellPhone3();
        String ext = userForm.getExt();
        String facebookUsername = userForm.getFacebookUsername();
        String twitterUsername = userForm.getTwitterUsername();

        if (password.equals(verifyPassword)) {
            User user = new User(User.Title.valueOf(title), email, firstName, lastName, password, company, city,
                    postalCode, streetNo, streetName,suiteApt, building, doorCode, facebookUsername, twitterUsername,
                    cellPhone1 + cellPhone2 + cellPhone3, ext);

            try {
                userService.save(user);

            } catch (DataIntegrityViolationException e) {
                warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));

            } catch (DAOException e) {
                if (e.getType().equals(DataIntegrityViolationException.class)) {
                    warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));

                } else {
                    warnings.add("unknow", new ActionMessage("message.registration.unknow.error"));
                }

                saveErrors(request, warnings);
                return mapping.findForward("register");

            }

            messages.add("successful", new ActionMessage("message.registration.is.successful"));
            saveMessages(request, messages);
            request.getSession().setAttribute(Constant.USER_TRANSIENT, user);

            Boolean isFromCheck = Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE));

            if (isFromCheck) {
                user.setRegistered(true);
                (request.getSession()).setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);
                DeliveryAddressForm deliveryAddressForm = userToDeliveryAddressForm(user);                
                request.setAttribute("deliveryAddressForm", deliveryAddressForm);
//                return mapping.findForward("paymentInfo");
                return mapping.findForward("deliveryAddress");

            } else {
                request.getSession(true).setAttribute(Constant.REGISTER_OR_LOGIN, "register");
                return mapping.findForward("redirectRegRes");
            }
        } else {
            warnings.add("passNotMatch", new ActionMessage("errors.registration.pass.dose.not.match"));
            saveErrors(request, warnings);
            return mapping.findForward("register");
        }
    }


    public ActionForward redirectToSuccess(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("success");
    }

    public ActionForward redirectToPaymentInfo(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("paymentInfo");
    }
}