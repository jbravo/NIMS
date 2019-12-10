package com.viettel.nims.transmission.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.viettel.nims.transmission.model.CatDepartmentEntity;
import com.viettel.nims.transmission.model.CatLocationBO;
import com.viettel.nims.transmission.model.CatOpticalCableTypeBO;
import com.viettel.nims.transmission.model.CatPoolTypeBO;
import com.viettel.nims.transmission.model.CfgMapOwnerBO;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatItemBO;
import com.viettel.nims.transmission.model.view.ViewTreeCatLocationBO;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
public interface TransmissionService {

  // Tra cuu dia ban (theo phan quyen user)
  public ResponseEntity<?> findCatLocation(CatLocationBO catLocationBO, HttpServletRequest request);

  //check ton tai dia ban trong db k theo phan quyen
  public boolean checkExitsLocation(String locationCode);

  //  check ton tai don vi trong db k theo phan quyen
  public boolean checkExitsDept(String deptCode);

  // Loc dia ban(autocomplete)
  public ResponseEntity<?> filterLocation(String deptName, HttpServletRequest request);

  List<ViewCatItemBO> findCatItemByCategoryId(String catName);

  // Tra cuu don vi (theo phan quyen user)
  public ResponseEntity<?> findDepartment(CatDepartmentEntity entity, HttpServletRequest request);

  // Loc don vi(autocomplete)
  public ResponseEntity<?> filterDepartment(String deptName, HttpServletRequest request);

  // check trung ma nha tram , 11/10/19 tulv: function sai k dung duoc.
  public boolean checkDuplicateStationCode(String stationCode, Long id);

  // lay thong tin doan cap
  public ResponseEntity<?> findCablesById(Long cableId);

  public List<CatPoolTypeBO> getPoolTypeList();

  //lay config map
  List<CfgMapOwnerBO> getAllCfgOwner();

  // lay danh sach loai cap
  List<CatOpticalCableTypeBO> getAllCableType();

  // DungPH Tra cuu nha tram
  public ResponseEntity<?> findStaion(InfraStationsBO stationsBO, HttpServletRequest request);

  //DungPH end
  // Tra cuu dia ban
  public ViewTreeCatLocationBO findCatLocationById(Long id, HttpServletRequest request);

  // Tra cuu don vi
  public ViewCatDepartmentBO findDepartmentById(Long id, HttpServletRequest request);

  // lay so doan cap giua 2 diem
  Integer getNumberOfCable(InfraCablesBO infraCablesBO);

  ResponseEntity<?> treeNodeCatDepartment(Long deptId, HttpServletRequest request);

  ResponseEntity<?> treeNodeCatLocation(ViewTreeCatLocationBO viewTreeCatLocationBO, HttpServletRequest request);

  // get all Cat ODF Type (DungPH)
  public ResponseEntity<?> getAllCatOdfType();

  //  get list deptIds by userId
  public List<Long> getDeptIdByUser(Long userId);

  public List<Long> getLocationIdByDeptIds(List<Long> deptIds);

  public boolean checkExitStationCode(String stationCode, HttpServletRequest request);
}
