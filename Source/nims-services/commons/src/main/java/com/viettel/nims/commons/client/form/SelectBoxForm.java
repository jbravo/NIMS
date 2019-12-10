package com.viettel.nims.commons.client.form;

import java.util.List;
import lombok.Data;

@Data
public class SelectBoxForm {

  private String type;
  private String id;
  private String defaul;
  private String priority;
  private List<ComboForm> options;
  private Long requied;
  private Long order;
}
