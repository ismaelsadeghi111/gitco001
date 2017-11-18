package com.sefryek.doublepizza.dao;

import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.exception.OverlapException;
import com.sefryek.doublepizza.model.DpDollarHistory;
import com.sefryek.doublepizza.model.Dpdollarday;
import com.sefryek.doublepizza.model.User;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 11/20/13
 * Time: 1:20 PM
 */
public class DollarDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(DollarDAO.class);
    private static final String SQL_Find_latest_dpDollar_day ="SELECT TOP 1 sunday_percentage , monday_percentage, tuesday_percentage , wednesday_percentage , thursday_percentage, friday_percentage , saturday_percentage, status, creation_date  from   dbo.dpdollardays order by dpdollardays_id DESC";



    public void saveDollarDays(Dpdollarday dpDollar) throws DAOException {
        try {
            String query = "INSERT INTO   dbo.dpdollardays(" +
                    "             sunday_percentage" +
                    "         ,  monday_percentage" +
                    "           ,  tuesday_percentage" +
                    "           ,  wednesday_percentage" +
                    "           ,  thursday_percentage" +
                    "           ,  friday_percentage" +
                    "           ,  saturday_percentage" +
                    "           ,  status)" +
                    "     VALUES (" +
                    dpDollar.getSunday() + "," +
                    dpDollar.getMonday() + "," +
                    dpDollar.getTuesday() + "," +
                    dpDollar.getWednesday() + "," +
                    dpDollar.getThursday() + "," +
                    dpDollar.getFriday() + "," +
                    dpDollar.getSaturday() + "," +
                    dpDollar.getStatus() + ") ";

            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
//
//        } catch (ParseException e) {
//            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
//            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());

        } finally {
            super.freeResources();
        }
    }


