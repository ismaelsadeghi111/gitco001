package com.sefryek.doublepizza.web.action;

import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.IContactInfoService;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IUserRoleService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.implementation.CateringContactInfoServiceImpl;
import com.sefryek.doublepizza.service.implementation.CateringServiceImpl;
import com.sefryek.doublepizza.web.form.CateringContactInfoForm;
import com.sefryek.doublepizza.web.form.CateringForm;
import com.sefryek.doublepizza.web.form.LoginForm;
import freemarker.template.Configuration;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 12/28/13
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CateringAction extends DispatchAction {
    private CateringServiceImpl cateringService;
    private CateringContactInfoServiceImpl cateringContactInfoService;
    private CateringOrder cateringOrder;
    private List<Catering> caterings;
    private CateringContactInfo cateringContactInfo;
    private List<CateringOrderDetail> cateringOrderDetails;
    private CateringForm cateringForm;
    WebApplicationContext context;

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("getCateringItemsOrder");
    }

    public List<Catering> getCaterings() {
        return caterings;
    }

    public void setCaterings(List<Catering> caterings) {
        this.caterings = caterings;
    }

    public CateringOrder getCateringOrder() {
        return cateringOrder;
    }

    public void setCateringOrder(CateringOrder cateringOrder) {
        this.cateringOrder = cateringOrder;
    }

    public CateringContactInfo getCateringContactInfo() {
        return cateringContactInfo;
    }

    public void setCateringContactInfo(CateringContactInfo cateringContactInfo) {
        this.cateringContactInfo = cateringContactInfo;
    }

    public CateringContactInfoServiceImpl getCateringContactInfoService() {
        return cateringContactInfoService;
    }

    public void setCateringContactInfoService(CateringContactInfoServiceImpl cateringContactInfoService) {
        this.cateringContactInfoService = cateringContactInfoService;
    }

    public List<CateringOrderDetail> getCateringOrderDetails() {
        return cateringOrderDetails;
    }

    public void setCateringOrderDetails(List<CateringOrderDetail> cateringOrderDetails) {
        this.cateringOrderDetails = cateringOrderDetails;
    }

    public CateringServiceImpl getCateringService() {
        return cateringService;
    }

    public void setCateringService(CateringServiceImpl cateringService) {
        this.cateringService = cateringService;
    }

    public ActionForward goToCaternig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        return mapping.findForward("getCateringItems");
    }

    public ActionForward goToCaternigOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        try {
            caterings.clear();
            cateringOrderDetails.clear();
            CateringForm cateringForm = (CateringForm) form;
            caterings = cateringService.getAllCaterings();
            cateringForm.setCaterings(caterings);

        } catch (DAOException e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("getCateringItemsOrder");
    }

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
    }

    public ActionForward addCateringItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        String cateringIdStr = request.getParameter("cateringId");
        String quantityStr = request.getParameter("quantity");
        Integer quantity = quantityStr != null ? Integer.valueOf(quantityStr) : null;
        String indexStr = request.getParameter("index");
        Integer index = indexStr != null ? Integer.valueOf(indexStr) : null;
        Long cateringId = cateringIdStr != null ? Long.valueOf(cateringIdStr) : null;
        CateringForm cateringForm = (CateringForm) form;
        if (cateringForm != null && cateringForm.getCaterings() != null) {
//            Catering catering = findCateringInList(cateringForm.getCaterings(),cateringId);
            Catering catering = cateringForm.getCaterings().get(index);
            catering = (catering == null && catering.getId() == null) ? caterings.get(index) : catering;
            CateringOrderDetail cateringOrderDetail = new CateringOrderDetail();
            cateringOrderDetail.setId(Long.valueOf(index));
            int id = cateringOrderDetails.size();
            cateringOrderDetail.setId(Long.valueOf(id));
            cateringOrderDetail.setCatering(catering);
            cateringOrderDetail.setQuantity(quantity);
            if (quantity != null && quantity > 0) {
                cateringOrderDetails.add(cateringOrderDetail);

            }
            request.getSession().setAttribute("cateringOrderDetails", cateringOrderDetails);

        }
        return mapping.findForward("getCateringItemsOrder");

    }

    public ActionForward removeCateringItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        if (cateringOrderDetails.size() == 1) {
            cateringOrderDetails.clear();

        } else {
            String itemId = request.getParameter("cateringOrderDetailId");

            CateringOrderDetail cateringOrderDetail = cateringOrderDetails.get((int) Long.parseLong(itemId));
            cateringOrderDetails.remove(cateringOrderDetail);
        }

        request.getSession().setAttribute("cateringOrderDetails", cateringOrderDetails);
        return mapping.findForward("getCateringContactInfo");

    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        CateringContactInfoForm cateringContactInfoForm = (CateringContactInfoForm) form;
        try {

            cateringContactInfo.setAddress(cateringContactInfoForm.getAddress());
            cateringContactInfo.setDeliveryDate(cateringContactInfoForm.getDeliveryDate());
            cateringContactInfo.setExt(cateringContactInfoForm.getExt());
            cateringContactInfo.setFirstName(cateringContactInfoForm.getFirstName());
            cateringContactInfo.setLastName(cateringContactInfoForm.getLastName());
            cateringContactInfo.setPhone(cateringContactInfoForm.getCellPhone1().concat(cateringContactInfoForm.getCellPhone2().concat(cateringContactInfoForm.getCellPhone3())));
            cateringContactInfo.setGender(cateringContactInfoForm.isGender());
            cateringContactInfoService.save(cateringContactInfo);
            cateringOrderDetails = (List<CateringOrderDetail>) request.getSession().getAttribute("cateringOrderDetails");
            cateringOrder.setCateringContactInfo(cateringContactInfo);
            cateringOrder.setCustomerNote(cateringContactInfoForm.getCustomerNote());
            cateringOrder.setCateringOrderDetails(cateringOrderDetails);
            Date currentDate = new Date();
            cateringOrder.setOrderDate(currentDate.toString());

            cateringService.save(cateringOrder);

            String detailTbl = "";
            int count = 0;
            for (CateringOrderDetail cateringOrderDetail : cateringOrderDetails) {
                cateringOrderDetail.setCateringOrder(cateringOrder);
                cateringService.save(cateringOrderDetail);
                count++;
                detailTbl = detailTbl + "<tr><td><font style='color:yellow'><b> " + count +
                        " : </b></font><br>" +
                        cateringOrderDetail.getQuantity() + " of " +
                        cateringOrderDetail.getCatering().getTitleEn()+ "<font style='color:yellow'><b> | </b></font>" + cateringOrderDetail.getCatering().getDescriptionEn()+
                        "<br><br></td></tr>";
            }
           request.setAttribute("flagSave", "True");
//            cateringService.save(cateringOrder);

            //Saeid AmanZadeh
            String sendMailStr = sendCateringMail(detailTbl, cateringContactInfoForm, cateringOrder);
            //


            return mapping.findForward("getCateringItemsOrder");

        } catch (Exception e) {
            request.setAttribute("flagSave", "False");
            return mapping.findForward("getCateringContactInfo");

        } finally {
            request.getSession().setAttribute("cateringOrderDetails", null);
            cateringOrderDetails.clear();
        }

    }
    public String sendCateringMail(String detailTbl, CateringContactInfoForm cateringContactInfoForm, CateringOrder cateringOrder) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String date = sdf.format(new Date());
        String subjectStr = "";
        String[] emailsStr = ApplicationConfig.getCateringEmails().split(",");
        SendMail sendMail= new SendMail();
        Map templateVars = new HashMap();

        //fill the template by your elements
        templateVars.put("uName", cateringContactInfoForm.getFirstName() + " " + cateringContactInfoForm.getLastName());

        templateVars.put("orderNum", cateringOrder.getId());
        templateVars.put("orderDate", date);
        templateVars.put("address", cateringContactInfoForm.getAddress());
        templateVars.put("tel", cateringContactInfoForm.getCellPhone1() + " " + cateringContactInfoForm.getCellPhone2() + " " + cateringContactInfoForm.getCellPhone3());
        templateVars.put("detail", detailTbl);
        templateVars.put("httpPath", ApplicationConfig.httpFilePath);
        //
        subjectStr = "You have received and order for catering. Order number: "+cateringOrder.getId();
        sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
        sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
        for(int int1=0; int1<emailsStr.length; int1++){
            sendMail.prepareMailSendCatering(Constant.MAIL_SENDER_USER_NAME,
                    emailsStr[int1],
                    subjectStr,
                    templateVars);
        }

       return "sent";
    }

    public ActionForward goToCaternigContactInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                 HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (cateringOrderDetails.size() > 0)
            return mapping.findForward("getCateringContactInfo");
        else {
            request.setAttribute("flagSave", "False");
            return mapping.findForward("getCateringItemsOrder");


/*            try {
//                String applyMsg = MessageUtil.get("msg.apply", (Locale) session.getAttribute(Globals.LOCALE_KEY));
                PrintWriter out = response.getWriter();
                out.flush();


            } catch (IOException e) {
            }*/

        }
//        return null;
    }


    private void save() throws Exception {
        try {
            cateringContactInfoService.save(cateringContactInfo);
            cateringOrder.setCateringContactInfo(cateringContactInfo);
            for (CateringOrderDetail cateringOrderDetail : cateringOrderDetails) {
                cateringService.save(cateringOrderDetail);
            }
            cateringOrder.setCateringOrderDetails(cateringOrderDetails);
            cateringService.save(cateringOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Catering findCateringInList(List<Catering> cateringList, Long cateringId) throws Exception {
        for (Catering catering : cateringList) {
            if (catering.getId() == cateringId) {
                return catering;
            }
        }
        return null;
    }

    public ActionForward goToCountinueShopping(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {

        try {
            caterings.clear();
            CateringForm cateringForm = (CateringForm) form;
            caterings = cateringService.getAllCaterings();
            cateringForm.setCaterings(caterings);

        } catch (DAOException e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("getCateringItemsOrder");
    }
}