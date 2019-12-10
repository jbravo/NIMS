package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.InfraCableLanesBO;

import java.util.List;

public interface InfraCableLanesDao {
  List<InfraCableLanesBO> findCableLanesByStationId(Long id);
}
