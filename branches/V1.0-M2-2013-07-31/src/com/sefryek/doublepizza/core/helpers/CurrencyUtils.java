package com.sefryek.doublepizza.core.helpers;

import com.sefryek.common.config.ApplicationConfig;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Feb 23, 2012
 * Time: 9:49:53 AM
 */
public class CurrencyUtils {

    public static String toMoney(BigDecimal value){

//        return "$" + value.setScale(2).toString();
        return "$" + value.setScale(2, RoundingMode.DOWN).toString();
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
