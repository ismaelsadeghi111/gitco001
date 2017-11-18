package com.sefryek.doublepizza.web.action;

import com.sefryek.common.LogMessages;
import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.DateUtil;
import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.CampaignDAO;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.enums.CampaignType;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.*;
import com.sefryek.doublepizza.web.form.CampaignForm;
import freemarker.template.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/8/13
 * Time: 2:32 PM
 */
public class CampaignAction extends DispatchAction {

    private static Logger logger;
    private static WebApplicationContext context;
    private static IStandardCampaign standardCampaign;
    private IPostalCampaign postalCampaign;
    private IBirthdayCampaign birthdayCampaign;
    private CampaignDAO campaignDAO;
    private int offset = 0;
    private int count = 10;
    private int totalPages = 0;
    private int totalRecords = 0;
    private int from = 0;
    private int to = 0;
    private IMenuService iMenuService;
    private Configuration freemarkerConfiguration;


    //Saeid AmanZadeh
    private MailSender mailSender;
    private MailMessage mailMessage;
    private static IUserService userService;

    //---


    public WebApplicationContext getContext() {
        return context;
    }

    public void setContext(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        standardCampaign = (IStandardCampaign) context.getBean(IStandardCampaign.BEAN_NAME);
        postalCampaign =(IPostalCampaign)context.getBean(IPostalCampaign.BEAN_NAME);
        birthdayCampaign =(IBirthdayCampaign)context.getBean(IBirthdayCampaign.BEAN_NAME);
        campaignDAO = new CampaignDAO();
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
//        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext().
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        offset = 0;
        return goToMainPage(mapping, form, request, response);
    }

