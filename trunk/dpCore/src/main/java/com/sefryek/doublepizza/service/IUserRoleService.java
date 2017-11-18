package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.model.UserRole;

/**
 * Created by sina.aliesmaily on 8/17/14.
 */
public interface IUserRoleService {

    public static String BEAN_NAME = "userRoleService";


    public enum UserRoleName {
        ROLE_USER,ROLE_ADMIN
    }

    public UserRole findByName(UserRoleName userRoleName);
}
