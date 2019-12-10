package com.viettel.nims.transmission.dao;
import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.*;
import com.viettel.nims.transmission.utils.Constains;
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
 * Created by VAN-BA on 8/24/2019.
 */
@Slf4j
@Repository
public class PillarDaoImpl  implements PillarDao {

  @Autowired
  private EntityManager entityManager;
  private Object obj;

//  @Override
//  // Tim kiem co ban
//  public FormResult findBasicPillar(PillarsBO pillarsBO) {
//    StringBuilder sqlCount = new StringBuilder("Select Count(st.pillarCode) ");
//    StringBuilder sql = new StringBuilder("from ");
//    sql.append(ViewPillarsBO.class.getName());
//    sql.append(" st where 1=1");
//    sql.append(" and st.rowStatus = 1 ");
//    sql.append(" order by st.pillarCode");
//    sqlCount.append(sql.toString());
//    // tim kiem co ban
//    Query query = entityManager.createQuery(sql.toString(), ViewPillarsBO.class);
//    // lay tong so ban ghi
//    Query queryCount = entityManager.createQuery(sqlCount.toString());
//    if (StringUtils.isNotEmpty(pillarsBO.getBasicInfo().trim())) {
//      query.setParameter("basicInfo", CommonUtils.getLikeCondition(pillarsBO.getBasicInfo()));
//      queryCount.setParameter("basicInfo", CommonUtils.getLikeCondition(pillarsBO.getBasicInfo()));
//    }
//    FormResult result = new FormResult();
//    //Set tong so ban ghi
//    result.setTotalRecords((Long) queryCount.getSingleResult());
//    // So ban ghi tren 1 trang
//    query.setMaxResults(pillarsBO.getRows());
//    // bat dau tu ban ghi so
//    query.setFirstResult(pillarsBO.getFirst());
//    try {
//      List<ViewPillarsBO> resultList = query.getResultList();
////      for (ViewPillarsBO v : resultList) {
////        v.setLongitude(v.getGeometry().getX());
////        v.setLatitude(v.getGeometry().getY());
////      }
//      result.setListData(resultList);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return result;
//  }

  @Override
  // Get list pillar type code
  public FormResult getPillarTypeCodeList() {
    //    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(ic.laneCode) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatPillarTypeBO.class.getName());
    sql.append(" cp where 1=1 ");
    sql.append(" and cp.rowStatus = 1");
    sql.append(" order by cp.pillarTypeCode");
    HashMap<String, Object> params = new HashMap<>();
//    if (entity.getLaneCode() != null && StringUtils.isNotEmpty(entity.getLaneCode().trim())) {
//      sql.append(" and ic.laneCode LIKE :laneCode");
//      params.put("laneCode", CommonUtils.getLikeCondition(entity.getLaneCode()));
//    }
//    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), CatPillarTypeBO.class);
//    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
//        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    List<CatPillarTypeBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
    return result;
  }

//  @Override
//  // Get list owner
//  public FormResult getOwnerName() {
//    StringBuilder sql = new StringBuilder("select ct.itemName from ");
//    sql.append(CatItemBO.class.getName());
//    sql.append(" ct where 1=1");
//    FormResult result = new FormResult();
//    try {
//      Query query = entityManager.createQuery(sql.toString());
//      List<CatItemBO> resultList = query.getResultList();
//      result.setListData(resultList);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return result;
//  }

  @Override
  // Get list laneCode
  public FormResult getLaneCodeList(InfraCableLanesBO entity) {
//    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(ic.laneCode) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraCableLanesBO.class.getName());
    sql.append(" ic where 1=1 ");
    HashMap<String, Object> params = new HashMap<>();
    if (entity.getLaneCode() != null && StringUtils.isNotEmpty(entity.getLaneCode().trim())) {
      sql.append(" and ic.laneCode LIKE :laneCode");
      params.put("laneCode", CommonUtils.getLikeCondition(entity.getLaneCode()));
    }
//    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class);
//    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
//        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    List<InfraCableLanesBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
    return result;
  }

