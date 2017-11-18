package com.sefryek.doublepizza.model;

import javax.persistence.*;
import javax.persistence.Table;


/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 3, 2012
 * Time: 3:44:07 PM
 */
@Entity
@Table(name = "Tax")
public class Tax {

//    @Column(name = "AccCode")
//    private String AccCode;

    @Column(name = "CodeName")
    private String CodeName;

//    @Column(name = "DeptCode")
//    private String DeptCode;

    @Column(name = "Description")
    private String Description;

//    @Column(name = "\'GL-Code\'")
//    private String GlCode;

    @Column(name = "Percentage")
    private Double Percentage;

    @Column(name = "RecType")
    private String RecType;

    @Column(name = "Seq")
    private String Seq;

    @Column(name = "Taxable")
    private String Taxable;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TaxId")
    private String TaxId;

    @Column(name = "WebName")
    private String webName;

//    public String getAccCode() {
//        return AccCode;
//    }
//
//    public void setAccCode(String accCode) {
//        AccCode = accCode;
//    }

    public String getCodeName() {
        return CodeName;
    }

    public void setCodeName(String codeName) {
        CodeName = codeName;
    }

//    public String getDeptCode() {
//        return DeptCode;
//    }
//
//    public void setDeptCode(String deptCode) {
//        DeptCode = deptCode;
//    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

//    public String getGlCode() {
//        return GlCode;
//    }
//
//    public void setGlCode(String glCode) {
//        GlCode = glCode;
//    }

    public Double getPercentage() {
        return Percentage;
    }

    public void setPercentage(Double percentage) {
        Percentage = percentage;
    }

    public String getRecType() {
        return RecType;
    }

    public void setRecType(String recType) {
        RecType = recType;
    }

    public String getSeq() {
        return Seq;
    }

    public void setSeq(String seq) {
        Seq = seq;
    }

    public String getTaxable() {
        return Taxable;
    }

    public void setTaxable(String taxable) {
        Taxable = taxable;
    }

    public String getTaxId() {
        return TaxId;
    }

    public void setTaxId(String taxId) {
        TaxId = taxId;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }
}
