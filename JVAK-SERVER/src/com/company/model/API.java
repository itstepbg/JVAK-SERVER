package com.company.model;

public class API {

    //Client-side.
    public final static String PREFIX_LOGIN = "login:";
    public final static String PREFIX_LOGOUT = "logout:";
    public final static String PREFIX_INIT_CONVERSATION = "init:";
    public final static String PREFIX_END_CONVERSATION = "end:";
    public final static String PREFIX_CHAT_MESSAGE = "chat:";

    //Server-side.
    public final static String PREFIX_STATUS = "status:";
    public final static String PREFIX_CONVERSATION_INITIALIZED = "chatStarted:";
    public final static String PREFIX_CONVERSATION_ENDED = "chatEnded:";
    public final static String STATUS_OK = "OK!";
    public final static String STATUS_NOT_OK = "NOT_OK!";

    //TODO
    public final static String PREFIX_CREATE_ACCOUNT = "create:";
    public final static String PREFIX_DELETE_ACCOUNT = "delete:";

}
