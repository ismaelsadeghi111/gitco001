package com.sefryek.doublepizza.dao;

import com.mysql.jdbc.Statement;
import com.sefryek.common.LogMessages;
import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.enums.CampaignType;
import com.sefryek.doublepizza.enums.EmailStatus;
import com.sefryek.doublepizza.model.BirthdayCampaign;
import com.sefryek.doublepizza.model.PostalCampaign;
import com.sefryek.doublepizza.model.StandardCampaign;
import com.sefryek.doublepizza.model.User;
import org.apache.log4j.Logger;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/13/13
 * Time: 10:59 AM
 */
public class CampaignDAO extends BaseDAO {
    private static Logger logger = Logger.getLogger(DollarScheduleDAO.class);
    private static final String GET_IDENTITY = "SELECT SCOPE_IDENTITY()";



    public Integer saveChangedSubscribeUserCampaign(String userId, String reason) throws DAOException {
        int resultInt = 0;

        try {
            connection = getConnection();

            String query = "UPDATE web_user SET Subscribed = 0 WHERE  Id = " + userId;

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet r1 = preparedStatement.getGeneratedKeys();
            if (r1.next()) {
                resultInt = r1.getInt(1);
            }
            if(resultInt >= 0){
                if(!reason.equals("")){
                    String unsubscribeQuery = "INSERT INTO unsubscribe  (User_id, Reason) VALUES ('"+userId+"' , '"+reason+"')";
                    preparedStatement = connection.prepareStatement(unsubscribeQuery, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.executeUpdate();
                    ResultSet r2 = preparedStatement.getGeneratedKeys();
                    if (r2.next()) {
                        resultInt = r2.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            logger.debug("UPDATE web_user SET Subscribed = 0 WHERE  Id = " + userId + " -> has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
        return resultInt;
    }

    //standard

    /**
     * Insert standard campaign and email information
     *
     * @param standardCampaign
     * @throws ParseException
     * @throws DAOException
     */
    public Integer saveStandardCampaign(StandardCampaign standardCampaign) throws ParseException, DAOException {
        int standardId = 0;
        String query = "";
        try {
            int ordered = 0;
            //if (standardCampaign.isOrdered()) ordered = 1;
            query = "INSERT INTO   dbo.standardcampaign  (" +
                    "  campaign_title_en ," +
                    "  campaign_title_fr  ," +
                    "  food_id  ," +
                    "  ordered ," +
                    "  order_days ," +
                    "  order_numbers ," +
                    "  status ," +
                    "  image_path ," +
                    "  image_path_en ," +
                    "  order_type ," +
                    "  creation_date ," +
                    "  campaign_note ," +
                    "  custom_order_operation ," +
                    "  campaign_date," +
                    "  subjectEn," +
                    "  subjectFr," +
                    "  items_html_en," +
                    "  items_html_fr)" +
                    " VALUES ( " +
                    "'" + standardCampaign.getCampaign_title_en() + "' ," +
                    "'" + standardCampaign.getCampaign_title_fr() + "' , '" +
                    standardCampaign.getMenu_id() + "' ," +
                    ordered + " ," +
                    "'" + standardCampaign.getOrderDays() + "' ," +
                    "'" + standardCampaign.getOrderNumbers() + "' ," +
                    "'" + standardCampaign.getStatus() + "' ," +
                    "'" + standardCampaign.getImageUrl() + "' ," +
                    "'" + standardCampaign.getImageUrlEn() + "' ," +
                    "'" + standardCampaign.getOrderType() + "' , " +
                    " GETDATE(), "+
                    "'" + standardCampaign.getNote() + "' , " +
                    "'" + standardCampaign.getOrderSign() + "' , " +
                    "'" + standardCampaign.getCampaign_date() + "' , " +
                    "'" + standardCampaign.getSubjectEn() + "' , " +
                    "'" + standardCampaign.getSubjectFr() + "' , " +
                    "'" + standardCampaign.getItemHtmlEn() + "' , " +
                    "'" + standardCampaign.getItemHtmlFr()+ "')";

            //query += " convert(datetime,'" + DateUtil.dateToString(standardCampaign.getCampaign_date()) + "',5))";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet r = preparedStatement.getGeneratedKeys();
            if (r.next()) {
                standardId = r.getInt(1);
            }
            logger.debug("Debug: St Campaign Query: "+query);
        } catch (SQLException e) {
            logger.debug("Debug: St Campaign Query: "+query);
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
        return standardId;
    }

    public Integer saveChangedStandardCampaign(StandardCampaign standardCampaign) throws ParseException, DAOException {
        int standardId = 0;

        try {
            int ordered = 0;
            if (standardCampaign.isOrdered()) ordered = 1;
            String query = "UPDATE dbo.standardcampaign SET" +
                    //" campaign_title_en =  " + standardCampaign.getCampaign_title_en()+
                    //" ,campaign_title_fr = " + standardCampaign.getCampaign_title_fr()+
                    //" ,food_id = " + standardCampaign.getMenu_id()+
                    //" ,order_days = " + standardCampaign.getOrderDays()+
                    //" ,order_numbers = " + standardCampaign.getOrderNumbers()+
                    " status = '" + standardCampaign.getStatus()+"'"+
                    //" ,campaign_note = " + standardCampaign.getNote()+
                    //" ,campaign_date = " + standardCampaign.getCampaign_date()+
                    " WHERE standardcampaign_id = " + standardCampaign.getStandardcampaign_id();

            connection = getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet r = preparedStatement.getGeneratedKeys();
            if (r.next()) {
                standardId = r.getInt(1);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
        return standardId;
    }

    /**
     * save Campaign for user with id and standard campaign id
     *
     * @throws DAOException
     */
    public void saveStandardCampaignForUser(String campaignDate, Integer postalCampaign, List<User> users) throws DAOException {
        try {
            for (User user : users) {
                String query = "INSERT INTO dbo.outboundmail_standardcampaign (" +
                        "  standardcampaign_id ," +
                        "  creation_date  ," +
                        "  campaign_date  ," +
                        "  email_address  ," +
                        "  user_name  ," +
                        "  template_filename  ," +
                        "  status )" +
                        "     VALUES (" +
                        postalCampaign + " ," +
                        "convert(datetime,'" + DateUtil.dateToString(new Date()) + "',5)" + "," +
                        "convert(datetime,'" + campaignDate + "',5)" + ",'"
                        + user.getEmail() + "' ,'" + user.getFirstName() + "'," + " 'standardCampaign.flt' ,'" +
                        EmailStatus.PENDING.name() + "')";
                connection = getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            super.freeResources();
        }
    }


    public  Map<String, String> getMenuItemCampaign() {
        logger.info("call method : getMenuItemCampaign");
        List<User> users = new ArrayList<User>();
        Map<String, String> menuList = new HashMap<>();
        try {
            //String findMenuItemQuery = "SELECT ProductNo, ProductName, WebDescEN FROM MenuItem where WebStatus = 1 Order By Seq ";
            String findMenuItemQuery = "select sum(quantity) orderCount, productno, GroupID ,p.webImage imageurl, p.desc_en desc_en, p.desc_fr desc_fr  from (select d.productno productno, m.GroupID GroupID, d.quantity quantity, h.todaydate, m.WebDescEN desc_en, m.WebDescFR desc_fr, m.WebImage webImage  from web_details_his d inner join web_header_his h   on h.Docnumber =d.Docnumber   inner join MenuItem m on m.productno =d.productno where m.WebStatus=1 ) p  WHERE p.productno != '0033' group by productno, quantity, p.webImage, p.desc_en, p.desc_fr, p.GroupID  order by orderCount DESC";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findMenuItemQuery);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                String prdNoStr = String.valueOf(Integer.parseInt(results.getString("productno")));
                menuList.put(results.getString("productno") + "/" + results.getString("GroupID") , results.getString("desc_en") + " - [" + results.getString("orderCount") + "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return menuList;
    }
    /**
     * retrieve users for standard campaign
     *
     * @param standardCampaign
     * @return
     */
    public List<User> getUsersForStandardCampaign(StandardCampaign standardCampaign) {

        List<User> users = new ArrayList<User>();
        try {
            String findUserQuery = "select distinct u.Id , u.FirstName , u.lastName , u.Email , u.Phone from dbo.Web_Header_His w , dbo.web_user u where u.Phone = w.Cust ";
            String conditionPart = "";
            Calendar calendar;
            String startDate;
            String endDate = " convert(datetime,'" + DateUtil.dateToString(new Date()) + "',5)";
            if (standardCampaign.getOrderType().equals(CampaignType.CustomerOrder.name())) {
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, -(standardCampaign.getOrderDays()));
                startDate = "convert(datetime,'" + DateUtil.dateToString(calendar.getTime()) + "',5)";
                findUserQuery += "and w.Todaydate > " + startDate + " and w.Todaydate <" + endDate + " and u.Subscribed = 'True'";
            } else if (standardCampaign.getOrderType().equals(CampaignType.CustomerNotOrder)) {
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, -(standardCampaign.getOrderDays()));
                endDate = "convert(datetime,'" + DateUtil.dateToString(calendar.getTime()) + "',5)";
                findUserQuery += "and w.Todaydate < " + endDate + " and u.Subscribed = 'True'";
            } else if (standardCampaign.getOrderType().equals(CampaignType.customizedOrdered)) {

            }
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findUserQuery);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                User user = new User();
                user.setId(resultSet.getInt("Id"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
//                user.setCellPhone(resultSet.getString("Phone"));
                users.add(user);
            }

        } catch (ParseException e) {

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return users;
    }

    /**
     * @throws DAOException
     */
    //--Saeid AmanZadhe
    public  List<CampaignEmailTemplate> getUserEmailByStandardCampaign(StandardCampaign standardCampaign, String lang) throws DAOException {

        //Join Query
        String query = "";
        String langQuery = "";
        if(!lang.equals("")){
            langQuery = "AND u.lang ='"+lang+"'";
        }else{
            langQuery = "";
        }

        if(standardCampaign.getOrderType().equals("CustomerOrder")){
            query = "SELECT DISTINCT u.Id, u.FirstName, u.LastName, u.email, c.Phone, u.Lang from Web_Header_His wh left join " +
                    "contact_info c on wh.Cust = c.Phone  join web_user u on u.id = c.user_id Where u.Subscribed = 1 "+langQuery+" AND wh.Cust is not null AND u.FirstName is not null AND u.Phone!='5143430343' AND DATEDIFF(D,wh.Todaydate, GETDATE()) < " + standardCampaign.getOrderDays();

        }else if(standardCampaign.getOrderType().equals("CustomerNotOrder")){
            query = "SELECT DISTINCT u.Id, u.FirstName, u.LastName, u.email, c.Phone, u.Lang from Web_Header_His wh left join " +
                    "contact_info c on wh.Cust = c.Phone  join web_user u on u.id = c.user_id Where u.Subscribed = 1 "+langQuery+" AND u.lang ='"+lang+"' AND wh.Cust is not null AND u.FirstName is not null AND u.Phone!='5143430343' AND not ( DATEDIFF(D,wh.Todaydate, GETDATE()) < " + standardCampaign.getOrderDays() + " )";

        }else if(standardCampaign.getOrderType().equals("customizedOrdered")){
            query = " SELECT  u.Id, u.FirstName, u.LastName, u.email, c.Phone, COUNT(wh.Docnumber) AS NumberOfOrders, u.Lang FROM  Web_Header_His wh "+
                    " LEFT JOIN contact_info c on wh.Cust = c.Phone  join web_user u on u.id = c.user_id  " +
                    " Where u.Subscribed = 1 "+langQuery+" AND DATEDIFF(D,wh.Todaydate, GETDATE()) < " + standardCampaign.getOrderDays() +
                    " GROUP BY c.phone, u.FirstName, u.LastName, u.Email, u.Id " +
                    " HAVING COUNT(wh.Docnumber) " + standardCampaign.getOrderSign() + " " + standardCampaign.getOrderNumbers();

        }else if(standardCampaign.getOrderType().equals("allUsers")){

            query = "SELECT distinct u.Id, u.FirstName, u.LastName, email, u.Phone, u.Lang from web_user u Where u.Subscribed = 1 " + langQuery;
        }

        logger.debug("-------------------------------------- Standard Campaign ----------------------------------------");
        logger.debug("Order Type : " + standardCampaign.getOrderType());
        logger.debug("Query : " + query);
        logger.debug("-------------------------------------------------------------------------------------------------");

        List<CampaignEmailTemplate> mailList = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int i=1;
            while (resultSet.next()){
                CampaignEmailTemplate campaignEmailTemplate = new CampaignEmailTemplate();
                //------changing mails to Test format X@TEST.Y
                String email = "";
                if(resultSet.getString("email") != null){
                    campaignEmailTemplate.setEmailAddress(resultSet.getString("email"));
                }
                if(resultSet.getString("Id") != null){
                    campaignEmailTemplate.setUserId(resultSet.getString("Id"));
                }

                if(resultSet.getString("Lang") != null){
                    campaignEmailTemplate.setLang(resultSet.getString("Lang"));
                }

                /*        email = resultSet.getString("email");
                        int startIndex = email.indexOf("@");
                        String secondSecOfEmail = email.substring(startIndex, email.length());
                        String tempEmail = email.substring(0, startIndex + 1);
                        tempEmail = tempEmail + "TEST" + (secondSecOfEmail.substring(secondSecOfEmail.indexOf("."), secondSecOfEmail.length()));
                        campaignEmailTemplate.setEmailAddress(tempEmail);
                    }*/
                //--
                if(resultSet.getString("FirstName")!= null && resultSet.getString("LastName") != null)
                    campaignEmailTemplate.setName(resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
                mailList.add(campaignEmailTemplate);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }

        logger.info(LogMessages.START_OF_METHOD + "writeObject");

        return mailList;
    }

    public List<StandardCampaign> getStandardCampaignById(int id) throws DAOException {

        String query =
                "  select * from dbo.standardcampaign " +
                        "where standardcampaign_id = " + id;

        List<StandardCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StandardCampaign standardCampaign = new StandardCampaign();
                standardCampaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                standardCampaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                standardCampaign.setCampaign_date(resultSet.getString("campaign_date"));
                standardCampaign.setImageUrl(resultSet.getString("image_path"));
                standardCampaign.setImageUrlEn(resultSet.getString("image_path_en"));
                standardCampaign.setOrdered(resultSet.getBoolean("ordered"));
                standardCampaign.setOrderNumbers(resultSet.getInt("order_numbers"));
                standardCampaign.setStandardcampaign_id(resultSet.getInt("standardcampaign_id"));
                standardCampaign.setStatus(resultSet.getString("status"));
                standardCampaign.setMenu_id(resultSet.getString("food_id"));
                standardCampaign.setOrderDays(resultSet.getInt("order_days"));
                standardCampaign.setOrderType(resultSet.getString("order_type"));
                standardCampaign.setOrderSign(resultSet.getString("custom_order_operation"));
                standardCampaign.setSubjectEn(resultSet.getString("subjectEn"));
                standardCampaign.setSubjectFr(resultSet.getString("subjectFr"));
                standardCampaign.setItemHtmlEn(resultSet.getString("items_html_en"));
                standardCampaign.setItemHtmlFr(resultSet.getString("items_html_fr"));

                list.add(standardCampaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }
    //--

    public List<StandardCampaign> getSendingStandardCampaignByNow() throws DAOException {

        String query = "select * from dbo.standardcampaign where  campaign_date < GETDATE() AND status='"+Constant.SENDING_CAMPAIGN+"'";

        logger.debug("Debug: " + query);
        List<StandardCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StandardCampaign standardCampaign = new StandardCampaign();
                standardCampaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                standardCampaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                standardCampaign.setCampaign_date(resultSet.getString("campaign_date"));
                standardCampaign.setImageUrl(resultSet.getString("image_path"));
                standardCampaign.setOrdered(resultSet.getBoolean("ordered"));
                standardCampaign.setOrderNumbers(resultSet.getInt("order_numbers"));
                standardCampaign.setStandardcampaign_id(resultSet.getInt("standardcampaign_id"));
                standardCampaign.setStatus(resultSet.getString("status"));
                standardCampaign.setMenu_id(resultSet.getString("food_id"));
                standardCampaign.setOrderDays(resultSet.getInt("order_days"));
                standardCampaign.setOrderType(resultSet.getString("order_type"));
                standardCampaign.setSubjectEn(resultSet.getString("subjectEn"));
                standardCampaign.setSubjectFr(resultSet.getString("subjectFr"));
                standardCampaign.setOrderSign(resultSet.getString("custom_order_operation"));
                list.add(standardCampaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    //---------------------------------------------------------------------------

    public List<StandardCampaign> getStandardCampaignByNow() throws DAOException {

        String query = "select * from dbo.standardcampaign where  campaign_date < GETDATE() AND status='"+Constant.RUN_CAMPAIGN+"'";

        logger.debug("Debug: " + query);
        List<StandardCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StandardCampaign standardCampaign = new StandardCampaign();
                standardCampaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                standardCampaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                standardCampaign.setCampaign_date(resultSet.getString("campaign_date"));
                standardCampaign.setImageUrl(resultSet.getString("image_path"));
                standardCampaign.setOrdered(resultSet.getBoolean("ordered"));
                standardCampaign.setOrderNumbers(resultSet.getInt("order_numbers"));
                standardCampaign.setStandardcampaign_id(resultSet.getInt("standardcampaign_id"));
                standardCampaign.setStatus(resultSet.getString("status"));
                standardCampaign.setMenu_id(resultSet.getString("food_id"));
                standardCampaign.setOrderDays(resultSet.getInt("order_days"));
                standardCampaign.setOrderType(resultSet.getString("order_type"));
                standardCampaign.setSubjectEn(resultSet.getString("subjectEn"));
                standardCampaign.setSubjectFr(resultSet.getString("subjectFr"));
                standardCampaign.setOrderSign(resultSet.getString("custom_order_operation"));
                list.add(standardCampaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    public List<StandardCampaign> getStandardCampaign(int offset, int count) throws DAOException {

        int from = offset * count;
        String query =
                "  select top " + count + " * from dbo.standardcampaign " +
                        "where standardcampaign_id  not in (select top " + from + " standardcampaign_id from standardcampaign order by standardcampaign_id)" +
                        "order by creation_date";


        List<StandardCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Date nowDate = new Date();
            while (resultSet.next()) {
                StandardCampaign standardCampaign = new StandardCampaign();
                standardCampaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                standardCampaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                standardCampaign.setCampaign_date(resultSet.getString("campaign_date").substring(0,resultSet.getString("campaign_date").length() - 5));
                standardCampaign.setImageUrl(resultSet.getString("image_path"));
                standardCampaign.setOrdered(resultSet.getBoolean("ordered"));
                standardCampaign.setOrderNumbers(resultSet.getInt("order_numbers"));
                standardCampaign.setStandardcampaign_id(resultSet.getInt("standardcampaign_id"));
                standardCampaign.setStatus(resultSet.getString("status"));
                standardCampaign.setMenu_id(resultSet.getString("food_id"));
                standardCampaign.setOrderDays(resultSet.getInt("order_days"));
                standardCampaign.setOrderType(resultSet.getString("order_type"));
                standardCampaign.setSubjectEn(resultSet.getString("subjectEn"));
                standardCampaign.setSubjectFr(resultSet.getString("subjectFr"));
                standardCampaign.setOrderSign(resultSet.getString("custom_order_operation"));

                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date campaignDate = dateParser.parse(resultSet.getString("campaign_date"));
               /* if(campaignDate.compareTo(nowDate) < 0 && resultSet.getString("status").equals("run")){
                    standardCampaign.setSendingStat("sending");
                }else{
                    standardCampaign.setSendingStat("stop");
                   }*/
                list.add(standardCampaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getCountStandardCampaign() throws DAOException {
        String query = "SELECT count(*) cnt FROM  dbo.standardcampaign ";
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

    public List<CampaignEmailTemplate> getOutboundStandardEmailsByDate(String campaign_date) {

        List<CampaignEmailTemplate> resultTemplate = new ArrayList<CampaignEmailTemplate>();
        try {
            String findUserQuery = "select o.creation_date,  o.email_address ,o.status ,o.template_filename , o.user_name" +
                    " , p.campaign_title_en , p.campaign_title_fr , p.image_url , m.Description , m.FoodCategory , m.Price , m.ProductName , m. ProductNo" +
                    "                , m.GroupID , m.WebDescEN , m.WebDescFR , m.category from dbo.outboundmail_standardcampaign o ," +
                    "  dbo.standardcampaign p , dbo.MenuItem m where o.standardcampaign_id = p.standardcampaign_id  and o.campaign_date = " +
                    " convert(datetime,'" + campaign_date + "',5) and  CAST(p.menu_Id AS INT) = CAST(m.ProductNo AS INT )";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findUserQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CampaignEmailTemplate template = new CampaignEmailTemplate();
                template.setCategory(resultSet.getInt("category"));
                //template.setCreationDate(resultSet.getDate("creation_date"))Cal;
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MINUTE, 4);
                template.setCreationDate(c.getTime());
                template.setRecipientEmailAddress(resultSet.getString("email_address"));
                template.setTemplateFileName(resultSet.getString("template_filename"));
                template.setName(resultSet.getString("user_name"));
                template.setTitleEn(resultSet.getString("campaign_title_en"));
                template.setTitleFr(resultSet.getString("campaign_title_fr"));
                template.setImagePath(resultSet.getString("image_url"));
                template.setDescription(resultSet.getString("Description"));
                template.setFoodCategory(resultSet.getString("FoodCategory"));
                template.setWebDescEN(resultSet.getString("WebDescEN"));
                template.setWebDescFR(resultSet.getString("WebDescFR"));
                resultTemplate.add(template);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return resultTemplate;

    }

    //Postal
    public Integer savePostalCampaign(PostalCampaign postalCampaign) throws DAOException {
        int postalId = 0;
        try {
            String query = "INSERT INTO   dbo.postalcampaign  (" +
                    "  campaign_title_en ," +
                    "  campaign_title_fr  ," +
                    "  menu_id  ," +
                    "  status ," +
                    "  postal_code," +
                    "  image_url ," +
                    "  campaign_date )" +
                    "     VALUES (" +
                    "'" + postalCampaign.getCampaign_title_en() + "' ," +
                    "'" + postalCampaign.getCampaign_title_fr() + "' ," +
                    postalCampaign.getMenu_id() + " ," +
                    postalCampaign.getStatus() + " ," +
                    "'" + postalCampaign.getPostalCode() + "' ," +
                    "'" + postalCampaign.getImageUrl() + "' ," +
                    " convert(datetime,'" + DateUtil.dateToString(postalCampaign.getCampaign_date()) + "',5))";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet r = preparedStatement.getGeneratedKeys();
            if (r.next()) {
                postalId = r.getInt(1);
            }

        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            super.freeResources();
        }
        return postalId;
    }

    public List<User> getUsersForPostalCampaign(String postalCode) {
        List<User> users = new ArrayList<>();
        try {
            String findUserQuery = "select u.Id , u.FirstName , u.lastName , u.Email , u.Phone  from dbo.web_user u where u.PostalCode like '%" + postalCode + "%'";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findUserQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("Id"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
//                user.setCellPhone(resultSet.getString("Phone"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return users;

    }

    /**
     * @param postalCampaign
     * @throws DAOException
     */
    public void savePostalCampaignForUsers(Date campaignDate, Integer postalCampaign, List<User> users) throws DAOException {
        try {
            connection = getConnection();
            for (User user : users) {
                String query = "INSERT INTO dbo.outboundmail_postalcampaign (" +
                        "  postalcampaign_id ," +
                        "  creation_date  ," +
                        "  campaign_date  ," +
                        "  email_address  ," +
                        "  user_name  ," +
                        "  template_filename  ," +
                        "  status )" +
                        "     VALUES (" +
                        postalCampaign + " ," +
                        "convert(datetime,'" + DateUtil.dateToString(new Date()) + "',5)" + "," +
                        "convert(datetime,'" + DateUtil.dateToString(campaignDate) + "',5)" + ",'"
                        + user.getEmail() + "' ,'" + user.getFirstName() + "'," + " 'PostalCampaign.flt' ,'" +
                        EmailStatus.PENDING.name() + "')";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            super.freeResources();
        }
    }

    public List<CampaignEmailTemplate> getOutboundEmailsByDate(Date campaign_date) {

        List<CampaignEmailTemplate> resultTemplate = new ArrayList<CampaignEmailTemplate>();
        try {
            String findUserQuery = "select o.creation_date,  o.email_address ,o.status ,o.template_filename , o.user_name" +
                    " , p.campaign_title_en , p.campaign_title_fr , p.image_url , m.Description , m.FoodCategory , m.Price , m.ProductName , m. ProductNo" +
                    "                , m.GroupID , m.WebDescEN , m.WebDescFR , m.category from dbo.outboundmail_postalcampaign o ," +
                    "  dbo.postalcampaign p , dbo.MenuItem m where o.postalcampaign_id = p.postalcampaign_id  and o.campaign_date = " +
                    " convert(datetime,'" + DateUtil.dateToString(campaign_date) + "',5) and  CAST(p.menu_Id AS INT) = CAST(m.ProductNo AS INT )";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findUserQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CampaignEmailTemplate template = new CampaignEmailTemplate();
                template.setCategory(resultSet.getInt("category"));
                //template.setCreationDate(resultSet.getDate("creation_date"))Cal;
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MINUTE, 4);
                template.setCreationDate(c.getTime());
                template.setRecipientEmailAddress(resultSet.getString("email_address"));
                template.setTemplateFileName(resultSet.getString("template_filename"));
                template.setName(resultSet.getString("user_name"));
                template.setTitleEn(resultSet.getString("campaign_title_en"));
                template.setTitleFr(resultSet.getString("campaign_title_fr"));
                template.setImagePath(resultSet.getString("image_url"));
                template.setDescription(resultSet.getString("Description"));
                template.setFoodCategory(resultSet.getString("FoodCategory"));
                template.setWebDescEN(resultSet.getString("WebDescEN"));
                template.setWebDescFR(resultSet.getString("WebDescFR"));
                resultTemplate.add(template);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return resultTemplate;

    }

    public List<PostalCampaign> getPostalCampaign(int offset, int count) throws DAOException {

        int from = offset * count;
        String query =
                "  select top " + count + " * from dbo.postalcampaign " +
                        "where postalcampaign_id  not in (select top " + from + " postalcampaign_id from postalcampaign order by postalcampaign_id)" +
                        "order by postalcampaign_id";


        List<PostalCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostalCampaign standardCampaign = new PostalCampaign();
                standardCampaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                standardCampaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                standardCampaign.setCampaign_date(resultSet.getDate("campaign_date"));
                standardCampaign.setImageUrl(resultSet.getString("image_url"));
                standardCampaign.setStatus(resultSet.getInt("status"));
                standardCampaign.setMenu_id(resultSet.getString("menu_id"));
                standardCampaign.setPostalCode(resultSet.getString("postal_code"));
                list.add(standardCampaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    public int getCountPostalCampaign() throws DAOException {
        String query = "SELECT count(*) cnt FROM  dbo.postalcampaign ";
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

    //Birthday
    public void saveBirthdayCampaign(BirthdayCampaign birthdayCampaign) throws DAOException {
        try {

            String query = "INSERT INTO   dbo.birthdaycampain  (" +
                    "  campaign_title_en ," +
                    "  campaign_title_fr  ," +
                    "  menu_id  ," +
                    "  status ," +
                    "  contactInfo_id," +
                    "  campaign_note)" +
                    "     VALUES (" +
                    birthdayCampaign.getCampaign_title_en() + " ," +
                    birthdayCampaign.getCampaign_title_fr() + " ," +
                    birthdayCampaign.getMenu_id() + " ," +
                    birthdayCampaign.getStatus() + " ," +
                    birthdayCampaign.getContactinfo_id() + " ," +
                    birthdayCampaign.getCampaign_note() + " ," +
                    birthdayCampaign.getCampaign_note() + ")";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public void saveBirthdayCampaignForUser(Integer birthdayCampaign, Integer userId) throws DAOException {
        try {
            String query = "INSERT INTO dbo.outboundmail_birthdaycampaign (" +
                    "  birthdaycampaign_id ," +
                    "  user_id  ," +
                    "  status )" +
                    "     VALUES (" +
                    birthdayCampaign + " ," +
                    userId + " ," +
                    0 + ")";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Debug: retrieve data from DB has been failed.\n cause: " + e.getMessage());
        } finally {
            super.freeResources();
        }
    }

    public List<Integer> getUsersForBirthdayCampaign(String postalCode) {
        List<Integer> userIds = new ArrayList<>();
        try {
            String findUserQuery = "";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(findUserQuery);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                userIds.add(results.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return userIds;

    }

    public List<BirthdayCampaign> getBirthdayCampaign(int offset, int count) throws DAOException {

        int from = offset * count;
        String query =
                "  select top " + count + " * from dbo.postalcampaign " +
                        "where postalcampaign_id  not in (select top " + from + " postalcampaign_id from postalcampaign order by postalcampaign_id)" +
                        "order by postalcampaign_id";


        List<BirthdayCampaign> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BirthdayCampaign campaign = new BirthdayCampaign();
                campaign.setCampaign_title_en(resultSet.getString("campaign_title_en"));
                campaign.setCampaign_title_fr(resultSet.getString("campaign_title_fr"));
                //campaign.setImageUrl(resultSet.getString("image_url"));
                campaign.setStatus(resultSet.getInt("status"));
                campaign.setMenu_id(resultSet.getString("menu_id"));
                list.add(campaign);
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    public int getCountBirthdayCampaign() throws DAOException {
        String query = "SELECT count(*) cnt FROM  dbo.postalcampaign ";
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

    //Saeid AmanZadeh-only for test
    public List<CampaignEmail> getEmailCampaign() throws DAOException {
        String query = "select * from " + Constant.TABLE_LIST.web_user + " order by id";

        List<CampaignEmail> list = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int i= 0;
            while (resultSet.next()) {
                i++;
                if(i<=100){//100 records are enough

                    //------changing algorithm to this format X@TEST.Y
                    CampaignEmail emailCampaign = new CampaignEmail();
                    emailCampaign.setUserId(resultSet.getString("Id"));
                    String email = resultSet.getString("Email");

                    int startIndex = email.indexOf("@");
                    String secondSecOfEmail = email.substring(startIndex, email.length());
                    String tempEmail = email.substring(0, startIndex + 1);
                    tempEmail = tempEmail + "TEST" + (secondSecOfEmail.substring(secondSecOfEmail.indexOf("."), secondSecOfEmail.length()));
                    //System.out.println(tempEmail);
                    //--
                    emailCampaign.setEmailAddress(email);
                    list.add(emailCampaign);
                }
            }
        } catch (SQLException e) {
            logger.debug("Debug: retrive data from DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
        return list;
    }

    //Saeid AmanZadeh
    //Masking emails to test format *@TestUniqNum.*
    public int maskeEmails() throws ParseException, DAOException {
        int standardId = 0;
        ApplicationConfig applicationConfig = new ApplicationConfig();

        try {
            int ordered = 0;
            String query = "\n" +
                    "DECLARE @intFlag INT\n" +
                    "DECLARE @randStr NVARCHAR(10)\n" +
                    "DECLARE @countTbl INT\n" +
                    "SET @countTbl = (SELECT COUNT(Email) FROM web_user) + 3000" +
                    "\n" +
                    "\n" +
                    "SET @intFlag = 1\n" +
                    "\n" +
                    "WHILE (@intFlag <= @countTbl)\n" +
                    "BEGIN\n" +
                    "    PRINT @intFlag\n" +
                    "    SET @intFlag = @intFlag + 1\n" +
                    "    SET @randStr = CONVERT(NVARCHAR, (RAND(@intFlag)* 1000000))\n" +
                    "    update web_user set Email = (select (SUBSTRING(Email, 0,\n" +
                    " CHARINDEX('@', Email)+1) + 'TEST'\n" +
                    " + \n" +
                    " @randStr\n" +
                    " + \n" +
                    " SUBSTRING(SUBSTRING(Email, CHARINDEX('@', Email)+1, LEN(Email)), \n" +
                    "CHARINDEX('.', SUBSTRING(Email, CHARINDEX('@', Email)+1, LEN(Email))),10)) from web_user where id=@intFlag) where id=@intFlag\n" +
                    "\n" +
                    "END";
            logger.debug("masked query: " + query);

            if(applicationConfig.getRunMode().equals("DEV") || applicationConfig.getRunMode().equals("TEST")){
                connection = getConnection();
                preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();
            }
            ResultSet r = preparedStatement.getGeneratedKeys();
            if (r.next()) {
                standardId = r.getInt(1);
            }
        } catch (SQLException e) {
            logger.debug("Debug: mask emails to test format on the database has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        } finally {
            super.freeResources();
        }
        return standardId;
    }
//----
}
