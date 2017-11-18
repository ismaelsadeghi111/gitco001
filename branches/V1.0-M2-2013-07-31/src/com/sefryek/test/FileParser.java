package com.sefryek.test;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 15, 2012
 * Time: 3:15:45 PM
 */
public class FileParser {
    private static Integer counter = 0; 
    public static void formatFile(String fileName) {
        File file = new File(fileName);
        Map<String, String> mobileNumMap = new HashMap<String, String>();
        List<String> mobileArrayList = new ArrayList<String>();
        HashSet<String> mobileNumsHashSet = null;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            StringBuffer fileContent = new StringBuffer();
            fileContent.append(IOUtils.toString(bufReader));



            StringBuffer contentWithHyphenAsDelim1 = new StringBuffer(fileContent.toString().replace("\\,", "-"));
            StringBuffer contentWithHyphenAsDelim2 = new StringBuffer(contentWithHyphenAsDelim1.toString().replace("\\N", "-"));
            StringBuffer contentWithHyphenAsDelim3 = new StringBuffer(contentWithHyphenAsDelim2.toString().replace("\n", "-"));


            String[] elementArrays = contentWithHyphenAsDelim3.toString().split("-");

            for (String aString : elementArrays) {
                StringBuffer mobileN1 = new StringBuffer(aString);
                if (mobileN1.length() == 12) {
//                    mobileNumMap.put(mobileN1.toString(), mobileN1.toString());
                    mobileArrayList.add(mobileN1.toString());
                    System.out.println(counter++ + "- " + mobileN1.toString());
                }
            }


            File outputFile = new File("c:\\formated_mobile_nums_arraylist.cvs");

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }


            StringBuffer strBuf = new StringBuffer();
//            Set<String> mobileList = mobileNumMap.keySet();
            mobileNumsHashSet = new HashSet<String>(mobileArrayList);

            for (String aString : mobileArrayList) {
                strBuf.append(aString).append("\n");
            }

            FileWriter wirter = new FileWriter(outputFile);
            BufferedWriter bufWriter = new BufferedWriter(wirter);

            bufWriter.write(strBuf.toString());


        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch(IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String[] args) {
//        FileParser.formatFile("C:\\users.cvs");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("build.properties"));
            String ver = properties.getProperty("project.version");
            System.out.println(ver);
        } catch (IOException e) {
        }

    }


}
