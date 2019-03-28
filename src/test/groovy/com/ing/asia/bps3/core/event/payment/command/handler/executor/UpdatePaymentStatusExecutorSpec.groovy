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

@SpringBootTest(classes = [BpsTestConfiguration])
@ActiveProfiles("test")
class UpdatePaymentStatusExecutorSpec extends Specification {

    @Autowired
    PaymentRepository paymentRepository;

    @Shared
    def paymentId = 1L

    @Shared
    def accountId = 1L

    @Shared
    def billerId = 1L

    @Shared
    Biller meralcoBiller = new Biller(1L, "Meralco")

    @Unroll
    def 'should update Payment status to #arg'() {
        given:
        def updatePaymentStatusCommand = new UpdatePaymentStatusCommand(paymentId, accountId, billerId, 100, arg)

        and:
        def updatePaymentStatusExecutor = new UpdatePaymentStatusExecutor(paymentRepository, updatePaymentStatusCommand)

        and:
        paymentRepository.findById(paymentId) >> new Payment(paymentId, 100, meralcoBiller, LocalDateTime.now(), arg, accountId)

        and:
        paymentRepository.update(_) >> new Payment(paymentId, 100, meralcoBiller, LocalDateTime.now(), expected, accountId)

        when:
        def resultEvent = updatePaymentStatusExecutor.execute()

        then:
        verifyAll {
            updatePaymentStatusExecutor.payment.status.equals(expected)
            resultEvent instanceof PaymentEndedEvent
            resultEvent.paymentId == paymentId
            resultEvent.accountId == accountId;
            resultEvent.billerId == billerId
            resultEvent.amount.compareTo(100) == 0
        }

        where:
        arg                                        || expected
        Payment.Status.COMPLETED                   || Payment.Status.COMPLETED
        Payment.Status.FAILED_AND_REVERSED         || Payment.Status.FAILED_AND_REVERSED
        Payment.Status.FAILED_INSUFFICIENT_BALANCE || Payment.Status.FAILED_INSUFFICIENT_BALANCE

    }
}
