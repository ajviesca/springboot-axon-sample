package com.ing.asia.bps3.infrastrcuture.axon.saga.payment;

import com.ing.asia.bps3.core.event.payment.command.api.DebitSourceAccountCommand;
import com.ing.asia.bps3.core.event.payment.command.api.UpdatePaymentStatusCommand;
import com.ing.asia.bps3.core.event.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.event.payment.command.api.SendPaymentCommand;
import com.ing.asia.bps3.core.event.payment.event.api.*;
import com.ing.asia.bps3.core.domain.payment.Payment;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class PaymentSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentInProgressEvent paymentInProgressEvent) {
        commandGateway.send(new DebitSourceAccountCommand(paymentInProgressEvent.getPaymentId(),
                paymentInProgressEvent.getAccountId(), paymentInProgressEvent.getBillerId(),
                paymentInProgressEvent.getAmount()));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(SourceAccountDebitedEvent sourceAccountDebitedEvent) {
        commandGateway.send(new SendPaymentCommand(sourceAccountDebitedEvent.getPaymentId(),
                sourceAccountDebitedEvent.getAccountId(), sourceAccountDebitedEvent.getBillerId(),
                sourceAccountDebitedEvent.getAmount()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentSuccessfulEvent paymentSuccessfulEvent) {
        commandGateway.send(new UpdatePaymentStatusCommand(paymentSuccessfulEvent.getPaymentId(),
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
        commandGateway.send(new UpdatePaymentStatusCommand(sourceAccountInsufficientBalanceEvent.getPaymentId(),
                sourceAccountInsufficientBalanceEvent.getAccountId(), sourceAccountInsufficientBalanceEvent.getBillerId(),
                sourceAccountInsufficientBalanceEvent.getAmount(), Payment.Status.FAILED_INSUFFICIENT_BALANCE));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentReversedEvent paymentReversedEvent) {
        commandGateway.send(new UpdatePaymentStatusCommand(paymentReversedEvent.getPaymentId(),
                paymentReversedEvent.getAccountId(), paymentReversedEvent.getBillerId(),
                paymentReversedEvent.getAmount(), Payment.Status.FAILED_AND_REVERSED));
    }
}
