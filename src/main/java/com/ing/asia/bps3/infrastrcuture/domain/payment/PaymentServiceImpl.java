package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.entity.biller.Biller;
import com.ing.asia.bps3.core.entity.biller.BillerRepository;
import com.ing.asia.bps3.core.entity.payment.Payment;
import com.ing.asia.bps3.core.entity.payment.PaymentRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BillerRepository billerRepository) {
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
    }

    @Override
    public Payment postPayment(PostPaymentSave postPaymentSave) {
        Biller biller = billerRepository.findById(postPaymentSave.getBillerId());
        Payment payment = new Payment(postPaymentSave.getId(),
                postPaymentSave.getAmount(),
                biller,
                LocalDateTime.now());
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
