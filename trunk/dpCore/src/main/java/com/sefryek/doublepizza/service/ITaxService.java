package com.sefryek.doublepizza.service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 3, 2012
 * Time: 4:02:50 PM
 */
public interface ITaxService {
    public static String BEAN_NAME = "taxService";
    public List findAll();
}
