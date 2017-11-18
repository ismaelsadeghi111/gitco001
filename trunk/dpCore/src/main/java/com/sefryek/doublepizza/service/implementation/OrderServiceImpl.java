package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.OrderDAO;
import com.sefryek.doublepizza.dao.OrderHistoryDao;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.ContactInfo;
import com.sefryek.doublepizza.model.Order;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.IOrderService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Mar 27, 2012
 * Time: 6:21:38 PM
 */
public class OrderServiceImpl implements IOrderService {
    private OrderDAO orderDAO;
    private OrderHistoryDao orderHistoryDao;

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public OrderHistoryDao getOrderHistoryDao() {
        return orderHistoryDao;
    }

    public void setOrderHistoryDao(OrderHistoryDao orderHistoryDao) {
        this.orderHistoryDao = orderHistoryDao;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    public List<OrderHistory> getOrderHistoryByUserEmail(User user) throws DAOException {
        return orderHistoryDao.getOrderHistoryByUserEmail(user);
    }

    public List<OrderHistory> getOrderHistoryByUserId(User user) throws DAOException {
        return orderHistoryDao.getOrderHistoryByUserId(user);
    }

    public void save(Order order, ContactInfo contactInfo, String pickupPhone, String pickupExt) throws DAOException {
        orderDAO.save(order, contactInfo, pickupPhone, pickupExt);
    }

    public Integer  getLastDocNumber() {
        Integer docNumber = orderDAO.getLastDocNumber();
        if (docNumber == null){
            docNumber = 0;
            orderDAO.createLastDocNumber(docNumber);
        }
        return docNumber;
    }
}
