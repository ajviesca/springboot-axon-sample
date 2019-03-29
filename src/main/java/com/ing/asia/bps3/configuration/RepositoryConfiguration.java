package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountEntityJPA;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountRepositoryImpl;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntityJPA;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerRepositoryImpl;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntityJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class RepositoryConfiguration {

    private final PaymentEntityJPA paymentEntityJPA;
    private final BillerEntityJPA billerEntityJPA;
    private final AccountEntityJPA accountEntityJPA;

    public RepositoryConfiguration(PaymentEntityJPA paymentEntityJPA, BillerEntityJPA billerEntityJPA, AccountEntityJPA accountEntityJPA) {
        this.paymentEntityJPA = paymentEntityJPA;
        this.billerEntityJPA = billerEntityJPA;
        this.accountEntityJPA = accountEntityJPA;
    }

    @Bean
    public BillerRepository billerRepository() {
        return new BillerRepositoryImpl(billerEntityJPA);
    }

    @Bean
    public PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl(paymentEntityJPA);
    }

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepositoryImpl(accountEntityJPA);
    }
}
