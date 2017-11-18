package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.Suggestion;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 6, 2012
 * Time: 9:57:35 AM
 */
public class SuggestionDAO extends BaseDAO{
    private static Logger logger = Logger.getLogger(SuggestionDAO.class);
    private static final String Suggestion_Select_Clause =
            "Select Productno, modifierID, Price From suggestions";

    public List<Suggestion> findAll(){
        List<Suggestion> suggestionList = new ArrayList<Suggestion>();

        SQLQuery sqlQuery = getSessionFactory().
                getCurrentSession().
                createSQLQuery(Suggestion_Select_Clause).
                addScalar(Constant.SUGGESTION_PRODUCTNO, Hibernate.STRING).
                addScalar(Constant.SUGGESTION_MODIFIERID, Hibernate.STRING).
                addScalar(Constant.SUGGESTION_PRICE, Hibernate.BIG_DECIMAL);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = sqlQuery.list();        
        for (Map<String, Object> item : list){
            suggestionList.add(new Suggestion(
                    (String)item.get(Constant.SUGGESTION_PRODUCTNO),
                    (String)item.get(Constant.SUGGESTION_MODIFIERID),
                    (BigDecimal)item.get(Constant.SUGGESTION_PRICE)));
        }

        return suggestionList;
    }
}
