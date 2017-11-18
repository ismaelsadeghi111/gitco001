package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 12, 2011
 * Time: 2:35:29 AM
 */

public class StoreDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(StoreDAO.class);

    public List loadStoreMastTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.STOREMAST_ID  + "," +
                    Constant.STOREMAST_NAME + "," +
                    Constant.STOREMAST_CITY + "," +
                    Constant.STOREMAST_ADDRESS1 + "," +
                    Constant.STOREMAST_ADDRESS2 + "," +
                    Constant.STOREMAST_PHONE + "," +
                    Constant.STOREMAST_POSTALCODE + "," +
                    Constant.STOREMAST_WEBSTATUS + "," + 
                    Constant.STOREMAST_IMAGEURL +
                    " FROM " + Constant.TABLE_LIST.Storemast.toString() +
                    " Order By " + Constant.STOREMAST_ID;// sorted by 'id'
            logger.debug("loadStoreMastTBL, queryStr= " + queryStr);
            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.STOREMAST_ID, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_NAME, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_CITY, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_ADDRESS1, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_ADDRESS2, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_PHONE, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_POSTALCODE, Hibernate.STRING)
                    .addScalar(Constant.STOREMAST_WEBSTATUS, Hibernate.BOOLEAN)
                    .addScalar(Constant.STOREMAST_IMAGEURL, Hibernate.STRING);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadStoreHoursTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.STOREHOURS_ID+ "," +
                    Constant.STOREHOURS_DAY_ID+ "," +
                    Constant.STOREHOURS_DAY_NAME_EN + "," +
                    Constant.STOREHOURS_DAY_NAME_FR + "," +
                    Constant.STOREHOURS_OPENING_HOURS + "," +
                    Constant.STOREHOURS_CLOSING_HOURS +
                    " FROM " + Constant.TABLE_LIST.StoreHours.toString() +
                    " Order By " + Constant.STOREHOURS_ID;// sorted by 'id'
            logger.debug("loadStoreHoursTBL, queryStr= " + queryStr);

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.STOREHOURS_ID, Hibernate.STRING)
                    .addScalar(Constant.STOREHOURS_DAY_ID, Hibernate.INTEGER)
                    .addScalar(Constant.STOREHOURS_DAY_NAME_EN, Hibernate.STRING)
                    .addScalar(Constant.STOREHOURS_DAY_NAME_FR, Hibernate.STRING)
                    .addScalar(Constant.STOREHOURS_OPENING_HOURS, Hibernate.STRING)
                    .addScalar(Constant.STOREHOURS_CLOSING_HOURS, Hibernate.STRING);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List loadPcodsTBL() throws DAOException {

        try {
            String queryStr = "SELECT " +
                    Constant.PCODES_POSTAL_CODE+ "," +
                    Constant.PCODES_LATITUDE+ "," +
                    Constant.PCODES_LONGITUDE+ 
                    " FROM " + Constant.TABLE_LIST.Pcodes.toString() +
                    " Order By " + Constant.PCODES_POSTAL_CODE;// sorted by 'id'

            SQLQuery query = getSession().createSQLQuery(queryStr)
                    .addScalar(Constant.PCODES_POSTAL_CODE, Hibernate.STRING)
                    .addScalar(Constant.PCODES_LATITUDE, Hibernate.DOUBLE)
                    .addScalar(Constant.PCODES_LONGITUDE, Hibernate.DOUBLE);

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return query.list();

        } catch (Exception e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }


}