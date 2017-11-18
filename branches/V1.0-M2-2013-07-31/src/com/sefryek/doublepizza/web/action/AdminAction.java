package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.service.exception.DataLoadException;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 18, 2012
 * Time: 9:22:31 AM
 */
public class AdminAction extends DispatchAction {
    private Logger logger;
    private InMemoryData inMemoryData;
    private WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        logger = Logger.getLogger(AdminAction.class);
        inMemoryData = new InMemoryData();
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws DataLoadException {
        inMemoryData.initializeService(context);

        inMemoryData.loadData();

        response.setContentType("text/html");

        try {
            PrintWriter out = response.getWriter();
            out.write("Double Pizza has been Launched successfully.");
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new DataLoadException(e, IOException.class, "Data has been load successfully!! but an exception happend while preparing success message.");
        }

        return null;
    }

    public ActionForward forwardToDashBoard(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) throws DataLoadException {
        return mapping.findForward("gotoDashboard");
    }
}
