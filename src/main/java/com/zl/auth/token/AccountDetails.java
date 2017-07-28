package com.zl.auth.token;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public final class AccountDetails {

  private final String username;

  private final List<GrantedAuthority> authorities;

  public AccountDetails(String username, List<GrantedAuthority> authorities) {
    this.username = username;
    this.authorities = authorities;
  }

  public AccountDetails(String username) {
    this(username, Collections.emptyList());
  }

  public String getUsername() {
    return username;
  }

  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }

}
