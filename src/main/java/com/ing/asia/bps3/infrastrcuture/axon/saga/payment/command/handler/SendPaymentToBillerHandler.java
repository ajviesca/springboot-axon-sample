package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.event.payment.command.api.SendPaymentToBillerCommand;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.SendPaymentToBillerExecutor;
import com.ing.asia.bps3.core.event.payment.event.api.BasePaymentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;

public class SendPaymentToBillerHandler extends BaseCommandHandler<SendPaymentToBillerCommand, BasePaymentEvent> {

    public SendPaymentToBillerHandler(EventBus eventBus) {
        super(eventBus);
    }

    @CommandHandler
    public void handle(SendPaymentToBillerCommand command) {
        publish(new SendPaymentToBillerExecutor(command).execute().getResultEvent());
    }
}
