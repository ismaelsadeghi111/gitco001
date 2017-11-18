package com.sefryek.doublepizza.device;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jws.WebService;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: vahid.s
 * Date: Mar 28, 2012
 */
@WebService(serviceName = "/phoneServices")
public class PhoneServicesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(request, httpServletResponse);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServicesHandler servicesHandler = ServicesHandler.getHandler(request, httpServletResponse);
        String res = "";
        if (servicesHandler != null) {
            try {
                res = servicesHandler.handleRequest(request);
            } catch (Exception e) {
                res = "ERROR IN REQUEST";
            }
        }

        if (res != null) {
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            PrintWriter output = httpServletResponse.getWriter();
            output.write(res);
            output.flush();
            output.close();
        }

    }
}
