package com.sefryek.doublepizza.web.action;
import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.web.form.QuickMenuForm;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.core.*;
import com.sefryek.doublepizza.InMemoryData;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 12/28/13
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuickMenuAction extends DispatchAction {
    private IDollarService dollarService;
    public enum ClassTypeEnum {
        CATEGORY, COMBINED, SINGLEMENUITEM
    }


    class CategoryComperator implements Comparator {
        public int compare(Object o1, Object o2) {
            Category c1 = (Category) o1;
            return c1.compareTo(o2);
        }
    }



    private Category getDefaultCategory(String menuName) throws Exception {

        Menu menuObj = null;
        if (menuName != null && !menuName.isEmpty()) {
            menuObj = InMemoryData.getMenuByName(menuName);
        }


        List<Category> specialList = null;
//        assert menuObj != null;
        if(menuObj != null){
            specialList = menuObj.getCategoryList();
            Collections.sort(specialList, new CategoryComperator());
            return specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX);
        }
        return null;

    }

    public List<Category> getMenuSpecialList(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);
        List<Category> specialList = null;

        String menuType = request.getParameter("menuType");
        Menu menuObj = new Menu(null);
        if (menuType.equals("menu")) {
            menuObj = InMemoryData.getMenuByName(Constant.ORDER_MENU_NAME);
            if (menuObj.getCategoryList() != null){
                specialList =  menuObj.getCategoryList();
            }
            if (getDefaultCategory(menuType) != null && getDefaultCategory(menuType).getId() != null){
                request.setAttribute("defaultSelectedCategoryId", getDefaultCategory(menuType).getId());
            }
        } else if (menuType.equals("special")) {
            menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
            if (menuObj.getCategoryList() != null){
                specialList =  menuObj.getCategoryList();
            }
            if (getDefaultCategory(menuType) != null && getDefaultCategory(menuType).getId() != null){
                request.setAttribute("defaultSelectedCategoryId", getDefaultCategory(menuType).getId());
            }
        } else if (menuType.equals("both")){
            menuObj = InMemoryData.getMenuByName(Constant.ORDER_MENU_NAME);
            if (menuObj.getCategoryList() != null){
                specialList =  menuObj.getCategoryList();
            }
            menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
            if (menuObj.getCategoryList() != null){
                specialList.addAll( menuObj.getCategoryList());
            }
        }

        String menuName = (String)session.getAttribute(Constant.MENU_NAME);

        if (menuName != null) {
            session.setAttribute(Constant.MENU_NAME, menuName);
        }

        if(specialList != null && specialList.size() >0){
            Collections.sort(specialList, new CategoryComperator());
        }

        session.setAttribute("menuType", menuType);
        request.setAttribute("menuType", menuType);
        request.setAttribute("defaultSelectedCategoryIndex", Constant.DEFAULT_SELECTED_CATEGORY_INDEX);
        return specialList;
    }

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
    }

    public ActionForward goToQuickMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
           // Saeid AmanZadeh
           //----------------- set todayDPDollarsPercentage on session --------------
           HttpSession session = request.getSession();
           Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
           int dayNo = DateUtil.getDayOfWeekNumber(new Date());
           Float todayDPDollarsPercentage = dollarService.getDollarPercentageByNumberOfDay(dayNo);
           DpDollarSchedule dpDollarSchedule = dollarService.findDPDollarSchedualByDate(new Date());
           todayDPDollarsPercentage = (dpDollarSchedule != null && dpDollarSchedule.getPercentage() > 0) ? dpDollarSchedule.getPercentage() : todayDPDollarsPercentage;
           session.setAttribute("todayDPDollarsPercentage", todayDPDollarsPercentage);

           List<Map<String,Float>> dpDollarsWeeklyList = dollarService.getDpDollarsWeekly(locale);

           session.setAttribute("dpDollarsWeeklyList", dpDollarsWeeklyList);
           //
           return mapping.findForward("getQuickMenu");
    }
    public ActionForward getFoodByType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        List<Category> foodCategories = new ArrayList<>();
        String foodType = request.getParameter("foodType");
        Menu menuObj = new Menu(null);
          if (foodType.equals("special")){
              menuObj = InMemoryData.getMenuByName(foodType);
              foodCategories = menuObj.getCategoryList();
          } else {

              foodCategories = InMemoryData.getFoodByTypeInMenuList(foodType);
          }

           QuickMenuForm quickMenuForm = (QuickMenuForm) form;
           quickMenuForm.setQuickMenuSpecialList(foodCategories);

           return mapping.findForward("getQuickMenuItems");
    }

}
