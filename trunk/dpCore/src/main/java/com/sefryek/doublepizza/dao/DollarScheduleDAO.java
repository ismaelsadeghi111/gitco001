package com.sefryek.doublepizza.dao;

import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.exception.OverlapException;
import com.sefryek.doublepizza.model.DpDollarSchedule;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: sepideh
 * Date: 11/22/13
 * Time: 8:28 PM
 */
public class DollarScheduleDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(DollarScheduleDAO.class);
    private static final String SQL_Find_latest_dpDollar_Schedule =
            "SELECT  t.sunday , t.monday, t.tuesday , t.wednesday , t.thursday, t.friday , t.saturday  from  DP_Dollar t ";


    private void checkDateOverlap(String startDate, String endDate,String mode) throws SQLException, OverlapException {
        String sqlQuery = "select count(*) cnt from  dbo.dpdollarschadule where '" +
                startDate + "' between  CONVERT(VARCHAR, [start_dpdollar_date],111) and CONVERT(VARCHAR, [end_dpdollar_date],111) or '" +
                endDate
                + "' between  CONVERT(VARCHAR, [start_dpdollar_date],111) and CONVERT(VARCHAR, [end_dpdollar_date],111)  ";
        connection = getConnection();
        preparedStatement = connection.prepareStatement(sqlQuery);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("cnt");
            if ((count > 1 && mode.equals("edit") )|| (count > 0 && mode.equals("add") )) {
                throw new OverlapException("date has overlap");
            }
        }
    }

    public void saveDollarSchedule(DpDollarSchedule dpDollar) throws ParseException, DAOException, OverlapException {
        try {

            checkDateOverlap(DateUtil.dateToStringYYY_MM_DD(dpDollar.getStartDate()), DateUtil.dateToStringYYY_MM_DD(dpDollar.getEndDate()),"add");
            String query = "INSERT INTO   dbo.dpdollarschadule  (" +
                    "  start_dpdollar_date ," +
                    "  end_dpdollar_date  ," +
                    "  percentage)" +
                    "     VALUES (" +
                    " convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(dpDollar.getStartDate()) + "',111)," +
                    " convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(dpDollar.getEndDate()) + "',111)," +
                    dpDollar.getPercentage() + ") ";

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

    public void updateDollarSchedule(DpDollarSchedule dpDollar) throws DAOException, ParseException, OverlapException {

        try {
            checkDateOverlap(DateUtil.dateToStringYYY_MM_DD(dpDollar.getStartDate()), DateUtil.dateToStringYYY_MM_DD(dpDollar.getEndDate()),"edit");
            String query = "update dbo.dpdollarschadule  " +
                    " set start_dpdollar_date = " +
                    " convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(dpDollar.getStartDate()) + "',111), " +
                    "  end_dpdollar_date = " +
                    " convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(dpDollar.getEndDate()) + "',111)," +
                    " percentage = " +
                    dpDollar.getPercentage() +
                    "where dpdollarschadule_Id= " + dpDollar.getId();

            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public void removeDollarSchedule(int id) throws DAOException {

        try {
            String query = "delete from dbo.dpdollarschadule  " +
                    " where  dpdollarschadule_Id= " + id;
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public List<DpDollarSchedule> getDpDollarSchedule(int offset, int count) throws DAOException {

        int from = offset * count;
        List<DpDollarSchedule> list = new ArrayList<>();
        String query =
                "  select top " + count + " * from dbo.dpdollarschadule " +
                        "where dpdollarschadule_Id not in (select top " + from + " dpdollarschadule_Id from dpdollarschadule order by dpdollarschadule_Id)" +
                        "order by dpdollarschadule_Id";

        System.out.println(query);
//        String query = "SELECT * FROM  dbo.dpdollarschadule order by dpdollarschadule_Id ASC offset " + from +
//                " ROWS FETCH NEXT " + to + " ROWS ONLY  ";


        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DpDollarSchedule dpDollarSchedule = new DpDollarSchedule();
                dpDollarSchedule.setId(resultSet.getInt("dpdollarschadule_Id"));
                dpDollarSchedule.setStartDate(resultSet.getDate("start_dpdollar_date"));
                dpDollarSchedule.setEndDate(resultSet.getDate("end_dpdollar_date"));
                dpDollarSchedule.setPercentage(resultSet.getFloat("percentage"));
                list.add(dpDollarSchedule);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    public int getCountSchedule() throws DAOException {

        List<DpDollarSchedule> list = new ArrayList<>();
        String query = "SELECT count(*) cnt FROM  dbo.dpdollarschadule ";

        int count = 0;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("cnt");
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return count;
    }
    public DpDollarSchedule findDPDollarSchedualByDate(Date date) throws DAOException, ParseException {

        DpDollarSchedule dpDollarSchedule = null;
        String query = "SELECT top 1 * FROM  dbo.dpdollarschadule" +
                        " where " +
                        "convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(date) + "',111) between start_dpdollar_date and end_dpdollar_date order  by start_dpdollar_date";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dpDollarSchedule = new DpDollarSchedule();
                dpDollarSchedule.setId(resultSet.getInt("dpdollarschadule_Id"));
                dpDollarSchedule.setStartDate(resultSet.getDate("start_dpdollar_date"));
                dpDollarSchedule.setEndDate(resultSet.getDate("end_dpdollar_date"));
                dpDollarSchedule.setPercentage(resultSet.getFloat("percentage"));

            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return dpDollarSchedule;
    }

}
