package com.viettel.nims.transmission.commom;

import com.viettel.nims.commons.util.Response;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseBase extends ResponseEntity {


  public ResponseBase(HttpStatus status) {
    super(status);
  }

  public ResponseBase(Object body, HttpStatus status) {
    super(body, status);
  }

  public ResponseBase(MultiValueMap headers, HttpStatus status) {
    super(headers, status);
  }

  public ResponseBase(Object body, MultiValueMap headers, HttpStatus status) {
    super(body, headers, status);
  }

  public static ResponseEntity createResponse(Object body, String message, Object httpStateCode) {
    Response response = new Response();
    response.setContent(body);
    response.setMessage(message);
    response.setStatus(httpStateCode.toString());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  public static ResponseEntity createResponse(Object body, MultiValueMap headers, String message, Object httpStateCode) {
    Response response = new Response();
    response.setContent(body);
    response.setMessage(message);
    response.setStatus(httpStateCode.toString());
    return new ResponseEntity(response, headers, HttpStatus.OK);
  }

}
