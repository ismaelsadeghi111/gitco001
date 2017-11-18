package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.SliderDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Slide;
import com.sefryek.doublepizza.service.ISliderService;

import java.text.ParseException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 7, 2012
 * Time: 12:28:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class SliderServiceImpl implements ISliderService{

    private SliderDAO sliderDAO;
    public void setSliderDAO(SliderDAO sliderDAO) {
        this.sliderDAO = sliderDAO;
    }

    public void save (Slide slide) throws DAOException {
        sliderDAO.save(slide);
    }

    public void updateSlide (Slide slide) throws DAOException, ParseException {
        sliderDAO.updateSlide(slide);
    }


    public List<Slide> findTopSlides() throws DAOException {
        return  sliderDAO.getAllSlides();
    }
}
