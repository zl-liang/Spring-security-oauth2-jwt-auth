package com.zl.auth.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.zl.auth.annotations.NoAuthentication;
import com.zl.auth.token.JwtConstants;

import io.jsonwebtoken.lang.Collections;

@Component
public class GetPathWithOutTokenListener {

  @Autowired
  private RequestMappingHandlerMapping handlerMapping;

  @Autowired
  private JwtConstants jwtConstants;

  public List<String> getAntPaths() {
    List<String> pathList = new ArrayList<String>();
    Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
    Iterator<?> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      @SuppressWarnings("rawtypes")
      Map.Entry entry = (Map.Entry) iterator.next();
      HandlerMethod method = (HandlerMethod) entry.getValue();
      NoAuthentication noAuthentication = method.getMethodAnnotation(NoAuthentication.class);
      if (null != noAuthentication) {
        String uri = "";
        RequestMappingInfo info = (RequestMappingInfo) entry.getKey();
        Object[] objects = info.getPatternsCondition().getPatterns().toArray();
        if (objects.length > 0) {
          uri = (String) objects[0];
        }
        uri = uri.replaceAll("\\{[^}]*\\}", "*");
        pathList.add(uri);
      }
    }
    if (!Collections.isEmpty(jwtConstants.getPathList())) {
      pathList.addAll(pathList);
    }
    return pathList;
  }

  public String[] getPermitPath() {
    List<String> path = getAntPaths();
    String[] pathArray = path.toArray(new String[path.size()]);
    return pathArray;
  }



}
