package com.viettel.nims.transmission.commom;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ResponseEntityBase<T> extends HttpEntity<T> {
  private final Object status;
  private  Object stateCode;
  private  String stateMessage;
  /**
   * Create a new {@code ResponseEntity} with the given status code, and no body nor headers.
   * @param status the status code
   */
  public ResponseEntityBase(HttpStatus status) {
    this(null, null, status);
  }

  /**
   * Create a new {@code ResponseEntity} with the given body and status code, and no headers.
   * @param body the entity body
   * @param status the status code
   */
  public ResponseEntityBase(@Nullable T body, HttpStatus status) {
    this(body, null, status);
  }

  /**
   * Create a new {@code HttpEntity} with the given headers and status code, and no body.
   * @param headers the entity headers
   * @param status the status code
   */
  public ResponseEntityBase(MultiValueMap<String, String> headers, HttpStatus status) {
    this(null, headers, status);
  }

  /**
   * Create a new {@code HttpEntity} with the given body, headers, and status code.
   * @param body the entity body
   * @param headers the entity headers
   * @param status the status code
   */
  public ResponseEntityBase(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
    super(body, headers);
    Assert.notNull(status, "HttpStatus must not be null");
    this.status = status;
  }

  /**
   * Create a new {@code HttpEntity} with the given body, headers, and status code.
   * Just used behind the nested builder API.
   * @param body the entity body
   * @param headers the entity headers
   * @param status the status code (as {@code HttpStatus} or as {@code Integer} value)
   */
  private ResponseEntityBase(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Object status) {
    super(body, headers);
    Assert.notNull(status, "HttpStatus must not be null");
    this.status = status;
  }


  /**
   * Return the HTTP status code of the response.
   * @return the HTTP status as an HttpStatus enum entry
   */
  public HttpStatus getStatusCode() {
    if (this.status instanceof HttpStatus) {
      return (HttpStatus) this.status;
    }
    else {
      return HttpStatus.valueOf((Integer) this.status);
    }
  }

  /**
   * Return the HTTP status code of the response.
   * @return the HTTP status as an int value
   * @since 4.3
   */
  public int getStatusCodeValue() {
    if (this.status instanceof HttpStatus) {
      return ((HttpStatus) this.status).value();
    }
    else {
      return (Integer) this.status;
    }
  }


  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    }
    if (!super.equals(other)) {
      return false;
    }
    ResponseEntityBase<?> otherEntity = (ResponseEntityBase<?>) other;
    return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
  }

  @Override
  public int hashCode() {
    return (super.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.status));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("<");
    builder.append(this.status.toString());
    if (this.status instanceof HttpStatus) {
      builder.append(' ');
      builder.append(((HttpStatus) this.status).getReasonPhrase());
    }
    builder.append(',');
    T body = getBody();
    HttpHeaders headers = getHeaders();
    if (body != null) {
      builder.append(body);
      builder.append(',');
    }
    builder.append(headers);
    builder.append('>');
    return builder.toString();
  }


  // Static builder methods

  /**
   * Create a builder with the given status.
   * @param status the response status
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder status(HttpStatus status) {
    Assert.notNull(status, "HttpStatus must not be null");
    return new ResponseEntityBase.DefaultBuilder(status);
  }

  /**
   * Create a builder with the given status.
   * @param status the response status
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder status(int status) {
    return new ResponseEntityBase.DefaultBuilder(status);
  }

  /**
   * Create a builder with the status set to {@linkplain HttpStatus#OK OK}.
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder ok() {
    return status(HttpStatus.OK);
  }

  /**
   * A shortcut for creating a {@code ResponseEntity} with the given body and
   * the status set to {@linkplain HttpStatus#OK OK}.
   * @return the created {@code ResponseEntity}
   * @since 4.1
   */
  public static <T> ResponseEntityBase<T> ok(T body) {
    ResponseEntityBase.BodyBuilder builder = ok();
    return builder.body(body);
  }

  /**
   * Create a new builder with a {@linkplain HttpStatus#CREATED CREATED} status
   * and a location header set to the given URI.
   * @param location the location URI
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder created(URI location) {
    ResponseEntityBase.BodyBuilder builder = status(HttpStatus.CREATED);
    return builder.location(location);
  }

  /**
   * Create a builder with an {@linkplain HttpStatus#ACCEPTED ACCEPTED} status.
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder accepted() {
    return status(HttpStatus.ACCEPTED);
  }

  /**
   * Create a builder with a {@linkplain HttpStatus#NO_CONTENT NO_CONTENT} status.
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.HeadersBuilder<?> noContent() {
    return status(HttpStatus.NO_CONTENT);
  }

  /**
   * Create a builder with a {@linkplain HttpStatus#BAD_REQUEST BAD_REQUEST} status.
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.BodyBuilder badRequest() {
    return status(HttpStatus.BAD_REQUEST);
  }

  /**
   * Create a builder with a {@linkplain HttpStatus#NOT_FOUND NOT_FOUND} status.
   * @return the created builder
   * @since 4.1
   */
  public static ResponseEntityBase.HeadersBuilder<?> notFound() {
    return status(HttpStatus.NOT_FOUND);
  }

  /**
   * Create a builder with an
   * {@linkplain HttpStatus#UNPROCESSABLE_ENTITY UNPROCESSABLE_ENTITY} status.
   * @return the created builder
   * @since 4.1.3
   */
  public static ResponseEntityBase.BodyBuilder unprocessableEntity() {
    return status(HttpStatus.UNPROCESSABLE_ENTITY);
  }


  /**
   * Defines a builder that adds headers to the response entity.
   * @param <B> the builder subclass
   * @since 4.1
   */
  public interface HeadersBuilder<B extends ResponseEntityBase.HeadersBuilder<B>> {

    /**
     * Add the given, single header value under the given name.
     * @param headerName the header name
     * @param headerValues the header value(s)
     * @return this builder
     * @see HttpHeaders#add(String, String)
     */
    B header(String headerName, String... headerValues);

    /**
     * Copy the given headers into the entity's headers map.
     * @param headers the existing HttpHeaders to copy from
     * @return this builder
     * @since 4.1.2
     * @see HttpHeaders#add(String, String)
     */
    B headers(@Nullable HttpHeaders headers);

    /**
     * Set the set of allowed {@link HttpMethod HTTP methods}, as specified
     * by the {@code Allow} header.
     * @param allowedMethods the allowed methods
     * @return this builder
     * @see HttpHeaders#setAllow(Set)
     */
    B allow(HttpMethod... allowedMethods);

    /**
     * Set the entity tag of the body, as specified by the {@code ETag} header.
     * @param etag the new entity tag
     * @return this builder
     * @see HttpHeaders#setETag(String)
     */
    B eTag(String etag);

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     * @param lastModified the last modified date
     * @return this builder
     * @see HttpHeaders#setLastModified(long)
     */
    B lastModified(long lastModified);

    /**
     * Set the location of a resource, as specified by the {@code Location} header.
     * @param location the location
     * @return this builder
     * @see HttpHeaders#setLocation(URI)
     */
    B location(URI location);

    /**
     * Set the caching directives for the resource, as specified by the HTTP 1.1
     * {@code Cache-Control} header.
     * <p>A {@code CacheControl} instance can be built like
     * {@code CacheControl.maxAge(3600).cachePublic().noTransform()}.
     * @param cacheControl a builder for cache-related HTTP response headers
     * @return this builder
     * @since 4.2
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">RFC-7234 Section 5.2</a>
     */
    B cacheControl(CacheControl cacheControl);

    /**
     * Configure one or more request header names (e.g. "Accept-Language") to
     * add to the "Vary" response header to inform clients that the response is
     * subject to content negotiation and variances based on the value of the
     * given request headers. The configured request header names are added only
     * if not already present in the response "Vary" header.
     * @param requestHeaders request header names
     * @since 4.3
     */
    B varyBy(String... requestHeaders);

    /**
     * Build the response entity with no body.
     * @return the response entity
     * @see ResponseEntity.BodyBuilder#body(Object)
     */
    <T> ResponseEntity<T> build();
  }


  /**
   * Defines a builder that adds a body to the response entity.
   * @since 4.1
   */
  public interface BodyBuilder extends ResponseEntityBase.HeadersBuilder<ResponseEntityBase.BodyBuilder> {

    /**
     * Set the length of the body in bytes, as specified by the
     * {@code Content-Length} header.
     * @param contentLength the content length
     * @return this builder
     * @see HttpHeaders#setContentLength(long)
     */
    ResponseEntityBase.BodyBuilder contentLength(long contentLength);

    /**
     * Set the {@linkplain MediaType media type} of the body, as specified by the
     * {@code Content-Type} header.
     * @param contentType the content type
     * @return this builder
     * @see HttpHeaders#setContentType(MediaType)
     */
    ResponseEntityBase.BodyBuilder contentType(MediaType contentType);

    /**
     * Set the body of the response entity and returns it.
     * @param <T> the type of the body
     * @param body the body of the response entity
     * @return the built response entity
     */
    <T> ResponseEntityBase<T> body(@Nullable T body);
  }


  private static class DefaultBuilder implements ResponseEntityBase.BodyBuilder {

    private final Object statusCode;

    private final HttpHeaders headers = new HttpHeaders();

    public DefaultBuilder(Object statusCode) {
      this.statusCode = statusCode;
    }

    @Override
    public ResponseEntityBase.BodyBuilder header(String headerName, String... headerValues) {
      for (String headerValue : headerValues) {
        this.headers.add(headerName, headerValue);
      }
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder headers(@Nullable HttpHeaders headers) {
      if (headers != null) {
        this.headers.putAll(headers);
      }
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder allow(HttpMethod... allowedMethods) {
      this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder contentLength(long contentLength) {
      this.headers.setContentLength(contentLength);
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder contentType(MediaType contentType) {
      this.headers.setContentType(contentType);
      return this;
    }

    @Override
    public <T> ResponseEntityBase<T> body(T body) {
      return null;
    }

    @Override
    public ResponseEntityBase.BodyBuilder eTag(String etag) {
      if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
        etag = "\"" + etag;
      }
      if (!etag.endsWith("\"")) {
        etag = etag + "\"";
      }
      this.headers.setETag(etag);
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder lastModified(long date) {
      this.headers.setLastModified(date);
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder location(URI location) {
      this.headers.setLocation(location);
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder cacheControl(CacheControl cacheControl) {
      String ccValue = cacheControl.getHeaderValue();
      if (ccValue != null) {
        this.headers.setCacheControl(cacheControl.getHeaderValue());
      }
      return this;
    }

    @Override
    public ResponseEntityBase.BodyBuilder varyBy(String... requestHeaders) {
      this.headers.setVary(Arrays.asList(requestHeaders));
      return this;
    }

    @Override
    public <T> ResponseEntity<T> build() {
      return null;
    }


  }
}
