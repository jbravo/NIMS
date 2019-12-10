package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.InfraCouplerBO;

import java.util.List;

public interface InfraCouplerDao {

  void saveOrUpdateInfraCoupler(InfraCouplerBO infraCouplersBO);

  boolean deleteCoupler(InfraCouplerBO infraCouplerBO);

  List<InfraCouplerBO> getInfraCouplersByOdfId(Long odfId);

  public int deleteCouplersByOdfId(Long odfId);
  void saveInfraCouplers(InfraCouplerBO infraCouplerBO);

  void updateInfraCouplers(InfraCouplerBO infraCouplerBO);
}
