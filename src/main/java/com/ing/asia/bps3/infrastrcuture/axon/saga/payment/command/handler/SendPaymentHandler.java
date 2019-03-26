package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
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

    private final BillerRepository billerRepository;

    public SendPaymentHandler(EventBus eventBus, BillerRepository billerRepository) {
        super(eventBus);
        this.billerRepository = billerRepository;
    }

    @CommandHandler
    public void handle(SendPaymentCommand command) {
        Biller biller = billerRepository.findById(command.getBillerId());

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
