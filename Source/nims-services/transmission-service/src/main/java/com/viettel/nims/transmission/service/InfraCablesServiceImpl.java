package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.dao.*;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraPointsBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.PillarsBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VTN-PTPM-NV64 on 8/30/2019.
 */
@Service
@Transactional(transactionManager="globalTransactionManager")
public class InfraCablesServiceImpl implements InfraCablesService{

  @Autowired
  InfraCablesDao infraCablesDao;

  @Autowired
  TransmissionDao transmissionDao;

  @Autowired
  InfraStationDao infraStationDao;

  @Autowired
  PillarDao pillarDao;

  @Autowired
  InfraPoolDao infraPoolDao;

  @Override
  public ResponseEntity<?> saveOrUpdateCable(InfraCablesBO infraCablesBO) {
    if (infraCablesBO.getSourceId() == null) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraCablesBO.getDestId() == null) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraCablesBO.getCableCode() == null) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraCablesBO.getConstructionCode() == null) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
    }
//    else if (infraCablesBO.getCableTypeId() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
//    }
    else if (infraCablesBO.getLength() == null) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
    }else{
      try{
        infraCablesBO.setDeptId(342L);
        infraCablesDao.saveOrUpdateCable(infraCablesBO);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK.toString());
        response.setContent("Cập nhật thông tin thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
      }catch(Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
      }
    }
  }

   @Override
  public ResponseEntity<?> findCableById(Long id) {
    try{
      InfraCablesBO infraCablesBO = infraCablesDao.finById(id);
      Map<String, Object> map = new HashMap<String, Object>();
      if(infraCablesBO.getSourceId() != null){
        map = getInfoByPointType(infraCablesBO.getSourceId());
        infraCablesBO.setSourceCode((String) map.get("code"));
        infraCablesBO.setDeptId((Long) map.get("deptId"));
      }
      if(infraCablesBO.getDestId() != null){
        map = getInfoByPointType(infraCablesBO.getDestId());
        infraCablesBO.setDestCode((String) map.get("code"));
      }
      Response<InfraCablesBO> response = new Response<>();
      response.setStatus(HttpStatus.OK.toString());
      response.setContent(infraCablesBO);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }catch(Exception e){
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  private Map<String, Object> getInfoByPointType(Long id){
    InfraPointsBO infraPointsBO = transmissionDao.findInfraPointsById(id);
    Map<String, Object> map = new HashMap<String, Object>();
    if (infraPointsBO!=null){
      if(BigInteger.valueOf(1).compareTo(infraPointsBO.getType()) == 0){
        ViewInfraStationsBO infraStationsBO = infraStationDao.findStationById(infraPointsBO.getId());
        map.put("code" , infraStationsBO.getStationCode());
        map.put("deptId",infraStationsBO.getDeptId());
      }else if(BigInteger.valueOf(2).compareTo(infraPointsBO.getType()) == 0){
        PillarsBO pillarsBO = pillarDao.findPillarById(infraPointsBO.getId());
        map.put("code" , pillarsBO.getPillarCode());
        map.put("deptId",pillarsBO.getDeptId());
      }else{
        ViewInfraPoolsBO infraPoolsBO = infraPoolDao.findPoolById(infraPointsBO.getId());
        map.put("code" , infraPoolsBO.getPoolCode());
        map.put("deptId",infraPoolsBO.getDeptId());
      }
    }
    return map;
  }

}
