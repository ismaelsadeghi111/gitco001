package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.InMemoryData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 4, 2012
 * Time: 4:04:38 PM
 */
public class TaxHandler {

    public BigDecimal getStepTax(BigDecimal subTotal, int step){

        List <Tax> taxRules = InMemoryData.getTaxList();
        BigDecimal stepPrice = new BigDecimal(0);
        stepPrice = stepPrice.add(subTotal);
        BigDecimal stepTax = new BigDecimal(0);

        for (int i = 0; i <= step; i++){
            stepTax =BigDecimal.valueOf((subTotal.doubleValue()) * (taxRules.get(i).getPercentage() / 100));
        }

        return stepTax;
    }

    public BigDecimal getTotalPriceWithTax(BigDecimal subtotal){

        List<Tax> taxRules = InMemoryData.getTaxList();

        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        BigDecimal stepTax = new BigDecimal(0);

        for (int i = 0; i < taxRules.size(); i++) {
           stepTax = getStepTax(subtotal, i);
           stepTax = stepTax.setScale(2, RoundingMode.HALF_UP);
           totalPrice = totalPrice.add(stepTax);

        }

        return totalPrice;
    }
}
