package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.model.Order;
import com.sefryek.doublepizza.dao.exception.DAOException;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Mar 27, 2012
 * Time: 6:18:55 PM
 */
public interface IOrderService {
    public static String BEAN_NAME = "orderService";

    public void save(Order order) throws DAOException;

    public Integer  getLastDocNumber();
}
