package com.zl.auth.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAccountAuthentication extends AbstractAuthenticationToken {

  private static final long serialVersionUID = -4658791720734575074L;

  private final AccountDetails accountDetails;

  public JwtAccountAuthentication(AccountDetails accountDetails) {
    super(accountDetails.getAuthorities());
    this.accountDetails = accountDetails;
    this.eraseCredentials();
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return accountDetails;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
