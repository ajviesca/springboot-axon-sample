package com.ing.asia.bps3.core.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.axon.saga.payment.command.api.PayBillCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.PaymentInProgressEvent;
import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

import java.time.LocalDateTime;

public class PayBillHandler extends BaseCommandHandler<PayBillCommand> {

    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;

    public PayBillHandler(EventBus eventBus, PaymentRepository paymentRepository, BillerRepository billerRepository) {
        super(eventBus);
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
    }

    @CommandHandler
    public void handle(PayBillCommand command) {
        Biller biller = billerRepository.findById(command.getBillerId());
        Payment payment = new Payment(System.currentTimeMillis(),
                command.getAmount(), biller, LocalDateTime.now(), Payment.Status.PLACED, command.getAccountId());
        payment = paymentRepository.save(payment);
        publish(new PaymentInProgressEvent(payment.getId(), payment.getPaidByAccountId(),
                biller.getId(), payment.getAmount()));
    }


}
