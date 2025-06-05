package com.sampleproject.sampleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->{
            // service authentication matching
            auth
                    .requestMatchers("/bootstrap-5.0.2-dist/**").permitAll()
                    .requestMatchers("/fontawesome-free-6.71-web/**").permitAll()
                    .requestMatchers("/create-admin").permitAll()
                    .requestMatchers("/index/**").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/dashboard").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .requestMatchers("/employee/**").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .requestMatchers("/privilege/**").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .requestMatchers("/user/**").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .requestMatchers("/transport/**").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .requestMatchers("/venue").hasAnyAuthority("Admin" , "Travel Agent" , "Vehicle Owner" , "Guider" , "Driver")
                    .anyRequest().authenticated();

        })
                // login authentication
                .formLogin(login ->{
            login
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard" , true)
                    .failureUrl("/login?error=usernamepassworderror")
                    .usernameParameter("username")
                    .passwordParameter("password");

        })
                // logout authentication
                .logout(logout ->{
            logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login");
        })
                .exceptionHandling(exception ->{
                    exception.accessDeniedPage("/errorPage");
                })
                .csrf(csrf ->{
                    csrf.disable();;
                });
    return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
