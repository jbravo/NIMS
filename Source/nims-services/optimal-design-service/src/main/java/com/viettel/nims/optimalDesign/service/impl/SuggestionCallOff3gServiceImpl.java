package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOff3gDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSector3gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff3g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOff3gRepository;
import com.viettel.nims.optimalDesign.repository.custom.RepositoryCustomUtils;
import com.viettel.nims.optimalDesign.service.SuggestionCallOff3gService;
import com.viettel.nims.optimalDesign.service.SuggestionSector3gService;
import com.viettel.nims.optimalDesign.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author rabbit on 8/23/2019.
 */

@Service
public class SuggestionCallOff3gServiceImpl implements SuggestionCallOff3gService {
  @Autowired
  private SuggestionCallOff3gRepository suggestionCallOff3gRepository;

  private BaseMapper<SuggestionCallOff3g, SuggestionCallOff3gDTO> suggestionCallOff3gMapper = new BaseMapper<>(SuggestionCallOff3g.class, SuggestionCallOff3gDTO.class);

  @Autowired
  private SuggestionSector3gService suggestionSector3gService;

  @Autowired
  RepositoryCustomUtils repositoryCustomUtils;

  @Override
  public String validate(SuggestionCallOff3gDTO suggestionCallOff3gDTO, Boolean isCreate) {
    if (suggestionCallOff3gDTO != null && !isCreate) {
      Optional<SuggestionCallOff3g> callOff3g = suggestionCallOff3gRepository.findById(suggestionCallOff3gDTO.getSuggestionCallOffId());

      if (isCreate && callOff3g.isPresent()) {
        return "suggestionCallOff3g.exists";
      } else if (!isCreate && !callOff3g.isPresent()) {
        return "suggestionCallOff3g.notexists";
      }

      return null;
    }

    return null;
  }

  /**
   * @param suggestionCallOff3gDTOS
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOS) {

    if (suggestionCallOff3gDTOS == null) return null;

    for (SuggestionCallOff3gDTO item : suggestionCallOff3gDTOS) {
      if (item != null) {
        Boolean isCreate = item.getSuggestionCallOffId() == null ? true : false;
        String messageCode = validate(item, isCreate);
        if (messageCode != null) return messageCode;
      }
    }

    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionCallOff3gDTO> save(List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOS, Long suggestId) throws Exception{
    boolean isCreate = suggestId==null ? true:false;

    //Xoa cac SuggestionCallOff3g va SuggestionSector3g ton tai trong db
    if(!isCreate) {
      //#Lay cac SUGGESTION_CALL_OFF_ID cua SuggestionCallOff3g ton tai trong db theo suggestId
      //#SELECT SUGGESTION_CALL_OFF_ID FROM SUGGESTION_CALL_OFF_3G
      // WHERE SUGGEST_ID = :suggestId
      List<Long> suggestCallOffIds = Convert.convertListObjectsToListLong(
          repositoryCustomUtils.getAllValueOfColumnByParentId("SUGGESTION_CALL_OFF_3G", "SUGGESTION_CALL_OFF_ID", "SUGGEST_ID", suggestId)
      );
      //#Xoa cac SuggestionCallOff3g va SuggestionSector3g
      if (suggestCallOffIds.size() > 0) {
        //Delete all suggestCallOff3g by ids: suggestionCallOffIds
        //#DELETE FROM SUGGESTION_CALL_OFF_3G
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllInListId("SUGGESTION_CALL_OFF_3G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
        //#DELETE FROM SUGGESTION_SECTOR_3G
        // WHERE SUGGESTION_CALL_OFF_ID IN (suggestCallOffIds[0],...,suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_SECTOR_3G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
      }
    }

    List<SuggestionCallOff3g> suggestionCallOff3gs = suggestionCallOff3gMapper.toPersistenceBean(suggestionCallOff3gDTOS);
    List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOSSaved = suggestionCallOff3gMapper.toDtoBean(suggestionCallOff3gRepository.saveAll(suggestionCallOff3gs));

    int size = suggestionCallOff3gDTOS.size();

    //save multi list suggestionSector3g
    for (int i = 0; i < size; ++i) {
      List<SuggestionSector3gDTO> suggestionSector3gDTOS = new ArrayList<>();

      Long suggestionCallOffId = suggestionCallOff3gDTOSSaved.get(i).getSuggestionCallOffId();

      List<SuggestionSector3gDTO> list = suggestionCallOff3gDTOS.get(i).getSuggestionSector3gDTOList();

      if(list!=null)
      for (SuggestionSector3gDTO item : list) {
        item.setSuggestionCallOffId(suggestionCallOffId);
        suggestionSector3gDTOS.add(item);
      }

      suggestionSector3gDTOS = suggestionSector3gService.save(suggestionSector3gDTOS);
      suggestionCallOff3gDTOSSaved.get(i).setSuggestionSector3gDTOList(suggestionSector3gDTOS);
    }

    return suggestionCallOff3gDTOSSaved;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOff3gDTO save(SuggestionCallOff3gDTO suggestionCallOff3gDTO) throws Exception{
    SuggestionCallOff3g suggestionCallOff3g = suggestionCallOff3gMapper.toPersistenceBean(suggestionCallOff3gDTO);
    SuggestionCallOff3gDTO suggestionCallOff3gDTOSaved = suggestionCallOff3gMapper.toDtoBean(suggestionCallOff3gRepository.save(suggestionCallOff3g));

    //save list suggestionSector3g
    List<SuggestionSector3gDTO> suggestionSector3gDTOS = new ArrayList<>();

    Long suggestionCallOffId = suggestionCallOff3gDTOSaved.getSuggestionCallOffId();

    List<SuggestionSector3gDTO> list = suggestionCallOff3gDTO.getSuggestionSector3gDTOList();
    for (SuggestionSector3gDTO item : list) {
      item.setSuggestionCallOffId(suggestionCallOffId);
      suggestionSector3gDTOS.add(item);
    }

    suggestionSector3gDTOS = suggestionSector3gService.save(suggestionSector3gDTOS);
    suggestionCallOff3gDTOSaved.setSuggestionSector3gDTOList(suggestionSector3gDTOS);

    return suggestionCallOff3gDTO;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionCallOff3gDTO> gets(Long suggestId) {
    List<SuggestionCallOff3g> list = suggestionCallOff3gRepository.findAllBySuggestId(suggestId);
    List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOS = suggestionCallOff3gMapper.toDtoBean(list);

    for(SuggestionCallOff3gDTO suggestionCallOff3gDTO: suggestionCallOff3gDTOS){
      List<SuggestionSector3gDTO> suggestionSector3gDTOS = suggestionSector3gService.gets(suggestionCallOff3gDTO.getSuggestionCallOffId());

      suggestionCallOff3gDTO.setSuggestionSector3gDTOList(suggestionSector3gDTOS);
    }

    return suggestionCallOff3gDTOS;
  }

  @Override
  public void setCode(String code, Long id) throws Exception {
    SuggestionCallOff3g suggestionCallOff3g = suggestionCallOff3gRepository.findBySuggestId(id);
    if(suggestionCallOff3g == null)
      return;
    suggestionCallOff3g.setCabinetCode(code);
    suggestionCallOff3gRepository.save(suggestionCallOff3g);
  }
}
