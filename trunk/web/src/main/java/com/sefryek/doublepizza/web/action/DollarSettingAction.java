package com.sefryek.doublepizza.web.action;

import com.sefryek.common.LogMessages;
import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.DateUtil;
import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dao.exception.OverlapException;
import com.sefryek.doublepizza.model.DpDollarHistory;
import com.sefryek.doublepizza.model.DpDollarSchedule;
import com.sefryek.doublepizza.model.Dpdollarday;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.web.form.DpDollarForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 11/19/13
 * Time: 3:01 PM
 */
public class DollarSettingAction extends DispatchAction {

    private Logger logger;
    private WebApplicationContext context;
    private IDollarService iDollarService;
    private IUserService userService;


    private int offset = 0;
    private int count = 10;
    private int totalPages = 0;
    private int totalRecords = 0;
    private int from = 0;
    private int to = 0;

    public WebApplicationContext getContext() {
        return context;
    }

    public void setContext(WebApplicationContext context) {
        this.context = context;
    }


    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        iDollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
        userService = (IUserService) context.getBean(userService.BEAN_NAME);

    }


    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        offset = 0;
        return goToMainPage(mapping, form, request, response);
    }

    public ActionForward goToMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        logger.info(LogMessages.START_OF_METHOD + "goToMainPage - > forwardtoHomePage");

        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward dollarDisplay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }

        List<DpDollarSchedule> list = null;
        try {

            Dpdollarday dpDollar = iDollarService.getDpDollar();
            Float minval = iDollarService.getMinValue();
            ApplicationConfig.dpDollarMinValue = (minval == null) ? Double.valueOf(0.00) : Double.valueOf(minval);
            totalRecords = iDollarService.getCountSchedule();
            totalPages = (totalRecords % count) == 0 ? (totalRecords / count) : (totalRecords / count) + 1;
            offset = (offset >= totalPages) ? totalPages - 1 : offset;
            offset = (offset == -1) ? 0 : offset;
            from = (offset * count);
            to = (offset * count) + count;
            list = iDollarService.getDpDollarSchedule(offset, count);

            request.setAttribute("dpDollarDays", dpDollar);
            request.setAttribute("dpDollarSchadule", list);
            request.setAttribute("minValue", minval);
            request.setAttribute("from", from + 1);
            request.setAttribute("to", (to) < totalRecords ? to : totalRecords);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageNumber", offset + 1);
            request.setAttribute("count", count);

        } catch (DAOException e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return mapping.findForward("forwardToDollarDisplay");
    }

    public ActionForward insertPeriodic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        try {
            DpDollarForm dpDollarForm = (DpDollarForm) form;
            Dpdollarday dpDollar = new Dpdollarday(dpDollarForm.getSunday(),
                    dpDollarForm.getMonday(), dpDollarForm.getTuesday(),
                    dpDollarForm.getWednesday(), dpDollarForm.getThursday(),
                    dpDollarForm.getFriday(), dpDollarForm.getSaturday());

            iDollarService.doSaveDpDollar(dpDollar);
        } catch (DAOException e) {
            request.setAttribute("message", "Error during retrieving data");
            e.printStackTrace();
        }
        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward insertMinval(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        try {
            DpDollarForm dpDollarForm = (DpDollarForm) form;
            iDollarService.doSaveMinValues(dpDollarForm.getMinValue());

        } catch (ParseException e) {
            request.setAttribute("message", "Invalid date format,th correct date format is yyyy-mm-dd");
            e.printStackTrace();
        } catch (DAOException e) {
            request.setAttribute("message", " failed to edit min value");
            e.printStackTrace();
        }
        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward editSchadule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }

        try {
            DpDollarForm dpDollarForm = (DpDollarForm) form;
            DpDollarSchedule dpDollarSchedule = new DpDollarSchedule();
            dpDollarSchedule.setStartDate(DateUtil.stringToDate(dpDollarForm.getStartDate()));
            dpDollarSchedule.setEndDate(DateUtil.stringToDate(dpDollarForm.getEndDate()));
            dpDollarSchedule.setPercentage(dpDollarForm.getPercentage());
            if (dpDollarForm != null && dpDollarForm.getSubmitMode() != null
                    && (dpDollarForm.getSubmitMode().equals("edit"))) {
                dpDollarSchedule.setId(dpDollarForm.getSchaduleId());
                iDollarService.updateDpDollarSchedule(dpDollarSchedule);
                request.setAttribute("message", "schedule edited successfully");

            } else {
                iDollarService.doSaveDpDollarSchedule(dpDollarSchedule);
                request.setAttribute("message", "Add new schedule successfully");
            }
        } catch (DAOException e) {
            request.setAttribute("message", "schedule failed to add/edit");
            e.printStackTrace();

        } catch (ParseException e) {
            request.setAttribute("message", "Invalid date format,the correct date format is yyyy-mm-dd");
            e.printStackTrace();
        } catch (OverlapException e) {
            request.setAttribute("message", "Date has overlap");
        }

        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward removeSchadule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }

        DpDollarForm dpDollarForm = (DpDollarForm) form;
        try {
            iDollarService.removeDpDollarSchedule(dpDollarForm.getSchaduleId());
            request.setAttribute("message", "schedule removed successfully");

        } catch (ParseException e) {
            request.setAttribute("message", "Invalid date format,th correct date format is yyyy-mm-dd");
            e.printStackTrace();
        } catch (DAOException e) {
            request.setAttribute("message", "schedule failed to remove");
            e.printStackTrace();
        }
        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward paging(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }

        DpDollarForm dpDollarForm = (DpDollarForm) form;
        if (dpDollarForm.getPagingAction().equals("firstPage")) {
            offset = 0;
        } else if (dpDollarForm.getPagingAction().equals("nextPage")) {
            offset++;
        } else if (dpDollarForm.getPagingAction().equals("lastPage")) {
            offset = totalPages - 1;
        } else if (dpDollarForm.getPagingAction().equals("previousPage")) {
            offset--;
        }

        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward dollarPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        offset = 0;
        return dollarDisplay(mapping, form, request, response);
    }

    public ActionForward calculateDPDollar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        offset = 0;
        List<String> usersId = new ArrayList<>();
        try {
            usersId = userService.getAllUsersId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (usersId != null) {
            int correctNumbers = 0;
            int inCorrectNumbers = 0;
            for (String userId : usersId) {
                List<DpDollarHistory> dpDollarHistories = null;
                List<DpDollarHistory> fixedDpDollarHistories = new ArrayList<>();
                try {
                    dpDollarHistories = iDollarService.getAllDpDollarHistoryByUserId(Integer.valueOf(userId));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (userId.equalsIgnoreCase("15555")){
                    logger.debug("User Id: " + userId);
                }
                if (dpDollarHistories != null && dpDollarHistories.size() > 0) {
                    Double balance = new Double(00.00d);
                    Double lastBalance = new Double(00.00d);
                    for (DpDollarHistory dpDollarHistory : dpDollarHistories) {
                        if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.EARNED.name())) {
                            balance += dpDollarHistory.getAmount();
                        } else if (dpDollarHistory.getStatus().equalsIgnoreCase(DpDollarHistory.Status.SPENT.name())) {
                            balance -= dpDollarHistory.getAmount();
                        }
                        lastBalance = dpDollarHistory.getBalance();
                        fixedDpDollarHistories.add(dpDollarHistory);
                        DecimalFormat df = new DecimalFormat("####0.00");
                        double value = balance;
                        double rounded = (double) Math.round(value * 100) / 100;
                        balance = rounded;
                        lastBalance = (double) Math.round(lastBalance * 100) / 100;
                        MathContext mc = new MathContext(2); // 2 precision
                        BigDecimal bg1, bg2, bg3;
                        bg1 = BigDecimal.valueOf(balance);
                        bg2 = BigDecimal.valueOf(lastBalance);
                        bg3 = bg2.subtract(bg1, mc);
                        if ((bg3.compareTo(BigDecimal.valueOf(0.01))==0)){
                            lastBalance = balance;
                        }
                        logger.debug("User id :" + userId + " Status: " + dpDollarHistory.getStatus() + " Amount: " + dpDollarHistory.getAmount() + " Old balance: " + df.format(lastBalance)  + " New Balance: " + df.format(balance) + (balance.compareTo(lastBalance) == 0 ? " " : "wrong balance*****"));
                    }
                    if (balance.compareTo(lastBalance) == 1) {
                        for (DpDollarHistory fixedDpDollarHistory : fixedDpDollarHistories) {
                            try {
                                iDollarService.updateDpDollarHistory(fixedDpDollarHistory);
                            } catch (DAOException e) {
                                e.printStackTrace();
                            }
                        }
                        logger.debug("Balance is incorrect for user  id: " + userId);
                        inCorrectNumbers++;
                    } else {
                        for (DpDollarHistory fixedDpDollarHistory : fixedDpDollarHistories) {
                            try {
                                iDollarService.updateDpDollarHistory(fixedDpDollarHistory);
                            } catch (DAOException e) {
                                e.printStackTrace();
                            }
                        }
                        logger.debug("wowwwwwwwwwww Balance is Correct for user  id: " + userId);
                        correctNumbers++;
                    }
                }
            }
            logger.debug("Numbers of correct  users: " + correctNumbers);
            logger.debug("Numbers of incorrect  users: " + inCorrectNumbers);
        }
        return dollarDisplay(mapping, form, request, response);
    }


}
