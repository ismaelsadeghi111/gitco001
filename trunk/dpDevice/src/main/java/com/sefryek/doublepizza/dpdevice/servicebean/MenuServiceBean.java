package com.sefryek.doublepizza.dpdevice.servicebean;

import com.sefryek.common.LogMessages;
import com.sefryek.common.util.CollectionsUtils;
import com.sefryek.common.util.DateUtil;
import com.sefryek.common.util.ServiceFinder;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.CurrencyUtils;
import com.sefryek.doublepizza.core.helpers.SuggestionsHelper;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dpdevice.model.*;
import com.sefryek.doublepizza.dto.*;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.*;
import com.sefryek.doublepizza.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nima on 12/28/13.
 */
public class MenuServiceBean {

    public List<CheckedCategoryForDevice> getMenuItems(ServletContext servletContext, String menuType) throws Exception {
        List<Category> specialList = new ArrayList<Category>();
        List<CategoryForDevice> tempSpecialList = new ArrayList<CategoryForDevice>();
        List<CheckedCategoryForDevice> finalList = new ArrayList<CheckedCategoryForDevice>();
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);

        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();

        Menu menuObj = new Menu(null);
        if (menuType.equals("getMenu")) {
            menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
        }
        if (menuType.equals("getSpecial")) {
            menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
        }

        if (menuObj.getCategoryList() != null) {
            specialList = menuObj.getCategoryList();
            CheckedCategoryForDevice chkForDevice = new CheckedCategoryForDevice();

            Category cat;
            for (int i = 0; i < specialList.size(); i++) {
                CategoryForDevice categoryForDevice = new CategoryForDevice();
                cat = (Category) specialList.get(i);
                categoryForDevice.setId(cat.getId());
                String categoryNameEN = cat.getName(Locale.ENGLISH);
                categoryForDevice.setNameKeyEN(categoryNameEN);
                String categoryNameFR = cat.getName(Locale.FRENCH);
                categoryForDevice.setNameKeyFR(categoryNameFR);
                String prefixURL = context + middlePath;
                categoryForDevice.setImageURL(prefixURL + cat.getImageURL());
                categoryForDevice.setSequence(cat.getSequence());
                tempSpecialList.add(categoryForDevice);
            }

            if (menuType.equals("getMenu")) {
                chkForDevice.setMenuType("Menu");
            }
            if (menuType.equals("getSpecial")) {
                chkForDevice.setMenuType("Special");
            }
            Collections.sort(tempSpecialList, new CategoryComperator());
            chkForDevice.setCategoryList(tempSpecialList);
            finalList.add(chkForDevice);
        }

