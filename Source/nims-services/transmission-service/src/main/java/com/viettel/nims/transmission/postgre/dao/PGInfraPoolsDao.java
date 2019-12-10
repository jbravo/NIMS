package com.viettel.nims.transmission.postgre.dao;

import com.viettel.nims.transmission.model.dto.PGInfraPoolsDto;
import com.viettel.nims.transmission.model.dto.PGInfraStationDto;
import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by VTN-PTPM-NV68 on 10/3/2019.
 */
@Component
public class PGInfraPoolsDao {

  @Autowired
  @Qualifier("secondJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Transactional(transactionManager = "secondTransactionManager")
  public void insertOrUpdate(PGInfraPoolsDto dto) {
      if (dto != null) {
        org.postgis.Point point = new org.postgis.Point();
        point.setX(dto.getLongitude());
        point.setY(dto.getLatitude());
        if (!dto.isUpdate()) {
          String SQL = "INSERT INTO infra_pools(pool_id, pool_code, dept_id, location_id, pool_type_id, owner_id, status, geometry, address)VALUES (?,?,?,?,?,?,?,?,?)";
          jdbcTemplate.update(SQL, dto.getPool_id(), dto.getPool_code(), dto.getDept_id(), dto.getLocation_id(),
              dto.getPool_type_id(), dto.getOwner_id(), dto.getStatus(), new org.postgis.PGgeometry(point), dto.getAddress());
        } else {
          String SQL = "UPDATE infra_pools SET pool_code = ?, dept_id = ?, location_id = ?, pool_type_id = ?, owner_id = ?, status = ? , geometry = ?, address = ? WHERE pool_id = ?";
          jdbcTemplate.update(SQL, dto.getPool_code(), dto.getDept_id(), dto.getLocation_id(), dto.getPool_type_id(), dto.getOwner_id(), dto.getStatus(), new org.postgis.PGgeometry(point),dto.getAddress(), dto.getPool_id());
        }

      }
  }

  @Transactional(transactionManager = "secondTransactionManager")
  public void delete(Long poolId) {
    String SQL = "delete from infra_pools where pool_id = ?";
    jdbcTemplate.update(SQL, poolId);
  }
}
