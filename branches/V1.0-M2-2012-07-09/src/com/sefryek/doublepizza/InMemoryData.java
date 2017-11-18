package com.sefryek.doublepizza;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.model.Menu;
import com.sefryek.doublepizza.service.*;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.LogMessages;
import com.sefryek.doublepizza.core.comparator.StoreDistanceComperator;
import com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper;
import com.sefryek.doublepizza.core.helpers.ArchiveHandler;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.common.util.serialize.Serializer;
import com.sefryek.common.util.serialize.IOUtils;
import com.sefryek.common.util.serialize.StringUtil;
import com.sefryek.common.util.RuntimeInfo;
import com.sefryek.common.MessageUtil;
import com.sefryek.common.Point;
import com.sefryek.common.config.ApplicationConfig;

import org.springframework.web.context.WebApplicationContext;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.List;
import java.math.BigDecimal;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 30, 2012
 * Time: 12:34:58 PM
 */
public class InMemoryData {

    public static int counter = 0;
    public static String log = "";


    private IMenuService menuService;
    private static IStore storeService;
    private ITaxService taxService;
    private static IPostalCodeService postalCodeService;
    private static ISuggestionService suggestionservice;
    private static final OrderNumberHandler ORDER_NUMBER_HANDLER = OrderNumberHandler.getInstance();
    private IOrderService orderService;
    private static ISliderService sliderService;
    private static HashMap<String, String> frenchDataHashMap;
    private static HashMap<String, String> englishDataHashMap;

    private static Logger logger = Logger.getLogger(InMemoryData.class);
    private static List<Menu> menuList;
    private static List<Store> storeList;
    private static List<Tax> taxList;
    private static List<Suggestion> suggestionList;
    private static List<Map<String, Object>> webCategoriesTBL;//refer to main category
    private static List<Map<String, Object>> termTBL;
    private static List<Map<String, Object>> modlinkTBL;
    private static List<Map<String, Object>> prodlinkTBL;
    private static List<Map<String, Object>> menuitemTBL;
    private static List<Map<String, Object>> modifierTBL;
    private static List<Map<String, Object>> webCateg_webMenu_menuItem_JoinTBL;//refer to subCategories(Category or CombinedMenuItem)
    private static List<Map<String, Object>> groupTerm_menuItem_JoinTBL;
    private static List<Map<String, Object>> modifier_preset_modLink_JoinTBL;
    private static List<Map<String, Object>> prodLink_modLink_modifier_menuItem_JoinTBL;
    private static List<Map<String, Object>> modifier_contents_JoinTBL;
    private static List<Map<String, Object>> modifier_preset_JoinTBL;
    private static List<Map<String, Object>> storeMastTBL;
    private static List<Map<String, Object>> storeHoursTBL;

    //MessageResources tables
    private static List<Object[]> webCategoryTitlesTBL;
    private static List<Object[]> termTitlesTBL;
    private static List<Object[]> menuItemTitlesTBL;
    private static List<Object[]> modifierTitlesTBL;
    private static List<Object[]> topping_categoryTBL;
    private static Table alternativeTable;
    private static WebApplicationContext context;

    private static String latestUserUrl;

    private static List<Slider> sliderList;

    public static List<Slider> getSliderList() {
        return InMemoryData.sliderList;
    }

    public static String getLatestUserUrl() {
        return latestUserUrl;
    }

    public static void setLatestUserUrl(String latestUserUrl) {
        InMemoryData.latestUserUrl = latestUserUrl;
    }

    public static void setStoreHoursTBL(List<Map<String, Object>> storeHoursTBL) {
        InMemoryData.storeHoursTBL = storeHoursTBL;
    }

    public static void setStoreMastTBL(List<Map<String, Object>> storeMastTBL) {
        InMemoryData.storeMastTBL = storeMastTBL;
    }

    public static List<Object[]> getWebCategoryTitlesTBL() {
        return webCategoryTitlesTBL;
    }

    public static void setWebCategoryTitlesTBL(List<Object[]> webCategoryTitlesTBL) {
        InMemoryData.webCategoryTitlesTBL = webCategoryTitlesTBL;
    }

    public IStore getStoreService() {
        return storeService;
    }

    public void setStoreService(IStore _storeService) {
        storeService = _storeService;
    }


    public static List<Object[]> getTermTitlesTBL() {
        return termTitlesTBL;
    }

    public static void setTermTitlesTBL(List<Object[]> termTitlesTBL) {
        InMemoryData.termTitlesTBL = termTitlesTBL;
    }

    public static List<Object[]> getMenuItemTitlesTBL() {
        return menuItemTitlesTBL;
    }

    public static void setMenuItemTitlesTBL(List<Object[]> menuItemTitlesTBL) {
        InMemoryData.menuItemTitlesTBL = menuItemTitlesTBL;
    }

    public static List<Object[]> getModifierTitlesTBL() {
        return modifierTitlesTBL;
    }

    public static void setModifierTitlesTBL(List<Object[]> modifierTitlesTBL) {
        InMemoryData.modifierTitlesTBL = modifierTitlesTBL;
    }

    public static List<Object[]> getTopping_categoryTBL() {
        return topping_categoryTBL;
    }

    public static void setTopping_categoryTBL(List<Object[]> topping_categoryTBL) {
        InMemoryData.topping_categoryTBL = topping_categoryTBL;
    }

    public static List<Map<String, Object>> getModifier_preset_JoinTBL() {
        return modifier_preset_JoinTBL;
    }

    public static void setModifier_preset_JoinTBL(List<Map<String, Object>> modifier_preset_JoinTBL) {
        InMemoryData.modifier_preset_JoinTBL = modifier_preset_JoinTBL;
    }

    public static List<Map<String, Object>> getModifier_contents_JoinTBL() {
        return modifier_contents_JoinTBL;
    }

    public static void setModifier_contents_JoinTBL(List<Map<String, Object>> modifier_contents_JoinTBL) {
        InMemoryData.modifier_contents_JoinTBL = modifier_contents_JoinTBL;
    }

    public static List<Map<String, Object>> getModlinkTBL() {
        return modlinkTBL;
    }

    public static void setModlinkTBL(List<Map<String, Object>> modlinkTBL) {
        InMemoryData.modlinkTBL = modlinkTBL;
    }

    public static List<Map<String, Object>> getProdlinkTBL() {
        return prodlinkTBL;
    }

    public static void setProdlinkTBL(List<Map<String, Object>> prodlinkTBL) {
        InMemoryData.prodlinkTBL = prodlinkTBL;
    }

    public static List<Map<String, Object>> getMenuitemTBL() {
        return menuitemTBL;
    }

    public static void setMenuitemTBL(List<Map<String, Object>> menuitemTBL) {
        InMemoryData.menuitemTBL = menuitemTBL;
    }

    public static List<Map<String, Object>> getGroupTerm_menuItem_JoinTBL() {
        return groupTerm_menuItem_JoinTBL;
    }

    public static void setGroupTerm_menuItem_JoinTBL(List<Map<String, Object>> groupTerm_menuItem_JoinTBL) {
        InMemoryData.groupTerm_menuItem_JoinTBL = groupTerm_menuItem_JoinTBL;
    }

    public static List<Map<String, Object>> getWebCateg_webMenu_menuItem_JoinTBL() {
        return webCateg_webMenu_menuItem_JoinTBL;
    }

    public static void setWebCateg_webMenu_menuItem_JoinTBL(List<Map<String, Object>> webCateg_webMenu_menuItem_JoinTBL) {
        InMemoryData.webCateg_webMenu_menuItem_JoinTBL = webCateg_webMenu_menuItem_JoinTBL;
    }

    public static List<Map<String, Object>> getProdLink_modLink_modifier_menuItem_JoinTBL() {
        return prodLink_modLink_modifier_menuItem_JoinTBL;
    }

    public static void setProdLink_modLink_modifier_menuItem_JoinTBL(List<Map<String, Object>> prodLink_modLink_modifier_menuItem_JoinTBL) {
        InMemoryData.prodLink_modLink_modifier_menuItem_JoinTBL = prodLink_modLink_modifier_menuItem_JoinTBL;
    }

    public static Table getAlternativeTable() {
        return alternativeTable;
    }

    public static void setAlternativeTable(Table alternativeTable) {
        InMemoryData.alternativeTable = alternativeTable;
    }

