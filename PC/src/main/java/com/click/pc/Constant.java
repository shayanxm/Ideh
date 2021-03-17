package com.click.pc;

public class Constant {
    public static final long SPLASH_TIME = 2000;
    public static long BACK_PRESSED = 2000;

    public static final String _ID = "ID";
    public static final String _TOKEN = "TOKEN";
    public static final String _MOBILE = "MOBILE";
    public static final String _CODE = "CODE";
    public static final String _NATIONAL_CODE = "NATIONAL_CODE";
    public static final String _TITLE = "TITLE";
    public static final String _DESCRIPTION = "DESCRIPTION";


    public static final String BASE_URL = "https://www.ideh.click/api/";
    public static final String VERSION = "v1/";


    public static final String LOGIN = "login";
    public static final String LOGOUT = "LogOut";
    public static final String REGISTER = "register";
    public static final String CHECK_CODE = "Check_Code";
    public static final String VERIFY_CODE = "SendSmsVerifiToMobile";
    public static final String USER_ACCOUNT = "UserAccount";
    public static final String IDEA_LIST = "ListIdea";
    public static final String REGISTER_IDEA = "RegisterIdea";
    public static final String DATA_INFO = "GetDataIdeaInfo";
    public static final String GENRE_LIST = "GetGanere";
    public static String ERROR = "error";


    public enum DRAWER_MENU {
        HOME,
        NEW_IDEA,
        USER_LIST,
    }

    public enum LIST_TYPE{
        GENRE,
        KEYWORD,
        REVIEW_TITLE,
        REVIEW_DESCRIPTION,
    }
}
