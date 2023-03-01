package com.ecomerce.guava.controller;

import com.ecomerce.guava.dto.checkout.CheckoutItemDto;
import com.ecomerce.guava.dto.checkout.StripeResponse;
import com.ecomerce.guava.service.OrderService;
import com.ecomerce.guava.service.impl.AuthenticationServiceImpl;
import com.ecomerce.guava.service.impl.OrderServiceImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private OrderServiceImpl orderService;

    //stripe session checkout api

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
            throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