    public IMenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        InMemoryData.logger = logger;
    }

    public static List<Map<String, Object>> getModifierTBL() {
        return modifierTBL;
    }

    public static void setModifierTBL(List<Map<String, Object>> modifierTBL) {
        InMemoryData.modifierTBL = modifierTBL;
    }

    public static List<Map<String, Object>> getWebCategoriesTBL() {
        return webCategoriesTBL;
    }

    public static void setWebCategoriesTBL(List<Map<String, Object>> webCategoriesTBL) {
        InMemoryData.webCategoriesTBL = webCategoriesTBL;
    }

    public static List<Map<String, Object>> getTermTBL() {
        return termTBL;
    }

    public static void setTermTBL(List<Map<String, Object>> termTBL) {
        InMemoryData.termTBL = termTBL;
    }

    public static List<Map<String, Object>> getModifier_preset_modLink_JoinTBL() {
        return modifier_preset_modLink_JoinTBL;
    }

    public static void setModifier_preset_modLink_JoinTBL(List<Map<String, Object>> modifier_preset_modLink_JoinTBL) {
        InMemoryData.modifier_preset_modLink_JoinTBL = modifier_preset_modLink_JoinTBL;
    }

    public static List<Menu> getMenuList() {
        return menuList;
    }

    public static void setMenuList(List<Menu> menuList) {
        InMemoryData.menuList = menuList;
    }

    public static List<Suggestion> getSuggestionList(){
        return suggestionList;
    }

    private static void copySliderImages() throws IOException {
        String srcPath = ApplicationConfig.sliderSrcImagesPath;
        String destPath = ApplicationConfig.sliderDestImagesPath;
        logger.debug("Debug: start copy slidres images srcPath = \t " + srcPath +
                "\n destPath = " + destPath);

        for (int i = 0; i <= 2; i++) {
            String fileName = "slide_0" + i + ".png";

            IOUtils.copy(srcPath + fileName, destPath + fileName, true);
        }
    }

    private static void loadSliderResources() {
        try {

            InMemoryData.copySliderImages();
            sliderList = sliderService.findTopSlides();
        } catch (Exception e) {
            logger.debug("error: loadSliderResources.\n cause: " + e.getMessage());
        }
    }

    public void writeTBLFile() {
        String dirPath = ArchiveHandler.createDirectory();
        if (dirPath == null) {
            logger.error("error on create archive directory because dirPath is null");
            return;
        }

        Serializer.writeObject(Constant.TABLE_LIST.Storemast.toString(), storeMastTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.StoreHours.toString(), storeHoursTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.WebCategories.toString(), webCategoriesTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.Term.toString(), termTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.MenuItem.toString(), menuitemTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.Modifier.toString(), modifierTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.ProdLink.toString(), prodlinkTBL, dirPath);
        Serializer.writeObject(Constant.TABLE_LIST.ModLink.toString(), modlinkTBL, dirPath);
        Serializer.writeObject("webCateg_webMenu_menuItem_JoinTBL", webCateg_webMenu_menuItem_JoinTBL, dirPath);
        Serializer.writeObject("groupterm_menuitem_JoinTBL", groupTerm_menuItem_JoinTBL, dirPath);
        Serializer.writeObject("modifier_preset_JoinTBL", modifier_preset_JoinTBL, dirPath);
        Serializer.writeObject("modifier_contents", modifier_contents_JoinTBL, dirPath);
        Serializer.writeObject("topping_categoryTBL", topping_categoryTBL, dirPath);
    }

    public void readTBLFile() {

        storeMastTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.Storemast.toString());
        storeHoursTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.StoreHours.toString());
        webCategoriesTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.WebCategories.toString());
        termTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.Term.toString());
        webCateg_webMenu_menuItem_JoinTBL = (List<Map<String, Object>>) Serializer.readObject("webCateg_webMenu_menuItem_JoinTBL");
        groupTerm_menuItem_JoinTBL = (List<Map<String, Object>>) Serializer.readObject("groupterm_menuitem_JoinTBL");
        modifier_preset_JoinTBL = (List<Map<String, Object>>) Serializer.readObject("modifier_preset_JoinTBL");
        prodlinkTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.ProdLink.toString());
        modlinkTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.ModLink.toString());
        modifierTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.Modifier.toString());
        menuitemTBL = (List<Map<String, Object>>) Serializer.readObject(Constant.TABLE_LIST.MenuItem.toString());
        modifier_contents_JoinTBL = (List<Map<String, Object>>) Serializer.readObject("modifier_contents");
        topping_categoryTBL = (List<Object[]>) Serializer.readObject("topping_categoryTBL");


    }

    public static List<Map<String, Object>> findById(String table, String column, Integer value) {

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            if (table.equals(Constant.JOIN_TABLE_LIST.groupTerm_menuItem_JoinTBL.toString())) {
                for (Map<String, Object> row : groupTerm_menuItem_JoinTBL) {
                    if (row.get(column) != null && !row.get(column).toString().equals("")) {
                        if (Integer.valueOf(row.get(column).toString()).equals(value)) {
                            resultList.add(row);
                        }
                    }
                }
            }

            if (table.equals(Constant.JOIN_TABLE_LIST.webMenuItem_menuItem_JoinTBL.toString())) {
                for (Map<String, Object> row : webCateg_webMenu_menuItem_JoinTBL) {
                    if (row.get(column) != null && !row.get(column).toString().equals("")) {
                        if (Integer.valueOf(row.get(column).toString()).equals(value)) {
                            resultList.add(row);
                        }
                    }
                }
            }

            if (table.equals(Constant.JOIN_TABLE_LIST.modifier_preset_modLink_JoinTBL.toString())) {
                for (Map<String, Object> row : modifier_preset_modLink_JoinTBL) {
                    if (row.get(column) != null && !row.get(column).toString().equals("")) {
                        if (Integer.valueOf(row.get(column).toString()).equals(value)) {
                            resultList.add(row);
                        }
                    }
                }
            }

            if (table.equals(Constant.JOIN_TABLE_LIST.prodLink_modLink_modifier_menuItem_JoinTBL.toString())) {


                for (Map<String, Object> row : prodLink_modLink_modifier_menuItem_JoinTBL) {
                    if (row.get(column) != null && !row.get(column).toString().equals("")) {
                        if (Integer.valueOf(row.get(column).toString()).equals(value)) {
                            resultList.add(row);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.debug("Debug: search process has been failed.\n cause: " + e.getMessage());
        }
        return resultList;
    }

    //initializing service beans (this method is called only once, just in the ApplicationContextListener)
    public void initializeService(WebApplicationContext context) {
        logger = Logger.getLogger(InMemoryData.class);
        menuService = (IMenuService) context.getBean(IMenuService.BEAN_NAME);
        storeService = (IStore) context.getBean(IStore.BEAN_NAME);
        taxService = (ITaxService) context.getBean(ITaxService.BEAN_NAME);
        orderService = (IOrderService) context.getBean(IOrderService.BEAN_NAME);
        sliderService = (ISliderService) context.getBean(ISliderService.BEAN_NAME);
        postalCodeService = (IPostalCodeService) context.getBean(IPostalCodeService.BEAN_NAME);
        suggestionservice = (ISuggestionService) context.getBean(ISuggestionService.BEAN_NAME);
        InMemoryData.context = context;

    }

    //loading DB data to the static variable (this method is called only once, just in the ApplicationContextListener)
    public void loadData() throws DataLoadException {
        logger.info(LogMessages.START_OF_METHOD + "loadData");

        Menu orderMenu = new Menu(Constant.ORDER_MENU_NAME);
        Menu specialMenu = new Menu(Constant.SPECIAL_MENU_NAME);

        List<Menu> menuListTemp;

        menuListTemp = new ArrayList<Menu>();

        try {

            if (RuntimeInfo.isDeveleopMode()) {
                readTBLFile();

            } else {
                InMemoryData.setWebCategoriesTBL(menuService.loadWebCategoriesTable());
                InMemoryData.setTermTBL(menuService.loadTermTable());
                InMemoryData.setMenuitemTBL(menuService.loadMenuItemTable());
                InMemoryData.setModifierTBL(menuService.loadModifierTable());
                InMemoryData.setProdlinkTBL(menuService.loadProdLinkTable());
                InMemoryData.setModlinkTBL(menuService.loadModLinkTable());
                InMemoryData.setGroupTerm_menuItem_JoinTBL(menuService.loadTermGrptermMenuitemJoinTBL());
                InMemoryData.setWebCateg_webMenu_menuItem_JoinTBL(menuService.loadWebcategWebmenuMenuitemJoinTBL());
                InMemoryData.setModifier_preset_JoinTBL(menuService.loadModifierPresetJoinTBL());
                InMemoryData.setModifier_contents_JoinTBL(menuService.loadModifierContentsJoinTBL());
                InMemoryData.setStoreMastTBL(storeService.loadStoreMastTBL());
                InMemoryData.setStoreHoursTBL(storeService.loadStoreHoursTBL());
                InMemoryData.setTopping_categoryTBL(menuService.loadToppingCategoryTable());

                writeTBLFile();
                
            }

            List<Store> storeListTemp = createStoresList();

            orderMenu.setCategoryList(menuService.getMenuCategoryList());
            specialMenu.setCategoryList(menuService.getSpecialMenuCategoryList());

            menuListTemp.add(orderMenu);
            menuListTemp.add(specialMenu);
            setOrderNumber(orderService.getLastDocNumber());
            loadSliderResources();

            menuList = menuListTemp;
            storeList = storeListTemp;

            taxList = getTaxes();
            suggestionList = createSuggestionList();


        } catch (Exception e) {
            throw new DataLoadException(e, e.getClass(), "Data has not been load successfully!");

        }
    }

//    //reading "menu" from file
//    public void readMenuFromFile() {
//        menuList = (List<Menu>) Serializer.readObject(Constant.SERIALIZED_OBJ_NAME);
//    }

    //writting "menu" to a file

    public void writeMenuToFile() {
//        Serializer.writeObject(Constant.SERIALIZED_OBJ_NAME, menuList);
        Serializer.writeObject("groupterm_menuitem_JoinTBL", groupTerm_menuItem_JoinTBL);
        Serializer.writeObject("webCateg_webMenu_menuItem_JoinTBL", webCateg_webMenu_menuItem_JoinTBL);
    }

    public static Category findCategoryById(String menu, Integer catId) {
        logger.info(LogMessages.START_OF_METHOD + "findCategoryById");
        List<Category> categoryList;

        for (Menu currentMenu : InMemoryData.menuList) {
            categoryList = currentMenu.getCategoryList();

            for (Category category : categoryList) {
                if (category.getId().equals(catId.toString())) {
                    return category;
                }

                List<SubCategory> subCategories = category.getSubCategoryList();
                for (SubCategory subCategory : subCategories) {
                    if (subCategory.getType() == Category.class)
                        if (((Category) subCategory.getObject()).getId().equals(String.valueOf(catId)))
                            return (Category) subCategory.getObject();
                }
            }
        }
        return null;
    }

    public static List<ToppingCategory> getToppingsCategoryListWithoutExclusives(String combinedMenuItemID, String singleItemID) {

        List<Map<String, Object>> prodLinkRowList;
        List<Map<String, Object>> modLinkRowList;
        List<Map<String, Object>> modifierRowList;
        List<Map<String, Object>> toppingList = new ArrayList<Map<String, Object>>();
        Set<String> toppingCategoryList = new HashSet<String>();
        List<ToppingCategory> result = new ArrayList<ToppingCategory>();
        ToppingCategory toppingCategory = null;
        List<ToppingSubCategory> toppingSubCategoryList = new ArrayList<ToppingSubCategory>();
        List<Integer> defaultToppings = new ArrayList<Integer>();
        ToppingSubCategory toppingSubCategory;

        try {
            //get prodlink rows that belongs to a specific productNo
            prodLinkRowList = getJoinedGroupId(combinedMenuItemID);
            //get prodlink rows that belongs to a specific productNo
            modLinkRowList = getJoindModifierId(prodLinkRowList);

            //get prodlink rows that belongs to a specific productNo
            modifierRowList = getModifierRowList(modLinkRowList);

            for (Map<String, Object> modifierRow : modifierRowList) {
                toppingList.add(modifierRow);
            }

            for (Map<String, Object> row : toppingList) {
                if (row.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE) != null) {
                    toppingCategoryList.add(row.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE).toString());
                }
            }

            int categoryIndex = 0;
            //get Default selected toppings 'preSelected Toppings'
            List<Integer> preSelectedToppings = InMemoryData.getDefaultToppingList(combinedMenuItemID, singleItemID);

            for (String itemType : toppingCategoryList) {

                toppingSubCategoryList = new ArrayList<ToppingSubCategory>();
                defaultToppings = new ArrayList<Integer>();

                toppingCategory = new ToppingCategory(categoryIndex++,
                        getToppingOrCaptionTitleKey(itemType, singleItemID), false);

                for (Map<String, Object> toppingRow : toppingList) {
                    if (toppingRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE).equals(itemType)) {

                        Integer toppingID = Integer.parseInt(toppingRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID).toString());
                        Topping topping = getToppingInstance(toppingRow, singleItemID);

                        //create a toppingSubCateg
                        toppingSubCategory = new ToppingSubCategory(Topping.class, topping);

                        //check that is a default or not.
                        if (isDefault(preSelectedToppings, toppingID)) {
                            defaultToppings.add(toppingID);
                        }

                        toppingSubCategoryList.add(toppingSubCategory);
                    }
                }

                toppingCategory.setToppingSubCategoryList(toppingSubCategoryList);

                toppingCategory.setDefaultToppingList(defaultToppings);

                result.add(toppingCategory);
            }
        } catch (Exception e) {
            logger.error("can not execute getToppingsCategoryList service. cause: " + e.getMessage());
        }
        return result;
    }


    public static List<ToppingCategory> getDistinctToppingsCategoryList(
            List<Map<String, Object>> prodLinkRowList, String combinedMenuItemID, String singleItemID) throws DAOException {

        List<Map<String, Object>> modLinkRowList;
        List<Map<String, Object>> modifierRowList;
        List<String> itemTypes = new ArrayList<String>();
        List<ToppingCategory> result = new ArrayList<ToppingCategory>();
        ToppingCategory toppingCategory = null;
        List<ToppingSubCategory> toppingSubCategoryList = new ArrayList<ToppingSubCategory>();
        List<Integer> defaultToppings = new ArrayList<Integer>();
        ToppingSubCategory toppingSubCategory;

        try {

            //get prodlink rows that belongs to a specific productNo
            modLinkRowList = getJoindModifierId(prodLinkRowList);

            //get prodlink rows that belongs to a specific productNo
            modifierRowList = getModifierRowList(modLinkRowList);


            for (Map<String, Object> row : modifierRowList) {
                if (row.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE) != null) {
                    boolean founded = false;
                    for (String itemType : itemTypes) {
                        if (itemType.equals(row.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE)))
                            founded = true;

                    }
                    if (!founded)
                        itemTypes.add(row.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE).toString());
                }
            }

            itemTypes = sortToppingCategories(itemTypes);


            int categoryIndex = 0;
            //get Default selected toppings 'preSelected Toppings'
            List<Integer> preSelectedToppings = InMemoryData.getDefaultToppingList(combinedMenuItemID, singleItemID);

            for (String itemType : itemTypes) {
                toppingSubCategoryList = new ArrayList<ToppingSubCategory>();
                defaultToppings = new ArrayList<Integer>();

                boolean isExclusive = (itemType.equals("crust") || itemType.equals("cookingmode"));
                toppingCategory = new ToppingCategory(categoryIndex++,
                        getToppingOrCaptionTitleKey(itemType, singleItemID), isExclusive);


                for (Map<String, Object> toppingRow : modifierRowList) {
                    if (toppingRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE).equals(itemType)) {

                        Integer toppingID = Integer.parseInt(toppingRow.get(
                                Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID).toString());
                        Topping topping = getToppingInstance(toppingRow, singleItemID);

                        //create a toppingSubCateg
                        toppingSubCategory = new ToppingSubCategory(Topping.class, topping);

                        //check that is a default or not.
                        if (!(toppingCategory.getExclusive()) && isDefault(preSelectedToppings, toppingID)) {
                            defaultToppings.add(toppingID);
                        }

                        toppingSubCategoryList.add(toppingSubCategory);
                    }
                }


                if (toppingCategory.getExclusive())
                    defaultToppings.add(((Topping) (toppingSubCategoryList.get(0).getObject())).getId());

                toppingCategory.setToppingSubCategoryList(toppingSubCategoryList);
                toppingCategory.setDefaultToppingList(defaultToppings);
                result.add(toppingCategory);

            }

        } catch (Exception e) {

            throw new DAOException(e, e.getClass(), "can not execute getToppingsCategoryList service for single: " + singleItemID
                    + " in combined: " + combinedMenuItemID + " . cause: " + e.getMessage());

        }
        return result;
    }

    private static List<String> sortToppingCategories(List<String> toppingCategories) {

        List<String> sortedToppingCategories = new ArrayList<String>();

        int minSequenceIndex = 0;
        int minSequence = Integer.MIN_VALUE;

        for (int i = 0; i < topping_categoryTBL.size(); i++) {
            Object[] row = (topping_categoryTBL.get(i));
            minSequenceIndex = i;

            minSequence = (Integer) row[row.length - 1];

            for (int j = i + 1; j < topping_categoryTBL.size(); j++) {
                Object[] currentRow = topping_categoryTBL.get(j);
                if (minSequence > (Integer) currentRow[currentRow.length - 1]) {
                    minSequence = (Integer) currentRow[currentRow.length - 1];
                    minSequenceIndex = j;
                }
            }

            Object temp = topping_categoryTBL.get(minSequenceIndex);
            topping_categoryTBL.set(minSequenceIndex, topping_categoryTBL.get(i));
            topping_categoryTBL.set(i, (Object[]) temp);
        }


        for (Object[] currentRow : topping_categoryTBL) {
            for (String toppingCategory : toppingCategories) {
                if (toppingCategory.equals(currentRow[3])) {
                    sortedToppingCategories.add(toppingCategory);
                }
            }
        }

        return sortedToppingCategories;
    }

    // if topping id is in default topping list then return true
    private static Boolean isDefault(List<Integer> defaultList, int id) {
        Boolean result = false;
        for (Integer singleItemDefaultTopping : defaultList) {
            if (id == singleItemDefaultTopping) {
                result = true;
            }
        }
        return result;
    }

    // this method retrive list of presetItem per each CombiendMenuItem.
    public static void fillCombinedItem(CombinedMenuItem combinedMenuItem
            , boolean isTwoForOne) {

        List<MenuSingleItem> menuSingleItems = new ArrayList<MenuSingleItem>();
        List<List<MenuSingleItem>> allAlternatives = new ArrayList<List<MenuSingleItem>>();
        List<String> itemCaptions = new ArrayList<String>();
        List<String> specialSingleItemIds = getSpecialSingleItemIds(combinedMenuItem.getProductNo());

        try {
            List<Map<String, Object>> allProdLinkRowList;
            List<Map<String, Object>> modLinkRowList;
            List<Map<String, Object>> modifierRowList;
            List<Map<String, Object>> mainProdLinkList = new ArrayList<Map<String, Object>>();
            List<MenuSingleItem> itemAlternatives;
            List<ToppingCategory> toppingCategories = new ArrayList<ToppingCategory>();
            allProdLinkRowList = getJoinedGroupId(combinedMenuItem.getProductNo());
            int numberOfPizzas = 0;

            for (int k = 0; k < allProdLinkRowList.size(); k++) {

                List<Map<String, Object>> toppingRows = new ArrayList<Map<String, Object>>();
                Map<String, Object> prodLinkRow = allProdLinkRowList.get(k);

                /**
                 * if forced value is not zero, it is main item and all other items with zero forced value comes after it
                 * are the topping on the main item. it stops when getsto the next
                 */
                Integer forced = (Integer) prodLinkRow.get("ProdLink." + Constant.PRODLINK_FORCED);
                if (forced.equals(Constant.VALUE_IS_FORCED)) {
                    // do nothing

                } else {

                    toppingRows = new ArrayList<Map<String, Object>>();

                    int toppingCategoryIndex = k + 1;
                    while (toppingCategoryIndex < allProdLinkRowList.size()) {
                        if (allProdLinkRowList.get(toppingCategoryIndex).get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_FORCED).equals(Constant.VALUE_IS_FORCED)) {
                            toppingRows.add(allProdLinkRowList.get(toppingCategoryIndex));
                            toppingCategoryIndex++;

                        } else
                            break;
                    }

                    itemAlternatives = new ArrayList<MenuSingleItem>();
                    mainProdLinkList = new ArrayList<Map<String, Object>>();
                    mainProdLinkList.add(prodLinkRow);

                    modLinkRowList = getJoindModifierId(mainProdLinkList);
                    modifierRowList = getModifierRowList(modLinkRowList);
                    int iterationOfMenuSingleItem = 0;
                    int iterationOfDefaultSingleItem = 0;
                    boolean foundDefaultSingleItem = false;

                    for (Map<String, Object> aModifierRow : modifierRowList) {
                        if (Boolean.valueOf(aModifierRow.get(
                                Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSTATUS).toString())) {

                            MenuSingleItem menuSingleItem = InMemoryData.getMenuSingleItemInstance(aModifierRow,
                                    combinedMenuItem.getProductNo(), "" + Constant.TABLE_LIST.Modifier);

                            //set toppingCategories for each product
                            toppingCategories = getDistinctToppingsCategoryList(toppingRows, combinedMenuItem.getProductNo(), menuSingleItem.getId());
                            menuSingleItem.setToppingCategoryList(toppingCategories);

                            if (isTwoForOne) {
                                if ((menuSingleItem.getName(Locale.ENGLISH)).contains("All Dressed")) {
                                    menuSingleItems.add(menuSingleItem);
                                    itemCaptions.add(getPizzaCaptionKey(Constant.ITEM_TYPE_PIZZA,
                                            combinedMenuItem.getProductNo(), 1));
                                    menuSingleItems.add(menuSingleItem);
                                    itemCaptions.add(getPizzaCaptionKey(Constant.ITEM_TYPE_PIZZA,
                                            combinedMenuItem.getProductNo(), 2));

                                } else {
                                    itemAlternatives.add(menuSingleItem);

                                }

                            } else {

                                iterationOfMenuSingleItem = 0;
                                for (String specialSingleItemId : specialSingleItemIds) {
                                    if (specialSingleItemId.equals(menuSingleItem.getId())) {
                                        foundDefaultSingleItem = true;
                                        iterationOfMenuSingleItem++;
                                    }
                                }

                                if (foundDefaultSingleItem) {
                                    foundDefaultSingleItem = false;
                                    iterationOfDefaultSingleItem = iterationOfMenuSingleItem;
                                }

                                if (iterationOfMenuSingleItem > 0) {
                                    String itemType = (String) aModifierRow.get(
                                            Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_ITEM_TYPE);

                                    for (int i = 0; i < iterationOfMenuSingleItem; i++) {
                                        String caption;
                                        if (itemType.equals(Constant.ITEM_TYPE_PIZZA) && iterationOfMenuSingleItem > 1) {
                                            numberOfPizzas++;
                                            caption = getPizzaCaptionKey(itemType, combinedMenuItem.getProductNo(), numberOfPizzas);

                                        } else
                                            caption = getToppingOrCaptionTitleKey(itemType, combinedMenuItem.getProductNo());

                                        menuSingleItems.add(menuSingleItem);
                                        itemCaptions.add(caption);
                                    }
                                } else {
                                    itemAlternatives.add(menuSingleItem);

                                }
                            }
                        }
                    }

                    if (isTwoForOne) {
                        allAlternatives.add(itemAlternatives);
                        allAlternatives.add(itemAlternatives);

                    } else {

                        if (iterationOfDefaultSingleItem > 0) {
                            for (int i = 0; i < iterationOfDefaultSingleItem; i++)
                                allAlternatives.add(itemAlternatives);

                        } else
                            allAlternatives.add(itemAlternatives);

                    }
                }
            }

            combinedMenuItem.setAlternatives(allAlternatives);
            combinedMenuItem.setMenuSingleItemList(menuSingleItems);
            combinedMenuItem.setCaptionKies(itemCaptions);

        } catch (Exception e) {
            logger.error("can not execute getDefaultSingleItemList service. cause: " + e.getMessage());

        }
    }

    // this method retrive list of preSelectedTopping per each toppingCategory.
    public static List<Integer> getDefaultToppingList(String combinedMenuItemID, String singleItemID) {

        List<Integer> result = new ArrayList<Integer>();

        try {

            for (Map<String, Object> presetRow : modifier_contents_JoinTBL) {
                if (Integer.parseInt((String) presetRow.get(Constant.CONTENTS_PRODUCTNO)) == Integer.parseInt(combinedMenuItemID)
                        && Integer.parseInt((String) presetRow.get(Constant.CONTENTS_MODIFIERID)) == Integer.parseInt(singleItemID)) {
                    result.add(Integer.valueOf(presetRow.get(Constant.CONTENTS_MODTOFIND).toString()));
                }
            }

        } catch (Exception e) {
            logger.error("can not execute getDefaultToppingList service. cause: " + e.getMessage() + " combiendID: " + combinedMenuItemID + " singleItemID: " + singleItemID);
        }
        return result;
    }

    private static List<Map<String, Object>> getJoinedWithGroupIdToFindToppings(String productId) {
        List<Map<String, Object>> prodLinkRowList = new ArrayList<Map<String, Object>>();

        int index = -1;

        try {

            for (int i = 0; i < prodlinkTBL.size(); i++) {
                Map<String, Object> row = prodlinkTBL.get(i);

                if (row.get(Constant.PRODLINK_PRODUCTNO) != null
                        && !row.get(Constant.PRODLINK_PRODUCTNO).toString().equals("")) {

                    if (Integer.parseInt(row.get(Constant.PRODLINK_PRODUCTNO).toString())
                            == Integer.parseInt(productId)) {
                        index = i;

                    }
                }
            }

            for (Map<String, Object> row : prodlinkTBL) {
                if (row.get(Constant.MENUITEM_PRODUCTNO) != null) {
                    if (!row.get(Constant.MENUITEM_PRODUCTNO).toString().equals("")) {
                        if (Integer.parseInt(row.get(Constant.MENUITEM_PRODUCTNO).toString()) == Integer.parseInt(productId)) {

                            //change keySets of rowMap to set tablaName as prefix
                            Map<String, Object> prodLinkMap = new HashMap<String, Object>();
                            for (String currentKey : row.keySet()) {
                                prodLinkMap.put(Constant.TABLE_LIST.ProdLink + "." + currentKey, row.get(currentKey));
                            }

                            prodLinkRowList.add(prodLinkMap);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("can not fetch service getJoinedGroupId toppingItemList. cause: " + e.getMessage());
        }

        return prodLinkRowList;

    }

    // this method return list of modGroupNo per each ProductNo.
    private static List<Map<String, Object>> getJoinedGroupId(String productId) {

        List<Map<String, Object>> prodLinkRowList = new ArrayList<Map<String, Object>>();

        try {

            for (Map<String, Object> row : prodlinkTBL) {
                if (row.get(Constant.MENUITEM_PRODUCTNO) != null) {
                    if (!row.get(Constant.MENUITEM_PRODUCTNO).toString().equals("")) {
                        if (Integer.parseInt(row.get(Constant.MENUITEM_PRODUCTNO).toString()) == Integer.parseInt(productId)) {

                            //change keySets of rowMap to set tablaName as prefix
                            Map<String, Object> prodLinkMap = new HashMap();
                            for (String currentKey : row.keySet()) {
                                prodLinkMap.put(Constant.TABLE_LIST.ProdLink + "." + currentKey, row.get(currentKey));
                            }

                            prodLinkRowList.add(prodLinkMap);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("can not fetch service getJoinedGroupId toppingItemList. cause: " + e.getMessage());
        }

        return prodLinkRowList;
    }

    // this method return list of modifierId per List of modGroupNo.
    private static List<Map<String, Object>> getJoindModifierId(List<Map<String, Object>> prodLinkRowList) {

        List<Map<String, Object>> modLinkRowList = new ArrayList<Map<String, Object>>();

        try {

            for (Map<String, Object> modlinkRow : modlinkTBL) {
                for (Map<String, Object> prodlinkrow : prodLinkRowList) {
                    if (modlinkRow.get(Constant.PRODLINK_MODGROUPNO) != null) {
                        if (!modlinkRow.get(Constant.PRODLINK_MODGROUPNO).toString().equals("")) {
                            if (Integer.parseInt(modlinkRow.get(Constant.PRODLINK_MODGROUPNO).toString()) ==
                                    Integer.parseInt(prodlinkrow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_MODGROUPNO).toString())) {

                                //change keySets of rowMap to set tablaName as prefix
                                Map<String, Object> modLinkMap = new HashMap();
                                for (String currentKey : modlinkRow.keySet()) {
                                    modLinkMap.put(Constant.TABLE_LIST.ModLink + "." + currentKey, modlinkRow.get(currentKey));
                                }

                                modLinkMap.putAll(prodlinkrow);
                                modLinkRowList.add(modLinkMap);

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("can not fetch service getJoinedModifierId toppingItemLis. cause: " + e.getMessage());
        }
        return modLinkRowList;
    }

    // this method return list of modifier table row per each list of modifierId.
    private static List<Map<String, Object>> getModifierRowList(List<Map<String, Object>> modifierIdList) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {

            for (Map<String, Object> modifierRow : modifierTBL) {
                for (Map<String, Object> modlinkrow : modifierIdList) {
                    if (modifierRow.get(Constant.MODIFIER_MODIFIERID) != null) {
                        if (!modifierRow.get(Constant.MODIFIER_MODIFIERID).toString().equals("")) {
                            if (Integer.parseInt(modifierRow.get(Constant.MODIFIER_MODIFIERID).toString()) ==
                                    Integer.parseInt(modlinkrow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_MODIFIERID).toString())) {

                                //change keySets of rowMap to set tablaName as prefix
                                Map<String, Object> modifierMap = new HashMap<String, Object>();
                                for (String currentKey : modifierRow.keySet()) {
                                    modifierMap.put(Constant.TABLE_LIST.Modifier + "." + currentKey, modifierRow.get(currentKey));
                                }

                                modifierMap.putAll(modlinkrow);
                                result.add(modifierMap);

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("can not fetch service getModifierRowList toppingItemList. cause: " + e.getMessage());
        }

        return result;
    }

    // this method return default singleItem that its sequence defined Constant in project.
    public static String getDefaultSingleItem(CombinedMenuItem combinedMenuItem) {

        List<Map<String, Object>> prodLinkRowList;
        List<Map<String, Object>> modifierRowList;
        List<Map<String, Object>> alterProdLinkList = new ArrayList<Map<String, Object>>();
        String result = null;

        try {

            prodLinkRowList = getJoinedGroupId(combinedMenuItem.getProductNo());

            for (Map<String, Object> prodLinkRow : prodLinkRowList) {
                if (prodLinkRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_SEQ).equals(Constant.SINGLEITEM_PRODLINK_SEQ)) {
                    alterProdLinkList.add(prodLinkRow);
                }
            }

            modifierRowList = getModifierRowList(getJoindModifierId(alterProdLinkList));

            for (Map<String, Object> modifierRow : modifierRowList) {
                if (Boolean.valueOf(modifierRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSTATUS).toString()) &&
                        (modifierRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_SEQ) ==
                                Integer.valueOf(Constant.SEQUENCE_DEFAULT_SINGLEITEM))) {

                    result = modifierRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID).toString();

                }
            }

        } catch (Exception e) {
            logger.error("can not execute getAlternativeList service. cause: " + e.getMessage());
        }

        return result;
    }

    public static MenuSingleItem getMenuSingleItemInstanceForSuggestion(Map tableRow, String parentID, BigDecimal price) {

        String modifierId;
        MenuSingleItem menuSingleItem = null;
        try {
            modifierId = tableRow.get(Constant.MODIFIER_MODIFIERID).toString();
        } catch (Exception e) {
            modifierId = null;
        }

        // if main Id is null or empty, then its record is not valid and dont insert it.
        if (modifierId != null && !modifierId.equals("")) {

            String englishName = (tableRow.get(Constant.MODIFIER_NAME_EN) != null)
                    ? tableRow.get(Constant.MODIFIER_NAME_EN).toString()
                    : null;

            String frenchName = (tableRow.get(Constant.MODIFIER_NAME_FR) != null)
                    ? tableRow.get(Constant.MODIFIER_NAME_FR).toString()
                    : null;

            String englishdescription = (tableRow.get(Constant.MODIFIER_DESCRIPTION_EN) != null)
                    ? tableRow.get(Constant.MODIFIER_DESCRIPTION_EN).toString()
                    : Constant.DESC_ALTERNATIVE;


            String frenchDescription = (tableRow.get(Constant.MODIFIER_DESCRIPTION_FR) != null)
                    ? tableRow.get(Constant.MODIFIER_DESCRIPTION_FR).toString()
                    : Constant.DESC_ALTERNATIVE;

            String nameKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Modifier + "",
                    modifierId, parentID, Constant.MODIFIER_NAME_EN,
                    englishName, frenchName);

            String descriptionKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Modifier + "",
                    modifierId, parentID, Constant.MODIFIER_DESCRIPTION_EN,
                    englishdescription, frenchDescription);

            menuSingleItem = new MenuSingleItem(modifierId, MenuSingleItem.IdType.ModifierID,
                    Constant.SUGGESTIONS_MENUSINGLE_PRODUCTNO,
                    nameKey, descriptionKey,
                    (String) tableRow.get(Constant.MODIFIER_IMAGE), price,
                    0,
                    new BigDecimal(0),
                    false,
                    false,
                    (String) tableRow.get(Constant.MODIFIER_SIZE),
                    Integer.parseInt(tableRow.get(Constant.MODIFIER_WEBSEQ).toString()));
            menuSingleItem.setProductNo(Constant.SUGGESTIONS_MENUSINGLE_PRODUCTNO);
        }
        return menuSingleItem;

    }

    // this method create a new MenuSingleItem Obj and set all fields followed by input Map parameter of table row.
    public static MenuSingleItem getMenuSingleItemInstance(Map tableRow, String parentID, String tableType) {

        MenuSingleItem menuSingleItem = null;

        if (tableType.equals(("" + Constant.TABLE_LIST.Modifier))) {
            String modifierId;

            String keyPrefix = "" + Constant.TABLE_LIST.Modifier + ".";

            try {
                modifierId = tableRow.get(keyPrefix + Constant.MODIFIER_MODIFIERID).toString();
            } catch (Exception e) {
                modifierId = null;
            }

            // if main Id is null or empty, then its record is not valid and dont insert it.
            if (modifierId != null && !modifierId.equals("")) {

                String englishName = (tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_EN) != null)
                        ? tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_EN).toString()
                        : null;

                String frenchName = (tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_FR) != null)
                        ? tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_FR).toString()
                        : null;

                String englishdescription = (tableRow.get(
                        Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_DESCRIPTION_EN) != null)
                        ? tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_DESCRIPTION_EN).toString()
                        : Constant.DESC_ALTERNATIVE;


                String frenchDescription = (tableRow.get(
                        Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_DESCRIPTION_FR) != null)
                        ? tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_DESCRIPTION_FR).toString()
                        : Constant.DESC_ALTERNATIVE;

                String nameKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Modifier + "",
                        modifierId, parentID, Constant.MODIFIER_NAME_EN,
                        englishName, frenchName);

                String descriptionKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.Modifier + "",
                        modifierId, parentID, Constant.MODIFIER_DESCRIPTION_EN,
                        englishdescription, frenchDescription);


                BigDecimal price = new BigDecimal(tableRow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_PRICE).toString());

                if (price.compareTo(new BigDecimal(0)) == 0) {
                    price = new BigDecimal(tableRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_PRICE).toString());

                }

                menuSingleItem = new MenuSingleItem(modifierId, MenuSingleItem.IdType.ModifierID,
                        (String) tableRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.MODLINK_MODGROUPNO),
                        nameKey, descriptionKey,
                        (String) tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_IMAGE), price,
                        Integer.parseInt(tableRow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_FREEMODS).toString()),
                        new BigDecimal(tableRow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_FREEMODPRICE).toString()),
                        (Boolean) tableRow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_TWOFORONE),
                        (Boolean) tableRow.get(Constant.TABLE_LIST.ModLink + "." + Constant.MODIFIER_PORTION),
                        (String) tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_SIZE),
                        Integer.parseInt(tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSEQ).toString()));

            }

            return menuSingleItem;

        } else if (tableType.equals("" + Constant.TABLE_LIST.MenuItem)) {

            String productNo = (String) tableRow.get(Constant.MENUITEM_PRODUCTNO);

            String englishName = (tableRow.get(Constant.MENUITEM_NAME_EN) != null)
                    ? tableRow.get(Constant.MENUITEM_NAME_EN).toString() : null;

            String frenchName = (tableRow.get(Constant.MENUITEM_NAME_FR) != null)
                    ? tableRow.get(Constant.MENUITEM_NAME_FR).toString() : null;

            String englishdescription = (tableRow.get(Constant.MENUITEM_DESCRIPTION_EN) != null)
                    ? tableRow.get(Constant.MENUITEM_DESCRIPTION_EN).toString() : Constant.DESC_ALTERNATIVE;

            String frenchDescription = (tableRow.get(Constant.MENUITEM_DESCRIPTION_FR) != null)
                    ? tableRow.get(Constant.MENUITEM_DESCRIPTION_FR).toString() : Constant.DESC_ALTERNATIVE;

            String nameKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.MenuItem + "",
                    productNo, parentID, Constant.MENUITEM_NAME_EN,
                    englishName, frenchName);

            String descriptionKey = getHashMapKeyAndSaveWords(Constant.TABLE_LIST.MenuItem + "",
                    productNo, parentID, Constant.MENUITEM_DESCRIPTION_EN,
                    englishdescription, frenchDescription);

            menuSingleItem = new MenuSingleItem(productNo,
                    MenuSingleItem.IdType.ProductNo, "", nameKey, descriptionKey,
                    (String) tableRow.get(Constant.MENUITEM_WEBIMAGE),
                    new BigDecimal(tableRow.get(Constant.MENUITEM_PRICE).toString()),
                    Integer.parseInt(tableRow.get(Constant.MENUITEM_FREEMODS).toString()),
                    new BigDecimal(tableRow.get(Constant.MENUITEM_FREEMODPRICE).toString()),
                    (Boolean) tableRow.get(Constant.MENUITEM_TWOFORONE),
                    (Boolean) tableRow.get(Constant.MENUITEM_PORTION),
                    (String) tableRow.get(Constant.MENUITEM_SIZE),
                    Integer.parseInt(tableRow.get(Constant.MENUITEM_SEQUENCE).toString()));

            return menuSingleItem;

        } else
            return null;

    }

    public static Topping getToppingInstance(Map tableRow, String parentID) {

        Integer toppingID = Integer.parseInt(tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID).toString());

        String englishName = tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_EN).toString();
        String frenchName = tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_NAME_FR).toString();

        String nameKey = getHashMapKeyAndSaveWords("" + Constant.TABLE_LIST.Modifier, toppingID, parentID,
                Constant.MODIFIER_MODIFIERID, englishName, frenchName);

        return new Topping(
                toppingID,
                new BigDecimal(tableRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_PRICE).toString()),
                nameKey,
                (tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSEQ) != null &&
                        !tableRow.get(Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSEQ).equals("") ?
                        Integer.parseInt(tableRow.get(Constant.TABLE_LIST.Modifier + "." +
                                Constant.MODIFIER_WEBSEQ).toString()) : Integer.valueOf(Constant.SEQ_ALTERNATIVE)));

    }

    // this method return unique key for resource boundle properties file.
    private static String getResourceBoundleKey(String tableName, Object firstId, Object secondId, String columnKey) {
        String finalSecondId = Constant.SECONDID_ALTERNATIVE;

        if (!finalSecondId.equals(""))
            finalSecondId += ".";

        return tableName.toLowerCase() + "." +
                String.valueOf(firstId) + "." +
                ((secondId != null) && (!secondId.toString().equals("")) ?
                        secondId.toString() + "."
                        : finalSecondId
                ) +
                columnKey.toLowerCase();

    }

    public static List<CombinedMenuItem> getCategoryCombinedMenuItemList(Category category) {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryCombinedMenuItemList");

        List<CombinedMenuItem> combinedMenuItemList = new ArrayList<CombinedMenuItem>();
        List<SubCategory> subRawCategoryList = category.getSubCategoryList();

        for (SubCategory subCategoryItem : subRawCategoryList) {
            if (subCategoryItem.getType().equals(CombinedMenuItem.class))
                combinedMenuItemList.add((CombinedMenuItem) subCategoryItem.getObject());
        }

        return combinedMenuItemList;
    }

    public static List<MenuSingleItem> getCategoryMenuSingleItemList(Category category) {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryMenuSingleItemList");

        List<MenuSingleItem> menuSingleItemList = new ArrayList<MenuSingleItem>();

        List<SubCategory> subRawCategoryList = category.getSubCategoryList();
        for (SubCategory subCategoryItem : subRawCategoryList) {
            if (subCategoryItem.getType().equals(MenuSingleItem.class))
                menuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
        }
        return menuSingleItemList;
    }

