package com.ing.asia.bps3.core.event.payment.command.handler.executor

import com.ing.asia.bps3.configuration.BpsTestConfiguration
import com.ing.asia.bps3.core.domain.biller.Biller
import com.ing.asia.bps3.core.domain.payment.Payment
import com.ing.asia.bps3.core.domain.payment.PaymentRepository
import com.ing.asia.bps3.core.event.payment.command.api.UpdatePaymentStatusCommand
import com.ing.asia.bps3.core.event.payment.event.api.PaymentEndedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

import static com.ing.asia.bps3.core.domain.payment.PaymentStatus.*

@SpringBootTest(classes = [BpsTestConfiguration])
@ActiveProfiles("test")
class UpdatePaymentStatusExecutorSpec extends Specification {

    @Autowired
    PaymentRepository paymentRepository

    @Shared
    def paymentId = 1L

    @Shared
    def accountId = 1L

    @Shared
    def billerId = 1L

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    @Unroll
    def 'should update Payment status to #arg and create PaymentEndedEvent'() {
        given:
        def updatePaymentStatusCommand = new UpdatePaymentStatusCommand(paymentId, accountId, billerId, 100, arg)

        and:
        def updatePaymentStatusExecutor = new UpdatePaymentStatusExecutor(paymentRepository, updatePaymentStatusCommand)

        and:
        paymentRepository.findById(paymentId) >> new Payment(paymentId, 100, meralcoBiller, LocalDateTime.now(), arg, accountId)

        and:
        paymentRepository.update(_) >> new Payment(paymentId, 100, meralcoBiller, LocalDateTime.now(), expected, accountId)

        when:
        def resultEvent = updatePaymentStatusExecutor.execute().getResultEvent()

        then:
        verifyAll {
            updatePaymentStatusExecutor.payment.status == expected
            resultEvent instanceof PaymentEndedEvent
        }

        where:
        arg                         || expected
        COMPLETED                   || COMPLETED
        FAILED_AND_REVERSED         || FAILED_AND_REVERSED
        FAILED_INSUFFICIENT_BALANCE || FAILED_INSUFFICIENT_BALANCE

    }
}
