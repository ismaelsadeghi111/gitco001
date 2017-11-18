package com.sefryek.doublepizza.core;

public interface Constant {

    public static final String NO_IMAGE_NAME = "no_image.jpg";

    public static final Integer MAX_LENGTH_CATEGORY_NAME = 20;

    public static final String NO_DELIVERY_CODE = "099";

    public static final Integer MAX_POSTAL_CODE_LENGTH = 6;

    public static final Integer VALUE_IS_FORCED = 1;
    public static final Integer VALUE_IS_NOT_FORCED = 0;

    public static final Integer VALUE_CUSTOMIZING_BASKET_ITEM_MODE = 1;
    public static final Integer VALUE_NOT_CUSTOMIZING_BASKET_ITEM_MODE = 0;

    public static final String TOPPING_CATEGORY_CRUST = "crust";
    public static final String TOPPING_CATEGORY_COOKING_MODE = "cookingmode";

    public static final String STORE = "store";

    public static final String STORE_ID = "storeId";

    public static final int MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE = 20;
    public static final int MAXIMUM_CHARACTERS_OF_TOPPIMGS = 30;

    public final static int SINGLEITEM_PRODLINK_SEQ = 1;

    public final static int COMBINED_DEFAULT_ITEM_SEQUENCE = 1;

    public final static String ITEM_TYPE_PIZZA = "pizza";

    public final static String REGISTERATION_OPERAION = "register";

    public final static String OPERAION = "operation";

    public final static String SIZE_TOPPING = "topping";

    public final static String TOTAL_BASKET_ITEM = "total_basket_item";

    public final static String TOTAL_BASKET_PRICE = "total_basket_price";

    public final static String LATEST_USER_URL = "latestURL";

    public final static String REGISTER_OR_LOGIN = "registerOrLogin";

    public final static String LOGIN_URL = "/frontend.do?operation=goToLoginPage";

    public final static String VISIBILITY_VALUE = "1";

    public final static int DEFAULT_SELECTED_CATEGORY_INDEX = 0;

    public final static int ALTERNATIVE_FIRST_ITEM = 0;

    public final static int DEFAULT_SELECTED_CITY_INDEX = 0;

    public final static int EXCLUSIVE_TOPPING_CATEGORY_DEFAULT_ITEM_NUMBER = 3;

    public final static int EXCLUSIVE_DEFAULT_ITEM = 1;

    public final static String STORE_CITY = "storeCityList";
    public final static String CITY_ID_PARAM = "cityId";
    public final static String STORE_LIST = "storeList";
    public final static String STORE_LIST_PICKUP = "storeListPickup";
    public final static String POSTAL_CODE_NOT_FOUND = "postalCodeNotFound";

    //google Map
    public final static String LATITUDE = "latitude";
    public final static String LONGITUDE = "longitude";

    //alternatives Strings
    public final static String NAME_ALTERNATIVE = "Unknown";
    public final static String DESC_ALTERNATIVE = "";
    public final static String SEQ_ALTERNATIVE = "0";
    public final static String WEBSTATUS_ALTERNATIVE = "-1";
    public final static String SECONDID_ALTERNATIVE = "";
    public final static String ALTERNATIVE_ALL_CITIES = "All Cities";

    //tables Info
    //------WebCategory columns
    public final static String WEBCATEGORIES_ID = "CategId";
    public final static String WEBCATEGORIES_NAME_EN = "TitleEN";
    public final static String WEBCATEGORIES_NAME_FR = "TitleFR";
    public final static String WEBCATEGORIES_DESCRIPTION_EN = "DescEN";
    public final static String WEBCATEGORIES_DESCRIPTION_FR = "DescFR";
    public final static String WEBCATEGORIES_SEQUENCE = "CategSeq";
    public final static String WEBCATEGORIES_PICTURE = "Picture";
    public final static String WEBCATEGORIES_STATUS = "CategStatus";

    //keys
    public final static String WEBCATEGORIES_NAME_KEY = "title";
    public final static String WEBCATEGORIES_DESCRIPTION_KEY = "desc";

    public final static String TOPPING_IDENTIFIER_STRING = "topping";

