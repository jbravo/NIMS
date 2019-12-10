package com.viettel.nims.geo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created by VTN-PTPM-NV64 on 7/10/2019.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  protected CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and().authorizeRequests()
        .anyRequest().permitAll()
        .and().csrf().disable();
  }
}
