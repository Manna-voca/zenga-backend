package com.mannavoca.zenga.common.config.openfeign;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.mannavoca.zenga"})
public class OpenFeignConfig {

    @Bean
    Logger.Level riotFeignClientLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new OpenFeignError();
    }
}
