package com.sefryek.doublepizza.dto.web.backend.campaign;

import com.sefryek.common.LogMessages;
import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.DateUtil;
import com.sefryek.common.util.ServiceFinder;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.CurrencyUtils;
import com.sefryek.doublepizza.dao.DollarScheduleDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.IStandardCampaign;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.service.implementation.UserServiceImpl;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.*;

import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import freemarker.template.Template;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;


/**
 * User: Saeid AmanZadeh
 * Date: 15/5/14
 * Time: 9:53 AM
 */
public class SendMail {

    private static Logger logger = Logger.getLogger(DollarScheduleDAO.class);
    //private MailSender myMailSender;
    private JavaMailSender mailSender;
    public Configuration freemarkerConfiguration;
    private List<CampaignEmailTemplate> campaignEmailTemplates;
    private IStandardCampaign standardCampaignService;
    private WebApplicationContext context;
    private IUserService userService;


    private String[] mailList;
    private String from;
    private String to;
    private String subject;
    private String msg;
    private Map templateVars;


    public List<CampaignEmailTemplate> getCampaignEmailTemplates() {
        return campaignEmailTemplates;
    }

    public void setCampaignEmailTemplates(List<CampaignEmailTemplate> campaignEmailTemplates) {
        this.campaignEmailTemplates = campaignEmailTemplates;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String[] getMailList() {
        return mailList;
    }

    public void setMailList(String[] mailList) {
        this.mailList = mailList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getTemplateVars() {
        return templateVars;
    }

    public void setTemplateVars(Map templateVars) {
        this.templateVars = templateVars;
    }

    public IStandardCampaign getStandardCampaignService() {
        return standardCampaignService;
    }

    public void setStandardCampaignService(IStandardCampaign standardCampaignService) {
        this.standardCampaignService = standardCampaignService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    public Configuration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

   public static String correctFrenchHTMLChars(String str1){
       String finalizeStr;
       finalizeStr = str1.replaceAll("À", "&#192;");
       finalizeStr = finalizeStr.replaceAll("à", "&#224;");
       finalizeStr = finalizeStr.replaceAll("Â", "&#194;");
       finalizeStr = finalizeStr.replaceAll("â", "&#226;");
       finalizeStr = finalizeStr.replaceAll("Æ", "&#198;");
       finalizeStr = finalizeStr.replaceAll("æ", "&#230;");
       finalizeStr = finalizeStr.replaceAll("Ç", "&#199;");
       finalizeStr = finalizeStr.replaceAll("ç", "&#231;");
       finalizeStr = finalizeStr.replaceAll("È", "&#200;");
       finalizeStr = finalizeStr.replaceAll("è", "&#232;");
       finalizeStr = finalizeStr.replaceAll("É", "&#201;");
       finalizeStr = finalizeStr.replaceAll("é", "&#233;");
       finalizeStr = finalizeStr.replaceAll("Ê", "&#202;");
       finalizeStr = finalizeStr.replaceAll("ê", "&#234;");
       finalizeStr = finalizeStr.replaceAll("Ë", "&#203;");
       finalizeStr = finalizeStr.replaceAll("ë", "&#235;");
       finalizeStr = finalizeStr.replaceAll("Î", "&#206;");
       finalizeStr = finalizeStr.replaceAll("î", "&#238;");
       finalizeStr = finalizeStr.replaceAll("Ï", "&#207;");
       finalizeStr = finalizeStr.replaceAll("ï", "&#239;");
       finalizeStr = finalizeStr.replaceAll("Ô", "&#212;");
       finalizeStr = finalizeStr.replaceAll("ô", "&#244;");
       finalizeStr = finalizeStr.replaceAll("Œ", "&#140;");
       finalizeStr = finalizeStr.replaceAll("œ", "&#156;");
       finalizeStr = finalizeStr.replaceAll("Ù", "&#217;");
       finalizeStr = finalizeStr.replaceAll("ù", "&#249;");
       finalizeStr = finalizeStr.replaceAll("Û", "&#219;");
       finalizeStr = finalizeStr.replaceAll("û", "&#251;");
       finalizeStr = finalizeStr.replaceAll("Ü", "&#220;");
       finalizeStr = finalizeStr.replaceAll("ü", "&#252;");
       finalizeStr = finalizeStr.replaceAll("«", "&#171;");
       finalizeStr = finalizeStr.replaceAll("»", "&#187;");
       finalizeStr = finalizeStr.replaceAll("€", "&#128;");
       finalizeStr = finalizeStr.replaceAll("₣", "&#8355;");
       return finalizeStr;
   }
    public void doCheckCampaignAndSend(IStandardCampaign standardCampaignService, IUserService userService)
    {
        logger.info("Debug: Start of Checking Campaign And Send");

        //get list of campaign
        List<StandardCampaign> readylist = new ArrayList();
        List<StandardCampaign> sendinglist = new ArrayList();
        readylist = standardCampaignService.getStandardCampaignByNow();
        sendinglist = standardCampaignService.getSendingStandardCampaignByNow();
        if(!(sendinglist.size() > 0) && readylist.size() > 0){
            for(int campaignCnt = 0; campaignCnt < readylist.size(); campaignCnt++){
                StandardCampaign selectedStandardCampaign = new StandardCampaign();
                selectedStandardCampaign = readylist.get(campaignCnt);

                logger.info("Debug: Sending StandardCampaign with this image from "+selectedStandardCampaign.getImageUrl());

                List<CampaignEmailTemplate> mailListResult = new ArrayList();
                mailListResult = standardCampaignService.getUserEmailByStandardCampaign(selectedStandardCampaign, "");
                String mailLstStr = "";
                for (int myInt1 = 0; myInt1 < mailListResult.size(); myInt1++){
                    mailLstStr = mailListResult.get(myInt1).getEmailAddress() + "," + mailLstStr;
                }

                //Clean the text of emails
                String[] mailListArray = null;
                mailLstStr = mailLstStr.trim();
                if(!mailLstStr.equals("")){
                    if(mailLstStr.substring(mailLstStr.length()-2,mailLstStr.length()-1).equals(","))
                        mailLstStr = mailLstStr.substring(0,mailLstStr.length()-2);

                    mailListArray = mailLstStr.split(",");
                    for(int counter = 0; counter <= mailListArray.length -1; counter++){
                        mailListArray[counter] = mailListArray[counter].trim();
                    }
                }

                if(mailListArray != null){
                    logger.info("Debug: I'm ready to send Campaign[" + selectedStandardCampaign.getStandardcampaign_id() +"] to " + mailListArray.length + " number of emails");
                    if(prepareTemplateBeforeSendMail(userService, selectedStandardCampaign, mailListArray, standardCampaignService, "", "Fr").equals("1")){
                        //Changing Status to 'Sent'
                        logger.info("Debug: Changing Status to 'sent'");
                        selectedStandardCampaign.setStatus("sent");
                        standardCampaignService.saveChangedStandardCampaign(selectedStandardCampaign);
                        logger.info("Debug: End of Changing Status to 'sent' ");
                    }else {
                        logger.info("Debug: status of Campaign[" + selectedStandardCampaign.getStandardcampaign_id() +"] remained Pause.");
                    }
                }else{
                    logger.info("Debug: The Campaign width Id " + selectedStandardCampaign.getStandardcampaign_id() + ", does not include any user.");
                }
            }
        }else{
            if(sendinglist.size() > 0)
                logger.info("Debug: There is a Sending Campaign Now, so we should wait.");
            if(readylist.size() < 1)
                logger.info("Debug: There is No ready Campaign to send.");
        }
        logger.info("Debug: End of Checking Campaign And Send.");
    }

    public String prepareTemplateBeforeSendMail(IUserService myUserService, StandardCampaign selectedCampaign, String[] mailList, IStandardCampaign standardCampaignService, String exportMod, String exLang) {
        logger.info("Debug: Start of PrepareTemplateBeforeSendMail in SendMail.java");

        //Changing Status to 'Sent'
        if(exportMod.equals("")){
            logger.info("Debug: Changing Status to 'sending'");
            selectedCampaign.setStatus("sending");
            standardCampaignService.saveChangedStandardCampaign(selectedCampaign);
            logger.info("Debug: End of Changing Status to 'sending' ");
        }
        //Champaign is sending now so its status is Sending

        String foodName = "";
        String foodDescription = "";
        String price = "";
        String orderNowURLStr = "";
        String[] filterEmails = new String[]{"saeid.amanzadeh@sefr-yek.com", "sefryek_test@yahoo.com","sefryek.test@gmail.com","saeed@sefr-yek.com","haniye.jalaliyar@sefr-yek.com","mostafa.jamshid@sefr-yek.com", "fatemeh@sefr-yek.com", "sefryek.test@hotmail.com"};
        Boolean isValidMailAddress = false;
        String[] menuIdGroupId = selectedCampaign.getMenu_id().split("/");
        BigDecimal prico = BigDecimal.valueOf(0);
        Category category = null;

        //

        Integer menuItemId = Integer.parseInt(menuIdGroupId[0]);
        Integer categoryIdint = Integer.parseInt(menuIdGroupId[1]);
        Integer groupIdint = Integer.parseInt(menuIdGroupId[2]);

      //
        String preId = "";
        if((menuItemId>0)&&(menuItemId<10))preId="000"+menuItemId;
        if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
        if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
        //

        CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(menuItemId.toString(), groupIdint.toString());
        category = InMemoryData.getCombinedParentCategory(combinedMenuItem);
        if(combinedMenuItem != null){
            //COMBINED
            //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId=0&combinedId="+combinedMenuItem.getProductNo().toString()+"&type=COMBINED&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+groupIdint.toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
            orderNowURLStr = "/food/0/0/"+combinedMenuItem.getProductNo().toString()+"/COMBINED/"+category.getId().toString()+"/"+menuItemId.toString()+"/"+groupIdint.toString()+"/0/0";
            if(exLang.equals("Fr")){
                foodName = combinedMenuItem.getName(Locale.FRANCE);
                foodDescription = combinedMenuItem.getDescription(Locale.FRANCE);
            }else{
                foodName = combinedMenuItem.getName(Locale.ENGLISH);
                foodDescription = combinedMenuItem.getDescription(Locale.ENGLISH);
            }

            price = CurrencyUtils.doubleRoundingFormat( combinedMenuItem.getPrice()).toString();
        }else{
            category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, menuItemId);
            if(category != null){
               //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId=0&combinedId="+category.getId()+"&type=CATEGORY&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+groupIdint.toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
                orderNowURLStr = "/food/"+category.getId().toString()+"/0/0/CATEGORY/"+category.getId().toString()+"/"+menuItemId.toString()+"/"+groupIdint.toString()+"/0/0";
                if(exLang.equals("Fr")){
                    foodName = category.getName(Locale.FRANCE);
                    foodDescription = category.getDescription(Locale.FRANCE);
                }else{
                    foodName = category.getName(Locale.ENGLISH);
                    foodDescription = category.getDescription(Locale.ENGLISH);
                }
                if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                    prico=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }   else {
                    prico=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }
                price= CurrencyUtils.doubleRoundingFormat(prico).toString();
            }
        }
        if(category == null && combinedMenuItem == null){
            MenuSingleItem singleItem= InMemoryData.findMenuSingleItemByIdAndGroupId(preId.toString(), "");
            category = InMemoryData.getSingleParentCategory(singleItem);
            if(singleItem != null){
                //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId="+singleItem.getProductNo().toString()+"&combinedId=0&type=SINGLEMENUITEM&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+singleItem.getGroupId().toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
                orderNowURLStr = "/food/0/"+singleItem.getId().toString()+"/0/SINGLEMENUITEM/"+category.getId().toString()+"/"+menuItemId.toString()+"/null/0/0";
                if(exLang.equals("Fr")){
                    foodName = singleItem.getName(Locale.FRANCE);
                    foodDescription = singleItem.getDescription(Locale.FRANCE);
                }else{
                    foodName = singleItem.getName(Locale.ENGLISH);
                    foodDescription = singleItem.getDescription(Locale.ENGLISH);
                }
                price = CurrencyUtils.doubleRoundingFormat(singleItem.getPrice()).toString();
            }
        }
        logger.debug("Debug: ");
        logger.debug("Debug: "+orderNowURLStr);
        logger.debug("Debug: ");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        if(!exportMod.equals("html")){
                for(int int1 = 0; int1 <= mailList.length - 1; int1++){
                    isValidMailAddress = true;
                   /* for(int int2 = 0; int2 <= filterEmails.length-1; int2++){
                        if(mailList[int1].equals(filterEmails[int2])){
                            isValidMailAddress = true;
                        }
                    }*/

                    if(isValidMailAddress){
                        List<StandardCampaign> currentSelectedCampaignLst = standardCampaignService.getStandardCampaignById(selectedCampaign.getStandardcampaign_id());
                            if(currentSelectedCampaignLst.get(0).getStatus().equals("sending")){
                                try {
                                    Thread.sleep(applicationConfig.getEmailDelaySend());
                                    String lang = "";
                                    //get User info by email
                                    User myUser = myUserService.findByEmail(mailList[int1]);
                                    //
                                    Map templateVars = new HashMap();
                                    //fill the template by your elements

                                    templateVars.put("foodTitle", correctFrenchHTMLChars(foodName));
                                    templateVars.put("orderNowURLStr", orderNowURLStr);
                                    templateVars.put("foodDesc", correctFrenchHTMLChars(foodDescription));
                                    templateVars.put("price", price);
                                    String campaignId = String.valueOf(selectedCampaign.getStandardcampaign_id());
                                    templateVars.put("campaignId", campaignId.replaceAll(",",""));
                                    templateVars.put("menuId", menuIdGroupId[0].toString());
                                    templateVars.put("groupId", menuIdGroupId[1].toString());
                                    templateVars.put("httpPath", ApplicationConfig.httpFilePath);
                                    if(myUser != null){
                                        if(myUser.getLang().equals("En")){
                                            lang = "En";
                                            templateVars.put("foodPic",  selectedCampaign.getImageUrlEn());
                                            templateVars.put("lang", "Fr");
                                            templateVars.put("dpdollarImg", "dpdollarEn.jpg");
                                            templateVars.put("subjectCampaign", correctFrenchHTMLChars(selectedCampaign.getCampaign_title_en()));
                                            templateVars.put("item_html", selectedCampaign.getItemHtmlEn().replaceAll("httpPath", ApplicationConfig.httpFilePath));
                                        }else{
                                            lang = "Fr";
                                            templateVars.put("foodPic",  selectedCampaign.getImageUrl());
                                            templateVars.put("lang", "En");
                                            templateVars.put("dpdollarImg", "dpdollarFr.jpg");
                                            templateVars.put("subjectCampaign", correctFrenchHTMLChars(selectedCampaign.getCampaign_title_fr()));
                                            templateVars.put("item_html", selectedCampaign.getItemHtmlFr().replaceAll("httpPath", ApplicationConfig.httpFilePath));
                                        }
                                        templateVars.put("userId", String.valueOf(myUser.getId()).replaceAll(",", ""));


                                    }else{
                                        //if your user was not in the Db we set 1 as userId
                                        lang = "Fr";
                                        templateVars.put("lang", "En");
                                        templateVars.put("foodPic",  selectedCampaign.getImageUrl());
                                        templateVars.put("dpdollarImg", "dpdollarFr.jpg");
                                        templateVars.put("subjectCampaign", correctFrenchHTMLChars(selectedCampaign.getCampaign_title_en()));
                                        templateVars.put("userId","1");
                                        templateVars.put("item_html", selectedCampaign.getItemHtmlFr().replaceAll("httpPath", ApplicationConfig.httpFilePath));
                                    }
                                    //
                                     String resultStr = prepareMsgBeforeSendCampaignMail(Constant.MAIL_SENDER_USER_NAME,
                                                mailList[int1],
                                                selectedCampaign.getSubjectFr(),
                                                templateVars, lang, "");

                                    DateTime myDate = new DateTime();
                                    String timeStr = myDate.getHourOfDay() + ":" + myDate.getMinuteOfHour() + ":" + myDate.getSecondOfMinute();
                                    logger.debug("Debug: Campaign[" + currentSelectedCampaignLst.get(0).getStandardcampaign_id() + "] has been sent to the E-mail Number " + ( int1 + 1 )+ " [" + mailList[int1] + "] Successfully! in "+timeStr);

                                    //
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ServiceException e) {
                                    e.printStackTrace();
                                } catch (DAOException e) {
                                    e.printStackTrace();
                                }
                        }else{
                                logger.debug("Debug: Sending Campaign[" + currentSelectedCampaignLst.get(0).getStandardcampaign_id() + "] to emails has been stopped because of changing campaign status during sending it !");
                                return "-1";
                            }

                }else{
                        logger.debug("Debug: Email Number " + ( int1 + 1 )+ " [" + mailList[int1] + "] is not Valid Email Address!");
                    }
                }
        }else if(exportMod.equals("html")){
            Map templateVars = new HashMap();
            //fill the template by your elements

            templateVars.put("foodTitle", correctFrenchHTMLChars(foodName));
            templateVars.put("orderNowURLStr", orderNowURLStr);
            templateVars.put("foodDesc", correctFrenchHTMLChars(foodDescription));
            templateVars.put("price", price);
            String campaignId = String.valueOf(selectedCampaign.getStandardcampaign_id());
            templateVars.put("campaignId", campaignId.replaceAll(",",""));
            templateVars.put("menuId", menuIdGroupId[0].toString());
            templateVars.put("groupId", menuIdGroupId[1].toString());
            templateVars.put("httpPath", ApplicationConfig.httpFilePath);

            if(exLang.equals("En")){
                    templateVars.put("lang", "Fr");
                    templateVars.put("foodPic", selectedCampaign.getImageUrlEn());
                    templateVars.put("dpdollarImg", "dpdollarEn.jpg");
                    templateVars.put("subjectCampaign", correctFrenchHTMLChars(selectedCampaign.getCampaign_title_en()));
                    templateVars.put("item_html", selectedCampaign.getItemHtmlEn().replaceAll("httpPath", ApplicationConfig.httpFilePath));
            }else{
                    templateVars.put("lang", "En");
                    templateVars.put("foodPic", selectedCampaign.getImageUrl());
                    templateVars.put("dpdollarImg", "dpdollarFr.jpg");
                    templateVars.put("subjectCampaign", correctFrenchHTMLChars(selectedCampaign.getCampaign_title_fr()));
                    templateVars.put("item_html", selectedCampaign.getItemHtmlFr().replaceAll("httpPath", ApplicationConfig.httpFilePath));
            }
            //

            return prepareMsgBeforeSendCampaignMail(Constant.MAIL_SENDER_USER_NAME, "", "", templateVars, exLang, "html");

        }
     return "1";
    }

    public String prepareMsgBeforeSendCampaignMail(String from, String to, String subject, Map templateVars, String lang, String exportMod) {
        logger.debug("Debug: Call prepareMsgBeforeSendCampaignMail");

        String templatePath = ApplicationConfig.campaignTemplateFilepath + File.separator;
        final StringWriter htmlWriter = new StringWriter();

        //logger.debug("Debug: TemplatePath : "+ templatePath + "CampaignTemplate"+lang+".flt");
        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));

