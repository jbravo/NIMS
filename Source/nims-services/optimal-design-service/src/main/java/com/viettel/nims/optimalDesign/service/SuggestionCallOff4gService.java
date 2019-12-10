package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOff4gDTO;

import java.util.List;

/**
 * @author rabbit on 8/23/2019.
 */
public interface SuggestionCallOff4gService {
  String validate(SuggestionCallOff4gDTO suggestionCallOff4gDTO, Boolean isCreate);

  String validate(List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOS);

  SuggestionCallOff4gDTO save(SuggestionCallOff4gDTO SuggestionCallOff4gDTO) throws Exception;

  List<SuggestionCallOff4gDTO> save(List<SuggestionCallOff4gDTO> SuggestionCallOff4gDTO, Long suggestId) throws Exception;

  List<SuggestionCallOff4gDTO> gets(Long suggestId);

  public void setCode(String code, Long id) throws Exception;
}
