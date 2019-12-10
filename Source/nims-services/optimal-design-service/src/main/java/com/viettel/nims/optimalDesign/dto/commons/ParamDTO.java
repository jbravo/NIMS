package com.viettel.nims.optimalDesign.dto.commons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ParamDTO {

  private String name;

  private Object value;
}
