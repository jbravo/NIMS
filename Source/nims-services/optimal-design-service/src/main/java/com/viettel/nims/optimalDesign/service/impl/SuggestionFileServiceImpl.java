package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionFileDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionFile;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionFileRepository;
import com.viettel.nims.optimalDesign.service.SuggestionFileService;
import com.viettel.nims.optimalDesign.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author rabbit on 8/24/2019.
 */

@Service
public class SuggestionFileServiceImpl implements SuggestionFileService {
  @Autowired
  private SuggestionFileRepository suggestionFileRepository;

  private BaseMapper<SuggestionFile, SuggestionFileDTO> suggestionFileMapper = new BaseMapper<>(SuggestionFile.class, SuggestionFileDTO.class);

  @Override
  public String validate(SuggestionFileDTO suggestionFileDTO, Boolean isCreate) {
    if(suggestionFileDTO!=null && isCreate!=null) {
      Optional<SuggestionFile> File = suggestionFileRepository.findById(suggestionFileDTO.getId());

      if(isCreate&&File.isPresent()){
        return "suggestionFile.exists";
      }else if(!isCreate&&!File.isPresent()){
        return "suggestionFile.notexists";
      }

      return null;
    }

    return null;
  }

  /**
   *
   * @param suggestionFileDTOS
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(List<SuggestionFileDTO> suggestionFileDTOS) {

    if(suggestionFileDTOS==null) return null;

    for(SuggestionFileDTO item: suggestionFileDTOS){
      if(item!=null){
        Boolean isCreate = item.getId()==null ? true:false;
        String messageCode = validate(item, isCreate);
        if(messageCode!=null) return messageCode;
      }
    };

    return null;
  }
}
