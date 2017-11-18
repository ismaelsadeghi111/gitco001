package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.exception.OverlapException;
import com.sefryek.doublepizza.model.DpDollarHistory;
import com.sefryek.doublepizza.model.Dpdollarday;
import com.sefryek.doublepizza.model.DpDollarSchedule;
import com.sefryek.doublepizza.model.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 11/20/13
 * Time: 1:19 PM
 */
public interface IDollarService {
    public static String BEAN_NAME = "dollarService";

    public void doSaveDpDollar(Dpdollarday dpDollar) throws DAOException;

    public Dpdollarday getDpDollar() throws DAOException;

    public Float getMinValue() throws DAOException;

    public void doSaveDpDollarSchedule(DpDollarSchedule dpDollarSchedule) throws ParseException, DAOException, OverlapException;

    public void doSaveMinValues(Float minVal) throws  ParseException, DAOException;

    public void updateDpDollarSchedule(DpDollarSchedule dpDollarSchedule) throws ParseException, DAOException, OverlapException;

    public void removeDpDollarSchedule(int dpDollarScheduleId) throws ParseException, DAOException;

    public List<DpDollarSchedule> getDpDollarSchedule(int offset,int count) throws DAOException;

    public int  getCountSchedule() throws DAOException;

    public Double calculateDpDollarsBalanceForUser(int userId);

    public  void calculateDpDollarsBalanceForAll();

    public Float getDollarPercentageByNumberOfDay(int numberOfDay) throws DAOException;

    public void saveDollarHistory(DpDollarHistory dpDollarHistory) throws DAOException, OverlapException, java.text.ParseException, SQLException;

    public DpDollarSchedule findDPDollarSchedualByDate(Date date) throws DAOException, ParseException;

    public List<DpDollarHistory> getAllDpDollarHistoryByUserEmail(User user) throws SQLException;

    public void save(Object obj) throws DAOException;

    public void saveHistory(DpDollarHistory dpDollarHistory) throws DAOException;

    public List<DpDollarHistory> getAllDpDollarHistoryByUserId(int userId) throws SQLException;

    public List<DpDollarHistory> findDpDollarsHistoryByUserId(int userId);

    public DpDollarHistory findDpDollarBalanceByUserId(int userId);

    public List<Map<String,Float>> getDpDollarsWeekly(Locale locale);

    public void reattachToSession(Object obj);

    public void updateDpDollarHistory(DpDollarHistory  dpDollarHistory) throws DAOException;
}
