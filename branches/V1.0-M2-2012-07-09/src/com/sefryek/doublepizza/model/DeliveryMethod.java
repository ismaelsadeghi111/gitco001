package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.InMemoryData;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 3, 2012
 * Time: 5:44:43 PM
 */
public class DeliveryMethod {

    private Integer id;
    private String nameKey;


    public DeliveryMethod(Integer id, String englishName, String frenchName) {
        this.id = id;
        this.nameKey = InMemoryData.getHashMapKeyAndSaveWords("notable", "no-id", "delivery-method", "" + id, englishName, frenchName);
    }

    public Integer getId() {
        return id;
    }

    public String getName(Locale loc) {

        return InMemoryData.getData(nameKey, loc);
    }
}
