package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountService
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerService
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@TestConfiguration
@Profile("test")
class ServiceTestConfiguration {

    DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

    @Bean
    AccountService accountService() {
        detachedMockFactory.Stub(AccountService);
    }

    @Bean
    BillerService billerService() {
        detachedMockFactory.Stub(BillerService);
    }

    @Bean
    PaymentService paymentService() {
        detachedMockFactory.Stub(PaymentService);
    }
}
