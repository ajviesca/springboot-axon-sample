package com.ing.asia.bps3.infrastrcuture.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityJPA extends JpaRepository<PaymentEntity, Long> {
}
