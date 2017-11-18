package com.sefryek.doublepizza.web.action;

import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Popular;
import com.sefryek.doublepizza.model.PopularCategory;
import com.sefryek.doublepizza.service.IMenuService;
import com.sefryek.doublepizza.service.IPopularService;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.web.form.PopularForm;
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
 * User: E_Ghasemi
 * Date: 12/13/13
 * Time: 8:04 AM
 */
public class PopularAction extends DispatchAction {

    private Logger logger;
    private WebApplicationContext context;
    private IPopularService iPopularService;
    private IMenuService iMenuService;
    private Popular currentPopular1;
    private Popular currentPopular2;
    private Popular currentPopular3;
    private Popular currentPopular4;
    List<Popular> populars;




    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        iPopularService = (IPopularService) context.getBean(IPopularService.BEAN_NAME);
        try {
            populars =iPopularService.getActivePopulars();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        iMenuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws DataLoadException, DAOException, ServiceException {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        setCurrentPopulars(request);
        request.setAttribute("popularSpecialList", /*iMenuService.getTopTenOrders()*/ null);
        request.setAttribute("allMenuItemList", /*iMenuService.getTopTenOrders()*/ null);
        request.setAttribute("popularSpecialList",  null);
        return mapping.findForward("forwardToPopular");
    }

    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        try {
            PopularForm popularForm = (PopularForm) form;
            if (popularForm != null && popularForm.getSearchFrom() != null) {
                request.setAttribute("searchFrom", popularForm.getSearchFrom());
            }
            if (popularForm != null && popularForm.getSearchTo() != null) {
                request.setAttribute("searchTo", popularForm.getSearchTo());
            }
            if (popularForm != null && popularForm.getSearchFoodName() != null) {
                request.setAttribute("searchFoodName", popularForm.getSearchFoodName());
            }
            List<PopularCategory> categoryMenuList =
                    iMenuService.getMenuCategoryListByName(popularForm.getSearchFoodName(),popularForm.getSearchFrom(),popularForm.getSearchTo());
            request.setAttribute("allMenuItemList", categoryMenuList); //set to action form
            request.setAttribute("searchFromAt",popularForm.getSearchFrom());
            request.setAttribute("searchToAt",popularForm.getSearchTo());
            request.setAttribute("searchFoodNameAt",popularForm.getSearchFoodName());
            setCurrentPopulars(request);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return mapping.findForward("forwardToPopular");
    }

    public ActionForward getPopularsByDate(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        try {
            PopularForm popularForm = (PopularForm) form;
            if (popularForm != null && popularForm.getSearchFrom() != null) {
                request.setAttribute("searchFrom", popularForm.getSearchFrom());
            }
            if (popularForm != null && popularForm.getSearchTo() != null) {
                request.setAttribute("searchTo", popularForm.getSearchTo());
            }
              if (popularForm.getSearchFrom()==null){
                  popularForm.setSearchFrom("2014/01/01 18:00");
              }
            List<Popular> popularList =
                    iPopularService.getPopularsByDate(popularForm.getSearchTo(), popularForm.getSearchFrom());
//            List<PopularCategory> popularList =
//                    iMenuService.getMenuCategoryListByName(popularForm.getSearchFoodName());
            request.setAttribute("popularSpecialList", popularList); //set to action form
            setCurrentPopulars(request);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return mapping.findForward("forwardToPopular");
    }


    public ActionForward apply(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws DataLoadException, DAOException, ParseException {

        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        PopularForm popularForm = (PopularForm) form;
        popularForm.setPriority1(1);
        popularForm.setPriority2(2);
        popularForm.setPriority3(3);
        popularForm.setPriority4(4);
        Popular popular1 = new Popular(popularForm.getQuantity1(), popularForm.getFoodName1(), popularForm.getImageUrl1(),Integer.parseInt(popularForm.getMenuitemId1()), popularForm.getCategoryId1(), popularForm.getGroupId1(),popularForm.getPriority1());
        Popular popular2 = new Popular(popularForm.getQuantity2(), popularForm.getFoodName2(), popularForm.getImageUrl2(),Integer.parseInt(popularForm.getMenuitemId2()), popularForm.getCategoryId2(), popularForm.getGroupId2(),popularForm.getPriority2());
        Popular popular3 = new Popular(popularForm.getQuantity3(), popularForm.getFoodName3(), popularForm.getImageUrl3(),Integer.parseInt(popularForm.getMenuitemId3()), popularForm.getCategoryId3(), popularForm.getGroupId3(),popularForm.getPriority3());
        Popular popular4 = new Popular(popularForm.getQuantity4(), popularForm.getFoodName4(), popularForm.getImageUrl4(),Integer.parseInt(popularForm.getMenuitemId4()), popularForm.getCategoryId4(), popularForm.getGroupId4(),popularForm.getPriority4());

        currentPopular1=populars.get(0);
        currentPopular2=populars.get(1);
        currentPopular3=populars.get(2);
        currentPopular4=populars.get(3);

        if (checkFilled(popular4) != null) {
            iPopularService.savePopular(popular4, currentPopular4.getPopularItemsId());
        }
        if (checkFilled(popular3) != null) {
            iPopularService.savePopular(popular3, currentPopular3.getPopularItemsId());
        }

        if (checkFilled(popular2) != null) {
            iPopularService.savePopular(popular2, currentPopular2.getPopularItemsId());
        }
        if (checkFilled(popular1) != null) {
            iPopularService.savePopular(popular1, currentPopular1.getPopularItemsId());
        }

        return search(mapping, form, request, response);
    }

    private Popular checkFilled(Popular popularForm) {
        if (popularForm.getMenuitemId().equals(null) ||
                popularForm.getMenuitemId().equals("")) {
            return null;
        }
        return popularForm;
    }

    private void setCurrentPopulars(HttpServletRequest request) throws DAOException, ServiceException {

        List<Popular> populars = iPopularService.getActivePopulars();
        request.setAttribute("currentPopulars", populars);
    }


    private Popular getActivePopular(int popNo, List<Popular> activePopulars) {
        try {

            return activePopulars.get(popNo);
        } catch (Exception e) {
            return new Popular();
        }
    }


}
