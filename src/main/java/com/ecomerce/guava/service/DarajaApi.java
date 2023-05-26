package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.mpesa.AccessTokenResponse;
import com.ecomerce.guava.dto.mpesa.InternalStkPushRequest;
import com.ecomerce.guava.dto.mpesa.StkPushSyncResponse;

public interface DarajaApi {

    AccessTokenResponse getAccessToken();
    StkPushSyncResponse performStkPushTransaction(InternalStkPushRequest internalStkPushRequest);



}
