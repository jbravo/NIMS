package com.viettel.nims.optimalDesign.dto;


import lombok.Data;

import java.util.List;

@Data
public class DeleteSuggestionDTO {
  private List<Long> idSuggestions;
  private String userName;
}
