package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Repository
public class InfraSleeveDaoImpl implements InfraSleeveDao {

  @Autowired
  private EntityManager entityManager;

  @Override
  // Tim kiem co ban
  public FormResult findBasicSleeve(InfraSleevesBO infraSleevesBO, Long userId) {
    try {
      StringBuilder sqlCount = new StringBuilder("Select Count(slv.sleeveId) ");
      StringBuilder sql = new StringBuilder("from ");
      StringBuilder append = sql.append(ViewInfraSleevesBO.class.getName());
      sql.append(" slv where 1=1");
      sql.append(" and slv.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
      if (infraSleevesBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraSleevesBO.getBasicInfo().trim())) {
        sql.append(" AND slv.sleeveCode LIKE :basicInfo");
      }
      sql.append(" order by slv.sleeveCode asc");
      sqlCount.append(sql.toString());
      // tim kiem co ban
      Query query = entityManager.createQuery(sql.toString(), ViewInfraSleevesBO.class);
      // lay tong so ban ghi
      Query queryCount = entityManager.createQuery(sqlCount.toString());
      query.setParameter("userId", userId);
      queryCount.setParameter("userId", userId);
      if (infraSleevesBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraSleevesBO.getBasicInfo().trim())) {
        query.setParameter("basicInfo", CommonUtils.getLikeCondition(infraSleevesBO.getBasicInfo()));
        queryCount.setParameter("basicInfo", CommonUtils.getLikeCondition(infraSleevesBO.getBasicInfo()));
      }
      FormResult result = new FormResult();
      //Set tong so ban ghi
      result.setTotalRecords((Long) queryCount.getSingleResult());
      // So ban ghi tren 1 trang
      query.setMaxResults(infraSleevesBO.getRows());
      // bat dau tu ban ghi so
      query.setFirstResult(infraSleevesBO.getFirst());

      List<ViewInfraSleevesBO> resultList = query.getResultList();
      for (ViewInfraSleevesBO item : resultList) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (item.getUpdateTime() != null) {
          String strDate = df.format(item.getUpdateTime());
          item.setModifyDate(strDate);
        }
        if (item.getInstallationDate() != null) {
          String strDate = df.format(item.getInstallationDate());
          item.setInstallation(strDate);
        }
      }
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
      return null;
    }
  }

  @Override
  // Tim kiem nang cao
  public FormResult findAdvanceSleeve(InfraSleevesBO infraSleevesBO, Long userId) {
    StringBuilder sqlCount = new StringBuilder("Select Count(slv.sleeveId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewInfraSleevesBO.class.getName());
    sql.append(" slv where 1=1");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and slv.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    params.put("userId", userId);
    if (infraSleevesBO.getBasicInfo() == null || !StringUtils.isNotEmpty(infraSleevesBO.getBasicInfo().trim())) {
      if (infraSleevesBO.getSleeveCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getSleeveCode().trim())) {
        sql.append(" AND slv.sleeveCode LIKE :SLEEVE_CODE");
        params.put("SLEEVE_CODE", CommonUtils.getLikeCondition(infraSleevesBO.getSleeveCode()));
      }
      if (infraSleevesBO.getSleeveTypeId() != null) {
        sql.append(" AND slv.sleeveTypeId = :sleeveTypeId");
        params.put("sleeveTypeId", infraSleevesBO.getSleeveTypeId());
      }
      if (infraSleevesBO.getPurpose() != null) {
        sql.append(" AND slv.purpose = :PURPOSE");
        params.put("PURPOSE", infraSleevesBO.getPurpose());
      }
      if (infraSleevesBO.getSerial() != null && StringUtils.isNotEmpty(infraSleevesBO.getSerial().trim())) {
        sql.append(" AND slv.serial LIKE :SERIAL");
        params.put("SERIAL", CommonUtils.getLikeCondition(infraSleevesBO.getSerial()));
      }
      if (infraSleevesBO.getPillarCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getPillarCode().trim())) {
        sql.append(" AND slv.pillarCode = :PILLAR_CODE");
        params.put("PILLAR_CODE", infraSleevesBO.getPillarCode());
      }
      if (infraSleevesBO.getPoolCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getPoolCode().trim())) {
        sql.append(" AND slv.poolCode = :POOL_CODE");
        params.put("POOL_CODE", infraSleevesBO.getPoolCode());
      }
      if (infraSleevesBO.getDeptId() != null) {
        sql.append(" AND slv.deptId = :deptId");
        params.put("deptId", infraSleevesBO.getDeptId());
      }
      if (infraSleevesBO.getLaneCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getLaneCode().trim())) {
        sql.append(" AND slv.laneCode = :LANE_CODE");
        params.put("LANE_CODE", infraSleevesBO.getLaneCode());
      }
    } else {
      if (infraSleevesBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraSleevesBO.getBasicInfo().trim())) {
        sql.append(" AND slv.sleeveCode LIKE :SLEEVE_CODE");
        params.put("SLEEVE_CODE", CommonUtils.getLikeCondition(infraSleevesBO.getBasicInfo()));
      }
    }
    // Sort by key
    if (infraSleevesBO.getKeySort() != null && StringUtils.isNotEmpty(infraSleevesBO.getKeySort().trim())) {

      if (StringUtils.isNotEmpty(infraSleevesBO.getsSleeveCode().trim())) {
        sql.append(" AND slv.sleeveCode LIKE :sleeveCode");
        params.put("sleeveCode", CommonUtils.getLikeCondition(infraSleevesBO.getsSleeveCode()));
      }
      if (infraSleevesBO.getsSleeveTypeId() != null && infraSleevesBO.getSleeveTypeId() == null) {
        sql.append(" AND slv.sleeveTypeId = :sleeveTypeId");
        params.put("sleeveTypeId", infraSleevesBO.getsSleeveTypeId());
      }
      if (infraSleevesBO.getsPillarCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getsPillarCode().trim())) {
        if (infraSleevesBO.getLikePillarCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getLikePillarCode().trim())) {
          sql.append(" AND slv.pillarCode Like :pillarCode");
          params.put("pillarCode", CommonUtils.getLikeCondition(infraSleevesBO.getsPillarCode()));
        } else {
          sql.append(" AND slv.pillarCode = :pillarCode");
          params.put("pillarCode", infraSleevesBO.getsPillarCode());
        }
      }
      if (infraSleevesBO.getsPoolCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getsPoolCode().trim())) {
        if (infraSleevesBO.getLikePoolCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getLikePoolCode().trim())) {
          sql.append(" AND slv.poolCode Like :poolCode");
          params.put("poolCode", CommonUtils.getLikeCondition(infraSleevesBO.getsPoolCode()));
        } else {
          sql.append(" AND slv.poolCode = :poolCode");
          params.put("poolCode", infraSleevesBO.getsPoolCode());
        }
      }
      if (infraSleevesBO.getsLaneName() != null && StringUtils.isNotEmpty(infraSleevesBO.getsLaneName().trim())) {
        if (infraSleevesBO.getLikeLaneCode() != null && StringUtils.isNotEmpty(infraSleevesBO.getLikeLaneCode().trim())) {
          sql.append(" AND slv.laneCode Like :laneCode");
          params.put("laneCode", CommonUtils.getLikeCondition(infraSleevesBO.getsLaneName()));
        } else {
          sql.append(" AND slv.laneCode = :laneCode");
          params.put("laneCode", infraSleevesBO.getsLaneName());
        }
      }
      if (infraSleevesBO.getsDeptPath() != null && StringUtils.isNotEmpty(infraSleevesBO.getsDeptPath().trim())) {
        if (infraSleevesBO.getLikeDeptPath() != null && StringUtils.isNotEmpty(infraSleevesBO.getLikeDeptPath().trim())) {
          sql.append(" AND slv.deptPath Like :deptPath");
          params.put("deptPath", CommonUtils.getLikeCondition(infraSleevesBO.getsDeptPath()));
        } else {
          sql.append(" AND slv.deptPath = :deptPath");
          params.put("deptPath", infraSleevesBO.getsDeptPath());
        }
      }
      if (infraSleevesBO.getVendorName() != null && StringUtils.isNotEmpty(infraSleevesBO.getVendorName().trim())) {
        sql.append(" AND slv.vendorName = :vendorName");
        params.put("vendorName", infraSleevesBO.getVendorName());
      }
      if (infraSleevesBO.getOwnerName() != null && StringUtils.isNotEmpty(infraSleevesBO.getOwnerName().trim())) {
        sql.append(" AND slv.ownerName = :ownerName");
        params.put("ownerName", infraSleevesBO.getOwnerName());
      }
      if (infraSleevesBO.getsPurpose() != null && infraSleevesBO.getPurpose() == null) {
        sql.append(" AND slv.purpose = :PURPOSE");
        params.put("PURPOSE", infraSleevesBO.getsPurpose());
      }
      if (infraSleevesBO.getsSerial() != null && StringUtils.isNotEmpty(infraSleevesBO.getsSerial().trim())) {
        sql.append(" AND slv.serial LIKE :serial");
        params.put("serial", CommonUtils.getLikeCondition(infraSleevesBO.getsSerial()));
      }
      if (infraSleevesBO.getsStatus() != null) {
        sql.append(" AND slv.status = :status");
        params.put("status", infraSleevesBO.getsStatus());
      }
      if (infraSleevesBO.getNote() != null && StringUtils.isNotEmpty(infraSleevesBO.getNote().trim())) {
        sql.append(" AND slv.note LIKE :note");
        params.put("note", CommonUtils.getLikeCondition(infraSleevesBO.getNote()));
      }
      if (infraSleevesBO.getLocation() != null && StringUtils.isNotEmpty(infraSleevesBO.getLocation().trim())) {
        sql.append(" AND slv.location LIKE :location");
        params.put("location", CommonUtils.getLikeCondition(infraSleevesBO.getLocation()));
      }
      if (infraSleevesBO.getLatitude() != null && StringUtils.isNotEmpty(infraSleevesBO.getLatitude().trim())) {
        sql.append(" AND CONVERT(slv.latitude, CHAR(150)) LIKE :latitude");
        params.put("latitude", CommonUtils.getLikeCondition(infraSleevesBO.getLatitude().toString()));
      }
      if (infraSleevesBO.getLongitude() != null && StringUtils.isNotEmpty(infraSleevesBO.getLongitude().trim())) {
        sql.append(" AND CONVERT(slv.longitude, CHAR(150)) LIKE :longitude");
        params.put("longitude", CommonUtils.getLikeCondition(infraSleevesBO.getLongitude().toString()));
      }
      if (infraSleevesBO.getInstallationDate() != null || infraSleevesBO.getUpdateTime() != null) {
        if (infraSleevesBO.getInstallationDate() != null) {
          sql.append(" AND DATE(slv.installationDate) = :value");
          params.put("value", infraSleevesBO.getInstallationDate());
        }
        if (infraSleevesBO.getUpdateTime() != null) {
          sql.append(" AND DATE(slv.updateTime) = Date(:updateDate) ");
          params.put("updateDate", infraSleevesBO.getUpdateTime());
        }
      }
      infraSleevesBO.setSortName("");
    }
    //sort by field
    if (infraSleevesBO.getSortField() != null) {
      sql.append(" order by slv.");
      sql.append(infraSleevesBO.getSortField());
      if (infraSleevesBO.getSortOrder() == 1) {
        sql.append(" asc");
      } else if (infraSleevesBO.getSortOrder() == -1) {
        sql.append(" desc");
      }
    } else {
      sql.append(" order by slv.sleeveCode asc");
    }
    sqlCount.append(sql.toString());
    // tim kiem nang cao
    try {
      Query query = entityManager.createQuery(sql.toString(), ViewInfraSleevesBO.class);
      // lay tong so ban ghi
      Query queryCount = entityManager.createQuery(sqlCount.toString());
      FormResult result = new FormResult();
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
          queryCount.setParameter(param.getKey(), param.getValue());
        }
      }
      //Set tong so ban ghi
      result.setTotalRecords((Long) queryCount.getSingleResult());
      // So ban ghi tren 1 trang
      query.setMaxResults(infraSleevesBO.getRows());
      // bat dau tu ban ghi sofindWeldSleeveBySleeveId
      query.setFirstResult(infraSleevesBO.getFirst());
      List<ViewInfraSleevesBO> resultList = query.getResultList();
      for (ViewInfraSleevesBO item : resultList) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (item.getUpdateTime() != null) {
          String strDate = df.format(item.getUpdateTime());
          item.setModifyDate(strDate);
        }
        if (item.getInstallationDate() != null) {
          String strDate = df.format(item.getInstallationDate());
          item.setInstallation(strDate);
        }
      }
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
      return null;
    }
  }

  public FormResult listSleeve(Long userId) {
    try {
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewInfraSleevesBO.class.getName());
      sql.append(" slv where 1=1");
      sql.append(" and slv.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
      sql.append(" order by slv.sleeveCode asc");
      Query query = entityManager.createQuery(sql.toString(), ViewInfraSleevesBO.class);
      query.setParameter("userId", userId);
      FormResult result = new FormResult();

      List<ViewInfraSleevesBO> resultList = query.getResultList();
      for (ViewInfraSleevesBO item : resultList) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (item.getUpdateTime() != null) {
          String strDate = df.format(item.getUpdateTime());
          item.setModifyDate(strDate);
        }
        if (item.getInstallationDate() != null) {
          String strDate = df.format(item.getInstallationDate());
          item.setInstallation(strDate);
        }
      }
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
      return null;
    }
  }

  @Override
  public FormResult findLaneListByCode(String laneCode) {
    try {
      StringBuilder sql = new StringBuilder("from ");
      sql.append(InfraCableLanesBO.class.getName());
      sql.append(" p where 1 = 1 and p.rowStatus = 1 and p.laneCode = :laneCode");
      Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class).setParameter("laneCode", laneCode);
      List<InfraCableLanesBO> resultList = query.getResultList();
      FormResult result = new FormResult();
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
      return null;
    }
  }

  @Override
  public ViewInfraSleevesBO findViewSleeveById(Long id) {
    ViewInfraSleevesBO vInfraSleevesBO = entityManager.find(ViewInfraSleevesBO.class, id);
    return vInfraSleevesBO;
  }

  @Override
  public void saveSleeve(InfraSleevesBO infraSleevesBO) {
    try {
      Session session = entityManager.unwrap(Session.class);
      if (infraSleevesBO.getSleeveId() == null) {
        infraSleevesBO.setCreateTime(new Date());
      }
      infraSleevesBO.setRowStatus(1L);
      session.saveOrUpdate(infraSleevesBO);
    } catch (Exception e) {
      log.error("Exception", e);
    }
  }

  @Override
  public InfraSleevesBO findSleeveById(Long id) {
    InfraSleevesBO infraSleevesBO = entityManager.find(InfraSleevesBO.class, id);
    return infraSleevesBO;
  }

  @Override
  public int delete(Long id) {
    Session session = entityManager.unwrap(Session.class);
    InfraSleevesBO infraSleevesBO = entityManager.find(InfraSleevesBO.class, id);
    if (infraSleevesBO != null && infraSleevesBO.getRowStatus() == 1L) {
      infraSleevesBO.setRowStatus(2L);
      session.save(infraSleevesBO);
      return 1;
    }
    return 0;
  }

  @Override
  public FormResult getSleeveTypeList() {
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(CatSleeveTypeBO.class.getName());
    sql.append(" clt where 1=1");
    sql.append(" and clt.rowStatus = 1");
    sql.append(" order by clt.sleeveTypeCode asc");
    FormResult result = new FormResult();
    try {
      Query query = entityManager.createQuery(sql.toString(), CatSleeveTypeBO.class);
      List<CatSleeveTypeBO> resultList = query.getResultList();
      result.setListData(resultList);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return result;
  }

  @Override
  public FormResult getDataFormSearchAdvance(Long userId) {
    FormResult result = new FormResult();
    StringBuilder sqlSelect = new StringBuilder(" from ");
    sqlSelect.append(ViewInfraSleevesBO.class.getName());
    sqlSelect.append(" slv where 1=1");
    sqlSelect.append(" and slv.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    Query query = entityManager.createQuery(sqlSelect.toString(), ViewInfraSleevesBO.class);
    query.setParameter("userId", userId);
    List<ViewInfraStationsBO> resultList = query.getResultList();
    result.setListData(resultList);
    return result;
  }

  //tim mang xong da duoc han noi
  public List<InfraSleevesBO> findWeldSleeveBySleeveId(Long id) {
    List<InfraSleevesBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(InfraSleevesBO.class.getName());
    sql.append(" slv where 1=1 ");
    sql.append(" and slv.rowStatus = 1 ");
    sql.append(" and slv.sleeveId = ");
    sql.append(" (SELECT DISTINCT b.pk.sleeveId from ");
    sql.append(WeldSleeveBO.class.getName());
    sql.append(" b WHERE b.pk.sleeveId = :sleeveId)");
    Query query = entityManager.createQuery(sql.toString(), InfraSleevesBO.class).setParameter("sleeveId", id);
    try {
      result = query.getResultList();
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return result;
  }

  @Override
  public FormResult getVendorList() {
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(CatItemBO.class.getName());
    sql.append(" item where 1=1");
    sql.append(" and item.rowStatus = 1");
    sql.append(" and item.categoryId = 26"); //26: ma nha san xuat
    sql.append(" order by item.itemName asc");
    FormResult result = new FormResult();
    try {
      Query query = entityManager.createQuery(sql.toString());
      List<InfraSleevesBO> resultList = query.getResultList();
      result.setListData(resultList);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return result;
  }

  @Override
  public List<InfraSleevesBO> findListSleeveBySleeveCode(String sleeveCode, Long sleeveId) {
    List<InfraSleevesBO> resultList = null;
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraSleevesBO.class.getName());
    sql.append(" slv where 1=1");
    sql.append(" and slv.rowStatus = 1");
    sql.append(" and slv.sleeveCode = :sleeveCode");
    if (sleeveId != null) {
      sql.append(" and slv.sleeveId != :id");
    }
    Query query = entityManager.createQuery(sql.toString(), InfraSleevesBO.class);
    query.setParameter("sleeveCode", sleeveCode);
    if (sleeveId != null) {
      query.setParameter("id", sleeveId);
    }
    resultList = query.getResultList();
    if (resultList == null) {
      return new ArrayList<>();
    }
    return resultList;
  }

  @Override
  public List<InfraSleevesBO> getListSleeveBySleeveCode(String sleeveCode, Long userId) {
    List<InfraSleevesBO> resultList = null;
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraSleevesBO.class.getName());
    sql.append(" slv where 1=1");
    sql.append(" and slv.rowStatus = 1");
    sql.append(" and slv.sleeveCode = :sleeveCode");
    sql.append(" and slv.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), InfraSleevesBO.class);
    query.setParameter("userId", userId);
    query.setParameter("sleeveCode", sleeveCode);
    resultList = query.getResultList();
    if (resultList == null) {
      return new ArrayList<>();
    }
    return resultList;
  }


  /**
   * hungnv
   */
  @Override
  public List<InfraSleevesBO> findSleeveByLaneCode(String laneCode) {
    List<InfraSleevesBO> resultList = null;
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraSleevesBO.class.getName());
    sql.append(" slv where 1=1");
    sql.append(" and slv.rowStatus = 1");
    sql.append(" and slv.laneCode = :laneCode");
    Query query = entityManager.createQuery(sql.toString(), InfraSleevesBO.class);
    query.setParameter("laneCode", laneCode);
    resultList = query.getResultList();
    if (resultList == null) {
      return new ArrayList<>();
    }
    return resultList;
  }

  /**
   * hungnv
   */
  @Override
  public InfraSleevesBO findSleeveBySleeveCode(String sleeveCode) {
    InfraSleevesBO infraSleevesBO = new InfraSleevesBO();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraSleevesBO.class.getName());
    sql.append(" slv where 1=1");
    sql.append(" and slv.rowStatus = 1");
    sql.append(" and slv.sleeveCode = :sleeveCode");
    Query query = entityManager.createQuery(sql.toString(), InfraSleevesBO.class);
    query.setParameter("sleeveCode", sleeveCode);
    if (query.getFirstResult() != 0) {
      infraSleevesBO = (InfraSleevesBO) query.getSingleResult();
      if (Objects.isNull(infraSleevesBO)) {
        return null;
      }
    }

    return infraSleevesBO;
  }

}
