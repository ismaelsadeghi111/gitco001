package com.sefryek.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 28, 2012
 * Time: 4:41:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationUtils {

    public static double getDistanceToKm(double lat1, double lat2, double lon1, double lon2){
//        formula
//        a = sin²(?lat/2) + cos(lat1).cos(lat2).sin²(?long/2)
//        c = 2.atan2(?a, ?(1?a))
//        d = R.c
        
        double R = 6371; // km
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d;
    }
}
