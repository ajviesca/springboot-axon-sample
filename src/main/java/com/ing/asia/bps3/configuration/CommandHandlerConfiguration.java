package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler.*;
import org.axonframework.eventhandling.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
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
    public PostPaymentHandler payBillHandler() {
        return new PostPaymentHandler(eventBus, paymentRepository, billerRepository);
    }

    @Bean
    public DebitSourceAccountHandler debitSourceHandler() {
        return new DebitSourceAccountHandler(eventBus, accountRepository);
    }

    @Bean
    public SendPaymentToBillerHandler sendPaymentHandler() {
        return new SendPaymentToBillerHandler(eventBus, billerRepository);
    }

    @Bean
    public ReversePaymentHandler reversePaymentHandler() {
        return new ReversePaymentHandler(eventBus, accountRepository);
    }

    @Bean
    public UpdatePaymentStatusHandler paymentHandler() {
        return new UpdatePaymentStatusHandler(eventBus, paymentRepository);
    }

}
