package com.sefryek.doublepizza.security;

import com.sefryek.doublepizza.dao.UserDAO;
import com.sefryek.doublepizza.dao.UserRoleDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.UserRole;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.service.implementation.UserServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import org.springframework.security.userdetails.*;


import java.util.HashSet;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: sina.aliesmaily
 * Date: 8/16/14
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class DoublePizaaDetailService implements UserDetailsService {

    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {

        com.sefryek.doublepizza.model.User dpUser = null;
        com.sefryek.doublepizza.model.User dpUserByPass = null;
        String pass = null;
        try {
            dpUser = userService.findByEmailAndIsRegistered(email, true);
        }catch (DAOException ex){
            ex.printStackTrace();
        }catch (ServiceException ex){
            ex.printStackTrace();
        }

        if(dpUser != null){
         pass = dpUser.getPassword();
        }



        if(pass != null){
            try {
                dpUserByPass = userService.findByEmailAndPassword(email,pass);
            } catch (DAOException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }


        if (dpUserByPass == null) {
            throw new UsernameNotFoundException(email);
        }
//        GrantedAuthority[] authorities = buildUserAuthority(dpUserByPass.getUserRole());
//        return buildUserForAuthentication(dpUserByPass, authorities);
        return dpUserByPass;
    }

//    @Override
//    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
//
//        com.sefryek.doublepizza.model.User dpUser;
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//      dpUser = userDAO.findByEmailAndPassword(token.getName(),token.getCredentials().toString());
//
//        if (dpUser == null) {
//            throw new UsernameNotFoundException(token.getName());
//        }
//     GrantedAuthority[] authorities = buildUserAuthority(dpUser.getUserRoles());
//        return buildUserForAuthentication(dpUser, authorities);  //To change body of implemented methods use File | Settings | File Templates.
//    }

    private User buildUserForAuthentication(com.sefryek.doublepizza.model.User dpUserbyPass,
                                            GrantedAuthority[] authorities) {
        return new User(dpUserbyPass.getEmail(), dpUserbyPass.getPassword(),
                 true, true, true,true, authorities);
    }

    private GrantedAuthority[] buildUserAuthority(UserRole userRole) {

        Set<GrantedAuthority> setAuths = new HashSet<>(8);
        if (userRole == null) {
            return null;
        }


            setAuths.add(new GrantedAuthorityImpl(userRole.getName()));




        return setAuths.toArray(new GrantedAuthority[] {});
    }






//    private GrantedAuthority[] getAuthorities(boolean isAdmin) {
//        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
//        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
//        if (isAdmin) {
//            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
//        }
//        return authList.toArray(new GrantedAuthority[] {});
//    }
}
