package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountEntityJPA
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntityJPA
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntityJPA
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
    BillerEntityJPA billerEntityJPA() {
        detachedMockFactory.Stub(BillerEntityJPA)
    }

    @Bean
    PaymentEntityJPA paymentEntityJPA() {
        detachedMockFactory.Stub(PaymentEntityJPA)
    }

    @Bean
    AccountEntityJPA accountEntityJPA() {
        detachedMockFactory.Stub(AccountEntityJPA)
    }
}
