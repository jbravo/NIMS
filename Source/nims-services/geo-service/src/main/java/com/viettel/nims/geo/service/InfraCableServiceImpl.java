package com.viettel.nims.geo.service;

import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.fcn.InfraStations;
import com.viettel.nims.geo.model.form.CableForm;
import com.viettel.nims.geo.service.base.GenericDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class InfraCableServiceImpl extends GenericDaoImpl<InfraStations, Long> {

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

      sb.append("               (" +
          "                select json_strip_nulls(row_to_json(t))" +
          "                from (" +
          "                    select" +
          "                        cable_id," +
          "                        cable_code," +
          "                        status,  " +
          "                        'cables' as type  " +
          "                ) t" +
          "                ) as properties " +
          "           FROM nims.infra_cables" +
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

  public int updateCable(CableForm cableForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("update nims.infra_cables set geometry = 'LINESTRING( ");
      for (int i = 0, n = cableForm.getLngLats().size(); i < n; i++) {
        sb.append(cableForm.getLngLats().get(i).getLng()).append(" ").append(cableForm.getLngLats().get(i).getLat()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(")' ").append(" where cable_id = ?");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, cableForm.getCableId());

      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int editCable(CableForm cableForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("update nims.infra_cables set cable_type_id = :cable_type_id");
      sb.append(" where cable_id = :cable_id");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter("cable_type_id", cableForm.getCableTypeId());
      query.setParameter("cable_id", cableForm.getCableId());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int delete(Long id) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("delete from nims.infra_cable ");
      sb.append(" where cable_id = ? ");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, id);
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int addNewCable(CableForm cableForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into nims.infra_cables (cable_id, cable_code, cable_type_id, status, dept_id, geometry) ")
          .append(" values (?, ?, ?, ?, ?, ");
      sb.append("'LINESTRING( ");
      for (int i = 0, n = cableForm.getLngLats().size(); i < n; i++) {
        sb.append(cableForm.getLngLats().get(i).getLng()).append(" ").append(cableForm.getLngLats().get(i).getLat()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(")' )");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, cableForm.getCableId() != null ? cableForm.getCableId() : new Date().getTime());
      query.setParameter(2, cableForm.getCableCode());
      query.setParameter(3, cableForm.getCableTypeId());
      query.setParameter(4, 1);
      query.setParameter(5, cableForm.getDeptId());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }


  public List<String> findByData(CableForm cableForm) {
    List<String> queryResult = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(" SELECT ST_AsGeoJSON(geometry) \\:\\: JSONB AS geometry, ");
      sb.append("  (SELECT json_strip_nulls(row_to_json(t)) ");
      sb.append("        FROM (SELECT cable_id, cable_code, status) t)    AS properties ");
      sb.append("         FROM nims.infra_cables ");
      sb.append("         WHERE 1=1 ");
      if (cableForm.getCableId() != null) {
        sb.append("         AND cable_id = ").append(cableForm.getCableId());
      }
      if (cableForm.getCableCode() != null) {
        sb.append("         AND LOWER(cable_code) like '%").append(cableForm.getCableCode()).append("%'");
      }
      Query query = entityManager.createNativeQuery(sb.toString());
      queryResult = query.setMaxResults(5).getResultList();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return queryResult;
  }

}
