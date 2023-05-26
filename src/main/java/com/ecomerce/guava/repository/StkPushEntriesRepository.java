package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.StkPush_Entries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StkPushEntriesRepository extends JpaRepository<StkPush_Entries, Long> {

    //  Find Record By MerchantRequestID or CheckoutRequestID ...
    StkPush_Entries findByMerchantRequestIDOrCheckoutRequestID(String merchantRequestID, String checkoutRequestID);

    // Find Transaction By TransactionId ...
    StkPush_Entries findByTransactionId(String transactionId);
}
