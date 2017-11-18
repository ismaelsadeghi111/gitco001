package com.sefryek.doublepizza.web.action;

import com.sefryek.common.util.DateUtil;
import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.ContactInfo;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.IContactInfoService;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.web.form.ProfileForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: E_Ghasemi
 * Date: 1/27/14
 * Time: 4:11 PM
 */
public class ProfileAction extends DispatchAction {

    private Logger logger;
    private WebApplicationContext context;
    private IContactInfoService infoService;
    private IDollarService dollarService;
    private IUserService iUserService;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        infoService = (IContactInfoService) context.getBean(IContactInfoService.BEAN_NAME);
        iUserService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
    }


    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws DataLoadException, DAOException, ServiceException {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        return gotoMainPage(mapping, form, request, response);
    }


    public ActionForward gotoMainPage(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        try {
            if (user != null) {
                if (user.getBirthDate() != null) {
                    user.setBirthDateStr(DateUtil.dateToStringYYY_MM_DD(user.getBirthDate()).substring(0, 10));
                }
//                Double dpDollarAmount = dollarService.calculateDpDollarsBalanceForUser(user);
//                user.setDpDollarBalance(CurrencyUtils.doubleRoundingFormat(dpDollarAmount));
                request.getSession().setAttribute(Constant.USER_TRANSIENT, user);
                List<ContactInfo> list = infoService.getAll(user.getId());
                Map<Long, ContactInfo> map = new HashMap();
                for (ContactInfo c : list) {
                    map.put(c.getId(), c);
                }
                if(user.getSubscribed()){
                    request.setAttribute("subscribeValue", "checked");
                }else{
                    request.setAttribute("subscribeValue", "");
                }
                request.setAttribute("contacts", list);
                request.setAttribute("contactMap", map);
            }
        } catch (ParseException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("forwardToProfile");
    }

    public ActionForward saveContactInfo(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        ProfileForm profileForm = (ProfileForm) form;
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        if (user != null){
            try {
                if (((ProfileForm) form).getSubmitMode().equals("edit")) {


                    infoService.update(getContactInfo(profileForm, user));
                } else {
                    infoService.save(getContactInfo(profileForm, user));
                }
            } catch (DAOException e) {
                logger.error("Error :" + e.getMessage());
            }
        }
        return gotoMainPage(mapping, form, request, response);
    }

    public ActionForward saveUserInfo(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        ProfileForm profileForm = (ProfileForm) form;

        if (user != null){
            user.setTwitterUsername(profileForm.getTwitterUsername());
            user.setFacebbookUsername(profileForm.getFacebbookUsername());
            user.setCompany(profileForm.getCompany());
            user.setFirstName(profileForm.getFirstName());
            user.setLastName(profileForm.getLastName());
            user.setEmail(profileForm.getEmail());
            user.setTitle(User.Title.valueOf(profileForm.getTitle()));
            if(profileForm.getSubscribe().equals("true") || profileForm.getSubscribe().equals("checked")){
                user.setSubscribed(true);
            }else {
                user.setSubscribed(false);
            }

            try {
                Date birthdate = DateUtil.stringToDate(profileForm.getBirthDate());
                if (birthdate.after(new Date(System.currentTimeMillis()))) {
                    request.setAttribute("message", "invalid birthdate");
                } else {
                    user.setBirthDate(birthdate);
                    iUserService.update(user);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return gotoMainPage(mapping, form, request, response);
    }

    public ActionForward changePassword(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        return mapping.findForward("forwardToChangePassword");
    }

    public ActionForward savePassword(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }
        ProfileForm profileForm = (ProfileForm) form;
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        if (user != null) {
                if (user.getPassword().equals(profileForm.getOldPassword())) {
                user.setPassword(profileForm.getNewPassword());
                request.setAttribute("passMessage", "successfully");
                request.setAttribute("flagChgPass","True");
                iUserService.update(user);
            } else {
                request.setAttribute("passMessage", "wrong");
                return mapping.findForward("forwardToChangePassword");
            }
        }

        return mapping.findForward("forwardToChangePassword");
        //return  gotoMainPage(mapping, form,request,response);
    }

    private ContactInfo getContactInfo(ProfileForm profileForm, User user) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(profileForm.getId());
        contactInfo.setAddressScreenEN(profileForm.getAddressScreenEN());
        contactInfo.setBuilding(profileForm.getBuilding());
        contactInfo.setCity(profileForm.getCity());
        contactInfo.setDoorCode(profileForm.getDoorCode());
        contactInfo.setExt(profileForm.getExt());
        contactInfo.setPhone(profileForm.getPhone1() + profileForm.getPhone2() + profileForm.getPhone3());
        contactInfo.setPostalcode(profileForm.getPostalcode1() + " "+ profileForm.getPostalcode2());
        contactInfo.setStreet(profileForm.getStreet());
        contactInfo.setStreetNo(profileForm.getStreetNo());
        contactInfo.setSuiteAPT(profileForm.getSuiteAPT());
        contactInfo.setUserId(user.getId());
        return contactInfo;
    }

}


