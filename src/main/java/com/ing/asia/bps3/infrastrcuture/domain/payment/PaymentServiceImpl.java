package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentStatus;
import com.ing.asia.bps3.core.domain.payment.PostPaymentFacade;
import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final PostPaymentFacade postPaymentFacade;
    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;

    public PaymentServiceImpl(PostPaymentFacade postPaymentFacade, PaymentRepository paymentRepository,
                              BillerRepository billerRepository) {
        this.postPaymentFacade = postPaymentFacade;
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
    }

    @Override
    public Payment postPayment(PostPaymentCommand postPaymentCommand) {
        Biller biller = billerRepository.findById(postPaymentCommand.getBillerId());
        Payment payment = new Payment(postPaymentCommand.getPaymentAmount(),
                biller,
                LocalDateTime.now(), PaymentStatus.PLACED, postPaymentCommand.getAccountId());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment postPaymentV2(PostPaymentCommand postPaymentCommand) {
        return postPaymentFacade.postPayment(postPaymentCommand);
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
