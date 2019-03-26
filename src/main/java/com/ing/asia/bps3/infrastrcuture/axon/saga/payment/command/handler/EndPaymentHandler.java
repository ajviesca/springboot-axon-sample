package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.api.EndPaymentCommand;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api.PaymentEndedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndPaymentHandler extends BaseCommandHandler<EndPaymentCommand> {

    private final static Logger LOG = LoggerFactory.getLogger(EndPaymentHandler.class);
    private final PaymentRepository paymentRepository;

    public EndPaymentHandler(EventBus eventBus, PaymentRepository paymentRepository) {
        super(eventBus);
        this.paymentRepository = paymentRepository;
    }

    @CommandHandler
    public void handle(EndPaymentCommand command) {
        LOG.info("end payment handler enter");
        Payment payment = paymentRepository.findById(command.getPaymentId());
        payment.setStatus(command.getPaymentStatus());
        paymentRepository.save(payment);
        publish(new PaymentEndedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                command.getAmount()));
        LOG.info("end payment handler exit");
    }
}
