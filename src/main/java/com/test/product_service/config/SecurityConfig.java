package com.test.product_service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
public class SecurityConfig {

    @Value("secret.header.key")
    private  String SECRET_HEADER;
    @Value("secret.header.value")
    private  String SECRET_VALUE;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Hola si me estoy ejecutando!");
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(request -> {
                            // Obtener el header enviado
                            String gatewayHeader = request.getHeader(SECRET_HEADER);
                            return gatewayHeader != null && gatewayHeader.equals(SECRET_VALUE);
                        }).permitAll()  // Solo permite si el header es correcto
                        .anyRequest().denyAll() // Bloquea cualquier otro acceso
                );

        return http.build();
    }


}
