package com.ing.asia.bps3.infrastrcuture.axon.saga.payment;

import com.ing.asia.bps3.core.domain.payment.PaymentStatus;
import com.ing.asia.bps3.core.event.payment.command.api.DebitSourceAccountCommand;
import com.ing.asia.bps3.core.event.payment.command.api.ReversePaymentCommand;
import com.ing.asia.bps3.core.event.payment.command.api.SendPaymentToBillerCommand;
import com.ing.asia.bps3.core.event.payment.command.api.UpdatePaymentStatusCommand;
import com.ing.asia.bps3.core.event.payment.event.api.*;
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
                paymentInProgressEvent.getPaymentAmount()));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(SourceAccountDebitedEvent sourceAccountDebitedEvent) {
        commandGateway.send(new SendPaymentToBillerCommand(sourceAccountDebitedEvent.getPaymentId(),
                sourceAccountDebitedEvent.getAccountId(), sourceAccountDebitedEvent.getBillerId(),
                sourceAccountDebitedEvent.getPaymentAmount()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentSuccessfulEvent paymentSuccessfulEvent) {
        commandGateway.send(new UpdatePaymentStatusCommand(paymentSuccessfulEvent.getPaymentId(),
                paymentSuccessfulEvent.getAccountId(), paymentSuccessfulEvent.getBillerId(),
                paymentSuccessfulEvent.getPaymentAmount(), PaymentStatus.COMPLETED));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentFailedEvent paymentFailedEvent) {
        commandGateway.send(new ReversePaymentCommand(paymentFailedEvent.getPaymentId(),
                paymentFailedEvent.getAccountId(), paymentFailedEvent.getBillerId(),
                paymentFailedEvent.getPaymentAmount()));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(SourceAccountInsufficientBalanceEvent sourceAccountInsufficientBalanceEvent) {
        commandGateway.send(new UpdatePaymentStatusCommand(sourceAccountInsufficientBalanceEvent.getPaymentId(),
                sourceAccountInsufficientBalanceEvent.getAccountId(), sourceAccountInsufficientBalanceEvent.getBillerId(),
                sourceAccountInsufficientBalanceEvent.getPaymentAmount(), PaymentStatus.FAILED_INSUFFICIENT_BALANCE));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void on(PaymentReversedEvent paymentReversedEvent) {
        commandGateway.send(new UpdatePaymentStatusCommand(paymentReversedEvent.getPaymentId(),
                paymentReversedEvent.getAccountId(), paymentReversedEvent.getBillerId(),
                paymentReversedEvent.getPaymentAmount(), PaymentStatus.FAILED_AND_REVERSED));
    }
}
