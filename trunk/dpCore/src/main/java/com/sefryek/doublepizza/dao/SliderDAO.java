package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Slide;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 04 09 2014
 * Time: 14:45
 */

public class SliderDAO  extends BaseDAO {
    private static Logger logger = Logger.getLogger(SliderDAO.class);
    public void save(Object obj) throws DAOException{
    try {
        super.save(obj);
    } catch (DAOException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
    }
    }

    public List<Slide> getAllSlides()  throws DAOException {
        List<Slide> result=new ArrayList<Slide>();

        try {
            connection = getConnection();
//            connection.setAutoCommit(false);
            String queryString = "select top 6 si.slide_id, si.productNo,si.status,si.group_id,si.priority,si.title_en,si.title_fr,si.image_en,si.image_fr,i.category,si.url from slide_items si inner join menuitem i on  CAST(i.productno AS INT) = CAST(si.productNo AS INT ) order by si.slide_id asc ";
            preparedStatement = connection.prepareStatement(queryString);
//            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
//            logger.debug("Query DpDollarHistory : "+queryString);
//            logger.debug("Get DpDollarHistory for : "+user.getEmail());
            result = new ArrayList<Slide>();

            while (resultSet.next()) {
                Slide slide=new Slide();
                slide.setId(resultSet.getLong("slide_id"));
                slide.setProductNo(resultSet.getString("productNo"));
                slide.setStatus(resultSet.getBoolean("status"));
                slide.setGroupId(resultSet.getString("group_id"));
                slide.setPriority(resultSet.getInt("priority"));
                slide.setWebDescEN(resultSet.getString("title_en"));
                slide.setWebDescFR(resultSet.getString("title_fr"));
                slide.setImageURLEN(resultSet.getString("image_en"));
                slide.setImageURLFR(resultSet.getString("image_fr"));
                slide.setCatId(resultSet.getInt("category"));
                slide.setExtraYrl(resultSet.getString("url"));
                result.add(slide);
            }

        }    catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } finally {
            super.freeResources();
        }

        return     result;
    }

    public void updateSlide(Slide slide) throws ParseException, DAOException {
        int status=1;
        if(slide.getStatus()!=null){
        if(slide.getStatus().equals(Boolean.FALSE)){
            status=0;
        }
        }
        try {
        String  query="";
        if(slide.getStatus()==null){
         query = "UPDATE slide_items SET group_id="+slide.getGroupId()+", title_en='"+slide.getWebDescEN()+"', category_id="+slide.getCatId()+ ", productNo="+slide.getProductNo()+",url='"+slide.getExtraYrl()+"' WHERE  slide_id="+slide.getId();
        }  else {         query = "UPDATE slide_items SET group_id="+slide.getGroupId()+", title_en='"+slide.getWebDescEN()+"', category_id="+slide.getCatId()+ ", productNo="+slide.getProductNo()+",status="+status+", url='"+slide.getExtraYrl()+"' WHERE  slide_id="+slide.getId();}
        connection = getConnection();
        preparedStatement = connection.prepareStatement(query);
        logger.debug("query update slide_items: "+query);
        preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

}

