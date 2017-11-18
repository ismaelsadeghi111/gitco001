package com.sefryek.doublepizza.web.form;

import com.sefryek.doublepizza.model.DpDollarHistory;
import org.apache.struts.validator.ValidatorForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/10/14.
 */
public class DpDollarsHistoryForm  extends ValidatorForm {
    private List<DpDollarHistory> dpDollarHistories = new ArrayList<DpDollarHistory>();

    public List<DpDollarHistory> getDpDollarHistories() {
        return dpDollarHistories;
    }

    public void setDpDollarHistories(List<DpDollarHistory> dpDollarHistories) {
        this.dpDollarHistories = dpDollarHistories;
    }
}
