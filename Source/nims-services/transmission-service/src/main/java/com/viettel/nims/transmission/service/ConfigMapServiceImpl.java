package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.dao.ConfigMapDao;
import com.viettel.nims.transmission.model.CfgMapUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by VTN-PTPM-NV64 on 9/16/2019.
 */
@Service
public class ConfigMapServiceImpl implements ConfigMapService {

  @Autowired
  ConfigMapDao configMapDao;

  @Override
  public ResponseEntity<?> saveConfigCommon(CfgMapUserBO cfgMapUserBO) {
    try{
      cfgMapUserBO.setUserId(221L);
      configMapDao.saveConfigCommon(cfgMapUserBO);
      Response<String> response = new Response<>();
      response.setStatus(HttpStatus.OK.toString());
      response.setContent("Cập nhật thông tin thành công");
      return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  @Override
  public ResponseEntity<?> getInfoConfigMap(Long id) {
    try{
      CfgMapUserBO cfgMapUserBO = configMapDao.getInfoConfigMap(id);
      Response<CfgMapUserBO> response = new Response<>();
      response.setStatus(HttpStatus.OK.toString());
      response.setContent(cfgMapUserBO);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }
}
