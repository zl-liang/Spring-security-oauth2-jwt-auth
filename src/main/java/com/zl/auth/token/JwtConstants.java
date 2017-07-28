package com.zl.auth.token;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "noAuthenticationPath")
public final class JwtConstants {

  @Setter
  @Getter
  private List<String> pathList = new ArrayList<String>();

  public JwtConstants() {}

  public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";

  public static final String JWT_TOKEN_SIGN_KEY = "adasda";

}
