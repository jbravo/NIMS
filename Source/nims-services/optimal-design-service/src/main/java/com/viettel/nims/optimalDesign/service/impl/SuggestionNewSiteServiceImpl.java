package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionNewSiteDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionRadioDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionNewSite;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionNewSiteRepository;
import com.viettel.nims.optimalDesign.service.SuggestionNewSiteService;
import com.viettel.nims.optimalDesign.service.SuggestionRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author rabbit on 8/23/2019.
 */

@Service
public class SuggestionNewSiteServiceImpl implements SuggestionNewSiteService {

  @Autowired
  private SuggestionNewSiteRepository suggestionNewSiteRepository;

  @Autowired
  private SuggestionRadioService suggestionRadioService;

  private BaseMapper<SuggestionNewSite, SuggestionNewSiteDTO> suggestionNewSiteMapper = new BaseMapper<>(SuggestionNewSite.class, SuggestionNewSiteDTO.class);

  /**
   *
   * @param suggestionNewSiteDTO
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(SuggestionNewSiteDTO suggestionNewSiteDTO) {

    if(suggestionNewSiteDTO==null)
      return "suggestionNewSite.notexists";

    Boolean isCreate = suggestionNewSiteDTO.getSuggestNewSiteId() == null ? true : false;

    if(isCreate!=null) {

      if(isCreate){
        Optional<SuggestionNewSite> newSite = suggestionNewSiteRepository.findByStationCode(suggestionNewSiteDTO.getStationCode());

        if(newSite.isPresent())
          return "suggestionNewSite.stationCode.exists";
      }else {
        Optional<SuggestionNewSite> newSite = suggestionNewSiteRepository.findById(suggestionNewSiteDTO.getSuggestNewSiteId());

        if(!newSite.isPresent())
           return "suggestionNewSite.notexists";
        else{
          //validate by stationcode
          newSite = suggestionNewSiteRepository.findByStationCodeIdNot(suggestionNewSiteDTO.getSuggestNewSiteId(), suggestionNewSiteDTO.getStationCode());

          if(newSite.isPresent()){
            return "suggestionNewSite.stationCode.exists";
          }
        }
      }
    }

    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(SuggestionNewSiteDTO suggestionNewSiteDTO) throws Exception{
    SuggestionNewSite suggestionNewSite = suggestionNewSiteMapper.toPersistenceBean(suggestionNewSiteDTO);
    suggestionNewSite = suggestionNewSiteRepository.save(suggestionNewSite);

    SuggestionRadioDTO suggestionRadioDTO = suggestionNewSiteDTO.getSuggestionRadioDTO();
    if(suggestionRadioDTO!=null) {
      suggestionRadioDTO.setSuggestId(suggestionNewSite.getSuggestNewSiteId());
    }

    suggestionRadioService.save(suggestionRadioDTO);
  }


  @Override
  public SuggestionNewSiteDTO get(Long id) {

    Optional<SuggestionNewSite> opt = suggestionNewSiteRepository.findById(id);
    if(opt.isPresent()){
      SuggestionNewSite suggestionNewSite = opt.get();
      SuggestionNewSiteDTO suggestionNewSiteDTO = suggestionNewSiteMapper.toDtoBean(suggestionNewSite);

      suggestionNewSiteDTO.setSuggestionRadioDTO(suggestionRadioService.get(suggestionNewSiteDTO.getSuggestNewSiteId()));

      return suggestionNewSiteDTO;
    }
    return null;
  }

  @Override
  public void setStationCode(String code, Long id) throws Exception {
    SuggestionNewSite suggestionNewSite = suggestionNewSiteRepository.getOne(id);
    suggestionNewSite.setStationCodeApproved(code);
    suggestionNewSiteRepository.save(suggestionNewSite);
  }


}
