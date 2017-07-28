package com.zl.auth.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.auth.token.AccountDetails;
import com.zl.auth.token.JwtConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service
public class JwsTokenService {

  private final JwtConfiguration settings;

  private final DateTime currentTime = new DateTime();

  @Autowired
  public JwsTokenService(JwtConfiguration settings) {
    this.settings = settings;
  }

  public AccountDetails jwsClaimsToUserContext(String token) {
    Jws<Claims> jwsClaims = settings.parseClaims(token);
    String subject = jwsClaims.getBody().getSubject();
    return new AccountDetails(subject);
  }

  public boolean hasTokenExpired(String token) {
    Jws<Claims> jwsClaims = settings.parseClaims(token);
    Claims bodyClaims = jwsClaims.getBody();
    Date expiryDate = bodyClaims.getExpiration();
    boolean hasExpired = expiryDate.after(currentTime.toDate());

    return hasExpired;

  }
}
