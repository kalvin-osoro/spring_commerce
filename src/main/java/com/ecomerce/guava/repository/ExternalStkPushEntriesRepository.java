package com.ecomerce.guava.repository;

import com.ecomerce.guava.dto.mpesa.ExternalStkPushRequest;
import com.ecomerce.guava.model.ExternalStkPushRequestssss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalStkPushEntriesRepository extends JpaRepository<ExternalStkPushRequest, Long> {
}
