package com.sefryek.doublepizza.service.implementation;

import java.util.*;

import com.sefryek.doublepizza.service.IMenuService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.dao.MenuDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.common.MessageUtil;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 13, 2011
 * Time: 7:52:37 AM
 */
public class MenuServiceImpl implements IMenuService {

    private static Logger logger = Logger.getLogger(MenuServiceImpl.class);

    private MenuDAO menuDAO;

    public void setMenuDAO(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<Category> getMenuCategoryList() throws ServiceException {

        List<Category> categories = new ArrayList<Category>();
        Integer categoryId = -1;

        try {

            List<Map<String, Object>> webCategoriesTable = InMemoryData.getWebCategoriesTBL();
            InMemoryData.log = "";

            for (Map<String, Object> tblRow : webCategoriesTable) {

                try {
                    categoryId = Integer.parseInt(tblRow.get(Constant.WEBCATEGORIES_ID).toString());

                } catch (Exception e) {
                    categoryId = null;
                }

                // if main Id is null or empty, then its record is not valid and dont insert it.
                if (categoryId != null && !categoryId.toString().equals("")) {

                    String englishName = tblRow.get(Constant.WEBCATEGORIES_NAME_EN) != null ?
                            tblRow.get(Constant.WEBCATEGORIES_NAME_EN).toString() : null;

                    String frenchName = tblRow.get(Constant.WEBCATEGORIES_NAME_FR) != null ?
                            tblRow.get(Constant.WEBCATEGORIES_NAME_FR).toString() : null;

                    String englishDescription = tblRow.get(Constant.WEBCATEGORIES_DESCRIPTION_EN) != null ?
                            tblRow.get(Constant.WEBCATEGORIES_DESCRIPTION_EN).toString() : Constant.DESC_ALTERNATIVE;

                    String frenchDescription = tblRow.get(Constant.WEBCATEGORIES_DESCRIPTION_FR) != null ?
                            tblRow.get(Constant.WEBCATEGORIES_DESCRIPTION_FR).toString() : Constant.DESC_ALTERNATIVE;

                    if (englishName == null || frenchName == null) {
                        logger.error("There are no name for category id: " + categoryId);
                        continue;
                    }

                    //get unique resource key
                    String nameKey = InMemoryData.getHashMapKeyAndSaveWords(Constant.TABLE_LIST.WebCategories.toString(),
                            categoryId.toString(),
                            null,
                            Constant.WEBCATEGORIES_NAME_KEY, englishName, frenchName);

                    String descKey = InMemoryData.getHashMapKeyAndSaveWords(
                            Constant.TABLE_LIST.WebCategories.toString(), categoryId.toString(), null,
                            Constant.WEBCATEGORIES_DESCRIPTION_KEY, englishDescription, frenchDescription);

                    InMemoryData.log += "\n category " + categoryId + ", name " + nameKey + ", descKey";
                    Category category = new Category(
                            String.valueOf(categoryId), nameKey, descKey,
                            (tblRow.get(Constant.WEBCATEGORIES_SEQUENCE) != null) &&
                                    !(tblRow.get(Constant.WEBCATEGORIES_SEQUENCE).toString().equals("")) ?
                                    Integer.valueOf(tblRow.get(Constant.WEBCATEGORIES_SEQUENCE).toString()) :
                                    Integer.parseInt(Constant.SEQ_ALTERNATIVE),
                            tblRow.get(Constant.WEBCATEGORIES_PICTURE).toString());


                    //recursive fetch of subCategories and MenuItems per each category.
                    List<SubCategory> subCategories = getSubCategoryList(category.getId(), false);

                    category.setId("1" + category.getId());

                    if (subCategories != null && subCategories.size() != 0) {
                        category.setSubCategoryList(subCategories);
                        categories.add(category);
                    }
                }
            }

        } catch (Exception e) {
            throw new ServiceException(e, e.getClass(), "Can not retrieve (MENU) menu because of a problem in categoryId: " + categoryId);
        }
        return categories;
    }

    public List<Category> getSpecialMenuCategoryList() throws ServiceException {

        List<Category> categories = new ArrayList<Category>();
        Integer categoryId = -1;

        try {

            List<Map<String, Object>> table = InMemoryData.getTermTBL();
            for (Map<String, Object> tblRow : table) {

                try {
                    categoryId = Integer.parseInt(tblRow.get(Constant.TERM_ID).toString());
                } catch (Exception e) {
                    categoryId = null;
                }

                // if main Id is null or empty, then its record is not valid and dont insert it.
                if (categoryId != null && !categoryId.toString().equals("")) {


                    String englishName = tblRow.get(Constant.TERM_NAME_EN) != null ?
                            tblRow.get(Constant.TERM_NAME_EN).toString() : null;

                    String frenchName = tblRow.get(Constant.TERM_NAME_FR) != null ?
                            tblRow.get(Constant.TERM_NAME_FR).toString() : null;

                    String englishDescription = tblRow.get(Constant.TERM_DESCRIPTION_EN) != null ?
                            tblRow.get(Constant.TERM_DESCRIPTION_EN).toString() : Constant.DESC_ALTERNATIVE;

                    String frenchDescription = tblRow.get(Constant.TERM_DESCRIPTION_FR) != null ?
                            tblRow.get(Constant.TERM_DESCRIPTION_FR).toString() : Constant.DESC_ALTERNATIVE;

                    if (englishName == null || frenchName == null) {
                        logger.error("There are no name for category id: " + categoryId);
                        continue;
                    }

                    //get unique resource key
                    String nameKey = InMemoryData.getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Term.toString(),
                            categoryId.toString(), null, Constant.TERM_NAME_KEY, englishName, frenchName);

                    String descKey = InMemoryData.getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Term.toString(),
                            categoryId.toString(), null, Constant.TERM_DESCRIPTION_KEY, englishDescription, frenchDescription);

                    Category category = new Category(
                            String.valueOf(categoryId), nameKey, descKey, (tblRow.get(Constant.TERM_ID) != null) &&
                            !(tblRow.get(Constant.TERM_ID).toString().equals("")) ?
                            Integer.valueOf(tblRow.get(Constant.TERM_ID).toString()) :
                            Integer.parseInt(Constant.SEQ_ALTERNATIVE),
                            tblRow.get(Constant.TERM_IMAGE).toString());

                    //recursive fetch of subCategories and MenuItems per each category.
                    List<SubCategory> subCategories = getSubCategoryList(category.getId(), true);

                    if (subCategories != null && subCategories.size() != 0) {
                        category.setSubCategoryList(subCategories);
                        categories.add(category);
                    }

                }
            }

        } catch (Exception e) {
            logger.debug("Can not retrieve (SPECIAL) menu." + " on categoryID: " + categoryId + "\n cause: " + e.getMessage());
            throw new ServiceException(e.getCause(), e.getClass(), e.getMessage());
        }
        return categories;
    }

    /**
     * @param item
     * @return returns true if this item is a combined and false if not.
     * @throws com.sefryek.doublepizza.service.exception.ServiceException
     *
     */
    private Boolean isCombinedMenuItem(Map<String, Object> item) throws ServiceException {

        Boolean result = false;

        try {
            // menuItems that has preset = 1 or twoForOne= 1 --> or special = 1 means this item is a combinedItem.
            if (((Boolean) item.get(Constant.MENUITEM_PRESET)) || (Boolean) item.get(Constant.MENUITEM_TWOFORONE)
                    || (Boolean) item.get(Constant.MENUITEM_SPECIAL)) {
                result = true;

            }

        } catch (Exception e) {
            logger.debug("Debug: the execution of singleItemTypeChecker method has been failed.\n cause: " + e.getMessage());
            System.out.println("productNo: " + (Integer) item.get(Constant.MENUITEM_PRODUCTNO));
            e.printStackTrace();
            throw new ServiceException(e.getCause(), e.getClass(), e.getMessage());
        }
        return result;
    }

    private Boolean isMenuSingleItem(Map<String, Object> manuItemRow) throws ServiceException {
        boolean isSingleItem = false;
        boolean foundSequenceOne = false;

        try {
            if (!isCombinedMenuItem(manuItemRow)) {
                List<Map<String, Object>> prodLink =
                        InMemoryData.getProdlinkJoinedWithMenuItemAt(manuItemRow.get(Constant.MENUITEM_PRODUCTNO).toString());

                for (Map<String, Object> aProdLink : prodLink) {
                    Integer sequence = (Integer) (aProdLink.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_SEQ));

                    if (sequence.equals(1)) {
                        foundSequenceOne = true;
                        Integer forced = (Integer) aProdLink.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_FORCED);
                        if (forced.equals(1))
                            isSingleItem = true;

                    }
                }
            }

            if (!foundSequenceOne)
                isSingleItem = true;

        } catch (Exception e) {
            logger.debug("Debug:the execution of categoryChecker method has been failed.\n cause: " + e.getMessage());
            throw new ServiceException(e.getCause(), e.getClass(), e.getMessage());
        }

        return isSingleItem;
    }

    private boolean isCategory(Map<String, Object> menuItemRow) throws ServiceException {

        boolean isCategory = false;

        try {
            if (!isCombinedMenuItem(menuItemRow) && !(Boolean) menuItemRow.get(Constant.MENUITEM_PRESET)) {
                List<Map<String, Object>> prodLink =
                        InMemoryData.getProdlinkJoinedWithMenuItemAt(menuItemRow.get(Constant.MENUITEM_PRODUCTNO).toString());

                for (Map<String, Object> aProdLink : prodLink) {
                    Integer sequence = (Integer) (aProdLink.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_SEQ));

                    if (sequence.equals(1)) {
                        Integer forced = (Integer) aProdLink.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_FORCED);
                        if (forced.equals(0))
                            isCategory = true;

                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Debug:the execution of categoryChecker method has been failed.\n cause: " + e.getMessage());
            throw new ServiceException(e.getCause(), e.getClass(), e.getMessage());
        }

        return isCategory;
    }

    /**
     * @param categId
     * @param isSpecial
     * @return returns List of sub category per each categoryId.
     * @throws com.sefryek.doublepizza.service.exception.ServiceException
     *
     */
    private List<SubCategory> getSubCategoryList(String categId, boolean isSpecial) throws Exception {

        List<Map<String, Object>> table;
        List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
        Integer productNo = -1, groupId = -1;

        if (categId != null && !categId.equals("")) {

            if (isSpecial) {
                table = InMemoryData.findById(Constant.JOIN_TABLE_LIST.groupTerm_menuItem_JoinTBL.toString(),
                        Constant.TERM_ID, Integer.valueOf(categId));

            } else {
                table = InMemoryData.findById(Constant.JOIN_TABLE_LIST.webMenuItem_menuItem_JoinTBL.toString(),
                        Constant.WEBCATEGORIES_ID, Integer.valueOf(categId));

            }

            try {

                for (Map<String, Object> tblRow : table) {

                    //  if ((Boolean) tblRow.get(Constant.MENUITEM_WEB_STATUS)) {

                    SubCategory subCategory = new SubCategory();

                    try {
                        productNo = Integer.parseInt(tblRow.get(Constant.MENUITEM_PRODUCTNO).toString());

                    } catch (Exception innerE) {
                        productNo = null;
                        logger.error(innerE);
                        continue;
                    }

                    try {
                        groupId = Integer.parseInt(tblRow.get(Constant.MENUITEM_GROUPID).toString());

                    } catch (Exception innerE) {
                        groupId = null;

                    }

                    String englishName = tblRow.get(Constant.MENUITEM_NAME_EN) != null ?
                            tblRow.get(Constant.MENUITEM_NAME_EN).toString() : null;

                    String frenchName = tblRow.get(Constant.MENUITEM_NAME_FR) != null ?
                            tblRow.get(Constant.MENUITEM_NAME_FR).toString() : null;

                    String englishDescription = tblRow.get(Constant.MENUITEM_DESCRIPTION_EN) != null ?
                            tblRow.get(Constant.MENUITEM_DESCRIPTION_EN).toString() : Constant.DESC_ALTERNATIVE;

                    String frenchDescription = tblRow.get(Constant.MENUITEM_DESCRIPTION_FR) != null ?
                            tblRow.get(Constant.MENUITEM_DESCRIPTION_FR).toString() : Constant.DESC_ALTERNATIVE;

                    //if main Id is null or empty, then its record is not valid and dont insert it.
                    if ((productNo != null && !productNo.toString().equals("")) && englishName != null && frenchName != null) {

                        //get unique resource key
                        String nameKey = InMemoryData.getHashMapKeyAndSaveWords(Constant.TABLE_LIST.MenuItem.toString(),
                                productNo, groupId, Constant.MENUITEM_NAME_KEY, englishName, frenchName);

                        String descKey = InMemoryData.getHashMapKeyAndSaveWords(
                                Constant.TABLE_LIST.MenuItem.toString(), productNo, groupId,
                                Constant.MENUITEM_DESCRIPTION_KEY, englishDescription, frenchDescription);

                        if (isCombinedMenuItem(tblRow)) {

                            boolean preset = (Boolean) tblRow.get(Constant.MENUITEM_PRESET);
                            boolean noPrint = (Boolean) tblRow.get(Constant.MENUITEM_NO_PRINT);

                            CombinedMenuItem combinedMenuItem = new CombinedMenuItem(productNo.toString(),
                                    groupId != null ? groupId.toString() :
                                            Constant.NAME_ALTERNATIVE, nameKey, descKey,
                                    tblRow.get(Constant.MENUITEM_SEQUENCE) != null ?
                                            Integer.parseInt(tblRow.get(Constant.MENUITEM_SEQUENCE).toString()) :
                                            Integer.parseInt(Constant.SEQ_ALTERNATIVE),
                                    (preset || !noPrint), preset, (String) tblRow.get(Constant.MENUITEM_WEBIMAGE));

                            //get preset MenuSingleItems for wanted combiendMenuItem
                            InMemoryData.fillCombinedItem(combinedMenuItem, (Boolean) tblRow.get(Constant.MENUITEM_TWOFORONE));

                            subCategory.setType(CombinedMenuItem.class);
                            subCategory.setObject(combinedMenuItem);

                            subCategoryList.add(subCategory);

                        } else if (isMenuSingleItem(tblRow)) {
                            MenuSingleItem menuSingleItem = InMemoryData.getMenuSingleItemInstance(tblRow, categId,
                                    "" + Constant.TABLE_LIST.MenuItem);

                            subCategory.setType(MenuSingleItem.class);
                            subCategory.setObject(menuSingleItem);

                            subCategoryList.add(subCategory);


                        } else if (isCategory(tblRow)) {
                            Category category = new Category(productNo.toString(), nameKey, descKey,
                                    tblRow.get(Constant.MENUITEM_SEQUENCE) != null ?
                                            Integer.valueOf(tblRow.get(Constant.MENUITEM_SEQUENCE).toString()) :
                                            Integer.valueOf(Constant.SEQ_ALTERNATIVE),
                                    tblRow.get(Constant.MENUITEM_WEBIMAGE).toString());

                            category.setSubCategoryList(InMemoryData.getMenuSingleItemsofInnerCategory(category.getId()));

                            subCategory.setType(Category.class);
                            subCategory.setObject(category);

                            subCategoryList.add(subCategory);

                        } else {
                            logger.error(MessageUtil.get("exception.data.doesnt.match.models", "" + productNo));

                        }
                    }
                }

            } catch (Exception e) {
                throw new Exception("Can not retrieve subcategories on productNo: " + productNo + " on groupId: "
                        + groupId + "\nCause: " + e.getMessage());
            }
        }
        return subCategoryList;
    }

    public List loadModifierTable() throws DAOException, ServiceException {
        return menuDAO.loadModifierTable();
    }

    public List<Map<String, Object>> loadMenuItemTable() throws DAOException, ServiceException {
        return menuDAO.loadMenuItemTable();
    }

    public List<Map<String, Object>> loadProdLinkTable() throws DAOException, ServiceException {
        return menuDAO.loadProdLinkTable();
    }

    public List<Map<String, Object>> loadWebCategoriesTable() throws DAOException, ServiceException {
        return menuDAO.loadWebCategoriesTable();
    }

    public List<Map<String, Object>> loadModLinkTable() throws DAOException, ServiceException {
        return menuDAO.loadModLinkTable();
    }

    public List<Map<String, Object>> loadTermTable() throws DAOException, ServiceException {
        return menuDAO.loadTermTable();
    }

    public List<Object[]> loadToppingCategoryTable() throws DAOException, ServiceException {
        return menuDAO.loadToppingCategoryTitlesCOL();
    }

    public List<Map<String, Object>> loadModifierPresetModLinkJoinTBL() throws DAOException {
        return menuDAO.loadModifierPresetModLinkJoinTBL();
    }

    public List<Map<String, Object>> loadTermGrptermMenuitemJoinTBL() throws DAOException {
        return menuDAO.loadTermGrptermMenuitemJoinTBL();
    }

    public List<Map<String, Object>> loadWebcategWebmenuMenuitemJoinTBL() throws DAOException {
        return menuDAO.loadWebcategWebmenuMenuitemJoinTBL();
    }

    public List<Map<String, Object>> loadModifierContentsJoinTBL() throws DAOException {
        return menuDAO.loadModifierContentsJoinTBL();
    }

    public List<Map<String, Object>> loadModifierPresetJoinTBL() throws DAOException {
        return menuDAO.loadModifierPresetJoinTBL();
    }
}
