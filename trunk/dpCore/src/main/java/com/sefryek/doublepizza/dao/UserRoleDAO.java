package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.UserRole;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sina.aliesmaily on 8/17/14.
 */
public class UserRoleDAO extends BaseDAO{

    private static final Logger log = Logger.getLogger(UserRoleDAO.class);

    public static final String NAME = "name";

    public void save(UserRole transientInstance) throws DAOException {
        try {
            super.save(transientInstance);
        } catch (DAOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    public UserRole findByName(String name) {
        log.debug("finding user role by name");
        List list = super.findByPropertyEqualTo(UserRole.class, NAME, name);
        if(list != null && list.size() > 0)
            return (UserRole) list.get(0);
        return null;
    }

}
