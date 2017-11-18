package com.sefryek.common.util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: May 13, 2012
 * Time: 4:39:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollectionsUtils {
    public static List top(List list, int top){
        List result = null;
        Integer counter = 0;

        result = new ArrayList();
        for (Object item : list) {
            result.add(item);
            counter++;
            if (counter.equals(top))
                break;
        }
        return result;
    }

    public static List union(List list1, List list2){
        Set union = new HashSet(list1);
        union.addAll(new HashSet(list2));
        List result = new ArrayList(union);
        return result;
    }
}
