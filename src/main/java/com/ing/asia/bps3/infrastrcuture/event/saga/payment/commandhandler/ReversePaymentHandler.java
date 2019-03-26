package com.ing.asia.bps3.infrastrcuture.event.saga.payment.commandhandler;

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountEntity;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.command.ReversePaymentCommand;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.event.PaymentReversedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

import java.math.BigDecimal;

public class ReversePaymentHandler extends BaseCommandHandler<ReversePaymentCommand> {

    private final AccountJPA accountJPA;
    private final PaymentJPA paymentJPA;

    public ReversePaymentHandler(EventBus eventBus, AccountJPA accountJPA, PaymentJPA paymentJPA) {
        super(eventBus);
        this.accountJPA = accountJPA;
        this.paymentJPA = paymentJPA;
    }

    @CommandHandler
    public void handle(ReversePaymentCommand command) {
        AccountEntity account = accountJPA.findById(command.getAccountId()).get();
        BigDecimal currentAmount = account.getBalance();
        BigDecimal accountToReverse = command.getAmount();
        account.setBalance(currentAmount.add(accountToReverse));
        accountJPA.save(account);

        publish(new PaymentReversedEvent(command.getPaymentId(), command.getAccountId(), command.getBillerId(),
                command.getAmount()));


    }
}
