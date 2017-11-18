package com.sefryek.doublepizza.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/21/14
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@javax.persistence.Table(name = "catering")
public class Catering implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catering_id")
    private Long id;

    @Column(name = "title_en")
    private String  titleEn;

    @Column(name = "title_fr")
    private String  titleFr;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "min_serving")
    private String minServing;

    @Column(name = "max_serving")
    private String maxServing;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_fr")
    private String descriptionFr;
//    private Category category;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMinServing() {
        return minServing;
    }

    public void setMinServing(String minServing) {
        this.minServing = minServing;
    }

    public String getMaxServing() {
        return maxServing;
    }

    public void setMaxServing(String maxServing) {
        this.maxServing = maxServing;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    @Transient
    public String getName(Locale loc){
        if (loc.equals(Locale.FRENCH) || loc.equals(Locale.FRANCE)) {
           return this.titleFr;
        } else {
            return this.titleEn;
        }
    }

    @Transient
    public String getDescription(Locale loc){
        if (loc.equals(Locale.FRENCH) || loc.equals(Locale.FRANCE)) {
            return this.descriptionFr;

        } else {
            return this.descriptionEn;
        }
    }
}


