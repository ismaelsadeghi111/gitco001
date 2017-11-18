package com.sefryek.common.util.serialize;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 3, 2012
 * Time: 5:58:30 PM
 */
public class StringUtil {


    public static String shortify(String str, int maxLength) {

        if (str.length() < maxLength)
            return str;

        else {
            StringBuffer sb = new StringBuffer();
            sb.append(str.substring(0, maxLength - 3));
            sb.append("...");
            return sb.toString();

        }
    }

    public static String quotedString(String str) {
        return str.replace("\"", "``");
    }

    public static String removeSpaces(String str) {

        StringBuffer sb = new StringBuffer();

        int spaceIndex = str.indexOf(" ");

        while (spaceIndex != -1) {

            sb.append(str.substring(0, spaceIndex));
            sb.append(str.substring(spaceIndex + 1, str.length()));
            str = sb.toString();

            spaceIndex = str.indexOf(" ");

        }

        return str;
    }

    public static String extractIdFormEmailAddress(String emailAddress) {

        if (emailAddress.contains("@")) {
            return emailAddress.substring(0, emailAddress.indexOf("@"));

        } else {
            return emailAddress;
        }
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isDigit(char ch) {
        return ch >= 48 && ch <= 57;

    }

    public static boolean isCapitalLetter(char ch) {
        return ch >= 65 && ch <= 90;

    }

    public static String putZeroToNumber(String number, int digits){
        String result = number;
        int zeroCount = digits - result.length();
        for(int i = 0; i < zeroCount; i++){
            result = "0" + result;
        }
        return result;
    }

    public static String extractFileName(String url, String seprator){
        int slashIndex = url.lastIndexOf(seprator);
        int dotIndex = url.lastIndexOf('.', slashIndex);
        String filenameWithoutExtension;
        if (dotIndex == -1) {
            filenameWithoutExtension = url.substring(slashIndex + 1);
        } else {
            filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
        }
        return filenameWithoutExtension;
    }

}
