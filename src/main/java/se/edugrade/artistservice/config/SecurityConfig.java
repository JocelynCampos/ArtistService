package se.edugrade.artistservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import se.edugrade.artistservice.converter.JwtAuthConverter;

@Configuration
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/edufy/v1/artist/test",
                                "/h2-console/**",
                                "/edufy/v1/artist/all").permitAll()


                        .requestMatchers(HttpMethod.GET, "/edufy/v1/artist/albums").hasRole("user")
                        .requestMatchers(HttpMethod.GET,  "/edufy/v1/artist/media").hasRole("user")
                        .requestMatchers(HttpMethod.GET,"/edufy/v1/artist/**").hasAnyRole("user", "admin")
                        .requestMatchers(HttpMethod.POST, "/edufy/v1/artist/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT,  "/edufy/v1/artist/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE,"/edufy/v1/artist/**").hasRole("admin")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(auth2 -> auth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        return http.build();
    }

}
