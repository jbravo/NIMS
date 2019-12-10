package com.viettel.nims.optimalDesign.utils;

import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;

public class ResponseUtils {

  public static ResponseDTO responseByCon(String errCode, String mes, Object data){
    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setErrorCode(errCode);
    responseDTO.setMessage(mes);
    responseDTO.setData(data);
    return responseDTO;
  }
}
