package com.sefryek.common.config;

import java.math.BigDecimal;


/**
 * Created by IntelliJ IDEA.
 * User: Sarvenaz
 * Date: May 9, 2011
 * Time: 4:57:49 PM
 */

public class ApplicationConfig {

    public static String runMode;

    public static String versionInfo;
    public static String versionShort;
    public static String driver;
    public static String url;
    public static String username;
    public static String password;

    public static long emailDelaySend;
    public static String campaignFilepath;
    public static String campaignTemplateFilepath;
    public static String httpFilePath;

    public static String cateringEmails;

    public static String schema;

    public static String invoiceNotificationStartupDelay;
    public static String invoiceNotificationInterval;
    public static String invoiceNotificationName;

    public static String invoiceOverdueStartupDelay;
    public static String invoiceOverdueInterval;
    public static String invoiceOverdueName;

    public static String customerReportIntervel;
    public static String customerReportName;

    public static String ipUrl;
    public static String timerName;
    public static String dataHome;

    public static String currencyUnit;
    public static int currencyDecimalDigits;

    public static String sliderSrcImagesPath;
    public static String sliderDestImagesPath;

    public static String archivePath;
    public static String pathSplitterSign;

    public static String dataResourcesStartPath;

    public static String iphoneImagePathPosfix;

    public static BigDecimal orderPriceMinValue;

    public static String imageNotFoundFileName;

    public static Double dpDollarMinValue;


    public static String getCateringEmails() {
        return cateringEmails;
    }

    public void setCateringEmails(String cateringEmails) {
        ApplicationConfig.cateringEmails = cateringEmails;
    }

    public String getSliderSrcImagesPath() {
        return sliderSrcImagesPath;
    }

    public void setSliderSrcImagesPath(String sliderSrcImagesPath) {
        ApplicationConfig.sliderSrcImagesPath = sliderSrcImagesPath;
    }

    public String getSliderDestImagesPath() {
        return sliderDestImagesPath;
    }

    public void setSliderDestImagesPath(String sliderDestImagesPath) {
        ApplicationConfig.sliderDestImagesPath = sliderDestImagesPath;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        ApplicationConfig.versionInfo = versionInfo;
    }

