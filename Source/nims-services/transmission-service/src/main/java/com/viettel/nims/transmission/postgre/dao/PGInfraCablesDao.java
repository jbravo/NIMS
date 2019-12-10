package com.viettel.nims.transmission.postgre.dao;

import com.viettel.nims.transmission.model.dto.PGInfraCablesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by VTN-PTPM-NV68 on 10/3/2019.
 */
@Component
public class PGInfraCablesDao {

  @Autowired
  @Qualifier("secondJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Transactional(transactionManager = "secondTransactionManager")
  public void insertOrUpdate(PGInfraCablesDto dto) {
    if (dto != null && dto.getCable_id() != null) {
      org.postgis.Point point = new org.postgis.Point();
      point.setX(dto.getLongitude());
      point.setY(dto.getLatitude());
      if (!dto.isUpdate()) {
        String SQL = "INSERT INTO infra_cables(cable_id, cable_code, dept_id, location_id, cable_type_id, owner_id, status, geometry)VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(SQL, dto.getCable_id(), dto.getCable_code(), dto.getDept_id(), dto.getLocation_id(), dto.getCable_type_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point));
      } else {
        String SQL = "UPDATE infra_cables SET cable_code = ?, dept_id = ?, location_id = ?, cable_type_id = ?, owner_id = ?, status = ? , geometry = ? WHERE cable_id = ?";
        jdbcTemplate.update(SQL, dto.getCable_code(), dto.getDept_id(), dto.getLocation_id(), dto.getCable_type_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point), dto.getCable_id());
      }

    }
  }
  @Transactional(transactionManager = "secondTransactionManager")
  public void delete(Long cableId) {
    String SQL = "delete from infra_cables where cable_id = ?";
    jdbcTemplate.update(SQL, cableId);
  }
}
