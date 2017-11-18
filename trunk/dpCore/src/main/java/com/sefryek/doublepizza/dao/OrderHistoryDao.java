package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateJdbcException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hossein Sadeghi Fard on 1/26/14.
 */

public class OrderHistoryDao extends BaseDAO implements IOrderHistoryDao {
    private static Logger logger = Logger.getLogger(OrderHistoryDao.class);
    private OrderDetailHistoryDAO orderDetailHistoryDAO;
    private List<OrderHistory> orderHistories;

    public List<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public void setOrderHistories(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public OrderDetailHistoryDAO getOrderDetailHistoryDAO() {
        return orderDetailHistoryDAO;
    }

    public void setOrderDetailHistoryDAO(OrderDetailHistoryDAO orderDetailHistoryDAO) {
        this.orderDetailHistoryDAO = orderDetailHistoryDAO;
    }

    public List<OrderHistory> getOrderHistoryByUserEmail(User user) throws DAOException {
        Session session = getHibernateSession();
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        try {

//            Query query = session.getNamedQuery("OrderHistory.findOrderHistoryByEmail").setString("email",user.getEmail());

/*            String queryString ="select * from Web_Header_His wh on dh.Docnumber = wh.Docnumber  left join web_user u on wh.Cust = u.Phone and wh.Ext = u.Ext where u.Email = :Email";
            Query query =  session.createSQLQuery(queryString)
                    .addScalar(Constant.WEB_HEADER_HIS_Docnumber, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_TAKEOUT, Hibernate.BOOLEAN)
                    .addScalar(Constant.WEB_HEADER_HIS_CUST, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_EXT, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_TODAYDATE, Hibernate.DATE)
                    .addScalar(Constant.WEB_HEADER_HIS_STORE, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_TOTAL, Hibernate.FLOAT)
                    .addScalar(Constant.WEB_HEADER_HIS_DISCOUNTDESC, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_DISCOUNT, Hibernate.FLOAT)
                    .addScalar(Constant.WEB_HEADER_HIS_DOCNUMBERORG, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_DOCTYPE, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_DAYNO, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_STOREOUT, Hibernate.STRING)
                    .addScalar(Constant.WEB_HEADER_HIS_PDATE, Hibernate.STRING)
                    .setString(Constant.USER_EMAIL, user.getEmail())
                    .setFirstResult(0)
                    .setMaxResults(50);
                    query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                     List<OrderHistory> result = (List<OrderHistory>)query.list();
                     if(result != null && result.size()>0){
                    OrderDetail orderDetail= new OrderDetail();
                    for(OrderHistory orderHistory : result){
                        orderHistory.setOrderDetailHistories(orderDetailHistoryDAO.getOrderDatailHistoryById(orderHistory.getDocnumber()));

                    }
                }

            */
//=============================================================================================================================================
//       String queryString ="select distinct wh.Docnumber, wh.Total, wh.Pdate, wh.Store, wh.Takeout from Web_Details_His dh join Web_Header_His wh on dh.Docnumber = wh.Docnumber  left join web_user u on wh.Cust = u.Phone and wh.Ext = u.Ext where u.Email = :Email";
       String queryString =" select * from Web_Header_His wh left join  contact_info info on wh.Cust =info.phone join web_user u on info.user_id =u.id where u.Email = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            logger.debug("getOrderHistoryByUserEmail for: " +user.getEmail());
            logger.debug("Query :" +queryString);
            while (resultSet.next()) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setDocnumber(resultSet.getString("Docnumber"));
                if (resultSet.getBoolean("Takeout") == false) {
                    orderHistory.setDeliveryType(OrderHistory.DeliveryType.PICKUP);
                } else {
                    orderHistory.setDeliveryType(OrderHistory.DeliveryType.DELIVERY);
                }

                orderHistory.setCust(resultSet.getString("Cust"));
                orderHistory.setExt(resultSet.getString("Ext"));
                orderHistory.setTodaydate(resultSet.getDate("Todaydate"));
                orderHistory.setStore(resultSet.getString("Store"));
                orderHistory.setTotal(resultSet.getFloat("Total"));
                orderHistory.setDiscountDesc(resultSet.getString("DiscountDesc"));
                orderHistory.setDiscount(resultSet.getFloat("Discount"));
                orderHistory.setUser(user);
                orderHistory.setOrderDetailHistories(orderDetailHistoryDAO.getOrderDatailHistoryById(orderHistory.getDocnumber()));
                orderHistoryList.add(orderHistory);
            }
//=============================================================================================================================================

            return  orderHistoryList;
        } catch (HibernateJdbcException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.freeResources();
        }
        return null;
    }

    public List<OrderHistory> getOrderHistoryByUserId(User user) throws DAOException {
        Session session = getHibernateSession();
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        try {
             String queryString =" select top 1 * from Web_Header_His wh left join  contact_info info on wh.Cust =info.phone join web_user u on info.user_id =u.id where u.id = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            logger.debug("getOrderHistoryByUserID for: " +user.getId());
            logger.debug("Query :" +queryString);
            while (resultSet.next()) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setDocnumber(resultSet.getString("Docnumber"));
                if (resultSet.getBoolean("Takeout") == false) {
                    orderHistory.setDeliveryType(OrderHistory.DeliveryType.PICKUP);
                } else {
                    orderHistory.setDeliveryType(OrderHistory.DeliveryType.DELIVERY);
                }

                orderHistory.setCust(resultSet.getString("Cust"));
                orderHistory.setExt(resultSet.getString("Ext"));
                orderHistory.setTodaydate(resultSet.getDate("Todaydate"));
                orderHistory.setStore(resultSet.getString("Store"));
                orderHistory.setTotal(resultSet.getFloat("Total"));
                orderHistory.setDiscountDesc(resultSet.getString("DiscountDesc"));
                orderHistory.setDiscount(resultSet.getFloat("Discount"));
                orderHistory.setUser(user);
                orderHistory.setOrderDetailHistories(orderDetailHistoryDAO.getOrderDatailHistoryById(orderHistory.getDocnumber()));
                orderHistoryList.add(orderHistory);
            }
//=============================================================================================================================================

            return  orderHistoryList;
        } catch (HibernateJdbcException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.freeResources();
        }
        return null;
    }
}
