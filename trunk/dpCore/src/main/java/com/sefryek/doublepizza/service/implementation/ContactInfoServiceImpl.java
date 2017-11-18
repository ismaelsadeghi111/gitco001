package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.ContactInfoDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.ContactInfo;
import com.sefryek.doublepizza.service.IContactInfoService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: E_Ghasemi
 * Date: 1/28/14
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactInfoServiceImpl implements IContactInfoService {
    ContactInfoDAO contactInfoDAO;

    public void setContactInfoDAO(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public void save(ContactInfo contactInfo) {
        contactInfoDAO.save(contactInfo);
    }

    @Override
    public void update(ContactInfo contactInfo) {
        contactInfoDAO.update(contactInfo);
    }

    @Override
    public List<ContactInfo> getAll(Integer userId) {
        return contactInfoDAO.getAll(userId);
    }

}
