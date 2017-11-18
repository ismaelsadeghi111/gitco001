package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.InMemoryData;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 5, 2012
 * Time: 2:05:09 PM
 */
public class WorkingHour {

    private Integer dayId;
    private String openingHour;
    private String closingHour;
    private String dayOfWeekKey;
    private String dayofWeekGroup;

    public WorkingHour(Integer dayId, String dayofWeekKey, String dayofWeekGroup, String openingHour, String closingHour) {
        this.dayId = dayId;
        this.dayOfWeekKey = dayofWeekKey;
        this.dayofWeekGroup = dayofWeekGroup;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public String getDayOfWeek(Locale loc) {
        return InMemoryData.getData(dayOfWeekKey, loc);

    }

    public String getDayofWeekGroup() {
        return dayofWeekGroup;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }
}
