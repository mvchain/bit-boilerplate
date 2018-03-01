package com.mvc.bitcoin;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author qyc
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class BitCoinBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BitCoinBootstrap.class).web(true).run(args);
    }

}
