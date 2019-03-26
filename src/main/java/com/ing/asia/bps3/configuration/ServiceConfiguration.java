package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerService;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerServiceImpl;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentService;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BillerService billerService(BillerRepository billerRepository) {
        return new BillerServiceImpl(billerRepository);
    }

    @Bean
    public PaymentService paymentService(PaymentRepository paymentRepository, BillerRepository billerRepository) {
        return new PaymentServiceImpl(paymentRepository, billerRepository);
    }
}
