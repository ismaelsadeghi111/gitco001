package com.sefryek.league.model.log;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Nov 23, 2011
 * Time: 1:26:01 PM
 */
@Entity
@Table(name = "user_activity")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "service_type")
    private int serviecType;

    @Column(name = "application_version")
    private String applicationVersion;

    @Column(name = "gateway")
    private String gateWay;

    @Column(name = "request_content")
    @Type(type = "text")
    private String requestContent;

    @Column(name = "response_content")
    @Type(type = "text")
    private String responseContent;

    @Column(name = "sms_service_number")
    private String smsServiceNumber;

    @Column(name = "user_identifier")
    private String userIdentifier;

    @Column(name = "request_message_id")
    private Long requestMessageId;

    @Column(name = "response_messages_ids")
    @Type(type = "text")
    private String responseMessagesIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getServiecType() {
        return serviecType;
    }

    public void setServiecType(int serviecType) {
        this.serviecType = serviecType;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getGateWay() {
        return gateWay;
    }

    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getSmsServiceNumber() {
        return smsServiceNumber;
    }

    public void setSmsServiceNumber(String smsServiceNumber) {
        this.smsServiceNumber = smsServiceNumber;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Long getRequestMessageId() {
        return requestMessageId;
    }

    public void setRequestMessageId(Long requestMessageId) {
        this.requestMessageId = requestMessageId;
    }

    public String getResponseMessagesIds() {
        return responseMessagesIds;
    }

    public void setResponseMessagesIds(String responseMessagesIds) {
        this.responseMessagesIds = responseMessagesIds;
    }
}
