package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.checkout.CheckoutItemDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException;


//     SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto);
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto);
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto);
}
