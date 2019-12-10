package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.*;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTrans;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransDevice;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransOldCableLane;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffTransRepository;
import com.viettel.nims.optimalDesign.repository.custom.RepositoryCustomUtils;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransCableLaneNewService;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransDeviceService;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransOldCableLaneService;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransService;
import com.viettel.nims.optimalDesign.utils.Constants;
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
public class SuggestionCallOffTransServiceImpl implements SuggestionCallOffTransService {
  @Autowired
  private SuggestionCallOffTransRepository suggestionCallOffTransRepository;

  private BaseMapper<SuggestionCallOffTrans, SuggestionCallOffTransDTO> suggestionCallOffTransMapper = new BaseMapper<>(SuggestionCallOffTrans.class, SuggestionCallOffTransDTO.class);

  @Autowired
  private SuggestionCallOffTransOldCableLaneService suggestionCallOffTransOldCableLaneService;

  @Autowired
  private SuggestionCallOffTransCableLaneNewService suggestionCallOffTransCableLaneNewService;

  @Autowired
  private SuggestionCallOffTransDeviceService suggestionCallOffTransDeviceService;

  @Autowired
  RepositoryCustomUtils repositoryCustomUtils;

  @Override
  public String validate(SuggestionCallOffTransDTO suggestionCallOffTransDTO, Boolean isCreate) {
    if(suggestionCallOffTransDTO!=null && isCreate!=null) {
      Optional<SuggestionCallOffTrans> callOffTrans = suggestionCallOffTransRepository.findById(suggestionCallOffTransDTO.getSuggestionCallOffId());

      if(isCreate&&callOffTrans.isPresent()){
        return "suggestionCallOffTrans.exists";
      }else if(!isCreate&&!callOffTrans.isPresent()){
        return "suggestionCallOffTrans.notexists";
      }

      return null;
    }

    return null;
  }

