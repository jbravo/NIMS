package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionCallOff3gDTO;

import java.util.List;

/**
 * @author rabbit on 8/23/2019.
 */
public interface SuggestionCallOff3gService {
  String validate(SuggestionCallOff3gDTO suggestionCallOff3gDTO, Boolean isCreate);

  String validate(List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOS);


  SuggestionCallOff3gDTO save(SuggestionCallOff3gDTO SuggestionCallOff3gDTO) throws Exception;

  List<SuggestionCallOff3gDTO> save(List<SuggestionCallOff3gDTO> SuggestionCallOff3gDTO, Long suggestId) throws Exception;

  List<SuggestionCallOff3gDTO> gets(Long suggestId);

  public void setCode(String code, Long id) throws Exception;
}
