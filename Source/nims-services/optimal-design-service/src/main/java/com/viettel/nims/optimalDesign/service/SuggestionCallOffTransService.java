package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransDTO;

import java.util.List;

/**
 * @author rabbit on 8/23/2019.
 */

public interface SuggestionCallOffTransService {
  String validate(SuggestionCallOffTransDTO suggestionCallOffTransDTO, Boolean isCreate);

  String validate(List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOS);

  void save(List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOS, Long suggestId) throws Exception;

  List<SuggestionCallOffTransDTO> gets(Long suggestId);
}
