package com.viettel.nims.commons.client.form;

import lombok.Data;

@Data
public class TextboxForm {

  private String type;
  private String id;
  private String defaul;
  private String priority;
  private Long requied;
  private Long order;
}
