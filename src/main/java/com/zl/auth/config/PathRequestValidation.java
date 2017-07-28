package com.zl.auth.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.zl.auth.listener.GetPathWithOutTokenListener;

@Component
public class PathRequestValidation implements RequestMatcher {

  private List<String> antPaths = new ArrayList<String>();

  @Autowired
  private GetPathWithOutTokenListener getPathWithOutTokenListener;

  @Override
  public boolean matches(HttpServletRequest request) {
    antPaths = getPathWithOutTokenListener.getAntPaths();
    boolean result = true;
    for (String rm : antPaths) {
      if (new AntPathRequestMatcher(rm).matches(request)) {
        result = false;
      }
    }
    return result;
  }

}
