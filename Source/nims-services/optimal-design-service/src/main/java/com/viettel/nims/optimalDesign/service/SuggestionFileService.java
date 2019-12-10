package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionFileDTO;

import java.util.List;

/**
 * @author rabbit on 8/24/2019.
 */
public interface SuggestionFileService {
  String validate(SuggestionFileDTO suggestionFileDTO, Boolean isCreate);

  String validate(List<SuggestionFileDTO> suggestionFileDTOS);
}
