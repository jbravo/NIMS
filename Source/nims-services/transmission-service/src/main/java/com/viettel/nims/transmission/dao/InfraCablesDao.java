package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.InfraCablesBO;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InfraCablesDao {

  List<InfraCablesBO> findCablesByStationId(Long id);

  void saveOrUpdateCable(InfraCablesBO infraCablesBO);


  public List<InfraCablesBO> findCableByHolderId(Long holderId);

  //find by id
  public InfraCablesBO finById(Long id);

  // get all cable
  public List<InfraCablesBO> getAllCalble(Long userId, BigInteger type);

  //find by id
  public InfraCablesBO finByCableCode(String cableCode);
}
