package com.sefryek.doublepizza.device.handlers;

import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sefryek.doublepizza.model.FileUpload;

import java.io.*;

/**
 * Created by Saeid AmanZadeh on 6/2/14.
 */
public class FileUploadController{


    //@Override
    public String uploadFile(MultipartFile file)
            throws Exception {

       // FileUpload fileUpload = (FileUpload)command;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String rtnStr = "";
       // MultipartFile file = fileUpload.getFile();

        String fileName = "";

        if(file != null){
            if(file.getSize() == 0){
                rtnStr = "required.fileUpload";
            }else{
                fileName = file.getOriginalFilename();

                //do whatever you want
                try {
                    inputStream = file.getInputStream();

                    File newFile = new File("C:/Users/nagesh.chauhan/files/" + fileName);
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    }
                    outputStream = new FileOutputStream(newFile);
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            rtnStr = "FileUploadSuccess";
        }

        return rtnStr;
    }

}
