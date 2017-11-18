package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.service.ISliderService;
import com.sefryek.doublepizza.model.Slider;
import com.sefryek.doublepizza.dao.SliderDAO;

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

    public List<Slider> findTopSlides(){
        return sliderDAO.findTopSlides();    
    }
}
