package com.zl.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.zl.auth.listener.GetPathWithOutTokenListener;
import com.zl.auth.token.JwtAuthenticationProvider;
import com.zl.auth.token.JwtTokenAuthenticationProcessingFilter;
import com.zl.auth.token.validation.TokenValidator;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationFailureHandler failureHandler;

  @Autowired
  private TokenValidator tokenValidator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Autowired
  private GetPathWithOutTokenListener getPathWithOutTokenListener;

  @Autowired
  private PathRequestValidation pathRequestValidation;

  public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter()
      throws Exception {
    JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
        failureHandler, tokenValidator, pathRequestValidation);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String[] pathPermit = getPathWithOutTokenListener.getPermitPath();
    http.csrf().disable().exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint()).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers(pathPermit).permitAll().and().authorizeRequests().anyRequest().authenticated()
        .and()
        .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);
  }

}
