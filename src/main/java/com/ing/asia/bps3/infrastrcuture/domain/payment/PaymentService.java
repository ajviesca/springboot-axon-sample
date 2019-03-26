package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.entity.payment.Payment;

import java.util.List;

public interface PaymentService {

    Payment postPayment(PostPaymentSave postPaymentSave);

    List<Payment> getPaymentHistory();

    Payment findById(Long id);
}
