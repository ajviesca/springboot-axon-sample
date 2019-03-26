package com.ing.asia.bps3.presentation.payment

import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [PaymentController])
class PaymentControllerSpecification extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    PaymentService paymentService

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    def "should return payment history"() {
        given:
        LocalDateTime postedDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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
        result.andReturn().response.contentAsString == '[{"id":1,"amount":1000,"biller":{"id":1,"billerName":"Meralco"},"postDate":"' +postedDate+ '","status":"COMPLETED","paidByAccountId":1}]'

    }

    @TestConfiguration
    static class StubServiceConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

        @Bean
        PaymentService paymentService() {
            return detachedMockFactory.Stub(PaymentService);
        }

        @Bean
        CommandGateway commandGateway() {
            return detachedMockFactory.Stub(CommandGateway)
        }
    }
}
