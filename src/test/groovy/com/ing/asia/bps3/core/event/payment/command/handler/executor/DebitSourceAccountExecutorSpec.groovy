package com.ing.asia.bps3.core.event.payment.command.handler.executor

import com.ing.asia.bps3.configuration.BpsTestConfiguration
import com.ing.asia.bps3.core.domain.account.Account
import com.ing.asia.bps3.core.domain.account.AccountRepository
import com.ing.asia.bps3.core.event.payment.command.api.DebitSourceAccountCommand
import com.ing.asia.bps3.core.event.payment.event.api.SourceAccountDebitedEvent
import com.ing.asia.bps3.core.event.payment.event.api.SourceAccountInsufficientBalanceEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = [BpsTestConfiguration])
@ActiveProfiles("test")
class DebitSourceAccountExecutorSpec extends Specification {

    @Autowired
    AccountRepository accountRepository

    @Shared
    def paymentId = 1L

    @Shared
    def accountId = 1L

    @Shared
    def billerId = 1L

    @Unroll
    def "with paymentAmount = #paymentAmount and currentBalance = #currentBalance, should create #expectedEvent"() {
        given:
        def debitSourceAccountCommand = new DebitSourceAccountCommand(paymentId, accountId, billerId, paymentAmount)

        and:
        def debitSourceAccountExecutor = new DebitSourceAccountExecutor(accountRepository, debitSourceAccountCommand)

        and:
        accountRepository.findById(accountId) >> new Account(accountId, "Juan dela Cruz", currentBalance)

        and:
        accountRepository.save(_) >> new Account(accountId, "Juan dela Cruz", currentBalance - paymentAmount)

        when:
        def resultEvent = debitSourceAccountExecutor.execute().getResultEvent()

        then:
        resultEvent.class.simpleName == expectedEvent

        where:
        currentBalance | paymentAmount || expectedEvent
        50             | 100           || SourceAccountInsufficientBalanceEvent.simpleName
        50             | 50.5          || SourceAccountInsufficientBalanceEvent.simpleName
        100            | 50            || SourceAccountDebitedEvent.simpleName
        100            | 100           || SourceAccountDebitedEvent.simpleName

    }
}
