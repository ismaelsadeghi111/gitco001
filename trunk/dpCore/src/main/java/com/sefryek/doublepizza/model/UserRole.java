package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.Constant;
import org.springframework.security.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;



/**
 * Created with IntelliJ IDEA.
 * User: sina.aliesmaily
 * Date: 8/16/14
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@javax.persistence.Table(name = Constant.ROLE_USER_TBL_NAME)
public class UserRole implements GrantedAuthority {


    public UserRole() {
    }
    public UserRole(Integer roleId,String name, String description,Date createDate,Date ceasedDate) {
        this.roleId = roleId;
        this.name = name;
        this.description =description;
        this.createDate = createDate;
        this.ceasedDate =ceasedDate;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Constant.USER_ROLE_ID)
    private Integer roleId;
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }





    @Column(name = Constant.USER_ROLE_NAME)
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Column(name = Constant.USER_ROLE_DESCRIPTION)
    private String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = Constant.USER_ID_REF, nullable = false)
//    private User user;
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


    @Column(name = Constant.ROLE_CEASED_DATE)
    private Date ceasedDate;
    public Date getCeasedDate() {
        return ceasedDate;
    }



    @Column(name = Constant.ROLE_CREATE_DATE)
    private Date createDate;
    public Date getCreateDate() {
        return createDate;
    }


    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        if (this.equals(o)) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;

        UserRole userRole = (UserRole) o;

        if (!name.equals(userRole.name)) return false;
        if (!roleId.equals(userRole.roleId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
