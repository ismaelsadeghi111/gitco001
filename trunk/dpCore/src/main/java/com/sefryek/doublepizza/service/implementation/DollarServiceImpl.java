package com.sefryek.doublepizza.service.implementation;

import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.dao.DollarDAO;
import com.sefryek.doublepizza.dao.DollarScheduleDAO;
import com.sefryek.doublepizza.dao.UserDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.exception.OverlapException;
import com.sefryek.doublepizza.model.DpDollarHistory;
import com.sefryek.doublepizza.model.DpDollarSchedule;
import com.sefryek.doublepizza.model.Dpdollarday;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.IDollarService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 11/20/13
 * Time: 4:37 PM
 */
public class DollarServiceImpl implements IDollarService {
    private static Logger logger = Logger.getLogger(DollarServiceImpl.class);
    private DollarDAO dollarDAO;
    private DollarScheduleDAO dollarScheduleDAO;
    private UserDAO userDAO;
//    userService = (IUserService) context.getBean(userService.BEAN_NAME);


    public DollarScheduleDAO getDollarScheduleDAO() {
        return dollarScheduleDAO;
    }

    public void setDollarScheduleDAO(DollarScheduleDAO dollarScheduleDAO) {
        this.dollarScheduleDAO = dollarScheduleDAO;
    }

    public DollarDAO getDollarDAO() {
        return dollarDAO;
    }

    public void setDollarDAO(DollarDAO dollarDAO) {
        this.dollarDAO = dollarDAO;
    }

    @Override
    public void doSaveDpDollar(Dpdollarday dpDollar) throws DAOException {
        dollarDAO.saveDollarDays(dpDollar);
    }


    @Override
    public Dpdollarday getDpDollar() throws DAOException {
        return dollarDAO.getDpDollar();
    }

    @Override
    public Float getMinValue() throws DAOException {
        return dollarDAO.getMinval();
    }

    @Override
    public void doSaveDpDollarSchedule(DpDollarSchedule dpDollarSchedule) throws ParseException, DAOException, OverlapException {
        dollarScheduleDAO.saveDollarSchedule(dpDollarSchedule);
    }

    @Override
    public void doSaveMinValues(Float minvalue) throws DAOException {
        dollarDAO.doSaveMinvalue(minvalue);
    }

    @Override
    public void updateDpDollarSchedule(DpDollarSchedule dpDollarSchedule) throws ParseException, DAOException, OverlapException {
        dollarScheduleDAO.updateDollarSchedule(dpDollarSchedule);
    }

    @Override
    public void removeDpDollarSchedule(int dpDollarScheduleId) throws DAOException {
        dollarScheduleDAO.removeDollarSchedule(dpDollarScheduleId);
    }

    @Override
    public List<DpDollarSchedule> getDpDollarSchedule(int offset, int count) throws DAOException {
        return dollarScheduleDAO.getDpDollarSchedule(offset, count);
    }

    @Override
    public int getCountSchedule() throws DAOException {
        return dollarScheduleDAO.getCountSchedule();

    }

    @Override
    public Float getDollarPercentageByNumberOfDay(int numberOfDay) throws DAOException {
        Float percentage = new Float(0);
        Dpdollarday dpDollarday = dollarDAO.getDpDollar();

        switch (numberOfDay) {
            case 1:
                percentage = dpDollarday.getSunday();
                break;
            case 2:
                percentage = dpDollarday.getMonday();
                break;
            case 3:
                percentage = dpDollarday.getTuesday();
                break;
            case 4:
                percentage = dpDollarday.getWednesday();
                break;
            case 5:
                percentage = dpDollarday.getThursday();
                break;
            case 6:
                percentage = dpDollarday.getFriday();
                break;
            case 7:
                percentage = dpDollarday.getSaturday();
                break;
        }
        percentage = percentage == null ? new Float(0) : percentage;
        return percentage;
    }


