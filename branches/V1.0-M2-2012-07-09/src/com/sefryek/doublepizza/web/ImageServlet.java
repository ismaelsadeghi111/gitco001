package com.sefryek.doublepizza.web;

import com.sefryek.doublepizza.device.ServicesHandler;
import com.sefryek.common.config.ApplicationConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.jws.WebService;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 8, 2012
 * Time: 7:08:56 PM
 */
@WebService(serviceName = "/imageServices")
public class ImageServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(request, httpServletResponse);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        try {
            String fileName = request.getParameter("image");
            String extenstion = fileName.substring(fileName.indexOf('.') + 1);
            String contentType = "image/" + extenstion;
            String fullPathFileName = ApplicationConfig.dataResourcesStartPath + fileName;
            File file = new File(fullPathFileName);
            if (!file.exists())
                file = new File(ApplicationConfig.imageNotFoundFileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis, 1024);
            httpServletResponse.setContentType(contentType);
            BufferedOutputStream output = new BufferedOutputStream(httpServletResponse.getOutputStream());

            for (int data; (data = bis.read()) > -1;) {
                output.write(data);
            }

            bis.close();
            output.flush();
            output.close();
            
        }
        catch (IOException e) {

        } finally {
            // close the streams
        }
    }
}
