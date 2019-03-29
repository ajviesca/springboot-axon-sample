package com.ing.asia.bps3.infrastructure.domain.payment

import com.ing.asia.bps3.configuration.BpsTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.core.domain.payment.PaymentStatus
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntity
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentRepositoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = [BpsTestConfiguration, PaymentRepositorySpecificationConfig])
@ActiveProfiles("test")
class PaymentRepositorySpec extends Specification {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentJPA paymentJPA;

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    @Shared
    BillerEntity meralcoBillerEntity = new BillerEntity(1L, "Meralco")

    def "should save"() {
        given:
        Payment payment = new Payment(100, meralcoBiller, LocalDateTime.now(), PaymentStatus.PLACED, 1L)

        and:
        PaymentEntity paymentEntity = new PaymentEntity(System.currentTimeMillis(), payment.getAmount(), meralcoBillerEntity,
                payment.getPostDate(), payment.getStatus(), payment.getPaidByAccountId())

        and:
        paymentJPA.save(_) >> paymentEntity

        when:
        Payment resultPayment = paymentRepository.save(payment)

        then:
        resultPayment != null
        resultPayment.getId() != null
        resultPayment.biller.id == meralcoBiller.id
        resultPayment.status == PaymentStatus.PLACED
    }

    @TestConfiguration
    @Profile("test")
    static class PaymentRepositorySpecificationConfig {

        @Bean
        @Primary
        PaymentRepository overrideDefaultPaymentRepository(PaymentJPA paymentJPA) {
            return new PaymentRepositoryImpl(paymentJPA)
        }
    }
}

