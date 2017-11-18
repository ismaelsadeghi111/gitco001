package com.sefryek.common.util;

import com.sefryek.doublepizza.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Created by sina.aliesmaily on 8/20/14.
 */
public abstract class SecurityUtils {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static boolean isAdmin() {
        User currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getUserRole() != null && StringUtils.isNotEmpty(currentUser.getUserRole().getAuthority())) {
            if (ROLE_ADMIN.equalsIgnoreCase(currentUser.getUserRole().getAuthority())) {
                return true;
            }
            return false;
        }
        return false; //@todo must check if user not exist
    }

    public static boolean isUser() {
        User currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getUserRole() != null && StringUtils.isNotEmpty(currentUser.getUserRole().getAuthority())) {
            if (ROLE_USER.equalsIgnoreCase(currentUser.getUserRole().getAuthority())) {
                return true;
            }
            return false;
        }
        return false; //@todo must check if user not exist
    }

    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }
}
