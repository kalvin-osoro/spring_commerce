package com.ecomerce.guava.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "external_stk_push_entries")
@Data
@RequiredArgsConstructor
public class ExternalStkPushRequestssss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;

    private String amount;

    private String callBackURL;

    private String phoneNumber;

    private String partyA;

    private String partyB;

    private String accountReference;

    private String transactionDesc;

    private String businessShortCode;

    private String timestamp;

    private String password;

}
