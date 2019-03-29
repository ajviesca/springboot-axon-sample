package com.ing.asia.bps3.infrastructure.domain.payment


import com.ing.asia.bps3.core.domain.payment.PaymentStatus
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntity
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntityJPA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

@DataJpaTest
class PaymentEntityJpaSpec extends Specification {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PaymentEntityJPA paymentJPA;

    def setup() {
        paymentJPA.deleteAll();
    }

    def "should retrieve payment record"() {
        given: "data persisted by entity manager"
        def paymentId = System.currentTimeMillis();
        entityManager.persist(new PaymentEntity(id: paymentId, amount: 100, postDate: LocalDateTime.now(),
                status: PaymentStatus.PLACED))

        when: "paymentEntityJPA.findById is called"
        PaymentEntity payment = paymentJPA.findById(paymentId).get()

        then: "payment should not be null"
        payment != null

    }

    @Unroll
    def "should retrieve #expectedRecordCount  records "() {
        given: "list of payments persisted by entity manager"
        for (PaymentEntity payment : createPayments(numberOfPayments)) {
            entityManager.persist(payment);
        }

        when: "paymentJPA.findAll is called"
        List<PaymentEntity> results = paymentJPA.findAll()

        then: "results.size should be #expectedRecordCount"
        results.size() == expectedRecordCount

        where:
        numberOfPayments    ||  expectedRecordCount
        1                   ||  1
        5                   ||  5
    }

    List<PaymentEntity> createPayments(int numberOfPayments) {
        List<PaymentEntity> payments = new ArrayList<>();

        for (int i = 0; i < numberOfPayments; i++) {
            payments.add(
                    new PaymentEntity(id: System.currentTimeMillis() + i, amount: System.currentTimeMillis(), postDate: LocalDateTime.now(),
                            status: PaymentStatus.PLACED)
            )
        }

        payments
    }
}
