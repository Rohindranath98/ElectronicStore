package com.lcwd.electronic.store.config;

import com.lcwd.electronic.store.security.JwtAuthenticationEntryPoint;
import com.lcwd.electronic.store.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;


    /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normal= User.builder()
                .username("Rohindra")
                .password(passwordEncoder().encode("Rohindra123@"))
                .roles("NORMAL")

                .build();
        UserDetails admin=User.builder()
                .username("Rishu")
                .password(passwordEncoder().encode("Rishu123@"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normal,admin);



    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       //   http.authorizeRequests()
        //         .anyRequest()
           //       .authenticated()
             //     .and()
               //   .formLogin()
                //  .loginPage("login.html")
               //   .loginProcessingUrl("/process-url")
               //   .defaultSuccessUrl("/dashboard")
                //  .failureUrl("/error")
                //  .and()
                //  .logout()
                 // .logoutUrl("/do-logout");

               http.csrf()
                       .disable()
                       .cors()
                       .disable()
                       .authorizeRequests()
                       .antMatchers("/login")
                       .permitAll()
                       .anyRequest()
                       .authenticated()
                       .and()
                       .exceptionHandling()
                       .authenticationEntryPoint(authenticationEntryPoint)
                       .and()
                       .sessionManagement()
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

               http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

               return http.build();


    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
   }




}
