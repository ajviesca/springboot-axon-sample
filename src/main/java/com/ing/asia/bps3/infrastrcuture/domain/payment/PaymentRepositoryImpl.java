package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJPA paymentJPA;

    public PaymentRepositoryImpl(PaymentJPA paymentJPA) {
        this.paymentJPA = paymentJPA;
    }

    @Override
    public List<Payment> findAll() {
        List<PaymentEntity> payments = paymentJPA.findAll();
        return payments.stream().map(getPaymentEntityToPaymentMapper()).collect(Collectors.toList());
    }

    @Override
    public Payment save(Payment payment) {
        Biller sourceBiller = payment.getBiller();
        BillerEntity billerEntity = toBillerEntity(sourceBiller);
        PaymentEntity paymentEntity = new PaymentEntity(System.currentTimeMillis(), payment.getAmount(), billerEntity,
                payment.getPostDate(), payment.getStatus(), payment.getPaidByAccountId());
        PaymentEntity saved = paymentJPA.save(paymentEntity);
        return toPayment(saved);
    }

    @Override
    public Payment findById(Long id) {
        PaymentEntity paymentEntity = paymentJPA.findById(id).get();
        return toPayment(paymentEntity);
    }

    @Override
    public Payment update(Payment payment) {
        PaymentEntity paymentEntity = paymentJPA.findById(payment.getId()).get();
        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setStatus(payment.getStatus());
        paymentEntity.setPostDate(payment.getPostDate());
        Biller biller = payment.getBiller();
        paymentEntity.setBiller(toBillerEntity(biller));
        paymentEntity.setPaidByAccountId(payment.getPaidByAccountId());
        PaymentEntity saved = paymentJPA.save(paymentEntity);
        return toPayment(saved);
    }

    private Function<PaymentEntity, Payment> getPaymentEntityToPaymentMapper() {
        return paymentEntity -> {
            return toPayment(paymentEntity);
        };
    }

    private Biller toBiller(BillerEntity billerEntity) {
        return new Biller(billerEntity.getId(), billerEntity.getBillerName());
    }

    private Payment toPayment(PaymentEntity paymentEntity) {
        return new Payment(paymentEntity.getId(), paymentEntity.getAmount(),
                toBiller(paymentEntity.getBiller()), paymentEntity.getPostDate(),
                paymentEntity.getStatus(), paymentEntity.getPaidByAccountId()
        );
    }

    private BillerEntity toBillerEntity(Biller sourceBiller) {
        return new BillerEntity(sourceBiller.getId(), sourceBiller.getBillerName());
    }
}
