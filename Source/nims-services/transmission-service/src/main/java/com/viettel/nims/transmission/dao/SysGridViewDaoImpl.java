package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.transmission.model.SysGridViewBO;
import com.viettel.nims.transmission.model.SysGridViewColumnBO;
import com.viettel.nims.transmission.model.SysGridViewUserBO;
import com.viettel.nims.transmission.model.view.ViewSysGridBO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SysGridViewDaoImpl implements SysGridViewDao {

  @Autowired
  private EntityManager entityManager;


  @Override
  public List<ViewSysGridBO> getGridView(ViewSysGridBO viewSysGridBO) {
    try {
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewSysGridBO.class.getName());
      sql.append(" vsg where 1=1 ");
      HashMap<String, Object> params = new HashMap<>();
      if (viewSysGridBO.getUserId() != null) {
        sql.append(" and vsg.userId = :userId ");
        params.put("userId", viewSysGridBO.getUserId());
      }

      Query query = entityManager.createQuery(sql.toString(), ViewSysGridBO.class);
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
        }
      }

      List<ViewSysGridBO> resultList = query.getResultList();
      return resultList;
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  public List<SysGridViewUserBO> getAllColumsByUser(Long userId, Long gridId) {
    StringBuilder sql = new StringBuilder(" select su from ");
    sql.append(SysGridViewUserBO.class.getName());
    sql.append(" su, ");
    sql.append(SysGridViewColumnBO.class.getName());
    sql.append(" sl ");
    sql.append(" where 1 = 1 and su.columnId = sl.columnId and sl.gridId = :gridId and su.userId = :userId");
    Query query = entityManager.createQuery(sql.toString(), SysGridViewUserBO.class).setParameter("gridId", gridId).setParameter("userId", userId);
    List<SysGridViewUserBO> result = query.getResultList();
    return result;
  }

  @Override
  public List<SysGridViewColumnBO> getAllColumns(Long gridId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(SysGridViewColumnBO.class.getName());
    sql.append("  where  gridId = :gridId ");
    Query query = entityManager.createQuery(sql.toString(), SysGridViewColumnBO.class).setParameter("gridId", gridId);
    List<SysGridViewColumnBO> result = query.getResultList();
    if (!CommonUtils.isNullOrEmpty(result)) {
      return result;
    }
    return null;
  }

  @Override
  public void saveSysGridUser(SysGridViewUserBO sysGridViewUserBO) {
    Session session = entityManager.unwrap(Session.class);
    session.saveOrUpdate(sysGridViewUserBO);
  }

  @Override
  public Long getGridIdByName(String gridName) {
    StringBuilder sql = new StringBuilder("select gridId from ");
    sql.append(SysGridViewBO.class.getName());
    sql.append(" where gridViewName =:gridName ");
    Query query = entityManager.createQuery(sql.toString()).setParameter("gridName", gridName);
    List<Long> result = query.getResultList();
    if (!CommonUtils.isNullOrEmpty(result)) {
      return result.get(0);
    }
    return null;
  }
}
