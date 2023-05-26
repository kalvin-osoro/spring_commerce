package com.ecomerce.guava.controller;

import com.ecomerce.guava.dto.mpesa.*;
import com.ecomerce.guava.repository.StkPushEntriesRepository;
import com.ecomerce.guava.service.DarajaApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mobile-money")
@RequiredArgsConstructor
@Slf4j
public class MpesaController {
    private final DarajaApi darajaApi;
    private final AcknowledgeResponse acknowledgeResponse;
    private final ObjectMapper objectMapper;

    private final StkPushEntriesRepository stkPushEntriesRepository;

    @GetMapping(path = "/token", produces = "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken() {
        return ResponseEntity.ok(darajaApi.getAccessToken());
    }

//    @PostMapping(value = "register-url")
//    public ResponseEntity<RegisterUrlResponse> registerUrl() {
//        return ResponseEntity.ok(darajaApi.registerUrl());
//    }

    @PostMapping("/stk-transaction-request")
    public ResponseEntity<StkPushSyncResponse> performStkPushTransaction(@RequestBody InternalStkPushRequest internalStkPushRequest) {

        StkPushSyncResponse stkPushSyncResponse = darajaApi.performStkPushTransaction(internalStkPushRequest);

        return ResponseEntity.ok(stkPushSyncResponse);
    }


    @PostMapping("/stk-transaction-result")
    public ResponseEntity<AcknowledgeResponse> acknowledgeStkPushResponse(@RequestBody StkPushAsyncResponse stkPushAsyncResponse)
            throws JsonProcessingException {
        log.info("==================STK push asyncResponse============");
        log.info(objectMapper.writeValueAsString(stkPushAsyncResponse));

        return ResponseEntity.ok(acknowledgeResponse);
    }



}
