package com.example.demo.AngularIjVendas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.headers()
                .frameOptions()
                .sameOrigin();
        http.authorizeRequests()
                .antMatchers("/**/oauth/token",
                        "/h2-console/**", "/favicon.ico",
                        "/api/usuarios").permitAll()
                .antMatchers(
                        "/api/clientes/**",
                        "/api/servicos-prestados/**").authenticated()
                .anyRequest().denyAll();
    }
}
