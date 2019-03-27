package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountService
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerService
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Configuration
@Profile("test")
class ServiceTestConfiguration {

    DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

    @Bean
    @Profile(["mockAccountService", "mockAllService"])
    AccountService accountService() {
        detachedMockFactory.Stub(AccountService);
    }

    @Bean
    @Profile(["mockBillerService", "mockAllService"])
    BillerService billerService() {
        detachedMockFactory.Stub(BillerService);
    }

    @Bean
    @Profile(["mockPaymentService", "mockAllService"])
    PaymentService paymentService() {
        detachedMockFactory.Stub(PaymentService);
    }
}
