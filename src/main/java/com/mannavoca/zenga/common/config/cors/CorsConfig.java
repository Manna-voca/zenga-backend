package com.mannavoca.zenga.common.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://bucolic-empanada-eb0720.netlify.app");
        config.addAllowedOrigin("http://bucolic-empanada-eb0720.netlify.app");
        config.addAllowedOrigin("https://main.d3io9tfwnwde06.amplifyapp.com");
        config.addAllowedOrigin("https://zenga.club");
        config.addAllowedOrigin("https://*.netlify.app");
        config.addAllowedOrigin("https://*.zenga.club");
        config.addAllowedOrigin("https://www.zenga.club");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
