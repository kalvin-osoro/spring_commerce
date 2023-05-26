package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.config.MpesaConfig;
import com.ecomerce.guava.dto.mpesa.AccessTokenResponse;
import com.ecomerce.guava.dto.mpesa.ExternalStkPushRequest;
import com.ecomerce.guava.dto.mpesa.InternalStkPushRequest;
import com.ecomerce.guava.dto.mpesa.StkPushSyncResponse;
import com.ecomerce.guava.model.ExternalStkPushRequestssss;
import com.ecomerce.guava.model.StkPush_Entries;
import com.ecomerce.guava.repository.ExternalStkPushEntriesRepository;
import com.ecomerce.guava.repository.StkPushEntriesRepository;
import com.ecomerce.guava.service.DarajaApi;
import com.ecomerce.guava.utils.Constants;
import com.ecomerce.guava.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static com.ecomerce.guava.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DarajaApiImpl implements DarajaApi {

    private final MpesaConfig mpesaConfig;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final StkPushEntriesRepository stkPushEntriesRepository;
    private final ExternalStkPushEntriesRepository externalStkPushEntriesRepository;

    @Override
    public AccessTokenResponse getAccessToken() {

        // get the Base64 rep of consumerKey + ":" + consumerSecret
        String encodedCredentials = HelperUtility.toBase64String(String.format("%s:%s", mpesaConfig.getConsumerKey(),
                mpesaConfig.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfig.getOauthEndpoint(), mpesaConfig.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BASIC_AUTH_STRING, encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;

            // Extract the response body as a string
            String responseBody = response.body().string();

            // Use Jackson to decode the responseBody
            return objectMapper.readValue(responseBody, AccessTokenResponse.class);

        } catch (IOException e) {
            log.error(String.format("Could not get access token. %s", e.getLocalizedMessage()));
            return null;
        }
    }




    @Override
    public StkPushSyncResponse performStkPushTransaction(InternalStkPushRequest internalStkPushRequest) {

        ExternalStkPushRequest externalStkPushRequest = new ExternalStkPushRequest();

//        ExternalStkPushRequestssss externalStkPushRequest = new ExternalStkPushRequestssss();

        externalStkPushRequest.setBusinessShortCode(mpesaConfig.getStkPushShortCode());

        String transactionTimestamp = HelperUtility.getTransactionTimestamp();
        String stkPushPassword = HelperUtility.getStkPushPassword(mpesaConfig.getStkPushShortCode(), mpesaConfig.getStkPassKey(),transactionTimestamp);
        externalStkPushRequest.setPassword(stkPushPassword);
        externalStkPushRequest.setTimestamp(transactionTimestamp);
        externalStkPushRequest.setTransactionType(Constants.CUSTOMER_PAYBILL_ONLINE);
        externalStkPushRequest.setAmount(internalStkPushRequest.getAmount());
        externalStkPushRequest.setPartyA(internalStkPushRequest.getPhoneNumber());
        externalStkPushRequest.setPartyB(mpesaConfig.getStkPushShortCode());
        externalStkPushRequest.setPhoneNumber(internalStkPushRequest.getPhoneNumber());
        externalStkPushRequest.setCallBackURL(mpesaConfig.getStkPushRequestCallbackUrl());
        externalStkPushRequest.setAccountReference(HelperUtility.getTransactionUniqueNumber());
        externalStkPushRequest.setTransactionDesc(String.format("%s Transaction",internalStkPushRequest.getPhoneNumber()));

        StkPush_Entries entries = new StkPush_Entries();

        entries.setEntryDate(new Date());
        entries.setTransactionType("Stk Push");
        entries.setAmount(internalStkPushRequest.getAmount());
        entries.setMsisdn(internalStkPushRequest.getPhoneNumber());

        stkPushEntriesRepository.save(entries);

        externalStkPushEntriesRepository.save(externalStkPushRequest);


        AccessTokenResponse accessTokenResponse = getAccessToken();

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(externalStkPushRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfig.getStkPushRequestUrl())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            // use Jackson to Decode the ResponseBody ...

            return objectMapper.readValue(response.body().string(), StkPushSyncResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not Perform STK push request -> %s", e.getLocalizedMessage()));
            return null;
        }

    }

}
