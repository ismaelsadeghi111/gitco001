package com.sefryek.doublepizza.web.action;

import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.PopularCategory;
import com.sefryek.doublepizza.model.Slide;
import com.sefryek.doublepizza.service.IMenuService;
import com.sefryek.doublepizza.service.ISliderService;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.web.form.SliderForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 06 09 2014
 * Time: 11:38
 */
public class SlideAction extends DispatchAction {
    private Logger logger;
    private WebApplicationContext context;
    private ISliderService sliderService;
    private IMenuService iMenuService;
    List<Slide> slideList;



    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        sliderService = (ISliderService) context.getBean(ISliderService.BEAN_NAME);
        try {
            slideList =sliderService.findTopSlides();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws DataLoadException, DAOException, ServiceException {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        List<Slide> sliderList = InMemoryData.getSliderList();
        request.setAttribute("sliderList",sliderList);
        return mapping.findForward("forwardToSlide");
    }


    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        try {
            SliderForm slideForm = (SliderForm) form;
            if (slideForm != null && slideForm.getSearchFrom() != null) {
                request.setAttribute("searchFrom", slideForm.getSearchFrom());
            }
            if (slideForm != null && slideForm.getSearchTo() != null) {
                request.setAttribute("searchTo", slideForm.getSearchTo());
            }
            if (slideForm != null && slideForm.getSearchFoodName() != null) {
                request.setAttribute("searchFoodName", slideForm.getSearchFoodName());
            }
            List<PopularCategory> categoryMenuList =iMenuService.getMenuCategoryListByName(slideForm.getSearchFoodName(),slideForm.getSearchFrom(),slideForm.getSearchTo());
            request.setAttribute("allMenuItemList", categoryMenuList); //set to action form
            List<Slide> sliderList = null;
            try {
                sliderList = sliderService.findTopSlides();
            } catch (DAOException e) {
                e.printStackTrace();
            }
            request.setAttribute("sliderList",sliderList);
            request.setAttribute("searchFromAt",slideForm.getSearchFrom());
            request.setAttribute("searchToAt",slideForm.getSearchTo());
            request.setAttribute("searchFoodNameAt",slideForm.getSearchFoodName());

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return mapping.findForward("forwardToSlide");
    }


    public ActionForward apply(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws DataLoadException, DAOException, ParseException {
        SliderForm slideForm = (SliderForm) form;

        if (slideForm.getIsupdate1().equalsIgnoreCase("yes")||slideForm.getUrl1()!=null) {
            Slide slide1 = new Slide();
            slide1.setId(Long.parseLong(slideForm.getSlideId1()));
            slide1.setProductNo(slideForm.getMenuitemId1().toString());
            slide1.setGroupId(slideForm.getGroupId1().toString());
            if(slideForm.getStatus1().equalsIgnoreCase("NotActive")) {
                slide1.setStatus(Boolean.FALSE);
            } else   if(slideForm.getStatus1().equalsIgnoreCase("Active")){
                slide1.setStatus(Boolean.TRUE);
            }
            slide1.setPriority(1);
            slide1.setCatId(Integer.parseInt(slideForm.getCategoryId1()));
            slide1.setExtraYrl(slideForm.getUrl1());
            slide1.setWebDescEN(slideForm.getTitle1());
            sliderService.updateSlide(slide1);
        }

        if (slideForm.getIsupdate2().equalsIgnoreCase("yes")||slideForm.getUrl2()!=null) {
            Slide slide2 = new Slide();
            slide2.setId(Long.parseLong(slideForm.getSlideId2()));
            slide2.setProductNo(slideForm.getMenuitemId2().toString());
            slide2.setGroupId(slideForm.getGroupId2().toString());
            if(slideForm.getStatus2().equalsIgnoreCase("NotActive")) {
                slide2.setStatus(Boolean.FALSE);
            } else if(slideForm.getStatus2().equalsIgnoreCase("Active")) {
                slide2.setStatus(Boolean.TRUE);
            }
            slide2.setPriority(1);
            slide2.setCatId(Integer.parseInt(slideForm.getCategoryId2()));
            slide2.setExtraYrl(slideForm.getUrl2());
            slide2.setWebDescEN(slideForm.getTitle2());
            sliderService.updateSlide(slide2);
        }

        if (slideForm.getIsupdate3().equalsIgnoreCase("yes")||slideForm.getUrl3()!=null) {
            Slide slide3 = new Slide();
            slide3.setId(Long.parseLong(slideForm.getSlideId3()));
            slide3.setProductNo(slideForm.getMenuitemId3().toString());
            slide3.setGroupId(slideForm.getGroupId3().toString());
            if(slideForm.getStatus3().equalsIgnoreCase("NotActive")) {
                slide3.setStatus(Boolean.FALSE);
            } else  if(slideForm.getStatus3().equalsIgnoreCase("Active")){
                slide3.setStatus(Boolean.TRUE);
            }
            slide3.setPriority(1);
            slide3.setCatId(Integer.parseInt(slideForm.getCategoryId3()));
            slide3.setExtraYrl(slideForm.getUrl3());
            slide3.setWebDescEN(slideForm.getTitle3());
            sliderService.updateSlide(slide3);
        }

        if (slideForm.getIsupdate4().equalsIgnoreCase("yes")||slideForm.getUrl4()!=null) {
            Slide slide4 = new Slide();
            slide4.setId(Long.parseLong(slideForm.getSlideId4()));
            slide4.setProductNo(slideForm.getMenuitemId4().toString());
            slide4.setGroupId(slideForm.getGroupId4().toString());
            if(slideForm.getStatus4().equalsIgnoreCase("NotActive")) {
                slide4.setStatus(Boolean.FALSE);
            } else  if(slideForm.getStatus4().equalsIgnoreCase("Active")) {
                slide4.setStatus(Boolean.TRUE);
            }
            slide4.setPriority(1);
            slide4.setCatId(Integer.parseInt(slideForm.getCategoryId4()));
            slide4.setExtraYrl(slideForm.getUrl4());
            slide4.setWebDescEN(slideForm.getTitle4());
            sliderService.updateSlide(slide4);
        }


        if (slideForm.getIsupdate5().equalsIgnoreCase("yes")||slideForm.getUrl5()!=null) {
            Slide slide5 = new Slide();
            slide5.setId(Long.parseLong(slideForm.getSlideId5()));
            slide5.setProductNo(slideForm.getMenuitemId5().toString());
            slide5.setGroupId(slideForm.getGroupId5().toString());
            if(slideForm.getStatus5().equalsIgnoreCase("NotActive")) {
                slide5.setStatus(Boolean.FALSE);
            } else   if(slideForm.getStatus5().equalsIgnoreCase("Active")){
                slide5.setStatus(Boolean.TRUE);
            }
            slide5.setPriority(1);
            slide5.setCatId(Integer.parseInt(slideForm.getCategoryId5()));
            slide5.setExtraYrl(slideForm.getUrl5());
            slide5.setWebDescEN(slideForm.getTitle5());
            sliderService.updateSlide(slide5);
        }

        if (slideForm.getIsupdate6().equalsIgnoreCase("yes")||slideForm.getUrl6()!=null) {
            Slide slide6 = new Slide();
            slide6.setId(Long.parseLong(slideForm.getSlideId6()));
            slide6.setProductNo(slideForm.getMenuitemId6().toString());
            slide6.setGroupId(slideForm.getGroupId6().toString());
            if(slideForm.getStatus6().equalsIgnoreCase("NotActive")) {
                slide6.setStatus(Boolean.FALSE);
            } else if(slideForm.getStatus6().equalsIgnoreCase("Active")){
                slide6.setStatus(Boolean.TRUE);
            }
            slide6.setPriority(1);
            slide6.setCatId(Integer.parseInt(slideForm.getCategoryId6()));
            slide6.setExtraYrl(slideForm.getUrl6());
            slide6.setWebDescEN(slideForm.getTitle6());
            sliderService.updateSlide(slide6);
        }

        return search(mapping, form, request, response);
    }
}