    //------StoreMast columns
    public final static String STOREMAST_ID = "Storenum";
    public final static String STOREMAST_NAME = "WebStoreName";
    public final static String STOREMAST_CITY = "City";
    public final static String STOREMAST_ADDRESS1 = "Address1";
    public final static String STOREMAST_ADDRESS2 = "Address2";
    public final static String STOREMAST_PHONE = "Phone";
    public final static String STOREMAST_POSTALCODE = "pc";
    public final static String STOREMAST_IMAGEURL = "ImageUrl";
    public final static String STOREMAST_WEBSTATUS = "WebStatus";

    //------Pcods columns
    public final static String PCODES_POSTAL_CODE = "POSTAL_CODE";
    public final static String PCODES_LATITUDE = "LATITUDE";
    public final static String PCODES_LONGITUDE = "LONGITUDE";

    //------StoreHours columns
    public final static String STOREHOURS_ID = "store_num";
    public final static String STOREHOURS_DAY_NAME_EN = "day_name_en";
    public final static String STOREHOURS_DAY_NAME_FR = "day_name_fr";
    public final static String STOREHOURS_DAY_ID = "day_id";
    public final static String STOREHOURS_OPENING_HOURS = "opening_hours";
    public final static String STOREHOURS_CLOSING_HOURS = "closing_hours";

    //------Term columns
    public final static String TERM_ID = "id";
    public final static String TERM_NAME_EN = "name_en";
    public final static String TERM_NAME_FR = "name_fr";
    public final static String TERM_DESCRIPTION_FR = "desc_fr";
    public final static String TERM_DESCRIPTION_EN = "desc_en";
    public final static String TERM_IMAGE = "image";

    //keys
    public final static String TERM_NAME_KEY = "name";
    public final static String TERM_DESCRIPTION_KEY = "desc";

    //------Topping_Category columns
    public final static String TOPPING_CATEGORY_ID = "id";
    public final static String TOPPING_CATEGORY_TITLE_EN = "title_en";
    public final static String TOPPING_CATEGORY_TITLE_FR = "title_fr";
    public final static String TOPPING_CATEGORY_URL = "url";
    public final static String TOPPING_CATEGORY_IMAGE_NAME = "image_Name";
    public final static String TOPPING_CATEGORY_WEB_SEQUENCE = "WebSeq";

    //keys
    public final static String TOPPING_CATEGORY_NAME_KEY = "title";
    public final static String TOPPING_CATEGORY_DESCRIPTION_KEY = "desc";

    //------MenuItem  columns
    public final static String MENUITEM_PRODUCTNO = "ProductNo";
    public final static String MENUITEM_GROUPID = "GroupID";
    public final static String MENUITEM_PRODUCTNAME = "ProductName";
    public final static String MENUITEM_PRODUCTNAME1 = "ProductName1";
    public final static String MENUITEM_DESCRIPTION = "Description";
    public final static String MENUITEM_DESCRIPTION2 = "Description2";
    public final static String MENUITEM_SEQUENCE = "Seq";
    public final static String MENUITEM_CATEGORY = "category";
    public final static String MENUITEM_WEBIMAGE = "WebImage";
    public final static String MENUITEM_PRICE = "Price";
    public final static String MENUITEM_FREEMODS = "Freemods";
    public final static String MENUITEM_FREEMODPRICE = "Freemodprice";
    public final static String MENUITEM_PORTION = "Portions";
    public final static String MENUITEM_SIZE = "Size";
    public final static String MENUITEM_NUMTOPPING = "NumToppings";
    public final static String MENUITEM_PRESET = "Preset";
    public final static String MENUITEM_TWOFORONE = "Twoforone";
    public final static String MENUITEM_NAME_EN = "WebDescEN";
    public final static String MENUITEM_NAME_FR = "WebDescFR";
    public final static String MENUITEM_DESCRIPTION_EN = "Description_en";
    public final static String MENUITEM_DESCRIPTION_FR = "Description_fr";
    public final static String MENUITEM_NO_PRINT = "Noprint";
    public final static String MENUITEM_WEB_STATUS = "WebStatus";
    public final static String MENUITEM_SPECIAL = "Special";

    //keys
    public final static String MENUITEM_NAME_KEY = "webDesc";
    public final static String MENUITEM_DESCRIPTION_KEY = "description";

