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
import java.util.ResourceBundle;

import com.sefryek.doublepizza.service.IStore;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 20, 2012
 * Time: 5:19:25 PM
 */
public class StoreAction extends DispatchAction {

    private Logger logger;
    private ResourceBundle messageBundle;
    private IStore storeService;
    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        storeService = (IStore) context.getBean(IStore.BEAN_NAME);
    }

    public ActionForward viewMap(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        Store selectedStore = InMemoryData.getStore(request.getParameter(Constant.STORE_ID));

        request.setAttribute(Constant.STORE,selectedStore);

        return mapping.findForward("viewMap");
    }

}