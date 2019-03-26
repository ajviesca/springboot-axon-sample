package com.ing.asia.bps3.core.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.axon.saga.payment.command.api.SendPaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.PaymentFailedEvent;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.PaymentSuccessfulEvent;
import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

import java.math.BigDecimal;

public class SendPaymentHandler extends BaseCommandHandler<SendPaymentCommand> {

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

            publish(new PaymentSuccessfulEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        } else {
            publish(new PaymentFailedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        }
    }
}
