package com.ing.asia.bps3.core.event.payment.command.handler.executor;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.event.payment.command.api.DebitSourceAccountCommand;
import com.ing.asia.bps3.core.event.payment.event.api.BasePaymentEvent;
import com.ing.asia.bps3.core.event.payment.event.api.SourceAccountDebitedEvent;
import com.ing.asia.bps3.core.event.payment.event.api.SourceAccountInsufficientBalanceEvent;

import java.math.BigDecimal;

public class DebitSourceAccountExecutor {

    private AccountRepository accountRepository;
    private Long paymentId;
    private Long accountId;
    private Long billerId;
    private BigDecimal paymentAmount;
    private Account account;
    private BigDecimal currentBalance;
    private BasePaymentEvent resultEvent;

    public DebitSourceAccountExecutor(AccountRepository accountRepository,
                                      DebitSourceAccountCommand debitSourceAccountCommand) {
        this.accountRepository = accountRepository;
        this.paymentId = debitSourceAccountCommand.getPaymentId();
        this.accountId = debitSourceAccountCommand.getAccountId();
        this.billerId = debitSourceAccountCommand.getBillerId();
        this.paymentAmount = debitSourceAccountCommand.getPaymentAmount();
    }

    public DebitSourceAccountExecutor execute() {
        getAccountRecord();
        getAccountCurrentBalance();

        if (isInsufficientAccountBalance()) {
            createInsufficientBalanceEvent();
        } else {
            deductPaymentAmountFromCurrentBalance();
            updateAccountRecord();
            createAmountDebitedEvent();
        }

        return this;
    }


    public BasePaymentEvent getResultEvent() {
        return resultEvent;
    }

    private void getAccountRecord() {
        account = accountRepository.findById(accountId);
    }

    private void getAccountCurrentBalance() {
        currentBalance = account.getBalance();
    }

    private void deductPaymentAmountFromCurrentBalance() {
        account.setBalance(currentBalance.subtract(paymentAmount));
    }

    private void updateAccountRecord() {
        accountRepository.save(account);
    }

    private boolean isInsufficientAccountBalance() {
        return currentBalance.compareTo(paymentAmount) < 0;
    }

    private void createInsufficientBalanceEvent() {
        resultEvent = new SourceAccountInsufficientBalanceEvent(paymentId, accountId, billerId, paymentAmount);
    }

    private void createAmountDebitedEvent() {
        resultEvent = new SourceAccountDebitedEvent(paymentId, accountId, billerId,
                paymentAmount);
    }
}
