package com.ing.asia.bps3.configuration;

import com.ing.asia.bps3.core.entity.biller.BillerRepository;
import com.ing.asia.bps3.core.entity.payment.PaymentRepository;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerJPA;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerRepositoryImpl;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentJPA;
import com.ing.asia.bps3.infrastrcuture.domain.payment.PaymentRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public BillerRepository billerRepository(BillerJPA billerJPA) {
        return new BillerRepositoryImpl(billerJPA);
    }

    @Bean
    public PaymentRepository paymentRepository(PaymentJPA paymentJPA) {
        return new PaymentRepositoryImpl(paymentJPA);
    }
}
