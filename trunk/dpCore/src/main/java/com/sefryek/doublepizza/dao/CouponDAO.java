package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.model.Coupon;

import java.util.List;

/**
 * Created by Mostafa.Jamshid on 1/21/15.
 */
public class CouponDAO extends BaseDAO {

    public List<Coupon> findAll(){
        return (List<Coupon>)super.findAll(Coupon.class);
    }
}
