package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.DocumentBO;
import com.viettel.nims.transmission.model.InfraPointsBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by VTN-PTPM-NV64 on 7/31/2019.
 */
@Slf4j
@Repository
public class InfraStationDaoImpl implements InfraStationDao {

  @Autowired
  private EntityManager entityManager;

  @Override
  // Tim kiem co ban
  public FormResult findBasicStation(InfraStationsBO infraStationsBO) {
    try {
      FormResult result = new FormResult();
      StringBuilder sqlCount = new StringBuilder("Select Count(st.stationId) ");
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewInfraStationsBO.class.getName());
      sql.append(" st where 1=1");
      sql.append(" and st.rowStatus = 1 ");
      HashMap<String, Object> params = new HashMap<>();
      if (infraStationsBO.getBasicInfo() != null
          && StringUtils.isNotEmpty(infraStationsBO.getBasicInfo().trim())) {
        sql.append(" and st.stationCode like :basicInfo ");
//        sql.append(" OR st.locationName LIKE :basicInfo");
//        sql.append(" OR st.deptName LIKE :basicInfo");
        params.put("basicInfo", CommonUtils.getLikeCondition(infraStationsBO.getBasicInfo()));
      }
      if (infraStationsBO.getSortField() != null && infraStationsBO.getSortOrder() != null) {
        if (infraStationsBO.getSortOrder() == 1) {
          sql.append(" order by st." + infraStationsBO.getSortField() + "  desc");
        } else {
          sql.append(" order by st." + infraStationsBO.getSortField() + "  asc");
        }
      } else {
        sql.append(" order by st.createTime desc");
      }
      sqlCount.append(sql.toString());
      // tim kiem co ban
      Query query = entityManager.createQuery(sql.toString(), ViewInfraStationsBO.class);
      // lay tong so ban ghi
      Query queryCount = entityManager.createQuery(sqlCount.toString());
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
          queryCount.setParameter(param.getKey(), param.getValue());
        }
      }
      // Set tong so ban ghi
      result.setTotalRecords((Long) queryCount.getSingleResult());

      // So ban ghi tren 1 trang
      if (infraStationsBO.getRows() != null) {
        query.setMaxResults(infraStationsBO.getRows());
      }
      // bat dau tu ban ghi so
      if (infraStationsBO.getFirst() != null) {
        query.setFirstResult(infraStationsBO.getFirst());
      }
      List<ViewInfraStationsBO> resultList = query.getResultList();
