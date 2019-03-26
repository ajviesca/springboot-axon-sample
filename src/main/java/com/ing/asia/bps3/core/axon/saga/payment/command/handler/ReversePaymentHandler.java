package com.ing.asia.bps3.core.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.PaymentReversedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

import java.math.BigDecimal;

public class ReversePaymentHandler extends BaseCommandHandler<ReversePaymentCommand> {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public ReversePaymentHandler(EventBus eventBus, AccountRepository accountRepository, PaymentRepository paymentRepository) {
        super(eventBus);
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @CommandHandler
    public void handle(ReversePaymentCommand command) {
        Account account = accountRepository.findById(command.getAccountId());
        BigDecimal currentAmount = account.getBalance();
        BigDecimal accountToReverse = command.getAmount();
        account.setBalance(currentAmount.add(accountToReverse));
        accountRepository.save(account);

        publish(new PaymentReversedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                command.getAmount()));


    }
}