    //------Modifier  columns
    public final static String MODIFIER_MODIFIERID = "ModifierID";
    public final static String MODIFIER_MODGROUPNO = "ModGroupNo";
    public final static String MODIFIER_MODENAME = "ModName";
    public final static String MODIFIER_IMAGE = "Image";
    public final static String MODIFIER_PRICE = "Price";
    public final static String MODIFIER_FREEMODS = "Freemods";
    public final static String MODIFIER_FREEMODPRICE = "Freemodprice";
    public final static String MODIFIER_TWOFORONE = "Twoforone";
    public final static String MODIFIER_PORTION = "Portions";
    public final static String MODIFIER_NUMTOPPING = "NumToppings";
    public final static String MODIFIER_NAME_EN = "WebDescEN";
    public final static String MODIFIER_NAME_FR = "WebDescFR";
    public final static String MODIFIER_DESCRIPTION_EN = "WebDescEN";
    public final static String MODIFIER_DESCRIPTION_FR = "WebDescFR";
    public final static String MODIFIER_ITEM_TYPE = "item_Type";
    public final static String MODIFIER_SIZE = "Size";
    public final static String MODIFIER_WEBSEQ = "webSeq";
    public final static String MODIFIER_WEBSTATUS = "WebStatus";
    public final static String MODIFIER_ISFOOD = "Is_Food";
    public final static String MODIFIER_FULL_DESCRIPTION = "FullDescription";
    public final static String MODIFIER_FULL_DESCRIPTION2 = "FullDescription2";    
    //keys
    public final static String MODIFIER_NAME_KEY = "webDesc";
    public final static String MODIFIER_DESCRIPTION_KEY = "webDesc";

    //------default sequence number for menuSingleItem to specify default menuSingleItem for each combinedMenuItem.
    public final static String SEQUENCE_DEFAULT_SINGLEITEM = "1";
    //------Preset columns
    public final static String PRESET_BASEITEM = "baseitem";
    public final static String PRESET_ITEMNUMBER = "ItemNumber";
    public final static String PRESET_MODGROUPNO = "ModGroupNo";
    public final static String PRESET_PRESET_SEQUENCE = "Presetseq";

    //------Prodlink columns
    public final static String PRODLINK_TABLE = "ProdLink.";
    public final static String PRODLINK_PRODUCTNO = "ProductNo";
    public final static String PRODLINK_PRESETSEQ = "Presetseq";
    public final static String PRODLINK_MODGROUPNO = "ModGroupNo";
    public final static String PRODLINK_SEQ = "Seq";
    public final static String PRODLINK_FORCED = "Forced";
    public final static String PRODLINK_PRICE = "Price";

    //------modlink columns
    public final static String MODLINK_MODIFIERID = "ModifierID";
    public final static String MODLINK_MODGROUPNO = "ModGroupNo";
    public final static String MODLINK_PRICE = "Price";
    public final static String MODLINK_FREEMODS = "Freemods";
    public final static String MODLINK_FREEMODPRICE = "Freemodprice";
    public final static String MODLINK_TWOFORONE = "Twoforone";
    public final static String MODLINK_PORTIONS = "Portions";
    public final static String MODLINK_SEQ = "Seq";

    //------group_term columns
    public final static String GROUP_TERM_TERM_ID = "term_id";
    public final static String GROUP_TERM_GROUP_ID = "group_id";

    //------Contents columns
    public final static String CONTENTS_MODTOFIND = "ModToFind";
    public final static String CONTENTS_MODIFIERID = "Modifierid";
    public final static String CONTENTS_PRODUCTNO = "Productno";


    //------WebMenuItem columns
    public final static String WEBMWNUITEM_CATEGID = "CategId";
    public final static String WEBMWNUITEM_PRODUCTNO = "ProductNo";
    public final static String WEBMWNUITEM_PRODSEQ = "ProdSeq";

    //------
    public static enum TABLE_LIST {
        WebCategories, MenuItem, Modifier, Term, WebMenuItem, web_feedback, web_user, Pcodes, ProdLink, ModLink, Preset,
        Contents, group_term, Topping_category, Storemast, StoreHours, User
    }


