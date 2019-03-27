package com.ing.asia.bps3.infrastructure.domain.payment

import com.ing.asia.bps3.configuration.RepositoryConfiguration
import com.ing.asia.bps3.configuration.ServiceTestConfiguration
import com.ing.asia.bps3.core.domain.biller.BillerRepository
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentServiceImpl
import com.ing.asia.bps3.infrastrcuture.domain.payment.PostPaymentSave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = [PaymentControllerSpecificationConfig])
@ActiveProfiles(["test", "mockAccountService", "mockBillerService"])
class PaymentServiceSpecification extends Specification {

    @Autowired
    PaymentService paymentService;

    def 'should definitely fail'() {
        given:
        PostPaymentSave paymentSave = new PostPaymentSave();

        when:
        paymentService.postPayment(paymentSave);

        then:
        1 == 1
    }

    @TestConfiguration
    @Import([RepositoryConfiguration, ServiceTestConfiguration])
    static class PaymentControllerSpecificationConfig {

        @Bean
        PaymentService paymentService(PaymentRepository paymentRepository, BillerRepository billerRepository) {
            return new PaymentServiceImpl(paymentRepository, billerRepository)
        }
    }
}
