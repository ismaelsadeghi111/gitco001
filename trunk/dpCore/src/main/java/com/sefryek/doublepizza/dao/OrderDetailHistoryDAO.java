package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.OrderDetail;
import com.sefryek.doublepizza.model.OrderDetailHistory;
import com.sefryek.doublepizza.model.OrderHistory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.orm.hibernate3.HibernateJdbcException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hossein Sadeghi Fard on 1/23/14.
 */
public class OrderDetailHistoryDAO extends BaseDAO {
    private List<OrderDetailHistory> orderDetailHistories;

    public List<OrderDetailHistory> getOrderDetailHistories() {
        return orderDetailHistories;
    }

    public void setOrderDetailHistories(List<OrderDetailHistory> orderDetailHistories) {
        this.orderDetailHistories = orderDetailHistories;
    }

    public List<OrderDetailHistory> getOrderDatailHistoryById(String id) throws DAOException {
        Session session = getHibernateSession();
        List<OrderDetailHistory> orderDetailHistoryList = new ArrayList<OrderDetailHistory>();

        try {
            String queryString = "select wd.*, wi.CategId, mi.GroupID, mi.WebImage, mi.WebDescEN, mi.WebDescFR, mi.Description_en, mi.Description_fr from Web_Details_His wd join WebMenuItem wi on wd.productNo= wi.ProductNo inner join MenuItem mi on wi.ProductNo = mi.ProductNo where wd.Docnumber = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrderDetailHistory orderDetailHistory = new OrderDetailHistory();

                orderDetailHistory.setDocNumber(id);
                orderDetailHistory.setTaxable(resultSet.getBoolean("Taxable"));
                orderDetailHistory.setQuantity(resultSet.getInt("Quantity"));
                orderDetailHistory.setItemKind(resultSet.getString("ItemKind"));
                orderDetailHistory.setItemName(resultSet.getString("ItemNumber"));
                orderDetailHistory.setItemName(resultSet.getString("ItemName"));
                orderDetailHistory.setItemName2(resultSet.getString("ItemName2"));
                orderDetailHistory.setItemPrice(resultSet.getBigDecimal("ItemPrice"));
                orderDetailHistory.setStore(resultSet.getString("Store"));
                orderDetailHistory.setTwoforOne(resultSet.getBoolean("TwoforOne"));
                orderDetailHistory.setFreeMods(resultSet.getBigDecimal("FreeMods"));
                orderDetailHistory.setFreeModPrice(resultSet.getBigDecimal("FreeModPrice"));
                orderDetailHistory.setOrgprice(resultSet.getBigDecimal("Orgprice"));
                orderDetailHistory.setPresetmaster(resultSet.getBoolean("Presetmaster"));
                orderDetailHistory.setLev2(resultSet.getDouble("Lev2"));
                orderDetailHistory.setLev2(resultSet.getDouble("Seq"));
                orderDetailHistory.setiPrepare(resultSet.getString("iPrepare"));
                orderDetailHistory.setProductNo(resultSet.getString("productNo"));
                orderDetailHistory.setModifierId(resultSet.getString("modifierId"));
                orderDetailHistory.setModGroupNo(resultSet.getString("modGroupNo"));
                orderDetailHistory.setBaseItem(resultSet.getString("baseItem"));
                //=============================================================
                orderDetailHistory.setCategoryId(resultSet.getInt("CategId"));
                orderDetailHistory.setGroupId(resultSet.getString("GroupID"));
                orderDetailHistory.setImageUrl(resultSet.getString("WebImage"));
                orderDetailHistory.setTitleEn(resultSet.getString("WebDescEN"));
                orderDetailHistory.setTitleFr(resultSet.getString("WebDescFR"));
                orderDetailHistory.setDescriptionEn(resultSet.getString("Description_en"));
                orderDetailHistory.setDescriptionFr(resultSet.getString("Description_fr"));

                orderDetailHistoryList.add(orderDetailHistory);
            }
            /*SQLQuery query = (SQLQuery) session.createSQLQuery(queryString)
                    .setString(Constant.WEB_HEADER_HIS_Docnumber, id);
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
           result = query.list();*/

//            Query query = session.getNamedQuery("OrderDetailHistory.findOrderDetailHistoryById").setString("docnumber",id);
            return orderDetailHistoryList;
        } catch (HibernateException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.freeResources();
        }

        return orderDetailHistories;


    }
}
