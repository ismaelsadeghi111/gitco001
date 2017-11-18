package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.PopularCategory;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * User: sepehr
 * Date: Sep 12, 2011
 * Time: 2:35:29 AM
 */

public class MenuDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(MenuDAO.class);

    public List loadTermTitlesCOL() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.TERM_ID + "," +
                    Constant.TERM_NAME_EN + "," +
                    Constant.TERM_DESCRIPTION_EN + "," +
                    Constant.TERM_NAME_FR + "," +
                    Constant.TERM_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.Term.toString() +
                    " Order By " + Constant.TERM_ID;// sorted by 'id'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.TERM_ID, Hibernate.STRING)
                    .addScalar(Constant.TERM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.TERM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.TERM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.TERM_DESCRIPTION_FR, Hibernate.STRING);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadToppingCategoryTitlesCOL() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.TOPPING_CATEGORY_ID + "," +
                    Constant.TOPPING_CATEGORY_TITLE_EN + "," +
                    Constant.TOPPING_CATEGORY_TITLE_FR + "," +
                    Constant.TOPPING_CATEGORY_URL + "," +
                    Constant.TOPPING_CATEGORY_IMAGE_NAME + "," +
                    Constant.TOPPING_CATEGORY_WEB_SEQUENCE +
                    " FROM " + Constant.TABLE_LIST.Topping_category.toString() +
                    " Order By " + Constant.TOPPING_CATEGORY_ID;// sorted by 'id'
            logger.debug("loadToppingCategoryTitlesCOL, queryStr= " + queryStr);
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.TOPPING_CATEGORY_ID, Hibernate.INTEGER)
                    .addScalar(Constant.TOPPING_CATEGORY_TITLE_EN, Hibernate.STRING)
                    .addScalar(Constant.TOPPING_CATEGORY_TITLE_FR, Hibernate.STRING)
                    .addScalar(Constant.TOPPING_CATEGORY_URL, Hibernate.STRING)
                    .addScalar(Constant.TOPPING_CATEGORY_IMAGE_NAME, Hibernate.STRING)
                    .addScalar(Constant.TOPPING_CATEGORY_WEB_SEQUENCE, Hibernate.INTEGER);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadModifierTitleCOL() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.MODIFIER_MODIFIERID + "," +
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.MODIFIER_ISREDEEMABLE + "," +
                    Constant.MODIFIER_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.Modifier.toString() +
                    " where " + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_ISREDEEMABLE, Hibernate.BOOLEAN);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadWebCategTitlesCOL() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.WEBCATEGORIES_ID + "," +
                    Constant.WEBCATEGORIES_NAME_EN + "," +
                    Constant.WEBCATEGORIES_DESCRIPTION_EN + "," +
                    Constant.WEBCATEGORIES_NAME_FR + "," +
                    Constant.WEBCATEGORIES_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.WebCategories.toString() +
                    " where " + Constant.WEBCATEGORIES_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.WEBCATEGORIES_SEQUENCE;// sorted by 'CategSeq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.WEBCATEGORIES_ID, Hibernate.INTEGER)
                    .addScalar(Constant.WEBCATEGORIES_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_DESCRIPTION_FR, Hibernate.STRING);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadMenuItemTitlesCOL() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.MENUITEM_PRODUCTNO + "," +
                    Constant.MENUITEM_GROUPID + "," +
                    Constant.MENUITEM_DESCRIPTION + "," +
                    Constant.MENUITEM_DESCRIPTION2 + "," +
                    Constant.MENUITEM_NAME_EN + "," +
                    Constant.MENUITEM_DESCRIPTION_EN + "," +
                    Constant.MENUITEM_NAME_FR + "," +
                    Constant.MENUITEM_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.MenuItem.toString() +
                    " where " + Constant.MENUITEM_WEB_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MENUITEM_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_GROUPID, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_FR, Hibernate.STRING);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadMenuItemTable() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.MENUITEM_PRODUCTNO + "," +
                    Constant.MENUITEM_GROUPID + "," +
                    Constant.MENUITEM_PRODUCTNAME + "," +
                    Constant.MENUITEM_DESCRIPTION + "," +
                    Constant.MENUITEM_DESCRIPTION2 + "," +
                    Constant.MENUITEM_SEQUENCE + "," +
                    Constant.MENUITEM_CATEGORY + "," +
                    Constant.MENUITEM_WEBIMAGE + "," +
                    Constant.MENUITEM_PRESET + "," +
                    Constant.MENUITEM_NAME_EN + "," +
                    Constant.MENUITEM_NAME_FR + "," +
                    Constant.MENUITEM_PRICE + "," +
                    Constant.MENUITEM_FREEMODS + "," +
                    Constant.MENUITEM_FREEMODPRICE + "," +
                    Constant.MENUITEM_TWOFORONE + "," +
                    Constant.MENUITEM_PORTION + "," +
                    Constant.MENUITEM_SIZE + "," +
                    Constant.MENUITEM_SPECIAL + "," +
                    Constant.MENUITEM_DESCRIPTION_EN + "," +
                    Constant.MENUITEM_DESCRIPTION_FR + "," +
                    Constant.MENUITEM_NO_PRINT + "," +
                    Constant.MENUITEM_ISREDEEMABLE +
                    " FROM " + Constant.TABLE_LIST.MenuItem +
                    " where " + Constant.MENUITEM_WEB_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'
            logger.debug("loadMenuItemTable, query= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MENUITEM_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_GROUPID, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRODUCTNAME, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_SEQUENCE, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_CATEGORY, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_WEBIMAGE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRESET, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_FREEMODS, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_FREEMODPRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_TWOFORONE, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_PORTION, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_SIZE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_SPECIAL, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_ISREDEEMABLE, Hibernate.BOOLEAN);



            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List<Map<String, Object>> loadProdLinkTable() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.PRODLINK_PRODUCTNO + "," +
                    Constant.PRODLINK_MODGROUPNO + "," +
                    Constant.PRODLINK_PRESETSEQ + "," +
                    Constant.PRODLINK_SEQ + "," +
                    Constant.PRODLINK_PRICE + "," +
                    Constant.PRODLINK_FORCED +
                    " FROM " + Constant.TABLE_LIST.ProdLink +
                    " Order By " + Constant.PRESET_PRESET_SEQUENCE +","+Constant.PRODLINK_SEQ ;// sorted by 'PresetSeq' and 'Seq'

            logger.debug("loadProdLinkTable, queryStr= " + queryStr);
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.PRODLINK_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.PRODLINK_MODGROUPNO, Hibernate.STRING)
                    .addScalar(Constant.PRODLINK_PRESETSEQ, Hibernate.INTEGER)
                    .addScalar(Constant.PRODLINK_SEQ, Hibernate.INTEGER)
                    .addScalar(Constant.PRODLINK_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.PRODLINK_FORCED, Hibernate.INTEGER);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return (List<Map<String, Object>>) query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadModifierTable() throws DAOException {

        try {

            String queryStr = "SELECT " +
                    Constant.MODIFIER_MODIFIERID + "," +
                    Constant.MODIFIER_ITEM_TYPE + "," +
                    Constant.MODIFIER_SIZE + "," +
                    Constant.MODIFIER_IMAGE + "," +
                    Constant.MODIFIER_NUMTOPPING + "," +
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_FR + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.MODIFIER_WEBSEQ + "," +
                    Constant.MODIFIER_WEBSTATUS + "," +
                    Constant.MODIFIER_ISREDEEMABLE + "," +
                    Constant.MODIFIER_ISFOOD +
                    " FROM " + Constant.TABLE_LIST.Modifier +
                    " where " + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            logger.debug("loadModifierTable, query= " +queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_ITEM_TYPE, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_SIZE, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_IMAGE, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NUMTOPPING, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_WEBSEQ, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_WEBSTATUS, Hibernate.BOOLEAN)
                    .addScalar(Constant.MODIFIER_ISFOOD, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadModLinkTable() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.MODLINK_MODIFIERID + "," +
                    Constant.MODLINK_MODGROUPNO + "," +
                    Constant.MODLINK_PRICE + "," +
                    Constant.MODLINK_FREEMODS + "," +
                    Constant.MODLINK_FREEMODPRICE + "," +
                    Constant.MODLINK_TWOFORONE + "," +
                    Constant.MODLINK_PORTIONS + "," +
                    Constant.MODLINK_SEQ +
                    " FROM " + Constant.TABLE_LIST.ModLink;
            logger.debug("loadProdLinkTable, queryStr= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODLINK_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.MODLINK_MODGROUPNO, Hibernate.STRING)
                    .addScalar(Constant.MODLINK_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MODLINK_FREEMODS, Hibernate.INTEGER)
                    .addScalar(Constant.MODLINK_FREEMODPRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MODLINK_TWOFORONE, Hibernate.BOOLEAN)
                    .addScalar(Constant.MODLINK_PORTIONS, Hibernate.BOOLEAN)
                    .addScalar(Constant.MODLINK_SEQ, Hibernate.INTEGER);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadTermTable() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.TERM_ID + "," +
                    Constant.TERM_NAME_EN + "," +
                    Constant.TERM_NAME_FR + "," +
                    Constant.TERM_DESCRIPTION_EN + "," +
                    Constant.TERM_DESCRIPTION_FR + "," +
                    Constant.TERM_IMAGE +
                    " FROM " + Constant.TABLE_LIST.Term +
                    " Order By " + Constant.TERM_ID;// sorted by 'id'

            logger.debug("loadTermTable, queryStr= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.TERM_ID, Hibernate.INTEGER)
                    .addScalar(Constant.TERM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.TERM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.TERM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.TERM_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.TERM_IMAGE, Hibernate.STRING);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadTermTableByDate(String fromDate, String toDate) throws DAOException {

        try {
//            String queryStr = "SELECT " +
//                    Constant.TERM_ID + "," +
//                    Constant.TERM_NAME_EN + "," +
//                    Constant.TERM_NAME_FR + "," +
//                    Constant.TERM_DESCRIPTION_EN + "," +
//                    Constant.TERM_DESCRIPTION_FR + "," +
//                    Constant.TERM_IMAGE +
//                    " FROM " + Constant.TABLE_LIST.Term +
//                    " Order By " + Constant.TERM_ID;// sorted by 'id'

            String queryStr = " select " +
                    "id," +
                    "name_en," +
                    "name_fr," +
                    "desc_en," +
                    "desc_fr," +
                    " image " +
                    " FROM " + Constant.TABLE_LIST.Term +
                    " WHERE  id in (" +

                    " select distinct term_id from  group_term where group_id in" +
                    " (" +
                    " select distinct  w.CategId from web_details_his d inner join web_header_his h on h.Docnumber =d.Docnumber" +
                    " inner join webMenuItem w on w.productno =d.productno" +
                    " ))";
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar("id", Hibernate.INTEGER)
                    .addScalar("name_en", Hibernate.STRING)
                    .addScalar("name_fr", Hibernate.STRING)
                    .addScalar("desc_en", Hibernate.STRING)
                    .addScalar("desc_fr", Hibernate.STRING)
                    .addScalar("image", Hibernate.STRING);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadWebCategoriesTable() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.WEBCATEGORIES_ID + "," +
                    Constant.WEBCATEGORIES_NAME_EN + "," +
                    Constant.WEBCATEGORIES_NAME_FR + "," +
                    Constant.WEBCATEGORIES_DESCRIPTION_EN + "," +
                    Constant.WEBCATEGORIES_DESCRIPTION_FR + "," +
                    Constant.WEBCATEGORIES_SEQUENCE + "," +
                    Constant.WEBCATEGORIES_PICTURE +
                    " FROM " + Constant.TABLE_LIST.WebCategories +
                    " where " + Constant.WEBCATEGORIES_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " order by " + Constant.WEBCATEGORIES_SEQUENCE;// sorted by 'CategSeq'

            logger.debug("loadWebCategoriesTable, query=" + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.WEBCATEGORIES_ID, Hibernate.INTEGER)
                    .addScalar(Constant.WEBCATEGORIES_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.WEBCATEGORIES_SEQUENCE, Hibernate.INTEGER)
                    .addScalar(Constant.WEBCATEGORIES_PICTURE, Hibernate.STRING);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List<PopularCategory> loadWebCategoriesByNameTable(String foodName,String fromDate,String toDate) throws DAOException {
        List<PopularCategory> popularCategories;

        try {
            String where1 = " where m.WebStatus=1";
            String where2 = " where 1=1 ";

            if ((fromDate != null && !fromDate.equals(""))&&(toDate != null && !toDate.equals(""))) {
             where1+=" and h.todaydate between '"+fromDate +"' and '"+toDate + "'";
            }
            if (foodName != null && !foodName.equals("")) {
                where2 += " and desc_en like  '%" + foodName + "%'";
            }

            String queryString = " select sum(quantity) orderCount, productno, GroupID ,p.webImage imageurl, p.desc_en desc_en, p.desc_fr desc_fr " +
                    " from " +
                    "(select d.productno productno, m.GroupID GroupID, d.quantity quantity," +
                    " h.todaydate, m.WebDescEN desc_en, m.WebDescFR desc_fr, m.WebImage webImage " +
                    " from web_details_his d inner join web_header_his h " +
                    "  on h.Docnumber =d.Docnumber " +
                    "  inner join MenuItem m on m.productno =d.productno" +
                      where1 +
                    " ) p " +
                     where2 +
                    "  group by productno, quantity, p.webImage, p.desc_en, p.desc_fr, p.GroupID" +
                    "   order by orderCount DESC ";

            logger.debug("loadWebCategoriesByNameTable ------------<><><><><>");
            logger.debug("query is : " + queryString);

            popularCategories = new ArrayList<>();
            connection = getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PopularCategory popularCategory = new PopularCategory();
                popularCategory.setOrderCount(resultSet.getInt("orderCount"));
                popularCategory.setDescEn(resultSet.getString("desc_en"));
                popularCategory.setDescFr(resultSet.getString("desc_fr"));
                popularCategory.setProductNo(resultSet.getString("productno"));
                popularCategory.setWebImage(resultSet.getString("imageurl"));
                popularCategory.setGroupId(resultSet.getInt("GroupID"));
                popularCategories.add(popularCategory);
            }
            logger.debug("**************************************** size is " + popularCategories.size());
        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            freeResources();
        }

        return popularCategories;
    }


    public List loadModifierPresetModLinkJoinTBL() throws DAOException {

        try {
            String queryStr = "SELECT " + Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_MODIFIERID + "," +
                    Constant.TABLE_LIST.Preset + "." + Constant.PRESET_BASEITEM + "," +
                    Constant.MODIFIER_MODGROUPNO + "," +
                    Constant.MODIFIER_IMAGE + "," +
                    Constant.MODIFIER_PRICE + "," +
                    Constant.MODIFIER_FREEMODS + "," +
                    Constant.MODIFIER_FREEMODPRICE + "," +
                    Constant.MODIFIER_TWOFORONE + "," +
                    Constant.MODIFIER_PORTION + "," +
                    Constant.MODIFIER_NUMTOPPING + "," +
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.MODIFIER_ISREDEEMABLE + "," +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Preset + "," + Constant.TABLE_LIST.ModLink +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_ITEMNUMBER +
                    " and " + Constant.TABLE_LIST.ModLink + "." + Constant.MODLINK_MODGROUPNO + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_MODGROUPNO +
                    " and " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.PRESET_BASEITEM, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_MODGROUPNO, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_IMAGE, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MODIFIER_FREEMODS, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_FREEMODPRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MODIFIER_TWOFORONE, Hibernate.BOOLEAN)
                    .addScalar(Constant.MODIFIER_PORTION, Hibernate.BOOLEAN)
                    .addScalar(Constant.MODIFIER_NUMTOPPING, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadTermGrptermMenuitemJoinTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.MENUITEM_PRODUCTNO + "," +
                    Constant.MENUITEM_GROUPID + "," +
                    Constant.MENUITEM_PRODUCTNAME + "," +
                    Constant.MENUITEM_DESCRIPTION + "," +
                    Constant.MENUITEM_DESCRIPTION2 + "," +
                    Constant.MENUITEM_SEQUENCE + "," +
                    Constant.TERM_ID + "," +
                    Constant.MENUITEM_WEBIMAGE + "," +
                    Constant.MENUITEM_NAME_EN + "," +
                    Constant.MENUITEM_NAME_FR + "," +
                    Constant.MENUITEM_PRESET + "," +
                    Constant.MENUITEM_PRICE + "," +
                    Constant.MENUITEM_FREEMODS + "," +
                    Constant.MENUITEM_FREEMODPRICE + "," +
                    Constant.MENUITEM_TWOFORONE + "," +
                    Constant.MENUITEM_PORTION + "," +
                    Constant.MENUITEM_SIZE + "," +
                    Constant.MENUITEM_DESCRIPTION_EN + "," +
                    Constant.MENUITEM_SPECIAL + "," +
                    Constant.MENUITEM_DESCRIPTION_FR + "," +
                    Constant.MENUITEM_NO_PRINT + "," +
                    Constant.MENUITEM_ISREDEEMABLE +
                    " FROM " + Constant.TABLE_LIST.Term + "," + Constant.TABLE_LIST.group_term + "," + Constant.TABLE_LIST.MenuItem +
                    " WHERE " + Constant.TABLE_LIST.group_term + "." + Constant.GROUP_TERM_TERM_ID + " = " + Constant.TABLE_LIST.Term + "." + Constant.TERM_ID +
                    " and " + Constant.TABLE_LIST.group_term + "." + Constant.GROUP_TERM_GROUP_ID + " = " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_PRODUCTNO +
                    " and " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_WEB_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By " + Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'

            logger.debug("loadTermGrptermMenuitemJoinTBL, queryStr= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MENUITEM_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_GROUPID, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRODUCTNAME, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_SEQUENCE, Hibernate.INTEGER)
                    .addScalar(Constant.TERM_ID, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_WEBIMAGE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRESET, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_FREEMODS, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_FREEMODPRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_TWOFORONE, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_PORTION, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_SIZE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_SPECIAL, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadWebcategWebmenuMenuitemJoinTBL() throws DAOException {

        try {
            String queryStr = "SELECT " + Constant.TABLE_LIST.MenuItem + "." +
                    Constant.MENUITEM_PRODUCTNO + "," +
                    Constant.MENUITEM_GROUPID + "," +
                    Constant.MENUITEM_DESCRIPTION + "," +
                    Constant.MENUITEM_DESCRIPTION2 + "," +
                    Constant.MENUITEM_SEQUENCE + "," +
                    Constant.MENUITEM_CATEGORY + "," +
                    Constant.TABLE_LIST.WebCategories + "." +
                    Constant.WEBCATEGORIES_ID + "," +
                    Constant.MENUITEM_WEBIMAGE + "," +
                    Constant.MENUITEM_NAME_EN + "," +
                    Constant.MENUITEM_NAME_FR + "," +
                    Constant.MENUITEM_DESCRIPTION_EN + "," +
                    Constant.MENUITEM_DESCRIPTION_FR + "," +
                    Constant.MENUITEM_PRICE + "," +
                    Constant.MENUITEM_FREEMODS + "," +
                    Constant.MENUITEM_FREEMODPRICE + "," +
                    Constant.MENUITEM_TWOFORONE + "," +
                    Constant.MENUITEM_PORTION + "," +
                    Constant.MENUITEM_SIZE + "," +
                    Constant.MENUITEM_PRESET + "," +
                    Constant.MENUITEM_SPECIAL + "," +
                    Constant.MENUITEM_WEB_STATUS + "," +
                    Constant.MENUITEM_NO_PRINT + "," +
                    Constant.MENUITEM_ISREDEEMABLE +
                    " FROM " + Constant.TABLE_LIST.WebMenuItem + "," + Constant.TABLE_LIST.WebCategories + "," + Constant.TABLE_LIST.MenuItem +
                    " WHERE " + Constant.TABLE_LIST.WebMenuItem + "." + Constant.WEBMWNUITEM_CATEGID + " = " + Constant.TABLE_LIST.WebCategories + "." + Constant.WEBCATEGORIES_ID +
                    " and " + Constant.TABLE_LIST.WebMenuItem + "." + Constant.WEBMWNUITEM_PRODUCTNO + " = " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_PRODUCTNO +
                    " and " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_WEB_STATUS + " = " + Constant.VISIBILITY_VALUE +
                    " order by " + Constant.WEBMWNUITEM_PRODSEQ;// sorted by 'ProdSeq'

            logger.debug("loadWebcategWebmenuMenuitemJoinTBL, queryStr= " + queryStr);
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MENUITEM_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_GROUPID, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_SEQUENCE, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_CATEGORY, Hibernate.INTEGER)
                    .addScalar(Constant.WEBCATEGORIES_ID, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_WEBIMAGE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_FREEMODS, Hibernate.INTEGER)
                    .addScalar(Constant.MENUITEM_FREEMODPRICE, Hibernate.BIG_DECIMAL)
                    .addScalar(Constant.MENUITEM_TWOFORONE, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_PORTION, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_SIZE, Hibernate.STRING)
                    .addScalar(Constant.MENUITEM_PRESET, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_SPECIAL, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_WEB_STATUS, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN)
                    .addScalar(Constant.MENUITEM_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            List l = query.list();

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadModifierContentsJoinTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +

                    Constant.TABLE_LIST.Contents + "." +
                    Constant.CONTENTS_PRODUCTNO + "," +
                    Constant.TABLE_LIST.Contents + "." +
                    Constant.CONTENTS_MODIFIERID + "," +
                    Constant.TABLE_LIST.Contents + "." +
                    Constant.CONTENTS_MODTOFIND + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_ITEM_TYPE + "," +
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_DESCRIPTION_FR + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_ISREDEEMABLE + "," +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_WEBSEQ +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Contents +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Contents + "." + Constant.CONTENTS_MODTOFIND +
                    " and " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " order by " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'
            logger.debug("loadModifierContentsJoinTBL, queryStr= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.CONTENTS_PRODUCTNO, Hibernate.STRING)
                    .addScalar(Constant.CONTENTS_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.CONTENTS_MODTOFIND, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_ITEM_TYPE, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_WEBSEQ, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadModifierPresetJoinTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.TABLE_LIST.Modifier + "." +
                    Constant.MODIFIER_MODIFIERID + "," +
                    Constant.PRESET_BASEITEM + "," +
                    Constant.PRESET_MODGROUPNO + "," +
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_FR + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.MODIFIER_ISREDEEMABLE + "," +
                    Constant.MODIFIER_WEBSEQ +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Preset +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_ITEMNUMBER +
                    " and " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " order by " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            logger.debug("loadModifierPresetJoinTBL, queryStr= " + queryStr);
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.PRESET_BASEITEM, Hibernate.STRING)
                    .addScalar(Constant.PRESET_MODGROUPNO, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_WEBSEQ, Hibernate.INTEGER)
                    .addScalar(Constant.MODIFIER_ISREDEEMABLE, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }


}

