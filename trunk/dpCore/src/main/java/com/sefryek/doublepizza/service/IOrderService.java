package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.ContactInfo;
import com.sefryek.doublepizza.model.Order;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Mar 27, 2012
 * Time: 6:18:55 PM
 */
public interface IOrderService {
    public static String BEAN_NAME = "orderService";
    public List<OrderHistory> getOrderHistoryByUserEmail(User user) throws DAOException ;
    public List<OrderHistory> getOrderHistoryByUserId(User user) throws DAOException ;
    public void save(Order order,ContactInfo contactInfo,String pickupPhone, String pickupExt) throws DAOException;

    public Integer  getLastDocNumber();
}
