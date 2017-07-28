package com.zl.auth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.zl.auth.model.Token;
import com.zl.auth.service.JwsTokenService;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwsTokenService tokenService;

  @Autowired
  public JwtAuthenticationProvider(JwsTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    Token accessToken = (Token) authentication.getPrincipal();

    String token = accessToken.getToken();

    AccountDetails accountDetails = tokenService.jwsClaimsToUserContext(token);

    return new JwtAccountAuthentication(accountDetails);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtTokenAuthentication.class.isAssignableFrom(authentication));
  }
}
