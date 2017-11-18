package com.sefryek.doublepizza.web.action;

import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.DpDollarHistory;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.web.form.DpDollarsHistoryForm;
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
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 2/10/14.
 */
public class DpDollarsAction extends DispatchAction {
    private Logger logger = Logger.getLogger(CheckoutAction.class);
    private IUserService userService;
    private IDollarService dollarService;
    private WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(DollarSettingAction.class);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
    }


    public ActionForward getDpDollarsHistory(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws SQLException {
        if (SecurityUtils.getCurrentUser() == null || SecurityUtils.getCurrentUser().equals("")) {
            return mapping.findForward("forbidden");
        }

        DpDollarsHistoryForm dpDollarsHistoryForm = (DpDollarsHistoryForm) form;
        List<DpDollarHistory> dpDollarHistories;
        User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
        if (user != null){
            dpDollarHistories = dollarService.findDpDollarsHistoryByUserId(user.getId());
//            dpDollarHistories = user.getDpDollarHistories();
            dpDollarsHistoryForm.setDpDollarHistories(dpDollarHistories);
        }
        return mapping.findForward("getDpDollarsHistory");
    }
}