        return finalList;
    }

    public List<CheckedSubCategoryForDevice> getSubMenuItems(ServletContext servletContext, String menuType) throws Exception {
        List<Category> menuList = new ArrayList<Category>();
        List<SubCategoryMiddleForDevice> tempSubMenuList = new ArrayList<SubCategoryMiddleForDevice>();
        List<CheckedSubCategoryForDevice> finalList = new ArrayList<CheckedSubCategoryForDevice>();
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);

        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();
        Double minPrice = Double.valueOf("00.00");
        Menu menuObj = new Menu(null);
        if (menuType.equals("getSubMenu")) {
            menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
        }
        if (menuType.equals("getSubSpecial")) {
            menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
        }

        if (menuObj.getCategoryList() != null) {
            menuList = menuObj.getCategoryList();
            CheckedSubCategoryForDevice chkForDevice = new CheckedSubCategoryForDevice();

            Category cat;
            SubCategory subCategory;

            for (int i = 0; i < menuList.size(); i++) {
                cat = (Category) menuList.get(i);
                List<SubCategory> subCategoryList = cat.getSubCategoryList();
                for (int j = 0; j < subCategoryList.size(); j++) {

                    SubCategoryForDevice subCategoryForDevice = new SubCategoryForDevice();
                    SubCategoryMiddleForDevice middleObj = new SubCategoryMiddleForDevice();

                    subCategory = (SubCategory) subCategoryList.get(j);
                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.CombinedMenuItem")) {
                        CombinedMenuItem combinedMenuItem= (CombinedMenuItem) subCategory.getObject();
                        subCategoryForDevice = convertListToMenuCombinedItem(combinedMenuItem, cat, context, middlePath);
                        if(combinedMenuItem.getProductNo().equalsIgnoreCase("690")){
                            subCategoryForDevice.setMaxToppingSize(20);
                        } else{subCategoryForDevice.setMaxToppingSize(5);}
                        middleObj.setType("combined");
                        middleObj.setObject(subCategoryForDevice);
                        tempSubMenuList.add(middleObj);
                    }
                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.MenuSingleItem")) {
                        MenuSingleItem menuSingleItem = (MenuSingleItem) subCategory.getObject();
                        subCategoryForDevice = convertListToMenuSingleItem(menuSingleItem, cat, context, middlePath);
                        subCategoryForDevice.setMaxToppingSize(5);
                        middleObj.setType("single");
                        middleObj.setObject(subCategoryForDevice);
                        tempSubMenuList.add(middleObj);
                    }
                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.Category")) {
                        Category menuCatItem = (Category) subCategory.getObject();
                        CombinedMenuItem combinedMenuItem = null;
                        MenuSingleItem menuSingleItem = null;
                        if(menuCatItem.getSubCategoryList().get(0).getObject().getClass().isInstance(combinedMenuItem)){
                            combinedMenuItem=(CombinedMenuItem)menuCatItem.getSubCategoryList().get(0).getObject();
                            if( combinedMenuItem.getProductNo().equalsIgnoreCase("690")){
                                subCategoryForDevice.setMaxToppingSize(20);
                            } else{
                                subCategoryForDevice.setMaxToppingSize(5);
                            }
                        }

                        List<MenuSingleItem> subMenuItemList = InMemoryData.getCategoryMenuSingleItemList(menuCatItem);
                        for (MenuSingleItem subSingleItem : subMenuItemList) {
                            minPrice = subSingleItem.getPrice().doubleValue();
                            if (subSingleItem.getPrice().doubleValue() < minPrice) {
                                minPrice = subSingleItem.getPrice().doubleValue();
                            }
                        }
                        subCategoryForDevice = convertListToCategoryItem(menuCatItem, minPrice.toString(), cat, context, middlePath);
                        middleObj.setType("innerCategory");
                        if(subCategoryForDevice.getMaxToppingSize() == 0)
                            subCategoryForDevice.setMaxToppingSize(5);
                        middleObj.setObject(subCategoryForDevice);
                        tempSubMenuList.add(middleObj);
                    }
                }
            }

            if (menuType.equals("getSubMenu")) {
                chkForDevice.setMenuType("subMenu");
            }
            if (menuType.equals("getSubSpecial")) {
                chkForDevice.setMenuType("subSpecial");
            }
            chkForDevice.setSubCategoryList(tempSubMenuList);
            finalList.add(chkForDevice);
        }

        return finalList;
    }

    public List<SubCategoryItemAlternatives> getSubMenuAlternativesItems(ServletContext servletContext, String menuType) throws Exception {
        int prvPrdNo = 0;
        List<Category> menuList = new ArrayList<Category>();
        List<SubCategoryItemAlternatives> itemAlternativesList = new ArrayList<SubCategoryItemAlternatives>();
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();
        SubCategoryItemAlternatives itemAlternatives = new SubCategoryItemAlternatives();
        itemAlternatives.setMenuType("alternative");
        List<SubCategorySingleItem> list = new ArrayList<SubCategorySingleItem>();

        Menu menuObj = new Menu(null);
        if (menuType.equals("getMenu")) {
            //menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
            menuObj = InMemoryData.getMenuByName(Constant.ORDER_MENU_NAME);
        }
        if (menuType.equals("getSpecial")) {
            //menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
            menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
        }

        if (menuObj.getCategoryList() != null) {
            menuList = menuObj.getCategoryList();
            CheckedSubCategoryForDevice chkForDevice = new CheckedSubCategoryForDevice();

            Category cat;
            SubCategory subCategory = new SubCategory();

            for (int i = 0; i < menuList.size(); i++) {

                cat = (Category) menuList.get(i);
                List<SubCategory> subCategoryList = cat.getSubCategoryList();


                for (int j = 0; j < subCategoryList.size(); j++) {

                    subCategory = (SubCategory) subCategoryList.get(j);

                    if (subCategory.getObject() instanceof MenuSingleItem) {
                        MenuSingleItem singleItem =(MenuSingleItem) subCategory.getObject();
                        SubCategorySingleItem subCategorySingleItem = new SubCategorySingleItem();

                        subCategorySingleItem.setId(singleItem.getId());
                        subCategorySingleItem.setGroupId(singleItem.getGroupId());
                        String nameEN = singleItem.getName(Locale.ENGLISH);
                        subCategorySingleItem.setNameKeyEN(nameEN);
                        String nameFR = singleItem.getName(Locale.FRENCH);
                        subCategorySingleItem.setNameKeyFR(nameFR);
                        subCategorySingleItem.setPrice(singleItem.getFormattedPrice());
                        subCategorySingleItem.setFreeToppingNo(String.valueOf(singleItem.getFreeToppingNo()));
                        subCategorySingleItem.setFreeToppingPrice(String.valueOf(singleItem.getFreeToppingPrice()));
                        subCategorySingleItem.setTwoForOne(singleItem.isTwoForOne());
                        subCategorySingleItem.setPortion(singleItem.isPortion());
                        subCategorySingleItem.setPizza(singleItem.isPizza());
                        String prefixURL = context + middlePath;
                        subCategorySingleItem.setImageURL(prefixURL+singleItem.getImageURL());
                        if(singleItem.getIsRedeemable() == null)
                            subCategorySingleItem.setIsRedeemable(false);
                        else
                            subCategorySingleItem.setIsRedeemable(singleItem.getIsRedeemable());
                        subCategorySingleItem.setProductNo(singleItem.getId());
                        subCategorySingleItem.setSequence(singleItem.getSequence());
                        subCategorySingleItem.setSize(singleItem.getSize());
                        list.add(subCategorySingleItem);
                        itemAlternatives.setAlternativeList(list);

                    }
                    if (subCategory.getObject() instanceof Category) {
                        Category category = (Category) subCategory.getObject();

                        if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem)
                        {
                            for(int jj=0;jj< category.getSubCategoryList().size();jj++)  {
                            MenuSingleItem menuSingleItem = (MenuSingleItem) category.getSubCategoryList().get(jj).getObject();

                            MenuSingleItem singleItem = menuSingleItem;
                            SubCategorySingleItem subCategorySingleItem = new SubCategorySingleItem();

                            subCategorySingleItem.setId(singleItem.getId());
                            subCategorySingleItem.setGroupId(singleItem.getGroupId());
                            String nameEN = singleItem.getName(Locale.ENGLISH);
                            subCategorySingleItem.setNameKeyEN(nameEN);
                            String nameFR = singleItem.getName(Locale.FRENCH);
                            subCategorySingleItem.setNameKeyFR(nameFR);
                            subCategorySingleItem.setPrice(singleItem.getFormattedPrice());
                            subCategorySingleItem.setFreeToppingNo(String.valueOf(singleItem.getFreeToppingNo()));
                            subCategorySingleItem.setFreeToppingPrice(String.valueOf(singleItem.getFreeToppingPrice()));
                            subCategorySingleItem.setTwoForOne(singleItem.isTwoForOne());
                            subCategorySingleItem.setPortion(singleItem.isPortion());
                            subCategorySingleItem.setPizza(singleItem.isPizza());
                            String prefixURL = context + middlePath;
                            subCategorySingleItem.setImageURL(prefixURL+singleItem.getImageURL());
                            if(singleItem.getIsRedeemable() == null)
                                subCategorySingleItem.setIsRedeemable(false);
                            else
                                subCategorySingleItem.setIsRedeemable(singleItem.getIsRedeemable());
                            subCategorySingleItem.setProductNo(pIdConvert(category.getId()));
                            subCategorySingleItem.setSequence(singleItem.getSequence());
                            subCategorySingleItem.setSize(singleItem.getSize());
//                            List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();
//                            List idList = new ArrayList();
//                            for (int t = 0; t < toppingCategoryList.size(); t++) {
//                                ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(t);
//                                idList.add(toppingCategory.getName(Locale.ENGLISH));
//                            }
//                            subCategorySingleItem.setToppingCategoryIdList(idList);
                            //subCategorySingleItem.setToppingCategoryList(singleItem.getToppingCategoryList());

                            list.add(subCategorySingleItem);
                            }
                            itemAlternatives.setAlternativeList(list);

                        }
                        if (category.getSubCategoryList().get(0).getObject() instanceof CombinedMenuItem) {
                            for(int jj=0;jj< category.getSubCategoryList().size()-1;++jj)  {
                            CombinedMenuItem combinedMenuItem = (CombinedMenuItem) category.getSubCategoryList().get(jj).getObject();
                            List<List<MenuSingleItem>> menuSingleItemList = combinedMenuItem.getAlternatives();


                            if (Integer.parseInt(combinedMenuItem.getProductNo()) != prvPrdNo) {
                                List<MenuSingleItem> menuSingleItemListForAllDresed = combinedMenuItem.getMenuSingleItemList();
                                MenuSingleItem defaultMenuSingleItem = menuSingleItemListForAllDresed.get(0);
                                List<MenuSingleItem> alternativeMenuItemList = combinedMenuItem.getAlternatives().get(0);
                                List<MenuSingleItem> allMenuSingleItemList = new ArrayList<MenuSingleItem>();
                                allMenuSingleItemList.add(defaultMenuSingleItem);
                                allMenuSingleItemList.addAll(alternativeMenuItemList);
                                prvPrdNo = Integer.parseInt(combinedMenuItem.getProductNo());


                                for (int l = 0; l < allMenuSingleItemList.size(); l++) {

                                    MenuSingleItem singleItem = (MenuSingleItem) allMenuSingleItemList.get(l);
                                    SubCategorySingleItem subCategorySingleItem = new SubCategorySingleItem();

                                    subCategorySingleItem.setId(singleItem.getId());
                                    subCategorySingleItem.setGroupId(singleItem.getGroupId());
                                    String nameEN = singleItem.getName(Locale.ENGLISH);
                                    subCategorySingleItem.setNameKeyEN(nameEN);
                                    String nameFR = singleItem.getName(Locale.FRENCH);
                                    subCategorySingleItem.setNameKeyFR(nameFR);
                                    subCategorySingleItem.setPrice(singleItem.getFormattedPrice());
                                    subCategorySingleItem.setFreeToppingNo(String.valueOf(singleItem.getFreeToppingNo()));
                                    subCategorySingleItem.setFreeToppingPrice(String.valueOf(singleItem.getFreeToppingPrice()));
                                    subCategorySingleItem.setTwoForOne(singleItem.isTwoForOne());
                                    subCategorySingleItem.setPortion(singleItem.isPortion());
                                    String prefixURL = context + middlePath;
                                    subCategorySingleItem.setImageURL(prefixURL+singleItem.getImageURL());
                                    subCategorySingleItem.setPizza(singleItem.isPizza());
                                    if(singleItem.getIsRedeemable() == null)
                                        subCategorySingleItem.setIsRedeemable(false);
                                    else
                                        subCategorySingleItem.setIsRedeemable(singleItem.getIsRedeemable());
                                    subCategorySingleItem.setProductNo(combinedMenuItem.getProductNo());
                                    subCategorySingleItem.setSequence(singleItem.getSequence());
                                    subCategorySingleItem.setSize(singleItem.getSize());
                                    List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();
                                    List idList = new ArrayList();
                                    for (int t = 0; t < toppingCategoryList.size(); t++) {
                                        ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(t);
                                        idList.add(toppingCategory.getName(Locale.ENGLISH));
                                    }
                                    subCategorySingleItem.setToppingCategoryIdList(idList);
                                    //subCategorySingleItem.setToppingCategoryList(singleItem.getToppingCategoryList());

                                    list.add(subCategorySingleItem);
                                    itemAlternatives.setAlternativeList(list);
                                }
                            }
                            }
                        }
                    }

                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.CombinedMenuItem")) {

                        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) subCategory.getObject();
                        List<List<MenuSingleItem>> menuSingleItemList = combinedMenuItem.getAlternatives();


                        if (Integer.parseInt(combinedMenuItem.getProductNo()) != prvPrdNo) {
                            List<MenuSingleItem> menuSingleItemListForAllDresed = combinedMenuItem.getMenuSingleItemList();


                            List<MenuSingleItem> allMenuSingleItemList = new ArrayList<MenuSingleItem>();
                            for (int jj=0;jj<menuSingleItemListForAllDresed.size();jj++){
                                MenuSingleItem defaultMenuSingleItem = menuSingleItemListForAllDresed.get(jj);
                                if(!allMenuSingleItemList.contains(defaultMenuSingleItem)){
                                allMenuSingleItemList.add(defaultMenuSingleItem);
                                }
                            }

                            List<MenuSingleItem> alternativeMenuItemList = new ArrayList<MenuSingleItem>();
                            String name = "";

                            for (int k = 0; k < menuSingleItemList.size(); k++){
                                if(menuSingleItemList.get(k).size()>0){
                                   if(!menuSingleItemList.get(k).get(0).getName(Locale.ENGLISH).equals(name)){
                                        alternativeMenuItemList.addAll(menuSingleItemList.get(k));
                                        name  = menuSingleItemList.get(k).get(0).getName(Locale.ENGLISH);
                                    }
                                    else{
                                        name  = menuSingleItemList.get(k).get(0).getName(Locale.ENGLISH);
                                    }
                            }
                            }


                            allMenuSingleItemList.addAll(alternativeMenuItemList);
                            prvPrdNo = Integer.parseInt(combinedMenuItem.getProductNo());

                            for (int l = 0; l < allMenuSingleItemList.size(); l++) {

                                MenuSingleItem singleItem = (MenuSingleItem) allMenuSingleItemList.get(l);
                                SubCategorySingleItem subCategorySingleItem = new SubCategorySingleItem();
                                subCategorySingleItem.setId(singleItem.getId());
                                subCategorySingleItem.setGroupId(singleItem.getGroupId());
                                String nameEN = singleItem.getName(Locale.ENGLISH);
                                subCategorySingleItem.setNameKeyEN(nameEN);
                                String nameFR = singleItem.getName(Locale.FRENCH);
                                subCategorySingleItem.setNameKeyFR(nameFR);
                                subCategorySingleItem.setPrice(singleItem.getFormattedPrice());
                                subCategorySingleItem.setFreeToppingNo(String.valueOf(singleItem.getFreeToppingNo()));
                                subCategorySingleItem.setFreeToppingPrice(String.valueOf(singleItem.getFreeToppingPrice()));
                                subCategorySingleItem.setTwoForOne(singleItem.isTwoForOne());
                                subCategorySingleItem.setPortion(singleItem.isPortion());
                                String prefixURL = context + middlePath;
                                subCategorySingleItem.setImageURL(prefixURL+singleItem.getImageURL());
                                subCategorySingleItem.setPizza(singleItem.isPizza());
                                if(singleItem.getIsRedeemable() == null)
                                    subCategorySingleItem.setIsRedeemable(false);
                                else
                                    subCategorySingleItem.setIsRedeemable(singleItem.getIsRedeemable());
                                subCategorySingleItem.setProductNo(combinedMenuItem.getProductNo());
                                subCategorySingleItem.setSequence(singleItem.getSequence());
                                subCategorySingleItem.setSize(singleItem.getSize());
                                List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();
                                List idList = new ArrayList();
                                for (int t = 0; t < toppingCategoryList.size(); t++) {
                                    ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(t);
                                    idList.add(toppingCategory.getName(Locale.ENGLISH));
                                }
                                subCategorySingleItem.setToppingCategoryIdList(idList);
                                //subCategorySingleItem.setToppingCategoryList(singleItem.getToppingCategoryList());

                                list.add(subCategorySingleItem);
                                itemAlternatives.setAlternativeList(list);
                            }

                        }
                    }
                }
            }
        }

        itemAlternativesList.add(itemAlternatives);
        //return itemAlternatives;
        return itemAlternativesList;
    }

    public List<SubCategoryMenuSingleItem> getSubMenuSingleItems(ServletContext servletContext, String menuType) throws Exception {
        List<Category> menuList = new ArrayList<Category>();
        List<SubCategoryMenuSingleItem> itemAlternativesList = new ArrayList<SubCategoryMenuSingleItem>();

        SubCategoryMenuSingleItem itemAlternatives = new SubCategoryMenuSingleItem();
        itemAlternatives.setMenuType("menuSingleItem");
        List<SubCategorySingleItem> list = new ArrayList<SubCategorySingleItem>();

        Menu menuObj = new Menu(null);
        if (menuType.equals("getMenu")) {
            menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
        }
        if (menuType.equals("getSpecial")) {
            menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
        }

        if (menuObj.getCategoryList() != null) {
            menuList = menuObj.getCategoryList();
            CheckedSubCategoryForDevice chkForDevice = new CheckedSubCategoryForDevice();

            Category cat;
            SubCategory subCategory;

            for (int i = 0; i < menuList.size(); i++) {

                cat = (Category) menuList.get(i);
                List<SubCategory> subCategoryList = cat.getSubCategoryList();


                for (int j = 0; j < subCategoryList.size(); j++) {

                    subCategory = (SubCategory) subCategoryList.get(j);

                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.CombinedMenuItem")) {


                        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) subCategory.getObject();
                        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();

                        for (int k = 0; k < menuSingleItemList.size(); k++) {
                            MenuSingleItem singleItem = (MenuSingleItem) menuSingleItemList.get(k);
                            SubCategorySingleItem subCategorySingleItem = new SubCategorySingleItem();

//                            List<SubCategorySingleItem> list = new ArrayList<SubCategorySingleItem>();

                            CombinedMenuItem combined = null;
                            combined = InMemoryData.findCombinedInnerCategory(String.valueOf(combinedMenuItem.getProductNo()), String.valueOf(combinedMenuItem.getGroupId()), cat);
                            subCategorySingleItem.setId(singleItem.getId());
                            subCategorySingleItem.setGroupId(singleItem.getGroupId());
                            String nameEN = singleItem.getName(Locale.ENGLISH);
                            subCategorySingleItem.setNameKeyEN(nameEN);
                            String nameFR = singleItem.getName(Locale.FRENCH);
                            subCategorySingleItem.setNameKeyFR(nameFR);
                            subCategorySingleItem.setPrice(singleItem.getFormattedPrice());
                            subCategorySingleItem.setFreeToppingNo(String.valueOf(singleItem.getFreeToppingNo()));
                            subCategorySingleItem.setFreeToppingPrice(String.valueOf(singleItem.getFreeToppingPrice()));
                            subCategorySingleItem.setTwoForOne(singleItem.isTwoForOne());
                            subCategorySingleItem.setPortion(singleItem.isPortion());
                            subCategorySingleItem.setPizza(singleItem.isPizza());
                            if(singleItem.getIsRedeemable() == null)
                                subCategorySingleItem.setIsRedeemable(false);
                            else
                                subCategorySingleItem.setIsRedeemable(singleItem.getIsRedeemable());
                            subCategorySingleItem.setProductNo(combinedMenuItem.getProductNo());
                            subCategorySingleItem.setSequence(singleItem.getSequence());
                            subCategorySingleItem.setSize(singleItem.getSize());

                            String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);

                            String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();
                            String prefixURL = context + middlePath;
                            subCategorySingleItem.setImageURL(prefixURL+singleItem.getImageURL());
                            List<String> captionEN = combined.getCaptions(Locale.ENGLISH);
                            subCategorySingleItem.setCaptionsEN((String) captionEN.get(k));
                            List<String> captionFR = combined.getCaptions(Locale.FRENCH);
                            subCategorySingleItem.setCaptionsFR((String) captionFR.get(k));
                            list.add(subCategorySingleItem);
                            itemAlternatives.setMenuSingleItemList(list);
//                            itemAlternativesList.add(itemAlternatives);
                        }
                    }

                }
            }
        }

