package com.mannavoca.zenga;

import com.mannavoca.zenga.common.security.jwt.JwtProperties;
import com.mannavoca.zenga.common.security.oauth.OAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class, OAuthProperties.class})
public class ZengaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZengaApplication.class, args);
    }

}
