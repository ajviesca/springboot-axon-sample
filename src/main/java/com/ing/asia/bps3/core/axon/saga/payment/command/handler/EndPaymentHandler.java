package com.ing.asia.bps3.core.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.axon.saga.payment.command.api.EndPaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.PaymentEndedEvent;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class EndPaymentHandler extends BaseCommandHandler<EndPaymentCommand> {
    private final PaymentRepository paymentRepository;

    public EndPaymentHandler(EventBus eventBus, PaymentRepository paymentRepository) {
        super(eventBus);
        this.paymentRepository = paymentRepository;
    }

    @CommandHandler
    public void handle(EndPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.getPaymentId());
        payment.setStatus(command.getPaymentStatus());
        paymentRepository.save(payment);
        publish(new PaymentEndedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                command.getAmount()));
    }
}
