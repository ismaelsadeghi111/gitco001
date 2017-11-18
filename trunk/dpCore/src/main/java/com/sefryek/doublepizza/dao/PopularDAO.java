package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Popular;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 12/13/13
 * Time: 9:26 AM
 */
public class PopularDAO extends BaseDAO {


    private static Logger logger = Logger.getLogger(PopularDAO.class);

    public List<Popular> getPopulars() throws DAOException {
        List<Popular> list = new ArrayList<>();
        try {
            String query = "select top 4 p.priority, p.productNo,p.status,p.popularItems_id,p.quantity,i.category ,p.group_id ," +
                    "i.price,i.WebDescEN,i.WebDescFR,i.webimage, i.Description_en, i.Description_fr, i.Preset, i.Twoforone , i.Special from popularitems p inner join menuitem i on  CAST(i.productno AS INT) = CAST(p.productNo AS INT ) " +
                    "where status=1 order by p.popularItems_id desc";
            logger.debug("getPopulars, query=" + query);
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Popular popular = new Popular();

                popular.setPriority(resultSet.getInt("priority"));
                popular.setPopularItemsId(resultSet.getInt("popularItems_id"));
                popular.setFoodName(resultSet.getString("WebDescEN")); //TODO frname
                popular.setFoodNameFr(resultSet.getString("WebDescFR"));
                popular.setFoodNameEn(resultSet.getString("WebDescEN"));
                popular.setMenuitemId(resultSet.getInt("productNo")); //TODO frname
                popular.setQuantity(resultSet.getInt("quantity"));
                popular.setImageUrl(resultSet.getString("webimage"));
                popular.setStatus(resultSet.getInt("status"));
                popular.setGroupId(resultSet.getInt("group_id"));
                popular.setCategoryId(resultSet.getInt("category"));
                popular.setPrice(resultSet.getDouble("price"));
                popular.setDescriptionEn(resultSet.getString("Description_en"));
                popular.setDescriptionFr(resultSet.getString("Description_fr"));
                popular.setPreset(resultSet.getBoolean("Preset"));
                popular.setTwoforone(resultSet.getBoolean("Twoforone"));
                popular.setSpecial(resultSet.getBoolean("Special"));
                list.add(popular);
            }

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } finally {
            super.freeResources();
        }
        return list;
    }

    public List<Popular> getPopularsByDate(String fromDate,String toDate) throws DAOException {
        List<Popular> list = new ArrayList<>();
        try {
            String query = "select  p.productNo,p.status,p.popularItems_id,p.quantity,p.category_id ,p.group_id ," +
                    "i.price,i.WebDescEN,i.WebDescFR,i.webimage, i.Description_en, i.Description_fr, i.Preset, i.Twoforone , i.Special from popularitems p inner join menuitem i on  CAST(i.productno AS INT) = CAST(p.productNo AS INT ) " +
                    "where status=1 " +
                    "and p.creation_date between '"+fromDate+"' AND '"+ toDate+"'" +
                    "order by p.popularItems_id desc";
            logger.debug("getPopulars, query=" + query);
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Popular popular = new Popular();
                popular.setPopularItemsId(resultSet.getInt("popularItems_id"));
                popular.setFoodName(resultSet.getString("WebDescEN")); //TODO frname
                popular.setFoodNameFr(resultSet.getString("WebDescFR"));
                popular.setFoodNameEn(resultSet.getString("WebDescEN"));
                popular.setMenuitemId(resultSet.getInt("productNo")); //TODO frname
                popular.setQuantity(resultSet.getInt("quantity"));
                popular.setImageUrl(resultSet.getString("webimage"));
                popular.setStatus(resultSet.getInt("status"));
                popular.setGroupId(resultSet.getInt("group_id"));
                popular.setCategoryId(resultSet.getInt("category_id"));
                popular.setPrice(resultSet.getDouble("price"));
                popular.setDescriptionEn(resultSet.getString("Description_en"));
                popular.setDescriptionFr(resultSet.getString("Description_fr"));
                popular.setPreset(resultSet.getBoolean("Preset"));
                popular.setTwoforone(resultSet.getBoolean("Twoforone"));
                popular.setSpecial(resultSet.getBoolean("Special"));
                list.add(popular);

            }

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } finally {
            super.freeResources();
        }
        return list;
    }

    public void savePopular(Popular popular) throws ParseException, DAOException {
        try {

            String query = "INSERT INTO   dbo.popularItems  (" +
                    "  productNo ," +
                    "  quantity  ," +
                    "  status  ," +
                    "  creation_date ," +
                    "  category_id ," +
                    "group_id," +
                    "priority" +
                    " )" +
                    "VALUES ('" +
                    popular.getMenuitemId() + "'," +
                    popular.getQuantity() + "," +
                    "1" +
                    ",GETDATE()," +
                    popular.getCategoryId() + "," +
                    popular.getGroupId() + "," +
                    popular.getPriority() +
                    ") ";
            logger.debug("Insert to popularitems, query=" + query);
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public void disablePreviousPopulars(Integer disabledPopularId) throws ParseException, DAOException {
        try {

            String query = " update  dbo.popularItems  set status = 0  where  (select count(*) from popularItems where status =1)  >= 4 and " +
                    "popularItems_id= " + disabledPopularId;
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }


}
