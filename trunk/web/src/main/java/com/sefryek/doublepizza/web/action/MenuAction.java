package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;
import java.util.List;
import java.io.PrintWriter;

import com.sefryek.doublepizza.service.IMenuService;
import com.sefryek.doublepizza.service.ITaxService;
import com.sefryek.doublepizza.service.IOrderService;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.InMemoryData;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class MenuAction extends DispatchAction {

    private Logger logger;
    private ResourceBundle messageBundle;
    private IMenuService menuService;
    private ITaxService taxService;
    private IOrderService orderService;
    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        menuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
        taxService = (ITaxService) context.getBean(ITaxService.BEAN_NAME);
        orderService = (IOrderService) context.getBean(IOrderService.BEAN_NAME);
    }

    public ActionForward updateMenuList(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Menu menu = new Menu("Order");
        Menu specialMenu = new Menu("Special");

        InMemoryData in = new InMemoryData();
//        in.readTBLFile();
//        
        //get projection of requierd tables
//        InMemoryData.setWebCategoriesTBL(menuService.loadWebCategoriesTable());
//        InMemoryData.setWebCateg_webMenu_menuItem_JoinTBL(menuService.loadWebcategWebmenuMenuitemJoinTBL());
//        InMemoryData.setGroupTerm_menuItem_JoinTBL(menuService.loadTermGrptermMenuitemJoinTBL());
//        InMemoryData.setModifier_preset_JoinTBL(menuService.loadQueryResultSet(Constant.JOIN_TABLE_LIST.Modifier_Preset_JoinTBL.toString()));
//        InMemoryData.setMenuitemTBL(menuService.loadMenuItemTable());
//        InMemoryData.setProdLink_modLink_modifier_menuItem_JoinTBL(menuService.loadQueryResultSet(Constant.JOIN_TABLE_LIST.prodLink_modLink_modifier_menuItem_JoinTBL.toString()));
//        InMemoryData.setModifierTBL(menuService.loadModifierTable());
//        InMemoryData.setProdlinkTBL(menuService.loadProdLinkTable());
//        InMemoryData.setModlinkTBL(menuService.loadModLinkTable());
//
//         InMemoryData.setModifier_contents_JoinTBL(menuService.loadModifierContentsJoinTBL());
//        InMemoryData.writeTBLFile();

//        menu.setCategoryList(menuService.getMenuCategoryList(InMemoryData.getWebCategoriesTBL()));
        specialMenu.setCategoryList(menuService.getSpecialMenuCategoryList(InMemoryData.getTermTBL()));
        //menuService.loadResourceBoundle();

        return mapping.findForward("success");
    }

    public ActionForward getTaxObjcts(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        orderService.getLastDocNumber();
//        System.out.print(str);

        return null;                                                                            

    }

}