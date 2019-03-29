package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountRepositoryImpl;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerRepositoryImpl;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class RepositoryConfiguration {

    private final PaymentJPA paymentJPA;
    private final BillerJPA billerJPA;
    private final AccountJPA accountJPA;

    public RepositoryConfiguration(PaymentJPA paymentJPA, BillerJPA billerJPA,
                                   AccountJPA accountJPA) {
        this.paymentJPA = paymentJPA;
        this.billerJPA = billerJPA;
        this.accountJPA = accountJPA;
    }

    @Bean
    public BillerRepository billerRepository() {
        return new BillerRepositoryImpl(billerJPA);
    }

    @Bean
    public PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl(paymentJPA);
    }

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepositoryImpl(accountJPA);
    }
}
