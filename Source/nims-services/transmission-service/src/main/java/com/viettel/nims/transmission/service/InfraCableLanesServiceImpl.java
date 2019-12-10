package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.dao.InfraCableLanesDao;
import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraCablesBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by VTN-PTPM-NV64 on 8/27/2019.
 */
@Service
@Transactional(transactionManager="globalTransactionManager")
public class InfraCableLanesServiceImpl implements InfraCableLanesService {

  @Autowired
  InfraCableLanesDao infraCableLanesDao;

  @Override
  public ResponseEntity<?> addCableLanes(InfraCableLanesBO cableLanes) {
    try{
      Response<InfraCablesBO> response = new Response<>();
      response.setStatus(HttpStatus.OK.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);
    }catch(Exception e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
