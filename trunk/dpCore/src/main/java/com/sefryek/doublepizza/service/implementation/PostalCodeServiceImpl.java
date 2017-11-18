package com.sefryek.doublepizza.service.implementation;

import com.sefryek.common.Point;
import com.sefryek.doublepizza.dao.PostalCodeDAO;
import com.sefryek.doublepizza.model.PostalCode;
import com.sefryek.doublepizza.service.IPostalCodeService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 29, 2012
 * Time: 10:09:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostalCodeServiceImpl implements IPostalCodeService {
    private PostalCodeDAO postalCodeDAO;

    public void setPostalCodeDAO(PostalCodeDAO postalCodeDAO) {
        this.postalCodeDAO = postalCodeDAO;
    }

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo) {
        return postalCodeDAO.findStreetNameByPostalCodeAndStreetNo(postalCode, streetNo);
    }

    public Point findLocationByPostalCode(String postalCode) {
        return postalCodeDAO.findLocationByPostalCode(postalCode);
    }

    public List<PostalCode> getCitiesListWithPosition() {
        return postalCodeDAO.getCitiesListWithPosition();
    }
}
