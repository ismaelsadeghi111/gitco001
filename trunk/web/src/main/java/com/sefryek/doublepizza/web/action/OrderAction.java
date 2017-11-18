package com.sefryek.doublepizza.web.action;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.OrderDetailHistory;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.implementation.OrderServiceImpl;
import com.sefryek.doublepizza.web.form.OrderForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Hossein Sadeghi Fard on 1/22/14.
 */
public class OrderAction  extends DispatchAction {
    private OrderForm orderForm;
    private OrderServiceImpl orderService;
    private List<OrderHistory> orderHistories;
    private List<OrderDetailHistory> orderHistoryDetails;

    public List<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public void setOrderHistories(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public List<OrderDetailHistory> getOrderHistoryDetails() {
        return orderHistoryDetails;
    }

    public void setOrderHistoryDetails(List<OrderDetailHistory> orderHistoryDetails) {
        this.orderHistoryDetails = orderHistoryDetails;
    }

    public OrderServiceImpl getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    public ActionForward getOrderHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
        String userEmail = request.getParameter("userEmail");
        orderForm = (OrderForm) form;
        if(user != null){
            orderForm.setUser(user);
        }

        orderHistories = orderService.getOrderHistoryByUserEmail(user);
        orderForm.setOrderHistories(orderHistories);
        request.getSession().setAttribute("orderHistories",orderHistories);
        return mapping.findForward("getOrderHistory");
    }

    public ActionForward getOrderHistoryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderForm = (OrderForm) form;
        String orderId = request.getParameter("orderId");
        orderHistories= (List<OrderHistory>) request.getSession().getAttribute("orderHistories");
        if (orderId != null && !orderId.isEmpty()) {
            for (OrderHistory orderHistory : orderHistories) {
                if(orderHistory.getDocnumber().equals(orderId)){
                    orderForm.setOrderHistoryDetails(orderHistory.getOrderDetailHistories());
            }
        }

    }
        return mapping.findForward("getOrderHistoryDetails");

    }
}