//        return itemAlternatives;
        itemAlternativesList.add(itemAlternatives);
        return itemAlternativesList;
    }

    public ToppingCategoryListItems getMenuTopping(ServletContext servletContext, String menuType) throws Exception {
        List<Category> menuList = new ArrayList<Category>();
        List<ToppingCategoryListItems> itemToppingList = new ArrayList<ToppingCategoryListItems>();

        ToppingCategoryListItems toppingCategoryListItems = new ToppingCategoryListItems();
        toppingCategoryListItems.setMenuType("menuToppings");

        // New List
        List subCategoryDeviceList = new ArrayList();
        ToppingCategoryDevice toppingCategoryDevice = null;
        ToppingDataList toppingData;
        //New List For New Object
        List<ToppingCategoryInfo> toppingCategoryInfoList = null;
        List<ToppingSubCategoryInfo> toppingSubCategoryInfoList = null;
        List<ToppingDataList> toppingDataListList = new ArrayList<ToppingDataList>();

        //List Check
        Map<String, ToppingCategoryInfo> mapCat = new HashMap<String, ToppingCategoryInfo>();
        Map<Integer, ToppingSubCategoryInfo> mapSubCat = new HashMap<Integer, ToppingSubCategoryInfo>();
        Map<Integer, ToppingDataList> mapDataList = new HashMap<Integer, ToppingDataList>();
        Map map = new HashMap();

        Menu menuObj = new Menu(null);
        if (menuType.equals("getMenu")) {
            menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
        }
        if (menuType.equals("getSpecial")) {
            menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
        }

        if (menuObj.getCategoryList() != null) {
            menuList = menuObj.getCategoryList();
            CheckedSubCategoryForDevice chkForDevice = new CheckedSubCategoryForDevice();

            Category cat;
            SubCategory subCategory;

            for (int i = 0; i < menuList.size(); i++) {

                cat = (Category) menuList.get(i);
                List<SubCategory> subCategoryList = cat.getSubCategoryList();

                for (int j = 0; j < subCategoryList.size(); j++) {

                    subCategory = (SubCategory) subCategoryList.get(j);


                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.CombinedMenuItem")) {

                        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) subCategory.getObject();
                        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
                        List<MenuSingleItem> alternativeMenuItemList = combinedMenuItem.getAlternatives().get(0);

                        Set<MenuSingleItem> menuSingleItemSet = new HashSet<MenuSingleItem>(menuSingleItemList);
                        List<MenuSingleItem> menuSingleItemListNoDup = new ArrayList<MenuSingleItem>(menuSingleItemSet);


                        for (int k = 0; k < menuSingleItemListNoDup.size(); k++) {
                            MenuSingleItem singleItem = (MenuSingleItem) menuSingleItemListNoDup.get(k);
                            List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();
                            for (int l = 0; l < toppingCategoryList.size(); l++) {
                                ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(l);

                                //Topping Category
//                                if (mapCat.size() < Constant.TOPPING_CATEGORY_SIZE) {
                                    ToppingCategoryInfo toppingCategoryInfo = new ToppingCategoryInfo();
                                    toppingCategoryInfo.setId(String.valueOf(toppingCategory.getId()));
                                    String nameEN = toppingCategory.getName(Locale.ENGLISH);
                                    toppingCategoryInfo.setNameKeyEN(nameEN);
                                    String nameFR = toppingCategory.getName(Locale.FRENCH);
                                    toppingCategoryInfo.setNameKeyFR(nameFR);
                                    toppingCategoryInfo.setExclusive(toppingCategory.getExclusive());
//                                    mapCat.put(Integer.parseInt(toppingCategory.getId().toString()), toppingCategoryInfo);
                                    mapCat.put(nameEN, toppingCategoryInfo);
//                                }

                                //Topping SubCategory
                                subCategoryDeviceList = toppingCategory.getToppingSubCategoryList();
                                for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                    ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                    Topping topping = (Topping) toppingSubCategory.getObject();
                                    ToppingSubCategoryInfo subToppingInfo = new ToppingSubCategoryInfo();
                                    String nameKeyEn = topping.getName(Locale.ENGLISH);
                                    subToppingInfo.setNameKeyEN(nameKeyEn);
                                    String nameKeyFr = topping.getName(Locale.FRENCH);
                                    subToppingInfo.setNameKeyFR(nameKeyFr);
                                    subToppingInfo.setWebSequence(topping.getWebSequence());
                                    subToppingInfo.setId(String.valueOf(topping.getId()));
                                    mapSubCat.put(topping.getId(), subToppingInfo);
                                }

                                //Topping Data List
                                List<ToppingSubCategoryPrice> toppingSubCategoryPriceList = new ArrayList<ToppingSubCategoryPrice>();
                                toppingData = new ToppingDataList();
                                toppingData.setCategoryId(String.valueOf(toppingCategory.getId()));
                                toppingData.setCategoryName(String.valueOf(toppingCategory.getName(Locale.ENGLISH)));
                                toppingData.setSequence(String.valueOf(singleItem.getSequence()));
                                toppingData.setParentId(singleItem.getId());
                                toppingData.setParentGroupId(singleItem.getGroupId());
                                toppingData.setSequence(String.valueOf(l));

                                List<Integer> oldDefualtToppingList = toppingCategory.getDefaultToppingList();
                                List<String> newDefualtToppingList = new ArrayList<String>();
                                for (Integer num : oldDefualtToppingList) {
                                    newDefualtToppingList.add(String.valueOf(num));
                                }
                                toppingData.setDefaultToppingList(newDefualtToppingList);

                                for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                    ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                    Topping topping = (Topping) toppingSubCategory.getObject();

                                    ToppingSubCategoryPrice subCategoryPrice = new ToppingSubCategoryPrice();
                                    subCategoryPrice.setId(String.valueOf(topping.getId()));
                                    subCategoryPrice.setPrice(String.valueOf(topping.getPrice()));
                                    toppingSubCategoryPriceList.add(subCategoryPrice);
                                    toppingData.setToppingSubCategoryList(toppingSubCategoryPriceList);
                                }
                                //mapDataList.put(toppingCategory.getId(), toppingData);
                                toppingDataListList.add(toppingData);
                            }
                        }

                        //Alternate Topping
                        for (int k = 0; k < alternativeMenuItemList.size(); k++) {
                            MenuSingleItem singleItem = (MenuSingleItem) alternativeMenuItemList.get(k);
                            List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();

                            for (int l = 0; l < toppingCategoryList.size(); l++) {
                                ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(l);

                                //Topping Category
//                                if (mapCat.size() < Constant.TOPPING_CATEGORY_SIZE) {
                                    ToppingCategoryInfo toppingCategoryInfo = new ToppingCategoryInfo();
                                    toppingCategoryInfo.setId(String.valueOf(toppingCategory.getId()));
                                    String nameEN = toppingCategory.getName(Locale.ENGLISH);
                                    toppingCategoryInfo.setNameKeyEN(nameEN);
                                    String nameFR = toppingCategory.getName(Locale.FRENCH);
                                    toppingCategoryInfo.setNameKeyFR(nameFR);
                                    toppingCategoryInfo.setExclusive(toppingCategory.getExclusive());
//                                    mapCat.put(Integer.parseInt(toppingCategory.getId().toString()), toppingCategoryInfo);
                                    mapCat.put(nameEN, toppingCategoryInfo);
//                                }

                                //Topping SubCategory
                                subCategoryDeviceList = toppingCategory.getToppingSubCategoryList();
                                for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                    ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                    Topping topping = (Topping) toppingSubCategory.getObject();
                                    ToppingSubCategoryInfo subToppingInfo = new ToppingSubCategoryInfo();
                                    String nameKeyEn = topping.getName(Locale.ENGLISH);
                                    subToppingInfo.setNameKeyEN(nameKeyEn);
                                    String nameKeyFr = topping.getName(Locale.FRENCH);
                                    subToppingInfo.setNameKeyFR(nameKeyFr);
                                    subToppingInfo.setWebSequence(topping.getWebSequence());
                                    subToppingInfo.setId(String.valueOf(topping.getId()));
                                    mapSubCat.put(topping.getId(), subToppingInfo);
                                }

                                //Topping Data List
                                List<ToppingSubCategoryPrice> toppingSubCategoryPriceList = new ArrayList<ToppingSubCategoryPrice>();
                                toppingData = new ToppingDataList();
                                toppingData.setCategoryId(String.valueOf(toppingCategory.getId()));
                                toppingData.setCategoryName(String.valueOf(toppingCategory.getName(Locale.ENGLISH)));
                                toppingData.setSequence(String.valueOf(singleItem.getSequence()));
                                toppingData.setParentId(singleItem.getId());
                                toppingData.setParentGroupId(singleItem.getGroupId());
                                toppingData.setSequence(String.valueOf(l));

                                List<Integer> oldDefualtToppingList = toppingCategory.getDefaultToppingList();
                                List<String> newDefualtToppingList = new ArrayList<String>();
                                for (Integer num : oldDefualtToppingList) {
                                    newDefualtToppingList.add(String.valueOf(num));
                                }
                                toppingData.setDefaultToppingList(newDefualtToppingList);

                                for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                    ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                    Topping topping = (Topping) toppingSubCategory.getObject();

                                    ToppingSubCategoryPrice subCategoryPrice = new ToppingSubCategoryPrice();
                                    subCategoryPrice.setId(String.valueOf(topping.getId()));
                                    subCategoryPrice.setPrice(String.valueOf(topping.getPrice()));
                                    toppingSubCategoryPriceList.add(subCategoryPrice);
                                    toppingData.setToppingSubCategoryList(toppingSubCategoryPriceList);
                                }
                                //mapDataList.put(toppingCategory.getId(), toppingData);
                                toppingDataListList.add(toppingData);
                            }
                        }
                        map.put(combinedMenuItem.getGroupId(), combinedMenuItem.getGroupId());
                    }


                    // Category
                    if (subCategory.getType().getName().equals("com.sefryek.doublepizza.model.Category")) {
                        Category category = (Category) subCategory.getObject();
                        List<SubCategory> subCategoryOfCList = category.getSubCategoryList();

                        for (int c = 0; c < subCategoryOfCList.size(); c++) {
                            SubCategory sb = (SubCategory) subCategoryOfCList.get(c);
                            MenuSingleItem menuSingleItem = (MenuSingleItem) sb.getObject();
                            List<ToppingCategory> toppingCategoryList = menuSingleItem.getToppingCategoryList();
                            if (toppingCategoryList != null) {
                                for (int l = 0; l < toppingCategoryList.size(); l++) {
                                    ToppingCategory toppingCategory = (ToppingCategory) toppingCategoryList.get(l);

                                    //Topping Category
//                                    if (mapCat.size() < Constant.TOPPING_CATEGORY_SIZE) {
                                        ToppingCategoryInfo toppingCategoryInfo = new ToppingCategoryInfo();
                                        toppingCategoryInfo.setId(String.valueOf(toppingCategory.getId()));
                                        String nameEN = toppingCategory.getName(Locale.ENGLISH);
                                        toppingCategoryInfo.setNameKeyEN(nameEN);
                                        String nameFR = toppingCategory.getName(Locale.FRENCH);
                                        toppingCategoryInfo.setNameKeyFR(nameFR);
                                        toppingCategoryInfo.setExclusive(toppingCategory.getExclusive());
//                                        mapCat.put(Integer.parseInt(toppingCategory.getId().toString()), toppingCategoryInfo);
                                        mapCat.put(nameEN, toppingCategoryInfo);
//                                    }
                                    //Topping SubCategory
                                    subCategoryDeviceList = toppingCategory.getToppingSubCategoryList();
                                    for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                        ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                        Topping topping = (Topping) toppingSubCategory.getObject();
                                        ToppingSubCategoryInfo subToppingInfo = new ToppingSubCategoryInfo();
                                        String nameKeyEn = topping.getName(Locale.ENGLISH);
                                        subToppingInfo.setNameKeyEN(nameKeyEn);
                                        String nameKeyFr = topping.getName(Locale.FRENCH);
                                        subToppingInfo.setNameKeyFR(nameKeyFr);
                                        subToppingInfo.setWebSequence(topping.getWebSequence());
                                        subToppingInfo.setId(String.valueOf(topping.getId()));
                                        mapSubCat.put(topping.getId(), subToppingInfo);
                                    }

//                                    //Topping Data List
                                    List<ToppingSubCategoryPrice> toppingSubCategoryPriceList = new ArrayList<ToppingSubCategoryPrice>();
                                    toppingData = new ToppingDataList();
                                    toppingData.setCategoryId(String.valueOf(toppingCategory.getId()));
                                    toppingData.setCategoryName(String.valueOf(toppingCategory.getName(Locale.ENGLISH)));
                                    toppingData.setSequence(String.valueOf(menuSingleItem.getSequence()));
                                    toppingData.setParentId(menuSingleItem.getId());
                                    toppingData.setParentGroupId(menuSingleItem.getGroupId());
                                    toppingData.setSequence(String.valueOf(l));

                                    List<Integer> oldDefualtToppingList = toppingCategory.getDefaultToppingList();
                                    List<String> newDefualtToppingList = new ArrayList<String>();
                                    for (Integer num : oldDefualtToppingList) {
                                        newDefualtToppingList.add(String.valueOf(num));
                                    }
                                    toppingData.setDefaultToppingList(newDefualtToppingList);

                                    for (int t = 0; t < subCategoryDeviceList.size(); t++) {
                                        ToppingSubCategory toppingSubCategory = (ToppingSubCategory) subCategoryDeviceList.get(t);
                                        Topping topping = (Topping) toppingSubCategory.getObject();

                                        ToppingSubCategoryPrice subCategoryPrice = new ToppingSubCategoryPrice();
                                        subCategoryPrice.setId(String.valueOf(topping.getId()));
                                        subCategoryPrice.setPrice(String.valueOf(topping.getPrice()));
                                        toppingSubCategoryPriceList.add(subCategoryPrice);
                                        toppingData.setToppingSubCategoryList(toppingSubCategoryPriceList);
                                    }
                                    toppingDataListList.add(toppingData);
                                }
                            }
                        }
                    }
                    // End Category


                }
            }
        }

        //New
        toppingCategoryInfoList = new ArrayList<ToppingCategoryInfo>(mapCat.values());
        toppingSubCategoryInfoList = new ArrayList<ToppingSubCategoryInfo>(mapSubCat.values());
        //toppingDataListList = new ArrayList<ToppingDataList>(mapDataList.values());
        toppingCategoryListItems.setToppingCategory(toppingCategoryInfoList);
        toppingCategoryListItems.setToppingSubCategory(toppingSubCategoryInfoList);
        toppingCategoryListItems.setToppingDataList(toppingDataListList);

        itemToppingList.add(toppingCategoryListItems);
        return toppingCategoryListItems;
    }

    public void getMenuToppings(ServletContext servletContext, String menuType) throws Exception {

    }

    public UserForDevice loginByEmailAndPassword(ServletContext servletContext, String email, String password) {
        UserForDevice userForDevice = new UserForDevice();
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        try {
            User user = userService.findByEmailAndPassword(email, password);
            if (user == null) {
                return userForDevice;
            } else {
                userForDevice.setUserId(user.getId());
                userForDevice.setFirstName(user.getFirstName());
                userForDevice.setLastName(user.getLastName());
                if(user.getSubscribed().equals(Boolean.FALSE))userForDevice.setSubscribe("False");
                if(user.getSubscribed().equals(Boolean.TRUE))userForDevice.setSubscribe("True");
                if(user.getBirthDate()!=null){
                userForDevice.setBirthday(user.getBirthDate().toString().substring(0, 10));
                }
                if (user.getTitle()== User.Title.MALE) {
                    userForDevice.setTitle(UserForDevice.Title.MALE);
                }
                if (user.getTitle()==User.Title.FEMALE) {
                    userForDevice.setTitle(UserForDevice.Title.FEMALE);
                }
                userForDevice.setEmail(user.getEmail());
                userForDevice.setCompany(user.getCompany());
                return userForDevice;
            }
        } catch (Exception e) {
            return userForDevice;
        }
    }


    public RegisterInfo registerNewUser(ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj) throws Exception {
        User user = null;
        UserForDevice userForDevice = new UserForDevice();
        RegisterInfo registerInfo = new RegisterInfo();
        int userId = 0;
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        IUserRoleService userRoleService = (IUserRoleService) ServiceFinder.findBean(servletContext,IUserRoleService.BEAN_NAME);
        UserRole userRole = userRoleService.findByName(IUserRoleService.UserRoleName.ROLE_USER);
        String title = reqRegisterInfoObj.getTitle();
        String email = reqRegisterInfoObj.getEmail();
        String firstName = reqRegisterInfoObj.getFirstName();
        String lastName = reqRegisterInfoObj.getLastName();
        String password = reqRegisterInfoObj.getPassword();
        String birthDate = reqRegisterInfoObj.getBirthday();
        String company = reqRegisterInfoObj.getCompany() != null ? reqRegisterInfoObj.getCompany() :"";
        String subscribed = reqRegisterInfoObj.getSubscribe();
        Date userBirthdate = null;
        if(reqRegisterInfoObj.getBirthday()!=null && reqRegisterInfoObj.getBirthday()!=""){
        userBirthdate = DateUtil.stringToDate(birthDate);
        }
//        Locale locale = (Locale) servletContext.getAttribute(Globals.LOCALE_KEY);
        Locale locale = Locale.ENGLISH;
        if (title.equalsIgnoreCase("MALE")) {
            user = new User(User.Title.MALE, email, firstName, lastName, password, company, "", "", birthDate,"En",subscribed,userRole);
        }
        if (title.equalsIgnoreCase("FEMALE")) {
            user = new User(User.Title.FEMALE, email, firstName, lastName, password,company, "", "", birthDate, "En",subscribed,userRole);
        }

        if (userService.isRegistered(email)) {
//            userForDevice.setEmail(reqRegObjControl.getEmail());
//            userForDevice.setUserId(-1);
            registerInfo.setEmail(reqRegisterInfoObj.getEmail());
            registerInfo.setUserId(-1);
            return registerInfo;
        } else /*if (userBirthdate.after(new Date(System.currentTimeMillis()))) {
//            userForDevice.setBirthday(null);
            registerInfo.setBirthday(null);
            return registerInfo;
        } else {*/
        {
            user.setIsRegistered(true);
            user.setBirthDate(userBirthdate);
            user.setRegFrom("iOS");
            userService.save(user);

            User regUser = userService.findByEmailAndIsRegistered(reqRegisterInfoObj.getEmail(), true);

            ContactInfoDevice contactInfoDevice = addAddress(servletContext, reqRegisterInfoObj, String.valueOf(regUser.getId()));

            registerInfo.setId(contactInfoDevice.getId());
            registerInfo.setAddress(contactInfoDevice.getAddress());
            registerInfo.setUserId(regUser.getId());
            registerInfo.setFirstName(regUser.getFirstName());
            registerInfo.setLastName(regUser.getLastName());



            if(regUser.getBirthDate()!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                registerInfo.setBirthday(sdf.format(regUser.getBirthDate()).toString());
            }
            else {registerInfo.setBirthday("");}
            if(regUser.getSubscribed().equals(Boolean.FALSE))registerInfo.setSubscribe("False");
            if(regUser.getSubscribed().equals(Boolean.TRUE))registerInfo.setSubscribe("True");
            if (title.equalsIgnoreCase("MALE")) {
                registerInfo.setTitle("MALE");
            }
            if (title.equalsIgnoreCase("FEMALE")) {
                registerInfo.setTitle("FEMALE");
            }
            registerInfo.setCompany(regUser.getCompany());
            registerInfo.setEmail(regUser.getEmail());
            registerInfo.setAddress(contactInfoDevice.getAddressScreenEN());
            registerInfo.setAddressScreenEN(contactInfoDevice.getAddressScreenEN());
            registerInfo.setPostalcode(contactInfoDevice.getPostalcode());
            registerInfo.setStreetNo(contactInfoDevice.getStreetNo());
            registerInfo.setStreet(contactInfoDevice.getStreet());
            registerInfo.setCity(contactInfoDevice.getCity());
            registerInfo.setBuilding(contactInfoDevice.getBuilding());
            registerInfo.setPhone(contactInfoDevice.getPhone());
            registerInfo.setExt(contactInfoDevice.getExt());
            registerInfo.setSuiteAPT(contactInfoDevice.getSuiteAPT());
            registerInfo.setDoorCode(contactInfoDevice.getDoorCode());

            return registerInfo;
        }
    }

    public RegisterInfo deliveryNewUser(ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj) throws Exception {
        User user = null;
        UserForDevice userForDevice = new UserForDevice();
        RegisterInfo registerInfo = new RegisterInfo();
        int userId = 0;
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        IUserRoleService userRoleService = (IUserRoleService) ServiceFinder.findBean(servletContext,IUserRoleService.BEAN_NAME);
        UserRole userRole = userRoleService.findByName(IUserRoleService.UserRoleName.ROLE_USER);

        String title = reqRegisterInfoObj.getTitle();
        String email = reqRegisterInfoObj.getEmail();
        String firstName = reqRegisterInfoObj.getFirstName();
        String lastName = reqRegisterInfoObj.getLastName();
        String company = reqRegisterInfoObj.getCompany();
        String ext = reqRegisterInfoObj.getExt();
        Locale locale =new Locale("English");
        String lang="";
        if(locale.getDisplayName().equals("English")){lang="En";}else{lang="Fr";}
        if (title.equalsIgnoreCase("MALE")) {
        user = new User(User.Title.MALE, email, firstName, lastName, null, company, null, null,ext,lang,"False",userRole);
        }else {user = new User(User.Title.FEMALE, email, firstName, lastName, null, company, null, null,ext,lang,"False",userRole);}


            user.setIsRegistered(false);
            userService.save(user);
            User regUser = userService.findByEmailAndIsRegistered(reqRegisterInfoObj.getEmail(), false);
            ContactInfoDevice contactInfoDevice = addAddress(servletContext, reqRegisterInfoObj, String.valueOf(regUser.getId()));
            registerInfo.setId(contactInfoDevice.getId());
            registerInfo.setUserId(regUser.getId());
            registerInfo.setFirstName(regUser.getFirstName());
            registerInfo.setLastName(regUser.getLastName());
            registerInfo.setBirthday(String.valueOf(regUser.getBirthDate()));
            if (title.equalsIgnoreCase("MALE")) {
                registerInfo.setTitle("0");
            }
            if (title.equalsIgnoreCase("FEMALE")) {
                registerInfo.setTitle("1");
            }
            registerInfo.setCompany(regUser.getCompany());
            registerInfo.setEmail(regUser.getEmail());

            registerInfo.setAddressScreenEN(contactInfoDevice.getAddressScreenEN());
            registerInfo.setPostalcode(contactInfoDevice.getPostalcode());
            registerInfo.setStreetNo(contactInfoDevice.getStreetNo());
            registerInfo.setStreet(contactInfoDevice.getStreet());
            registerInfo.setCity(contactInfoDevice.getCity());
            registerInfo.setBuilding(contactInfoDevice.getBuilding());
            registerInfo.setPhone(contactInfoDevice.getPhone());
            registerInfo.setExt(contactInfoDevice.getExt());
            registerInfo.setSuiteAPT(contactInfoDevice.getSuiteAPT());
            registerInfo.setDoorCode(contactInfoDevice.getDoorCode());

            return registerInfo;

    }
    public OrderInfo createOrder(ServletContext servletContext, ReqOrderObj reqOrderObj) throws Exception {
        IOrderService orderService= (IOrderService) ServiceFinder.findBean(servletContext, IOrderService.BEAN_NAME);
        IContactInfoService contactInfoService = (IContactInfoService) ServiceFinder.findBean(servletContext, IContactInfoService.BEAN_NAME);
        IDollarService dollarService = (IDollarService) ServiceFinder.findBean(servletContext,IDollarService.BEAN_NAME);
        Logger logger = Logger.getLogger(ReqOrderObj.class);
        Date deliverTime = new Date();
        Order order=new Order();
        OrderInfo orderInfo=  new OrderInfo();
        String totalPrice=reqOrderObj.getOrderPrice();
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        User user=  userService.findById(Integer.parseInt(reqOrderObj.getUserId()));
        user.setOfficialTitle(user.getTitle() == User.Title.MALE ? User.OfficialTitle.Mr : User.OfficialTitle.Ms);

        if (reqOrderObj.getDeliveryTime() != null)
        {
            deliverTime =DateUtil.stringToDate(reqOrderObj.getDeliveryTime());
            order.setOrderDate(deliverTime);
        }
        order.setPaymentType(reqOrderObj.getPaymentType());
        order.setStore(reqOrderObj.getStoreId());
        order.setOrderNote(reqOrderObj.getDeliveryNote()); //I forced set description to note
        order.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(totalPrice)));
        order.setDiscount(Double.valueOf(reqOrderObj.getDiscount()));
        order.setCouponCode(reqOrderObj.getDiscountDescription());
        order.setDiscountDesc("This order use " + reqOrderObj.getDiscountDescription() + " from coupon");
        if (reqOrderObj.getDeliveryType().equalsIgnoreCase("DELIVERY")) {
            order.setDeliveryType(Order.DeliveryType.DELIVERY);
        } else  if (reqOrderObj.getDeliveryType().equalsIgnoreCase("PICKUP")) {
            order.setDeliveryType(Order.DeliveryType.PICKUP);
        }
        order.setUser(user);
        List<BasketItem> basketItems=new ArrayList<>();

        for (int i = 0; i < reqOrderObj.getBasketItem().size(); i++) {
            BasketItem basketItem = new BasketItem();
            BasketItemForOrder tempItem = reqOrderObj.getBasketItem().get(i);
            if (tempItem.getClassType().equalsIgnoreCase("Combined")) {
                BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
                basketCombinedItem.setGroupIdRef(tempItem.getGroupIdRefrence());
                basketCombinedItem.setProductNoRef(tempItem.getProductNoRefrence());
                basketCombinedItem.setQuantity(Byte.parseByte(tempItem.getQuantity()));
                List<BasketSingleItem> basketSingleItems = new ArrayList<>();
                for (int j = 0; j < tempItem.getBasketSingleItemList().size(); j++) {
                    BasketSingleItemForOrder basketSingleItemForOrder = null;
                    basketSingleItemForOrder = tempItem.getBasketSingleItemList().get(j);
                    BasketSingleItem basketSingleItem = new BasketSingleItem();
                    basketSingleItem.setId(basketSingleItemForOrder.getId());
                    basketSingleItem.setGroupId(basketSingleItemForOrder.getGroupId());
                    Map<Integer, String> map = new HashMap<>();
                    for (int k = 0; k < basketSingleItemForOrder.getSelectedToppings().size(); k++) {
                        String st = "";
                        String st2 = "";
                        st = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("key");
                        st2 = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("value");
                        map.put(Integer.parseInt(st), st2);
                    }
                    basketSingleItem.setSelectedToppingMap(map);
                    basketSingleItem.setQuantity(basketSingleItemForOrder.getQuantity());
                    basketSingleItems.add(basketSingleItem);
                }
                basketCombinedItem.setBasketSingleItemList(basketSingleItems);
                basketItem.setObject(basketCombinedItem);
                basketItem.setClassType(BasketCombinedItem.class);
            }
            if (tempItem.getClassType().equalsIgnoreCase("Single")) {
                BasketSingleItem basketSingleItem = new BasketSingleItem();
                List<BasketSingleItem> basketSingleItems = new ArrayList<>();
                for (int j = 0; j < tempItem.getBasketSingleItemList().size(); j++) {
                    BasketSingleItemForOrder basketSingleItemForOrder = null;
                    basketSingleItemForOrder = tempItem.getBasketSingleItemList().get(j);
                    basketSingleItem.setId(basketSingleItemForOrder.getId());
                    basketSingleItem.setGroupId(basketSingleItemForOrder.getGroupId());
                    Map<Integer, String> map = new HashMap<>();
                    for (int k = 0; k < basketSingleItemForOrder.getSelectedToppings().size(); k++) {
                        String st = "";
                        String st2 = "";
                        st = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("key");
                        st2 = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("value");
                        map.put(Integer.parseInt(st), st2);
                    }
                    basketSingleItem.setSelectedToppingMap(map);
                    basketSingleItem.setQuantity(basketSingleItemForOrder.getQuantity());
                    basketSingleItems.add(basketSingleItem);
                }
                basketItem.setObject(basketSingleItem);
                basketItem.setClassType(BasketSingleItem.class);
            }

            basketItems.add(basketItem);
        }
        order.setBasketItems(basketItems);
        order.setOrderFrom("iOS");
        List<ContactInfo> userContacts = null;
        userContacts = contactInfoService.getAll(user.getId());
        ContactInfo contactInfo=userContacts.get(0);
        String pickPhone=null;
        String pickExt=null;
        if (reqOrderObj.getDeliveryType().equalsIgnoreCase(String.valueOf(Order.DeliveryType.PICKUP))){
            if (reqOrderObj.getPickupPhone()!=null){
                pickPhone=reqOrderObj.getPickupPhone();
                pickExt=reqOrderObj.getPickupExt();
            }
        }

        orderService.save(order, contactInfo, pickPhone, pickExt);

        String  docNumber="";
        if(order.getDocNumber()!=null){
            docNumber=order.getDocNumber();
        }
        orderInfo.setDocNumber(docNumber);


