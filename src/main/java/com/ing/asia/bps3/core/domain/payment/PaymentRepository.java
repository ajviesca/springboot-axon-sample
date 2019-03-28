package com.ing.asia.bps3.core.domain.payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findAll();

    Payment save(Payment payment);

    Payment findById(Long id);

    Payment update(Payment payment);
}
