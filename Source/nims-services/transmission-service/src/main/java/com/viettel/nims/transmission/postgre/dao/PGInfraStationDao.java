package com.viettel.nims.transmission.postgre.dao;

import com.viettel.nims.transmission.model.dto.PGInfraStationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Created by VTN-PTPM-NV68 on 10/3/2019.
 */
@Component
public class PGInfraStationDao {

  @Autowired
  @Qualifier("secondJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Transactional(transactionManager = "secondTransactionManager")
  public void insertOrUpdate(PGInfraStationDto dto) {
    if (dto != null) {
      org.postgis.Point point = new org.postgis.Point();
      point.setX(dto.getLongitude());
      point.setY(dto.getLatitude());
      if (!dto.isUpdate()) {
        String SQL = "INSERT INTO infra_stations(station_id, station_code, dept_id, location_id, owner_id, status, geometry, address)VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(SQL, dto.getStation_id(), dto.getStation_code(), dto.getDept_id(), dto.getLocation_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point), dto.getAddress());
      } else {
        String SQL = "UPDATE infra_stations SET station_code = ?, dept_id = ?, location_id = ?, owner_id = ?, status = ? , geometry = ?, address = ? WHERE station_id = ?";
        jdbcTemplate.update(SQL, dto.getStation_code(), dto.getDept_id(), dto.getLocation_id(), dto.getOwner_id(), dto.getStatus(),  new org.postgis.PGgeometry(point), dto.getAddress(), dto.getStation_id());
      }

    }
  }
  @Transactional(transactionManager = "secondTransactionManager")
  public void delete(Long stationId) {
    String SQL = "delete from infra_stations where station_id = ?";
    jdbcTemplate.update(SQL, stationId);
  }
}
