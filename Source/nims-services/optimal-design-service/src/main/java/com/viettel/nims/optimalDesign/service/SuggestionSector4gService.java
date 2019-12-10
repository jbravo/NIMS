package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionSector4gDTO;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
public interface SuggestionSector4gService {
  SuggestionSector4gDTO save(SuggestionSector4gDTO suggestionSector2gDTO) throws Exception;

  List<SuggestionSector4gDTO> save(List<SuggestionSector4gDTO> suggestionSector2gDTO) throws Exception;

  List<SuggestionSector4gDTO> gets(Long suggestionCallOffId);
}
