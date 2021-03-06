package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.core.domain.account.AccountRepository
import com.ing.asia.bps3.core.domain.biller.BillerRepository
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@TestConfiguration
@Profile("test")
class RepositoryTestConfiguration {

    DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    PaymentRepository paymentRepository() {
        detachedMockFactory.Stub(PaymentRepository)
    }

    @Bean
    BillerRepository billerRepository() {
        detachedMockFactory.Stub(BillerRepository)
    }

    @Bean
    AccountRepository accountRepository() {
        detachedMockFactory.Stub(AccountRepository)
    }
}
