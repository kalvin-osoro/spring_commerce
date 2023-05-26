package com.ecomerce.guava.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Table(name = "stk_push_entries")
@Entity
@Data
@RequiredArgsConstructor
public class StkPush_Entries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId;

    private String transactionId;

    private String transactionType;

    private String msisdn;

    private String amount;
//    private Long amount;

    private String merchantRequestID;

    private String checkoutRequestID;

    private Date entryDate;

    private String resultCode;

    private String rawCallbackPayloadResponse;
}
