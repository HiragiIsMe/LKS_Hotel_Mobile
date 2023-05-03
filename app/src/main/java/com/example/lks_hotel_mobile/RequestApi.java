package com.example.lks_hotel_mobile;

public class RequestApi {
    public static final String url = "http://192.168.0.35/";
    public static final String login = "api/employee";
    public static final String fdcheckout = "api/fdcheckout";
    public static final String room = "api/room";
    public static final String fd = "api/fd";

    public static String getBaseUrl(){
        return  url;
    }
    public static String getLoginUrl(){
        return getBaseUrl() + login;
    }
    public static String getCheckoutUrl(){
        return getBaseUrl() + fdcheckout;
    }
    public static String getRoomUrl(){
        return getBaseUrl() + room;
    }
    public static String getFd(){
        return getBaseUrl() + fd;
    }
}
