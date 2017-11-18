package com.sefryek.doublepizza.device.handlers;

import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.doublepizza.device.ServicesConstant;
import com.sefryek.doublepizza.device.ServicesHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: vahid.s
 * Date: May 15, 2012
 */
public class ImageDataHandler extends ServicesHandler {
    private HttpServletResponse response;
    public ImageDataHandler( HttpServletResponse response ){
        this.response = response;         
    }

    public String handleRequest(HttpServletRequest request) {
        String imageName = request.getParameter(ServicesConstant.PARAMETER_IMAGE_NAME);
        String extenstion = imageName.substring(imageName.indexOf('.') + 1);
        String contentType = "image/" + extenstion;
        String result = null;
        try {
            String imagePath = ApplicationConfig.dataResourcesStartPath + ApplicationConfig.iphoneImagePathPosfix + imageName  ;
            FileInputStream fis = new FileInputStream(new File( imagePath));
            BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
            response.setContentType( contentType );
            
            int ch ;
            while( (ch = fis.read())> -1 ){
                output.write( ch );
            }

            fis.close();
            output.flush();
            output.close();
            
        } catch (Exception e) {
//            result = ServicesConstant.UNKNOWN_ERROR ;
        }

        return result;
    }
}
