package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.ftp.Common;
import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.CableInSleeveResponDto;
import com.viettel.nims.transmission.model.view.ViewWeldSleeveBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Slf4j
@Repository
public class WeldSleeveDaoImpl  implements WeldSleeveDao {
  private static final Logger LOGGER = Logger.getLogger(WeldSleeveDao.class.getName());
  @Autowired
  private EntityManager entityManager;

  @Override
  public FormResult findBasicWeld(ViewWeldSleeveBO viewWeldSleeveBO) {
    try {
      FormResult result = new FormResult();
      StringBuilder sqlCount = new StringBuilder("SELECT COUNT(st.sleeveId) ");
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewWeldSleeveBO.class.getName());
      sql.append(" st where 1 = 1");
      HashMap<String, Object> params = new HashMap<>();
      sql.append("  and st.sleeveId = :sleeveId");
      params.put("sleeveId", viewWeldSleeveBO.getSleeveId());
      if (viewWeldSleeveBO.getSleeveCode() != null && StringUtils.isNotEmpty(viewWeldSleeveBO.getSleeveCode().trim())) {
        sql.append(" and st.sleeveCode like :sleeveCode");
        params.put("sleeveCode", CommonUtils.getLikeCondition(viewWeldSleeveBO.getSleeveCode()));
      }
      if (viewWeldSleeveBO.getSourceCableCode() != null && StringUtils.isNotEmpty(viewWeldSleeveBO.getSourceCableCode().trim())) {
        sql.append(" and st.sourceCableCode like :sourceCableCode");
        params.put("sourceCableCode", CommonUtils.getLikeCondition(viewWeldSleeveBO.getSourceCableCode()));
      }
      if (viewWeldSleeveBO.getSourceLineNo() != null) {
        sql.append(" and CONVERT(st.sourceLineNo, CHAR(150)) like :sourceLineNo");
        params.put("sourceLineNo", CommonUtils.getLikeCondition(viewWeldSleeveBO.getSourceLineNo().toString()));
      }
      if (viewWeldSleeveBO.getDestCableCode() != null && StringUtils.isNotEmpty(viewWeldSleeveBO.getDestCableCode().trim())) {
        sql.append(" and st.destCableCode like :destCableCode");
        params.put("destCableCode", CommonUtils.getLikeCondition(viewWeldSleeveBO.getDestCableCode()));
      }
      if (viewWeldSleeveBO.getDestLineNo() != null) {
        sql.append(" and CONVERT(st.destLineNo, CHAR(150)) like :destLineNo");
        params.put("destLineNo", CommonUtils.getLikeCondition(viewWeldSleeveBO.getDestLineNo().toString()));
      }
      if (viewWeldSleeveBO.getCreateUser() != null && StringUtils.isNotEmpty(viewWeldSleeveBO.getCreateUser().trim())) {
        sql.append(" and st.createUser like :createUser");
        params.put("createUser", CommonUtils.getLikeCondition(viewWeldSleeveBO.getCreateUser()));
      }
      if (viewWeldSleeveBO.getAttenuation() != null) {
        sql.append(" and CONVERT(st.attenuation, CHAR(150)) like :attenuation");
        params.put("attenuation", CommonUtils.getLikeCondition(viewWeldSleeveBO.getAttenuation().toPlainString()));
      }
      if (viewWeldSleeveBO.getSortField() != null && viewWeldSleeveBO.getSortOrder() != null) {
        if (viewWeldSleeveBO.getSortOrder() == 1) {
          sql.append(" order by st." + viewWeldSleeveBO.getSortField() + " desc");
        } else {
          sql.append(" order by st." + viewWeldSleeveBO.getSortField() + " asc");
        }
      } else {
        sql.append(" order by st.sleeveId desc");
      }
      sqlCount.append(sql.toString());
      // tim kiem co ban
      Query query = entityManager.createQuery(sql.toString(), ViewWeldSleeveBO.class);
//    query.setParameter("SLEEVE_ID", viewWeldSleeveBO.getSleeveId());
//    sqlCount.append(sql.toString());
      // Lay tong so ban ghi
      Query queryCount = entityManager.createQuery(sqlCount.toString());
      //    query.setParameter("SLEEVE_ID", viewWeldSleeveBO.getSleeveId());
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
          queryCount.setParameter(param.getKey(), param.getValue());
        }
      }
      // Set tong so ban ghi
      result.setTotalRecords((Long) queryCount.getSingleResult());
      if (viewWeldSleeveBO.getRows() != null) {
        // So ban ghi tren 1 trang
        query.setMaxResults(viewWeldSleeveBO.getRows());
      }

      if (viewWeldSleeveBO.getFirst() != null) {
        // bat dau tu ban ghi so
        query.setFirstResult(viewWeldSleeveBO.getFirst());
      }
      List<ViewWeldSleeveBO> resultList = query.getResultList();
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  public String saveWeldSleeve(WeldSleeveBO weldSleeveBO) {
    try {
      Session session = entityManager.unwrap(Session.class);
      session.saveOrUpdate(weldSleeveBO);
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
      return "FAILE";
    }
    return "Succes";
  }

  @Override
  public int delete(WeldSleeveBO.PK id) {
    Session session = entityManager.unwrap(Session.class);
    WeldSleeveBO weldSleeveBO = entityManager.find(WeldSleeveBO.class, id);
    try {
      entityManager.remove(weldSleeveBO);
      return 1;
    } catch (Exception e) {
      System.out.println(e);
    }
    return 0;
  }

  @Override
  public WeldSleeveBO findById(WeldSleeveBO.PK id) {
    return entityManager.find(WeldSleeveBO.class, id);
  }

  @Override
  public List<WeldSleeveBO> findAll() {
    return entityManager.createQuery("FROM view_weld_list", WeldSleeveBO.class).getResultList();

  }

  @Override
