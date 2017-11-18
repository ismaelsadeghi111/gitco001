package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.InMemoryData;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
            stepTax = (stepPrice.multiply(new BigDecimal(taxRules.get(i).getPercentage()))).divide(new BigDecimal(100));
            stepPrice = stepPrice.add(stepTax);
            
        }

        return stepTax;
    }

    public BigDecimal getTotalPriceWithTax(BigDecimal subtotal){

        List<Tax> taxRules = InMemoryData.getTaxList();

        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.setScale(2, RoundingMode.DOWN);
        BigDecimal stepTax = new BigDecimal(0);

        for (int i = 0; i < taxRules.size(); i++) {
           stepTax = getStepTax(subtotal, i);
           totalPrice = totalPrice.add(stepTax);

        }

        return totalPrice;
    }
}
