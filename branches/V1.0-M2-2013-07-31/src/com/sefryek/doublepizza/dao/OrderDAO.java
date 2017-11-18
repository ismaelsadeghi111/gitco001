package com.sefryek.doublepizza.dao;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.rules.BlankPizzaReplacingRule;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.common.util.serialize.StringUtil;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Collection;


/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Mar 26, 2012
 * Time: 4:24:16 PM
 */
public class OrderDAO extends BaseDAO {
    private class WebOrderDetails {
        private String docNumber;
        private Integer quantity;
        private String itemKind;
        private String itemNumber;
        private String itemName;
        private String itemName2;
        private BigDecimal itemPrice;
        private Boolean twoforOne;
        private BigDecimal freeMods;
        private BigDecimal freeModPrice;
        private String productNo;
        private String modifierId;
        private String modGroupNo;
        private String baseItem;
        private Integer Level2;
    }

    private static Logger logger = Logger.getLogger(OrderDAO.class);
    private static final String DOCNUMBER_PRIFIX_CHAR = "w";
    private static final String Insert_To_Web_Header =
            "INSERT INTO Web_Header (Docnumber, Takeout, Cust, Ext, Todaydate, Store, Total) " +
                    " VALUES (:docnumber, :takeout, :cust, :ext, :todaydate, :store, :total)";

    private static final String Insert_To_Web_Cust =
            "INSERT INTO webCust (DocNumber, Lastname, Phone," +
                    " Street, Apt, City, Pc, ext, " +
                    "Ccode, Building, Room,Takeout, Paytype, isRegistered, Notes, Store, Num) " +
                    "VALUES (:docnumber, :lastname, :phone, " +
                    ":street, :apt,:city, :pc, :ext ," +
                    ":ccode, :building, :room,:takeout, :paytype, :isregistered, :notes, :store, :num)";

    private static final String Insert_To_Web_Details =
            "INSERT INTO Web_Details (Docnumber, Taxable, Quantity, ItemKind, " +
                    "ItemNumber, ItemName, ItemName2, ItemPrice, Twoforone, Freemods, Freemodprice, Seq, productNo," +
                    " modifierID, modGroupNo, BaseItem, Lev2, Store)" +
                    "VALUES (:docnumber, 0, :quantity, :itemkind, :itemnumber, :itemname, :itemname2, " +
                    " :itemprice, :twoforone, :freemods, :freemodprice, :seq, :productno, :modifierid, :modgroupno, :baseitem, :lev2, :store)";

    private static final String Read_Last_DocNumber =
            "SELECT Top 1 DocNumber FROM LastDocNumber";

    private static final String Insert_Last_DocNumber =
            "Insert Into LastDocNumber (DocNumber) values(:docnumber) ";

    private static final String Update_Last_DocNumber =
            "Update LastDocNumber set DocNumber=:docnumber";

    private boolean repeatForLeftToppings(BasketSingleItem basketSingleItem) {
        Map<Integer, String> selectedToppings = basketSingleItem.getSelectedToppingMap();
        if (selectedToppings != null) {
            Collection<String> values = selectedToppings.values();
            for (String item : values) {
                if (item != null && item.equalsIgnoreCase(Constant.TOPPING_PART_LEFT))
                    return true;
            }
        }
        return false;
    }

    private boolean repeatForRightToppings(BasketSingleItem basketSingleItem) {
        Map<Integer, String> selectedToppings = basketSingleItem.getSelectedToppingMap();
        if (selectedToppings != null) {
            Collection<String> values = selectedToppings.values();
            for (String item : values) {
                if (item != null && item.equalsIgnoreCase(Constant.TOPPING_PART_RIGHT))
                    return true;
            }
        }
        return false;
    }

