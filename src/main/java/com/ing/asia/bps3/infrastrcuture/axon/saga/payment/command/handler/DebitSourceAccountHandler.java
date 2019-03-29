package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.event.payment.command.api.DebitSourceAccountCommand;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.DebitSourceAccountExecutor;
import com.ing.asia.bps3.core.event.payment.event.api.BasePaymentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class DebitSourceAccountHandler extends BaseCommandHandler<DebitSourceAccountCommand, BasePaymentEvent> {

    private final AccountRepository accountRepository;

    public DebitSourceAccountHandler(EventBus eventBus, AccountRepository accountRepository) {
        super(eventBus);
        this.accountRepository = accountRepository;
    }

    @CommandHandler
    public void handle(DebitSourceAccountCommand command) {
        publish(new DebitSourceAccountExecutor(accountRepository, command).execute().getResultEvent());
    }
}
