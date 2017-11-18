package com.sefryek.doublepizza.dpdevice.services;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dpdevice.common.EmailValidator;
import com.sefryek.doublepizza.dpdevice.model.*;
import com.sefryek.doublepizza.dpdevice.servicebean.MenuServiceBean;
import com.sefryek.doublepizza.dto.*;
import com.sefryek.doublepizza.model.Store;
import com.sefryek.doublepizza.model.Suggestion;
import com.sefryek.doublepizza.model.Tax;
import com.sefryek.doublepizza.service.IMenuService;
import org.jboss.resteasy.annotations.GZIP;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/service")
public class MenuService {

    private IMenuService menuService;
    private static ToppingCategoryListItems publicToppingCategoryListItems;

    public MenuService(){
        publicToppingCategoryListItems = new ToppingCategoryListItems();
    }

    @POST
    @Path("/getMenu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createMenu(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<CheckedCategoryForDevice> menuSpecialList = new ArrayList<CheckedCategoryForDevice>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            } else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            } else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                menuSpecialList = menuServiceBean.getMenuItems(servletContext, "getMenu");
//                response = Response.status(200).entity(menuSpecialList).build();
                response = Response.ok(menuSpecialList).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getSpecial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createSpecial(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<CheckedCategoryForDevice> menuSpecialList = new ArrayList<CheckedCategoryForDevice>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                menuSpecialList = menuServiceBean.getMenuItems(servletContext, "getSpecial");
//                response = Response.status(200).entity(menuSpecialList).build();
                response = Response.ok(menuSpecialList).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getSubMenu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createSubMenu(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<CheckedSubCategoryForDevice> list = new ArrayList<CheckedSubCategoryForDevice>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuItems(servletContext, "getSubMenu");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getSubSpecial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createSubSpecial(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<CheckedSubCategoryForDevice> list = new ArrayList<CheckedSubCategoryForDevice>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }  else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }  else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuItems(servletContext, "getSubSpecial");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getAlternativesMenu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createAlternativesMenu(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<SubCategoryItemAlternatives> list = new ArrayList<SubCategoryItemAlternatives>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            } else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
              else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuAlternativesItems(servletContext, "getMenu");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getAlternativesSpecial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createAlternativesSpecial(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<SubCategoryItemAlternatives> list = new ArrayList<SubCategoryItemAlternatives>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuAlternativesItems(servletContext, "getSpecial");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

     @POST
     @Path("/getMenuSingleItemMenu")
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     @GZIP
     public Response createMenuSingleItemMenu(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<SubCategoryMenuSingleItem> list = new ArrayList<SubCategoryMenuSingleItem>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuSingleItems(servletContext, "getMenu");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getMenuSingleItemSpecial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createMenuSingleItemSpecial(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        List<SubCategoryMenuSingleItem> list = new ArrayList<SubCategoryMenuSingleItem>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                list = menuServiceBean.getSubMenuSingleItems(servletContext, "getSpecial");
//                response = Response.status(200).entity(list).build();
                response = Response.ok(list).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getMenuToppings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createMenuTopping(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;
//        List<ToppingCategoryListItems> list = new ArrayList<ToppingCategoryListItems>();

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        ToppingCategoryListItems toppingCategoryListItems = new ToppingCategoryListItems();
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
//                list = menuServiceBean.getMenuTopping(servletContext, "getMenu");
                if(publicToppingCategoryListItems.getToppingCategory() == null){
                    toppingCategoryListItems = menuServiceBean.getMenuTopping(servletContext, "getMenu");
                    publicToppingCategoryListItems = toppingCategoryListItems;
                }
                  response = Response.ok(publicToppingCategoryListItems).cacheControl(cc).build();
//                response = Response.ok().status(200).entity(publicToppingCategoryListItems).cacheControl(cc).build();
//                response = Response.status(200).entity(publicToppingCategoryListItems).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/getSpecialToppings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createSpecialTopping(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;
//        List<ToppingCategoryListItems> list = new ArrayList<ToppingCategoryListItems>();

        CacheControl cc = new CacheControl();
        cc.setSMaxAge(86400);

        ToppingCategoryListItems toppingCategoryListItems = new ToppingCategoryListItems();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
//                list = menuServiceBean.getMenuTopping(servletContext, "getSpecial");
                toppingCategoryListItems = menuServiceBean.getMenuTopping(servletContext, "getSpecial");
//                response = Response.status(200).entity(toppingCategoryListItems).build();
                response = Response.ok(toppingCategoryListItems).cacheControl(cc).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/LoginUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createLogin(@Context ServletContext servletContext, ReqLoginObjControl reqLoginObjControl) {

        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        UserForDevice userForDevice = null;

        try {
            if (reqLoginObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqLoginObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqLoginObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else if(reqLoginObjControl.getEmail() == null || reqLoginObjControl.getPassword() == null){
                response = Response.status(204).build();
            } else {
                userForDevice = menuServiceBean.loginByEmailAndPassword(servletContext, reqLoginObjControl.getEmail(), reqLoginObjControl.getPassword());
                if(userForDevice == null || userForDevice.getUserId() == 0){
                    response = Response.status(205).build();
                }else{
                    response = Response.status(200).entity(userForDevice).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/NewRegister")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createRegister(@Context ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj) {
        Response response = null;
        Date birthDate = new Date();
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        UserForDevice userForDevice = null;
        RegisterInfo registerInfo = null;
        String dateStr ="";
        if(reqRegisterInfoObj.getBirthday()!=null && reqRegisterInfoObj.getBirthday()!=""){
            dateStr=reqRegisterInfoObj.getBirthday().replaceAll("-", ":");  }
        EmailValidator emailValidator = new EmailValidator();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        sdf.setLenient(false);

        int userId = 0;

        try {
//            Date birthDate = sdf.parse(reqRegObjControl.getBirthday());
            if(!dateStr.equalsIgnoreCase("")){sdf.parse(dateStr);}
            if (reqRegisterInfoObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqRegisterInfoObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqRegisterInfoObj.getUdid() == null) {
                response = Response.status(203).build();
            } else if (reqRegisterInfoObj.getPassword().length() <= 4) {
                response = Response.status(205).build();
            } else if (!emailValidator.validate(reqRegisterInfoObj.getEmail())) {
                response = Response.status(208).build();
            } else if (!reqRegisterInfoObj.getTitle().equalsIgnoreCase("MALE") && !reqRegisterInfoObj.getTitle().equalsIgnoreCase("FEMALE")){
                response = Response.status(209).build();
            }else if (reqRegisterInfoObj.getFirstName().length() <= 1) {
                response = Response.status(211).build();
            } else if(reqRegisterInfoObj.getLastName().length() <= 1){
                response = Response.status(212).build();
            }else {
//                userForDevice = menuServiceBean.registerNewUser(servletContext, reqRegObjControl);
                registerInfo = menuServiceBean.registerNewUser(servletContext, reqRegisterInfoObj);
                if(registerInfo.getUserId() == -1) {
                    response = Response.status(207).build();
                } else {
                    response = Response.status(200).entity(registerInfo).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    @POST
    @Path("/DeliveryInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createDelivery(@Context ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj) {
        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
//        UserForDevice userForDevice = null;
        RegisterInfo registerInfo = null;
        EmailValidator emailValidator = new EmailValidator();
        int userId = 0;

        try {
            if (reqRegisterInfoObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqRegisterInfoObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqRegisterInfoObj.getUdid() == null) {
                response = Response.status(203).build();
            } else if (!emailValidator.validate(reqRegisterInfoObj.getEmail())) {
                response = Response.status(208).build();
            } else if (!reqRegisterInfoObj.getTitle().equalsIgnoreCase("MALE") && !reqRegisterInfoObj.getTitle().equalsIgnoreCase("FEMALE")){
                response = Response.status(209).build();
            } else if (reqRegisterInfoObj.getFirstName().length() <= 1) {
                response = Response.status(211).build();
            } else {
                registerInfo = menuServiceBean.deliveryNewUser(servletContext, reqRegisterInfoObj);
                if(registerInfo.getUserId() == -1) {
                    response = Response.status(207).build();
                } else if(registerInfo.getBirthday() == null && userId == 0){
                    response = Response.status(210).build();
                }else {
                    response = Response.status(200).entity(registerInfo).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    @POST
    @Path("/Order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createOrder(@Context ServletContext servletContext, ReqOrderObj reqOrderObj) {
        Response response = null;
        UserForDevice userForDevice = null;
        List<Suggestion> suggestionsList= new ArrayList<>();

        MenuServiceBean menuServiceBean = new MenuServiceBean();
        OrderInfo reqOrderObjInfo = null;
        try {
            if (reqOrderObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqOrderObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else {
                reqOrderObjInfo = menuServiceBean.createOrder(servletContext, reqOrderObj);
                if(reqOrderObjInfo.getDocNumber() == "" || reqOrderObjInfo.getDocNumber() == null) {
                    response = Response.status(207).build();
                }else {
                    response = Response.status(200).entity(reqOrderObjInfo).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("/AddAddress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createAddAddress(@Context ServletContext servletContext, ContactInfoReqObj contactInfoReqObj) {

        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        ContactInfoDevice contactInfoDevice = null;

        try {
            if (contactInfoReqObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(contactInfoReqObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (contactInfoReqObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                contactInfoDevice = menuServiceBean.addAddress(servletContext, contactInfoReqObj);
                response = Response.status(200).entity(contactInfoDevice).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    @POST
    @Path("/getBranches")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createBranches(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;
        List<Store> storeList = new ArrayList<Store>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                storeList = menuServiceBean.getAllStores(servletContext, "getAllStores");
                response = Response.status(200).entity(storeList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/getPopulars")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createPopulars(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;
        List<String> popularsList = new ArrayList<String>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                String language = "";
                popularsList = menuServiceBean.getPopulars(servletContext, "getPopulars");
                response = Response.status(200).entity(popularsList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @POST
    @Path("/getContactInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getContactInfo(@Context ServletContext servletContext, ContactInfoReqObj contactInfoReqObj) {

        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        ContactInfoDevice contactInfoDevice = null;
        List<ContactInfoDevice> contactInfoDeviceList =new ArrayList<>();

        try {
            if (contactInfoReqObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(contactInfoReqObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (contactInfoReqObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                contactInfoDeviceList = menuServiceBean.getAllContactInfo(servletContext, contactInfoReqObj);
                response = Response.status(200).entity(contactInfoDeviceList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
    @POST
    @Path("/getTax")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response createTax(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

        Response response = null;
        List<Tax> taxList = new ArrayList<Tax>();
        MenuServiceBean menuServiceBean = new MenuServiceBean();

        try {
            if (reqObjControl.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqObjControl.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                taxList = menuServiceBean.getTax(servletContext,"getTaxList");
                response = Response.status(200).entity(taxList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

  @POST
  @Path("/getDPDollarPercent")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @GZIP
    public Response createdpDollarsWeeklyList(@Context ServletContext servletContext, ReqObjControl reqObjControl) {

      Response response = null;
      List<Map<String, Float>> dpDollarsWeeklyList = new ArrayList<>();
      MenuServiceBean menuServiceBean = new MenuServiceBean();

      try {
          if (reqObjControl.getVersion() == null) {
              response = Response.status(201).build();
          }
          else if (Double.valueOf(reqObjControl.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
              response = Response.status(202).build();
          }
          else if (reqObjControl.getUdid() == null) {
              response = Response.status(203).build();
          } else {
              dpDollarsWeeklyList = menuServiceBean.getdpDollarsWeeklyList(servletContext, "getdpDollarsWeeklyList");
              response = Response.status(200).entity(dpDollarsWeeklyList).build();
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return response;
  }

    @POST
    @Path("/getDPDollar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getDPDollarPerUser(@Context ServletContext servletContext, ContactInfoReqObj contactInfoReqObj) {

        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        UserForDevice userForDevice = null;
        String uid="";
        uid=Integer.toString(contactInfoReqObj.getUserId());

        try {
            if (contactInfoReqObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(contactInfoReqObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (contactInfoReqObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                userForDevice  = menuServiceBean.getDPDollarPerUser(servletContext, uid);
                response = Response.status(200).entity(userForDevice).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
    @POST
    @Path("/getSuggestions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getSuggestions(@Context ServletContext servletContext, ReqOrderObj reqOrderObj) {
        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        List<SubCategoryForDevice> suggestionsList= new ArrayList<>();
        try {
            if (reqOrderObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqOrderObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqOrderObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                suggestionsList  = menuServiceBean.getSuggestions(servletContext, reqOrderObj);
                response = Response.status(200).entity(suggestionsList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
 @POST
 @Path("/getStreetCity")
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
 @GZIP
    public Response getStreetCity(@Context ServletContext servletContext, ReqStreetObj reqStreetObj) {
        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        CityAndStreetForDevice   cityAndStreetForDevice=new CityAndStreetForDevice();
        try {
            if (reqStreetObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqStreetObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqStreetObj.getUdid() == null) {
                response = Response.status(203).build();
            } else  {
                cityAndStreetForDevice  = menuServiceBean.getCityAndStreet(servletContext, reqStreetObj);
                if(cityAndStreetForDevice.getCity()==null || cityAndStreetForDevice.getStreet()==null )   {
                    response = Response.status(216).build();

                } else  {
                response = Response.status(200).entity(cityAndStreetForDevice).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
 @POST
 @Path("/getCoupon")
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
 @GZIP
    public Response getCoupon(@Context ServletContext servletContext, ReqCouponObj reqCouponObj) {
        Response response = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
     CouponForDevice  couponForDevice=new CouponForDevice();
        try {
            if (reqCouponObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqCouponObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqCouponObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                couponForDevice  = menuServiceBean.getCoupon(servletContext, reqCouponObj);
                response = Response.status(200).entity(couponForDevice).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @POST
    @Path("/EditProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response editProfile(@Context ServletContext servletContext, ReqRegisterInfoObj reqRegisterInfoObj) {
        Response response = null;
        RegisterInfo registerInfo = null;
        MenuServiceBean menuServiceBean = new MenuServiceBean();
        CouponForDevice  couponForDevice=new CouponForDevice();
        try {
            if (reqRegisterInfoObj.getVersion() == null) {
                response = Response.status(201).build();
            }
            else if (Double.valueOf(reqRegisterInfoObj.getVersion())<Double.valueOf(Constant.APP_VERSION) ) {
                response = Response.status(202).build();
            }
            else if (reqRegisterInfoObj.getUdid() == null) {
                response = Response.status(203).build();
            } else {
                registerInfo  = menuServiceBean.editProfile(servletContext, reqRegisterInfoObj);

                response = Response.status(200).entity(registerInfo).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