    private void addWebDetailsItem(Session session, WebOrderDetails webOrderDetails, SequenceHandler sequence, String store) {
        session.createSQLQuery(Insert_To_Web_Details)
                .setString("docnumber", webOrderDetails.docNumber)
                .setInteger("quantity", webOrderDetails.quantity)
                .setString("itemkind", webOrderDetails.itemKind)
                .setString("itemnumber", StringUtil.putZeroToNumber(webOrderDetails.itemNumber, 4))
                .setString("itemname", webOrderDetails.itemName)
                .setString("itemname2", webOrderDetails.itemName2)
                .setBigDecimal("itemprice", webOrderDetails.itemPrice)
                .setBoolean("twoforone", webOrderDetails.twoforOne)
                .setBigDecimal("freemods", webOrderDetails.freeMods)
                .setBigDecimal("freemodprice", webOrderDetails.freeModPrice)
                .setInteger("seq", sequence.getNextValue())
                .setString("productno", StringUtil.putZeroToNumber(webOrderDetails.productNo, 4))
                .setString("modifierid", webOrderDetails.modifierId.equals("") ? "" : StringUtil.putZeroToNumber(webOrderDetails.modifierId, 4))
                .setString("modgroupno", StringUtil.putZeroToNumber(webOrderDetails.modGroupNo, 4))
                .setString("baseitem", webOrderDetails.baseItem)
                .setInteger("lev2", webOrderDetails.Level2)
                .setString("store", store)
                .executeUpdate();
    }

    private void addBasketSingleItemToWebDetails(Session session, BasketSingleItem basketSingleItem,
                                                 String docnumber, CombinedMenuItem parentCombined,
                                                 BasketCombinedItem basketCombinedItem, boolean isParentCombinedSpecial,
                                                 SequenceHandler sequence, String store, String categoryId) {
        Boolean repeatForLeft = repeatForLeftToppings(basketSingleItem);
        Boolean repeatForRight = repeatForRightToppings(basketSingleItem);
        MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
        ItemNames itemNames = InMemoryData.getSingleItemOrToppingNames(menuSingleItem.getId(), "");

        Map<Integer, String> selectedToppings = basketSingleItem.getSelectedToppingMap();
        Boolean allDefaultToppingsIsExist = InMemoryData.basketSingleItemDefaultToppingsIsExist(menuSingleItem, selectedToppings);
        if (allDefaultToppingsIsExist != null && allDefaultToppingsIsExist)
            InMemoryData.removeDefaultToppings(menuSingleItem, selectedToppings);

        InMemoryData.removeDefaultExclusiveToppings(menuSingleItem, selectedToppings);

        WebOrderDetails webOrderDetails = new WebOrderDetails();
        webOrderDetails.docNumber = docnumber;
        webOrderDetails.quantity = 1;
        webOrderDetails.itemKind = "I";

        if (menuSingleItem.getProductNo() != null)
            webOrderDetails.itemNumber = StringUtil.putZeroToNumber(menuSingleItem.getProductNo(), 4);
        else
            webOrderDetails.itemNumber = StringUtil.putZeroToNumber(menuSingleItem.getId(), 4);

        if (allDefaultToppingsIsExist != null && !allDefaultToppingsIsExist && menuSingleItem.isPizza()) {
            webOrderDetails.itemName = itemNames.getItemName() + Constant.SINGLE_MENU_ITEM_NATURE;
            webOrderDetails.itemName2 = "";
        } else {
            webOrderDetails.itemName = itemNames.getItemName();
            webOrderDetails.itemName2 = itemNames.getItemName2();
        }
        webOrderDetails.itemPrice = menuSingleItem.getPrice();
        webOrderDetails.twoforOne = menuSingleItem.isTwoForOne();
        webOrderDetails.freeMods = new BigDecimal(menuSingleItem.getFreeToppingNo());

        webOrderDetails.freeModPrice = menuSingleItem.getFreeToppingPrice();
        if (parentCombined == null) {
            if (categoryId != null)
                webOrderDetails.productNo = StringUtil.putZeroToNumber(categoryId, 4);
            else if (menuSingleItem.getProductNo() != null)
                webOrderDetails.productNo = menuSingleItem.getProductNo();
            else
                webOrderDetails.productNo = StringUtil.putZeroToNumber(menuSingleItem.getId(), 4);

        } else
            webOrderDetails.productNo = StringUtil.putZeroToNumber(parentCombined.getProductNo(), 4);

        if (menuSingleItem.getIdType() == MenuSingleItem.IdType.ProductNo )
            webOrderDetails.modifierId = "";
        else
            webOrderDetails.modifierId = StringUtil.putZeroToNumber(menuSingleItem.getId(), 4);

        if (menuSingleItem.getGroupId() == null)
            webOrderDetails.modGroupNo = "";
        else
            webOrderDetails.modGroupNo = StringUtil.putZeroToNumber(menuSingleItem.getGroupId(), 4);

        webOrderDetails.baseItem = "";
        webOrderDetails.Level2 = repeatForRight || repeatForLeft ? 1 : 0;
        if (parentCombined != null) {
            //get default single in parent combined
            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
            if (basketSingleItemList != null) {
                int index = basketSingleItemList.indexOf(basketSingleItem);
                if (index > -1) {
                    List<MenuSingleItem> menuSingleItemList = parentCombined.getMenuSingleItemList();
                    if (menuSingleItemList != null) {
                        MenuSingleItem defaultMenuSingleItem = menuSingleItemList.get(index);
                        if (defaultMenuSingleItem != null)
                            webOrderDetails.baseItem = defaultMenuSingleItem.getId();
                    }
                }
            }
        }
        if (!repeatForLeft & !repeatForLeft) {
            addWebDetailsItem(session, webOrderDetails, sequence, store);
            addToppingsToWebDetails(session, basketSingleItem, docnumber, parentCombined, menuSingleItem, Constant.TOPPING_PART_FULL, 0, sequence, store, categoryId);
        }
        if (repeatForLeft) {
            addWebDetailsItem(session, webOrderDetails, sequence, store);
            addToppingsToWebDetails(session, basketSingleItem, docnumber, parentCombined, menuSingleItem, Constant.TOPPING_PART_LEFT, 1, sequence, store, categoryId);
        }

        if (repeatForRight) {
            addWebDetailsItem(session, webOrderDetails, sequence, store);
            addToppingsToWebDetails(session, basketSingleItem, docnumber, parentCombined, menuSingleItem, Constant.TOPPING_PART_RIGHT, 1, sequence, store, categoryId);
        }


    }

