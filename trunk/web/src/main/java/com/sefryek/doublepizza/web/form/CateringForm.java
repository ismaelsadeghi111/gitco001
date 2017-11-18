package com.sefryek.doublepizza.web.form;

import com.sefryek.doublepizza.model.Catering;
import com.sefryek.doublepizza.model.CateringContactInfo;
import com.sefryek.doublepizza.model.CateringOrder;
import org.apache.struts.validator.ValidatorForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 12/28/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CateringForm extends ValidatorForm {
    private List<Catering> caterings = new ArrayList<Catering>();
    private CateringOrder cateringOrder= new CateringOrder();
    private CateringContactInfo cateringContactInfo;

    public CateringContactInfo getCateringContactInfo() {
        return cateringContactInfo;
    }

    public void setCateringContactInfo(CateringContactInfo cateringContactInfo) {
        this.cateringContactInfo = cateringContactInfo;
    }

    public CateringOrder getCateringOrder() {
        return cateringOrder;
    }

    public void setCateringOrder(CateringOrder cateringOrder) {
        this.cateringOrder = cateringOrder;
    }

    public void setCatreing(Catering catreing) {
        this.catreing = catreing;
    }

    private Catering catreing = new Catering();

    public  Catering getCatering(){
        return catreing;
    }

    public List<Catering> getCaterings() {
        return caterings;
    }

    public void setCaterings(List<Catering> caterings) {
        this.caterings = caterings;
    }


}
