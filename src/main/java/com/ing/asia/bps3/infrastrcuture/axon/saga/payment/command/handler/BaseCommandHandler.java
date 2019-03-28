package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.handler;

import com.ing.asia.bps3.core.event.payment.command.api.BasePaymentCommand;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;

public abstract class BaseCommandHandler<PAYMENT_COMMAND extends BasePaymentCommand> {
    private final EventBus eventBus;

    public BaseCommandHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    protected void publish(Object payload) {
        eventBus.publish(new GenericEventMessage<>(payload));
    }

    public abstract void handle(PAYMENT_COMMAND command);

}