    private void addToppingsToWebDetails(Session session, BasketSingleItem basketSingleItem,
                                         String docnumber, CombinedMenuItem parentCombined,
                                         MenuSingleItem menuSingleItem, String portion,
                                         Integer level2,
                                         SequenceHandler sequence, String store, String categoryId) {

        List<ToppingCategory> categories = menuSingleItem.getToppingCategoryList();
        Map<Integer, String> selectedToppings = basketSingleItem.getSelectedToppingMap();
        if (categories != null) {
            if (selectedToppings != null)
                for (Integer id : selectedToppings.keySet()) {
                    String value = selectedToppings.get(id);
                    if (value == null || ((value.equals(Constant.TOPPING_PART_FULL)) || value.equals(Constant.TOPPING_PART_NO_DEFAULT)) || (value.equals(portion))) {
                        for (ToppingCategory item : categories) {
                            Topping topping = InMemoryData.findToppingByIdInCategory(id, item);
                            if (topping != null) {
                                ItemNames itemNames = InMemoryData.getSingleItemOrToppingNames(topping.getId().toString(), null);
                                WebOrderDetails toppingWebOrderDetails = new WebOrderDetails();
                                toppingWebOrderDetails.docNumber = docnumber;
                                toppingWebOrderDetails.quantity = 1;
                                toppingWebOrderDetails.itemKind = "M";
                                toppingWebOrderDetails.itemNumber = StringUtil.putZeroToNumber(topping.getId().toString(), 4);

                                //TOdO mona check if it is needed or not, it might have conflict with put zero method
                                if (toppingWebOrderDetails.itemNumber.length() > 4)
                                    toppingWebOrderDetails.itemNumber = toppingWebOrderDetails.itemNumber.substring(0, 4);
                                toppingWebOrderDetails.itemName = itemNames.getItemName();
                                if (value != null && value.equals(Constant.TOPPING_PART_NO_DEFAULT))
                                    toppingWebOrderDetails.itemName = Constant.TOPPING_NO_SELECTED_DEFAULT + toppingWebOrderDetails.itemName;
                                toppingWebOrderDetails.itemName2 = itemNames.getItemName2();
                                toppingWebOrderDetails.itemPrice = topping.getPrice();
                                toppingWebOrderDetails.twoforOne = menuSingleItem.isTwoForOne();
                                toppingWebOrderDetails.freeMods = new BigDecimal(0);
                                toppingWebOrderDetails.freeModPrice = new BigDecimal(0);

                                if (parentCombined == null) {
                                    if (categoryId == null)
                                        toppingWebOrderDetails.productNo = StringUtil.putZeroToNumber(menuSingleItem.getId(), 4);

                                    else
                                        toppingWebOrderDetails.productNo = StringUtil.putZeroToNumber(categoryId, 4);


                                } else
                                    toppingWebOrderDetails.productNo = StringUtil.putZeroToNumber(parentCombined.getProductNo(), 4);

                                toppingWebOrderDetails.modifierId = topping.getId().toString();
                                if (toppingWebOrderDetails.modifierId.length() > 4)
                                    toppingWebOrderDetails.modifierId = toppingWebOrderDetails.modifierId.substring(0, 4);
                                toppingWebOrderDetails.modGroupNo = StringUtil.putZeroToNumber(item.getId().toString(), 4);
                                toppingWebOrderDetails.baseItem = "";
                                toppingWebOrderDetails.Level2 = level2;
                                addWebDetailsItem(session, toppingWebOrderDetails, sequence, store);
                            }
                        }
                    }
                }
        }
    }


