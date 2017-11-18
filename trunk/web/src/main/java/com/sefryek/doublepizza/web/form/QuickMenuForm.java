package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;
import com.sefryek.doublepizza.model.SubCategory;
import java.util.ArrayList;
import java.util.ListIterator;
import com.sefryek.doublepizza.model.Category;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 12/28/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuickMenuForm extends ValidatorForm {
    List<Category> quickMenuSpecialList = new ArrayList<Category>();

    public List<Category> getQuickMenuSpecialList() {
        return quickMenuSpecialList;
    }

    public void setQuickMenuSpecialList(List<Category> quickMenuSpecialList) {
        this.quickMenuSpecialList = quickMenuSpecialList;
    }



}
