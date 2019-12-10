package com.viettel.nims.commons.filter;

import com.viettel.nims.commons.client.form.ValidateForm;
import com.viettel.nims.commons.util.Constant;
import com.viettel.nims.commons.util.UserTokenVsmart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Service
public class TokenFilter extends OncePerRequestFilter {

  private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
      MediaType.valueOf("text/*"),
      MediaType.APPLICATION_FORM_URLENCODED,
      MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_XML,
      MediaType.valueOf("application/*+json"),
      MediaType.valueOf("application/*+xml"),
      MediaType.MULTIPART_FORM_DATA
  );
  private UserTokenVsmart userTokenVsmart;

  @Autowired
  public TokenFilter(UserTokenVsmart userTokenVsmart) {
    this.userTokenVsmart = userTokenVsmart;
  }

  private static void logRequestBody(ContentCachingRequestWrapper request, String prefix) {
    val content = request.getContentAsByteArray();
    if (content.length > 0) {
      logContent(content, request.getContentType(), request.getCharacterEncoding(), prefix);
    }
  }

  private static void logParameterRequest(ContentCachingRequestWrapper request, String prefix) {
    StringBuilder sb = new StringBuilder(request.getRequestURI()).append(":{");
    Map<String, String[]> parameter = request.getParameterMap();
    parameter.forEach((key, value) -> {
      sb.append(key).append(":").append(value[0]).append(";");
    });
    sb.append("}");
    log.info("{} {}", prefix, sb.toString());
  }

  private static void logResponse(ContentCachingResponseWrapper response, String prefix) {
    val status = response.getStatus();
    log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
    response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName)
        .forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue))
    );
    val content = response.getContentAsByteArray();
    if (content.length > 0) {
      logContent(content, response.getContentType(), response.getCharacterEncoding(), prefix);
    }
  }

  private static void logContent(byte[] content, String contentType, String contentEncoding,
      String prefix) {
    val mediaType = MediaType.valueOf(contentType);
    val visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
    if (visible) {
      try {
        val contentString = new String(content, contentEncoding);
        Stream.of(contentString.split("\r\n|\r|\n"))
            .forEach(line -> log.info("{} {}", prefix, line));
      } catch (UnsupportedEncodingException e) {
        log.info("{} [{} bytes content]", prefix, content.length);
      }
    } else {
      log.info("{} [{} bytes content]", prefix, content.length);
    }
  }

  private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
    if (request instanceof ContentCachingRequestWrapper) {
      return (ContentCachingRequestWrapper) request;
    } else {
      return new ContentCachingRequestWrapper(request);
    }
  }

  private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
    if (response instanceof ContentCachingResponseWrapper) {
      return (ContentCachingResponseWrapper) response;
    } else {
      return new ContentCachingResponseWrapper(response);
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = request.getHeader("token");
    String username = request.getHeader("username");
    String requestClient = request.getHeader("requestClientId");

    if (requestClient == null) {
      requestClient = String.valueOf(System.currentTimeMillis());
    }

    String pathInfo = request.getServletPath().trim();
    if (!"GET".equals(request.getMethod())
        && !"/authen/login".equals(pathInfo)
        && !"/authen/resetForgetPassword".equals(pathInfo)
        && !"/authen/changePassword".equals(pathInfo)
        && !"/authen/checkVersionUpdate".equals(pathInfo)
        && !"/authen/checkVersionUpdateForIOS".equals(pathInfo)
        && !"/aboutController/getLogMobile".equals(pathInfo)
        && !"/logClientController/sendFileLogClientServer".equals(pathInfo)
        && !pathInfo.contains("/actuator/")
    ) {
      ValidateForm validateForm = userTokenVsmart.validateToken(token, username);
      if (Constant.VALIDATE_TOKEN.RESULT_OK.equals(validateForm.getResult())) {
        request.setAttribute(Constant.USER_ASSIGN_ID, validateForm.getMessage());
        beforeRequestLogParam(wrapRequest(request), username, requestClient);
      } else {
        response.setContentType("LOGIN FALSE TOKEN");
        return;
      }
    }
    if (isAsyncDispatch(request)) {
      filterChain.doFilter(request, response);
    } else {
      doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain, username,
          requestClient);
    }
  }

  private void doFilterWrapped(
      ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response,
      FilterChain filterChain,
      String username,
      String requestId
  ) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } finally {
      afterRequest(request, response, username, requestId);
      response.copyBodyToResponse();
    }
  }

  private void afterRequest(
      ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response,
      String username,
      String requestId
  ) {
    if (!request.getServletPath().contains("/actuator/") && log.isInfoEnabled()) {
      logResponse(response, request.getRemoteAddr() + "_" + username + "_" + requestId + " ||<< ");
    }
  }

  private void beforeRequestLogParam(
      ContentCachingRequestWrapper request,
      String username,
      String requestId
  ) {
    if (log.isInfoEnabled()) {
      logParameterRequest(request,
          request.getRemoteAddr() + "_" + username + "_" + requestId + " ||>> ");
    }
  }
}
