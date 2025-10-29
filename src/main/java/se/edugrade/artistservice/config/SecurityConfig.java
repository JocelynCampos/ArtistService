package se.edugrade.artistservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/get-artists/**",
                                "/art-album/**",
                                "/art-media/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/add-artist",
                                "/upd-artist/**",
                                "/rem-artist/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.withUsername("Erik")
                .password("{noop}Edman")
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("Jocelyn")
                .password("{noop}CarrilloCampos")
                .build();

        UserDetails user3 = User.withUsername("Mohamed")
                .password("{noop}Sharshar")
                .build();

        UserDetails admin = User.withUsername("Hugo")
                .password("[noop}Ransvi")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, admin);
    }
}
