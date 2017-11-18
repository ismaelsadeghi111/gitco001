package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.CateringContactInfo;
import com.sefryek.doublepizza.model.CateringOrder;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateJdbcException;

/**
 * Created by Hossein Sadeghi Fard on 1/26/14.
 */
public class CateringContactInfoDao extends BaseDAO{

    public void save(CateringContactInfo cateringContactInfo) throws DAOException {
      super.save(cateringContactInfo);

    }

}
