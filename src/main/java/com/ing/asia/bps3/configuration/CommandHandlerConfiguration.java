package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler.*;
import org.axonframework.eventhandling.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfiguration {

    private final EventBus eventBus;
    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;
    private final AccountRepository accountRepository;

    public CommandHandlerConfiguration(EventBus eventBus, PaymentRepository paymentRepository,
                                       BillerRepository billerRepository, AccountRepository accountRepository) {
        this.eventBus = eventBus;
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    public PayBillHandler payBillHandler() {
        return new PayBillHandler(eventBus, paymentRepository, billerRepository);
    }

    @Bean
    public DebitSourceHandler debitSourceHandler() {
        return new DebitSourceHandler(eventBus, accountRepository);
    }

    @Bean
    public SendPaymentHandler sendPaymentHandler() {
        return new SendPaymentHandler(eventBus, billerRepository);
    }

    @Bean
    public ReversePaymentHandler reversePaymentHandler() {
        return new ReversePaymentHandler(eventBus, accountRepository, paymentRepository);
    }

    @Bean
    public EndPaymentHandler paymentHandler() {
        return new EndPaymentHandler(eventBus, paymentRepository);
    }

}
