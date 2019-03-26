package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountEntity;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.api.DebitSourceCommand;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api.SourceAccountInsufficientBalanceEvent;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api.SourceDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DebitSourceHandler extends BaseCommandHandler<DebitSourceCommand> {

    private Logger LOG = LoggerFactory.getLogger(DebitSourceHandler.class);

    private final AccountJPA accountJPA;

    public DebitSourceHandler(EventBus eventBus, AccountJPA accountJPA) {
        super(eventBus);
        this.accountJPA = accountJPA;
    }

    @CommandHandler
    public void handle(DebitSourceCommand command) {
        AccountEntity accountEntity = accountJPA.findById(command.getAccountId()).get();
        BigDecimal currentBalance = accountEntity.getBalance();
        BigDecimal amountToDeductFromCurrentBalance = command.getAmount();

        if (currentBalance.compareTo(amountToDeductFromCurrentBalance) < 0) {
            publish(new SourceAccountInsufficientBalanceEvent(command.getPaymentId(), command.getAccountId(),
                    command.getBillerId(), command.getAmount()));
        } else {
            accountEntity.setBalance(currentBalance.subtract(amountToDeductFromCurrentBalance));
            accountJPA.save(accountEntity);
            publish(new SourceDebitedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                    command.getAmount()));
        }
    }
}
