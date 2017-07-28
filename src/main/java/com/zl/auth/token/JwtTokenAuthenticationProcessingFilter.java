package com.zl.auth.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.zl.auth.model.JwtToken;
import com.zl.auth.model.Token;
import com.zl.auth.token.validation.TokenValidator;

public final class JwtTokenAuthenticationProcessingFilter
    extends AbstractAuthenticationProcessingFilter {

  private final AuthenticationFailureHandler failureHandler;

  private final TokenValidator tokenValidator;

  @Autowired
  public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
      TokenValidator tokenValidator, RequestMatcher matcher) {
    super(matcher);
    this.failureHandler = failureHandler;
    this.tokenValidator = tokenValidator;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

    String tokenString = request.getHeader(JwtConstants.JWT_TOKEN_HEADER_PARAM);

    if (tokenString == null) {
      throw new IllegalArgumentException("Error, token is null.");
    }

    String validatedToken = tokenValidator.validate(tokenString);

    Token token = new JwtToken(validatedToken);

    return getAuthenticationManager().authenticate(new JwtTokenAuthentication(token));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    failureHandler.onAuthenticationFailure(request, response, failed);

  }
}
