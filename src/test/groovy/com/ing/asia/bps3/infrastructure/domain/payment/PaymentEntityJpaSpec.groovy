package com.ing.asia.bps3.infrastructure.domain.payment


import com.ing.asia.bps3.core.domain.payment.PaymentStatus
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntity
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA
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
    PaymentJPA paymentJPA;

    def setup() {
        paymentJPA.deleteAll();
    }

    def "should retrieve payment record"() {
        given: "data persisted by entity manager"
        def paymentId = System.currentTimeMillis();
        entityManager.persist(new PaymentEntity(id: paymentId, amount: 100, postDate: LocalDateTime.now(),
                status: PaymentStatus.PLACED))

        when: "payment retrieved by id"
        Optional<PaymentEntity> payment = paymentJPA.findById(paymentId);

        then: "payment should not be null"
        payment.isPresent()

    }

    @Unroll
    def "should retrieve #expectedRecordCount records "() {
        given: "#numberOfPayments payments persisted by entity manager"
        for (PaymentEntity payment : createPayments(numberOfPayments)) {
            entityManager.persist(payment);
        }

        when: "all payment records are retrieved"
        List<PaymentEntity> results = paymentJPA.findAll()

        then: "results.size should be #expectedRecordCount"
        results.size() == expectedRecordCount

        where:
        numberOfPayments || expectedRecordCount
        1                || 1
        5                || 5
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
