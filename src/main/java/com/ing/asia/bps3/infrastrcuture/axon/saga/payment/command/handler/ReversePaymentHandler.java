package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.event.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.ReversePaymentExecutor;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentReversedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class ReversePaymentHandler extends BaseCommandHandler<ReversePaymentCommand, PaymentReversedEvent> {

    private final AccountRepository accountRepository;

    public ReversePaymentHandler(EventBus eventBus, AccountRepository accountRepository) {
        super(eventBus);
        this.accountRepository = accountRepository;
    }

    @CommandHandler
    public void handle(ReversePaymentCommand command) {
        publish(new ReversePaymentExecutor(accountRepository, command).execute());
    }
}