//      for (ViewInfraStationsBO v : resultList) {
//        v.setLongitude(v.getGeometry().getX());
//        v.setLatitude(v.getGeometry().getY());
//      }
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  // Tim kiem nang cao
  public FormResult findAdvanceStation(InfraStationsBO infraStationsBO, Long userId) {
    try {
      FormResult result = new FormResult();
      StringBuilder sqlCount = new StringBuilder("select count(st.stationId) ");
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewInfraStationsBO.class.getName());
      sql.append(" st where 1=1");
      sql.append(" and st.rowStatus = 1 ");
      HashMap<String, Object> params = new HashMap<>();
      sql.append(" and st.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
      sql.append(" and st.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
      params.put("userId", userId);
      if (infraStationsBO.getStationCode() != null && StringUtils.isNotEmpty(infraStationsBO.getStationCode().trim())) {
        sql.append(" and st.stationCode like :stationCode");
        params.put("stationCode", CommonUtils.getLikeCondition(infraStationsBO.getStationCode()));
      }
      if (infraStationsBO.getFillerStationCode() != null && StringUtils.isNotEmpty(infraStationsBO.getFillerStationCode().trim())) {
        sql.append(" and st.stationCode like :fillerStationCode");
        params.put("fillerStationCode", CommonUtils.getLikeCondition(infraStationsBO.getFillerStationCode()));
      }
      if (infraStationsBO.getDeptId() != null) {
        sql.append(" and st.deptId = :deptId");
        params.put("deptId", infraStationsBO.getDeptId());
      }
      if (infraStationsBO.getDeptName() != null && StringUtils.isNotEmpty(infraStationsBO.getDeptName().trim())) {
        sql.append(" and st.deptName like :deptName");
        params.put("deptName", CommonUtils.getLikeCondition(infraStationsBO.getDeptName()));
      }
      if (infraStationsBO.getLocationId() != null) {
        sql.append(" and st.locationId = :locationId");
        params.put("locationId", infraStationsBO.getLocationId());
      }
      if (infraStationsBO.getLocationName() != null && StringUtils.isNotEmpty(infraStationsBO.getLocationName().trim())) {
        sql.append(" and st.locationName like :locationName");
        params.put("locationName", CommonUtils.getLikeCondition(infraStationsBO.getLocationName()));
      }
      if (infraStationsBO.getTerrain() != null) {
        sql.append(" and st.terrain = :terrain");
        params.put("terrain", infraStationsBO.getTerrain());
      }
      if (infraStationsBO.getHouseOwnerName() != null && StringUtils.isNotEmpty(infraStationsBO.getHouseOwnerName().trim())) {
        sql.append(" and st.houseOwnerName like :houseOwnerName");
        params.put("houseOwnerName", CommonUtils.getLikeCondition(infraStationsBO.getHouseOwnerName()));
      }
      if (infraStationsBO.getHouseOwnerPhone() != null && StringUtils.isNotEmpty(infraStationsBO.getHouseOwnerPhone().trim())) {
        sql.append(" and st.houseOwnerPhone like :houseOwnerPhone");
        params.put("houseOwnerPhone", CommonUtils.getLikeCondition(infraStationsBO.getHouseOwnerPhone()));
      }
      if (infraStationsBO.getAddress() != null && StringUtils.isNotEmpty(infraStationsBO.getAddress().trim())) {
        sql.append(" and st.address like :address");
        params.put("address", CommonUtils.getLikeCondition(infraStationsBO.getAddress()));
      }
      if (infraStationsBO.getOwnerId() != null) {
        sql.append(" and st.ownerId =:ownerId");
        params.put("ownerId", infraStationsBO.getOwnerId());
      }
      if (infraStationsBO.getConstructionDate() != null) {
        sql.append(" and st.constructionDate =:constructionDate");
        params.put("constructionDate", infraStationsBO.getConstructionDate());
      }
      if (infraStationsBO.getStatus() != null) {
        sql.append(" and st.status =:status");
        params.put("status", infraStationsBO.getStatus());
      }
      if (infraStationsBO.getHouseStationTypeId() != null) {
        sql.append(" and st.houseStationTypeId =:houseStationTypeId");
        params.put("houseStationTypeId", infraStationsBO.getHouseStationTypeId());
      }
      if (infraStationsBO.getStationTypeId() != null) {
        sql.append(" and st.stationTypeId = :stationTypeId");
        params.put("stationTypeId", infraStationsBO.getStationTypeId());
      }
      if (infraStationsBO.getStationFeatureId() != null) {
        sql.append(" and st.stationFeatureId = :stationFeatureId");
        params.put("stationFeatureId", infraStationsBO.getStationFeatureId());
      }
      if (infraStationsBO.getBackupStatus() != null) {
        sql.append(" and st.backupStatus = :backupStatus");
        params.put("backupStatus", infraStationsBO.getBackupStatus());
      }
      if (infraStationsBO.getPosition() != null) {
        sql.append(" and st.position = :position");
        params.put("position", infraStationsBO.getPosition());
      }
      if (infraStationsBO.getLengthStr() != null && StringUtils.isNotEmpty(infraStationsBO.getLengthStr().trim())) {
        sql.append(" and CONVERT(st.length, CHAR(150)) like :length");
        params.put("length", CommonUtils.getLikeCondition(infraStationsBO.getLengthStr()));
      }
      if (infraStationsBO.getWidthStr() != null && StringUtils.isNotEmpty(infraStationsBO.getWidthStr().trim())) {
        sql.append(" and CONVERT(st.width, CHAR(150)) like :width");
        params.put("width", CommonUtils.getLikeCondition(infraStationsBO.getWidthStr()));
      }
      if (infraStationsBO.getHeightStr() != null && StringUtils.isNotEmpty(infraStationsBO.getHeightStr().trim())) {
        sql.append(" and CONVERT(st.height, CHAR(150)) like :height");
        params.put("height", CommonUtils.getLikeCondition(infraStationsBO.getHeightStr()));
      }
      if (infraStationsBO.getHeightestBuildingStr() != null && StringUtils.isNotEmpty(infraStationsBO.getHeightestBuildingStr().trim())) {
        sql.append(" and CONVERT(st.heightestBuilding, CHAR(150)) like :heightestBuilding");
        params.put("heightestBuilding",
            CommonUtils.getLikeCondition(infraStationsBO.getHeightestBuildingStr()));
      }
      if (infraStationsBO.getLongString() != null && StringUtils.isNotEmpty(infraStationsBO.getLongString().trim())) {
        sql.append(" AND CONVERT(st.longitude, CHAR(150)) LIKE :longitude");
        params.put("longitude", CommonUtils.getLikeCondition(infraStationsBO.getLongString()));
      }
      if (infraStationsBO.getLaString() != null && StringUtils.isNotEmpty(infraStationsBO.getLaString().trim())) {
        sql.append(" AND CONVERT(st.latitude, CHAR(150)) LIKE :latitude");
        params.put("latitude", CommonUtils.getLikeCondition(infraStationsBO.getLaString()));
      }
      if (infraStationsBO.getAuditType() != null) {
        sql.append(" and st.auditType = :auditType");
        params.put("auditType", infraStationsBO.getAuditType());
      }
      if (infraStationsBO.getAuditStatus() != null) {
        sql.append(" and st.auditStatus = :auditStatus");
        params.put("auditStatus", infraStationsBO.getAuditStatus());
      }
      if (infraStationsBO.getAuditReason() != null && StringUtils.isNotEmpty(infraStationsBO.getNote().trim())) {
        sql.append(" and st.auditReason like :auditReason");
        params.put("auditReason", CommonUtils.getLikeCondition(infraStationsBO.getAuditReason()));
      }
      if (infraStationsBO.getNote() != null && StringUtils.isNotEmpty(infraStationsBO.getNote().trim())) {
        sql.append(" and st.note like :note");
        params.put("note", CommonUtils.getLikeCondition(infraStationsBO.getNote()));
      }
      if (infraStationsBO.getFileCheck() != null && StringUtils.isNotEmpty(infraStationsBO.getFileCheck().trim())) {
        sql.append(" and st.fileCheck like :fileCheck");
        params.put("fileCheck", CommonUtils.getLikeCondition(infraStationsBO.getFileCheck()));
      }
      if (infraStationsBO.getFileListed() != null && StringUtils.isNotEmpty(infraStationsBO.getFileListed().trim())) {
        sql.append(" and st.fileListed like :fileListed");
        params.put("fileListed", CommonUtils.getLikeCondition(infraStationsBO.getFileListed()));
      }
      if (infraStationsBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraStationsBO.getBasicInfo().trim())) {
        sql.append(" and (st.stationCode like :basicInfo ");
        sql.append(" or st.locationName like :basicInfo");
        sql.append(" or st.deptName like :basicInfo)");
        params.put("basicInfo", CommonUtils.getLikeCondition(infraStationsBO.getBasicInfo()));
      }
      if (infraStationsBO.getSortField() != null && infraStationsBO.getSortOrder() != null) {
        if (infraStationsBO.getSortOrder() == 1) {
          sql.append(" order by st." + infraStationsBO.getSortField() + "  desc");
        } else {
          sql.append(" order by st." + infraStationsBO.getSortField() + "  asc");
        }
      } else {
        sql.append(" order by st.stationCode asc");
      }
      sqlCount.append(sql.toString());
      // tim kiem nang cao
      Query query = entityManager.createQuery(sql.toString(), ViewInfraStationsBO.class);
      // lay tong so ban ghi
      Query queryCount = entityManager.createQuery(sqlCount.toString());
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
          queryCount.setParameter(param.getKey(), param.getValue());
        }
      }
      // Set tong so ban ghi
      result.setTotalRecords((Long) queryCount.getSingleResult());
      if (infraStationsBO.getRows() != null) {
        // So ban ghi tren 1 trang
        query.setMaxResults(infraStationsBO.getRows());
      }
      if (infraStationsBO.getFirst() != null) {
        // bat dau tu ban ghi so
        query.setFirstResult(infraStationsBO.getFirst());
      }
      List<ViewInfraStationsBO> resultList = query.getResultList();
      result.setListData(resultList);
      return result;
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  // Them nha tram
  public Long saveStation(InfraStationsBO infraStationsBO) {
    Long stationId = infraStationsBO.getStationId();
    try {
      Session session = entityManager.unwrap(Session.class);
      if (infraStationsBO.getStationId() != null) {
        infraStationsBO.setUpdateTime(new Date());
      }
      if (infraStationsBO.getStationId() == null) {
        InfraPointsBO infraPointsBO = new InfraPointsBO();
        infraPointsBO.setType(BigInteger.ONE);
        stationId = (Long) session.save(infraPointsBO);
        infraStationsBO.setStationId(stationId);
        infraStationsBO.setStationCode(infraStationsBO.getStationCode().toUpperCase());
        infraStationsBO.setCreateTime(new Date());
        infraStationsBO.setRowStatus(1L);
      }
      session.saveOrUpdate(infraStationsBO);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return stationId;
  }

  @Override
  // tim nha tram theo id
  public ViewInfraStationsBO findStationById(Long id) {
    ViewInfraStationsBO infraStationsBO = entityManager.find(ViewInfraStationsBO.class, id);
    return infraStationsBO;
  }

  @Override
  // Xoa nha tram
  public int delete(Long id) {
    Session session = entityManager.unwrap(Session.class);
    InfraStationsBO infraStationsBO = entityManager.find(InfraStationsBO.class, id);
    if (infraStationsBO != null && infraStationsBO.getRowStatus() == 1) {
      infraStationsBO.setRowStatus(2L);
      session.save(infraStationsBO);
      return 1;
    }
    return 0;
  }

  @Override
  public InfraStationsBO findStationByCode(String code, Long userId) {
    List<InfraStationsBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraStationsBO.class.getName());
    sql.append(" st where 1=1 ");
    sql.append(" and st.stationCode = :code and st.rowStatus = 1 ");
    sql.append(" and st.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and st.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), InfraStationsBO.class).setParameter("code", code)
        .setParameter("userId", userId);
    try {
      result = query.getResultList();
    } catch (Exception e) {
      log.error("Exception", e);
    }
    if (!CommonUtils.isEmpty(result)) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public boolean checkStationByUserPermission(String code, Long userId) {
    List<InfraStationsBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraStationsBO.class.getName());
    sql.append(" st where 1=1 ");
    sql.append(" and st.stationCode = :code and st.rowStatus = 1 ");
    sql.append(" and st.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and st.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), InfraStationsBO.class).setParameter("code", code)
        .setParameter("userId", userId);
    try {
      result = query.getResultList();
    } catch (Exception e) {
      log.error("Exception", e);
    }
    if (!CommonUtils.isEmpty(result)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public Long saveDocument(DocumentBO documentBO) {
    Long stationId = null;
    try {
      Session session = entityManager.unwrap(Session.class);
      documentBO.setDocumentType(BigInteger.ONE);
      session.saveOrUpdate(documentBO);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return stationId;
  }

  @Override
  public DocumentBO getDocument(Long stationId, int type) {
    List<DocumentBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(DocumentBO.class.getName());
    sql.append(" st where 1=1 ");
    sql.append(" and st.documentId = :documentId");
    sql.append(" and st.documentType = :documentType");
    sql.append(" and st.attachFileType = :attachFileType");
    Query query = entityManager.createQuery(sql.toString(), DocumentBO.class).setParameter("documentId", stationId)
        .setParameter("documentType", BigInteger.ONE).setParameter("attachFileType", BigInteger.valueOf(type));
    try {
      result = query.getResultList();
    } catch (Exception e) {
      log.error("Exception", e);
    }
    if (!CommonUtils.isEmpty(result)) {
      return result.get(0);
    } else {
      return null;
    }
  }
}
