package com.ing.asia.bps3.core.axon.saga.payment;

import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.DebitSourceCommand;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.EndPaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.command.api.SendPaymentCommand;
import com.ing.asia.bps3.core.axon.saga.payment.event.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class PaymentSaga {

    private static final transient Logger LOG = LoggerFactory.getLogger(PaymentSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentInProgressEvent paymentInProgressEvent) {
        commandGateway.send(new DebitSourceCommand(paymentInProgressEvent.getPaymentId(),
                paymentInProgressEvent.getAccountId(), paymentInProgressEvent.getBillerId(),
                paymentInProgressEvent.getAmount()));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(SourceDebitedEvent sourceDebitedEvent) {
        commandGateway.send(new SendPaymentCommand(sourceDebitedEvent.getPaymentId(),
                sourceDebitedEvent.getAccountId(), sourceDebitedEvent.getBillerId(),
                sourceDebitedEvent.getAmount()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentSuccessfulEvent paymentSuccessfulEvent) {
        commandGateway.send(new EndPaymentCommand(paymentSuccessfulEvent.getPaymentId(),
                paymentSuccessfulEvent.getAccountId(), paymentSuccessfulEvent.getBillerId(),
                paymentSuccessfulEvent.getAmount(), Payment.Status.COMPLETED));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentFailedEvent paymentFailedEvent) {
        commandGateway.send(new ReversePaymentCommand(paymentFailedEvent.getPaymentId(),
                paymentFailedEvent.getAccountId(), paymentFailedEvent.getBillerId(),
                paymentFailedEvent.getAmount()));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(SourceAccountInsufficientBalanceEvent sourceAccountInsufficientBalanceEvent) {
        commandGateway.send(new EndPaymentCommand(sourceAccountInsufficientBalanceEvent.getPaymentId(),
                sourceAccountInsufficientBalanceEvent.getAccountId(), sourceAccountInsufficientBalanceEvent.getBillerId(),
                sourceAccountInsufficientBalanceEvent.getAmount(), Payment.Status.FAILED_INSUFFICIENT_BALANCE));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentReversedEvent paymentReversedEvent) {
        commandGateway.send(new EndPaymentCommand(paymentReversedEvent.getPaymentId(),
                paymentReversedEvent.getAccountId(), paymentReversedEvent.getBillerId(),
                paymentReversedEvent.getAmount(), Payment.Status.FAILED_AND_REVERSED));
    }
}
