package com.sefryek.doublepizza.service;

import java.util.List;

/**
 * User: Mostafa
 * Date: 01/21/15
 * Time: 9:56 AM
 */
public interface ICouponService {
    public static String BEAN_NAME = "couponService";
    public List findAll();

}
