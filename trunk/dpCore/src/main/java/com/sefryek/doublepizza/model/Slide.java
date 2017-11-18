package com.sefryek.doublepizza.model;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 04 09 2014
 * Time: 14:20
 */

import javax.persistence.*;
import java.io.Serializable;


@Entity
@javax.persistence.Table(name = "slide_items")
public class Slide implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "slide_id")
    private Long id;

    @Column(name = "productNo")
    private String  productNo;

    @Column(name = "group_id")
    private String  groupId;

    @Column(name = "status")
    private Boolean  status;

    @Column(name = "image_en")
    private String imageURLEN;

    @Column(name = "image_fr")
    private String imageURLFR;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "title_en")
    private String WebDescEN;

    @Column(name = "title_fr")
    private String WebDescFR;

    @Column(name = "category_id")
    private Integer catId;

    @Column(name = "url")
    private String extraYrl;

    public Slide() {
    }

    public Slide(Long id,String productNo, String groupId, Boolean status, String imageURLEN, String imageURLFR, Integer priority, String webDescEN, String webDescFR, Integer catId) {
        this.id=id;
        this.productNo = productNo;
        this.groupId = groupId;
        this.status = status;
        this.imageURLEN = imageURLEN;
        this.imageURLFR = imageURLFR;
        this.priority = priority;
        WebDescEN = webDescEN;
        WebDescFR = webDescFR;
        this.catId = catId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImageURLEN() {
        return imageURLEN;
    }

    public void setImageURLEN(String imageURLEN) {
        this.imageURLEN = imageURLEN;
    }

    public String getImageURLFR() {
        return imageURLFR;
    }

    public void setImageURLFR(String imageURLFR) {
        this.imageURLFR = imageURLFR;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getWebDescEN() {
        return WebDescEN;
    }

    public void setWebDescEN(String webDescEN) {
        WebDescEN = webDescEN;
    }

    public String getWebDescFR() {
        return WebDescFR;
    }

    public void setWebDescFR(String webDescFR) {
        WebDescFR = webDescFR;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getExtraYrl() {
        return extraYrl;
    }

    public void setExtraYrl(String extraYrl) {
        this.extraYrl = extraYrl;
    }
}