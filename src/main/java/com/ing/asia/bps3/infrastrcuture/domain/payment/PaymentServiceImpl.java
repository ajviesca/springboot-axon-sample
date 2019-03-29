package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BillerRepository billerRepository) {
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
    }

    // TODO: use payment commands/executor via facade pattern
    @Override
    public Payment postPayment(PostPaymentSave postPaymentSave) {
        Biller biller = billerRepository.findById(postPaymentSave.getBillerId());
        Payment payment = new Payment(postPaymentSave.getAmount(),
                biller,
                LocalDateTime.now(), PaymentStatus.PLACED, postPaymentSave.getAccountId());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentHistory() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id);
    }
}
