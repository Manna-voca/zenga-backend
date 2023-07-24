package com.mannavoca.zenga;

import com.mannavoca.zenga.common.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class})
public class ZengaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZengaApplication.class, args);
    }

}
