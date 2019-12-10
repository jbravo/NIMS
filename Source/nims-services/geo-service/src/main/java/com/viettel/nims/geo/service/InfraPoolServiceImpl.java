package com.viettel.nims.geo.service;


import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.fcn.InfraPools;
import com.viettel.nims.geo.service.base.GenericDaoImpl;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class InfraPoolServiceImpl extends GenericDaoImpl<InfraPools, Long> {

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
              "                        pool_id," +
              "                        pool_code, " +
              "                        status," +
              "                        owner_id," +
              "                        'pool' as type" +
              "                         " +
              "                ) t" +
              "                ) as properties " +
              "           FROM nims.infra_pools" +
              "           WHERE geometry " +
              "                   && ST_MakeEnvelope (" + bboxForm.getMaxY() + ", " + bboxForm.getMaxX() + ", " + bboxForm.getMinY() + ", " + bboxForm.getMinX() + ", 4326)" +
              "   ) f "
              + ") as fc");

      System.out.println("SQL: " + sb.toString());
      Query query = entityManager.createNativeQuery(sb.toString());
      queryResult = query.getResultList();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return queryResult;
  }
}
