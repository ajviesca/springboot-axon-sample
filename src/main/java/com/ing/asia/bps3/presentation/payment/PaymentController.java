package com.ing.asia.bps3.presentation.payment;

import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentEndedEvent;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PostPaymentSave;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final CommandGateway commandGateway;

    private DeferredResult<ResponseEntity<Payment>> deferredResult;

    public PaymentController(PaymentService paymentService, CommandGateway commandGateway) {
        this.paymentService = paymentService;
        this.commandGateway = commandGateway;
    }

    @PostMapping("/post-payment")
    public ResponseEntity<Payment> postPayment(@RequestBody PostPaymentSave postPaymentSave) {
        return ResponseEntity.ok(paymentService.postPayment(postPaymentSave));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Payment>> getPaymentHistory() {
        return ResponseEntity.ok(paymentService.getPaymentHistory());
    }

    @PostMapping("/post-payment-axon")
    public DeferredResult<ResponseEntity<Payment>> postPaymentAxon(@RequestBody PostPaymentCommand postPaymentCommand) {
        deferredResult = new DeferredResult();
        commandGateway.sendAndWait(postPaymentCommand);
        return deferredResult;
    }

    @EventHandler
    public void on(PaymentEndedEvent paymentEndedEvent) {
        Payment payment = paymentService.findById(paymentEndedEvent.getPaymentId());
        deferredResult.setResult(ResponseEntity.ok(payment));
    }
}
