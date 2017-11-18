package com.sefryek.doublepizza.web.action;

import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.CurrencyUtils;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.model.ContactInfo;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.model.UserRole;
import com.sefryek.doublepizza.service.IContactInfoService;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IUserRoleService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.web.form.DeliveryAddressForm;
import com.sefryek.doublepizza.web.form.LoginForm;
import com.sefryek.doublepizza.web.form.RegistrationForm;
import freemarker.template.Configuration;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class UserAction extends DispatchAction {

    private IUserService userService;
    private IDollarService dollarService;
    private IContactInfoService infoService;
    private IUserRoleService userRoleService;
    private IUserService iUserService;
    private AuthenticationManager authenticationManager;

    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        infoService = (IContactInfoService) context.getBean(IContactInfoService.BEAN_NAME);
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
        userRoleService = (IUserRoleService)context.getBean(IUserRoleService.BEAN_NAME);
        authenticationManager = (AuthenticationManager) context.getBean("authenticationManager");
    }

    private DeliveryAddressForm userToDeliveryAddressForm(User user){

        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        deliveryAddressForm.setTitle(user.getTitle().toString());
        deliveryAddressForm.setEmail(user.getEmail());
        deliveryAddressForm.setFirstName(user.getFirstName());
        deliveryAddressForm.setLastName(user.getLastName());
//        deliveryAddressForm.setCity(user.getCity());
        deliveryAddressForm.setCompany(user.getCompany());
/*        String phone = user.getCellPhone();
        if (phone != null){
            deliveryAddressForm.setCellPhone1(phone.substring(0, 3));
            deliveryAddressForm.setCellPhone2(phone.substring(3, 6));
            deliveryAddressForm.setCellPhone3(phone.substring(6, 10));
        }*/
//        deliveryAddressForm.setExt(user.getExt());
//        deliveryAddressForm.setStreetNo(user.getStreetNo());
//        deliveryAddressForm.setSuiteApt(user.getSuiteApt());
//        deliveryAddressForm.setBuilding(user.getBuilding());
//        deliveryAddressForm.setDoorCode(user.getDoorCode());
//        String postalCode = user.getPostalCode();
//        deliveryAddressForm.setPostalCode1(postalCode.substring(0, 3));
//        deliveryAddressForm.setPostalCode2(postalCode.substring(4, 7));
//        deliveryAddressForm.setStreet(user.getStreetName());
        return deliveryAddressForm;
    }

    public ActionForward login(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors warnings = new ActionErrors();
//        LoginForm loginForm = (LoginForm) form;
//        ActionErrors errors = loginForm.validate(mapping, request);
//        if (!errors.isEmpty()) {
//            saveErrors(request, errors);
//            return mapping.findForward("login");
//        }
//        String email = loginForm.getEmail();
//        String password = loginForm.getPassword();
//        User user = userService.findByEmailAndPassword(email, password);
        if(request.getParameter("error") != null){
            return mapping.findForward("login");
        }

        User user = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
//        SecurityContextHolder.getInstance().getAuthentication().getPrincipal();
//        User user = userService.findByEmail(email);
//        user.setRegistered(true);
        if (user != null) {
//            Double dpDollarAmount = 0.0;
//         if(dollarService != null){
//              dpDollarAmount = dollarService.calculateDpDollarsBalanceForUser(user);
//         }
//           if (dpDollarAmount != null){
//               user.setDpDollarBalance(CurrencyUtils.doubleRoundingFormat(dpDollarAmount));
//           }
//            if (user.getPassword().equals(password)) {
            request.getSession().setAttribute(Constant.USER_TRANSIENT, user);
            Boolean isFromCheck = Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE));
            if (isFromCheck) {
                user.setIsRegistered(true);
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
//            } else {
//                warnings.add("passwordNotFound", new ActionMessage("message.login.invalid.password"));
//                saveErrors(request, warnings);
//                return mapping.findForward("login");
//            }
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
        request.getSession().removeAttribute(Constant.BASKET);
        request.getSession().removeAttribute("totalPrice");
        request.getSession().removeAttribute("userForm");
        request.getSession().removeAttribute("checkoutForm");
        request.getSession().invalidate();
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

/*        ActionErrors warnings = new ActionErrors();
        EmailForm emailForm = (EmailForm) form;

        String email = emailForm.getEmail();

        User user = userService.findByEmail(email);

        if (user == null) {*/

        return mapping.findForward("forwardToRegistrationPage");

      /*  } else {
            warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));
            saveErrors(request, warnings);
            return mapping.findForward("login");
        }*/
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
        String postalCode = userForm.getPostalCode1() + "" + userForm.getPostalCode2();
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
        String subscribed="False";
        if(userForm.getSubscribed()!=null){subscribed=userForm.getSubscribed();}
        String facebookUsername = userForm.getFacebookUsername();
        String twitterUsername = userForm.getTwitterUsername();
        String birthDate = userForm.getBirthDate();
        Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
        String lang="";
        if(locale.getDisplayName().equals("English")){lang="En";}else{lang="Fr";}
//todo for null parameter
        if (password.equals(verifyPassword)) {

            UserRole userRole = userRoleService.findByName(IUserRoleService.UserRoleName.ROLE_USER);


            User user = new User(User.Title.valueOf(title), email, firstName, lastName, password, company,/* city,
                    postalCode, streetNo, streetName,suiteApt, building, doorCode,*/ facebookUsername, twitterUsername,
                 /*   cellPhone1 + cellPhone2 + cellPhone3,*/ birthDate,lang,subscribed, userRole);
            user.setIsRegistered(true);
            try {
                Date birthdate = DateUtil.stringToDate(birthDate);
                if (birthdate.after(new Date(System.currentTimeMillis()))) {
                    request.setAttribute("message", "invalid birthdate");
                } else {
                    user.setBirthDate(birthdate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                if(userService.isRegistered(email)) {
                    // give error saying that user is registered before
                    warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));
                    saveErrors(request, warnings);
                    return mapping.findForward("register");

                }
                else{
                    userService.save(user);
                    infoService.save(getContactInfo(userForm,user.getId(),locale));   }
            }

            catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
                warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));

            } catch (DAOException e) {
                log.error(e.getMessage());
                if (e.getType().equals(DataIntegrityViolationException.class)) {
                    warnings.add("duplicateUseremail", new ActionMessage("message.registration.duplicate.user"));

                } else {
                    warnings.add("unknow", new ActionMessage("message.registration.duplicate.user"));
                }

                saveErrors(request, warnings);

            }

            messages.add("successful", new ActionMessage("message.registration.is.successful"));
            saveMessages(request, messages);
            if (user != null) {
                Double dpDollarAmount = dollarService.calculateDpDollarsBalanceForUser(user.getId());
                user.setDpDollarBalance(CurrencyUtils.doubleRoundingFormat(dpDollarAmount));
                request.getSession().setAttribute(Constant.USER_TRANSIENT, user);
            }

            Boolean isFromCheck = Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE));

            if (isFromCheck) {
                user.setIsRegistered(true);
                authenticateUserAndSetSession(user, request);
                DeliveryAddressForm deliveryAddressForm = userToDeliveryAddressForm(user);
                request.setAttribute("deliveryAddressForm", deliveryAddressForm);
//               return mapping.findForward("paymentInfo");
                return mapping.findForward("deliveryAddress");

            } else {
                authenticateUserAndSetSessionForReg(user, request);
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


    private ContactInfo getContactInfo(RegistrationForm registrationForm,int userId, Locale locale) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setUserId(userId);
        contactInfo.setAddressScreenEN(registrationForm.getAddress());
        contactInfo.setBuilding(registrationForm.getBuilding());
        contactInfo.setCity(registrationForm.getCity());
        contactInfo.setDoorCode(registrationForm.getDoorCode());
        contactInfo.setExt(registrationForm.getExt());
        contactInfo.setPhone(registrationForm.getCellPhone1() + registrationForm.getCellPhone2() + registrationForm.getCellPhone3());
        contactInfo.setPostalcode(registrationForm.getPostalCode1() + " " + registrationForm.getPostalCode2());
        contactInfo.setStreet(registrationForm.getStreet());
        contactInfo.setStreetNo(registrationForm.getStreetNo());
        contactInfo.setSuiteAPT(registrationForm.getSuiteApt());
        contactInfo.setAddressScreenEN(registrationForm.getAddressName());
        return contactInfo;
    }

    public ActionForward forgetPasswordPage(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("fgPassword");
    }
    public ActionForward sendForgetPassword(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        LoginForm loginForm = (LoginForm) form;
        String email = loginForm.getEmailForgotPassword();
        User user = null;
        String lang = "";
        String subjectStr = "";
        if(email != null && !email.equals("")){
            user = userService.findByEmail(email);
        }else{
            return mapping.findForward("login");
        }

        if (user != null) {

            SendMail sendMail= new SendMail();
            Map templateVars = new HashMap();

            //fill the template by your elements
            if(user.getFirstName() != null && user.getLastName() != null )
                templateVars.put("name", user.getFirstName() + " " + user.getLastName());
            else
                templateVars.put("name", "Sir/Madam");

            templateVars.put("uName", user.getEmail());
            templateVars.put("userId", String.valueOf(user.getId()).replaceAll(",", ""));
            templateVars.put("randNum", String.valueOf(Math.random()));
            templateVars.put("httpPath", ApplicationConfig.httpFilePath);
            //
            if(loginForm.getLang().equals("true")){
                lang = "En";
                subjectStr = "Reset Your Double Pizza Password";
            }else{
                subjectStr = "Réinitialisation de votre Double mot Pizza";
                lang = "Fr";
            }

            sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
            sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
            sendMail.prepareMsgBeforeSendForgottenPassMail(lang, Constant.MAIL_SENDER_USER_NAME,
                    user.getEmail(),
                    subjectStr,
                    templateVars);

            request.setAttribute("forgotSendStatus", "sent");
        }else{

            request.setAttribute("forgotSendStatus", "invalidEmail");

        }
        request.setAttribute("email", email);
        return mapping.findForward("fgPassword");
    }

    public ActionForward viewResetPassword(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm loginForm = (LoginForm) form;
        loginForm.setUserId(request.getParameter("userId").toString());
        loginForm.setEmail(request.getParameter("email").toString());

        request.setAttribute("userId", request.getParameter("userId").toString());
        request.setAttribute("email", request.getParameter("email").toString());

        iUserService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        User user = iUserService.findById(Integer.parseInt(loginForm.getUserId()));

        if (user != null && user.getEmail().equals(loginForm.getEmail())) {
            request.setAttribute("flagSecurity","True");
        }else{
            request.setAttribute("flagSecurity","False");
            request.setAttribute("passMessage", "Wrong password!");
        }

        return mapping.findForward("resetPassword");
    }

    public ActionForward saveResetPassword(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        iUserService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        LoginForm loginForm = (LoginForm) form;
        User user = iUserService.findById(Integer.parseInt(loginForm.getUserId()));
        if (user != null && user.getEmail().equals(loginForm.getEmail())) {
            if (loginForm.getNewPassword().equals(loginForm.getNewPassword())) {
                user.setPassword(loginForm.getNewPassword());
                request.setAttribute("passMessage", "successfully");
                request.setAttribute("flagChgPass","True");
                iUserService.update(user);
            } else {
                request.setAttribute("flagChgPass","False");
                request.setAttribute("passMessage", "wrong");
                return mapping.findForward("forwardToChangePassword");
            }
        }else{
            request.setAttribute("flagChgPass","False");
            request.setAttribute("passMessage", "wrong");
        }

        request.setAttribute("userId", loginForm.getUserId());
        request.setAttribute("email", loginForm.getEmail());
        return mapping.findForward("resetPassword");

    }
    private void authenticateUserAndSetSession(User user,
                                               HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());


        request.getSession(true).setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    private void authenticateUserAndSetSessionForReg(User user,
                                                     HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());


        request.getSession(true).setAttribute(Constant.REGISTER_OR_LOGIN, "register");

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}