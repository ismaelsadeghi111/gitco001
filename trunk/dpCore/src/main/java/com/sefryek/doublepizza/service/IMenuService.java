package com.sefryek.doublepizza.service;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Category;
import com.sefryek.doublepizza.model.PopularCategory;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 16, 2011
 * Time: 10:44:04 PM
 */
public interface IMenuService {

    public static String BEAN_NAME = "menuService";

    public List<Category> getMenuCategoryList(List<Map<String, Object>> webCategories) throws ServiceException;

    public List<PopularCategory> getMenuCategoryListByName(String foodName,String fromDate,String toDate) throws ServiceException;

    public List<Category> getSpecialMenuCategoryList(List<Map<String, Object>> termTBL) throws ServiceException;

    public List<Category> getSpecialMenuCategoryListByDate() throws ServiceException;

    public List<Map<String,Object>> loadModifierTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadModLinkTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadMenuItemTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadProdLinkTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadWebCategoriesTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadTermTable() throws DAOException, ServiceException;

    public List<Object[]> loadToppingCategoryTable() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadModifierPresetModLinkJoinTBL() throws DAOException;

    public List<Map<String,Object>> loadTermGrptermMenuitemJoinTBL() throws DAOException;

    public List<Map<String,Object>> loadWebcategWebmenuMenuitemJoinTBL() throws DAOException;

    public List<Map<String,Object>> loadModifierContentsJoinTBL() throws DAOException;

    public List<Map<String,Object>> loadModifierPresetJoinTBL() throws DAOException;

}


