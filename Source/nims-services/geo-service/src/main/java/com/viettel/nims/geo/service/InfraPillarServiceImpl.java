package com.viettel.nims.geo.service;

import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.fcn.InfraPillar;
import com.viettel.nims.geo.model.fcn.InfraStations;
import com.viettel.nims.geo.model.form.PillarForm;
import com.viettel.nims.geo.service.base.GenericDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InfraPillarServiceImpl extends GenericDaoImpl<InfraPillar, Long> {

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
          "                        pillar_id," +
          "                        pillar_code," +
          "                        status,  " +
          "                        'pillar' as type " +
          "                ) t" +
          "                ) as properties " +
          "           FROM nims.infra_pillars" +
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

  public int updatePillar(PillarForm pillarForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("update nims.infra_pillars set geometry = 'LINESTRING( ");
      for (int i = 0, n = pillarForm.getLngLats().size(); i < n; i++) {
        sb.append(pillarForm.getLngLats().get(i).getLng()).append(" ").append(pillarForm.getLngLats().get(i).getLat()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(")' ").append(" where pillar_id = ?");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, pillarForm.getPillarId());

      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int delete(Long id) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("delete from nims.infra_pillar ");
      sb.append(" where pillar_id = ? ");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, id);
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int addNewPillar(PillarForm pillarForm) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into nims.infra_pillar (pillar_id, pillar_code, geometry) ")
          .append(" values (?, ?, ");
      sb.append("'LINESTRING( ");
      for (int i = 0, n = pillarForm.getLngLats().size(); i < n; i++) {
        sb.append(pillarForm.getLngLats().get(i).getLng()).append(" ").append(pillarForm.getLngLats().get(i).getLat()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(")' )");
      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, pillarForm.getPillarId() != null ? pillarForm.getPillarId() : new Date().getTime());
      query.setParameter(2, pillarForm.getPillarCode());
      return query.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }


  public List<String> findByData(PillarForm pillarForm) {
    List<String> queryResult = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(" SELECT ST_AsGeoJSON(geometry) \\:\\: JSONB AS geometry, ");
      sb.append("  (SELECT json_strip_nulls(row_to_json(t)) ");
      sb.append("        FROM (SELECT pillar_id, pillar_code, status) t)    AS properties ");
      sb.append("         FROM nims.infra_pillars ");
      sb.append("         WHERE LOWER(pillar_code) like '%").append(pillarForm.getPillarCode()).append("%'");
      Query query = entityManager.createNativeQuery(sb.toString());
      queryResult = query.setMaxResults(5).getResultList();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return queryResult;
  }
}