    public void save(Order order) throws DAOException {
        String docnumber = null;
        logger.debug("start saving new order");
        try {
            Session session = getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession();

            User user = order.getUser();

            //critical section!
            docnumber = createDocNumber();
            //end of critical section
            logger.debug("create new docnumber " + docnumber);
            order.setDocNumber(docnumber);
            String store = order.getStore();

            if (store == null || store.equals("")){
                store = Constant.NO_DELIVERY_CODE;
                logger.warn("store number was null or blank and set to 099 value for docnumber " + docnumber);
            }


            //create web header item and return docnumber
            session.createSQLQuery(Insert_To_Web_Header)
                    .setString("docnumber", docnumber)
                    .setString("cust", user.getCellPhone())
                    .setString("ext", user.getExt())
                    .setTimestamp("todaydate", order.getOrderDate())
                    .setString("store", store)
                    .setBigDecimal("total", order.getTotalPrice())
                    .setBoolean("takeout", order.getDeliveryType() == Order.DeliveryType.PICKUP)
                    .executeUpdate();

            //create web cust item
            session.createSQLQuery(Insert_To_Web_Cust)
                    .setString("docnumber", docnumber)
                    .setString("lastname", user.getLastName())
                    .setString("phone", user.getCellPhone())
                    .setString("street", user.getStreetName())
                    .setString("apt", user.getSuiteApt())
                    .setString("city", user.getCity())
                    .setString("pc", user.getPostalCode())
                    .setString("ext", user.getExt())
                    .setString("ccode", order.getCouponCode())
                    .setString("building", user.getBuilding())
                    .setString("room", user.getDoorCode())
                    .setBoolean("takeout", order.getDeliveryType() == Order.DeliveryType.PICKUP)
                    .setString("paytype", order.getPaymentType())
                    .setBoolean("isregistered", true)
                    .setString("notes", "")
                    .setString("store", store)
                    .setString("num", user.getStreetNo())
                    .executeUpdate();

            SequenceHandler sequence = new SequenceHandler();
            List<BasketItem> basketItemList = order.getBasketItems();

            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getClassType().equals(BasketCombinedItem.class)) {
                    BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                    CombinedMenuItem combinedMenuItem = basketCombinedItem.getCombined();
                    Boolean isSpacial = InMemoryData.CombinedIsSpecial(combinedMenuItem); 
                    ItemNames itemNames = InMemoryData.getCombinedItemNames(combinedMenuItem.getProductNo(), combinedMenuItem.getGroupId());

                    if (combinedMenuItem.getPrintable()) {
                        WebOrderDetails webOrderDetails = new WebOrderDetails();
                        webOrderDetails.docNumber = docnumber;
                        webOrderDetails.quantity = 1;
                        webOrderDetails.itemKind = combinedMenuItem.isPreset() ? "P" : "I";
                        webOrderDetails.itemNumber = "";
                        webOrderDetails.itemName = itemNames.getItemName();
                        webOrderDetails.itemName2 = itemNames.getItemName2();
                        webOrderDetails.itemPrice = new BigDecimal(0);
                        webOrderDetails.twoforOne = !isSpacial;
                        webOrderDetails.freeMods = new BigDecimal(0);
                        webOrderDetails.freeModPrice = new BigDecimal(0);
                        webOrderDetails.productNo = StringUtil.putZeroToNumber(combinedMenuItem.getProductNo(), 4);
                        webOrderDetails.modifierId = "";
                        webOrderDetails.modGroupNo = "";
                        webOrderDetails.baseItem = "";
                        webOrderDetails.Level2 = 0;
                        addWebDetailsItem(session, webOrderDetails, sequence, store);

                    }

                    for (BasketSingleItem basketSingleItem : basketCombinedItem.getBasketSingleItemList()) {
                        addBasketSingleItemToWebDetails(session, new BlankPizzaReplacingRule(basketSingleItem).checkRule(),
                                docnumber, combinedMenuItem, basketCombinedItem, isSpacial, sequence, store, null);
                    }

                } else {
                    BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();
                    String parentCategoryId = InMemoryData.getParentCategoryId(Integer.parseInt(basketSingleItem.getId()));
                    addBasketSingleItemToWebDetails(session, basketSingleItem, docnumber, null, null, false, sequence, store, parentCategoryId);

                }
            }

