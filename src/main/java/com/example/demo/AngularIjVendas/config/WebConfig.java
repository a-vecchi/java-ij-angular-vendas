package com.example.demo.AngularIjVendas.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> crosFilterFilterRegistrationBean() {
        List<String> all = Arrays.asList("*");

        CorsConfiguration crosConfiguration = new CorsConfiguration();
        crosConfiguration.setAllowedOriginPatterns(all);
        crosConfiguration.setAllowedHeaders(all);
        crosConfiguration.setAllowedMethods(all);
        crosConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", crosConfiguration);

        CorsFilter crosFilter = new CorsFilter(source);
        FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(crosFilter);
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return filter;
    }
}
