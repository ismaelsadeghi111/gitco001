package com.sefryek.doublepizza.web.action;

import com.sefryek.common.LogMessages;
import com.sefryek.common.util.SecurityUtils;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.service.exception.DataLoadException;
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

/**
 * User: E_Ghasemi
 * Date: 11/19/13
 * Time: 1:26 PM
 */
public class BackendAction extends DispatchAction {
    private Logger logger;
    private InMemoryData inMemoryData;
    private WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        logger = Logger.getLogger(BackendAction.class);
        inMemoryData = new InMemoryData();
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        return reloadData(mapping, form, request, response);
    }

    //forwarding user to the HomePage
    public ActionForward reloadData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        if (!SecurityUtils.isAdmin()) {
            return mapping.findForward("forbidden");
        }
        logger.info(LogMessages.START_OF_METHOD + "goToMainPage - > forwardtoReloadPage");
        return mapping.findForward("forwardtoReloadPage");
    }

    public void reload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        logger.info(LogMessages.START_OF_METHOD + "goToMainPage - > forwardtoReloadPage");
        inMemoryData.initializeService(context);
        try {
            //loading data from DB to "menu"
            //Saeid AmanZadeh
            inMemoryData.runTimeEnReload = true;
            inMemoryData.runTimeFaReload = true;
            //---
            inMemoryData.loadData();

        } catch (DataLoadException e) {
            logger.error("Excpetion in loadData(), Quartz call,", e);
        }
       Thread.sleep(1000);

    }

}
