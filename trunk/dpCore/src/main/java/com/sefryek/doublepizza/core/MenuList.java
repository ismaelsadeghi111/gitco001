package com.sefryek.doublepizza.core;

import com.sefryek.doublepizza.model.Menu;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 28, 2012
 * Time: 12:01:29 PM
 */
public class MenuList {

    private static Logger logger = Logger.getLogger(MenuList.class);
    private static List<Menu> menu;
    private static List<Map<String,Object>> webCategoriesTBL;
    private static List<Map<String,Object>> termTBL;
    private static List<Map<String,Object>> menuItem_groupTermJOINTBL;
    private static List<Map<String,Object>> webMenuItem_webCategories_menuItemJOINTBL;
    private static List<Map<String,Object>> modifier_preset_modLinkJOINTBL;

    public static List<Map<String, Object>> getWebCategoriesTBL() {
        return webCategoriesTBL;
    }

    public static void setWebCategoriesTBL(List<Map<String, Object>> webCategoriesTBL) {
        MenuList.webCategoriesTBL = webCategoriesTBL;
    }

    public static List<Map<String, Object>> getTermTBL() {
        return termTBL;
    }

    public static void setTermTBL(List<Map<String, Object>> termTBL) {
        MenuList.termTBL = termTBL;
    }

    public static List<Map<String, Object>> getMenuItem_groupTermJOINTBL() {
        return menuItem_groupTermJOINTBL;
    }

    public static void setMenuItem_groupTermJOINTBL(List<Map<String, Object>> menuItem_groupTermJOINTBL) {
        MenuList.menuItem_groupTermJOINTBL = menuItem_groupTermJOINTBL;
    }

    public static List<Map<String, Object>> getWebMenuItem_webCategories_menuItemJOINTBL() {
        return webMenuItem_webCategories_menuItemJOINTBL;
    }

    public static void setWebMenuItem_webCategories_menuItemJOINTBL(List<Map<String, Object>> webMenuItem_webCategories_menuItemJOINTBL) {
        MenuList.webMenuItem_webCategories_menuItemJOINTBL = webMenuItem_webCategories_menuItemJOINTBL;
    }

    public static List<Map<String, Object>> getModifier_preset_modLinkJOINTBL() {
        return modifier_preset_modLinkJOINTBL;
    }

    public static void setModifier_preset_modLinkJOINTBL(List<Map<String, Object>> modifier_preset_modLinkJOINTBL) {
        MenuList.modifier_preset_modLinkJOINTBL = modifier_preset_modLinkJOINTBL;
    }

    public static List<Menu> getMenu() {
        return menu;
    }

    public static void setMenu(List<Menu> menu) {
        MenuList.menu = menu;
    }

    public static List<Map<String, Object>> findByProperty(String table,String column,Object value) {

        List<Map<String, Object>> resultList= new ArrayList();
        try{

            if(table.equals(Constant.JOIN_TABLE_LIST.groupTerm_menuItem_JoinTBL.toString())){
                for(Map<String, Object> row:menuItem_groupTermJOINTBL){
                    if(row.get(column)!=null){
                        if(row.get(column).toString().equals(value)){
                            resultList.add(row);
                        }
                    }
                }
            }

            if(table.equals(Constant.JOIN_TABLE_LIST.webMenuItem_menuItem_JoinTBL.toString())){
                for(Map<String, Object> row:webMenuItem_webCategories_menuItemJOINTBL){
                    if(row.get(column)!=null){
                        if(row.get(column).toString().equals(value)){
                            resultList.add(row);
                        }
                    }
                }
            }

            if(table.equals(Constant.JOIN_TABLE_LIST.modifier_preset_modLink_JoinTBL.toString())){
                for(Map<String, Object> row:modifier_preset_modLinkJOINTBL){
                    if(row.get(column)!=null){
                        if(row.get(column).toString().equals(value)){
                            resultList.add(row);
                        }
                    }
                }
            }

        }catch (Exception e){
            logger.debug("Debug: search process has been failed.\n cause: " + e.getMessage());
        }
        return resultList;
    }
}