//a subcategory object can contain 3 type of objets[CombinedMenuItem, MenuSingleItem, Category],

    //  this method returns a List of Category objects(InnerCategoryList)

    public static List<Category> getCategoryInnerCategoryList(Category category) {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryInnerCategoryList");

        List<SubCategory> subRawCategoryList = category.getSubCategoryList();
        List<Category> categoryList = new ArrayList<Category>();

        for (SubCategory subCategoryItem : subRawCategoryList) {
            if (subCategoryItem.getType().equals(Category.class))
                categoryList.add((Category) subCategoryItem.getObject());
        }
        return categoryList;
    }

    public static List<Category> getAllInnerCategoryList() {
        Menu menu = getMenuByName(Constant.ORDER_MENU_NAME);

        List<Category> allCategoryList = menu.getCategoryList();
        List<Category> allInnerCategoryList = new ArrayList<Category>();

        for (Category categoryItem : allCategoryList) {
            List<Category> tempInnetCategoryList = getCategoryInnerCategoryList(categoryItem);
            if (tempInnetCategoryList.size() != 0)
                for (Category tempCategoryItem : tempInnetCategoryList) {
                    allInnerCategoryList.add(tempCategoryItem);
                }
        }
        return allInnerCategoryList;
    }

    public static Category getInnerCategoryById(String id) {
        List<Category> allInnerCategoryList = getAllInnerCategoryList();
        for (Category categoryItem : allInnerCategoryList) {
            if (categoryItem.getId().equals(id))
                return categoryItem;
        }
        return null;
    }

    public static CombinedMenuItem findCombinedInnerCategory(String productNo, String groupId, Category category) {
        List<CombinedMenuItem> combinedItemList = InMemoryData.getCategoryCombinedMenuItemList(category);
        for (CombinedMenuItem combined : combinedItemList) {
            if (combined.getProductNo().equals(productNo) && combined.getGroupId().equals(groupId))
                return combined;
        }
        return null;
    }

    //this method returns all of the menusingleitems that exists
    public static List<MenuSingleItem> getAllMenuSingleItemList() {
        Menu menu = getMenuByName(Constant.ORDER_MENU_NAME);

        List<Category> allCategoryList = menu.getCategoryList();
        List<MenuSingleItem> allMenuSIngleItemList = new ArrayList<MenuSingleItem>();


        for (Category categoryItem : allCategoryList) {
            allMenuSIngleItemList.addAll(getCategoryAllMenuSingleItemList(categoryItem));
        }


        Menu special = getMenuByName(Constant.SPECIAL_MENU_NAME);
        allCategoryList = special.getCategoryList();

        for (Category categoryItem : allCategoryList) {
            allMenuSIngleItemList.addAll(getCategoryAllMenuSingleItemList(categoryItem));
        }

        return allMenuSIngleItemList;
    }

    public static List<MenuSingleItem> getCategoryAllMenuSingleItemSkipCombinedList(Category category) {
        List<MenuSingleItem> resultMenuSingleItemList = new ArrayList<MenuSingleItem>();

        List<SubCategory> subRawCategoryList = category.getSubCategoryList();


        for (SubCategory subCategoryItem : subRawCategoryList) {
            if (subCategoryItem.getType().equals(Category.class)) {
                resultMenuSingleItemList.addAll(getCategoryMenuSingleItemList((Category) subCategoryItem.getObject()));

            } else if (subCategoryItem.getType().equals(MenuSingleItem.class))
                resultMenuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
        }
        return resultMenuSingleItemList;
    }


    public static List<MenuSingleItem> getCategoryAllMenuSingleItemList(Category category) {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryAllMenuSingleItemList");

        List<MenuSingleItem> resultMenuSingleItemList = new ArrayList<MenuSingleItem>();

        List<SubCategory> subRawCategoryList = category.getSubCategoryList();


        for (SubCategory subCategoryItem : subRawCategoryList) {
            if (subCategoryItem.getType().equals(Category.class)) {
                resultMenuSingleItemList.addAll(getCategoryMenuSingleItemList((Category) subCategoryItem.getObject()));

            } else if (subCategoryItem.getType().equals(CombinedMenuItem.class)) {
                CombinedMenuItem combinedMenuItem = (CombinedMenuItem) subCategoryItem.getObject();
                resultMenuSingleItemList.addAll(combinedMenuItem.getMenuSingleItemList());

                List<List<MenuSingleItem>> altMenuSingleListList = combinedMenuItem.getAlternatives();
                if (altMenuSingleListList == null) {
                    System.out.println("problem is " + combinedMenuItem.getName(Locale.ENGLISH));
                } else {
                    for (List<MenuSingleItem> listItem : altMenuSingleListList) {
                        resultMenuSingleItemList.addAll(listItem);
                    }
                }

            } else if (subCategoryItem.getType().equals(MenuSingleItem.class))
                resultMenuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
        }
        return resultMenuSingleItemList;
    }

    /**
     * @param id
     * @param groupId
     * @return returns sightly MenuSingleItem
     */
    public static MenuSingleItem findMenuSingleItemByIdAndGroupId(String id, String groupId) {
        List<MenuSingleItem> allMenuSingleItem = getAllMenuSingleItemList();
        if (groupId == null) {

            for (MenuSingleItem menuSingleItem : allMenuSingleItem) {
                if ((menuSingleItem.getId().equals(id)) && (menuSingleItem.getGroupId().equals("")))
                    return menuSingleItem;
            }

        } else {

            for (MenuSingleItem menuSingleItem : allMenuSingleItem) {
                if ((menuSingleItem.getId().equals(id)) && (menuSingleItem.getGroupId().equals(groupId)))
                    return menuSingleItem;
            }

        }

        return null;
    }

    public static MenuSingleItem findMenuSingleItemByModofierId(String modifierId, List<MenuSingleItem> menuSingleItemList){
        for (MenuSingleItem menuSingleItem : menuSingleItemList) {
            if (menuSingleItem.getId().equals(modifierId) )
                return menuSingleItem;
        }
        return null;
    }

    private static Map<String, Object> findModifierRowById(String modifierID) {

        try {
            for (Map<String, Object> modifierRow : modifierTBL) {
                if (modifierRow.get(Constant.MODIFIER_MODIFIERID) != null) {
                    if (!modifierRow.get(Constant.MODIFIER_MODIFIERID).toString().equals("")) {
                        if (Integer.parseInt(modifierRow.get(Constant.MODIFIER_MODIFIERID).toString()) ==
                                Integer.parseInt(modifierID)) {
                            return modifierRow;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("can not fetch service getModifierRowList toppingItemList. cause: " + e.getMessage());
        }

        return null;
    }

    public static Menu getMenuByName(String name) {
        for (Menu menu : menuList) {
            if (menu.getName().equals(name))
                return menu;
        }
        return null;
    }

    /**
     * @return all of the CombinedItems exist in the all menuList items.
     */
    public static List<CombinedMenuItem> getAllCombinedMenuItemList() {
        List<Category> catList;
        List<CombinedMenuItem> combinedList = new ArrayList<CombinedMenuItem>();
        List<SubCategory> subCatList;

        for (Menu menu : menuList) {
            catList = menu.getCategoryList();
            for (Category categry : catList) {
                subCatList = categry.getSubCategoryList();
                for (SubCategory subCat : subCatList) {
                    if (subCat.getType().equals(CombinedMenuItem.class)) {
                        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) subCat.getObject();
                        combinedList.add(combinedMenuItem);
                    }
                }
            }
        }

        return combinedList;
    }

    /**
     * @param prodNo:
     * @param groupId:
     * @return returns sightly combinedItem
     */

    public static CombinedMenuItem findCombinedMenuItemByProdNoAndGroupId(String prodNo, String groupId) {
        List<CombinedMenuItem> combinedList = getAllCombinedMenuItemList();
        for (CombinedMenuItem combinedItem : combinedList) {
            if ((combinedItem.getGroupId().equals(groupId)) && (combinedItem.getProductNo().equals(prodNo)))
                return combinedItem;
        }
        return null;
    }

    public static List<Map<String, Object>> getProdlinkJoinedWithMenuItemAt(String prodNo) {
        return getJoinedGroupId(prodNo);
    }

    public static List<SubCategory> getMenuSingleItemsofInnerCategory(String innerCategoryID) throws Exception {
        /**
         * it might be used for categories in second level like drinks --> can or submarine --> 7" sub
         */
        List<Map<String, Object>> prodLinkRowList;
        List<Map<String, Object>> modifierRowList;
        List<Map<String, Object>> modLinkRowList;
        List<Map<String, Object>> mainProdLinkList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> toppingProdlinkRows = new ArrayList<Map<String, Object>>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();


        prodLinkRowList = getJoinedGroupId(innerCategoryID);

        for (int i = 0; i < prodLinkRowList.size(); i++) {

            Map<String, Object> prodLinkRow = prodLinkRowList.get(i);

            if (prodLinkRow.get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_FORCED).equals(Constant.VALUE_IS_FORCED)) {
                // do nothing, its a topping

            } else {

                mainProdLinkList = new ArrayList<Map<String, Object>>();
                mainProdLinkList.add(prodLinkRow);
                int toppingIndex = i + 1;
                toppingProdlinkRows = new ArrayList<Map<String, Object>>();

                while (toppingIndex < prodLinkRowList.size()) {

                    if (prodLinkRowList.get(toppingIndex).get(Constant.TABLE_LIST.ProdLink + "." + Constant.PRODLINK_FORCED).equals(Constant.VALUE_IS_FORCED)) {
                        toppingProdlinkRows.add(prodLinkRowList.get(toppingIndex));
                        toppingIndex++;

                    } else
                        break;
                }

                modLinkRowList = getJoindModifierId(mainProdLinkList);
                modifierRowList = getModifierRowList(modLinkRowList);

                for (Map<String, Object> modifierRow : modifierRowList) {

                    MenuSingleItem menuSingleItem = InMemoryData.getMenuSingleItemInstance(modifierRow, innerCategoryID,
                            "" + Constant.TABLE_LIST.Modifier);

                    if (toppingProdlinkRows.size() > 0)
                        menuSingleItem.setToppingCategoryList(getDistinctToppingsCategoryList(toppingProdlinkRows, innerCategoryID, menuSingleItem.getId()));

                    else
                        menuSingleItem.setToppingCategoryList(null);

                    SubCategory subCategory = new SubCategory(MenuSingleItem.class, menuSingleItem);
                    subCategories.add(subCategory);

                }
            }
        }


        return subCategories;

    }

    public static List<String> getDefaultModifierIDs(String productNo) {

        List<String> defaultModifierIDs = new ArrayList<String>();

        for (Map<String, Object> aPresetModifierJoinRow : modifier_preset_JoinTBL) {

            if ((aPresetModifierJoinRow.get(Constant.PRESET_BASEITEM).equals(productNo))) {
                if ((Integer) aPresetModifierJoinRow.get(Constant.PRESET_PRESET_SEQUENCE) == 1)
                    defaultModifierIDs.add((String) aPresetModifierJoinRow.get(Constant.PRESET_ITEMNUMBER));

            }
        }

        return defaultModifierIDs;

    }

    public static List<Topping> getAllToppings() {
        List<MenuSingleItem> menuSingleItemList = getAllMenuSingleItemList();
        List<Topping> allToppingList = new ArrayList<Topping>();

        for (MenuSingleItem singleItem : menuSingleItemList) {
            List<ToppingCategory> toppingCategoryList = singleItem.getToppingCategoryList();
            if (toppingCategoryList == null)
                toppingCategoryList = new ArrayList<ToppingCategory>();

            for (ToppingCategory toppingCategory : toppingCategoryList) {
                List<ToppingSubCategory> topSubCat = toppingCategory.getToppingSubCategoryList();
                for (ToppingSubCategory topSubCatItem : topSubCat) {
                    if (topSubCatItem.getClassType().equals(Topping.class))
                        allToppingList.add((Topping) topSubCatItem.getObject());
                    else if (topSubCatItem.getClassType().equals(ToppingCategory.class))
                        allToppingList.addAll(getToppingCategoryToppings((ToppingCategory) topSubCatItem.getObject()));
                }
            }
        }


        return allToppingList;
    }

    public static Topping findToppingById(Integer id) {
        List<Topping> allTopList = getAllToppings();
        for (Topping topItem : allTopList) {
            if (topItem.getId().equals(id)) {
                return topItem;
            }
        }
        return null;
    }

    public static List<Topping> getToppingCategoryToppings(ToppingCategory topCat) {
        List<Topping> toppingList = new ArrayList<Topping>();
        for (ToppingSubCategory topSubCat : topCat.getToppingSubCategoryList()) {
            if (topSubCat.getClassType().equals(Topping.class)) {
                toppingList.add((Topping) topSubCat.getObject());
            } else if (topSubCat.getClassType().equals(ToppingCategory.class)) {
                toppingList.addAll(getToppingCategoryToppings((ToppingCategory) topSubCat.getObject()));
            }
        }
        return toppingList;
    }

    public static CombinedMenuItem getCombinedMenuItemModelByIdAndGroupId(String id, String groupId) {
        List<CombinedMenuItem> combinedMenuItemList = getAllCombinedMenuItemList();
        for (CombinedMenuItem combindItem : combinedMenuItemList) {
            if (combindItem.getProductNo().equals(id) && (combindItem.getGroupId().equals(groupId)))
                return combindItem;
        }
        return null;
    }

    public static String getPizzaCaptionKey(String itemType, String parentId, Integer iteration) {
        String nameKey = null;

        if (itemType.equals(Constant.ITEM_TYPE_PIZZA)) {

            String englishIterationTitle = MessageUtil.get("lable.caption.pizza" + iteration, Locale.ENGLISH);
            String frenchIterationTitle = MessageUtil.get("lable.caption.pizza" + iteration, Locale.FRENCH);
            try {
                for (Object row : topping_categoryTBL) {

                    Object[] toppingOrCaptionTitle = (Object[]) row;

                    if (toppingOrCaptionTitle[3].equals(itemType)) {

                        String englishName = toppingOrCaptionTitle[1].toString();
                        String frenchName = toppingOrCaptionTitle[2].toString();


                        nameKey = getHashMapKeyAndSaveWords("" + Constant.TABLE_LIST.Topping_category,
                                toppingOrCaptionTitle[0], parentId, iteration + "caption",
                                englishIterationTitle + " " + englishName, frenchIterationTitle + " " + frenchName);
                    }
                }

            } catch (Exception e) {
                logger.error("can not execute getToppingOrCaptionTitleKey method. cause: " + e.getMessage() + " on ItemType: " + itemType);
            }
        }

        return nameKey;

    }

    //get topping or caption title per each itemType
    public static String getToppingOrCaptionTitleKey(String itemType, String parentId) {
        String nameKey = null;
        boolean found = false;
        try {
            for (Object row : topping_categoryTBL) {

                Object[] toppingOrCaptionTitle = (Object[]) row;

                if (toppingOrCaptionTitle[3].equals(itemType)) {
                    found = true;
                    String englishName = toppingOrCaptionTitle[1].toString();
                    String frenchName = toppingOrCaptionTitle[2].toString();

                    nameKey = getHashMapKeyAndSaveWords("" + Constant.TABLE_LIST.Topping_category,
                            toppingOrCaptionTitle[0], parentId, "-1-", englishName, frenchName);
                }
            }

            if (!found) {
                nameKey = getHashMapKeyAndSaveWords("" + Constant.TABLE_LIST.Topping_category,
                        getId(itemType), parentId, "-1-", itemType, itemType);

            }

        } catch (Exception e) {
            logger.error("can not execute getToppingOrCaptionTitleKey method. cause: " + e.getMessage() + " on ItemType: " + itemType);
        }
        return nameKey;
    }

//get preSelected SingleItemID per each CombiendMenuItem

    public static List<String> getSpecialSingleItemIds(String CombinedMenuItemID) {

        List<String> specialSingleItemIds = new ArrayList<String>();

        try {
            for (Map<String, Object> presetRow : modifier_preset_JoinTBL) {
                if (presetRow.get(Constant.PRESET_BASEITEM) != null)
                    if (Integer.parseInt(presetRow.get(Constant.PRESET_BASEITEM).toString()) == Integer.parseInt(CombinedMenuItemID)) {
                        specialSingleItemIds.add(presetRow.get(Constant.MODIFIER_MODIFIERID).toString());
                    }
            }
        } catch (Exception e) {
            logger.error("can not execute getSpecialSingleItemIds method. cause: " + e.getMessage() + " on productNo: " + CombinedMenuItemID);
        }
        return specialSingleItemIds;
    }

    public static Category getDefaultCatgeoryByName(String name) {
        Menu menu = getMenuByName(name);
        return menu.getCategoryList().get(0);
    }

    public static Topping findToppingByIdInCategory(Integer id, ToppingCategory toppingCategory) {
        for (ToppingSubCategory item : toppingCategory.getToppingSubCategoryList()) {
            if (item.getClassType() == Topping.class &
                    ((Topping) item.getObject()).getId().equals(id)) {
                return (Topping) item.getObject();
            }
        }
        return null;
    }


    public static String getBasketSingleItemAllToppingsToString(BasketSingleItem basketSingleItem, String part, Boolean addComma, Locale loc) {
        String exclusiveToppingTag = "<img src=\"<context>/img/et-%S.png\" class=\"img_topping\">";

        StringBuffer result = new StringBuffer();
        MenuSingleItem menuSingleItem = basketSingleItem.getSingle();


        Map<Integer, String> selectedToppingMap = basketSingleItem.getSelectedToppingMap();
        if (selectedToppingMap != null) {
            Set<Integer> toppingIds = selectedToppingMap.keySet();
            for (Integer toppingId : toppingIds) {
                String value = selectedToppingMap.get(toppingId);
                if ((part == null & value == null) || (part != null && part.equals(value))) {
                    for (ToppingCategory toppingCatrgory : menuSingleItem.getToppingCategoryList()) {

                        Topping topping = findToppingByIdInCategory(toppingId, toppingCatrgory);
                        if (topping != null) {
                            if (toppingCatrgory.getExclusive()) {

                                List<Integer> defaultsTopping = toppingCatrgory.getDefaultToppingList();
                                if (defaultsTopping != null && !defaultsTopping.contains(toppingId)) {
                                    if (!addComma)
                                        result.append(String.format(exclusiveToppingTag, StringUtil.removeSpaces(toppingCatrgory.getName(Locale.ENGLISH))));
                                    result.append(topping.getName(loc));
                                    if (addComma)
                                        result.append(", ");
                                }
                            } else {
                                result.append(topping.getName(loc));
                                if (addComma)
                                    result.append(",");
                                result.append(" ");
                            }
                            break;
                        }

                    }
                }
            }
        }

        int length = result.length();
        if (length > 1 && result.charAt(length - 2) == ',') {
            result.deleteCharAt(length - 1);
            length = result.length();
            result.deleteCharAt(length - 1);
        }

        return result.toString();
    }

    public List<Tax> getTaxes() {
        return taxService.findAll();
    }


    public static List<Store> createStoresList() {

        List<Store> storeList = new ArrayList<Store>(storeMastTBL.size());

        for (Map<String, Object> storeRow : storeMastTBL) {

            try {
                String storeId = (String) storeRow.get(Constant.STOREMAST_ID);
                String name = (String) storeRow.get(Constant.STOREMAST_NAME);
                Boolean webStatus = (Boolean) storeRow.get(Constant.STOREMAST_WEBSTATUS);
                String imageUrl = (String) storeRow.get(Constant.STOREMAST_IMAGEURL);
                if (imageUrl == null || imageUrl.isEmpty())
                    imageUrl = "branch.png";

                String address1 = (String) storeRow.get(Constant.STOREMAST_ADDRESS1);
                String address2 = (String) storeRow.get(Constant.STOREMAST_ADDRESS2);

                String city = (String) storeRow.get(Constant.STOREMAST_CITY);
                String postalCode = (String) storeRow.get(Constant.STOREMAST_POSTALCODE);

                if (webStatus) {
                    Store store = new Store(storeId, name, city, address1, address2, postalCode, imageUrl, webStatus,
                            getWorkingHourOfThisStore(storeId));
                    storeList.add(store);
                }

            } catch (NullPointerException e) {
                //Do nothing just continue to fill list
            }

        }

        try {
            storeService.fillStoreLocation(storeList);

        } catch (DAOException e) {
            logger.debug(e.getMessage(), e);
        } catch (ServiceException e) {
            logger.debug(e.getMessage(), e);
        }

        return storeList;
    }

    private static List<Suggestion> createSuggestionList(){
        List<Suggestion> suggestionList = null;
        try {
            suggestionList = suggestionservice.findAll();

            for (Suggestion suggestion : suggestionList){
                String modifierId = suggestion.getModifierId();
                BigDecimal suggestionPrice = suggestion.getPrice();

                Map<String, Object> modifierRow = findModifierRowById(modifierId);
                MenuSingleItem menuSingleItem = getMenuSingleItemInstanceForSuggestion(modifierRow,
                        suggestion.getProductNo(), suggestionPrice);

                if (menuSingleItem != null){
                    MenuSingleItem clonedMenuSingleItem = (MenuSingleItem)menuSingleItem.clone();
                    suggestion.setMenuSingleItem(clonedMenuSingleItem);
                }
            }

        } catch (DAOException e) {
            logger.debug(e.getMessage(), e);
        } catch (ServiceException e) {
            logger.debug(e.getMessage(), e);
        }
        return suggestionList;
    }


    //get list of Cities
    public static List<String> getCitiesCoveredByDoublePizza(Locale loc) {
        List<String> cities = new ArrayList<String>();
        ResourceBundle currentBundle = ResourceBundle.getBundle("MessageResources", loc);

        cities.add(currentBundle.getString("store.loacator.all.cities"));

        int index = 0;
        for (Store store : storeList) {
            String newCity = store.getCity();
            boolean found = false;
            for (String city : cities) {
                if (city.equals(newCity))
                    found = true;
            }

            if (!found && store.getWebStatus())
                cities.add(newCity);
        }

        return cities;
    }

    public static boolean isDefaultTopping(MenuSingleItem menuSingleItem, Integer toppingId) {
        for (ToppingCategory toppingCatrgory : menuSingleItem.getToppingCategoryList()) {
            List<Integer> defaultToppingList = toppingCatrgory.getDefaultToppingList();
            if (defaultToppingList != null) {
                for (Integer defaultToppingId : defaultToppingList) {
                    if (defaultToppingId.equals(toppingId))
                        return true;
                }
            }
        }
        return false;

    }

    //get list of Store branches in specific City
    public static List<Store> getStoresInThisCity(String selectedCityKey) {

        ResourceBundle enBundle = ResourceBundle.getBundle("MessageResources", Locale.ENGLISH);
        ResourceBundle frBundle = ResourceBundle.getBundle("MessageResources", Locale.FRENCH);

        String enAllCities = enBundle.getString("store.loacator.all.cities");
        String frAllCities = frBundle.getString("store.loacator.all.cities");

        Boolean isAll = false;

        if (enAllCities != null && frAllCities != null)
            if (enAllCities.equals(selectedCityKey) || frAllCities.equals(selectedCityKey)) {
                isAll = true;
            }

        Boolean selectAllCities = isAll;

        List<Store> storesInThisCity = new ArrayList<Store>();

        for (Store store : storeList) {

            if ((store.getWebStatus()) && (selectAllCities || store.getCity().equals(selectedCityKey)))
                storesInThisCity.add(store);
        }

        return storesInThisCity;
    }

    public static List<Store> getStoresInThisCityOrderByStoreDistance(String selectedCityKey, double currentLat, double currentLon) {
        List<Store> storeList = getStoresInThisCity(selectedCityKey);
        Collections.sort(storeList, new StoreDistanceComperator(currentLat, currentLon));
        return storeList;
    }

    public static List<Store> getStoresInThisCityOrderByStoreDistance(String selectedCityKey, String userPostalCode) {
        Point location = postalCodeService.findLocationByPostalCode(userPostalCode);
        if (location == null) {
            return null;
        }

        double currentLat = location.getX();
        double currentLon = location.getY();

        return getStoresInThisCityOrderByStoreDistance(selectedCityKey, currentLat, currentLon);
    }

    public static List<WorkingHour> getWorkingHoursGroups(String storeId, Locale loc) {

        List<WorkingHour> fakeWorkingHours = new ArrayList<WorkingHour>();

        Store wantedStore = null;

        for (Store store : storeList) {
            if (store.getStoreId().equals(storeId)) {
                wantedStore = store;
            }
        }

        List<List<WorkingHour>> workingHourGroups = new ArrayList<List<WorkingHour>>();

        assert wantedStore != null;
        List<WorkingHour> workingHoursList = wantedStore.getWorkingHours();

        for (WorkingHour workingHour : workingHoursList) {

            boolean found = false;
            for (List<WorkingHour> workingHourGroup : workingHourGroups) {

                WorkingHour firstWorkingHourInGroup = workingHourGroup.get(0);
                if (firstWorkingHourInGroup.getOpeningHour().equals(workingHour.getOpeningHour())
                        && firstWorkingHourInGroup.getClosingHour().equals(workingHour.getClosingHour())) {
                    found = true;
                    workingHourGroup.add(workingHour);
                }
            }

            if (!found) {
                List<WorkingHour> newWorkingHourGroup = new ArrayList<WorkingHour>();
                newWorkingHourGroup.add(workingHour);
                workingHourGroups.add(newWorkingHourGroup);

            }

        }


        Integer fakeDayId = -1;
        StringBuffer daysofWorkingHourGroup;


        for (List<WorkingHour> workingHourGroup : workingHourGroups) {
            daysofWorkingHourGroup = new StringBuffer();

            WorkingHour firstWorkingHourinGroup = workingHourGroup.get(0);
            for (int i = 0; i < workingHourGroup.size(); i++) {

                WorkingHour workingHour = workingHourGroup.get(i);

                daysofWorkingHourGroup.append(workingHour.getDayOfWeek(loc).substring(0, 3));
                if (i < workingHourGroup.size() - 1) {
                    daysofWorkingHourGroup.append("/");
                }
            }


            WorkingHour newFakeWorkingHour = new WorkingHour(fakeDayId, null, daysofWorkingHourGroup.toString(),
                    firstWorkingHourinGroup.getOpeningHour(), firstWorkingHourinGroup.getClosingHour());

            fakeWorkingHours.add(newFakeWorkingHour);

        }

        return fakeWorkingHours;

    }

    private static List<WorkingHour> getWorkingHourOfThisStore(String storeId) {

        List<WorkingHour> workingHours = new ArrayList<WorkingHour>();

        for (Map<String, Object> storeHoursRow : storeHoursTBL) {

            if (storeHoursRow.get(Constant.STOREHOURS_ID).equals(storeId)) {

                Integer dayId = (Integer) storeHoursRow.get(Constant.STOREHOURS_DAY_ID);
                String englishDayOfWeek = (String) storeHoursRow.get(Constant.STOREHOURS_DAY_NAME_EN);
                String frenchDayofWeek = (String) storeHoursRow.get(Constant.STOREHOURS_DAY_NAME_FR);
                String openingHour = (String) storeHoursRow.get(Constant.STOREHOURS_OPENING_HOURS);
                String closingHour = (String) storeHoursRow.get(Constant.STOREHOURS_CLOSING_HOURS);

                String dayOfWeekKey = InMemoryData.getHashMapKeyAndSaveWords("" + Constant.TABLE_LIST.StoreHours,
                        storeId, dayId, Constant.STOREHOURS_DAY_NAME_EN, englishDayOfWeek, frenchDayofWeek);
                WorkingHour workingHour = new WorkingHour(dayId, dayOfWeekKey, null, openingHour, closingHour);
                workingHours.add(workingHour);

            }
        }

        return workingHours;
    }

    public static List<Store> getAllStores() {
        return storeList;
    }

    public static Store getStore(String storeId) {

        for (Store currentItem : storeList) {
            if (currentItem.getStoreId().equals(storeId)) {
                return currentItem;
            }
        }
        return null;
    }

//    public static BasketSingleItem getExpensiveItemOfBasketItem(BasketCombinedItem basketCombinedItem) {
//        BasketCombinedItemHelper helper = new BasketCombinedItemHelper();
//        return helper.getExpensiveItem(basketCombinedItem);
//    }

    public static BigDecimal getCombinedRealPrice(CombinedMenuItem combined) {
        List<BigDecimal> singlePrices = new ArrayList<BigDecimal>();

        for (int i = 0; i <= combined.getMenuSingleItemList().size() - 1; i++) {
            BigDecimal defaultMaxValue = new BigDecimal(1000000000);
            BigDecimal minPrice = defaultMaxValue;

            BigDecimal singlePrice = combined.getMenuSingleItemList().get(i).getPrice();

            if (minPrice.compareTo(singlePrice) == 1)
                minPrice = singlePrice;

            List<MenuSingleItem> alternatives = combined.getAlternatives().get(i);
            for (MenuSingleItem item : alternatives) {
                if (minPrice.compareTo(item.getPrice()) == 1)
                    minPrice = item.getPrice();
            }

            if (minPrice.compareTo(defaultMaxValue) == 0)
                minPrice = new BigDecimal(0);

            singlePrices.add(minPrice);
        }

        BigDecimal maxPriceInPrices = new BigDecimal(0);
        for (BigDecimal price : singlePrices) {
            if (price.compareTo(maxPriceInPrices) == 1)
                maxPriceInPrices = price;
        }

        return maxPriceInPrices;


    }

    public static Category getCombinedParentCategory(CombinedMenuItem combinedMenuItem) {

        for (Menu currentMenu : InMemoryData.menuList) {
            List<Category> categoryList = currentMenu.getCategoryList();
            for (Category category : categoryList) {
                List<CombinedMenuItem> combinedList = getCategoryCombinedMenuItemList(category);
                if (combinedList.contains(combinedMenuItem))
                    return category;
            }
        }
        return null;
    }

    public static Category getSingleParentCategory(MenuSingleItem menuSingleItem) {

        for (Menu currentMenu : InMemoryData.menuList) {
            List<Category> categoryList = currentMenu.getCategoryList();
            for (Category category : categoryList) {
                List<MenuSingleItem> menuSingleList = getCategoryMenuSingleItemList(category);
                if (menuSingleList.contains(menuSingleItem))
                    return category;
            }
        }
        return null;
    }

    public static Category getSubCategoryParentCategory(Category subCategory) {

        for (Menu currentMenu : InMemoryData.menuList) {
            List<Category> categoryList = currentMenu.getCategoryList();
            for (Category categoryItem : categoryList) {
                List<Category> subCategoryList = getCategoryInnerCategoryList(categoryItem);
                if (subCategoryList.contains(subCategory))
                    return categoryItem;
            }
        }
        return null;
    }

    public static Category getSingleParentSubCategory(MenuSingleItem menuSinleItem) {

        for (Menu currentMenu : InMemoryData.menuList) {
            List<Category> categoryList = currentMenu.getCategoryList();
            for (Category categoryItem : categoryList) {
                List<Category> subCategoryList = getCategoryInnerCategoryList(categoryItem);
                for (Category subCategory : subCategoryList) {
                    List<MenuSingleItem> menuSingleItemList = getCategoryMenuSingleItemList(subCategory);
                    if (menuSingleItemList.contains(menuSinleItem))
                        return subCategory;
                }
            }
        }
        return null;
    }

    public static List<BasketSingleItem> getBasketAllSingleItems(Basket basket) {
        List<BasketSingleItem> basketSingleItemList = new ArrayList<BasketSingleItem>();

        for (BasketItem basketItem : basket.getBasketItemList()) {
            if (basketItem.getClassType() == BasketSingleItem.class)
                basketSingleItemList.add((BasketSingleItem) basketItem.getObject());
            else {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                List<BasketSingleItem> basketSingleList = basketCombinedItem.getBasketSingleItemList();
                for (BasketSingleItem basketSingleItem : basketSingleList) {
                    basketSingleItemList.add(basketSingleItem);
                }
            }

        }

        return basketSingleItemList;
    }

    public static BasketSingleItem findBasketSingleItemById(Basket basket, Integer identifier) {
        List<BasketSingleItem> basketSingleItemList = getBasketAllSingleItems(basket);

        for (BasketSingleItem basketSingleItem : basketSingleItemList) {
            if (basketSingleItem.getIdentifier().equals(identifier))
                return basketSingleItem;
        }
        return null;
    }

    public static String getCombinedMenuItemSize(CombinedMenuItem combinedMenuItem) {
        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
        if (menuSingleItemList != null) {
            for (MenuSingleItem menuSingleItem : menuSingleItemList) {
                if (menuSingleItem.getSize().equalsIgnoreCase("small") || menuSingleItem.getSize().equalsIgnoreCase("medium") ||
                        menuSingleItem.getSize().equalsIgnoreCase("large") || menuSingleItem.getSize().equalsIgnoreCase("xlarge")) {
                    return menuSingleItem.getSize();
                }
            }
        }
        return new String("");
    }

    public static Integer getSliceCount(String size) {
        if (size.equalsIgnoreCase("small"))
            return 6;
        else if (size.equalsIgnoreCase("medium"))
            return 8;
        else if (size.equalsIgnoreCase("large"))
            return 10;
        else if (size.equalsIgnoreCase("xlarge"))
            return 12;
        return null;
    }

    public static boolean CombinedIsSpacial(CombinedMenuItem combinedMenuItem) {
        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
        if (menuSingleItemList != null) {
            for (MenuSingleItem menuSingleItem : menuSingleItemList) {
                if (menuSingleItem.isTwoForOne())
                    return false;
            }
        }
        return true;
    }

    public static Integer getNextOrderNumber() {

        synchronized (ORDER_NUMBER_HANDLER) {
            return ORDER_NUMBER_HANDLER.getNextOrderNumber();

        }
    }


    private static void setOrderNumber(Integer newOrderNumber) {

        synchronized (ORDER_NUMBER_HANDLER) {
            ORDER_NUMBER_HANDLER.setOrderNumber(newOrderNumber);
        }
    }

    public static List<Tax> getTaxList() {
        return taxList;
    }

    public static void addTOLangualHashmaps(String key, String englishWord, String frenchWord) {

        if (englishDataHashMap == null)
            englishDataHashMap = new HashMap<String, String>();

        if (frenchDataHashMap == null)
            frenchDataHashMap = new HashMap<String, String>();

        boolean isExistedKey = (frenchDataHashMap.get(key) != null || englishDataHashMap.get(key) != null);

        if (!isExistedKey) {
            englishDataHashMap.put(key, englishWord);
            frenchDataHashMap.put(key, frenchWord);

        }
    }

    public static String getData(String key, Locale loc) {

        String word;

/**
 * if the locale of user is french return the french word but for any other locales return english
 */
        if (loc.equals(Locale.FRENCH) || loc.equals(Locale.FRANCE)) {
            word = frenchDataHashMap.get(key);

        } else {
            word = englishDataHashMap.get(key);

        }

        return word;
    }


    public static String getHashMapKeyAndSaveWords(String tableName, Object firstId, Object secondId,
                                                   String columnKey, String englishWord, String frenchWord) {

        String key = getResourceBoundleKey(tableName, firstId, secondId, columnKey);
        addTOLangualHashmaps(key, englishWord, frenchWord);

        return key;
    }

    public static Integer getId(String str) {

        int id = 0;

        for (int i = 0; i < str.length(); i++) {
            id += str.charAt(i);
        }

        return id;
    }

    public static Store findStoreById(String storeNo) {
        for (Store store : storeList) {
            if (store.getStoreId().equals(storeNo))
                return store;
        }
        return null;
    }

    public static boolean toppingIsCountable(String toppingName) {
        return (toppingName != null) && (!toppingName.equalsIgnoreCase("free") & !toppingName.equalsIgnoreCase("dairy"));
    }

    public static ItemNames getSingleItemOrToppingNames(String id, String groupID) {

        String itemName = "";
        String itemName2 = "";

        if (groupID == null || groupID.equals("")) {
            for (Map<String, Object> modifier : modifierTBL) {
                if (Integer.parseInt(id) == Integer.parseInt((String) modifier.get(Constant.MODIFIER_MODIFIERID))) {
                    itemName = (String) modifier.get(Constant.MODIFIER_FULL_DESCRIPTION);
                    itemName2 = (String) modifier.get(Constant.MODIFIER_FULL_DESCRIPTION2);

                }
            }
            return new ItemNames(itemName, itemName2);

        } else {
            /**
             * its not a combined but has been defined in DB like combineds
             */
            return getCombinedItemNames(id, groupID);

        }
    }


    public static ItemNames getCombinedItemNames(String productNo, String groupID) {

        String itemName = "";
        String itemName2 = "";

        for (Map<String, Object> menuItem : menuitemTBL) {


            if (Integer.parseInt(productNo) == (Integer.parseInt((String) (menuItem.get(Constant.MENUITEM_PRODUCTNO))))
                    && Integer.parseInt(groupID) == Integer.parseInt((String) menuItem.get(Constant.MENUITEM_GROUPID))) {

                itemName = (String) menuItem.get(Constant.MENUITEM_DESCRIPTION);
                itemName2 = (String) menuItem.get(Constant.MENUITEM_DESCRIPTION2);

            }
        }

        return new ItemNames(itemName, itemName2);
    }

    public static String getFileServicePath(String dir) {
        return "/downloadFile.do?fileName=" + dir + ApplicationConfig.pathSplitterSign;
    }

    public static String getImageServicePath(String dir) {
        return "/imageServices?image=" + dir + ApplicationConfig.pathSplitterSign;
    }

    public static String getImageServicePath(String dir1, String dir2) {
        return getImageServicePath(dir1 + ApplicationConfig.pathSplitterSign + dir2);
    }

    public static String getContextRealPath(String nextPath) {
        return InMemoryData.context.getServletContext().getRealPath(nextPath);
    }

    public static List<Topping> getMenuSingleItemDefaultToppings(MenuSingleItem menuSingleItem) {
        List<Topping> defaultToppings = new ArrayList<Topping>();
        List<ToppingCategory> categories = menuSingleItem.getToppingCategoryList();
        if (categories != null)
            for (ToppingCategory item : categories) {
                for (Integer id : item.getDefaultToppingList()) {
                    Topping topping = findToppingByIdInCategory(id, item);
                    if (topping != null)
                        defaultToppings.add(topping);
                }
            }
        return defaultToppings;
    }

    public static List<Topping> getMenuSingleItemDefaultToppingsWithoutExclusives(MenuSingleItem menuSingleItem) {
        List<Topping> defaultToppings = new ArrayList<Topping>();
        List<ToppingCategory> categories = menuSingleItem.getToppingCategoryList();
        if (categories != null)
            for (ToppingCategory item : categories) {
                if (!item.getExclusive())
                    for (Integer id : item.getDefaultToppingList()) {
                        Topping topping = findToppingByIdInCategory(id, item);
                        if (topping != null)
                            defaultToppings.add(topping);
                    }
            }
        return defaultToppings;
    }

    public static boolean isDefaultTopping(MenuSingleItem menuSingleItem, Topping topping) {
        List<Topping> defaultToppings = InMemoryData.getMenuSingleItemDefaultToppings(menuSingleItem);
        return defaultToppings.contains(topping);
    }

    public static void basketCombinedItemRestoreToDefault(BasketCombinedItem basketCombinedItem) {
        CombinedMenuItem combinedMenuItem = basketCombinedItem.getCombined();
        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

        if (combinedMenuItem != null) {
            for (int i = 0; i < basketSingleItemList.size(); i++) {
                BasketSingleItem basketSingleItem = basketSingleItemList.get(i);
                MenuSingleItem menuSingleItem = combinedMenuItem.getMenuSingleItemList().get(i);
                basketSingleItem.setGroupId(menuSingleItem.getGroupId());
                basketSingleItem.setId(menuSingleItem.getId());
                basketSingleItem.getSelectedToppingMap().clear();
            }
        }
    }

    public static Boolean basketSingleItemDefaultToppingsIsExist(MenuSingleItem menuSingleItem, Map<Integer, String> selectedToppings) {
        if (selectedToppings == null || menuSingleItem == null)
            return null;

        List<Topping> defaultToppings = InMemoryData.getMenuSingleItemDefaultToppings(menuSingleItem);
        for (Topping topping : defaultToppings) {
            boolean exist = false;
            for (Integer toppingId : selectedToppings.keySet()) {
                String part = selectedToppings.get(toppingId);
                if (topping.getId().equals(toppingId)) {
//                    if ((part != null && part.equals(Constant.TOPPING_PART_FULL)) || part == null){
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                return false;
            }
        }
        return true;
    }

    public static void removeDefaultToppings(MenuSingleItem menuSingleItem, Map<Integer, String> selectedToppings) {
        if (selectedToppings == null)
            return;

        Map<Integer, String> newSelectedToppings = new HashMap<Integer, String>(selectedToppings);

        List<Topping> defaultToppings = InMemoryData.getMenuSingleItemDefaultToppings(menuSingleItem);

        for (Topping topping : defaultToppings) {
            for (Integer toppingId : newSelectedToppings.keySet()) {
                String part = newSelectedToppings.get(toppingId);
                if (topping.getId().equals(toppingId)) {
//                    if ((part != null && part.equals(Constant.TOPPING_PART_FULL)) || part == null)
                    selectedToppings.remove(toppingId);
                    break;
                }
            }
        }
    }

    public static String getParentCategoryId(Integer sigleChildId) {

        for (int i = 0; i < menuList.size(); i++) {
            List<Category> categories = ((Menu) menuList.get(i)).getCategoryList();

            for (Category category1 : categories) {
                List<SubCategory> subCategories = category1.getSubCategoryList();
                for (SubCategory subCategory : subCategories) {
                    if (subCategory.getType().equals(Category.class)) {
                        Category innerCategory = (Category) subCategory.getObject();
                        List<SubCategory> innerSubCategories = innerCategory.getSubCategoryList();
                        for (SubCategory innerSubCategory : innerSubCategories) {
                            if (innerSubCategory.getType().equals(MenuSingleItem.class) && (Integer.parseInt(((MenuSingleItem) innerSubCategory.getObject()).getId()) == sigleChildId))
                                return innerCategory.getId();

                        }
                    }
                }
            }

        }
        return null;
    }



}