package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.common.config.ApplicationConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: May 2, 2012
 * Time: 1:35:58 PM
 *This class is a wraper for create and handling archive directory for storing serialized objects
 *  such as menu, category, combined, single and all others
 */
public class ArchiveHandler {
    private static final Logger logger = Logger.getLogger(ArchiveHandler.class);
    
    private String getApplicationVersion(){
        return ApplicationConfig.versionShort;
    }

    ///muste be generate unique directory name with current datetime + version i.e. (201205021355_ver1.0.0.1)
    private String generateDirName() throws IOException {
        String now = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String version = getApplicationVersion();
        return now + "_ver" + version;
    }

    private String makeDire() throws IOException {
        String dirName = generateDirName();
        logger.info("generated directory name " + dirName);
        String srcPath = ApplicationConfig.archivePath + dirName;
        Boolean result = (new File(srcPath)).mkdir();
        logger.info("create directory on path " + srcPath);
        if (result)
            return srcPath;
        else
           return null;
    }

    public static String createDirectory(){
        String result;
        try {
            logger.info("start create directory");
            ArchiveHandler archiveHandler = new ArchiveHandler();
            result = archiveHandler.makeDire() + ApplicationConfig.pathSplitterSign;
        } catch (IOException e) {
            result = null;
            logger.error("error on create directory message: \n" + e.getMessage());
        }
        return result;
    }
}