  public boolean checkExitPillarCode(String pillarCode, Long pillarId){
    HashMap<String, Object> params = new HashMap<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(PillarsBO.class.getName());
    sql.append(" cd where 1=1 ");
    sql.append(" and cd.pillarCode = :pillarCode ");
    params.put("pillarCode", pillarCode);
    sql.append(" and cd.rowStatus = 1 ");
    Query query = entityManager.createQuery(sql.toString(), PillarsBO.class);
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
      }
    }
    List<PillarsBO> resultList = query.getResultList();
    if ((resultList.size() > 0 && pillarId != null) || resultList.size() == 0) {
      return true;
    }
    return false;
  }

  @Override
  // Get list laneCode cho popup
  public FormResult getListLaneCode(InfraCableLanesBO entity, Long userId) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.laneCode) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewInfraCableLaneBO.class.getName());
    sql.append(" cd where 1=1 ");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and cd.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and cd.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    params.put("userId", userId);
    if (entity.getLaneCode() != null && StringUtils.isNotEmpty(entity.getLaneCode().trim())) {
      sql.append(" and cd.laneCode LIKE :laneCode");
      params.put("laneCode", CommonUtils.getLikeCondition(entity.getLaneCode()));
    }
    if (entity.getLaneCodeTemp() != null && StringUtils.isNotEmpty(entity.getLaneCodeTemp().trim())) {
      sql.append(" and cd.laneCode LIKE :laneCodeTemp");
      params.put("laneCodeTemp", CommonUtils.getLikeCondition(entity.getLaneCodeTemp()));
    }
    if (entity.getDeptName() != null && StringUtils.isNotEmpty(entity.getDeptName().trim())) {
      sql.append(" and cd.deptName LIKE :deptName");
      params.put("deptName", CommonUtils.getLikeCondition(entity.getDeptName()));
    }
    if (entity.getLocationName() != null && StringUtils.isNotEmpty(entity.getLocationName().trim())) {
      sql.append(" and cd.locationName LIKE :locationName");
      params.put("locationName", CommonUtils.getLikeCondition(entity.getLocationName()));
    }
    if (entity.getSortField() != null && entity.getSortOrder() != null) {
      if (entity.getSortOrder() == 1) {
        sql.append(" order by cd." + entity.getSortField() + "  desc");
      } else {
        sql.append(" order by cd." + entity.getSortField() + "  asc");
      }
    } else {
      sql.append(" order by cd.laneCode");
    }
    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), ViewInfraCableLaneBO.class);
    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    if (entity.getRows() != null) {
      query.setMaxResults(entity.getRows());
    }
    if (entity.getFirst() != null) {
      query.setFirstResult(entity.getFirst());
    }
    List<ViewInfraCableLaneBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
    result.setTotalRecords((Long) queryCount.getSingleResult());
    return result;
  }

