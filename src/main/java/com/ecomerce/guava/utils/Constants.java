package com.ecomerce.guava.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;

@Slf4j
public class Constants {

    public static final String BASIC_AUTH_STRING = "Basic";
    public static final String BEARER_AUTH_STRING = "Bearer";
    public static final String AUTHORIZATION_HEADER_STRING = "authorization";
    public static final String CACHE_CONTROL_HEADER = "cache-control";
    public static final String CACHE_CONTROL_HEADER_VALUE = "no-cache";
    public static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static final String ACCOUNT_BALANCE_COMMAND = "AccountBalance";

    public static final String CUSTOMER_PAYBILL_ONLINE = "CustomerPayBillOnline";
    public static final String SHORT_CODE_IDENTIFIER = "4";



}
