package com.sefryek.doublepizza.service;

import com.sefryek.common.Point;
import com.sefryek.doublepizza.model.PostalCode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 29, 2012
 * Time: 10:06:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPostalCodeService {
    public static String BEAN_NAME = "postalCodeService";

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo);
    
    public Point findLocationByPostalCode(String postalCode);

    public List<PostalCode> getCitiesListWithPosition();
}