//        ================ add for dpdollar =================

        DpDollarHistory dpDollarHistory = new DpDollarHistory();
        Double balance = new Double(00.00d);
        Double discount  = Double.valueOf(reqOrderObj.getSpentDPD()) == null ? new Double(0.00) :  Double.valueOf(reqOrderObj.getSpentDPD() );
        logger.info(LogMessages.START_OF_METHOD + "checkoutDiscount");
        if(discount != null && !discount.equals(new Double(00.00d))){
            dpDollarHistory.setAmount(discount);
            dpDollarHistory.setStatus(DpDollarHistory.Status.SPENT.name());
            dpDollarHistory.setOrderId(order.getDocNumber());
            dpDollarHistory.setCreationDate(new Date());
            logger.debug("from device createOrder checkoutDiscount method: start of reattachment user to session");
            dollarService.reattachToSession(user);
            logger.debug("from device createOrder checkoutDiscount method: reattachment user to session finished");
            logger.debug("checkoutDiscount method: start of calculateDpDollarsBalanceForUser");
            balance = dollarService.calculateDpDollarsBalanceForUser(user.getId());
            logger.debug("from device createOrder checkoutDiscount method: calculateDpDollarsBalanceForUser finished");
            balance = balance - discount;
            balance = CurrencyUtils.doubleRoundingFormat(balance);
            dpDollarHistory.setBalance(balance);

            dpDollarHistory.setUser(user);
            try {
                logger.debug("from device createOrder checkoutDiscount method: start of earned dpDollar saving");
                dollarService.saveHistory(dpDollarHistory);
                logger.debug("from device createOrder checkoutDiscount method: earned dpDollar saving finished");
            } catch (Exception e) {
                logger.debug("from device createOrder checkoutDiscount Method: error on saving Discount DpDollar  \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                        e.getStackTrace() + " \n \t cause: \n" + e.getCause());
                e.printStackTrace();
            }
            user.setDpDollarBalance(balance);
            logger.debug("checkoutDiscount method: start of reattachment user to session");
            dollarService.reattachToSession(user);
            logger.debug("checkoutDiscount method: reattachment user to session finished");
        }

        dpDollarHistory = new DpDollarHistory();
        logger.info(LogMessages.START_OF_METHOD + "checkoutEarned");
        Double earnedDpDollarsAmount  = new Double(00.00d);
        earnedDpDollarsAmount = Double.valueOf(reqOrderObj.getEarnedDPD()) == null ? new Double(0) :  Double.valueOf(reqOrderObj.getEarnedDPD());

        dpDollarHistory.setAmount(earnedDpDollarsAmount);
        dpDollarHistory.setStatus(DpDollarHistory.Status.EARNED.name());
        int dayNo = DateUtil.getDayOfWeekNumber(new Date());
        dpDollarHistory.setPercentage(CurrencyUtils.doubleRoundingFormat((dollarService.getDollarPercentageByNumberOfDay(dayNo))));
        dpDollarHistory.setCreationDate(new Date());
        logger.debug("from device createOrder checkoutEarned  method: start of reattachment user to session");
        dollarService.reattachToSession(user);
        logger.debug("from device createOrder checkoutEarned method: reattachment user to session finished");
        logger.debug("from device createOrder checkoutEarned method: start of calculateDpDollarsBalanceForUser");
        balance = dollarService.calculateDpDollarsBalanceForUser(user.getId());
        logger.debug("from device createOrder checkoutEarned method: calculateDpDollarsBalanceForUser finished");
        balance = balance + earnedDpDollarsAmount;
        balance = CurrencyUtils.doubleRoundingFormat(balance);

        dpDollarHistory.setUser(user);

        dpDollarHistory.setBalance(balance);
        dpDollarHistory.setOrderId(order.getDocNumber());
        try {
            logger.debug("from device createOrder checkoutEarned method: start of earned dpDollar saving");
            dollarService.saveHistory(dpDollarHistory);
            logger.debug("from device createOrder checkoutEarned method: earned dpDollar saving finished");
        } catch (Exception e) {
            logger.debug("checkoutEarned Method: error on saving Earned DpDollar \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            e.printStackTrace();
        }
        user.setDpDollarBalance(balance);
        logger.debug("from device createOrder checkoutEarned method: start of reattachment user to session");
        dollarService.reattachToSession(user);
        logger.debug("from device createOrder checkoutEarned method: reattachment user to session finished");
//      ============================= end of for dpdollar

//      ============================= check for new user
        List<OrderHistory> orderHistories=new ArrayList<OrderHistory>();
        if(user.getIsRegistered()==Boolean.TRUE){
            orderHistories = orderService.getOrderHistoryByUserId(user);
        }

        orderInfo.setIsNewUser(Boolean.TRUE);
        if (orderHistories.size()>0){
            orderInfo.setIsNewUser(Boolean.FALSE);
        }
        return orderInfo;

    }

    public ContactInfoDevice addAddress(ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj, String userId) throws Exception {

        Locale locale = (Locale) servletContext.getAttribute(Globals.LOCALE_KEY);

        ContactInfoDevice contactInfoDevice = new ContactInfoDevice();
        contactInfoDevice.setId(Long.valueOf(userId));
        contactInfoDevice.setAddressScreenEN(reqRegisterInfoObj.getAddressName());
        contactInfoDevice.setPostalcode(reqRegisterInfoObj.getPostalCode());
        contactInfoDevice.setStreetNo(reqRegisterInfoObj.getStreetNo());
        contactInfoDevice.setStreet(reqRegisterInfoObj.getStreet());
        contactInfoDevice.setCity(reqRegisterInfoObj.getCity());
        contactInfoDevice.setBuilding(reqRegisterInfoObj.getBuilding());
        contactInfoDevice.setPhone(reqRegisterInfoObj.getPhone());
        contactInfoDevice.setExt(reqRegisterInfoObj.getExt());
        contactInfoDevice.setSuiteAPT(reqRegisterInfoObj.getSuiteApt());
        contactInfoDevice.setDoorCode(reqRegisterInfoObj.getDoorCode());

        IContactInfoService infoService = (IContactInfoService) ServiceFinder.findBean(servletContext, IContactInfoService.BEAN_NAME);
        infoService.save(getContactInfo(contactInfoDevice, Integer.parseInt(userId)));

//        List<ContactInfo> list = infoService.getAll(reqRegisterInfoObj.getUserId());
        List<ContactInfo> list = infoService.getAll(Integer.parseInt(userId));
        int i = list.size();
        Collections.sort(list, new CustomComparator());
        ContactInfo contactInfo = (ContactInfo) list.get(i - 1);


        ContactInfoDevice contactInfoDeviceReturn = new ContactInfoDevice();
        contactInfoDeviceReturn.setId(contactInfo.getId());
        contactInfoDeviceReturn.setAddressScreenEN(contactInfo.getAddressScreenEN());
        contactInfoDeviceReturn.setPostalcode(contactInfo.getPostalcode());
        contactInfoDeviceReturn.setStreetNo(contactInfo.getStreetNo());
        contactInfoDeviceReturn.setStreet(contactInfo.getStreet());
        contactInfoDeviceReturn.setCity(contactInfo.getCity());
        contactInfoDeviceReturn.setBuilding(contactInfo.getBuilding());
        contactInfoDeviceReturn.setPhone(contactInfo.getPhone());
        contactInfoDeviceReturn.setExt(contactInfo.getExt());
        contactInfoDeviceReturn.setSuiteAPT(contactInfo.getSuiteAPT());
        contactInfoDeviceReturn.setDoorCode(contactInfo.getDoorCode());

        return contactInfoDeviceReturn;
    }

    public ContactInfoDevice addAddress(ServletContext servletContext, ContactInfoReqObj contactInfoReqObj) throws Exception {

        Locale locale = (Locale) servletContext.getAttribute(Globals.LOCALE_KEY);

        ContactInfoDevice contactInfoDevice = new ContactInfoDevice();
        contactInfoDevice.setId(Long.valueOf(contactInfoReqObj.getUserId()));
        contactInfoDevice.setAddressScreenEN(contactInfoReqObj.getAddressName());
        contactInfoDevice.setPostalcode(contactInfoReqObj.getPostalCode());
        contactInfoDevice.setStreetNo(contactInfoReqObj.getStreetNo());
        contactInfoDevice.setStreet(contactInfoReqObj.getStreet());
        contactInfoDevice.setCity(contactInfoReqObj.getCity());
        contactInfoDevice.setBuilding(contactInfoReqObj.getBuilding());
        contactInfoDevice.setPhone(contactInfoReqObj.getPhone());
        contactInfoDevice.setExt(contactInfoReqObj.getExt());
        contactInfoDevice.setSuiteAPT(contactInfoReqObj.getSuiteApt());
        contactInfoDevice.setDoorCode(contactInfoReqObj.getDoorCode());

        IContactInfoService infoService = (IContactInfoService) ServiceFinder.findBean(servletContext, IContactInfoService.BEAN_NAME);
        infoService.save(getContactInfo(contactInfoDevice, contactInfoDevice.getUserId()));

        List<ContactInfo> list = infoService.getAll(contactInfoReqObj.getUserId());
        int i = list.size();
        Collections.sort(list, new CustomComparator());
        ContactInfo contactInfo = (ContactInfo) list.get(i - 1);


        ContactInfoDevice contactInfoDeviceReturn = new ContactInfoDevice();
        contactInfoDeviceReturn.setId(contactInfo.getId());
        contactInfoDeviceReturn.setAddressScreenEN(contactInfo.getAddressScreenEN());
        contactInfoDeviceReturn.setPostalcode(contactInfo.getPostalcode());
        contactInfoDeviceReturn.setStreetNo(contactInfo.getStreetNo());
        contactInfoDeviceReturn.setStreet(contactInfo.getStreet());
        contactInfoDeviceReturn.setCity(contactInfo.getCity());
        contactInfoDeviceReturn.setBuilding(contactInfo.getBuilding());
        contactInfoDeviceReturn.setPhone(contactInfo.getPhone());
        contactInfoDeviceReturn.setExt(contactInfo.getExt());
        contactInfoDeviceReturn.setSuiteAPT(contactInfo.getSuiteAPT());
        contactInfoDeviceReturn.setDoorCode(contactInfo.getDoorCode());

        return contactInfoDevice;
    }
  public List<ContactInfoDevice> getAllContactInfo(ServletContext servletContext, ContactInfoReqObj contactInfoReqObj) throws Exception {

        Locale locale = (Locale) servletContext.getAttribute(Globals.LOCALE_KEY);
        List<ContactInfoDevice> contactInfoDeviceList =new ArrayList<>();
        IContactInfoService infoService = (IContactInfoService) ServiceFinder.findBean(servletContext, IContactInfoService.BEAN_NAME);
        List<ContactInfo> list = infoService.getAll(contactInfoReqObj.getUserId());

        for(ContactInfo contactInfo:list){
            ContactInfoDevice contactInfoDevice = new ContactInfoDevice();
            contactInfoDevice.setAddress(contactInfo.getAddressScreenEN());
            contactInfoDevice.setAddress(contactInfo.getAddressScreenEN());
            contactInfoDevice.setBuilding(contactInfo.getBuilding());
            contactInfoDevice.setCity(contactInfo.getCity());
            contactInfoDevice.setDoorCode(contactInfo.getDoorCode());
            contactInfoDevice.setExt(contactInfo.getExt());
            contactInfoDevice.setId(contactInfo.getId());
            contactInfoDevice.setUserId(contactInfo.getUserId());
            contactInfoDevice.setPhone(contactInfo.getPhone());
            contactInfoDevice.setStreet(contactInfo.getStreet());
            contactInfoDevice.setStreetNo(contactInfo.getStreetNo());
            contactInfoDevice.setPostalcode(contactInfo.getPostalcode());
            contactInfoDevice.setSuiteAPT(contactInfo.getSuiteAPT());
            contactInfoDeviceList.add(contactInfoDevice);
        }
        return contactInfoDeviceList;
    }

    private SubCategoryForDevice convertListToMenuSingleItem(MenuSingleItem menuSingleItem, Category cat, String context, String middlePath) {

        SubCategoryForDevice subCategoryForDevice = new SubCategoryForDevice();

        subCategoryForDevice.setId(pIdConvert(menuSingleItem.getId()));
        subCategoryForDevice.setMenuId(cat.getId());
        String categoryNameEN = menuSingleItem.getName(Locale.ENGLISH);
        subCategoryForDevice.setNameKeyEN(categoryNameEN);
        String categoryNameFR = menuSingleItem.getName(Locale.FRENCH);
        subCategoryForDevice.setNameKeyFR(categoryNameFR);
        String descriptionNameEN = menuSingleItem.getDescription(Locale.ENGLISH);
        subCategoryForDevice.setDescriptionKeyEN(descriptionNameEN);
        String descriptionNameFR = menuSingleItem.getDescription(Locale.FRENCH);
        subCategoryForDevice.setDescriptionKeyFR(descriptionNameFR);

        subCategoryForDevice.setPrice(menuSingleItem.getFormattedPrice());
        subCategoryForDevice.setFreeToppingNo(menuSingleItem.getFreeToppingNo());
        subCategoryForDevice.setFreeToppingPrice(menuSingleItem.getFreeToppingPrice());
        subCategoryForDevice.setGroupId(menuSingleItem.getGroupId());
        subCategoryForDevice.setIdType(String.valueOf(menuSingleItem.getIdType()));
        String prefixURL = context + middlePath;
        subCategoryForDevice.setImageURL(prefixURL + menuSingleItem.getImageURL());
        subCategoryForDevice.setProductNo(menuSingleItem.getProductNo());
        subCategoryForDevice.setSequence(menuSingleItem.getSequence());
        subCategoryForDevice.setSize(menuSingleItem.getSize());
        subCategoryForDevice.setTwoForOne(menuSingleItem.isTwoForOne());
        subCategoryForDevice.setPortion(menuSingleItem.isPortion());
        subCategoryForDevice.setPizza(menuSingleItem.isPizza());
        if(menuSingleItem.getIsRedeemable() == null)
            subCategoryForDevice.setIsRedeemable(false);
        else
            subCategoryForDevice.setIsRedeemable(menuSingleItem.getIsRedeemable());

        return subCategoryForDevice;
    }

    private SubCategoryForDevice convertListToCategoryItem(Category Category, String price, Category cat, String context, String middlePath) {

        SubCategoryForDevice subCategoryForDevice = new SubCategoryForDevice();

        subCategoryForDevice.setId(pIdConvert(Category.getId()));
        subCategoryForDevice.setMenuId(cat.getId());
        String categoryNameEN = Category.getName(Locale.ENGLISH);
        subCategoryForDevice.setNameKeyEN(categoryNameEN);
        String categoryNameFR = Category.getName(Locale.FRENCH);
        subCategoryForDevice.setNameKeyFR(categoryNameFR);
        String descriptionNameEN = Category.getDescription(Locale.ENGLISH);
        subCategoryForDevice.setDescriptionKeyEN(descriptionNameEN);
        String descriptionNameFR = Category.getDescription(Locale.FRENCH);
        subCategoryForDevice.setDescriptionKeyFR(descriptionNameFR);

        String priceWithDollar = "$" + price;
        subCategoryForDevice.setPrice(priceWithDollar);
        if(Category.getIsRedeemable() == null)
            subCategoryForDevice.setIsRedeemable(false);
        else
            subCategoryForDevice.setIsRedeemable(Category.getIsRedeemable());

        return subCategoryForDevice;
    }

    private SubCategoryForDevice convertListToMenuCombinedItem(CombinedMenuItem combinedMenuItem, Category cat, String context, String middlePath) {

        SubCategoryForDevice subCategoryForDevice = new SubCategoryForDevice();
        subCategoryForDevice.setProductNo(combinedMenuItem.getProductNo());
        subCategoryForDevice.setGroupId(combinedMenuItem.getGroupId());
        subCategoryForDevice.setMenuId(cat.getId());
        String categoryNameEN = combinedMenuItem.getName(Locale.ENGLISH);
        subCategoryForDevice.setNameKeyEN(categoryNameEN);
        String categoryNameFR = combinedMenuItem.getName(Locale.FRENCH);
        subCategoryForDevice.setNameKeyFR(categoryNameFR);
        String descriptionNameEN = combinedMenuItem.getDescription(Locale.ENGLISH);
        subCategoryForDevice.setDescriptionKeyEN(descriptionNameEN);
        String descriptionNameFR = combinedMenuItem.getDescription(Locale.FRENCH);
        subCategoryForDevice.setDescriptionKeyFR(descriptionNameFR);
        String price = CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem) combinedMenuItem)).toString();
        subCategoryForDevice.setPrice(price);
        String prefixURL = context + middlePath;
        subCategoryForDevice.setImageURL(prefixURL + combinedMenuItem.getImageURl());
        subCategoryForDevice.setSequence(combinedMenuItem.getSequence());
        subCategoryForDevice.setIsPrintable(combinedMenuItem.getPrintable());
        if(combinedMenuItem.getIsRedeemable() == null)
            subCategoryForDevice.setIsRedeemable(false);
        else
            subCategoryForDevice.setIsRedeemable(combinedMenuItem.getIsRedeemable());
        List<String> captionEN = combinedMenuItem.getCaptions(Locale.ENGLISH);
        subCategoryForDevice.setCaptionkeisEN(captionEN);
        List<String> captionFR = combinedMenuItem.getCaptions(Locale.FRENCH);
        subCategoryForDevice.setCaptionkeisFR(captionFR);

        return subCategoryForDevice;
    }

    class CategoryComperator implements Comparator {
        public int compare(Object o1, Object o2) {
            CategoryForDevice c1 = (CategoryForDevice) o1;
            return c1.compareTo(o2);
        }
    }

    private ContactInfo getContactInfo(ContactInfoDevice contactInfoDevice, int userId) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setUserId(userId);
        contactInfo.setAddressScreenEN(contactInfoDevice.getAddress());
        contactInfo.setBuilding(contactInfoDevice.getBuilding());
        contactInfo.setCity(contactInfoDevice.getCity());
        contactInfo.setDoorCode(contactInfoDevice.getDoorCode());
        contactInfo.setExt(contactInfoDevice.getExt());
        contactInfo.setPhone(contactInfoDevice.getPhone());
        contactInfo.setPostalcode(contactInfoDevice.getPostalcode());
        contactInfo.setStreet(contactInfoDevice.getStreet());
        contactInfo.setStreetNo(contactInfoDevice.getStreetNo());
        contactInfo.setSuiteAPT(contactInfoDevice.getSuiteAPT());
        contactInfo.setAddressScreenEN(contactInfoDevice.getAddressScreenEN());
        return contactInfo;
    }


    public class CustomComparator implements Comparator<ContactInfo> {
        @Override
        public int compare(ContactInfo o1, ContactInfo o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

    public List<Store> getAllStores(ServletContext servletContext, String menuType) {
        List<Store> storeList = InMemoryData.getAllDeviceStores(servletContext);
        return storeList;
    }

    List<Popular> populars;

    public List<String> getPopulars(ServletContext servletContext, String menuType) {
        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();
        IPopularService iPopularService = (IPopularService) ServiceFinder.findBean(servletContext, IPopularService.BEAN_NAME);
        try {
            populars = iPopularService.getActivePopulars();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        List<String> popularsList = new ArrayList<String>();


        Integer menuItemId;
        String preId;
        for (Popular popular : populars) {
            preId = "";
            menuItemId = popular.getMenuitemId();
            preId=menuItemId.toString();
            popularsList.add(pIdConvert(preId));
        }

        return popularsList;
    }
    public String pIdConvert(String id){

        String pId="";
        Integer idInt=Integer.parseInt(id);

        if(!(id==null)){
            if( (idInt>99)&&(Integer.parseInt(id)<1000)){pId="0"+idInt.toString();}
            if( (idInt>9)&&(Integer.parseInt(id)<100)){pId="00"+idInt.toString();}
            if( (idInt>=0)&&(Integer.parseInt(id)<10)){pId="000"+idInt.toString();}}
        return pId;
    }
    public List<Tax> getTax(ServletContext servletContext, String menuType) {

        List<Tax> taxList = InMemoryData.getTaxList();
        return taxList;
    }
    public List<Map<String,Float>> getdpDollarsWeeklyList(ServletContext servletContext, String menuType) {
        IDollarService dollarService;
        Locale locale = Locale.ENGLISH;
        dollarService = (IDollarService)  ServiceFinder.findBean(servletContext,IDollarService.BEAN_NAME);
        List<Map<String,Float>> dpDollarsWeeklyList = dollarService.getDpDollarsWeekly(locale);
        return dpDollarsWeeklyList;
    }

    public UserForDevice getDPDollarPerUser(ServletContext servletContext, String uid) {
        UserForDevice userForDevice = new UserForDevice();
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        IDollarService dollarService = (IDollarService) ServiceFinder.findBean(servletContext, IDollarService.BEAN_NAME);

        List<User> users=InMemoryData.getUserList();

        try {
            User user = userService.findById(Integer.parseInt(uid));
            if (user == null) {
                return userForDevice;
            } else {
             List<DpDollarHistory> listDpDollarHistory=new ArrayList<>();
                Double dpDollarAmount= new Double(0.00);
                listDpDollarHistory =  user.getDpDollarHistories();
                if(listDpDollarHistory.size()>0){
                 dpDollarAmount=user.getDpDollarHistories().get(listDpDollarHistory.size()-1).getBalance();
                }
                userForDevice.setDpDollar(dpDollarAmount.toString());
                userForDevice.setEmail(user.getEmail());
                if(user.getBirthDateStr()!=null){
                userForDevice.setBirthday(user.getBirthDateStr());
                }
                userForDevice.setFirstName(user.getFirstName());
                userForDevice.setLastName(user.getLastName());
                if(user.getTitle().equals(User.Title.FEMALE)){
                    userForDevice.setTitle(UserForDevice.Title.FEMALE);
                }else  if(user.getTitle().equals(User.Title.MALE)) {userForDevice.setTitle(UserForDevice.Title.MALE);
                }
            }
        } catch (Exception e) {
            return userForDevice;
        }
        return userForDevice;
    }

    public  List<SubCategoryForDevice> getSuggestions(ServletContext servletContext,ReqOrderObj reqOrderObj){
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();

        List<Suggestion> suggestionsList= new ArrayList<>();
        List<SubCategoryForDevice> subCategoryList= new ArrayList<>();
        Basket basket=new Basket();
        List<BasketItem> basketItems=new ArrayList<>();
        for (int i = 0; i < reqOrderObj.getBasketItem().size(); i++) {
            BasketItem basketItem = new BasketItem();
            BasketItemForOrder tempItem = reqOrderObj.getBasketItem().get(i);
            if (tempItem.getClassType().equalsIgnoreCase("Combined")) {
                BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
                basketCombinedItem.setGroupIdRef(tempItem.getGroupIdRefrence());
                basketCombinedItem.setProductNoRef(tempItem.getProductNoRefrence());
                basketCombinedItem.setQuantity(Byte.parseByte(tempItem.getQuantity()));
                List<BasketSingleItem> basketSingleItems = new ArrayList<>();
                for (int j = 0; j < tempItem.getBasketSingleItemList().size(); j++) {
                    BasketSingleItemForOrder basketSingleItemForOrder = null;
                    basketSingleItemForOrder = tempItem.getBasketSingleItemList().get(j);
                    BasketSingleItem basketSingleItem = new BasketSingleItem();
                    basketSingleItem.setId(basketSingleItemForOrder.getId());
                    basketSingleItem.setGroupId(basketSingleItemForOrder.getGroupId());
                    Map<Integer, String> map = new HashMap<>();
                    for (int k = 0; k < basketSingleItemForOrder.getSelectedToppings().size(); k++) {
                        String st = "";
                        String st2 = "";
                        st = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("key");
                        st2 = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("value");
                        map.put(Integer.parseInt(st), st2);
                    }
                    basketSingleItem.setSelectedToppingMap(map);
                    basketSingleItem.setQuantity(basketSingleItemForOrder.getQuantity());
                    basketSingleItems.add(basketSingleItem);
                }
                basketCombinedItem.setBasketSingleItemList(basketSingleItems);
                basketItem.setObject(basketCombinedItem);
                basketItem.setClassType(BasketCombinedItem.class);
            }
            if (tempItem.getClassType().equalsIgnoreCase("Single")) {
                BasketSingleItem basketSingleItem = new BasketSingleItem();
                List<BasketSingleItem> basketSingleItems = new ArrayList<>();
                for (int j = 0; j < tempItem.getBasketSingleItemList().size(); j++) {
                    BasketSingleItemForOrder basketSingleItemForOrder = null;
                    basketSingleItemForOrder = tempItem.getBasketSingleItemList().get(j);
                    basketSingleItem.setId(basketSingleItemForOrder.getId());
                    basketSingleItem.setGroupId(basketSingleItemForOrder.getGroupId());
                    Map<Integer, String> map = new HashMap<>();
                    for (int k = 0; k < basketSingleItemForOrder.getSelectedToppings().size(); k++) {
                        String st = "";
                        String st2 = "";
                        st = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("key");
                        st2 = (basketSingleItemForOrder.getSelectedToppings()).get(k).get("value");
                        map.put(Integer.parseInt(st), st2);
                    }
                    basketSingleItem.setSelectedToppingMap(map);
                    basketSingleItem.setQuantity(basketSingleItemForOrder.getQuantity());
                    basketSingleItems.add(basketSingleItem);
                }
                basketItem.setObject(basketSingleItem);
                basketItem.setClassType(BasketSingleItem.class);
            }

            basketItems.add(basketItem);
        }
        basket.setBasketItemList(basketItems);
        suggestionsList = CollectionsUtils.top(SuggestionsHelper.getSuggestionsForBasket(basket), 6);
        for(Suggestion suggestion:suggestionsList){
            if(suggestion.getMenuSingleItem()!=null){
            SubCategoryForDevice subCategoryForDevice=new SubCategoryForDevice();
            subCategoryForDevice.setId(suggestion.getMenuSingleItem().getId());
            subCategoryForDevice.setType(suggestion.getMenuSingleItem().getClass().getSimpleName());
            subCategoryForDevice.setGroupId(suggestion.getMenuSingleItem().getGroupId());
            subCategoryForDevice.setProductNo(suggestion.getMenuSingleItem().getProductNo());
            subCategoryForDevice.setNameKeyEN(suggestion.getMenuSingleItem().getName(Locale.ENGLISH));
            subCategoryForDevice.setNameKeyFR(suggestion.getMenuSingleItem().getName(Locale.FRENCH));
            subCategoryForDevice.setPrice(suggestion.getMenuSingleItem().getPrice().toString());
            String prefixURL = context + middlePath;
            subCategoryForDevice.setImageURL(prefixURL+suggestion.getMenuSingleItem().getImageURL());
            if(suggestion.getMenuSingleItem().getIsRedeemable() == null)
                subCategoryForDevice.setIsRedeemable(false);
            else
                subCategoryForDevice.setIsRedeemable(suggestion.getMenuSingleItem().getIsRedeemable());
            subCategoryForDevice.setSequence(suggestion.getMenuSingleItem().getSequence());
            subCategoryList.add(subCategoryForDevice);
            }
        }
        return subCategoryList;
    }


    public CityAndStreetForDevice getCityAndStreet(ServletContext servletContext, ReqStreetObj reqStreetObj) {
        String context = WebApplicationContextUtils.getWebApplicationContext(servletContext).getServletContext().getContextPath();
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        CityAndStreetForDevice   cityAndStreetForDevice=new CityAndStreetForDevice();
        List<String> cityAndStreet = null;
        String postalCode =reqStreetObj.getPostalCode();
        String streetNo = reqStreetObj.getStreetNo();
        Pattern p = Pattern.compile("[\\d]+");
        Matcher m = p.matcher(streetNo);
        if (m.matches()) {
            cityAndStreet = userService.findStreetNameByPostalCodeAndStreetNo(postalCode, streetNo);
            if (cityAndStreet != null && cityAndStreet.size() > 1) {
                cityAndStreetForDevice.setStreet(cityAndStreet.get(0));
                cityAndStreetForDevice.setCity(cityAndStreet.get(1));
            }
        }
        return cityAndStreetForDevice;
    }


    public  CouponForDevice getCoupon(ServletContext servletContext, ReqCouponObj reqCouponObj){
        CouponForDevice  couponForDevice=new CouponForDevice();
        ICouponService couponService = (ICouponService) ServiceFinder.findBean(servletContext, ICouponService.BEAN_NAME);
        List<Coupon> coupons=couponService.findAll();
        String coupon=reqCouponObj.getCoupon();
        for (Coupon dblCoupon : coupons) {
            if(coupon.equalsIgnoreCase(dblCoupon.getCouponName())){
                couponForDevice.setType(dblCoupon.getCouponType());
                couponForDevice.setValue(dblCoupon.getAmount());
            }
            if (couponForDevice.getType()==null){
                couponForDevice.setType("INVALID");
                couponForDevice.setValue((float)0);
            }
        }
        return couponForDevice;
    }
    public RegisterInfo editProfile(ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj){
        RegisterInfo userForDevice = new RegisterInfo();
        ContactInfo contactInfo = new ContactInfo();
        IUserService userService = (IUserService) ServiceFinder.findBean(servletContext, IUserService.BEAN_NAME);
        IContactInfoService infoService = (IContactInfoService) ServiceFinder.findBean(servletContext, IContactInfoService.BEAN_NAME);
        if(reqRegisterInfoObj.getId()==-1){
            userForDevice.setId(Long.valueOf(-1));
            try {
                User user = userService.findById(reqRegisterInfoObj.getUserId());
                if (user == null) {
                    return userForDevice;
                } else {
//                {Default, userId:””,firstName:””***, lastName:””*** , birthday: “yyyy-mm-dd” ***  , title:””* ,company:””}
                    user.setFirstName(reqRegisterInfoObj.getFirstName());
                    user.setLastName(reqRegisterInfoObj.getLastName());
                    user.setBirthDateStr(reqRegisterInfoObj.getBirthday());

                    if(reqRegisterInfoObj.getTitle().equalsIgnoreCase("MALE")){
                        user.setTitle(User.Title.MALE);
                    }else if(reqRegisterInfoObj.getTitle().equalsIgnoreCase("FEMALE")){
                        user.setTitle(User.Title.FEMALE);
                    }
                    user.setCompany(reqRegisterInfoObj.getCompany());
                    if (reqRegisterInfoObj.getSubscribe().equalsIgnoreCase("False")){
                        user.setSubscribed(Boolean.FALSE);
                    }else { user.setSubscribed(Boolean.TRUE);}

                    userService.update(user);
                    user=userService.findById(reqRegisterInfoObj.getUserId());
                        userForDevice.setUserId(user.getId());
                        userForDevice.setFirstName(user.getFirstName());
                        userForDevice.setLastName(user.getLastName());
                    if(user.getSubscribed().equals(Boolean.TRUE)){
                        userForDevice.setSubscribe("True");
                    } else {
                        userForDevice.setSubscribe("False");
                    }
                    userForDevice.setBirthday(String.valueOf(user.getBirthDateStr()));
                        if (user.getTitle()== User.Title.MALE) {
                            userForDevice.setTitle("MALE");
                        }
                        if (user.getTitle()== User.Title.FEMALE) {
                            userForDevice.setTitle("FEMALE");
                        }
                        userForDevice.setCompany(user.getCompany());
                        userForDevice.setEmail(user.getEmail());
                    }
            }
            catch (Exception e) {
                return userForDevice;
            }
        }   else{

            contactInfo.setId(reqRegisterInfoObj.getId());
            contactInfo.setUserId(reqRegisterInfoObj.getUserId());
            contactInfo.setAddressScreenEN(reqRegisterInfoObj.getAddressName());
            contactInfo.setBuilding(reqRegisterInfoObj.getBuilding());
            contactInfo.setCity(reqRegisterInfoObj.getCity());
            contactInfo.setDoorCode(reqRegisterInfoObj.getDoorCode());
            contactInfo.setExt(reqRegisterInfoObj.getExt());
            contactInfo.setPhone(reqRegisterInfoObj.getPhone());
            contactInfo.setPostalcode(reqRegisterInfoObj.getPostalCode());
            contactInfo.setStreet(reqRegisterInfoObj.getStreet());
            contactInfo.setStreetNo(reqRegisterInfoObj.getStreetNo());
            contactInfo.setSuiteAPT(reqRegisterInfoObj.getSuiteApt());
            infoService.update(contactInfo);
            List<ContactInfo> contactInfoList=infoService.getAll(reqRegisterInfoObj.getUserId());
            for(ContactInfo contactInfotemp:contactInfoList){

                if(contactInfotemp.getId()==reqRegisterInfoObj.getId()){
                    userForDevice.setId(Long.valueOf(contactInfotemp.getId()));
                    userForDevice.setAddress(contactInfotemp.getAddressScreenEN());
                    userForDevice.setAddressScreenEN(contactInfotemp.getAddressScreenEN());
                    userForDevice.setBuilding(contactInfotemp.getBuilding());
                    userForDevice.setCity(contactInfotemp.getCity());
                    userForDevice.setDoorCode(contactInfotemp.getDoorCode());
                    userForDevice.setPhone(contactInfotemp.getPhone());
                    userForDevice.setExt(contactInfotemp.getExt());
                    userForDevice.setPostalcode(contactInfotemp.getPostalcode());
                    userForDevice.setStreet(contactInfotemp.getStreet());
                    userForDevice.setStreetNo(contactInfotemp.getStreetNo());
                    userForDevice.setSuiteAPT(contactInfotemp.getSuiteAPT());
                }
            }
        }
        return userForDevice;
    }

}
