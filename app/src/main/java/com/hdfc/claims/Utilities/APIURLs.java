package com.hdfc.claims.Utilities;

/**
 * Created by bhattzen on 1/19/2016.
 */
public class APIURLs {

    //Authenticate User
    //headers : Content-Type = "application/json; charset=utf-8"
    public static String AUTH_USER_URL = "http://202.191.196.210/UAT/IPO/Computation/ServiceImplementation/Service.svc/AuthenticateUser";

    //Dashboard data
    //headers : Content-Type = "application/json; charset=utf-8"
    //oAuthkey = "UtcG1XdikxOHH2Rlc+W3FA=="
    public static String GET_DASHBOARD_DATA_URL = "http://202.191.196.210/UAT/IPO/Computation/ServiceImplementation/Service.svc/GetDasBoardData";

    //Verify Passcode
    //headers : Content-Type = "application/json; charset=utf-8"
    //oAuthkey = "UtcG1XdikxOHH2Rlc+W3FA=="
    public static String VERIFY_PASSCODE_URL = "http://202.191.196.210/UAT/IPO/Computation/ServiceImplementation/Service.svc/VerifyPasscode";

    public static String GET_INSURED_DETAIL_URL = "http://202.191.196.210/UAT/IPO/Computation/ServiceImplementation/Service.svc/GetCustomerInfo";


    public static String GET_DL_N_RC_DETAIL_URL = "http://202.191.196.210/UAT/IPO/Computation/ServiceImplementation/Service.svc/GetDLDetails";
}
