package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.core.Constant;

import java.util.*;

import org.hibernate.SQLQuery;
import org.hibernate.Hibernate;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 *
 *
 *
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
                    " FROM " + Constant.TABLE_LIST.Term.toString()+
                    " Order By "+Constant.TERM_ID;// sorted by 'id'

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
                    Constant.TOPPING_CATEGORY_TITLE_EN+ "," +
                    Constant.TOPPING_CATEGORY_TITLE_FR+ "," +
                    Constant.TOPPING_CATEGORY_URL+ "," +
                    Constant.TOPPING_CATEGORY_IMAGE_NAME + "," +
                    Constant.TOPPING_CATEGORY_WEB_SEQUENCE +
                    " FROM " + Constant.TABLE_LIST.Topping_category.toString()+
                    " Order By "+Constant.TOPPING_CATEGORY_ID;// sorted by 'id'

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
                    Constant.MODIFIER_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.Modifier.toString()+
                    " where " + Constant.MODIFIER_WEBSTATUS+" = "+Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING);

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
                    " FROM " + Constant.TABLE_LIST.WebCategories.toString()+
                    " where " + Constant.WEBCATEGORIES_STATUS+" = "+Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.WEBCATEGORIES_SEQUENCE;// sorted by 'CategSeq'

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
                    " FROM " + Constant.TABLE_LIST.MenuItem.toString()+
                    " where " + Constant.MENUITEM_WEB_STATUS+" = "+Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'

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
                    Constant.MENUITEM_NO_PRINT +
                    " FROM " + Constant.TABLE_LIST.MenuItem+
                    " where " + Constant.MENUITEM_WEB_STATUS +" = "+Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'

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
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN);

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
                    Constant.PRODLINK_PRICE +"," +
                    Constant.PRODLINK_FORCED +
                    " FROM " + Constant.TABLE_LIST.ProdLink+
                    " Order By "+Constant.PRODLINK_SEQ;// sorted by 'Seq'

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
                    Constant.MODIFIER_ISFOOD +
                    " FROM " + Constant.TABLE_LIST.Modifier +
                    " where " + Constant.MODIFIER_WEBSTATUS + " = " + Constant.VISIBILITY_VALUE +
                    " Order By "+Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

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
                    .addScalar(Constant.MODIFIER_ISFOOD, Hibernate.INTEGER);

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
                    " Order By "+Constant.TERM_ID;// sorted by 'id'

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
                    " where " + Constant.WEBCATEGORIES_STATUS+ " = " + Constant.VISIBILITY_VALUE +
                    " order by " + Constant.WEBCATEGORIES_SEQUENCE;// sorted by 'CategSeq'

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
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_FR +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Preset + "," + Constant.TABLE_LIST.ModLink +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_ITEMNUMBER +
                    " and " + Constant.TABLE_LIST.ModLink + "." + Constant.MODLINK_MODGROUPNO + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_MODGROUPNO+
                    " and " + Constant.TABLE_LIST.Modifier+ "." + Constant.MODIFIER_WEBSTATUS+ " = " + Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

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
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING);

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
                    Constant.MENUITEM_NO_PRINT +
                    " FROM " + Constant.TABLE_LIST.Term + "," + Constant.TABLE_LIST.group_term + "," + Constant.TABLE_LIST.MenuItem +
                    " WHERE " + Constant.TABLE_LIST.group_term + "." + Constant.GROUP_TERM_TERM_ID + " = " + Constant.TABLE_LIST.Term + "." + Constant.TERM_ID +
                    " and " + Constant.TABLE_LIST.group_term + "." + Constant.GROUP_TERM_GROUP_ID + " = " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_PRODUCTNO+
                    " and " + Constant.TABLE_LIST.MenuItem+ "." + Constant.MENUITEM_WEB_STATUS+ " = " + Constant.VISIBILITY_VALUE+
                    " Order By "+Constant.MENUITEM_SEQUENCE;// sorted by 'Seq'

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
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN);

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
                    Constant.MENUITEM_NO_PRINT +
                    " FROM " + Constant.TABLE_LIST.WebMenuItem + "," + Constant.TABLE_LIST.WebCategories + "," + Constant.TABLE_LIST.MenuItem +
                    " WHERE " + Constant.TABLE_LIST.WebMenuItem + "." + Constant.WEBMWNUITEM_CATEGID + " = " + Constant.TABLE_LIST.WebCategories + "." + Constant.WEBCATEGORIES_ID +
                    " and " + Constant.TABLE_LIST.WebMenuItem + "." + Constant.WEBMWNUITEM_PRODUCTNO + " = " + Constant.TABLE_LIST.MenuItem + "." + Constant.MENUITEM_PRODUCTNO +
                    " and " + Constant.TABLE_LIST.MenuItem+ "." + Constant.MENUITEM_WEB_STATUS+ " = " + Constant.VISIBILITY_VALUE+
                    " order by " + Constant.WEBMWNUITEM_PRODSEQ;// sorted by 'ProdSeq'

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
                    .addScalar(Constant.MENUITEM_NO_PRINT, Hibernate.BOOLEAN);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            List l= query.list();

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
                    Constant.MODIFIER_WEBSEQ +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Contents +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Contents + "." + Constant.CONTENTS_MODTOFIND+
                    " and " + Constant.TABLE_LIST.Modifier+ "." + Constant.MODIFIER_WEBSTATUS+ " = " + Constant.VISIBILITY_VALUE+
                    " order by " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

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
                    .addScalar(Constant.MODIFIER_WEBSEQ, Hibernate.INTEGER);

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
                    Constant.MODIFIER_NAME_EN + "," +
                    Constant.MODIFIER_NAME_FR + "," +
                    Constant.MODIFIER_DESCRIPTION_EN + "," +
                    Constant.MODIFIER_DESCRIPTION_FR + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION + "," +
                    Constant.MODIFIER_FULL_DESCRIPTION2 + "," +
                    Constant.MODIFIER_WEBSEQ +
                    " FROM " + Constant.TABLE_LIST.Modifier + "," + Constant.TABLE_LIST.Preset +
                    " WHERE " + Constant.TABLE_LIST.Modifier + "." + Constant.MODIFIER_MODIFIERID + " = " + Constant.TABLE_LIST.Preset + "." + Constant.PRESET_ITEMNUMBER+
                    " and " + Constant.TABLE_LIST.Modifier+ "." + Constant.MODIFIER_WEBSTATUS+ " = " + Constant.VISIBILITY_VALUE+
                    " order by " + Constant.MODIFIER_WEBSEQ;// sorted by 'webSeq'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.MODIFIER_MODIFIERID, Hibernate.STRING)
                    .addScalar(Constant.PRESET_BASEITEM, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_EN, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_DESCRIPTION_FR, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_FULL_DESCRIPTION2, Hibernate.STRING)
                    .addScalar(Constant.MODIFIER_WEBSEQ, Hibernate.INTEGER);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }


}

