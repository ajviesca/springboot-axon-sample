package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.api.SendPaymentCommand;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api.PaymentFailedEvent;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api.PaymentSuccessfulEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class SendPaymentHandler extends BaseCommandHandler<SendPaymentCommand> {

    private static final Logger LOG = LoggerFactory.getLogger(SendPaymentHandler.class);

    private final BillerJPA billerJPA;

    public SendPaymentHandler(EventBus eventBus, BillerJPA billerJPA) {
        super(eventBus);
        this.billerJPA = billerJPA;
    }

    @CommandHandler
    public void handle(SendPaymentCommand command) {
        BillerEntity biller = billerJPA.findById(command.getBillerId()).get();

        BigDecimal amount = command.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) > 0) {

            LOG.info("Sending payment :: biller={}, amount={}", biller.getBillerName(), command.getAmount());
            publish(new PaymentSuccessfulEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        } else {
            publish(new PaymentFailedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        }
    }
}
