package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CouponDAO;
import com.sefryek.doublepizza.service.ICouponService;

import java.util.List;

/**
 * Created by Mostafa.Jamshid on 1/21/15.
 */
public class CouponServiceImpl implements ICouponService {

    private CouponDAO couponDAO;

    public void setCouponDAO(CouponDAO couponDAO) {
        this.couponDAO = couponDAO;
    }

    public List findAll() {
        return couponDAO.findAll();
    }

}
