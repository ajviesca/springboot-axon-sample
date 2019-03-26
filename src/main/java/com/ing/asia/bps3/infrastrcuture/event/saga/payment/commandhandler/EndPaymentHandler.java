package com.ing.asia.bps3.infrastrcuture.event.saga.payment.commandhandler;

import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntity;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.command.EndPaymentCommand;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.event.PaymentEndedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndPaymentHandler extends BaseCommandHandler<EndPaymentCommand> {

    private final static Logger LOG = LoggerFactory.getLogger(EndPaymentHandler.class);
    private final PaymentJPA paymentJPA;

    public EndPaymentHandler(EventBus eventBus, PaymentJPA paymentJPA) {
        super(eventBus);
        this.paymentJPA = paymentJPA;
    }

    @CommandHandler
    public void handle(EndPaymentCommand command) {
        LOG.info("end payment handler enter");
        PaymentEntity payment = paymentJPA.findById(command.getPaymentId()).get();
        payment.setStatus(command.getPaymentStatus());
        publish(new PaymentEndedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                command.getAmount()));
        LOG.info("end payment handler exit");
    }
}
