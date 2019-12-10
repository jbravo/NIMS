package com.viettel.nims.transmission.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.viettel.nims.commons.ftp.Common;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.TreeNodeDTO;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Slf4j
@Repository
public class TransmissionDaoImpl implements TransmissionDao {

  @Autowired
  private EntityManager entityManager;


  @Override
  // Tra cuu dia ban
  public FormResult findCatLocation(CatLocationBO catLocationBO, Long userId) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cl.locationId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatLocationBO.class.getName());
    sql.append(" cl where 1=1 ");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and cl.locationId in (" + CommonUtil.sqlAppendLocationIds() + ") ");
    params.put("userId", userId);
    if (catLocationBO.getLocationCode() != null && StringUtils.isNotEmpty(catLocationBO.getLocationCode().trim())) {
      sql.append(" and cl.locationCode like :locationCode");
      params.put("locationCode", CommonUtils.getLikeCondition(catLocationBO.getLocationCode()));
    }
    if (catLocationBO.getLocationName() != null && StringUtils.isNotEmpty(catLocationBO.getLocationName().trim())) {
      sql.append(" and cl.locationName like :locationName");
      params.put("locationName", CommonUtils.getLikeCondition(catLocationBO.getLocationName()));
    }
    if (catLocationBO.getLocationId() != null) {
      sql.append(" and cl.locationId = :locationId");
      params.put("locationId", catLocationBO.getLocationId());
    }
    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), ViewCatLocationBO.class);
    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    FormResult result = new FormResult();
    result.setTotalRecords((Long) queryCount.getSingleResult());

    if (catLocationBO.getRows() != null) {
      query.setMaxResults(catLocationBO.getRows());
    }
    if (catLocationBO.getFirst() != null) {
      query.setFirstResult(catLocationBO.getFirst());
    }
    List<ViewCatLocationBO> resultList = query.getResultList();
    result.setListData(resultList);
    return result;
  }


  @Override
  // Loc dia ban(autocomplete)
  public FormResult filterLocation(String locationName, Long userId) {

    StringBuilder sql = new StringBuilder(" from ");
    sql.append(CatLocationBO.class.getName());
    sql.append("  cl  ");
    sql.append(" where  1=1 ");
    if (StringUtils.isNotEmpty(locationName)) {
      sql.append(" and cl.locationName like :locationName");
    }
    sql.append(" and cl.locationId in (" + CommonUtil.sqlAppendLocationIds() + ") ");
    Query query = entityManager.createQuery(sql.toString(), CatLocationBO.class).setParameter("userId", userId);
    if (StringUtils.isNotEmpty(locationName)) {
      query.setParameter("locationName", CommonUtils.getLikeCondition(locationName));
    }
    List<CatLocationBO> listEntity = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(listEntity);
    return result;
  }

  @Override
  public CatItemBO findCatItemById(long id) {
    CatItemBO catItemBO = entityManager.find(CatItemBO.class, id);
    return catItemBO;
  }

  @Override
  public List<ViewCatItemBO> findCatItemByCategoryId(String catName) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatItemBO.class.getName());
    sql.append(" ci where ci.categoryCode = :categoryCode");
    Query query = entityManager.createQuery(sql.toString(), ViewCatItemBO.class);
    query.setParameter("categoryCode", catName);
    return (List<ViewCatItemBO>) query.getResultList();
  }

  @Override
  // Tra cuu don vi
  public FormResult findDepartment(CatDepartmentEntity entity, Long userId) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.deptId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd where 1=1 ");
    HashMap<String, Object> params = new HashMap<>();
    sql.append(" and cd.deptId in ( " + CommonUtil.sqlAppendDepIds() + ")");
    params.put("userId", userId);
    if (entity.getParentId() != null) {
      sql.append(" and (cd.parentId = :parentId or cd.deptId= :parentId ) ");
      params.put("parentId", entity.getParentId());
    }
    if (entity.getDeptCode() != null && StringUtils.isNotEmpty(entity.getDeptCode().trim())) {
      sql.append(" and cd.deptCode LIKE :deptCode");
      params.put("deptCode", CommonUtils.getLikeCondition(entity.getDeptCode()));
    }
    if (entity.getDeptName() != null && StringUtils.isNotEmpty(entity.getDeptName().trim())) {
      sql.append(" and cd.deptName LIKE :deptName");
      params.put("deptName", CommonUtils.getLikeCondition(entity.getDeptName()));
    }
    if (entity.getPathName() != null && StringUtils.isNotEmpty(entity.getPathName().trim())) {
      sql.append(" and cd.pathName LIKE :pathName");
      params.put("pathName", CommonUtils.getLikeCondition(entity.getPathName()));
    }
    if (entity.getDeptId() != null) {
      sql.append(" and cd.deptId = :deptId ");
      params.put("deptId", entity.getDeptId());
    }
    if (entity.getSortField() != null && entity.getSortOrder() != null) {
      if (entity.getSortOrder() == 1) {
        sql.append(" order by cd." + entity.getSortField() + "  desc");
      } else {
        sql.append(" order by cd." + entity.getSortField() + "  asc");
      }
    }
    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class);
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
    List<ViewCatDepartmentBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
    result.setTotalRecords((Long) queryCount.getSingleResult());
    return result;
  }

  @Override
  // Loc don vi (autocomplete)
  public FormResult filterDepartment(String deptName, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd where 1=1");
    if (deptName != null && StringUtils.isNotEmpty(deptName)) {
      sql.append(" and ( cd.deptName like :deptName or cd.pathName like :deptName )");
    }
    sql.append(" and cd.deptId in (" + CommonUtil.sqlAppendDepIds() + ") ");
    sql.append(" order by cd.pathName");
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class).setParameter("userId", userId);
    if (deptName != null && StringUtils.isNotEmpty(deptName)) {
      query.setParameter("deptName", CommonUtils.getLikeCondition(deptName));
    }
    List<ViewCatDepartmentBO> listEntity = query.getResultList();
    if (listEntity != null && listEntity.size() > 0) {
      for (ViewCatDepartmentBO bo : listEntity) {
        bo.getPathName().replace("/", "");
      }
    }
    FormResult result = new FormResult();
    result.setListData(listEntity);
    return result;
  }

  @Override
  public List<ViewCatTenantsBO> findTenantByStationCode(String stationCode, Long id) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatTenantsBO.class.getName());
    sql.append(" ct where 1=1");
    sql.append(" and ct.stationCode = :stationCode");
    if (id != null) {
      sql.append(" and ct.stationId != :id");
    }
    Query query = entityManager.createQuery(sql.toString(), ViewCatTenantsBO.class);
    query.setParameter("stationCode", stationCode);
    if (id != null) {
      query.setParameter("id", id);
    }
    List<ViewCatTenantsBO> resultList = query.getResultList();
    if (resultList == null) {
      return new ArrayList<>();
    }
    return resultList;
  }

  /**
   * @author ThieuNV
   * @date 26/8/2019
   */
  @Override
  public List<CatPoolTypeBO> getPoolTypeList() {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatPoolTypeBO.class.getName());
    sql.append(" where 1=1 and rowStatus = 1 ");
    Query query = entityManager.createQuery(sql.toString(), CatPoolTypeBO.class);
    return (List<CatPoolTypeBO>) query.getResultList();
  }

  //DungPH
  @Override
  public FormResult findStation(InfraStationsBO stationsBO, Long userId) {

    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(stationId) ");
    StringBuilder sql = new StringBuilder("from ");

    sql.append(InfraStationsBO.class.getName());
    sql.append(" where 1=1 and rowStatus = 1 ");

    sql.append(" and deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");

    HashMap<String, Object> params = new HashMap<>();
    params.put("userId", userId);
    if (stationsBO.getStationCode() != null && StringUtils.isNotEmpty(stationsBO.getStationCode().trim())) {
      sql.append(" and stationCode LIKE :stationCode");
      params.put("stationCode", CommonUtils.getLikeCondition(stationsBO.getStationCode()));
    }
    if (stationsBO.getStationId() != null) {
      sql.append(" and stationId LIKE :stationId");
      params.put("stationId", stationsBO.getStationId());
    }
    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), InfraStationsBO.class);
    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    if (stationsBO.getRows() != null) {
      query.setMaxResults(stationsBO.getRows());
    }
    if (stationsBO.getFirst() != null) {
      query.setFirstResult(stationsBO.getFirst());
    }
    List<InfraStationsBO> resultList = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(resultList);
    result.setTotalRecords((Long) queryCount.getSingleResult());
    return result;
  }
  //DungPH end

  @Override
  public List<CfgMapOwnerBO> getAllCfgOwner() {
//    StringBuilder sql = new StringBuilder("from ");
//    sql.append(CfgMapOwnerBO.class.getName());
//    Query query = entityManager.createQuery(sql.toString(), CfgMapOwnerBO.class);
//    List<CfgMapOwnerBO> resultList = query.getResultList();
//    if (resultList == null) {
//      return new ArrayList<>();
//    }
//    return resultList;
    return null;
  }

  @Override
  public List<CatOpticalCableTypeBO> getAllCableType() {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatOpticalCableTypeBO.class.getName());
    Query query = entityManager.createQuery(sql.toString(), CatOpticalCableTypeBO.class);
    List<CatOpticalCableTypeBO> resultList = query.getResultList();
    if (resultList == null) {
      return new ArrayList<>();
    }
    return resultList;
  }

  @Override
  public ViewTreeCatLocationBO findCatLocationById(Long id, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewTreeCatLocationBO.class.getName());
    sql.append(" where locationId = :id");
    sql.append(" and locationId in (" + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), ViewTreeCatLocationBO.class);
    query.setParameter("id", id).setParameter("userId", userId);
    List<ViewTreeCatLocationBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public ViewCatDepartmentBO findDepartmentById(Long id, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" where deptId = :id");
    sql.append(" and deptId in ( " + CommonUtil.sqlAppendDepIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class).setParameter("userId", userId);
    query.setParameter("id", id);
    List<ViewCatDepartmentBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public Integer getNumberOfCable(InfraCablesBO infraCablesBO) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraCablesBO.class.getName());
    sql.append(" cb where cb.sourceId = :sourceId and cb.destId = :destId");
    Query query = entityManager.createQuery(sql.toString(), InfraCablesBO.class);
    query.setParameter("sourceId", infraCablesBO.getSourceId());
    query.setParameter("destId", infraCablesBO.getDestId());
    List<InfraCablesBO> resultList = query.getResultList();
    if (resultList != null) {
      return resultList.size();
    } else {
      return 0;
    }
  }

  @Override
  public ViewCatItemBO findCatItemByCategoryCodeAndId(Long itemId, String catName) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatItemBO.class.getName());
    sql.append(" ci where ci.categoryCode = :categoryCode  and ci.itemId =:itemId ");
    Query query = entityManager.createQuery(sql.toString(), ViewCatItemBO.class);
    query.setParameter("categoryCode", catName).setParameter("itemId", itemId);
    List<ViewCatItemBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public FormResult treeNodeCatDepartment(Long deptId, Long userId) {
    StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.deptId) ");
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd where 1=1 ");
//    sql.append(" and cd.deptId in (" + CommonUtil.sqlAppendDepIds() + ")");
    HashMap<String, Object> params = new HashMap<>();

    if (deptId == null) {
      sql.append("and cd.deptId in ( ");
      sql.append(" select sud.deptId  from ");
      sql.append(SysUserDepartmentBO.class.getName());
      sql.append(" sud where  sud.userId = :userId");
      sql.append(" ) ");
      params.put("userId", userId);
    }
    if (deptId != null) {
      sql.append("and cd.parentId = :deptId ");
      params.put("deptId", deptId);
    }


    sqlCount.append(sql.toString());
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class);

    Query queryCount = entityManager.createQuery(sqlCount.toString());
    if (params.size() > 0) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
        queryCount.setParameter(param.getKey(), param.getValue());
      }
    }
    List<ViewCatDepartmentBO> resultList = query.getResultList();
    List<TreeNodeDTO> result = new ArrayList<>();
    if (resultList.size() > 0) {
      for (ViewCatDepartmentBO item : resultList) {
        TreeNodeDTO treeNode = new TreeNodeDTO();
        treeNode.setNodeId(item.getDeptId() == null ? null : Long.valueOf(item.getDeptId()));
        treeNode.setParentId(item.getParentId() == null ? null : Long.valueOf(item.getParentId()));
        treeNode.setLabel(item.getDeptName() == null ? "" : item.getDeptName());
        treeNode.setDeptId(item.getDeptId() == null ? null : Long.valueOf(item.getDeptId()));
        treeNode.setDeptName(item.getDeptName() == null ? "" : item.getDeptName());
        treeNode.setPathName(item.getPathName() == null ? "" : item.getPathName());
        treeNode.setPath(item.getPath() == null ? "" : item.getPath());
        treeNode.setPathcode(item.getPathcode() == null ? "" : item.getPathcode());
        result.add(treeNode);
      }
    }

    FormResult formResult = new FormResult();
    formResult.setListData(result);
    formResult.setTotalRecords((Long) queryCount.getSingleResult());
    return formResult;
  }

  @Override
  public FormResult treeNodeCatLocation(ViewTreeCatLocationBO viewTreeCatLocationBO, Long userId) {
    try {
//      StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cl.locationId) ");
      StringBuilder sql = new StringBuilder("from ");
      sql.append(ViewTreeCatLocationBO.class.getName());
      sql.append(" cl where 1=1 ");
      HashMap<String, Object> params = new HashMap<>();
      sql.append(" and cl.deptId in (" + CommonUtil.sqlAppendDepIds() + ") ");
      params.put("userId", userId);
      if (viewTreeCatLocationBO.getDeptId() != null) {
        sql.append(" and cl.deptId = :deptId");
        params.put("deptId", viewTreeCatLocationBO.getDeptId());
      }
      if (viewTreeCatLocationBO.getIsTree() == 1 && viewTreeCatLocationBO.getLocationId() == null) {
        if (viewTreeCatLocationBO.getDeptId() != null) {
          sql.append(" and (cl.parentId is null or cl.parentId not in (");
          sql.append(" select tcl.locationId from ");
          sql.append(ViewTreeCatLocationBO.class.getName());
          sql.append(" tcl where tcl.deptId =: deptId ");
          sql.append(" ) ) ");
        } else {
          sql.append(" and cl.parentId is null or ( cl.deptId in ( ");
//          sql.append(" select tcl.locationId from  ");
//          sql.append(CntDepartmentLocationBO.class.getName());
//          sql.append("  tcl where tcl.deptId in ( ");
          sql.append(" select deptId from  ");
          sql.append(SysUserDepartmentBO.class.getName());
          sql.append(" where userId = :userId ");
          sql.append(") and  cl.parentId not in ( ");
          sql.append(" select tcl.locationId from ");
          sql.append(ViewTreeCatLocationBO.class.getName());
          sql.append(" tcl  where tcl.deptId in ( ");
          sql.append(" select deptId from ");
          sql.append(SysUserDepartmentBO.class.getName());
          sql.append(" where userId =:userId ");
          sql.append(" ) ) ) ");
        }

      }
      if (viewTreeCatLocationBO.getLocationId() != null && viewTreeCatLocationBO.getPathLocalId() == null) {
        if (viewTreeCatLocationBO.getIsTree() == 1) {
          sql.append(" and cl.parentId = :locationId ");
          params.put("locationId", viewTreeCatLocationBO.getLocationId());
        } else {
          sql.append(" and (cl.parentId = :locationId or cl.locationId = :locationId )");
          params.put("locationId", viewTreeCatLocationBO.getLocationId());
        }
      }
      if (viewTreeCatLocationBO.getLocationName() != null && StringUtils.isNotEmpty(viewTreeCatLocationBO.getLocationName())) {
        sql.append(" and (cl.locationName like :locationName or cl.pathLocalName like :locationName )");
        params.put("locationName", CommonUtils.getLikeCondition(viewTreeCatLocationBO.getLocationName()));
      }
      if (viewTreeCatLocationBO.getLocationCode() != null && StringUtils.isNotEmpty(viewTreeCatLocationBO.getLocationCode())) {
        sql.append(" and cl.locationCode like :locationCode");
        params.put("locationCode", CommonUtils.getLikeCondition(viewTreeCatLocationBO.getLocationCode()));
      }
      if (viewTreeCatLocationBO.getPathLocalId() != null && StringUtils.isNotEmpty(viewTreeCatLocationBO.getPathLocalId())) {
        sql.append(" and cl.pathLocalId like :pathLocalId");
        params.put("pathLocalId", CommonUtils.getLikeCondition("/" + viewTreeCatLocationBO.getPathLocalId() + "/"));
      }
      if (viewTreeCatLocationBO.getPathLocalName() != null && StringUtils.isNotEmpty(viewTreeCatLocationBO.getPathLocalName())) {
        sql.append(" and cl.pathLocalName like :pathLocalName");
        params.put("pathLocalName", CommonUtils.getLikeCondition(viewTreeCatLocationBO.getPathLocalName()));
      }
//      sqlCount.append(sql.toString());
      sql.append(" GROUP BY cl.locationId ");
      if (viewTreeCatLocationBO.getSortField() != null && viewTreeCatLocationBO.getSortOrder() != null) {
        if (viewTreeCatLocationBO.getSortOrder() == 1) {
          sql.append(" order by cl." + viewTreeCatLocationBO.getSortField() + "  desc");
        } else {
          sql.append(" order by cl." + viewTreeCatLocationBO.getSortField() + "  asc");
        }
      } else {
        sql.append(" order by cl.locationCode ");
      }
//      Query queryCount = entityManager.createQuery(sqlCount.toString());
      Query query = entityManager.createQuery(sql.toString(), ViewTreeCatLocationBO.class);
      if (params.size() > 0) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
          query.setParameter(param.getKey(), param.getValue());
//          queryCount.setParameter(param.getKey(), param.getValue());
        }
      }

      List<ViewTreeCatLocationBO> count = query.getResultList();

      if (viewTreeCatLocationBO.getRows() != null) {
        // So ban ghi tren 1 trang
        query.setMaxResults(viewTreeCatLocationBO.getRows());
      }
      if (viewTreeCatLocationBO.getFirst() != null) {
        // bat dau tu ban ghi so
        query.setFirstResult(viewTreeCatLocationBO.getFirst());
      }

      List<ViewTreeCatLocationBO> resultList = query.getResultList();
      FormResult formResult = new FormResult();


      if (viewTreeCatLocationBO.getIsTree() == 1) {
        List<TreeNodeDTO> result = new ArrayList<>();
        if (resultList.size() > 0) {
          for (ViewTreeCatLocationBO item : resultList) {
            TreeNodeDTO treeNode = new TreeNodeDTO();
            if (viewTreeCatLocationBO.getDeptId() != null) {
              treeNode.setNodeId(item.getDeptId() == null ? null : Long.valueOf(item.getDeptId()));
            }
            treeNode.setParentId(item.getParentId() == null ? null : Long.valueOf(item.getParentId()));
            treeNode.setLabel(item.getLocationName() == null ? "" : item.getLocationName());
            treeNode.setLocationId(item.getLocationId() == null ? null : Long.valueOf(item.getLocationId()));
            treeNode.setLocationName(item.getLocationName() == null ? "" : item.getLocationName());
            treeNode.setPathLocalName(item.getPathLocalName() == null ? "" : item.getPathLocalName());
            treeNode.setPathLocalId(item.getPathLocalId() == null ? "" : item.getPathLocalId());
            result.add(treeNode);
          }
        }
        formResult.setListData(result);
      } else {
        formResult.setListData(resultList);
      }
      formResult.setTotalRecords((long) count.size());
      return formResult;
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return null;
  }

  @Override
  public ViewCatItemBO findCatItemByItemCodeAndCaregoryCode(String itemCode, String catCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatItemBO.class.getName());
    sql.append(" ci where ci.categoryCode = :categoryCode  and ci.itemCode =:itemCode ");
    Query query = entityManager.createQuery(sql.toString(), ViewCatItemBO.class);
    query.setParameter("categoryCode", catCode).setParameter("itemCode", itemCode);
    List<ViewCatItemBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public ViewCatItemBO findCatItemByItemNameAndCaregoryCode(String itemName, String catCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatItemBO.class.getName());
    sql.append(" ci where ci.categoryCode = :categoryCode  and ci.itemName =:itemName ");
    Query query = entityManager.createQuery(sql.toString(), ViewCatItemBO.class);
    query.setParameter("categoryCode", catCode).setParameter("itemName", itemName);
    List<ViewCatItemBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public ViewCatDepartmentBO findDepartmentByCode(String deptCode, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" where deptCode = :deptCode");
    sql.append(" and deptId in (" + CommonUtil.sqlAppendDepIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class).setParameter("userId", userId);
    query.setParameter("deptCode", deptCode);
    List<ViewCatDepartmentBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public ViewCatLocationBO findLocationByCode(String locationCode, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatLocationBO.class.getName());
    sql.append(" where locationCode = :locationCode");
    sql.append(" and locationId in (" + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), ViewCatLocationBO.class).setParameter("userId", userId);
    query.setParameter("locationCode", locationCode);
    List<ViewCatLocationBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public FormResult findLaneCode(InfraCableLanesBO infraCableLanesBOList, Long userId) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraCableLanesBO.class.getName());
    sql.append(" ic where 1=1 ");
    Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class);
    List<InfraCableLanesBO> resultList = query.getResultList();
    List<ViewCatLocationBO> listEntity = query.getResultList();
    FormResult result = new FormResult();
    result.setListData(listEntity);
    return result;
  }

  @Override
  public FormResult findPillarTypeCode(CatPillarTypeBO catPillarTypeBOList) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatPillarTypeBO.class.getName());
    sql.append(" p where 1 = 1");
    sql.append(" and rowStatus = 1");
    HashMap<String, Object> params = new HashMap<>();
    Query query = entityManager.createQuery(sql.toString(), CatPillarTypeBO.class);
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

  @Override
  public InfraCableLanesBO findLaneCodeByCode(String laneCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(InfraCableLanesBO.class.getName());
    sql.append(" p where 1 = 1 and p.rowStatus = 1 and p.laneCode = :laneCode");
    Query query = entityManager.createQuery(sql.toString(), InfraCableLanesBO.class).setParameter("laneCode", laneCode);
    List<InfraCableLanesBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {

      return null;
    }
    return resultList.get(0);
  }


  @Override
  public boolean isExitByCode(String pillarCode) {
    try {
      PillarsBO pillarsBO = new PillarsBO();
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
      if (resultList.size() > 0) {
        return true;
      }
      return false;
    } catch (Exception e) {
      // TODO: handle exception
      log.error("Exception", e);
    }
    return false;
  }

  @Override
  public CatPillarTypeBO getPillarTypeCodeList(String pillarTypeCode, String pillar_type_code) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatPillarTypeBO.class.getName());
    sql.append(" cp where 1=1 ");
    sql.append("and cp.pillarTypeCode = :pillarTypeCodeList");
    HashMap<String, Object> params = new HashMap<>();
    Query query = entityManager.createQuery(sql.toString(), CatPillarTypeBO.class);
    query.setParameter("pillarTypeCodeList", pillarTypeCode);
    List<CatPillarTypeBO> resultList = query.getResultList();
    if (CommonUtils.isNullOrEmpty(resultList)) {
      return null;
    }
    return resultList.get(0);
  }

  @Override
  public List<Long> getDeptIdByUser(Long userId) {
    StringBuilder sql = new StringBuilder();
    sql.append("select su.deptId from ");
    sql.append(SysUserDepartmentBO.class.getName());
    sql.append(" su , ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd  ");
    sql.append(" where  cd.path like concat('%/',su.deptId,'/%') and su.userId = :id ");
    Query query = entityManager.createQuery(sql.toString()).setParameter("id", userId);
    List<Long> result = query.getResultList();
    return result;
  }

  @Override
  public List<Long> getLocationIdsByDeptIds(List<Long> deptIds) {
    StringBuilder sql = new StringBuilder();
    sql.append("select dl.locationId from ");
    sql.append(CntDepartmentLocationBO.class.getName());
    sql.append(" dl where 1=1 and dl.deptId in (:deptIds) ");
    Query query = entityManager.createQuery(sql.toString()).setParameter("deptIds", deptIds);
    List<Long> result = query.getResultList();
    return result;
  }

  @Override
  public InfraPointsBO findInfraPointsById(Long id) {
    InfraPointsBO infraPointsBO = entityManager.find(InfraPointsBO.class, id);
    return infraPointsBO;
  }

  @Override
  public CatPoolTypeBO getPoolTypeByCode(String poolTypeCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(CatPoolTypeBO.class.getName());
    sql.append(" where 1=1 and rowStatus = 1 and poolTypeCode = :poolTypeCode  ");
    Query query = entityManager.createQuery(sql.toString(), CatPoolTypeBO.class).setParameter("poolTypeCode", poolTypeCode);
    List<CatPoolTypeBO> result = query.getResultList();
    if (CommonUtils.isNullOrEmpty(result)) {
      return null;
    } else {
      return result.get(0);
    }
  }

  @Override
  public boolean exitStationCode(String stationCode, Long tenantId) {
    StringBuilder sql = new StringBuilder("");
    sql.append("  from ");
    sql.append(ViewCatTenantsBO.class.getName());
    sql.append("  a where  a.stationCode = :stationCode  and a.tenantId =:tenantId");

    Query query = entityManager.createQuery(sql.toString(), ViewCatTenantsBO.class).setParameter("stationCode", stationCode).setParameter("tenantId", tenantId);
    List reList = query.getResultList();
    if (CommonUtils.isNullOrEmpty(reList)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public Long getTenantByUser(Long userId) {
    StringBuilder sql = new StringBuilder();
    sql.append("  select vcd.tenantId ");
    sql.append(" from ");
    sql.append(SysUserDepartmentBO.class.getName());
    sql.append(" sud ,  ");
    sql.append(CatDepartmentEntity.class.getName());
    sql.append(" vcd  where  sud.deptId = vcd.deptId and sud.userId = :userId    ");
    Query query = entityManager.createQuery(sql.toString()).setParameter("userId", userId);
    query.setMaxResults(1);
    Long result = (Long) query.getSingleResult();
    return result;
  }

  @Override
  public boolean checkExitsLocation(String locationCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatLocationBO.class.getName());
    sql.append(" where locationCode = :locationCode");
    Query query = entityManager.createQuery(sql.toString(), ViewCatLocationBO.class);
    query.setParameter("locationCode", locationCode);
    List<ViewCatLocationBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return false;
    }
    return true;
  }

  @Override
  public boolean checkExitsDept(String deptCode) {
    StringBuilder sql = new StringBuilder("from ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" where deptCode = :deptCode");
    Query query = entityManager.createQuery(sql.toString(), ViewCatDepartmentBO.class);
    query.setParameter("deptCode", deptCode);
    List<ViewCatLocationBO> resultList = query.getResultList();
    if (resultList == null || resultList.size() == 0) {
      return false;
    }
    return true;
  }
}
