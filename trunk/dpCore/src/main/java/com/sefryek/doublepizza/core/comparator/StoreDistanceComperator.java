package com.sefryek.doublepizza.core.comparator;

import com.sefryek.common.util.LocationUtils;
import com.sefryek.doublepizza.model.Store;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 28, 2012
 * Time: 5:47:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreDistanceComperator implements Comparator {
    double currentLat;
    double currentLon;

    public StoreDistanceComperator(double currentLat, double currentLon){
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    public int compare(Object o1, Object o2) {
        Double lat1, lat2, lon1, lon2;
        Store store1 = (Store)o1;
        Store store2 = (Store)o2;

        lat1 = store1.getLatitude();
        lon1 = store1.getLongitude();

        if (lat1 == null || lon1 == null)
            return 1;

        lat2 = store2.getLatitude();
        lon2 = store2.getLongitude();

        if (lat2 == null || lon2 == null)
            return -1;        

        Double distance1 = LocationUtils.getDistanceToKm(currentLat, lat1, currentLon, lon1);
        Double distance2 = LocationUtils.getDistanceToKm(currentLat, lat2, currentLon, lon2);
        if (distance1 > distance2)
            return 1;
        else if (distance1 < distance2)
            return -1;
        else return 0;
    }
}
