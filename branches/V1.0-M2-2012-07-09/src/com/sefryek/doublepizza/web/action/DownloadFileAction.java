package com.sefryek.doublepizza.web.action;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sefryek.common.config.ApplicationConfig;
import com.sefryek.common.util.serialize.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 5, 2012
 * Time: 10:54:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadFileAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final int buffer_size = 1024;

        String fullFileName = request.getParameter("fileName");
        String fileName = StringUtil.extractFileName(fullFileName, ApplicationConfig.pathSplitterSign);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {

            FileInputStream in =
                    new FileInputStream(new File(ApplicationConfig.dataResourcesStartPath + fullFileName));

            ServletOutputStream out = response.getOutputStream();
            try{
                byte[] outputByte = new byte[buffer_size];
                
                int read = 0;
                while ((read = in.read(outputByte, 0, buffer_size)) != -1) {
                    out.write(outputByte, 0, read);
                }
            }
            finally{
                in.close();
                out.flush();
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
