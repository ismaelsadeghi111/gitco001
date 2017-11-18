package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.Constant;
import javax.persistence.*;
import java.util.Date;

/**
 * User: sepideh
 * Date: 11/22/13
 * Time: 7:48 PM
 */
public class DpDollarSchedule {
    private Integer id;

    private Date startDate;

    private Date endDate;

    private float percentage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }


}
