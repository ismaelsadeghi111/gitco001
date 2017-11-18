package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.Constant;

import javax.persistence.*;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 7, 2012
 * Time: 11:37:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = Constant.Slider_TBL_NAME)
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name=Constant.Slider_ID)
    private Integer id;

    @Column(name=Constant.Slider_PRICE)
    private BigDecimal price;

    @Column(name=Constant.Slider_FIRSTTEXT)
    private String firstText;

    @Column(name=Constant.Slider_SECONDTEXT)
    private String secondText;

    @Column(name=Constant.Slider_THIRDTEXT)
    private String thirdText;

    @Column(name=Constant.Slider_LINK)
    private String link;

    @Column(name=Constant.Slider_SEQUENCE)
    private int sequence;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFirstText() {
        return firstText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getThirdText() {
        return thirdText;
    }

    public void setThirdText(String thirdText) {
        this.thirdText = thirdText;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