//  @Override
//  // Get tuyen cap cho cot/be chua mang xong -> not use
//  public FormResult getListLaneCodeForSleeve(InfraCableLanesBO entity, Long userId) {
//    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.holderId) ");
//    StringBuilder sql = new StringBuilder("from ");
//    sql.append(ViewHangLaneCodeHasSleeveBO.class.getName());
//    sql.append(" cd where 1=1 ");
//    HashMap<String, Object> params = new HashMap<>();
//    sql.append(" and cd.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
//    sql.append(" and cd.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
//    params.put("userId", userId);
//    if (entity.getHolderId() != null) {
//      sql.append(" and cd.holderId = :holderId");
//      params.put("holderId", entity.getHolderId());
//    }
//    if (entity.getLaneCode() != null && StringUtils.isNotEmpty(entity.getLaneCode().trim())) {
//      sql.append(" and cd.laneCode LIKE :laneCode");
//      params.put("laneCode", CommonUtils.getLikeCondition(entity.getLaneCode()));
//    }
//    if (entity.getLaneCodeTemp() != null && StringUtils.isNotEmpty(entity.getLaneCodeTemp().trim())) {
//      sql.append(" and cd.laneCode LIKE :laneCodeTemp");
//      params.put("laneCodeTemp", CommonUtils.getLikeCondition(entity.getLaneCodeTemp()));
//    }
//    if (entity.getPathName() != null && StringUtils.isNotEmpty(entity.getPathName().trim())) {
//      sql.append(" and cd.pathName LIKE :pathName");
//      params.put("pathName", CommonUtils.getLikeCondition(entity.getPathName()));
//    }
//    if (entity.getPathLocalName() != null && StringUtils.isNotEmpty(entity.getPathLocalName().trim())) {
//      sql.append(" and cd.pathLocalName LIKE :pathLocalName");
//      params.put("pathLocalName", CommonUtils.getLikeCondition(entity.getPathLocalName()));
//    }
//    sql.append(" group by cd.holderId,cd.laneCode");
//    sqlCount.append(sql.toString());
//    if (entity.getSortField() != null && entity.getSortOrder() != null) {
//      if (entity.getSortOrder() == 1) {
//        sql.append(" order by cd." + entity.getSortField() + "  desc");
//      } else {
//        sql.append(" order by cd." + entity.getSortField() + "  asc");
//      }
//    } else {
//      sql.append(" order by cd.laneCode");
//    }
//    Query query = entityManager.createQuery(sql.toString(), ViewHangLaneCodeHasSleeveBO.class);
//    Query queryCount = entityManager.createQuery(sqlCount.toString());
//    if (params.size() > 0) {
//      for (Map.Entry<String, Object> param : params.entrySet()) {
//        query.setParameter(param.getKey(), param.getValue());
//        queryCount.setParameter(param.getKey(), param.getValue());
//      }
//    }
//    if (entity.getRows() != null) {
//      query.setMaxResults(entity.getRows());
//    }
//    if (entity.getFirst() != null) {
//      query.setFirstResult(entity.getFirst());
//    }
//    List<ViewHangLaneCodeHasSleeveBO> resultList = query.getResultList();
//    FormResult result = new FormResult();
//    result.setListData(resultList);
//    result.setTotalRecords(Long.valueOf(resultList.size()));
//    return result;
//  }

  @Override
  // Get list tuyen cap vat qua cot/be dung chung cho ca mang xong
  public FormResult getListLaneCodeHangPillar(PillarsBO entity, Long userId) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.cableId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewHangCableLaneBO.class.getName());
    sql.append(" cd where 1=1 ");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and cd.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and cd.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    params.put("userId", userId);
    if (entity.getPillarId() != null) {
      sql.append(" and cd.pillarId LIKE :pillarId");
      params.put("pillarId", entity.getPillarId());
    }
    if (entity.getPoolId() != null) {
      sql.append(" and cd.poolId LIKE :poolId");
      params.put("poolId", entity.getPoolId());
    }
    if (entity.getPillarCode() != null && StringUtils.isNotEmpty(entity.getPillarCode().trim())) {
      sql.append(" and cd.pillarCode LIKE :pillarCode");
      params.put("pillarCode", CommonUtils.getLikeCondition(entity.getPillarCode()));
    }
    if (entity.getPillarCodeTemp() != null && StringUtils.isNotEmpty(entity.getPillarCodeTemp().trim())) {
      sql.append(" and cd.pillarCode LIKE :pillarCodeTemp");
      params.put("pillarCodeTemp", CommonUtils.getLikeCondition(entity.getPillarCodeTemp()));
    }
    if (entity.getPoolCode() != null && StringUtils.isNotEmpty(entity.getPoolCode().trim())) {
        sql.append(" and cd.poolCode LIKE :poolCode");
        params.put("poolCode", CommonUtils.getLikeCondition(entity.getPoolCode()));
      }
    if (entity.getCableCode() != null && StringUtils.isNotEmpty(entity.getCableCode().trim())) {
      sql.append(" and cd.cableCode LIKE :cableCode");
      params.put("cableCode", CommonUtils.getLikeCondition(entity.getCableCode()));
    }
    if (entity.getLaneCode() != null && StringUtils.isNotEmpty(entity.getLaneCode().trim())) {
      sql.append(" and cd.laneCode LIKE :laneCode");
      params.put("laneCode", CommonUtils.getLikeCondition(entity.getLaneCode()));
    }
    if (entity.getLaneCodeTemp() != null && StringUtils.isNotEmpty(entity.getLaneCodeTemp().trim())) {
      sql.append(" and cd.laneCode LIKE :laneCodeTemp");
      params.put("laneCodeTemp", CommonUtils.getLikeCondition(entity.getLaneCodeTemp()));
    }
    if (entity.getPathName() != null && StringUtils.isNotEmpty(entity.getPathName().trim())) {
      sql.append(" and cd.pathName LIKE :pathName");
      params.put("pathName", CommonUtils.getLikeCondition(entity.getPathName()));
    }
    if (entity.getPathLocalName() != null && StringUtils.isNotEmpty(entity.getPathLocalName().trim())) {
      sql.append(" and cd.pathLocalName LIKE :pathLocalName");
      params.put("pathLocalName", CommonUtils.getLikeCondition(entity.getPathLocalName()));
    }
    if (entity.getPillarId() != null)
      sql.append(" group by cd.pillarId,cd.laneCode");
    if (entity.getPoolId() != null)
      sql.append(" group by cd.poolId,cd.laneCode");
    if (entity.getPillarCode() != null && StringUtils.isNotEmpty(entity.getPillarCode().trim())){
      sql.append(" group by cd.laneCode, cd.cableCode,cd.pillarCode");
    }
    if (entity.getPoolCode() != null && StringUtils.isNotEmpty(entity.getPoolCode().trim())) {
      sql.append(" group by cd.laneCode, cd.cableCode,cd.poolCode");
    }
      if (entity.getSortField() != null && entity.getSortOrder() != null) {
      if (entity.getSortOrder() == 1) {
        sql.append(" order by cd." + entity.getSortField() + "  desc");
      } else {
        sql.append(" order by cd." + entity.getSortField() + "  asc");
      }
    } else {
      sql.append(" order by cd.laneCode");
    }
    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), ViewHangCableLaneBO.class);
    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    if (entity.getRows() != null) {
      query.setMaxResults(entity.getRows());
    }
    if (entity.getFirst() != null) {
      query.setFirstResult(entity.getFirst());
    }
    List<ViewHangCableLaneBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
