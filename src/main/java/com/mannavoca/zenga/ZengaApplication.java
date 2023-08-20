package com.mannavoca.zenga;

import com.mannavoca.zenga.common.security.jwt.JwtProperties;
import com.mannavoca.zenga.common.security.oauth.OAuthProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class, OAuthProperties.class})
public class ZengaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZengaApplication.class, args);
    }

}