  /**
   *
   * @param suggestionCallOffTransDTOS
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOS) {

    if(suggestionCallOffTransDTOS==null) return null;

    for(SuggestionCallOffTransDTO item: suggestionCallOffTransDTOS){
      if(item!=null){
        Boolean isCreate = item.getSuggestionCallOffId()==null ? true:false;
        String messageCode = validate(item, isCreate);
        if(messageCode!=null) return messageCode;
      }
    };

    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOS, Long suggestId) throws Exception {
    boolean isCreate = suggestId==null ? true:false;

    //Xoa cac SuggestionCallOffTransDTOS, SuggestionCallOffTransCableLaneNewDTO, SuggestionCallOffTransOldCableLaneDTO, SuggestionCallOffTransDeviceDTO ton tai trong db
    if(!isCreate) {
      //#Lay cac SUGGESTION_CALL_OFF_ID cua SuggestionCallOffTransDTO ton tai trong db theo suggestId
      //#SELECT SUGGESTION_CALL_OFF_ID FROM SUGGESTION_CALL_OFF_TRANS
      // WHERE SUGGEST_ID = :suggestId
      List<Long> suggestCallOffIds = Convert.convertListObjectsToListLong(
        repositoryCustomUtils.getAllValueOfColumnByParentId("SUGGESTION_CALL_OFF_TRANS", "SUGGESTION_CALL_OFF_ID", "SUGGEST_ID", suggestId)
      );
      //#Xoa cac SuggestionCallOffTransDTOS, SuggestionCallOffTransCableLaneNewDTO, SuggestionCallOffTransOldCableLaneDTO, SuggestionCallOffTransDeviceDTO
      if (suggestCallOffIds.size() > 0) {
        //Delete all SUGGESTION_CALL_OFF_TRANS by ids: suggestionCallOffIds
        //#DELETE FROM SUGGESTION_CALL_OFF_TRANS
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllInListId("SUGGESTION_CALL_OFF_TRANS", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);

        //Delete all SuggestionCallOffTransCableLaneNewDTO, SuggestionCallOffTransOldCableLaneDTO, SuggestionCallOffTransDeviceDTO by referenceId: suggestionCallOffId
        //#DELETE FROM SUGGESTION_CALL_OFF_TRANS_CABLE_LANE_NEW
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_CALL_OFF_TRANS_CABLE_LANE_NEW", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
        //#DELETE FROM SUGGESTION_CALL_OFF_TRANS_OLD_CABLE_LANE
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_CALL_OFF_TRANS_OLD_CABLE_LANE", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
        //#DELETE FROM SUGGESTION_CALL_OFF_TRANS_DEVICE
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_CALL_OFF_TRANS_DEVICE", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
      }
    }

    List<SuggestionCallOffTrans> suggestionCallOffTransList = suggestionCallOffTransMapper.toPersistenceBean(suggestionCallOffTransDTOS);

    for(int i=0; i<suggestionCallOffTransDTOS.size(); ++i) {
      SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO = suggestionCallOffTransDTOS.get(i).getSuggestionCallOffTransCableLaneNewDTO();
      SuggestionCallOffTransOldCableLaneDTO suggestionCallOffTransOldCableLaneDTO = suggestionCallOffTransDTOS.get(i).getSuggestionCallOffTransOldCableLaneDTO();
      SuggestionCallOffTransDeviceDTO suggestionCallOffTransDeviceDTO = suggestionCallOffTransDTOS.get(i).getSuggestionCallOffTransDeviceDTO();

      Long suggestionCallOffId = suggestionCallOffTransList.get(i).getSuggestionCallOffId();

      if (suggestionCallOffTransCableLaneNewDTO != null) {
        suggestionCallOffTransCableLaneNewDTO.setSuggestionCallOffId(suggestionCallOffId);
      }

      if (suggestionCallOffTransOldCableLaneDTO != null) {
        suggestionCallOffTransOldCableLaneDTO.setSuggestionCallOffId(suggestionCallOffId);
      }

      if (suggestionCallOffTransDeviceDTO != null) {
        suggestionCallOffTransDeviceDTO.setSuggestionCallOffId(suggestionCallOffId);
      }

      suggestionCallOffTransOldCableLaneService.save(suggestionCallOffTransOldCableLaneDTO);
      suggestionCallOffTransCableLaneNewService.save(suggestionCallOffTransCableLaneNewDTO);
      suggestionCallOffTransDeviceService.save(suggestionCallOffTransDeviceDTO);
    }
  }

  @Override
  public List<SuggestionCallOffTransDTO> gets(Long suggestId) {
    List<SuggestionCallOffTrans> suggestionCallOffTrans = suggestionCallOffTransRepository.findAllBySuggestId(suggestId);
    List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOS = suggestionCallOffTransMapper.toDtoBean(suggestionCallOffTrans);

    for (SuggestionCallOffTransDTO suggestionCallOffTransDTO : suggestionCallOffTransDTOS) {
      if (suggestionCallOffTransDTO != null) {
        Long suggestionCallOffId = suggestionCallOffTransDTO.getSuggestionCallOffId();

        SuggestionCallOffTransOldCableLaneDTO suggestionCallOffTransOldCableLaneDTO = suggestionCallOffTransOldCableLaneService.get(suggestionCallOffId);
        SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO = suggestionCallOffTransCableLaneNewService.get(suggestionCallOffId);
        SuggestionCallOffTransDeviceDTO suggestionCallOffTransDeviceDTO = suggestionCallOffTransDeviceService.get(suggestionCallOffId);

        suggestionCallOffTransDTO.setSuggestionCallOffTransCableLaneNewDTO(suggestionCallOffTransCableLaneNewDTO);
        suggestionCallOffTransDTO.setSuggestionCallOffTransOldCableLaneDTO(suggestionCallOffTransOldCableLaneDTO);
        suggestionCallOffTransDTO.setSuggestionCallOffTransDeviceDTO(suggestionCallOffTransDeviceDTO);
      }
    }

    return suggestionCallOffTransDTOS;
  }
}
