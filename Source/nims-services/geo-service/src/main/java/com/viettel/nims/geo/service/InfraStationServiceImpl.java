package com.viettel.nims.geo.service;

import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.fcn.InfraStations;
import com.viettel.nims.geo.model.form.StationForm;
import com.viettel.nims.geo.service.base.GenericDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InfraStationServiceImpl extends GenericDaoImpl<InfraStations, Long> {

  public List<String> findByBbox(BboxForm bboxForm) {
    List<String> queryResult = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("select row_to_json(fc)" +
          " from (  " +
          "   SELECT 'FeatureCollection' as type," +
          "        array_to_json(array_agg(f)) as features " +
          "   FROM (");
      if (bboxForm.getZoom() <= 7) {
        sb.append("           SELECT  'Feature' As type, ST_AsGeoJSON(ST_Simplify(geometry,0.7))\\:\\:jsonb as geometry ,");
      } else if (bboxForm.getZoom() <= 10) {
        sb.append("           SELECT  'Feature' As type, ST_AsGeoJSON(ST_Simplify(geometry,0.5))\\:\\:jsonb as geometry ,");
      } else if (bboxForm.getZoom() <= 13) {
        sb.append("           SELECT  'Feature' As type, ST_AsGeoJSON(ST_Simplify(geometry,0.3))\\:\\:jsonb as geometry ,");
      } else {
        sb.append("           SELECT  'Feature' As type, ST_AsGeoJSON(geometry)\\:\\:jsonb as geometry ,");
      }
      sb.append(
          "               (" +
              "                select json_strip_nulls(row_to_json(t))" +
              "                from (" +
              "                    select" +
              "                        station_id," +
              "                        station_code, " +
              "                        status," +
              "                        owner_id," +
              "                         'stations' as type" +
              "                         " +
              "                ) t" +
              "                ) as properties " +
              "           FROM nims.infra_stations" +
              "           WHERE geometry " +
              "                   && ST_MakeEnvelope (" + bboxForm.getMaxY() + ", " + bboxForm.getMaxX() + ", " + bboxForm.getMinY() + ", " + bboxForm.getMinX() + ", 4326)" +
              "   ) f "
              + ") as fc");
      Query query = entityManager.createNativeQuery(sb.toString());
      queryResult = query.getResultList();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return queryResult;
  }

  public int updateStation(StationForm stationForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("update nims.infra_stations ")
          .append(" set geometry = 'POINT(")
          .append(stationForm.getLngLat().getLng())
          .append(" ")
          .append(stationForm.getLngLat().getLat())
          .append(")'");
      sb.append(" where station_id = ? ");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, stationForm.getStationId());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int delete(Long id) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("delete from nims.infra_stations ");
      sb.append(" where station_id = ? ");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, id);
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int addNewStation(StationForm stationForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into nims.infra_stations (station_id, station_code, address, status, dept_id, location_id, geometry) ")
          .append(" values (?, ?, ?, ?, ?, ?, 'POINT(")
          .append(stationForm.getLngLat().getLng()).append(" ")
          .append(stationForm.getLngLat().getLat()).append(")' )");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, stationForm.getStationId() != null ? stationForm.getStationId() : new Date().getTime());
      query.setParameter(2, stationForm.getStationCode());
      query.setParameter(3, stationForm.getAddress());
      query.setParameter(4, 0);
      query.setParameter(5,stationForm.getDeptId());
      query.setParameter(6,stationForm.getLocationId());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }


  public int editStation(StationForm stationForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("update nims.infra_stations ")
          .append(" set station_code = :station_code,address= :address,status = :status,dept_id = :dept_id,location_id = :location_id ,")
          .append(" geometry = 'POINT(")
          .append(stationForm.getLngLat().getLng())
          .append(" ")
          .append(stationForm.getLngLat().getLat())
          .append(")'");
      sb.append(" where station_id = :station_id ");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter("station_id", stationForm.getStationId());
      query.setParameter("station_code", stationForm.getStationCode());
      query.setParameter("address", stationForm.getAddress());
      query.setParameter("status", 0);
      query.setParameter("dept_id", stationForm.getDeptId());
      query.setParameter("location_id", stationForm.getLocationId());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }


  public List<String> findByData(StationForm stationForm) {
    List<String> queryResult = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(" SELECT row_to_json(fc) ");
      sb.append(" FROM ( ");
      sb.append(" SELECT ");
      sb.append(" 'FeatureCollection'         AS type, ");
      sb.append(" array_to_json(array_agg(f)) AS features ");
      sb.append(" FROM ( ");
      sb.append(" SELECT ");
      sb.append(" 'Feature' AS type, ");
      sb.append(" ST_AsGeoJSON(geometry) \\:\\:jsonb AS geometry, ");
      sb.append(" ( ");
      sb.append(" SELECT json_strip_nulls(row_to_json(t)) ");
      sb.append(" FROM ( ");
      sb.append(" SELECT ");
      sb.append(" station_id, ");
      sb.append(" station_code, ");
      sb.append(" status, ");
      sb.append(" owner_id ");
      sb.append(" ) t ");
      sb.append(" ) AS properties ");
      sb.append(" FROM nims.infra_stations ");
      sb.append(" WHERE station_id=  ").append(stationForm.getStationId());
      sb.append(" ) f ");
      sb.append(" ) AS fc ");
      Query query = entityManager.createNativeQuery(sb.toString());
      queryResult = query.getResultList();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return queryResult;
  }
}
