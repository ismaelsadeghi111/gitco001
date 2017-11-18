package com.sefryek.doublepizza.web;

import com.sefryek.common.config.ApplicationConfig;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        final int buffer_size = 1024;
        try {
            String fileName = request.getParameter("image");
            if (fileName.contains(".."))
                return;
            String extenstion = fileName.substring(fileName.indexOf('.') + 1);
            String contentType = "image/" + extenstion;
            String fullPathFileName = "";
            if(fileName == "campaign")
                fullPathFileName = ApplicationConfig.campaignFilepath + fileName;
            else
                fullPathFileName = ApplicationConfig.dataResourcesStartPath + fileName;

            File file = new File(fullPathFileName);
            if (!file.exists())
                file = new File(ApplicationConfig.imageNotFoundFileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis, 1024);
            httpServletResponse.setContentType(contentType);
            BufferedOutputStream output = new BufferedOutputStream(httpServletResponse.getOutputStream());


            byte[] outputByte = new byte[buffer_size];

            int read = 0;
            while ((read = bis.read(outputByte, 0, buffer_size)) != -1) {
                output.write(outputByte, 0, read);
            }

//            for (int data; (data = bis.read()) > -1;) {
//                output.write(data);
//            }

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
