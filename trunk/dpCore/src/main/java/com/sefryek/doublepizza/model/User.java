package com.sefryek.doublepizza.model;


import com.sefryek.doublepizza.core.Constant;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity()
@Table(name = Constant.USER_TBL_NAME)
public class User implements UserDetails {

    public enum UserType {
        INDIVIDUAL, CORPORATE
    }

    public enum PreferredLanguage {
        EN, FR
    }

    public enum Title {
        FEMALE, MALE
    }

    public enum OfficialTitle{
        Ms,Mr
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = Constant.USER_TITLE)
    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(name = Constant.USER_EMAIL)
    private String email;

    @Column(name = Constant.USER_FIRST_NAME)
    private String firstName;

    @Column(name = Constant.USER_LAST_NAME)
    private String lastName;

    @Column(name = Constant.USER_PASSWORD)
    private String password;

    @Column(name = Constant.USER_COMPANY)
    private String company;

  /*  @Column(name = Constant.USER_CITY)
    private String city;

    @Column(name = Constant.USER_POSTALCODE)
    private String postalCode;

    @Column(name = Constant.USER_STREET_NO)
    private String streetNo;

    @Column(name = Constant.USER_STREET_NAME)
    private String streetName;

    @Column(name = Constant.USER_SUITE_APT)
    private String suiteApt;

    @Column(name = Constant.USER_BUILDING)
    private String building;

    @Column(name = Constant.USER_DOOR_CODE)
    private String doorCode;*/

    @Column(name = Constant.USER_FACEBOOK_USERNAME)
    private String facebbookUsername;

    @Column(name = Constant.USER_TWITTER_USERNAME)
    private String twitterUsername;

/*    @Column(name = Constant.USER_PHONE)
    private String cellPhone;*/

/*    @Column(name = Constant.USER_EXT)
    private String ext;*/

    @Column(name = Constant.BIRHDATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "user", fetch = FetchType.EAGER, targetEntity = DpDollarHistory.class)
    private List<DpDollarHistory> dpDollarHistories;

    @Transient
    private String birthDateStr;

    @Column(name = Constant.USER_ISREGISTERED)
    private Boolean isRegistered = false;

    @Column(name = Constant.USER_SUBSCRIBED)
    private Boolean subscribed = true;

    @Column(name = Constant.USER_LANG)
    private String lang;


    @Column(name = "reg_from")
    private String regFrom;

    @Transient
    private Boolean reception;

    @Transient
    private OfficialTitle OfficaialTitle;

    @Transient
    private Double dpDollarBalance = new Double(00.00d);


    @OneToOne(fetch = FetchType.EAGER)

    @JoinColumn(name="user_role_id", updatable = true)
    private UserRole userRole;


    public User(Title title, String email, String firstName, String lastName, String password, String company,
                String facebbookUsername, String twitterUsername, /*String cellPhone,*/ String birthDateStr,String langStr,String subscribed,UserRole userRole) {

        this.title = title;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.company = company;
        this.birthDateStr =  birthDateStr;
        this.lang=langStr;
       /* this.city = city;
        this.postalCode = postalCode;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.suiteApt = suiteApt;
        this.building = building;
        this.doorCode = doorCode;*/
        this.facebbookUsername = facebbookUsername;
        this.twitterUsername = twitterUsername;
        this.userRole = userRole;
        if(subscribed.equals("True")){this.subscribed=true;}else {this.subscribed=false;}
//        this.cellPhone = cellPhone;
//        this.ext = ext;
    }


    public List<DpDollarHistory> getDpDollarHistories() {
        return dpDollarHistories;
    }

    public void setDpDollarHistories(List<DpDollarHistory> dpDollarHistories) {
        this.dpDollarHistories = dpDollarHistories;
    }

    public User() {
    }

    public Double getDpDollarBalance() {
        return dpDollarBalance;
    }

    public void setDpDollarBalance(Double dpDollarBalance) {
        this.dpDollarBalance = dpDollarBalance;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

  /*  public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   /* public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getSuiteApt() {
        return suiteApt;
    }

    public void setSuiteApt(String suiteApt) {
        this.suiteApt = suiteApt;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public GrantedAuthority[] getAuthorities() {
        if (getUserRole() != null) {
            return new GrantedAuthority[]{getUserRole()};
        }
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFacebbookUsername() {
        return facebbookUsername;
    }

    public void setFacebbookUsername(String facebbookUsername) {
        this.facebbookUsername = facebbookUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

/*    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }*/
/*
    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }*/

//    public String getDoorCode() {
//        return doorCode;
//    }

//    public void setDoorCode(String doorCode) {
//        this.doorCode = doorCode;
//    }

    public Boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getReception() {
        return reception;
    }

    public void setReception(Boolean reception) {
        this.reception = reception;
    }

//    public String getStreetName() {
//        return streetName;
//    }

//    public void setStreetName(String streetName) {
//        this.streetName = streetName;
//    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public User.OfficialTitle getOfficaialTitle() {
        return OfficaialTitle;
    }

    public void setOfficialTitle(User.OfficialTitle officaialTitle) {
        OfficaialTitle = officaialTitle;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getRegFrom() {
        return regFrom;
    }

    public void setRegFrom(String regFrom) {
        this.regFrom = regFrom;
    }

    public void setOfficaialTitle(OfficialTitle officaialTitle) {
        OfficaialTitle = officaialTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!email.equals(user.email)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