    public static enum JOIN_TABLE_LIST {
        groupTerm_menuItem_JoinTBL, webMenuItem_menuItem_JoinTBL,
        modifier_preset_modLink_JoinTBL, prodLink_modLink_modifier_menuItem_JoinTBL, Modifier_Contents_JoinTBL,
        Modifier_Preset_JoinTBL
    }

    public static final String IN_MEMORY_DATA_LOADR_JOB_NAME = "DOUBLE_PIZZA_QUARTZ_JOB_NAME";
    public static final String IN_MEMORY_DATA_LOADR_TASK_NAME = "DOUBLE_PIZZA_QUARTZ_TASK_NAME";
    public static final String IN_MEMORY_DATA_LOADR_CRON_NAME = "DOUBLE_PIZZA_QUARTZ_NAME";
    //    public static final String IN_MEMORY_DATA_LOADR_CRON_EXPRESSION = "59 * * * * ? *";
    public static final String IN_MEMORY_DATA_LOADR_CRON_EXPRESSION = "0 0 3 * * ? *";

    public static final String ORDER_MENU_NAME = "menu";
    public static final String SPECIAL_MENU_NAME = "special";
    public static final String MENU_NAME = "menuName";
    public static final String CATEGORYID = "catId";

    public static final String BASKET = "basket";
    public static final String ORDER = "order";
    public static final String LAST_CATEGORY = "lastCategory";
    public static final String LAST_SUB_CATEGORY = "lastSubCategory";
    public static final String LAST_SINGLE_ITEM = "lastSingleItem";
    public static final String LAST_COMBINED_ITEM = "lastCombinedItem";
    public static final String LAST_SELECTED_ITEM_TYPE = "lastSelectedItemType";    
    public static final String LAST_ORDER_DELIVERY_TYPE = "lastOrderDeliverytype";
    public static final String LAST_ORDER_STORE = "lastOrderStore";

    //selected menu item selectedCityItem
    public static final String Selected_Menu_Item_Att_Name = "selectedMenuItem";
    public static final String Selected_City_Item = "selectedCityItem";
    public static final String ALTERNATIVE_CITY_NAME = "City";
    public static final String Menu_Item_Home = "menu_item_home";
    public static final String Menu_Item_Store_Locator = "menu_item_storeLocator";
    public static final String Menu_Item_Franchising = "menu_item_franchising";
    public static final String Menu_Item_AboutUs = "menu_item_menu_aboutUs";
    public static final String Menu_Item_ContactUs = "menu_item_contactUs";
    public static final String Menu_Item_Lang = "menu_item_lang";
    public static final String SERIALIZED_OBJ_NAME = "obj";
    public static final String SINGLE_ITEM_SMALL = "small";
    public static final String SINGLE_ITEM_MEDUIM = "Medium";
    public static final String SINGLE_ITEM_LARGE = "Large";
    public static final String LOGIN_OR_REGIISTER_SOURCE = "login";

    //combined in session
    public static final String BASKET_COMBINED_IN_SESSION = "basketCombinedSessionObj";
    public static final String REGISTRATION_INFO_IN_SESSION = "registrationInfoInSession";
    public static final String IN_SESSION_REVIEW_SUGGESTION = "REVIEW_SUGGESTION";
    public static final String CUSTOMIZING_BASKET_ITEM = "CustomizingBasketItem";

    public static final String TITLE_MR = "Mr.";
    public static final String TITLE_MS = "Ms.";

    //checkout in session
    public final static String CHECK_OUT_USER_IN_SESSION = "check_out_user";
    public final static String NEAR_STORE_IN_SESSION = "near_store";
    public final static String USER_IS_TRUSTED_FOR_CHECKOUT = "userIsTrustedForCheckout";

    //user registration
    public final static String USER_TBL_NAME = "web_user";
    public static final String USER_ID = "id";
    public static final String USER_TITLE = "Title";
    public static final String USER_FIRST_NAME = "FirstName";
    public static final String USER_LAST_NAME = "LastName";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_COMPANY = "Company";
    public static final String USER_STREET_NO = "StreetNo";
    public static final String USER_STREET_NAME = "StreetName";
    public static final String USER_SUITE_APT = "SuitApt";
    public static final String USER_BUILDING = "Building";
    public static final String USER_DOOR_CODE = "DoorCode";
    public static final String USER_CITY = "City";
    public static final String USER_POSTALCODE = "PostalCode";
    public static final String USER_PHONE = "Phone";
    public static final String USER_EXT = "Ext";
    public static final String USER_FACEBOOK_USERNAME = "Facebook";
    public static final String USER_TWITTER_USERNAME = "Twitter";