    public ActionForward goToMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        logger.info("Debug:" + LogMessages.START_OF_METHOD + "goToMainPage - > gotoStandardCampaign");
        return showRegular(false, mapping, request);
    }
    /**
     *
     * @param locale
     * @return
     */

    public Map<String, String> getFoods(Locale locale) {
        Menu menuObj;
        Map<String, String> mapList = new HashMap<>();
        List<Category> specialList = null;
        menuObj = InMemoryData.getMenuByName(Constant.ORDER_MENU_NAME);
        if (menuObj.getCategoryList() != null) {
            specialList = menuObj.getCategoryList();
        }
        menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
        if (menuObj.getCategoryList() != null) {
            specialList.addAll(menuObj.getCategoryList());
        }

        for (Category menuItem : specialList) {
            List<CombinedMenuItem> combinedMenuItemList = new ArrayList<CombinedMenuItem>();
            for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                if (subCategoryItem.getType().equals(CombinedMenuItem.class)) {
                    combinedMenuItemList.add((CombinedMenuItem) subCategoryItem.getObject());
                }
            }
            for (CombinedMenuItem combinedItem : combinedMenuItemList) {
                mapList.put(combinedItem.getProductNo(), combinedItem.getName(locale/*(Locale).getAttribute(Globals.LOCALE_KEY)*/));
            }

            //========INNERCATEGORY======--%>

            List<Category> categoryList = new ArrayList<Category>();
            for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                if (subCategoryItem.getType().equals(Category.class)) {
                    categoryList.add((Category) subCategoryItem.getObject());
                }
            }
            if (categoryList != null) {
                for (Category innerCategoryItem : categoryList) {
                    List<MenuSingleItem> subMenuItemList = InMemoryData.getCategoryMenuSingleItemList(innerCategoryItem);
                    List<CombinedMenuItem> subCombinedItemList = InMemoryData.getCategoryCombinedMenuItemList(innerCategoryItem);
                    if (subMenuItemList != null) {
                        for (MenuSingleItem subSingleItem : subMenuItemList) {
                            mapList.put(subSingleItem.getProductNo(), subSingleItem.getName(locale/*(Locale).getAttribute(Globals.LOCALE_KEY)*/));
                        }
                    }
                    if (subCombinedItemList != null) {
                        for (CombinedMenuItem subCombinedMenuItem : subCombinedItemList) {
                            mapList.put(subCombinedMenuItem.getProductNo(), subCombinedMenuItem.getName(locale/*(Locale).getAttribute(Globals.LOCALE_KEY)*/));
                        }
                    }

                }
            }
            //========SINGLE ITEMS======--%>
            List<MenuSingleItem> menuSingleItemList = new ArrayList<MenuSingleItem>();
            for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                if (subCategoryItem.getType().equals(MenuSingleItem.class)) {
                    menuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
                }
            }
            ListIterator menuSingleItemListIterator = menuSingleItemList.listIterator();
            for (MenuSingleItem singleItem : menuSingleItemList) {
                mapList.put(singleItem.getProductNo(), singleItem.getName(locale/*(Locale).getAttribute(Globals.LOCALE_KEY)*/));
                menuSingleItemListIterator.next();
            }
        }
        return mapList;
    }
    /**
     * @param mapping
     * @param request
     * @return
     */
    public ActionForward showRegular(Boolean showAfterInsert, ActionMapping mapping ,HttpServletRequest request) {
        try {


          if (!SecurityUtils.isAdmin()) {
                return mapping.findForward("forbidden");
          }
            //--Saeid AmanZadeh
            //Changing Status of StandardCampaigns
            if (request.getParameter("newStatus") != null) {
                if (request.getParameter("newStatus").equals("run") || request.getParameter("newStatus").equals("pause")) {
                    List<StandardCampaign> list = new ArrayList();
                    int id = Integer.valueOf(request.getParameter("standardCampaignId").toString()).intValue();
                    list = standardCampaign.getStandardCampaignById(id);
                    StandardCampaign selectedCampaign = new StandardCampaign();
                    selectedCampaign = list.get(0);
                    selectedCampaign.setStatus(request.getParameter("newStatus"));
                    int intRes = standardCampaign.saveChangedStandardCampaign(selectedCampaign);
                }
            }
            //---
            //get Foods list

            Map<String, String> foodMapList = new HashMap<>();
            foodMapList = standardCampaign.getMenuItemCampaign();
            request.setAttribute("menuList", foodMapList);
            iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
            List<PopularCategory> categoryMenuList = iMenuService.getMenuCategoryListByName("", "", "");
            request.setAttribute("allMenuItemList", categoryMenuList);
            //

            totalRecords = standardCampaign.getCountStandardCampaign();
            totalPages = (totalRecords % count) == 0 ? (totalRecords / count) : (totalRecords / count) + 1;
            if (showAfterInsert) {
                offset = totalPages - 1;
            } else {
                offset = (offset >= totalPages) ? totalPages - 1 : offset;
            }
            from = (offset * count);
            to = (offset * count) + count;

            //get list of campaign
            List<StandardCampaign> list = new ArrayList();
            list = standardCampaign.getStandardCampaign(offset, count);

            request.setAttribute("campaignList", list);
            request.setAttribute("from", from + 1);
            request.setAttribute("to", (to) < totalRecords ? to : totalRecords);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageNumber", offset + 1);
            request.setAttribute("count", count);

        } catch (Exception e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("gotoStandardCampaign");
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showPostal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            //request.setAttribute("menuList", getFoods((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY)));

            //Saeid AmanZadeh

            totalRecords = postalCampaign.getCountPostalCampaign();
            List<PostalCampaign> list = new ArrayList();
            totalPages = (totalRecords % count) == 0 ? (totalRecords / count) : (totalRecords / count) + 1;
            offset = (offset >= totalPages) ? totalPages - 1 : offset;
            from = (offset * count);
            to = (offset * count) + count;

            //Get foods list
            Map<String, String> mapList = new HashMap<>();
            mapList = standardCampaign.getMenuItemCampaign();
            request.setAttribute("menuList", mapList);
            iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
            List<PopularCategory> categoryMenuList =iMenuService.getMenuCategoryListByName("","","");
            request.setAttribute("allMenuItemList", categoryMenuList);

            //--
            list = postalCampaign.getPostalCampaign(offset , count);

            request.setAttribute("campaignList", list);
            request.setAttribute("from", from + 1);
            request.setAttribute("to", (to) < totalRecords ? to : totalRecords);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageNumber", offset + 1);
            request.setAttribute("count", count);

        } catch (Exception e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("gotoPostalCampaign");
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showBirthday(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            //request.setAttribute("menuList", getFoods((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY)));
            Map<String, String> mapList = new HashMap<>();
            mapList = standardCampaign.getMenuItemCampaign();
            request.setAttribute("menuList", mapList);
            iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
            List<PopularCategory> categoryMenuList =iMenuService.getMenuCategoryListByName("","","");
            request.setAttribute("allMenuItemList", categoryMenuList);

            //--
            totalRecords = birthdayCampaign.getCountBirthdayCampaign();
            List<BirthdayCampaign> list = new ArrayList();
            totalPages = (totalRecords % count) == 0 ? (totalRecords / count) : (totalRecords / count) + 1;
            offset = (offset >= totalPages) ? totalPages - 1 : offset;
            from = (offset * count);
            to = (offset * count) + count;

            list = birthdayCampaign.getBirthdayCampaign(offset , count);

            request.setAttribute("campaignList", list);
            request.setAttribute("from", from + 1);
            request.setAttribute("to", (to) < totalRecords ? to : totalRecords);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageNumber", offset + 1);
            request.setAttribute("count", count);

        } catch (Exception e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("gotoBirthdayCampaign");
    }
    //Saeid AmanZadeh
    public ActionForward insertStandardCampaign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        CampaignForm campaign = (CampaignForm) form;
        StandardCampaign standardCampaignModel = new StandardCampaign();
        if (CampaignType.getType(((CampaignForm) form).getCampaignType()) == CampaignType.CustomerOrder) {
            standardCampaignModel.setOrderType(CampaignType.CustomerOrder.name());
            standardCampaignModel.setOrdered(true);
            standardCampaignModel.setOrderDays(campaign.getCustomOrderInLastDays());

        } else if (CampaignType.getType(((CampaignForm) form).getCampaignType()) == CampaignType.CustomerNotOrder) {
            standardCampaignModel.setOrderType(CampaignType.CustomerNotOrder.name());
            standardCampaignModel.setOrdered(false);
            standardCampaignModel.setOrderDays(campaign.getCustomNotOrderInLastDays());

        } else if (CampaignType.getType(((CampaignForm) form).getCampaignType()) == CampaignType.customizedOrdered) {
            standardCampaignModel.setOrderType(CampaignType.customizedOrdered.name());
            standardCampaignModel.setOrdered(true);
            standardCampaignModel.setOrderDays(campaign.getOrderInLastDays());
            standardCampaignModel.setOrderNumbers(campaign.getOrderNumbers());
            standardCampaignModel.setOrderSign(campaign.getOrderSign());
        }else if (CampaignType.getType(((CampaignForm) form).getCampaignType()) == CampaignType.allUsers) {
            standardCampaignModel.setOrderType(CampaignType.allUsers.name());
            standardCampaignModel.setOrdered(true);
        }
        try {
            //-- Upload File
            String savedFileName = "";
            String savedFileNameEn = "";
            Boolean validateFile = validate(campaign.getImageFile());
            Boolean validateFileEn = validate(campaign.getImageFileEn());
            if(validateFile){
                savedFileName = uploadImage(campaign.getImageFile());
            }
            if(validateFileEn){
                savedFileNameEn = uploadImage(campaign.getImageFileEn());
            }
            if(savedFileName!= null && !savedFileName.equals("") && savedFileNameEn != null && !savedFileNameEn.equals("")){
                standardCampaignModel.setImageUrl(savedFileName);
                standardCampaignModel.setImageUrlEn(savedFileNameEn);
                standardCampaignModel.setMenu_id(campaign.getFood());
                standardCampaignModel.setCampaign_title_en(campaign.getTitleEn());
                standardCampaignModel.setCampaign_title_fr(campaign.getTitleFr());
                standardCampaignModel.setCampaign_date(campaign.getCampaignDate());

                standardCampaignModel.setStatus(campaign.getStatus());
                String itemHtmlEn = "";
                String itemHtmlFr = "";

                if(!campaign.getItemHtmlEn().equals("")){
                    itemHtmlEn = campaign.getItemHtmlEn().trim().replaceAll("'", "''");
                }
                if(!campaign.getItemHtmlFr().equals("")){
                    itemHtmlFr = campaign.getItemHtmlFr().trim().replaceAll("'", "''");
                }

                standardCampaignModel.setItemHtmlEn(itemHtmlEn);
                standardCampaignModel.setItemHtmlFr(itemHtmlFr);
                standardCampaignModel.setSubjectEn(campaign.getSubjectEn().replaceAll("\"", ""));
                standardCampaignModel.setSubjectFr(campaign.getSubjectFr().replaceAll("\"", ""));
                int resultInt = standardCampaign.doSaveStandardCampaign(standardCampaignModel);
                if(resultInt > 0){
                    request.setAttribute("message", "saved");
                }else{
                    request.setAttribute("message", "saved");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return showRegular(true, mapping , request);
    }

    public ActionForward paging(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) {

        CampaignForm campaignForm = (CampaignForm) form;
        if (campaignForm.getPagingAction().equals("firstPage")) {
            offset = 0;
        } else if (campaignForm.getPagingAction().equals("nextPage")) {
            offset++;
        } else if (campaignForm.getPagingAction().equals("lastPage")) {
            offset = totalPages - 1;
        } else if (campaignForm.getPagingAction().equals("previousPage")) {
            offset--;
        }

        return showRegular(false, mapping,request);
    }

    public ActionForward insertPostalCampaign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) {
        try{
            CampaignForm campaignForm = (CampaignForm) form ;
            PostalCampaign postalCampaignModel = new PostalCampaign();
            postalCampaignModel.setCampaign_title_en(campaignForm.getTitleEn());
            postalCampaignModel.setCampaign_title_fr(campaignForm.getTitleFr());
            postalCampaignModel.setImageUrl(campaignForm.getImageFileName());
            postalCampaignModel.setMenu_id(campaignForm.getFood());
            postalCampaignModel.setPostalCode(campaignForm.getPostalCode());
            postalCampaignModel.setCampaign_date(DateUtil.stringToDate(campaignForm.getCampaignDate()));
            postalCampaign.completePostalCampaign(postalCampaignModel);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward insertBirthdayCampaign(ActionMapping mapping, ActionForm form, HttpServletRequest request,                                                HttpServletResponse response) {
        try{
            CampaignForm campaignForm = (CampaignForm) form ;
            BirthdayCampaign campaignModel = new BirthdayCampaign();
            campaignModel.setCampaign_title_en(campaignForm.getTitleEn());
            campaignModel.setCampaign_title_fr(campaignForm.getTitleFr());
            campaignModel.setImageUrl(campaignForm.getImageFileName());
            campaignModel.setMenu_id(campaignForm.getFood());
            birthdayCampaign.doSaveBirthdayCampaign(campaignModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Saeid AmanZadeh
    public ActionForward showMaskedEmails(ActionMapping mapping, ActionForm form, HttpServletRequest request,                                                HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        return mapping.findForward("gotoChangedEmailCampaign");
    }

    //Saeid AmanZadeh
    public ActionForward maskeEmails(ActionMapping mapping, ActionForm form, HttpServletRequest request,                                                HttpServletResponse response) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        try{
            if(applicationConfig.getRunMode().equals("DEV") || applicationConfig.getRunMode().equals("TEST")){
                int submitQuery = birthdayCampaign.maskeEmails();
                request.setAttribute("resultMsg", "submited");

            }else{
                logger.info("Debug: You are not in Dev or Test Mode !");
            }
        } catch (Exception e) {
            request.setAttribute("message", "Error during masking data");
            e.printStackTrace();
        }
        return mapping.findForward("gotoChangedEmailCampaign");
    }


    public ActionForward getJoinRegularCampaignUserOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                         HttpServletResponse response) {
        try{
            //Fill Select Food list
            //request.setAttribute("menuList", getFoods((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY)));
            Map<String, String> mapList = new HashMap<>();
            mapList = standardCampaign.getMenuItemCampaign();
            request.setAttribute("menuList", mapList);
            iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
            List<PopularCategory> categoryMenuList =iMenuService.getMenuCategoryListByName("","","");
            request.setAttribute("allMenuItemList", categoryMenuList);

            //----

            //Fill campaign Navigate List
            totalRecords = standardCampaign.getCountStandardCampaign();
            totalPages = (totalRecords % count) == 0 ? (totalRecords / count) : (totalRecords / count) + 1;
            offset = (offset >= totalPages) ? totalPages - 1 : offset;
            from = (offset * count);
            to = (offset * count) + count;
            List<StandardCampaign> list = new ArrayList();
            list = standardCampaign.getStandardCampaign(offset , count);
            request.setAttribute("campaignList", list);
            request.setAttribute("from", from + 1);
            request.setAttribute("to", (to) < totalRecords ? to : totalRecords);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageNumber", offset + 1);
            request.setAttribute("count", count);
            //
            //
            CampaignForm campaignForm = (CampaignForm) form ;
            campaignForm.setCampaignId(request.getParameter("standardCampaignId").toString());
            int id  = Integer.valueOf(request.getParameter("standardCampaignId").toString()).intValue();
            list = standardCampaign.getStandardCampaignById(id);
            StandardCampaign selectedCampaign = new StandardCampaign();
            selectedCampaign = list.get(0);
            //get mail list by this condition of standard campaign
            List<CampaignEmailTemplate> mailList = new ArrayList();
            mailList = standardCampaign.getUserEmailByStandardCampaign(selectedCampaign, "");

            request.setAttribute("resultStandardCampaignList", mailList);
            request.setAttribute("standardCampaignId", id);


            //----
        } catch (Exception e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
            return null;
        }
        return mapping.findForward("gotoShowResultStandardCampaign");
    }

    private String uploadImage(FormFile fileFrom) throws IOException {
        File file = null;
        OutputStream out = null;
        String fileNameWithTime = null;
        File dir = null;
        String prefix = FilenameUtils.getBaseName(fileFrom.getFileName());
        String suffix = FilenameUtils.getExtension(fileFrom.getFileName());
        Calendar cal = Calendar.getInstance();
        Date myDate = new Date();
        fileNameWithTime = (Math.round(Math.random()*100000))+"_"+ myDate.toString() + "_campaign." + suffix;
        fileNameWithTime = fileNameWithTime.replaceAll(" ", "");
        fileNameWithTime = fileNameWithTime.replaceAll(":", "");
        fileNameWithTime = fileNameWithTime.replaceAll("'", "");
        fileNameWithTime = fileNameWithTime.replaceAll("\"", "");
        //fileNameWithTime = fileNameWithTime.replaceAll("_", "");
        fileNameWithTime = fileNameWithTime.replaceAll("-", "");
        String destination = "";

        try{
            dir = new File(ApplicationConfig.getCampaignFilepath());
            if(!dir.exists())
                dir.mkdir();

            //save  in  filePath
            destination = ApplicationConfig.getCampaignFilepath() + fileNameWithTime.trim();
            File newFile = new File(destination);
            if(!newFile.exists()){
                FileOutputStream fos = new FileOutputStream(newFile);
                fos.write(fileFrom.getFileData());
                fos.flush();
                fos.close();
            }
        }catch (FileNotFoundException e) {

            logger.info("Debug: Destination = "+destination+" ");
            logger.info("Debug: ApplicationConfig.getCampaignFilepath = "+ApplicationConfig.getCampaignFilepath()+" **********");
            logger.info("File not found: " + e.getMessage());
        }catch (IOException e) { // catch all IOExceptions not handled by previous catch blocks
            logger.info("Debug: Destination = "+destination+"");
            logger.info("Debug: ApplicationConfig.getCampaignFilepath() = "+ApplicationConfig.getCampaignFilepath()+" **********");
            logger.info("Debug: General I/O exception: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Cannot read the file or directory!", e);
        }
        return fileNameWithTime;
    }

    private boolean validate(FormFile fileFrom)  {
        List<String> errors = new ArrayList<String>();

        if(fileFrom != null) try {
            Pattern pattern;
            int width = 0;
            int height = 0;
            String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
            pattern = Pattern.compile(IMAGE_PATTERN);
            InputStream inputStream = fileFrom.getInputStream();
            BufferedImage bufImage = ImageIO.read(inputStream);
            width = bufImage.getWidth();
            height = bufImage.getHeight();

               /* if(fileFrom.getFileSize() > 50 * 1024){
                    errors.add(MessageUtil.get("error.maxsize.image"));
                }
            if ((width > 100 || height > 100) || (width < 80 || height < 80)) {
                errors.add(MessageUtil.get("error.maxmindimension.image"));
            }*/

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return errors.size() == 0 ;
    }



    public ActionForward sendTestEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) {
        try{
            CampaignForm campaignMailListForm = (CampaignForm) form ;
            //Sending email by Spring
            Map<String, String> mapList = new HashMap<>();
            mapList = standardCampaign.getMenuItemCampaign();
            request.setAttribute("menuList", mapList);
            iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
            List<PopularCategory> categoryMenuList =iMenuService.getMenuCategoryListByName("","","");
            request.setAttribute("allMenuItemList", categoryMenuList);



            List<CampaignEmailTemplate> campaignEmailTemplatesLst = new ArrayList<CampaignEmailTemplate>();

            //Get selected Standard campaign
            List<StandardCampaign> list = new ArrayList();
            list = standardCampaign.getStandardCampaignById(campaignMailListForm.getStandardCampaignId());
            StandardCampaign selectedCampaign = new StandardCampaign();
            selectedCampaign = list.get(0);
            //
            SendMail sendMail= new SendMail();
            sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
            sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
            sendMail.setCampaignEmailTemplates(campaignEmailTemplatesLst);


            //Clean the text of emails
            String[] mailListArray = null;
            String mailLstStr = request.getParameter("mailList").toString().trim();
            if(!mailLstStr.equals("")){
                if(mailLstStr.substring(mailLstStr.length()-2,mailLstStr.length()-1).equals(","))
                    mailLstStr = mailLstStr.substring(0,mailLstStr.length()-2);

                mailListArray = mailLstStr.split(",");
                for(int counter = 0; counter <= mailListArray.length -1; counter++){
                    mailListArray[counter] = mailListArray[counter].trim();
                }
            }

            if(mailListArray != null){
                String sendStr = sendMail.prepareTemplateBeforeSendMail(userService ,selectedCampaign, mailListArray, standardCampaign, "", "Fr");
                request.setAttribute("sendStatus", "sent");
            }else{
                logger.info("Debug: There is no user email for sending this Campaign.");
            }
            //

            //end of Sending

            //
            CampaignForm campaignForm = (CampaignForm) form ;
            campaignForm.setCampaignId(request.getParameter("standardCampaignId").toString());
            int id  = Integer.valueOf(request.getParameter("standardCampaignId").toString()).intValue();
            //get mail list by this condition of standard campaign
            List<CampaignEmailTemplate> mailListResult = new ArrayList();
            mailListResult = standardCampaign.getUserEmailByStandardCampaign(selectedCampaign, "");

            request.setAttribute("resultStandardCampaignList", mailListResult);

            request.setAttribute("standardCampaignId", id);


        } catch (Exception e) {
            request.setAttribute("message", "Error during retrieving data");
            request.setAttribute("sendStatus", "not sent");
            e.printStackTrace();
            return null;
        }
        return mapping.findForward("gotoShowResultStandardCampaign");
    }
    //---
    public ActionForward showStandardCampaign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) {
        try{

            int campaignId = Integer.parseInt(request.getParameter("campaignId").toString());
            String groupId = request.getParameter("groupId").toString();
            String menuId = request.getParameter("menuId").toString();
            String userId = request.getParameter("userId").toString();
            String lang = request.getParameter("lang").toString();
            //Get selected Standard campaign
            List<StandardCampaign> list = new ArrayList();
            list = standardCampaign.getStandardCampaignById(campaignId);
            StandardCampaign selectedCampaign = new StandardCampaign();
            selectedCampaign = list.get(0);
            //

            SendMail sendMail = new SendMail();
            sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
            sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
            String campaignResult = sendMail.prepareTemplateForPrintInWeb(selectedCampaign, String.valueOf(groupId), String.valueOf(menuId),lang, userId);
            request.setAttribute("htmlContent", campaignResult );


        } catch (Exception e) {
            request.setAttribute("message", "Error during getting parameters");
            e.printStackTrace();
            return null;
        }
        return mapping.findForward("gotoShowTemplateStandardCampaign");
    }

    public ActionForward unsubUserForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        CampaignForm unsubUserCampaignFrm = (CampaignForm) form ;
        request.setAttribute("userId", request.getParameter("userId").toString());
        request.setAttribute("lang",request.getParameter("lang").toString());
        String userId = request.getParameter("userId").toString().trim();
        User myUser = userService.findById(Integer.parseInt(request.getParameter("userId").toString()));
        request.setAttribute("emailAddress",myUser.getEmail());
        return mapping.findForward("gotoSubscribeUserForm");
    }

    public ActionForward unsubUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) {
        try{
            CampaignForm unsubUserCampaignFrm = (CampaignForm) form ;

            String userId = unsubUserCampaignFrm.getUserId();
            String reason = unsubUserCampaignFrm.getReason();
            request.setAttribute("emailAddress",unsubUserCampaignFrm.getEmail());

            //Get user by id
            int resultInt = standardCampaign.saveChangedSubscribeUserCampaign(userId, reason);

            if(resultInt >= 0){
                request.setAttribute("unsubscribed","submited");
            }
            else{
                request.setAttribute("unsubscribed","faild");
            }

        } catch (Exception e) {
            request.setAttribute("message", "Error during getting parameters");
            e.printStackTrace();
            return null;
        }
        return mapping.findForward("gotoSubscribeUserForm");
    }


    public ActionForward generateCampaignFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) throws IOException {
        try
        {
            int campaignId = 0;
            String langStr = request.getParameter("lang");
            List<StandardCampaign> cmpList = new ArrayList();
            if(request.getParameter("standardCampaignId") != null){
                campaignId = Integer.parseInt(request.getParameter("standardCampaignId").toString());
            }

            cmpList = standardCampaign.getStandardCampaignById(campaignId);
            StandardCampaign selectedStandardCampaign = new StandardCampaign();
            selectedStandardCampaign = cmpList.get(0);

            List<CampaignEmailTemplate> cmpMailListResult = new ArrayList();
            cmpMailListResult = standardCampaign.getUserEmailByStandardCampaign(selectedStandardCampaign, langStr);
            String mailLstStr = "";
            String usrIdLstStr = "";
            String usrLangLstStr = "";
            for (int myInt1 = 0; myInt1 < cmpMailListResult.size(); myInt1++){
                mailLstStr = cmpMailListResult.get(myInt1).getEmailAddress() + "," + mailLstStr;
                usrIdLstStr = cmpMailListResult.get(myInt1).getUserId() + "," + usrIdLstStr;
                usrLangLstStr = cmpMailListResult.get(myInt1).getLang() + "," + usrLangLstStr;
            }

            //Clean the text of emails
            String[] mailListArray = null;
            String[] userIdListArray = null;
            String[] userLangListArray = null;
            mailLstStr = mailLstStr.trim();
            usrIdLstStr = usrIdLstStr.trim();
            usrLangLstStr = usrLangLstStr.trim();

            if(!mailLstStr.equals("")){
                if(mailLstStr.substring(mailLstStr.length()-2,mailLstStr.length()-1).equals(",")){
                    mailLstStr = mailLstStr.substring(0,mailLstStr.length()-2);
                    usrIdLstStr = usrIdLstStr.substring(0,usrIdLstStr.length()-2);
                    usrLangLstStr = usrLangLstStr.substring(0,usrLangLstStr.length()-2);
                }
                mailListArray = mailLstStr.split(",");
                userIdListArray = usrIdLstStr.split(",");
                userLangListArray = usrLangLstStr.split(",");

                for(int counter = 0; counter <= mailListArray.length -1; counter++){
                    mailListArray[counter] = mailListArray[counter].trim();
                    userIdListArray[counter] = userIdListArray[counter].trim();
                    userLangListArray[counter] = userLangListArray[counter].trim();
                }
            }
            StringBuffer sb = null;
            if(request.getParameter("fileType").equals("csv")){
                Date myDate = new Date();
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String fileNameWithTime = "dbpCampiagnEmailList"+ sdfDate.format(myDate).toString();
                response.setContentType("application/ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=dbpCampiagnEmailList"+langStr+".csv");
                ServletOutputStream out = response.getOutputStream();
                sb = generateEmailListCsvFileBuffer(mailListArray, userIdListArray, userLangListArray);
            }else if(request.getParameter("fileType").equals("html")){
                Date myDate = new Date();
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String fileNameWithTime = "dbpCampiagnEmailList"+ sdfDate.format(myDate).toString();
                response.setContentType("text/html");
                response.setHeader("Content-Disposition", "attachment; filename=dbpTemplateCampiagnHTML"+langStr+".html");
                sb = generateHTMLFileBuffer(mailListArray, selectedStandardCampaign, langStr);
            }
            ServletOutputStream out = response.getOutputStream();

            InputStream in = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            byte[] outputByte = new byte[256];
            //copy binary contect to output stream
            out.write(sb.toString().getBytes("UTF-8"));
            /*while(in.read(outputByte, 0, 256) != -1)
            {
                out.write(outputByte, 0, 256);
            }*/
            in.close();
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static StringBuffer generateHTMLFileBuffer(String[] mailListArray, StandardCampaign selectedStandardCampaign, String lang)
    {
        logger.debug("Debug: Call generateHTMLFileBuffer");
        SendMail sendMail = new SendMail();
        sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
        sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
        String sendStr  = sendMail.prepareTemplateBeforeSendMail(userService ,selectedStandardCampaign, mailListArray, standardCampaign, "html", lang);
        StringBuffer writer = new StringBuffer();
        writer.append(sendStr);
        return writer;
    }

    private static StringBuffer generateEmailListCsvFileBuffer(String[] mailListArray, String[] userIdListArray, String[] userLangListArray)
    {
        StringBuffer writer = new StringBuffer();
        writer.append("Email, UserId, Lang");
        writer.append('\n');
        for(int counter = 0; counter <= mailListArray.length -1; counter++){
            writer.append(mailListArray[counter] + ", " + userIdListArray[counter] + ", " + userLangListArray[counter]);
            writer.append('\n');
        }
        return writer;
    }




}
