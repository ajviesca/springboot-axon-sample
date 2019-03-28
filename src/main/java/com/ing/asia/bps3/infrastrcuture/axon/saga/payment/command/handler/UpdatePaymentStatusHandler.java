package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.event.payment.command.api.UpdatePaymentStatusCommand;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.UpdatePaymentStatusExecutor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class UpdatePaymentStatusHandler extends BaseCommandHandler<UpdatePaymentStatusCommand> {
    private final PaymentRepository paymentRepository;

    public UpdatePaymentStatusHandler(EventBus eventBus, PaymentRepository paymentRepository) {
        super(eventBus);
        this.paymentRepository = paymentRepository;
    }

    @CommandHandler
    public void handle(UpdatePaymentStatusCommand command) {
        publish(new UpdatePaymentStatusExecutor(paymentRepository, command).execute());
    }
}
