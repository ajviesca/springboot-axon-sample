package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand;

import java.util.List;

public interface PaymentService {

    Payment postPayment(PostPaymentCommand postPaymentCommand);

    Payment postPaymentV2(PostPaymentCommand postPaymentCommand);

    List<Payment> getPaymentHistory();

    Payment findById(Long id);
}
