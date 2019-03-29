package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@TestConfiguration
@Profile("test")
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration])
class JpaTestConfiguration {
    DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

    @Bean
    BillerJPA billerEntityJPA() {
        detachedMockFactory.Stub(BillerJPA)
    }

    @Bean
    PaymentJPA paymentEntityJPA() {
        detachedMockFactory.Stub(PaymentJPA)
    }

    @Bean
    AccountJPA accountEntityJPA() {
        detachedMockFactory.Stub(AccountJPA)
    }
}
