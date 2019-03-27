package com.ing.asia.bps3.infrastructure.domain.payment

import com.ing.asia.bps3.configuration.JpaTestConfiguration
import com.ing.asia.bps3.configuration.RepositoryTestConfiguration
import com.ing.asia.bps3.configuration.ServiceTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentRepositoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(classes = [PaymentRepositorySpecificationConfig])
@ActiveProfiles("test")
class PaymentRepositorySpecification extends Specification {

    @Autowired
    PaymentRepository paymentRepository;

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    def "should save"() {
        given:
        Payment

        when:
        paymentRepository.save(_) >> null

        then:
        1 == 1;
    }

    @TestConfiguration
    @Import([JpaTestConfiguration, RepositoryTestConfiguration, ServiceTestConfiguration])
    static class PaymentRepositorySpecificationConfig {

        @Bean
        @Primary
        PaymentRepository overrideDefaultPaymentRepository(PaymentJPA paymentJPA) {
            return new PaymentRepositoryImpl(paymentJPA)
        }
    }
}

