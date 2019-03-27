package com.ing.asia.bps3.presentation.payment


import com.ing.asia.bps3.configuration.ServiceTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import groovy.json.JsonSlurper
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [PaymentController])
@ActiveProfiles("test")
class PaymentControllerSpecification extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    PaymentService paymentService

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    def "should return payment history"() {
        given:
        LocalDateTime postedDate = LocalDateTime.now();
        List<Payment> payments = [
                new Payment(1L, new BigDecimal(1000), meralcoBiller, postedDate, Payment.Status.COMPLETED,
                        1L)
        ]

        and:
        paymentService.getPaymentHistory() >> payments;

        when:
        def result = mockMvc.perform(get("/payment/history"))

        then:
        result.andExpect(status().isOk())
        def responseContent = new JsonSlurper().parseText(result.andReturn().response.contentAsString)

        responseContent.size() == 1

        def resultFirstPayment = responseContent[0]
        def sourceFirstPayment = payments[0]
        resultFirstPayment.id == sourceFirstPayment.id
        resultFirstPayment.biller.id == sourceFirstPayment.biller.id
    }

    @TestConfiguration
    @Import([ServiceTestConfiguration])
    static class PaymentControllerSpecificationConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

        @Bean
        CommandGateway commandGateway() {
            return detachedMockFactory.Stub(CommandGateway)
        }
    }
}
