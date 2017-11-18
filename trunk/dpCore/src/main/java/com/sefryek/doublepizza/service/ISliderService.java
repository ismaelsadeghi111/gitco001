package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Slide;

import java.text.ParseException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 7, 2012
 * Time: 12:26:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISliderService {

    public static String BEAN_NAME = "sliderService";
    public void save(Slide slide) throws DAOException;
    public void updateSlide(Slide slide) throws DAOException, ParseException;
    public List<Slide> findTopSlides() throws DAOException;

}