            Template htmlTemplate = freemarkerConfiguration.getTemplate("CampaignTemplate"+lang+".flt");

            //Prepare Content by the template
            htmlTemplate.process(templateVars, htmlWriter);
            if(exportMod.equals("html")){
             return htmlWriter.toString();

            }else {
            //Prepare the Message Obj
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from.trim());
            message.setTo(to.trim());
            message.setSubject(subject);
            message.setText(htmlWriter.toString());
            logger.debug("Debug: We are in Phase 1 Test so we don't send any mail now!");
            //sendMail(message);
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file or directory!", e);
        } catch (TemplateException e) {
            throw new MailPreparationException("Can't generate HTML subscription mail", e);
        }
        return "";
    }

    public void prepareMsgBeforeSendForgottenPassMail(String lang, String from, String to, String subject, Map templateVars) {

        logger.debug("Call prepareMsgBeforeSendForgottenPassMail");

        String templatePath = ApplicationConfig.campaignTemplateFilepath + File.separator;
        final StringWriter htmlWriter = new StringWriter();

        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
            Template htmlTemplate = null;
            if(lang.equals("En")){
                htmlTemplate = freemarkerConfiguration.getTemplate("forgottenPassTemplateEn.flt");
            }else if(lang.equals("Fr")){
                htmlTemplate = freemarkerConfiguration.getTemplate("forgottenPassTemplateFr.flt");
            }
            //Prepare Content by the template
            htmlTemplate.process(templateVars, htmlWriter);

            //Prepare the Message Obj
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from.trim());
            message.setTo(to.trim());
            message.setSubject(subject);
            message.setText(htmlWriter.toString());
            sendMail(message);

        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file or directory!", e);
        } catch (TemplateException e) {
            throw new MailPreparationException("Can't generate HTML subscription mail", e);
        }

    }

    public void prepareMailSendCatering(String from, String to, String subject, Map templateVars) {

        logger.debug("Call prepareMailSendCatering");

        String templatePath = ApplicationConfig.campaignTemplateFilepath + File.separator;
        final StringWriter htmlWriter = new StringWriter();

        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
            Template htmlTemplate = null;
            htmlTemplate = freemarkerConfiguration.getTemplate("cateringTemplate.flt");

            //Prepare Content by the template
            htmlTemplate.process(templateVars, htmlWriter);

            //Prepare the Message Obj
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from.trim());
            message.setTo(to.trim());
            message.setSubject(subject);
            message.setText(htmlWriter.toString());
            sendMail(message);

        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file or directory!", e);
        } catch (TemplateException e) {
            throw new MailPreparationException("Can't generate HTML catering mail", e);
        }

    }

    public void prepareMailSendResetPassLinkTest(String lang, String from, String to, String subject, Map templateVars) {

        logger.debug("Call prepareMailSendResetPassLink");

        String templatePath = ApplicationConfig.campaignTemplateFilepath + File.separator;
        final StringWriter htmlWriter = new StringWriter();

        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
            Template htmlTemplate = null;
            if(lang.equals("En")){
                htmlTemplate = freemarkerConfiguration.getTemplate("resetPassEn.flt");
            }else if(lang.equals("Fr")){
                htmlTemplate = freemarkerConfiguration.getTemplate("resetPassFr.flt");
            }
            //Prepare Content by the template
            htmlTemplate.process(templateVars, htmlWriter);

            //Prepare the Message Obj
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from.trim());
            message.setTo(to.trim());
            message.setSubject(subject);
            message.setText(htmlWriter.toString());
            sendMail(message);

        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file or directory!", e);
        } catch (TemplateException e) {
            throw new MailPreparationException("Can't generate HTML subscription mail", e);
        }

    }


    //Saeid AmanZadeh
    //Main method to send a HTML content mail
    //All of Campaigns and other types of emails are using this unique method to send their mails
    //Create HTML and send it via spring java mail

    public void sendMail(final SimpleMailMessage message) {
        logger.debug("Debug: Final method in order to send  mail ");
        ApplicationConfig applicationConfig = new ApplicationConfig();

       //We are in testing phase of sending mails so this method works only in Dev or Test Mode

       // if(applicationConfig.getRunMode().equals("DEV") || applicationConfig.getRunMode().equals("TEST")){
            MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                InternetAddress[] addressTo = new InternetAddress[message.getTo().length];
                for (int i = 0; i < message.getTo().length; i++)
                    addressTo[i] = new InternetAddress(message.getTo()[i]);

                mimeMessage.addRecipients(Message.RecipientType.TO, addressTo);
                mimeMessage.setFrom(new InternetAddress(message.getFrom(), "Double Pizza"));

                //Extract the Bcc-recipients and assign them to the message;
                if(message.getCc() != null && message.getCc().length > 0 ){
                    InternetAddress[] addressCC = new InternetAddress[message.getCc().length];
                    for (int i = 0; i < message.getCc().length; i++)
                        addressCC[i] = new InternetAddress(message.getCc()[i]);

                    mimeMessage.addRecipients(Message.RecipientType.CC, addressCC);
                }

                // Extract the Bcc-recipients and assign them to the message;
                if(message.getBcc() != null && message.getBcc().length>0 ){
                    InternetAddress[] addressBCC = new InternetAddress[message.getBcc().length];
                    for (int i = 0; i <message.getBcc().length; i++)
                        addressBCC[i] = new InternetAddress(message.getBcc()[i]);

                    mimeMessage.addRecipients(Message.RecipientType.BCC, addressBCC);
                }

                // Subject field
                mimeMessage.setSubject(message.getSubject(),"UTF-8");

                // Create the Multipart to be added the parts to
                Multipart mp = new MimeMultipart();

                // Create and fill the first message part
                {
                    MimeBodyPart mbp = new MimeBodyPart();
                    mimeMessage.setHeader("Content-Type","text/html;charset=UTF-8");
                    mbp.setContent(message.getText(), "text/html;charset=UTF-8");
                    // Attach the part to the multipart;
                    mp.addBodyPart(mbp);
                }

                // Add the Multipart to the message
                mimeMessage.setContent(mp);
                // Set the Date: header
                mimeMessage.setSentDate(new Date());
                // Send the message;
                //logger.info(LogMessages.START_OF_METHOD + "sendMail in SendMail.java ");

            }
        };

        try {
            mailSender.send(preparator);
        }

        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            logger.info("Debug : Error getMostSpecificCause :" + ex.getMostSpecificCause().toString());
            logger.info("Debiug End of Exception Error");
        }
        //}
    }

    //Saeid AmanZadeh
    public String prepareTemplateForPrintInWeb(StandardCampaign selectedCampaign, String groupId, String menuId, String lang, String userId) throws IOException, TemplateException {

        logger.info(LogMessages.START_OF_METHOD + "prepareTemplateForPrintInWeb in SendMail.java ");

        String[] menuIdGroupId = selectedCampaign.getMenu_id().split("/");
        String orderNowURLStr = "";

        Integer menuItemId = Integer.parseInt(menuIdGroupId[0]);
        Integer categoryIdint = Integer.parseInt(menuIdGroupId[1]);
        Integer groupIdint = Integer.parseInt(menuIdGroupId[2]);

        //Saeid AmanZadeh
        //Deceting which Type of Foods is in selected campaign
        String foodName = "";
        String foodDescription = "";
        String price = "";
        BigDecimal prico = BigDecimal.valueOf(0);

        //
        String preId = "";
        if((menuItemId>0)&&(menuItemId<10))preId="000"+menuItemId;
        if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
        if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
        //
        CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(menuItemId.toString(), groupIdint.toString());
        Category category = InMemoryData.getCombinedParentCategory(combinedMenuItem);

        if(combinedMenuItem != null){
            //COMBINED
            //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId=0&combinedId="+combinedMenuItem.getProductNo().toString()+"&type=COMBINED&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+groupIdint.toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
            orderNowURLStr = "/food/0/0/"+combinedMenuItem.getProductNo().toString()+"/COMBINED/"+category.getId().toString()+"/"+menuItemId.toString()+"/"+groupIdint.toString()+"/0/0";
            if(lang.equals("Fr"))
                foodName = combinedMenuItem.getName(Locale.FRENCH);
            else
                foodName = combinedMenuItem.getName(Locale.ENGLISH);
            foodDescription = combinedMenuItem.getDescription(Locale.ENGLISH);
            price = CurrencyUtils.doubleRoundingFormat(combinedMenuItem.getPrice()).toString();
        }else {
            category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, menuItemId);
            if(category != null){
                //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId=0&combinedId="+category.getId()+"&type=CATEGORY&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+groupIdint.toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
                orderNowURLStr = "/food/"+category.getId().toString()+"/0/0/CATEGORY/"+category.getId().toString()+"/"+menuItemId.toString()+"/"+groupIdint.toString()+"/0/0";

                if(lang.equals("Fr"))
                    foodName = category.getName(Locale.FRENCH);
                else
                    foodName = category.getName(Locale.ENGLISH);

                foodDescription = category.getDescription(Locale.ENGLISH);
                if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                    prico=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }   else {
                    prico=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }
                price= CurrencyUtils.doubleRoundingFormat(prico).toString();
            }
        }
        if(category == null && combinedMenuItem == null){
            MenuSingleItem singleItem= InMemoryData.findMenuSingleItemByIdAndGroupId(menuItemId.toString(), groupIdint.toString());
            category = InMemoryData.getSingleParentCategory(singleItem);
            if(singleItem != null){
                //orderNowURLStr = "/frontend.do?operation=goToCustomizePage&singleId="+singleItem.getProductNo().toString()+"&combinedId=0&type=SINGLEMENUITEM&catId="+category.getId().toString()+"&menuItemId="+menuIdint.toString()+"&groupId="+singleItem.getGroupId().toString()+"&orderNumber=0&customizing=0&menuName=Menu&menuType=menu&clUrl=1";
                orderNowURLStr = "/food/0/"+singleItem.getId().toString()+"/0/SINGLEMENUITEM/"+category.getId().toString()+"/"+menuItemId.toString()+"/null/0/0";
                if(lang.equals("Fr"))
                    foodName = singleItem.getName(Locale.FRENCH);
                else
                    foodName = singleItem.getName(Locale.ENGLISH);
                foodDescription = singleItem.getDescription(Locale.ENGLISH);
                price = CurrencyUtils.doubleRoundingFormat(singleItem.getPrice()).toString();
            }
        }
        ///

        Map templateVars = new HashMap();

        //fill the template by your elements

        templateVars.put("foodTitle", correctFrenchHTMLChars(foodName));
        templateVars.put("foodDesc", correctFrenchHTMLChars(foodDescription));
        templateVars.put("price", price);
        templateVars.put("orderNowURLStr", orderNowURLStr);
        String campaignId = String.valueOf(selectedCampaign.getStandardcampaign_id());
        templateVars.put("campaignId", campaignId.replaceAll(",",""));
        templateVars.put("menuId", menuIdGroupId[0]);
        templateVars.put("groupId", menuIdGroupId[1]);
        templateVars.put("userId", String.valueOf(userId).replaceAll(",", ""));


        if(lang.equals("En")){
            templateVars.put("foodPic", selectedCampaign.getImageUrlEn());
            templateVars.put("lang", "Fr");
            templateVars.put("dpdollarImg", "dpdollarEn.jpg");
            templateVars.put("subjectCampaign",correctFrenchHTMLChars(selectedCampaign.getCampaign_title_en()));
            templateVars.put("item_html", selectedCampaign.getItemHtmlEn().replaceAll("httpPath", ApplicationConfig.httpFilePath));
        }
        else if(lang.equals("Fr")){
            templateVars.put("foodPic", selectedCampaign.getImageUrl());
            templateVars.put("lang", "En");
            templateVars.put("dpdollarImg", "dpdollarFr.jpg");
            templateVars.put("subjectCampaign",correctFrenchHTMLChars(selectedCampaign.getCampaign_title_fr()));
            templateVars.put("item_html", selectedCampaign.getItemHtmlFr().replaceAll("httpPath", ApplicationConfig.httpFilePath));
        }

        templateVars.put("httpPath", ApplicationConfig.httpFilePath);
        //

        //prepare HTML
        String templatePath = ApplicationConfig.campaignTemplateFilepath + File.separator;
        final StringWriter htmlWriter = new StringWriter();

        freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));

        Template htmlTemplate = freemarkerConfiguration.getTemplate("CampaignTemplateWeb"+lang+".flt");

        //Prepare Content by the template
        htmlTemplate.process(templateVars, htmlWriter);


        return htmlWriter.toString();
    }

}
