package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJPA paymentJPA;

    public PaymentRepositoryImpl(PaymentJPA paymentJPA) {
        this.paymentJPA = paymentJPA;
    }

    @Override
    public List<Payment> findAll() {
        List<PaymentEntity> payments = paymentJPA.findAll();

        return payments.stream().map(paymentEntity -> {
            BillerEntity billerEntity = paymentEntity.getBiller();
            return new Payment(paymentEntity.getId(), paymentEntity.getAmount(),
                    new Biller(billerEntity.getId(), billerEntity.getBillerName()), paymentEntity.getPostDate()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity(payment.getId(), payment.getAmount(), payment.getPostDate());
        Biller sourceBiller = payment.getBiller();
        BillerEntity billerEntity = new BillerEntity(sourceBiller.getId(), sourceBiller.getBillerName());
        paymentEntity.setBiller(billerEntity);
        PaymentEntity saved = paymentJPA.save(paymentEntity);
        billerEntity = saved.getBiller();
        return new Payment(saved.getId(), saved.getAmount(),
                new Biller(billerEntity.getId(), billerEntity.getBillerName()),
                saved.getPostDate());
    }

    @Override
    public Payment findById(Long id) {
        PaymentEntity paymentEntity = paymentJPA.findById(id).get();
        BillerEntity billerEntity = paymentEntity.getBiller();
        Payment payment = new Payment(paymentEntity.getId(), paymentEntity.getAmount(),
                new Biller(billerEntity.getId(), billerEntity.getBillerName()), paymentEntity.getPostDate());
        payment.setStatus(paymentEntity.getStatus());
        payment.setPaidByAccountId(paymentEntity.getPaidByAccountId());
        return payment;
    }
}
