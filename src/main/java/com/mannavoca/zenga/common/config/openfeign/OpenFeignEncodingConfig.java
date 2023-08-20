package com.mannavoca.zenga.common.config.openfeign;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class OpenFeignEncodingConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder formEncoder() {
        return new FormEncoder(new SpringEncoder(this.messageConverters));
    }
}