//Load danh sach soi trong cap
  public List<CableInSleeveResponDto> getListCableId(long cableTypeId, long sleeveId, long cableId) {
    Session session = entityManager.unwrap(Session.class);
    CatOpticalCableTypeBO typeCable = new CatOpticalCableTypeBO();
    List<Integer> listCableNo = new ArrayList<Integer>();// Danh sach cac soi da noi
    try {
      typeCable = session.find(CatOpticalCableTypeBO.class, cableTypeId); // L?y lo?i cable, s?i
      if (typeCable != null) {
        StringBuilder sql = new StringBuilder("SELECT s FROM ");
        sql.append(WeldSleeveBO.class.getName());
        sql.append(
            " s WHERE s.pk.sleeveId =:sleeveId AND ( s.pk.sourceCableId =:cableId OR s.pk.destCableId =:cableId )");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("sleeveId", sleeveId);
        query.setParameter("cableId", cableId);

        List<WeldSleeveBO> listWelSleeve = query.getResultList(); // L?y danh s?ch c?c b?n ghi c? c?p d?n ho?c
        if (listWelSleeve.size() > 0) {
          for (WeldSleeveBO weldSleeveBO : listWelSleeve) {
            if (weldSleeveBO.getPk().getSourceCableId() == cableId) {
              listCableNo.add(weldSleeveBO.getPk().getSourceLineNo());
            } else {
              listCableNo.add(weldSleeveBO.getPk().getDestLineNo());
            }
          }
        }
      }
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
    }
    // add c?c s?i trong c?p theo quad tr? v? list
    List<CableInSleeveResponDto> listCableInSleeve = new ArrayList<CableInSleeveResponDto>();
    InfraCablesBO infraCable = new InfraCablesBO();
    try {
      Session se = entityManager.unwrap(Session.class);
      infraCable = se.find(InfraCablesBO.class, cableId);// Lay th?ng tin do?n c?p
    } catch (Exception e) {
      LOGGER.info("context" + LOGGER.getName());
    }
    if (typeCable != null) {
      for (int i = 0; i < typeCable.getCapacity(); i++) {
        CableInSleeveResponDto cableInSleeve = new CableInSleeveResponDto();
        cableInSleeve.setCableId(cableId);
        cableInSleeve.setCableCode(infraCable.getCableCode());
        // Set quad cho c?c s?i
        if ((i + 1) % 6 == 0) {
          cableInSleeve.setQuad((i + 1) / 6);
        } else {
          cableInSleeve.setQuad((i + 1) / 6 + 1);
        }
        // T?m ki?m c?c s?i d? h?n hay chua v? th?m v?o list tr? v?
        boolean checkYanrUsed = listCableNo.contains(i + 1);
        if (!checkYanrUsed) {
          cableInSleeve.setYarn(i + 1);
          listCableInSleeve.add(cableInSleeve);
        }
      }
    }
    return listCableInSleeve;
  }

  @Override
  public InfraSleevesBO findSleeveCodeById(Long id) {
    Session session = entityManager.unwrap(Session.class);
    InfraSleevesBO infraSleeveBO = new InfraSleevesBO();
    try {
      infraSleeveBO = session.find(InfraSleevesBO.class, id);
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
    }

    return infraSleeveBO;
  }

  @Override
  public FormResult getListYarnCable(long cableTypeId, long sleeveId, long cableId) {
    System.out.println("cableTypeId " + cableTypeId + "sleeveId " + sleeveId + "cableId ");
    Session session = entityManager.unwrap(Session.class);
    CatOpticalCableTypeBO typeCable = new CatOpticalCableTypeBO();
    List<Integer> listCableNo = new ArrayList<Integer>();// Danh sach ca soi co the han noi
    try {
      typeCable = session.find(CatOpticalCableTypeBO.class, cableTypeId); // lay loai cable, soi
      if (typeCable != null) {
        StringBuilder sql = new StringBuilder("SELECT s FROM ");
        sql.append(WeldSleeveBO.class.getName());
        sql.append(
            " s WHERE s.pk.sleeveId =:sleeveId AND ( s.pk.sourceCableId =:cableId OR s.pk.destCableId =:cableId )");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("sleeveId", sleeveId);
        query.setParameter("cableId", cableId);

        List<WeldSleeveBO> listWelSleeve = query.getResultList(); // Lay danh sach cac soi da han noi cua cap
        if (listWelSleeve.size() > 0) {
          for (WeldSleeveBO weldSleeveBO : listWelSleeve) {
            if (weldSleeveBO.getPk().getSourceCableId() == cableId) {
              listCableNo.add(weldSleeveBO.getPk().getSourceLineNo());
            } else {
              listCableNo.add(weldSleeveBO.getPk().getDestLineNo());
            }
          }
        }
      }
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
    }
    // add cac soi theo quad co the han noi
    List<CableInSleeveResponDto> listCableInSleeve = new ArrayList<CableInSleeveResponDto>();
    InfraCablesBO infraCable = new InfraCablesBO();
    try {
      Session se = entityManager.unwrap(Session.class);
      infraCable = se.find(InfraCablesBO.class, cableId);// Lay thong tin doan cap
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
    }
    if (typeCable != null) {
      for (int i = 0; i < typeCable.getCapacity(); i++) {
        CableInSleeveResponDto cableInSleeve = new CableInSleeveResponDto();
        cableInSleeve.setCableId(cableId);
        cableInSleeve.setCableCode(infraCable.getCableCode());
        // Set quad cho cac soi
        if ((i + 1) % 6 == 0) {
          cableInSleeve.setQuad((i + 1) / 6);
        } else {
          cableInSleeve.setQuad((i + 1) / 6 + 1);
        }
        // tim kiem cac soi chua han noi them vao cable
        boolean checkYanrUsed = listCableNo.contains(i + 1);
        if (!checkYanrUsed) {
          cableInSleeve.setYarn(i + 1);
          listCableInSleeve.add(cableInSleeve);
        }
      }
    }
    FormResult result = new FormResult();
    result.setListData(listCableInSleeve);
    return null;
  }

  @Override
  public int deleteByFiveField(List<WeldSleeveBO.PK> weldSleeveBOS) {
    try {
      int result = 0;
      for (WeldSleeveBO.PK weldSleeveBO : weldSleeveBOS) {
        Session session = entityManager.unwrap(Session.class);
        WeldSleeveBO w = session.find(WeldSleeveBO.class, weldSleeveBO);
        session.delete(w);
        result = 1;
      }
      return result;
    } catch (Exception e) {
      System.out.println(e);
    }
    return 0;
  }

