package com.viettel.nims.optimalDesign.repository.custom;

import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.entity.Suggestion;

import java.util.List;

public interface SuggestionRepositoryCustom {
  List<Suggestion> findAllSuggestion(SuggestionSearchDTO suggestionDTO);

  public List<SuggestionSearchDTO> findAllSuggestionSearch(SuggestionSearchDTO searchDTO);

  }