    public static String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        ApplicationConfig.driver = driver;
    }

    public static String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        ApplicationConfig.url = url;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        ApplicationConfig.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        ApplicationConfig.password = password;
    }

    public static String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        ApplicationConfig.schema = schema;
    }

    public static String getInvoiceNotificationStartupDelay() {
        return invoiceNotificationStartupDelay;
    }

    public void setInvoiceNotificationStartupDelay(String invoiceNotificationStartupDelay) {
        ApplicationConfig.invoiceNotificationStartupDelay = invoiceNotificationStartupDelay;
    }

    public static String getInvoiceNotificationInterval() {
        return invoiceNotificationInterval;
    }

    public void setInvoiceNotificationInterval(String invoiceNotificationInterval) {
        ApplicationConfig.invoiceNotificationInterval = invoiceNotificationInterval;
    }

    public static String getInvoiceNotificationName() {
        return invoiceNotificationName;
    }

    public void setInvoiceNotificationName(String invoiceNotificationName) {
        ApplicationConfig.invoiceNotificationName = invoiceNotificationName;
    }

    public static String getInvoiceOverdueStartupDelay() {
        return invoiceOverdueStartupDelay;
    }

    public void setInvoiceOverdueStartupDelay(String invoiceOverdueStartupDelay) {
        ApplicationConfig.invoiceOverdueStartupDelay = invoiceOverdueStartupDelay;
    }

    public static String getInvoiceOverdueInterval() {
        return invoiceOverdueInterval;
    }

    public void setInvoiceOverdueInterval(String invoiceOverdueInterval) {
        ApplicationConfig.invoiceOverdueInterval = invoiceOverdueInterval;
    }

    public static String getInvoiceOverdueName() {
        return invoiceOverdueName;
    }

    public void setInvoiceOverdueName(String invoiceOverdueName) {
        ApplicationConfig.invoiceOverdueName = invoiceOverdueName;
    }

    public static String getCustomerReportIntervel() {
        return customerReportIntervel;
    }

    public void setCustomerReportIntervel(String customerReportIntervel) {
        ApplicationConfig.customerReportIntervel = customerReportIntervel;
    }

    public static String getCustomerReportName() {
        return customerReportName;
    }

    public void setCustomerReportName(String customerReportName) {
        ApplicationConfig.customerReportName = customerReportName;
    }

    public static String getIpUrl() {
        return ipUrl;
    }

    public void setIpUrl(String ipUrl) {
        ApplicationConfig.ipUrl = ipUrl;
    }

    public static String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        ApplicationConfig.timerName = timerName;
    }

    public static String getDataHome() {
        return dataHome;
    }

    public void setDataHome(String dataHome) {
        ApplicationConfig.dataHome = dataHome;
    }
    public static Integer getX() {
    	return 1000;
    }

    public static String getCurrencyUnit() {
        return ApplicationConfig.currencyUnit;
    }

    public static void setCurrencyUnit(String currencyUnit) {
        ApplicationConfig.currencyUnit = currencyUnit;
    }

    public static int getCurrencyDecimalDigits() {
        return currencyDecimalDigits;
    }

    public static void setCurrencyDecimalDigits(int currencyDecimalDigits) {
        ApplicationConfig.currencyDecimalDigits = currencyDecimalDigits;
    }

    public String getArchivePath() {
        return ApplicationConfig.archivePath;
    }

    public void setArchivePath(String archivePath) {
        ApplicationConfig.archivePath = archivePath;
    }

    public String getVersionShort() {
        return ApplicationConfig.versionShort;
    }

    public void setVersionShort(String versionShort) {
        ApplicationConfig.versionShort = versionShort;
    }

    public String getPathSplitterSign() {
        return ApplicationConfig.pathSplitterSign;
    }

    public void setPathSplitterSign(String pathSplitterSign) {
        ApplicationConfig.pathSplitterSign = pathSplitterSign;
    }

    public String getDataResourcesStartPath() {
        return dataResourcesStartPath;
    }

    public void setDataResourcesStartPath(String dataResourcesStartPath) {
        ApplicationConfig.dataResourcesStartPath = dataResourcesStartPath;
    }

    public void setIphoneImagePathPosfix(String iphoneImagePathPosfix) {
        ApplicationConfig.iphoneImagePathPosfix = iphoneImagePathPosfix;
    }

    public BigDecimal getOrderPriceMinValue() {
        return ApplicationConfig.orderPriceMinValue;
    }

    public void setOrderPriceMinValue(BigDecimal orderPriceMinValue) {
        ApplicationConfig.orderPriceMinValue = orderPriceMinValue;
    }

    public String getImageNotFoundFileName() {
        return this.imageNotFoundFileName;
    }

    //Saeid AmanZadeh
    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        ApplicationConfig.runMode = runMode;
    }

    public static String getCampaignFilepath() {
        return campaignFilepath;
    }

    public  void setCampaignFilepath(String campaignFilepath) {
        ApplicationConfig.campaignFilepath = campaignFilepath;
    }

    public static String getHttpFilePath() {
        return httpFilePath;
    }

    public  void setHttpFilePath(String httpFilePath) {
        ApplicationConfig.httpFilePath = httpFilePath;
    }

    public  void setCampaignTemplateFilepath(String campaignTemplateFilepath) {
        ApplicationConfig.campaignTemplateFilepath = campaignTemplateFilepath;
    }

    public static String getCampaignTemplateFilepath() {
        return campaignTemplateFilepath;
    }

    public static long getEmailDelaySend() {
        return emailDelaySend;
    }

    public  void setEmailDelaySend(long emailDelaySend) {
        ApplicationConfig.emailDelaySend = emailDelaySend;
    }
    //---

    public void setImageNotFoundFileName(String imageNotFoundFileName) {
        this.imageNotFoundFileName = imageNotFoundFileName;
    }
}
