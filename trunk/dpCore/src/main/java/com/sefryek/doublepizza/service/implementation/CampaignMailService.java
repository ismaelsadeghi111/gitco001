package com.sefryek.doublepizza.service.implementation;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.enums.CampaignType;
import com.sefryek.doublepizza.service.ICampaignMailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 * User: sepideh
 * Date: 1/23/14
 * Time: 12:59 PM
 */
public class CampaignMailService implements Runnable {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;
    private CampaignType campaignType;
    private List<CampaignEmailTemplate> campaignEmailTemplates;

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

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



    public Configuration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }


    @Override
    public void run() {
            MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    for (CampaignEmailTemplate campaign : campaignEmailTemplates) {
                        Thread.sleep(100000);
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                        helper.setFrom(campaign.getSenderEmailAddress());
                        helper.setTo(campaign.getRecipientEmailAddress());
                        helper.setSubject(campaign.getSubject());

                        Map<String, Object> templateVars = new HashMap<String, Object>();

                        templateVars.put("email", campaign);

                        Configuration cfg = new Configuration();
                        cfg.setTemplateLoader(new ClassTemplateLoader(CampaignMailService.class,
                                File.separator + "freemarker" + File.separator + "email" + File.separator));
                        Template template = cfg.getTemplate(campaign.getTemplateFileName(), "utf-8");

                        StringWriter content = new StringWriter();
                        template.process(templateVars, content);

                        helper.setText(content.toString(), true);
                }
            }
        };
        mailSender.send(messagePreparator);
    }
}
