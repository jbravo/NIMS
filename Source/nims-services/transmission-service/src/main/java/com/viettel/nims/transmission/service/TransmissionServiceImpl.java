package com.viettel.nims.transmission.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.utils.Constains;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.dao.CatOdfTypeDao;
import com.viettel.nims.transmission.dao.TransmissionDao;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatItemBO;
import com.viettel.nims.transmission.model.view.ViewCatTenantsBO;
import com.viettel.nims.transmission.model.view.ViewTreeCatLocationBO;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class TransmissionServiceImpl implements TransmissionService {

  @Autowired
  TransmissionDao transmissionDao;

  @Autowired
  CatOdfTypeDao catOdfTypeDao;

  @Override
  // Tra cuu dia ban
  public ResponseEntity<?> findCatLocation(CatLocationBO catLocationBO, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.findCatLocation(catLocationBO, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  // Loc dia ban(autocomplete)
  public ResponseEntity<?> filterLocation(String deptName, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = new FormResult();
    if (StringUtils.isNotEmpty(deptName)) {
      result = transmissionDao.filterLocation(deptName.trim(), userId);
    }
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  public List<ViewCatItemBO> findCatItemByCategoryId(String catName) {
    return transmissionDao.findCatItemByCategoryId(catName);
  }

  @Override
  // Tra cuu don vi
  public ResponseEntity<?> findDepartment(CatDepartmentEntity entity, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.findDepartment(entity, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  // Loc don vi(autocomplete)
  public ResponseEntity<?> filterDepartment(String deptName, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.filterDepartment(deptName, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  // check trung ma nha tram
  public boolean checkDuplicateStationCode(String stationCode, Long id) {
    List<ViewCatTenantsBO> resultList = new ArrayList<>();
    Set<String> tenants = new HashSet<>();
    if (StringUtils.isNotEmpty(stationCode.trim())) {
      resultList = transmissionDao.findTenantByStationCode(stationCode.trim(), id);
    }
    if (resultList.size() > 0) {
      for (ViewCatTenantsBO bo : resultList) {
        tenants.add(bo.getTenantCode());
      }
      if (resultList.size() > tenants.size()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @author ThieuNV
   * @date 26/8/2019
   */
  @Override
  public List<CatPoolTypeBO> getPoolTypeList() {
    return transmissionDao.getPoolTypeList();
  }

  //DungPH
  @Override
  public ResponseEntity<?> findStaion(InfraStationsBO stationsBO, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.findStation(stationsBO, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }
  //DungPH end

  @Override
  public ViewTreeCatLocationBO findCatLocationById(Long id, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    return transmissionDao.findCatLocationById(id, userId);
  }

  @Override
  public Integer getNumberOfCable(InfraCablesBO infraCablesBO) {
    try {
      Integer number = transmissionDao.getNumberOfCable(infraCablesBO);
      return number;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public ResponseEntity<?> findCablesById(Long cableId) {
    return null;
  }

  @Override
  public List<CfgMapOwnerBO> getAllCfgOwner() {
    return transmissionDao.getAllCfgOwner();
  }

  @Override
  public List<CatOpticalCableTypeBO> getAllCableType() {
    try {
      List<CatOpticalCableTypeBO> resultList = transmissionDao.getAllCableType();
      return resultList;
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  @Override
  public ViewCatDepartmentBO findDepartmentById(Long id, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    return transmissionDao.findDepartmentById(id, userId);
  }

  @Override
  public ResponseEntity<?> treeNodeCatDepartment(Long deptId, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.treeNodeCatDepartment(deptId, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  public ResponseEntity<?> treeNodeCatLocation(ViewTreeCatLocationBO viewTreeCatLocationBO, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = transmissionDao.treeNodeCatLocation(viewTreeCatLocationBO, userId);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }


  /**
   * method get all cat odf type
   *
   * @return Integer
   * @author dungph
   * @date 5/9/2019
   */
  @Override
  public ResponseEntity<?> getAllCatOdfType() {
    FormResult result = new FormResult();
    List<CatOdfTypeBO> catOdfTypeBOList = catOdfTypeDao.getAllCatOdfType();
    result.setListData(catOdfTypeBOList);
    return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
  }

  @Override
  public List<Long> getDeptIdByUser(Long userId) {
    return transmissionDao.getDeptIdByUser(userId);
  }

  @Override
  public List<Long> getLocationIdByDeptIds(List<Long> deptIds) {
    return transmissionDao.getLocationIdsByDeptIds(deptIds);
  }

  @Override
  public boolean checkExitStationCode(String stationCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    Long tenantId = transmissionDao.getTenantByUser(userId);
    return transmissionDao.exitStationCode(stationCode, tenantId);
  }

  @Override
  public boolean checkExitsLocation(String locationCode) {
    return false;
  }

  @Override
  public boolean checkExitsDept(String deptCode) {
    return false;
  }
}
