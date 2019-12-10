package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.InfraCableLanesBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InfraCableLanesDaoImpl implements InfraCableLanesDao {

  @Autowired
  EntityManager entityManager;

  @Override
  public List<InfraCableLanesBO> findCableLanesByStationId(Long id) {
    List<InfraCableLanesBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraCableLanesBO.class.getName());
    sql.append(" icl where 1=1 ");
    sql.append(" and icl.sourceId = :sourceId or icl.destId = :destId");

    Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class).setParameter("sourceId", id).setParameter("destId", id);

    try {
      result = query.getResultList();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }
}
