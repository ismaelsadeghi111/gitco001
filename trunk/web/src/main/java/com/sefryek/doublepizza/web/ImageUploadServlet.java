package com.sefryek.doublepizza.web;

import com.sefryek.common.config.ApplicationConfig;
//import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Amir Sadri
 * Date: Jul 2, 2008
 * Time: 5:52:56 AM
 */
public class ImageUploadServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

        String img = request.getParameter("img");
        if(img == null || img == ""){
            String source = ApplicationConfig.getCampaignFilepath() + File.separator;
            //+ Constant.IMAGE_DEFAULT_NAME.trim();

            File file = new File(source);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int size = fileInputStream.available();
                byte[] buffer = new byte[size];
                fileInputStream.read(buffer, 0, size);
                fileInputStream.close();
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(buffer);
                outputStream.flush();
                outputStream.close();
                response.flushBuffer();
                //response.setContentType(getServletContext().getMimeType(Constant.IMAGE_DEFAULT_NAME.trim()));
//                response.setContentLength(resultSet.getInt("imageContentLength"));
               // response.setHeader("Content-Disposition", "inline;filename=\"" + Constant.IMAGE_DEFAULT_NAME.trim() + "\"");


            } catch (FileNotFoundException e) {

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }   else {


        }

    }
}
