package com.sefryek.doublepizza.device;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Mar 12, 2012
 * Time: 10:55:01 AM
 */
public interface ServicesConstant {
    // responses
    String SUCCESS = "OK";
    String UNKNOWN_ERROR  = "UnknownError";
    String VALIDATE_ERROR = "ValidateError";
    String INVALID_EMAIL = "InvalidEmail";
    String DUPLICATE_SUBSCRIBE ="DuplicateSubscribe"; 

    // data spliters
    String RECORD_DELIMITER = "#";
    String FIELD_DELIMETER = ";";
    String MULTI_VALUES_DELIMITER  = "@";

    // services indicator
    String METHOD_GET_BRANCHES = "getBranches";
    String METHOD_SET_FEEDBACK = "setFeedBack";
    String METHOD_SUBSCRIBE = "subscribe";
    String METHOD_IMAGE_DATE= "getImage";

    // recieved parameters
    String PARAMETER_METHOD = "method";
    String PARAMETER_NAME = "Name";
    String PARAMETER_EMAIL = "Email";
    String PARAMETER_REVIEW_NOTE="ReviewNote";
    String PARAMETER_ID = "ID";
    String PARAMETER_SOURCE="Source";
    String PARAMETER_FIRST_NAME = "FirstName";
    String PARAMETER_LAST_NAME = "LastName";
    String PARAMETER_VERSION = "Vesion";
    String PARAMETER_GENDER = "Gender";
    String PARAMETER_UDID = "UdId";
    String PARAMETER_DEVICVE_TOCKEN = "DeviceTocken";
    String PARAMETER_LATITUDE = "Latitude";
    String PARAMETER_LONITUDE = "Longitude";
    String PARAMETER_IMAGE_NAME = "ImageName";

    // default values
    String FEEDBACK_SENDER_iPHONE_SOURCE= "iPhone";
    String FEEDBACK_SENDER_ANDROID_SOURCE = "android";
    String NULL_VALUE = "0";
    String GENDER_MALE = "Male";
    String GENDER_FEMALE = "Female";

    int THRESHOLD_MAIL_MAX_LEN = 50;
    int THRESHOLD_NAME_MAX_LEN = 30;
    int THRESHOLD_STORE_MAX_LEN = 50;
    

}
