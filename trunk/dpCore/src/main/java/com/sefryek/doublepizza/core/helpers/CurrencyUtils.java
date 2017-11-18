package com.sefryek.doublepizza.core.helpers;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Feb 23, 2012
 * Time: 9:49:53 AM
 */
public class CurrencyUtils {

    public static String toMoney(BigDecimal value){

//        return "$" + value.setScale(2).toString();
        return "$" + value.setScale(2, RoundingMode.HALF_UP).toString();
    }
    public static String toMoney(Number value){

        return "$" + (BigDecimal.valueOf(value.doubleValue()).setScale(2, RoundingMode.HALF_UP).toString());
    }

    public static Double doubleRoundingFormat(Number value){
        return Double.valueOf(BigDecimal.valueOf(value.doubleValue()).setScale(2, RoundingMode.HALF_UP).toString());
    }

    public static String roundTwoDecimals(Number value) {
        DecimalFormat df = new DecimalFormat("0.00");
        String formate = df.format(value);
        return formate;
    }

    public static String toMoneyWithoutDollarSign(BigDecimal value){
        return value.setScale(2).toString();        
    }

    public static String getMoneyWholePart(BigDecimal money){
        return String.valueOf(money.intValue());
    }

    public static String getMoneyFractionalPart(BigDecimal money){
        BigDecimal result = money.subtract(new BigDecimal(money.intValue()));
        String str = result.setScale(2).toString();
        return str.replace("0.", "");
    }
}
