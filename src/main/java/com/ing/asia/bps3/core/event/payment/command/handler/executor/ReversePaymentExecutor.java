package com.ing.asia.bps3.core.event.payment.command.handler.executor;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.event.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentReversedEvent;

import java.math.BigDecimal;

public class ReversePaymentExecutor {

    private AccountRepository accountRepository;
    private Long accountId;
    private Long paymentId;
    private Long billerId;
    private BigDecimal paymentAmount;
    private Account account;
    private BigDecimal accountBalance;
    private PaymentReversedEvent resultEvent;

    public ReversePaymentExecutor(AccountRepository accountRepository, ReversePaymentCommand command) {
        this.accountRepository = accountRepository;
        this.accountId = command.getAccountId();
        this.paymentId = command.getPaymentId();
        this.billerId = command.getBillerId();
        this.paymentAmount = command.getPaymentAmount();
    }

    public ReversePaymentExecutor execute() {
        getAccountRecord();
        getAccountBalance();
        reversePaymentAmount();
        updateAccountRecord();

        createPaymentReversedEvent();
        return this;
    }

    public PaymentReversedEvent getResultEvent() {
        return resultEvent;
    }

    private void createPaymentReversedEvent() {
        resultEvent = new PaymentReversedEvent(paymentId, accountId, billerId,
                paymentAmount);
    }

    private void getAccountRecord() {
        account = accountRepository.findById(accountId);
    }

    private void getAccountBalance() {
        accountBalance = account.getBalance();
    }

    private void reversePaymentAmount() {
        account.setBalance(accountBalance.add(paymentAmount));
    }

    private void updateAccountRecord() {
        accountRepository.save(account);
    }

}
