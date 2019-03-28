package com.ing.asia.bps3.infrastructure.domain.payment

import com.ing.asia.bps3.configuration.BpsTestConfiguration
import com.ing.asia.bps3.configuration.JpaTestConfiguration
import com.ing.asia.bps3.configuration.RepositoryTestConfiguration
import com.ing.asia.bps3.configuration.ServiceTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.biller.BillerRepository
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentServiceImpl
import com.ing.asia.bps3.infrastrcuture.domain.payment.PostPaymentSave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = [BpsTestConfiguration, PaymentControllerSpecificationConfig])
@ActiveProfiles("test")
class PaymentServiceSpecification extends Specification {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BillerRepository billerRepository;

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    def 'should post payment with status PLACED'() {
        given:
        PostPaymentSave paymentSave = new PostPaymentSave(1L, 100.0, 1L)

        and:
        billerRepository.findById(_) >> meralcoBiller

        and:
        paymentRepository.save(_) >> new Payment(System.currentTimeMillis(),
                paymentSave.getAmount(),
                meralcoBiller,
                LocalDateTime.now(), Payment.Status.PLACED, paymentSave.getAccountId())

        when:
        Payment paymentResult = paymentService.postPayment(paymentSave);

        then:
        paymentResult.id != null;
        paymentResult.amount.compareTo(paymentSave.amount) == 0
        paymentResult.biller.id == paymentSave.billerId
        paymentResult.status == Payment.Status.PLACED
    }

    @TestConfiguration
    @Profile("test")
    static class PaymentControllerSpecificationConfig {

        @Bean
        @Primary
        PaymentService overrideDefaultPaymentService(PaymentRepository paymentRepository, BillerRepository billerRepository) {
            return new PaymentServiceImpl(paymentRepository, billerRepository)
        }
    }
}