    public void calculateDpDollarsBalanceForAll() {


        List<User> users = userDAO.findAll();
        Double spentAmount = 00.00;
        Double earnedAmount = 00.00;
        if (users != null){
            for (User user : users) {
                List<DpDollarHistory> dpDollarHistories = user.getDpDollarHistories();
                if (dpDollarHistories != null && dpDollarHistories.size() > 0) {
                    for (DpDollarHistory dpDollarHistory : dpDollarHistories) {
                      /*  if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.EARNED.name())) {
                            earnedAmount += dpDollarHistory.getAmount();
                        } else if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.SPENT.name())) {
                            spentAmount += dpDollarHistory.getAmount();
                        }*/

                    }
                }
            }

        }


    }


    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Double calculateDpDollarsBalanceForUser(int userId) {
        logger.debug("calculate balance in ordering process Start calculateDpDollarsBalanceForUser");
        Double balance = new Double(00.00d);
        Double lastBalance = new Double(00.00d);
        List<DpDollarHistory> dpDollarHistories = new ArrayList<>();
        List<DpDollarHistory> fixedDpDollarHistories = new ArrayList<>();
        try {
            dpDollarHistories = dollarDAO.getAllDpDollarHistoryByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dpDollarHistories != null && dpDollarHistories.size() > 0) {

            for (DpDollarHistory dpDollarHistory : dpDollarHistories) {
                if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.EARNED.name())) {
                    balance += dpDollarHistory.getAmount();
                } else if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.SPENT.name())) {
                    balance -= dpDollarHistory.getAmount();
                }
                lastBalance = dpDollarHistory.getBalance();
                double value = balance;
                double rounded = (double) Math.round(value * 100) / 100;
                balance = rounded;
                MathContext mc = new MathContext(2); // 2 precision
                BigDecimal bg1, bg2, bg3;
                bg1 = BigDecimal.valueOf(balance);
                bg2 = BigDecimal.valueOf(lastBalance);
                bg3 = bg2.subtract(bg1, mc);
                if ((bg3.compareTo(BigDecimal.valueOf(0.01))==0)){
                    lastBalance = balance;
                }
                dpDollarHistory.setBalance(balance);
                fixedDpDollarHistories.add(dpDollarHistory);
                logger.debug("User id :" + userId + " Status: " + dpDollarHistory.getStatus() + " Amount: " + dpDollarHistory.getAmount() + " Old balance: " + lastBalance + " New Balance: " + balance + (balance.compareTo(lastBalance) == 0 ? " " : " wrong balance*****"));
            }
            if (balance.compareTo(lastBalance) == 1) {
                logger.debug("return balance for user: "+userId+" balance: "+balance);
                return balance;
            }
        }
        logger.debug("return balance for user: "+userId+" lastBalance: "+lastBalance);
        return lastBalance;
    }

    @Override
    public void saveDollarHistory(DpDollarHistory dpDollarHistory) throws DAOException, OverlapException, java.text.ParseException, SQLException {
        dollarDAO.saveDollarHistory(dpDollarHistory);
    }

    @Override
    public DpDollarSchedule findDPDollarSchedualByDate(Date date) throws DAOException, ParseException {
        DpDollarSchedule dpDollarSchedule = dollarScheduleDAO.findDPDollarSchedualByDate(date);
        return dpDollarSchedule;
    }

    @Override
    public List<DpDollarHistory> getAllDpDollarHistoryByUserEmail(User user) throws SQLException {
        List<DpDollarHistory> dpDollarHistories = dollarDAO.getAllDpDollarHistoryByUserEmail(user);
        return dpDollarHistories;
    }

    @Override
    public List<DpDollarHistory> getAllDpDollarHistoryByUserId(int userId) throws SQLException {
        List<DpDollarHistory> dpDollarHistories = dollarDAO.getAllDpDollarHistoryByUserId(userId);
        return dpDollarHistories;
    }

    @Override
    public void save(Object obj) throws DAOException {
        dollarDAO.save(obj);
    }

    @Override
    public List<DpDollarHistory> findDpDollarsHistoryByUserId(int userId) {
        List<DpDollarHistory> dpDollarHistoryList = dollarDAO.findDpDollarsHistoryByUserId(userId);
        return dpDollarHistoryList;
    }

    @Override
    public DpDollarHistory findDpDollarBalanceByUserId(int userId) {
        DpDollarHistory dpDollarHistory = dollarDAO.findLastDpDollarsHistoryByUserId(userId);
        return dpDollarHistory;
    }

    @Override
    public List<Map<String, Float>> getDpDollarsWeekly(Locale locale) {
//        int dayNo = DateUtil.getDayOfWeekNumber(new Date());
        Map<String, Float> dayWithPercentage = null;
        List<Map<String, Float>> dayWithPercentageList = new ArrayList<Map<String, Float>>();
        for (int i = 1; i <= 7; i++) {
            dayWithPercentage = new HashMap<>();
            try {
                Float percentage = getDollarPercentageByNumberOfDay(i);
                percentage = (percentage == null) ? new Float(0) : percentage;
                String nameOfDay = DateUtil.getNameOfDayByLocale(i, locale);
                dayWithPercentage.put(nameOfDay, percentage);

                dayWithPercentageList.add(dayWithPercentage);
            } catch (DAOException e) {
                e.printStackTrace();
            }

        }
        return dayWithPercentageList;
    }

    @Override
    public void reattachToSession(Object obj) {
        dollarDAO.reattachToSession(obj);
    }

    @Override
    public void saveHistory(DpDollarHistory dpDollarHistory) throws DAOException {
        dollarDAO.saveHistory(dpDollarHistory);
    }

    @Override
    public void updateDpDollarHistory(DpDollarHistory  dpDollarHistory) throws DAOException {
        dollarDAO.updateDpDollarHistory(dpDollarHistory);
    }

}
