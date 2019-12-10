package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.InfraCouplerBO;
import com.viettel.nims.transmission.utils.Constains;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * InfraCouplerDaoImpl
 * Version 1.0
 * Date: 03-09-2019
 * Copyright
 * Modification Logs:
 * DATE									AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 03-09-2019						DungPH				Create
 */
@Slf4j
@Repository
public class InfraCouplerDaoImpl implements InfraCouplerDao {

  @Autowired
  private EntityManager entityManager;

  @Override
  public void saveOrUpdateInfraCoupler(InfraCouplerBO infraCouplerBO) {
    Session session = entityManager.unwrap(Session.class);
    session.saveOrUpdate(infraCouplerBO);
  }

  @Override
  public void saveInfraCouplers(InfraCouplerBO infraCouplerBO) {
    StringBuilder sql = new StringBuilder(" FROM ");
    sql.append(InfraCouplerBO.class.getName());
    sql.append(" ic WHERE ");
    sql.append(" ic.odfId = :ODF_ID and ");
    sql.append(" ic.couplerNo = :COUPLER_NO");

    Query query = entityManager.createQuery(sql.toString(), InfraCouplerBO.class)
        .setParameter("ODF_ID", infraCouplerBO.getOdfId())
        .setParameter("COUPLER_NO", infraCouplerBO.getCouplerNo());
    query.getResultList();
  }

  @Override
  public boolean deleteCoupler(InfraCouplerBO infraCouplerBO) {
    Session session = entityManager.unwrap(Session.class);
    try {
      session.delete(infraCouplerBO);
      return true;
    } catch (Exception e) {
      log.error("Exception", e);
      return false;
    }
  }

  @Override
  public int deleteCouplersByOdfId(Long odfId) {
    try {
      StringBuilder sql = new StringBuilder(" FROM ");
      sql.append(InfraCouplerBO.class.getName());
      sql.append(" ic WHERE ");
      sql.append(" ic.odfId = :ODF_ID");
      Query query = entityManager.createQuery(sql.toString(), InfraCouplerBO.class)
          .setParameter("ODF_ID", odfId);
      query.getResultList();
      return Constains.NUMBER_ONE;
    } catch (Exception e) {
      log.error("Exception", e);
      return Constains.NUMBER_TWO;
    }
  }

  @Override
  public List<InfraCouplerBO> getInfraCouplersByOdfId(Long odfId) {
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(InfraCouplerBO.class.getName());
    sql.append(" ic where 1=1 ");
    sql.append(" and ic.odfId = :ODF_ID ");
    Query query = entityManager.createQuery(sql.toString(), InfraCouplerBO.class).setParameter("ODF_ID", odfId);

//
//    StringBuilder sqlCount = new StringBuilder("Select Count(couplerNo) from ");
//    sqlCount.append(InfraCouplerBO.class.getName());
//    sqlCount.append(" where ");
//    sqlCount.append(" odfId = :ODF_ID");
//    sqlCount.append(" and statuz = :STATUZ");
//    Query queryCount = entityManager.createQuery(sqlCount.toString(), InfraCouplerBO.class)
//        .setParameter("ODF_ID", odfId)
//        .setParameter("STATUZ", Constains.NUMBER_ZERO);
    return query.getResultList();
  }

  @Override
  public void updateInfraCouplers(InfraCouplerBO infraCouplerBO) {
    StringBuilder sql = new StringBuilder("UPDATE ");
    sql.append(InfraCouplerBO.class.getName());
    sql.append(" ic SET ");
    sql.append(" ic.status = :STATUS ");
    sql.append(" WHERE ic.couplerNo = :COUPLER_NO ");
    sql.append(" and ic.odfId = :ODF_ID");
    Query query = entityManager.createQuery(sql.toString(), InfraCouplerBO.class)
        .setParameter("ODF_ID", infraCouplerBO.getOdfId())
        .setParameter("COUPLER_NO", infraCouplerBO.getCouplerNo())
        .setParameter("STATUS", infraCouplerBO.getStatuz());
    query.getResultList();
  }

}
