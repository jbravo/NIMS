package com.viettel.nims.transmission.postgre.dao;

import com.viettel.nims.transmission.model.dto.PGInfraPillarsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by VTN-PTPM-NV68 on 10/3/2019.
 */
@Component
public class PGInfraPillarsDao {

  @Autowired
  @Qualifier("secondJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Transactional(transactionManager = "secondTransactionManager")
  public void insertOrUpdate(PGInfraPillarsDto dto) {
    if (dto != null) {
      org.postgis.Point point = new org.postgis.Point();
      point.setX(dto.getLongitude());
      point.setY(dto.getLatitude());
      if (!dto.isUpdate()) {
        String SQL = "INSERT INTO infra_pillars(pillar_id, pillar_code, dept_id, location_id, pillar_type_id, owner_id, status, geometry, address)VALUES (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(SQL, dto.getPillar_id(), dto.getPillar_code(), dto.getDept_id(), dto.getLocation_id(),
            dto.getPillar_type_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point), dto.getAddress());
      } else {
        String SQL = "UPDATE infra_pillars SET pillar_code = ?, dept_id = ?, location_id = ?, pillar_type_id = ?, owner_id = ?, status = ? , geometry = ?, address = ? WHERE pillar_id = ?";
        jdbcTemplate.update(SQL, dto.getPillar_code(), dto.getDept_id(), dto.getLocation_id(), dto.getPillar_type_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point), dto.getAddress(),dto.getPillar_id());
      }

    }
  }

  @Transactional(transactionManager = "secondTransactionManager")
  public void delete(Long pillarId) {
    String SQL = "delete from infra_pillars where pillar_id = ?";
    jdbcTemplate.update(SQL, pillarId);
  }
}
