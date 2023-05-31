package com.ecomerce.guava.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "external_stk_push_entries")
public class ExternalStkPushRequest {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	private Long id;

	@JsonProperty("TransactionType")
	private String transactionType;

	@JsonProperty("Amount")
	private String amount;

	@JsonProperty("CallBackURL")
	private String callBackURL;

	@JsonProperty("PhoneNumber")
	private String phoneNumber;

	@JsonProperty("PartyA")
	private String partyA;

	@JsonProperty("PartyB")
	private String partyB;

	@JsonProperty("AccountReference")
	private String accountReference;

	@JsonProperty("TransactionDesc")
	private String transactionDesc;

	@JsonProperty("BusinessShortCode")
	private String businessShortCode;

	@JsonProperty("Timestamp")
	private String timestamp;

	@JsonProperty("Password")
	private String password;
}