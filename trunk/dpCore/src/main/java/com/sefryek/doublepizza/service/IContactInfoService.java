package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.ContactInfo;

import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 1/28/14
 * Time: 9:11 AM
 */
public interface IContactInfoService {
    public static String BEAN_NAME = "contactInfoService";

    public void save(ContactInfo contactInfo)  throws DAOException;

    public void update(ContactInfo contactInfo);

    public List<ContactInfo> getAll(Integer userId);
}
