package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.domain.payment.PostPaymentFacade;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountService;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountServiceImpl;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerService;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerServiceImpl;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class ServiceConfiguration {

    private final BillerRepository billerRepository;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public ServiceConfiguration(BillerRepository billerRepository,
                                PaymentRepository paymentRepository,
                                AccountRepository accountRepository) {
        this.billerRepository = billerRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    public BillerService billerService() {
        return new BillerServiceImpl(billerRepository);
    }

    @Bean
    public PaymentService paymentService() {
        return new PaymentServiceImpl(postPaymentFacade(), paymentRepository,
                billerRepository);
    }

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl(accountRepository);
    }

    @Bean
    public PostPaymentFacade postPaymentFacade() {
        return new PostPaymentFacade(billerRepository, paymentRepository,
                accountRepository);
    }
}
