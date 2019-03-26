package com.ing.asia.bps3;

import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {AxonServerAutoConfiguration.class/*, JpaEventStoreAutoConfiguration.class*/})
public class Bps3Application {

    public static void main(String[] args) {
        SpringApplication.run(Bps3Application.class, args);
    }

}
