package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.command.handler.*;
import org.axonframework.eventhandling.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfiguration {

    private final EventBus eventBus;
    private final PaymentJPA paymentJPA;
    private final BillerJPA billerJPA;
    private final AccountJPA accountJpa;

    public CommandHandlerConfiguration(EventBus eventBus, PaymentJPA paymentJPA, BillerJPA billerJPA, AccountJPA accountJpa) {
        this.eventBus = eventBus;
        this.paymentJPA = paymentJPA;
        this.billerJPA = billerJPA;
        this.accountJpa = accountJpa;
    }

    @Bean
    public PayBillHandler payBillHandler() {
        return new PayBillHandler(eventBus, paymentJPA, billerJPA);
    }

    @Bean
    public DebitSourceHandler debitSourceHandler() {
        return new DebitSourceHandler(eventBus, accountJpa);
    }

    @Bean
    public SendPaymentHandler sendPaymentHandler() {
        return new SendPaymentHandler(eventBus, billerJPA);
    }

    @Bean
    public ReversePaymentHandler reversePaymentHandler() {
        return new ReversePaymentHandler(eventBus, accountJpa, paymentJPA);
    }

    @Bean
    public EndPaymentHandler paymentHandler() {
        return new EndPaymentHandler(eventBus, paymentJPA);
    }

}
