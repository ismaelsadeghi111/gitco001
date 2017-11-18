package com.sefryek.doublepizza.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import com.sefryek.doublepizza.model.Slider;
import com.sefryek.doublepizza.core.Constant;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 7, 2012
 * Time: 11:52:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SliderDAO extends BaseDAO{
    private static Logger logger = Logger.getLogger(SliderDAO.class);

    //load top 3 sliders order by sequence
    public List<Slider> findTopSlides(){
        Criteria criteria = getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createCriteria(Slider.class);

        criteria.addOrder(Order.asc("sequence"))
                .setMaxResults(Constant.Slider_MAX_RESULT);
        return criteria.list();
    }

}
