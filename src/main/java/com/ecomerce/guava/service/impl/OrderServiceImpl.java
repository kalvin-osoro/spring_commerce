package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.checkout.CheckoutItemDto;
import com.ecomerce.guava.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

//    @Value("${BASEURL}")
    private String baseURL = "http://localhost:8082/";

//    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey = "sk_test_51MevffJ7qeZtbDAlnjOB9rNdQVtptp7nelM4vP8JVXDJUafp2LVEdpOv1PQ7HDTyo8zQt9laJeN56Sbco0SQjDRy00Tk5xLMiu";
    @Override
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        //success and failure urls

        String successURL = baseURL + "payment/success";

        String failureURL = baseURL + "payment/failed";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (CheckoutItemDto checkoutItemDto: checkoutItemDtoList) {
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .setSuccessUrl(successURL)
                .addAllLineItem(sessionItemList)
                .build();
        return Session.create(params);
    }

    @Override
    public SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

   @Override
    public SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)checkoutItemDto.getPrice()*100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                ).build();
    }
}