            updateLastDocNumber(docnumber);
            

        } catch (Exception e) {
            logger.debug("Debug: save order on DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        finally{
            logger.debug("exit saving order. docnumber " + docnumber);
        }
    }

    public Integer getLastDocNumber() {
        Integer result = (Integer) getHibernateTemplate().getSessionFactory()
                .getCurrentSession()
                .createSQLQuery(Read_Last_DocNumber)
                .addScalar("DocNumber", Hibernate.INTEGER)
                .uniqueResult();
        return result;
    }

    public void createLastDocNumber(Integer docNumber) {
        getHibernateTemplate().getSessionFactory()
                .getCurrentSession()
                .createSQLQuery(Insert_Last_DocNumber)
                .setInteger("docnumber", docNumber)
                .executeUpdate();
    }

    private void updateLastDocNumber(String docNumberStr) {
        Integer docNumber = Integer.valueOf(docNumberStr.replace(DOCNUMBER_PRIFIX_CHAR, ""));
        getHibernateTemplate().getSessionFactory()
                .getCurrentSession()
                .createSQLQuery(Update_Last_DocNumber)
                .setInteger("docnumber", docNumber)
                .executeUpdate();
    }

    public List<String> getAllOrderId() {
        return getHibernateTemplate().getSessionFactory()
                .getCurrentSession()
                .createSQLQuery("SELECT * FROM Web_Header")
                .addScalar("docNumber", Hibernate.STRING).list();
    }

    private synchronized String createDocNumber() {

        String lastDocNumber = (InMemoryData.getNextOrderNumber()).toString();
        return DOCNUMBER_PRIFIX_CHAR + lastDocNumber;
    }
}


class SequenceHandler {
    private Integer Sequence = 0;

    public Integer getNextValue() {
        return ++Sequence;
    }
}