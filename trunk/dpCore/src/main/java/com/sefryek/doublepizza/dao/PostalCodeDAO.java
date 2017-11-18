package com.sefryek.doublepizza.dao;

import com.sefryek.common.Point;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.PostalCode;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 29, 2012
 * Time: 9:24:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostalCodeDAO extends BaseDAO {
    private static Logger logger = Logger.getLogger(PostalCodeDAO.class);
    private static final String SQL_Find_StreetName_AND_City =
            "SELECT TOP 1 STREET_NAME, CITY FROM Pcodes " +
            "WHERE (POSTAL_CODE = :postalCode AND " +
            "STREET_FROM_NO <= :streetNo AND STREET_TO_NO >= :streetNo) ";

    private static final String SQL_Find_Location =
            "SELECT TOP 1 LATITUDE, LONGITUDE " +
                    "FROM Pcodes " +
                    "WHERE POSTAL_CODE = :postalCode";

    private static final String SQL_City_List =
            "SELECT distinct CITY , " +
                    "(SELECT TOP 1 b.LATITUDE FROM Pcodes AS b WHERE b.CITY = A.CITY ) AS LATITUDE, " +
                    "(SELECT TOP 1 b.LONGITUDE FROM Pcodes AS b WHERE b.CITY = A.CITY ) AS LONGITUDE " +
                    "FROM Pcodes AS A " +
                    "Where Store <> :store " +
                    "ORDER BY CITY";



    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo){
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(SQL_Find_StreetName_AND_City)
                .addScalar("STREET_NAME", Hibernate.STRING)
                .addScalar("CITY", Hibernate.STRING)
                .setString("streetNo", streetNo)
                .setString("postalCode", postalCode);

        Object obj = query.uniqueResult();
        List<String> cityAndStreet = new ArrayList<String>();
        String streetName = null;
        String city = null;
        if (obj != null){
            Object[] arrayObj = (Object[])obj;
            streetName = (String)arrayObj[0];
            city = (String)arrayObj[1];
            cityAndStreet.add(streetName);
            cityAndStreet.add(city);
        }

        return cityAndStreet;
    }

    public Point findLocationByPostalCode(String postalCode){
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(SQL_Find_Location)
                .addScalar(Constant.POSTAL_CODE_LATITUDE, Hibernate.DOUBLE)
                .addScalar(Constant.POSTAL_CODE_LONGITUDE, Hibernate.DOUBLE)
                .setString("postalCode", postalCode);

        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Double>> list = (List<Map<String, Double>>)query.list();
        
        if (list.size() == 0)
            return null;

        Point result = new Point(list.get(0).get(Constant.POSTAL_CODE_LATITUDE),
                list.get(0).get(Constant.POSTAL_CODE_LONGITUDE));
        return result;

    }

    public List<PostalCode> getCitiesListWithPosition(){
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(SQL_City_List)
                .addScalar(Constant.POSTAL_CODE_LATITUDE, Hibernate.DOUBLE)
                .addScalar(Constant.POSTAL_CODE_LONGITUDE, Hibernate.DOUBLE)
                .addScalar(Constant.POSTAL_CODE_CITY, Hibernate.STRING)
                .setString("store", Constant.NO_DELIVERY_CODE);

        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> list = query.list();
        List<PostalCode> result = new ArrayList<PostalCode>();
        for (Map<String, Object> item : list){
            result.add(new PostalCode(null,
                    (String)item.get(Constant.POSTAL_CODE_CITY),
                    (Double)item.get(Constant.POSTAL_CODE_LATITUDE),
                    (Double)item.get(Constant.POSTAL_CODE_LONGITUDE) ));
        }


        return result;
    }    
}