package com.ing.asia.bps3.configuration

import com.ing.asia.bps3.infrastrcuture.domain.account.AccountJPA
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Configuration
@Profile("test")
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration])
class JpaTestConfiguration {
    DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

    @Bean
    BillerJPA billerJPA() {
        detachedMockFactory.Stub(BillerJPA)
    }

    @Bean
    PaymentJPA paymentJPA() {
        detachedMockFactory.Stub(PaymentJPA)
    }

    @Bean
    AccountJPA accountJPA() {
        detachedMockFactory.Stub(AccountJPA)
    }
}
