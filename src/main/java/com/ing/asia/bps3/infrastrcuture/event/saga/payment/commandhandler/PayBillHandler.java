package com.ing.asia.bps3.infrastrcuture.event.saga.payment.commandhandler;

import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentEntity;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.command.PayBillCommand;
import com.ing.asia.bps3.infrastrcuture.event.saga.payment.event.PaymentInProgressEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class PayBillHandler extends BaseCommandHandler<PayBillCommand> {

    private Logger LOG = LoggerFactory.getLogger(PayBillHandler.class);

    private final PaymentJPA paymentJPA;
    private final BillerJPA billerJPA;

    public PayBillHandler(EventBus eventBus, PaymentJPA paymentJPA, BillerJPA billerJPA) {
        super(eventBus);
        this.paymentJPA = paymentJPA;
        this.billerJPA = billerJPA;
    }

    @CommandHandler
    public void handle(PayBillCommand command) {
        PaymentEntity paymentEntity = new PaymentEntity(System.currentTimeMillis(),
                command.getAmount(), LocalDateTime.now());
        BillerEntity billerEntity = billerJPA.findById(command.getBillerId()).get();
        paymentEntity.setBiller(billerEntity);
        paymentEntity.setPaidByAccountId(command.getAccountId());
        paymentEntity = paymentJPA.save(paymentEntity);
        publish(new PaymentInProgressEvent(paymentEntity.getId(), paymentEntity.getPaidByAccountId(),
                billerEntity.getId(), paymentEntity.getAmount()));
    }


}
