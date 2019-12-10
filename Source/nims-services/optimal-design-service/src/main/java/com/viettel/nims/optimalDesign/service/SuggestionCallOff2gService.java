package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOff2gDTO;

import java.util.List;

/**
 * @author rabbit on 8/23/2019.
 */
public interface SuggestionCallOff2gService {
  String validate(SuggestionCallOff2gDTO suggestionCallOff2gDTO, Boolean isCreate);

  String validate(List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOS);

  SuggestionCallOff2gDTO save(SuggestionCallOff2gDTO SuggestionCallOff2gDTO) throws Exception;

  List<SuggestionCallOff2gDTO> save(List<SuggestionCallOff2gDTO> SuggestionCallOff2gDTO, Long suggestId) throws Exception;

  List<SuggestionCallOff2gDTO> gets(Long suggestId);

  public void setCode(String code, Long id) throws Exception;
}
