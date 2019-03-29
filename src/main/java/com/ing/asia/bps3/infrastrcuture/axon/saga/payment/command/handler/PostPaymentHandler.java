package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.PostPaymentExecutor;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentInProgressEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class PostPaymentHandler extends BaseCommandHandler<PostPaymentCommand, PaymentInProgressEvent> {

    private final PaymentRepository paymentRepository;
    private final BillerRepository billerRepository;

    public PostPaymentHandler(EventBus eventBus, PaymentRepository paymentRepository, BillerRepository billerRepository) {
        super(eventBus);
        this.paymentRepository = paymentRepository;
        this.billerRepository = billerRepository;
    }

    @CommandHandler
    public void handle(PostPaymentCommand command) {
        publish(new PostPaymentExecutor(billerRepository, paymentRepository, command).execute().getResultEvent());
    }


}
