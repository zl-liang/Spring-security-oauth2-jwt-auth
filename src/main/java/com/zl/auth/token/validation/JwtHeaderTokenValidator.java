package com.zl.auth.token.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JwtHeaderTokenValidator implements TokenValidator {

  private static String HEADER_PREFIX = "Bearer";

  private static final int HEADER_PREFIX_SIZE = HEADER_PREFIX.length() + 1;

  private static final String HEADER_BLANK_ERROR = "Error! Authorization header is blank.";

  private static final String HEADER_INVALID_ERROR = "Error! Authorization header is invalid.";

  private static final String TOKEN_INVALID_ERROR = "Error! Token is invalid.";

  @Override
  public String validate(String header) {

    if (StringUtils.isBlank(header)) {
      throw new AuthenticationServiceException(HEADER_BLANK_ERROR);
    }

    if (!header.contains(HEADER_PREFIX)) {
      throw new AuthenticationServiceException(HEADER_INVALID_ERROR);
    }

    String token = header.substring(HEADER_PREFIX_SIZE, header.length());

    if (token == null) {
      throw new IllegalArgumentException(TOKEN_INVALID_ERROR);
    }

    return token;
  }
}
