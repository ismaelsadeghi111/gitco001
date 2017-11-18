package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;

import java.util.List;

/**
 * Created by Hossein Sadeghi Fard on 1/26/14.
 */
public interface IOrderHistoryDao {
    public List<OrderHistory> getOrderHistoryByUserEmail(User user) throws DAOException;
}