//    result.setTotalRecords((Long) queryCount.getSingleResult());
    result.setTotalRecords(Long.valueOf(resultList.size()));
    return result;
  }

  @Override
  // Tim kiem nang cao
  public FormResult findAdvancePillar(PillarsBO pillarsBO, Long userId) {
    String s = "";
    StringBuilder sqlCount = new StringBuilder("Select Count(st.pillarTypeId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewPillarsBO.class.getName());
    sql.append(" st where 1=1");
    sql.append(" AND st.rowStatus = 1");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and st.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and st.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    params.put("userId", userId);
    if (pillarsBO.getBasicInfo() != null && StringUtils.isNotEmpty(pillarsBO.getBasicInfo().trim())) {
      sql.append(" AND st.pillarCode LIKE :basicInfo");
      params.put("basicInfo", CommonUtils.getLikeCondition(pillarsBO.getBasicInfo()));
    }
    if (pillarsBO.getLaneCode() != null && StringUtils.isNotEmpty(pillarsBO.getLaneCode().trim())) {
      sql.append(" AND st.laneCode LIKE :LANE_CODE");
      params.put("LANE_CODE", CommonUtils.getLikeCondition(pillarsBO.getLaneCode()));
    }
    if (pillarsBO.getPillarCode() != null && StringUtils.isNotEmpty(pillarsBO.getPillarCode().trim())) {
      sql.append(" AND st.pillarCode LIKE :PILLAR_CODE");
      params.put("PILLAR_CODE", CommonUtils.getLikeCondition(pillarsBO.getPillarCode()));
    }
    if (pillarsBO.getPillarTypeId() != null) {
      sql.append(" AND st.pillarTypeId = :PILLAR_TYPE_ID");
      params.put("PILLAR_TYPE_ID", pillarsBO.getPillarTypeId());
    }
    if (pillarsBO.getPillarTypeIdTemp() != null) {
      sql.append(" AND st.pillarTypeId = :PILLAR_TYPE_ID_TEMP");
      params.put("PILLAR_TYPE_ID_TEMP", pillarsBO.getPillarTypeIdTemp());
    }
    if (pillarsBO.getDeptId() != null) {
      sql.append(" AND st.deptId LIKE :DEPT_ID");
      params.put("DEPT_ID", pillarsBO.getDeptId());
    }
    if (pillarsBO.getLocationId() != null) {
      sql.append(" AND st.locationId LIKE :LOCATION_ID");
      params.put("LOCATION_ID", pillarsBO.getLocationId());
    }
    if (pillarsBO.getOwnerId() != null) {
      sql.append(" AND st.ownerId LIKE :OWNER_ID");
      params.put("OWNER_ID", pillarsBO.getOwnerId());
    }
    if (pillarsBO.getOwnerIdTemp() != null) {
      sql.append(" AND st.ownerId LIKE :OWNER_ID_TEMP");
      params.put("OWNER_ID_TEMP", pillarsBO.getOwnerIdTemp());
    }
    if (pillarsBO.getPillarStatusCable() != null) {
      //Neu cot da treo cap
      if (pillarsBO.getPillarStatusCable() == 1) {
        sql.append(" AND st.pillarStatusCable = 1 ");
      }
      //Neu cot chua treo cap
      if (pillarsBO.getPillarStatusCable() == 0) {
        sql.append(" AND st.pillarStatusCable = 0 ");
      }
    }
    if (pillarsBO.getAddress() != null && StringUtils.isNotEmpty(pillarsBO.getAddress().trim())) {
      sql.append(" AND st.address LIKE :ADDRESS");
      params.put("ADDRESS", CommonUtils.getLikeCondition(pillarsBO.getAddress()));
    }
    if (pillarsBO.getStatus() != null) {
      sql.append(" AND st.status LIKE :STATUS");
      params.put("STATUS", pillarsBO.getStatus());
    }
    if (pillarsBO.getLaString() != null && StringUtils.isNotEmpty(pillarsBO.getLaString().trim())) {
      sql.append(" AND CONVERT(st.latitude, CHAR(150)) LIKE :latitude");
      params.put("latitude", CommonUtils.getLikeCondition(pillarsBO.getLaString()));
    }
    if (pillarsBO.getLongString() != null && StringUtils.isNotEmpty(pillarsBO.getLongString().trim())) {
      sql.append(" AND CONVERT(st.longitude, CHAR(150)) LIKE :longitude");
      params.put("longitude", CommonUtils.getLikeCondition(pillarsBO.getLongString()));
    }
    if (pillarsBO.getNote() != null && StringUtils.isNotEmpty(pillarsBO.getNote().trim())) {
      sql.append(" AND st.note LIKE :NOTE");
      params.put("NOTE", CommonUtils.getLikeCondition(pillarsBO.getNote()));
    }
    if (pillarsBO.getConstructionDate() != null) {
//      Timestamp timestamp = new Timestamp(pillarsBO.getConstructionDate().getTime());
//      String s1 = timestamp.toString();
      sql.append(" AND st.constructionDate LIKE :CONSTRUCTION_DATE");
      params.put("CONSTRUCTION_DATE", pillarsBO.getConstructionDate());
    }
    if (pillarsBO.getSortField() != null && pillarsBO.getSortOrder() != null) {
      if (pillarsBO.getSortOrder() == 1) {
        sql.append(" order by st." + pillarsBO.getSortField() + "  desc");
      } else {
        sql.append(" order by st." + pillarsBO.getSortField() + "  asc");
      }
    } else {
      sql.append(" order by st.pillarCode");
    }
    sqlCount.append(sql.toString());
    // tim kiem nang cao
    Query query = entityManager.createQuery(sql.toString(), ViewPillarsBO.class);
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
    if (pillarsBO.getRows() != null)
      query.setMaxResults(pillarsBO.getRows());
    // bat dau tu ban ghi so
    if(pillarsBO.getFirst() != null)
    query.setFirstResult(pillarsBO.getFirst());
    List<ViewPillarsBO> resultList = query.getResultList();

//    for (ViewPillarsBO v : resultList) {
//      v.setLongitude(v.getGeometry().getX());
//      v.setLatitude(v.getGeometry().getY());
//    }
    result.setListData(resultList);
    return result;
  }

  @Override
  // Tim kiem nang cao
  public FormResult getPillarList(PillarsBO pillarsBO, Long userId) throws NoSuchFieldException {
    StringBuilder sqlCount = new StringBuilder("Select Count(st.pillarTypeId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewPillarsBO.class.getName());
    sql.append(" st where 1=1");
    sql.append(" AND st.rowStatus = 1");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and st.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and st.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    params.put("userId", userId);
    if (pillarsBO.getPillarCode() != null && StringUtils.isNotEmpty(pillarsBO.getPillarCode().trim())) {
      sql.append(" AND st.pillarCode LIKE :PILLAR_CODE");
      params.put("PILLAR_CODE", CommonUtils.getLikeCondition(pillarsBO.getPillarCode()));
    }
    if (pillarsBO.getPillarTypeCode() != null && StringUtils.isNotEmpty(pillarsBO.getPillarTypeCode().trim())) {
      sql.append(" AND st.pillarTypeCode LIKE :PILLAR_TYPE_CODE");
      params.put("PILLAR_TYPE_CODE", CommonUtils.getLikeCondition(pillarsBO.getPillarTypeCode()));
    }
    if (pillarsBO.getPathName() != null && StringUtils.isNotEmpty(pillarsBO.getPathName().trim())) {
      sql.append(" AND st.pathName LIKE :PATH_NAME");
      params.put("PATH_NAME", CommonUtils.getLikeCondition(pillarsBO.getPathName()));
    }
    if (pillarsBO.getPathLocalName() != null && StringUtils.isNotEmpty(pillarsBO.getPathLocalName().trim())) {
      sql.append(" AND st.pathLocalName LIKE :PATH_LOCAL_NAME");
      params.put("PATH_LOCAL_NAME", CommonUtils.getLikeCondition(pillarsBO.getPathLocalName()));
    }
    if (pillarsBO.getSortField() != null && pillarsBO.getSortOrder() != null) {
      if (pillarsBO.getSortOrder() == 1) {
        sql.append(" order by st." + pillarsBO.getSortField() + "  desc");
      } else {
        sql.append(" order by st." + pillarsBO.getSortField() + "  asc");
      }
    } else {
      sql.append(" order by st.pillarCode desc");
    }
    sqlCount.append(sql.toString());
    // tim kiem nang cao
    Query query = entityManager.createQuery(sql.toString(), ViewPillarsBO.class);
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
    if (pillarsBO.getRows() != null)
      query.setMaxResults(pillarsBO.getRows());
    // bat dau tu ban ghi so
    if(pillarsBO.getFirst() != null)
      query.setFirstResult(pillarsBO.getFirst());
    List<ViewPillarsBO> resultList = query.getResultList();
    result.setListData(resultList);
    return result;
  }

  @Override
  //Them cot
  public Long savePillar(PillarsBO pillarsBO) {
    try {
      Long pillarId = pillarsBO.getPillarId();
      Session session = entityManager.unwrap(Session.class);
      if (pillarsBO.getPillarId() != null) {
        pillarsBO.setUpdateTime(new Date());
      }
      if (pillarsBO.getPillarId() == null) {
        InfraPointsBO infraPointsBO = new InfraPointsBO();
        infraPointsBO.setType(BigInteger.valueOf(2));
        pillarId = (Long) session.save(infraPointsBO);
        pillarsBO.setPillarId(pillarId);
        pillarsBO.setCreateTime(new Date());
        pillarsBO.setRowStatus(1);
      }
      session.saveOrUpdate(pillarsBO);
      return pillarId;
    } catch (Exception e) {
      log.error("Exception", e);
      return null;
    }
  }

  @Override
  public PillarsBO findPillarById(Long pillarId) {
    PillarsBO pillarsBO = entityManager.find(PillarsBO.class, pillarId);
    ViewPillarsBO pillarStatusCable = entityManager.find(ViewPillarsBO.class, pillarId);
    pillarsBO.setPillarStatusCable(pillarStatusCable.getPillarStatusCable());
    pillarsBO.setPillarIndex(formatPillarIndex(pillarStatusCable.getPillarIndex()));
      return pillarsBO;
  }

  @Override
  public String getPillarIndex(String laneCode) {
    StringBuilder sqlMax = new StringBuilder("Select Max(st.pillarIndex) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewPillarsBO.class.getName());
    sql.append(" st where 1=1");
    sql.append(" and st.rowStatus = 1 ");
    sql.append(" and st.laneCode = '"+laneCode+"'");
    sqlMax.append(sql.toString());
    Query query = entityManager.createQuery(sqlMax.toString(), Integer.class);
    Integer result = (Integer) query.getSingleResult();
    if(result == null)
      result = 1;
    else
      result += 1;
    return formatPillarIndex(result);
  }

//  public static String getLikeCondition(String str) {
//    if (!str.trim().isEmpty()) {
//      String newStr =
//          str.trim()
//              .replace("\\", "\\\\")
//              .replace("\\t", "\\\\t")
//              .replace("\\n", "\\\\n")
//              .replace("\\r", "\\\\r")
//              .replace("\\z", "\\\\z")
//              .replace("\\b", "\\\\b")
//              .replaceAll("_", "\\\\_")
//              .replaceAll("%", "\\\\%");
//      str = "concat('%'," + "'" + str + "'" + ",'%')";
//    }
//    return str;
//  }

  @Override
  public Boolean isExitByCode(PillarsBO pillarsBO) {
    try {
      HashMap<String, Object> params = new HashMap<>();
      StringBuilder sql = new StringBuilder("from ");
      sql.append(PillarsBO.class.getName());
      sql.append(" cd where 1=1 ");
      sql.append(" and cd.pillarCode = :pillarCode ");
      params.put("pillarCode", pillarsBO.getPillarCode());
      sql.append(" and cd.rowStatus = 1 ");
      Query query = entityManager.createQuery(sql.toString(), PillarsBO.class);
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
        }
      }
      List<PillarsBO> resultList = query.getResultList();
      if (resultList.size() > 0) {
        return true;
      }
      return false;
    } catch (Exception e) {
      // TODO: handle exception
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  public List<ViewInfraSleevesBO> listSleevesInPillar(Long pillarId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewInfraSleevesBO.class.getName());
    sql.append(" sl where 1=1");
    sql.append(" AND sl.holderId = :HOLDERID");
    Query query = entityManager.createQuery(sql.toString(), ViewInfraSleevesBO.class);
    query.setParameter("HOLDERID", pillarId);
    return query.getResultList();
  }

  @Override
  public List<ViewHangCableBO> listPillarHangCable(Long pillarId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewHangCableBO.class.getName());
    sql.append(" sl where 1=1");
    sql.append(" AND sl.holderId = :PILLAR_ID");
    Query query = entityManager.createQuery(sql.toString(), ViewHangCableBO.class);
    query.setParameter("PILLAR_ID", pillarId);
    return query.getResultList();
  }

  //-----------KienNT----------------
  @Override
  public PillarsBO findPillarByCode(String pillarCode) {
    List<PillarsBO> result = new ArrayList<>();
    StringBuilder sql = new StringBuilder("from ");
    sql.append(PillarsBO.class.getName());
    sql.append(" p where 1=1 ");
    sql.append(" and p.pillarCode = :pillarCode and p.rowStatus = 1 ");
    Query query = entityManager.createQuery(sql.toString(), PillarsBO.class).setParameter("pillarCode", pillarCode);
    try {
      result = query.getResultList();
    } catch (Exception e) {
      log.error("Exception", e);
    }

    if (!result.isEmpty()) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public boolean checkRolePillarWithPillarCode(String pillarCode, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(PillarsBO.class.getName());
    sql.append(" p where 1=1 ");
    sql.append("and p.rowStatus = 1 ");
    sql.append(" and p.pillarCode = :pillarCode");
    sql.append(" and p.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and p.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), PillarsBO.class);
    query.setParameter("pillarCode", pillarCode);
    query.setParameter("userId", userId);
    try {
      List<?> result = query.getResultList();
      if(result.size() > 0){
        return true;
      }
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return false;
  }
  //--------------KienNt---------------

  @Override
  public int deletePillar(Long id) {
    Session session = entityManager.unwrap(Session.class);
    PillarsBO pillarsBO = entityManager.find(PillarsBO.class, id);
    if (pillarsBO != null && pillarsBO.getRowStatus() == 1) {
      pillarsBO.setRowStatus(Constains.NUMBER_TWO);
      session.save(pillarsBO);
      return Constains.NUMBER_ONE;
    }
    return Constains.NUMBER_ZERO;
  }

  public static String formatPillarIndex(Integer pillarIndex)
  {
    String s = "";
    if(pillarIndex < 10)
      s = "000" + pillarIndex;
    else if(pillarIndex < 100)
      s = "00" + pillarIndex;
    else if(pillarIndex < 1000)
      s = "0" + pillarIndex;
    else if(pillarIndex < 10000)
      s = "" + pillarIndex;
    else
      s = "";
    return s;
  }



}
