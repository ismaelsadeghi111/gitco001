package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.service.IOrderService;
import com.sefryek.doublepizza.model.Order;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.OrderDAO;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Mar 27, 2012
 * Time: 6:21:38 PM
 */
public class OrderServiceImpl implements IOrderService {
    private OrderDAO orderDAO;

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void save(Order order) throws DAOException {
        orderDAO.save(order);
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
