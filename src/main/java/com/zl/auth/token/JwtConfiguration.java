package com.zl.auth.token;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;

import com.zl.auth.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Configuration
public class JwtConfiguration {


  public Jws<Claims> parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(JwtConstants.JWT_TOKEN_SIGN_KEY).parseClaimsJws(token);
    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException
        | SignatureException ex) {
      throw new BadCredentialsException("Invalid JWT token: ", ex);
    } catch (ExpiredJwtException expiredEx) {
      throw new TokenExpiredException("JWT Token is expired");
    }
  }

}