//  @Override
//  public int deleteByFiveField(List<ViewWeldSleeveBO> viewWeldSleeveBO) {
//    try {
//      StringBuilder deleteQuery = new StringBuilder("DELETE ");
//      StringBuilder sql = new StringBuilder("FROM ");
//      sql.append(WeldSleeveBO.class.getName());
//      sql.append("clsl where 1=1 and clsl.sleeveId = ? and clsl.sourceCableId = ? " +
//          "and clsl.sourceLineNo = ? and clsl.destCableId = ? and clsl.destLineNo = ? ");
//      return 1;
//    } catch (Exception e) {
//        System.out.println(e);
//    }
//    return 0;
//  }

  @Override
  public FormResult findWeldSleeveBO() {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(st.sleeveId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(WeldSleeveBO.class.getName());
    sql.append(" st where 1 = 1");
    sqlCount.append(sql.toString());
    FormResult result = new FormResult();
    Query query = entityManager.createQuery(sql.toString(), WeldSleeveBO.class);
    List<WeldSleeveBO> resultList = query.getResultList();
    result.setListData(resultList);
    return result;
  }

  @Override
  public FormResult findWeldSleeveTestBO() {
    System.out.println("179");
    StringBuilder sqlCount = new StringBuilder("SELECT * ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(WeldSleeveTestBO.class.getName());
    sql.append(" st where 1 = 1");
    sqlCount.append(sql.toString());
    FormResult result = new FormResult();
    Query query = entityManager.createQuery("SELECT * FROM WeldSleeveTestBO ");
    List<WeldSleeveTestBO> resultList = query.getResultList();
    result.setListData(resultList);
    return result;
  }

  @Override
  public boolean checkweldingSleeveById(Long id) {
    return false;
  }

  @Override
  public boolean checkSleeveIdLaneId(ViewWeldSleeveBO viewWeldSleeveBO) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(st.laneId) ");
    sqlCount.append("from ");
    sqlCount.append(ViewWeldSleeveBO.class.getName());
    sqlCount.append(" st where 1 = 1");
    sqlCount.append("  and st.sleeveId = :SLEEVE_ID and st.laneId = :LANE_ID ");

    // Lay tong so ban ghi
    Query queryCount = entityManager.createQuery(sqlCount.toString());
    queryCount.setParameter("SLEEVE_ID", viewWeldSleeveBO.getSleeveId());
    queryCount.setParameter("LANE_ID", viewWeldSleeveBO.getLaneId());
    // --------
//      Query queryCountLaneId = entityManager.createQuery(sqlLaneId.toString());
//      query.setParameter("SLEEVE_ID", viewWeldSleeveBO.getSleeveId());
    // ---------

    List<Long> result = queryCount.getResultList();

    if(CommonUtils.isNullOrEmpty(result)){
      return false;
    }else{
      Long rs = result.get(0);
      if(rs == 1L){
        return true;
      }else{
        return false;
      }
    }
  }

  @Override
  public InfraCablesBO getCableById(Long cableId) {
    try {
      Session session = entityManager.unwrap(Session.class);
      InfraCablesBO cable = session.find(InfraCablesBO.class, cableId);
      return cable;
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
      return null;
    }
  }

  @Override
  public String updateWeldSleeve(WeldSleeveBO weldSleeveBO) {
    try {
      Session session = entityManager.unwrap(Session.class);
      WeldSleeveBO updateWeldSleeveBO = session.find(WeldSleeveBO.class, weldSleeveBO.getPk());
      if (updateWeldSleeveBO != null) {
        updateWeldSleeveBO.setAttenuation(weldSleeveBO.getAttenuation());
        updateWeldSleeveBO.setCreateUser(weldSleeveBO.getCreateUser());
        session.saveOrUpdate(updateWeldSleeveBO);
      } else {
        return "Fail";
      }
    } catch (Exception e) {
      LOGGER.info("Context: " + LOGGER.getName());
      return "Fail";
    }
    return "Secces";
  }

  @Override
  public String updateInfraCableLane(Long laneId) {
//    StringBuilder sql = new StringBuilder(" update ");
//    sql.append(InfraCableLanesBO.class.getName());
//    sql.append(" set rowStatus = 2 where  laneId in (:ids) ");
//    Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class);
//    query.executeUpdate();
    try {
        Session session = entityManager.unwrap(Session.class);
        InfraCableLanesBO lanesBO = session.find(InfraCableLanesBO.class, laneId);
        if (lanesBO != null) {
        lanesBO.setRowStatus(2L);
        session.saveOrUpdate(lanesBO);
//        String sqlUpdate = "UPDATE InfraCableLanesBO  set rowStatus = 2 " + "WHERE laneId = :laneId ";
//        Query query = session.createQuery(sqlUpdate);
//        query.setParameter("laneId", bos.getLaneId());
//        int result = query.executeUpdate();
//        System.out.println(result);
        return "Success";
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return "Fail";

  }

  @Override
  public CntCableLaneBO findLaneIdByCableId(Long cableId) {
    try {
      Session session = entityManager.unwrap(Session.class);
      StringBuilder sql = new StringBuilder("from ");
      sql.append(CntCableLaneBO.class.getName());
      sql.append(" cnt where 1=1 ");
      HashMap<String, Object> params = new HashMap<>();
      sql.append(" and cnt.cableId = :cableId");

      Query query = entityManager.createQuery(sql.toString(), CntCableLaneBO.class)
          .setParameter("cableId", cableId);
      List<CntCableLaneBO> result = query.getResultList();
      if(CommonUtils.isNullOrEmpty(result)){
        return null;
      }
      return result.get(0);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }

  }

}
