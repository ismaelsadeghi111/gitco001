package com.sefryek.common.util.serialize;

import org.apache.log4j.Logger;

import java.io.*;

import com.sefryek.doublepizza.core.LogMessages;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Feb 4, 2012
 * Time: 4:09:12 PM
 */
public class Serializer {
    private static final Logger logger = Logger.getLogger(Serializer.class);

    public static void writeObject(String name, Object object, String filePath) {
        logger.info(LogMessages.START_OF_METHOD + "writeObject");

        try {
            File rawFile = new File(filePath + name + ".ser");
            if (!rawFile.exists())
                rawFile.createNewFile();

            OutputStream file = new FileOutputStream(rawFile);
            BufferedOutputStream bufferedFile = new BufferedOutputStream(file);
            ObjectOutputStream objectFile = new ObjectOutputStream(bufferedFile);
            try {
                objectFile.writeObject(object);
            } finally {
                objectFile.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //writting "object" to a file( "name".ser )
    public static void writeObject(String name, Object object) {
        Serializer.writeObject(name, object, "C:\\");
    }

    //reading an object form a file ("name".ser)
    public static Object readObject(String name) {
        try {
//            File rawFile = new File("/home/doublepizzatest/ser/" + name + ".ser");
            File rawFile = new File("c:\\" + name + ".ser");
            InputStream file = new FileInputStream(rawFile);
            BufferedInputStream bufferedFile = new BufferedInputStream(file);
            ObjectInputStream objectFile = new ObjectInputStream(bufferedFile);
            try {
                return objectFile.readObject();

            } finally {
                objectFile.close();
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
