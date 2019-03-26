package com.ing.asia.bps3.core.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.DebitSourceCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.SourceAccountInsufficientBalanceEvent;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.SourceDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DebitSourceHandler extends BaseCommandHandler<DebitSourceCommand> {

    private Logger LOG = LoggerFactory.getLogger(DebitSourceHandler.class);

    private final AccountRepository accountRepository;

    public DebitSourceHandler(EventBus eventBus, AccountRepository accountRepository) {
        super(eventBus);
        this.accountRepository = accountRepository;
    }

    @CommandHandler
    public void handle(DebitSourceCommand command) {
        Account account = accountRepository.findById(command.getAccountId());
        BigDecimal currentBalance = account.getBalance();
        BigDecimal amountToDeductFromCurrentBalance = command.getAmount();

        if (currentBalance.compareTo(amountToDeductFromCurrentBalance) < 0) {
            publish(new SourceAccountInsufficientBalanceEvent(command.getPaymentId(), command.getAccountId(),
                    command.getBillerId(), command.getAmount()));
        } else {
            account.setBalance(currentBalance.subtract(amountToDeductFromCurrentBalance));
            accountRepository.save(account);
            publish(new SourceDebitedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        }
    }
}
