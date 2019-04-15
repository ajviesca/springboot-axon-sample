package com.ing.asia.bps3.infrastructure.domain.payment

import com.ing.asia.bps3.configuration.BpsTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.biller.BillerRepository
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.core.domain.payment.PaymentStatus
import com.ing.asia.bps3.core.domain.payment.PostPaymentFacade
import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentServiceImpl
import org.junit.Ignore
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

@SpringBootTest(classes = [BpsTestConfiguration, PaymentServiceSpecConfig])
@ActiveProfiles("test")
class PaymentServiceSpec extends Specification {

    @Autowired
    PaymentService paymentService

    @Autowired
    PaymentRepository paymentRepository

    @Autowired
    BillerRepository billerRepository

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    @Ignore
    @Deprecated
    def 'should post payment with status PLACED'() {
        given:
        PostPaymentCommand paymentSave = new PostPaymentCommand(billerId: 1L, paymentAmount: 100.0, accountId: 1L)

        and:
        billerRepository.findById(_) >> meralcoBiller

        and:
        paymentRepository.save(_) >> new Payment(System.currentTimeMillis(),
                paymentSave.getPaymentAmount(),
                meralcoBiller,
                LocalDateTime.now(), PaymentStatus.PLACED, paymentSave.getAccountId())

        when:
        Payment paymentResult = paymentService.postPayment(paymentSave)

        then:
        paymentResult.id != null
        paymentResult.amount.compareTo(paymentSave.getPaymentAmount()) == 0
        paymentResult.biller.id == paymentSave.billerId
        paymentResult.status == PaymentStatus.PLACED
    }

    @TestConfiguration
    @Profile("test")
    static class PaymentServiceSpecConfig {

        @Bean
        @Primary
        PaymentService overrideDefaultPaymentService(PostPaymentFacade postPaymentFacade,
                                                     PaymentRepository paymentRepository,
                                                     BillerRepository billerRepository) {
            return new PaymentServiceImpl(postPaymentFacade, paymentRepository, billerRepository)
        }
    }
}
