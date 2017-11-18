package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.UserRoleDAO;

import com.sefryek.doublepizza.model.UserRole;
import com.sefryek.doublepizza.service.IUserRoleService;

/**
 * Created by sina.aliesmaily on 8/17/14.
 */
public class UserRoleServiceImpl implements IUserRoleService{


    private UserRoleDAO userRoleDAO;


    @Override
    public UserRole findByName(UserRoleName userRoleName) {

        return userRoleDAO.findByName(userRoleName.name());
    }


    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }
}