    //Subscriber
    public final static String SUBSCRIBER_TBL_NAME = "Subscribers";
    public final static String SUBSCRIBER_ID = "Id";
    public final static String SUBSCRIBER_VERSION = "Version";
    public final static String SUBSCRIBER_UDID = "Udid";
    public final static String SUBSCRIBER_DEVICE_TOCKEN = "DeviceTocken";
    public final static String SUBSCRIBER_DEVICE_TYPE = "DeviceType";
    public final static String SUBSCRIBER_LATITUDE = "Latitude";
    public final static String SUBSCRIBER_LONGITUDE = "Longitude";
    public final static String SUBSCRIBER_EMAIL = "Email";
    public final static String SUBSCRIBER_NAME = "Name";
    public final static String SUBSCRIBER_GENDER = "Gender";
    public final static String SUBSCRIBER_RECEPTION = "Reception";

    //------StoreMast columns
    public final static String FEEDBACK_TBL_NAME = "web_feedback";
    public final static String FEEDBACK_ID = "Id";
    public final static String FEEDBACK_NAME = "Name";
    public final static String FEEDBACK_EMAIL = "Email";
    public final static String FEEDBACK_MESSAGE = "Message";
    public final static String FEEDBACK_SOURCE = "Source";
    public final static String FEEDBACK_STORENUMBER = "StoreNumber";
    public final static String FEEDBACK_MILLISECONDS = "Milliseconds";

    //registration
    public static final String USER_TRANSIENT = "user_transient";

    public final static String Slider_TBL_NAME = "Slider";
    public final static String Slider_ID = "Id";
    public final static String Slider_PRICE = "Price";
    public final static String Slider_FIRSTTEXT = "FirstText";
    public final static String Slider_SECONDTEXT = "SecondText";
    public final static String Slider_THIRDTEXT = "ThirdText";
    public final static String Slider_LINK = "Link";
    public final static String Slider_SEQUENCE = "Seq";
    public final static int Slider_MAX_RESULT = 3;

    public final static String FEEDBACK_STORENUMBER_VALUE = "DoublePizza";

    public final static Integer DELIVERY_METHOD_DELIVERY = 0;
    public final static Integer DELIVERY_METHOD_PICKUP = 1;

    public final static String DATA_RESOURCES_STORES_PATH = "stores";
    public final static String DATA_RESOURCES_FOODS_PATH = "foods"; 
    public final static String DATA_RESOURCES_FOODS_TINY_PATH = "tiny";
    public final static String DATA_RESOURCES_SLIDES_PATH = "slides";
    public final static String DATA_RESOURCES_CATERING_PATH = "catering";

    public final static String POSTAL_CODE_PC = "POSTAL_CODE";
    public final static String POSTAL_CODE_CITY = "CITY";
    public final static String POSTAL_CODE_LATITUDE = "LATITUDE";
    public final static String POSTAL_CODE_LONGITUDE = "LONGITUDE";

    public final static String TOPPING_PART_FULL = "full";
    public final static String TOPPING_PART_LEFT = "left";
    public final static String TOPPING_PART_RIGHT = "right";
    public final static String TOPPING_PART_NO_DEFAULT = "no default";

    public final static String TOPPING_NO_SELECTED_DEFAULT = "No ";
    public final static String SINGLE_MENU_ITEM_NATURE = " Nature";

    public final static String SUGGESTION_TBL_NAME = "suggestions";
    public final static String SUGGESTION_PRODUCTNO = "Productno";
    public final static String SUGGESTION_MODIFIERID = "modifierID";
    public final static String SUGGESTION_PRICE = "Price";
    public final static String SUGGESTION_MODIFIER_PARENT = "suggested";

    public final static String PARAMETER_SELECTED_SUGGESTION = "selectedSuggestions";

    public final static String SUGGESTIONS_MENUSINGLE_PRODUCTNO = "7777";
    public final static int MENUITEM_PRODUCTNO_DIGITS = 4;


}