//    public Dpdollarday getDpDollar() throws DAOException {
//
//        Dpdollarday dpDollar = null;
//        try {
//            String query = "SELECT Top 1 * FROM  dbo.dpdollardays order by dpdollardays_id DESC";
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                dpDollar = new Dpdollarday();
//                dpDollar.setFriday(resultSet.getFloat("friday_percentage"));
//                dpDollar.setMonday(resultSet.getFloat("monday_percentage"));
//                dpDollar.setSaturday(resultSet.getFloat("saturday_percentage"));
//                dpDollar.setTuesday(resultSet.getFloat("tuesday_percentage"));
//                dpDollar.setThursday(resultSet.getFloat("thursday_percentage"));
//                dpDollar.setWednesday(resultSet.getFloat("wednesday_percentage"));
//                dpDollar.setSunday(resultSet.getFloat("sunday_percentage"));
//                dpDollar.setStatus(resultSet.getByte("status"));
//                dpDollar.setCreation_date(resultSet.getDate("creation_date"));
//            }
//        } catch (SQLException e) {
//            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
//            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
//        } finally {
//            super.freeResources();
//        }
//
//        return dpDollar;
//    }
    public Dpdollarday getDpDollar() throws DAOException {
        logger.debug("Start getDpDollar method ");

        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(SQL_Find_latest_dpDollar_day);
        Object obj = query.uniqueResult();
        Dpdollarday dpDollar = new Dpdollarday();
        if (obj != null) {
            Object[] arrayObj = (Object[]) obj;
            dpDollar.setSunday(((Double)(arrayObj[0])).floatValue());
            dpDollar.setMonday(((Double)(arrayObj[1])).floatValue());
            dpDollar.setTuesday(((Double)(arrayObj[2])).floatValue());
            dpDollar.setWednesday(((Double)(arrayObj[3])).floatValue());
            dpDollar.setThursday(((Double)(arrayObj[4])).floatValue());
            dpDollar.setFriday(((Double)(arrayObj[5])).floatValue());
            dpDollar.setStatus(((Number)arrayObj[6]).byteValue());
        }
        logger.debug("Return dpDollar getSunday: "+dpDollar.getSunday());
        logger.debug("Return dpDollar getMonday: "+dpDollar.getMonday());
        logger.debug("Return dpDollar getTuesday: "+dpDollar.getTuesday());
        logger.debug("Return dpDollar getWednesday: "+dpDollar.getWednesday());
        logger.debug("Return dpDollar getThursday: "+dpDollar.getThursday());
        logger.debug("Return dpDollar getFriday: "+dpDollar.getFriday());
        logger.debug("Return dpDollar Status: "+dpDollar.getStatus());
        return dpDollar;
    }

    public Float getMinval() throws DAOException {

        Float minval = Float.valueOf(0);
        try {
            String query = "SELECT dpdollarminvalue FROM dpsetting";
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            resultSet.next();
            minval = resultSet.getFloat("dpdollarminvalue");

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.becouse dpdollar days has no records\n cause: " + e.getMessage());
//            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }

        return minval;
    }

    public void doSaveMinvalue(Float minValue) throws DAOException {

        try {
            String query = "update dbo.dpsetting set dpdollarminvalue=" + minValue + "";
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
       public static void main(String[] args) {

        final String username = "elahe.ghasemi@gmail.com";
        final String password = "ham333da";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("elahe.ghasemi@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public List getAllDpDollarHistoryByUserEmail(User user) throws SQLException {
        List<DpDollarHistory> result = null;
        try {
            connection = getConnection();
//            connection.setAutoCommit(false);
            String queryString = "select * from dpdollar_history dh where dh.user_email = ";
            queryString += " '"+user.getEmail()+"' ";
            queryString += " order by dh.user_id, dh.creation_date ";
            preparedStatement = connection.prepareStatement(queryString);
//            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            result = new ArrayList<DpDollarHistory>();
            while (resultSet.next()) {
                DpDollarHistory dpDollarHistory = new DpDollarHistory();
                dpDollarHistory.setId(resultSet.getLong("dpdollar_history_id"));
                dpDollarHistory.setAmount(resultSet.getDouble("amount"));
                dpDollarHistory.setCreationDate(resultSet.getDate("creation_date"));
//                dpDollarHistory.setEmail(resultSet.getString("user_email"));
                dpDollarHistory.setOrderId(resultSet.getString("order_id"));
             /*   if (resultSet.getInt("status") == 0) {
                    dpDollarHistory.setStatus(DpDollarHistory.Status.EARNED);
                } else if (resultSet.getInt("status") == 1) {

                        dpDollarHistory.setStatus(DpDollarHistory.Status.SPENT);
                    }*/

                    result.add(dpDollarHistory);
                }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return result;
    }
 public List getAllDpDollarHistoryByUserId(int userId) throws SQLException {
        List<DpDollarHistory> result = null;
        try {
            connection = getConnection();
//            connection.setAutoCommit(false);
            String queryString = "select * from dpdollar_history dh where dh.user_id = ";
            queryString += " '"+userId+"' ";
            queryString += " order by dh.user_id, dh.creation_date ";
            preparedStatement = connection.prepareStatement(queryString);
//            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
//            logger.debug("Query DpDollarHistory : "+queryString);
//            logger.debug("Get DpDollarHistory for : "+user.getEmail());
            result = new ArrayList<DpDollarHistory>();
            while (resultSet.next()) {
                DpDollarHistory dpDollarHistory = new DpDollarHistory();
                dpDollarHistory.setId(resultSet.getLong("dpdollar_history_id"));
                dpDollarHistory.setOrderId(resultSet.getString("order_id"));
                if (resultSet.getString("status").equalsIgnoreCase(DpDollarHistory.Status.EARNED.toString())||resultSet.getString("status").equalsIgnoreCase("Credit")) {
                    dpDollarHistory.setStatus(DpDollarHistory.Status.EARNED.toString());
                } else if (resultSet.getString("status").equalsIgnoreCase(DpDollarHistory.Status.SPENT.toString())) {
                    dpDollarHistory.setStatus(DpDollarHistory.Status.SPENT.toString());
                }
                dpDollarHistory.setAmount(resultSet.getDouble("amount"));
                dpDollarHistory.setBalance(resultSet.getDouble("balance"));
                dpDollarHistory.setPercentage(resultSet.getDouble("percentage"));
                dpDollarHistory.setCreationDate(resultSet.getDate("creation_date"));

                    result.add(dpDollarHistory);
                }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return result;
    }

    public void saveDollarHistory(DpDollarHistory dpDollarHistory) throws DAOException, OverlapException, java.text.ParseException, SQLException {
        try {

            String query = "INSERT INTO  dpdollarhistory  (" +
                    "  order_id ," +
                    "  status  ," +
                    "  amount  ," +
                    "  user_email  ," +
                    "  creation_date)" +
                    "  VALUES (" +
                    "'" + dpDollarHistory.getOrderId() + "'"+"," +
                          dpDollarHistory.getStatus() + "," +
                          dpDollarHistory.getAmount() + "," +
//                    "'" + dpDollarHistory.getEmail() + "'"+"," +
                    " convert(datetime,'" + DateUtil.dateToStringYYY_MM_DD(dpDollarHistory.getCreationDate()) + "',111)) ";

            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            connection.rollback();
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public void save(Object obj) throws DAOException {
        super.save(obj);
    }

    public void saveHistory(Object obj) throws DAOException {
        org.hibernate.classic.Session session = getHibernateSession();
        session.setFlushMode(FlushMode.AUTO);
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            tx.begin();
            super.save(obj);
            tx.commit();
            session.flush();
        } catch (Exception e){
            logger.debug("error on saving"+ obj.toString() +" \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            tx.rollback();

        }

    }

    public List findDpDollarsHistoryByUserId(int userId){
  /*      Criteria criteria = getHibernateSession().createCriteria(DpDollarHistory.class);
        criteria.createCriteria("user").add(Restrictions.eq("id",userId));
        return criteria.list();*/
//        String query = "select d from DpDollarHistory d join User u on d.user.id = u.id where u.id = ? order by d.creationDate DESC ";
        String query = "select d from DpDollarHistory d where  d.user.id = ? order by d.creationDate ASC ";
        return getHibernateTemplate().find(query,userId);
    }

    public DpDollarHistory findLastDpDollarsHistoryByUserId(int userId){
        String query = "select d from DpDollarHistory d where  d.user.id = ? order by d.creationDate DESC ";
        return (DpDollarHistory) getHibernateTemplate().find(query,userId).get(0);
    }

    public void reattachToSession(Object obj){
        super.reattachToSession(obj);
    }


    public void updateDpDollarHistory(DpDollarHistory  dpDollarHistory) throws DAOException {
        try {
            String query = "update  dpdollar_history  set balance=" + dpDollarHistory.getBalance() + "where dpdollar_history_id="+dpDollarHistory.getId();
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

}
