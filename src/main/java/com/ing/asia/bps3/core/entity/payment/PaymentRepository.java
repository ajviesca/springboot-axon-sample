package com.ing.asia.bps3.core.entity.payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findAll();

    Payment save(Payment payment);

    Payment findById(Long id);
}
