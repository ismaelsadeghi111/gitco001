package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.enums.CampaignType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: sepideh
 * Date: 1/23/14
 * Time: 12:59 PM
 */
    public class CampaignMailRunnable implements Runnable {
    private JavaMailSender javaMailSender;
    private Configuration freemarkerConfiguration;
    private CampaignType campaignType;
    private List<CampaignEmailTemplate> campaignEmailTemplates;

    public List<CampaignEmailTemplate> getCampaignEmailTemplates() {
        return campaignEmailTemplates;
    }

    public void setCampaignEmailTemplates(List<CampaignEmailTemplate> campaignEmailTemplates) {
        this.campaignEmailTemplates = campaignEmailTemplates;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Configuration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public void run() {
        try {
            //Thread.sleep(100000);
            for(CampaignEmailTemplate campaign : campaignEmailTemplates) {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(/*campaign.getSenderEmailAddress()*/"se_ab@yahoo.com");
                helper.setTo(campaign.getRecipientEmailAddress());
                helper.setSubject(/*campaign.getSubject()*/"Test");

                Map<String, Object> templateVars = new HashMap<String, Object>();

                templateVars.put("email", campaign);

                /*freemarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(CampaignMailRunnable.class,
                        File.separator + "WEB-INF" + File.separator + "ftl" + File.separator));*/
                Template template = freemarkerConfiguration.getTemplate(campaign.getTemplateFileName(), "utf-8");

                StringWriter content = new StringWriter();
                template.process(templateVars, content);

                helper.setText(content.toString(), true);
                getJavaMailSender().send(message);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
