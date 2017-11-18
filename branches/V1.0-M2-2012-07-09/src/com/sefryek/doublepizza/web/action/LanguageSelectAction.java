package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.Globals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import java.util.Locale;
import java.util.List;
import java.io.PrintWriter;
import java.io.IOException;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper;
import com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper;
import com.sefryek.doublepizza.InMemoryData;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class LanguageSelectAction extends DispatchAction{

    public ActionForward changeLangToFrench(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String forward = request.getParameter("location");

        request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.FRENCH);
        response.sendRedirect(forward);

        return null;
    }

    public ActionForward changeLangToEnglish(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String forward = request.getParameter("location");

        request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.ENGLISH);
        response.sendRedirect(forward);

        return null;
    }

    //some testing method(showes Basket content)
    public ActionForward showBasket(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        BasketCombinedItemHelper basketCombinedItemHelper = new BasketCombinedItemHelper();
        BasketSingleItemHelper basketSingleItemHelper = new BasketSingleItemHelper();


        Basket basket = (Basket)session.getAttribute(Constant.BASKET);
        StringBuffer stringBuffer = new StringBuffer();
        for (BasketItem basketItem :  basket.getBasketItemList()){
            if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                BasketSingleItem basketSingleItem = (BasketSingleItem)basketItem.getObject();
                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(), basketSingleItem.getGroupId());
                stringBuffer.append(menuSingleItem.getName(Locale.ENGLISH)).append("\t");
                stringBuffer.append(basketSingleItemHelper.getPrice(basketSingleItem)).append("\n");
            }else {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem)basketItem.getObject();
                CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(), basketCombinedItem.getGroupIdRef());
                stringBuffer.append(combinedMenuItem.getName(Locale.ENGLISH)).append("\t");
                stringBuffer.append(basketCombinedItemHelper.getPrice(basketCombinedItem)).append("\n");

                List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
                for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
                    stringBuffer.append('\t').append(InMemoryData.findMenuSingleItemByIdAndGroupId(aBasketSingleItem.getId(),
                            aBasketSingleItem.getGroupId()).getName(Locale.ENGLISH)).append('\n');
                }

            }
        }

        try {
            PrintWriter out  = response.getWriter();
            out.write(stringBuffer.toString());
            out.flush();

        } catch (IOException e) {
        }

        return null;


    }


    public ActionForward showSession(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        StringBuffer stringBuffer = new StringBuffer();
        BasketSingleItemHelper basketSingleItemHelper = new BasketSingleItemHelper();

        BasketCombinedItem basketCombinedItem = (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
        if (basketCombinedItem != null) {
            stringBuffer.append(InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(),
                    basketCombinedItem.getGroupIdRef()).getName(Locale.ENGLISH));
            stringBuffer.append("\n");

            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

            for (BasketSingleItem curbasketSingle : basketSingleItemList) {
                stringBuffer.append('\t').append(InMemoryData.findMenuSingleItemByIdAndGroupId(curbasketSingle.getId(), curbasketSingle.getGroupId()).getName(Locale.ENGLISH)).append('\t');
                stringBuffer.append(basketSingleItemHelper.getPrice(curbasketSingle));
                stringBuffer.append('\n');
            }
        }

        else {
            stringBuffer.append("Null");
        }


        try {
            PrintWriter out  = response.getWriter();
            out.write(stringBuffer.toString());
            out.flush();

        } catch (IOException e) {
        }

        return null;
    }

    public ActionForward showBasketSingleInCombinedInSession(ActionMapping mapping, ActionForm form,
                                                             HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        StringBuffer stringBuffer = new StringBuffer();
        Locale curLocale = (Locale)session.getAttribute(Globals.LOCALE_KEY);


        BasketCombinedItem basketCombinedItem = (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

        for (BasketSingleItem curBasketSingleItem : basketSingleItemList) {
            stringBuffer.append(curBasketSingleItem.getSingle().getName(curLocale)).append("\n\t")
                    .append(curBasketSingleItem.getGroupId())
                    .append(", ")
                    .append(curBasketSingleItem.getId()).append("\n");
//            stringBuffer.append(curBasketSingleItem.getIdentifier()).append("#");
        }


        try {
            PrintWriter out  = response.getWriter();
            out.write(stringBuffer.toString());
            out.flush();

        } catch (IOException e) {
        }

        return null;

    }

    public ActionForward invalidateSession(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        session.invalidate();

        try {
            PrintWriter out = response.getWriter();
            out.write("true");
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }





}
