package com.viettel.nims.transmission.dao;

import java.util.List;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
public interface TransmissionDao {

  // Tra cuu dia ban
  public FormResult findCatLocation(CatLocationBO catLocationBO, Long userId);

  // Loc dia ban (autocomplete)
  public FormResult filterLocation(String locationName, Long userId);

  CatItemBO findCatItemById(long id);

  List<ViewCatItemBO> findCatItemByCategoryId(String catName);

  // Tra cuu don vi
  public FormResult findDepartment(CatDepartmentEntity entity, Long userId);

  // Loc don vi(autocomplete)
  public FormResult filterDepartment(String deptName, Long userId);


  public List<ViewCatTenantsBO> findTenantByStationCode(String stationCode, Long id);

  public List<CatPoolTypeBO> getPoolTypeList();

  public CatPoolTypeBO getPoolTypeByCode(String poolTypeCode);

  //Tra cuu ten tram (them moi Odf) DungPH
  FormResult findStation(InfraStationsBO stationsBO, Long userId);

  //DungPH end
//
  // lay config map
  public List<CfgMapOwnerBO> getAllCfgOwner();

  // lay danh sach loai cap
  public List<CatOpticalCableTypeBO> getAllCableType();

  // Tra cuu dia ban
  public ViewTreeCatLocationBO findCatLocationById(Long id, Long userId);

  // Tra cuu don vi
  public ViewCatDepartmentBO findDepartmentById(Long id, Long userId);

  public ViewCatDepartmentBO findDepartmentByCode(String deptCode, Long userId);

  public ViewCatLocationBO findLocationByCode(String locationCode, Long userId);

  Integer getNumberOfCable(InfraCablesBO infraCablesBO);

  FormResult treeNodeCatDepartment(Long deptId, Long userId);

  // tim item theo category code va Id
  ViewCatItemBO findCatItemByCategoryCodeAndId(Long itemId, String catName);

  FormResult treeNodeCatLocation(ViewTreeCatLocationBO viewTreeCatLocationBO, Long userId);

  // tim item theo category code va item code
  ViewCatItemBO findCatItemByItemCodeAndCaregoryCode(String itemCode, String catCode);

  // tim item theo category code va item name
  ViewCatItemBO findCatItemByItemNameAndCaregoryCode(String itemName, String catCode);

  // find infraPoint by id
  InfraPointsBO findInfraPointsById(Long id);

  //----------KienNT---------------
  // tra cua ma tuyen
  public FormResult findLaneCode(InfraCableLanesBO infaCableLanesBOList, Long userId);

  public FormResult findPillarTypeCode(CatPillarTypeBO catPillarTypeBOList);

  public InfraCableLanesBO findLaneCodeByCode(String laneCode);

  boolean isExitByCode(String pillarCode);

  CatPillarTypeBO getPillarTypeCodeList(String pillarTypeCode, String pillar_type_code);


  //--------------Kien Nt----------------

  //  get deptIds by userId
  List<Long> getDeptIdByUser(Long userId);

  //  get locationIds by deptIds
  List<Long> getLocationIdsByDeptIds(List<Long> deptIds);

  boolean exitStationCode(String stationCode, Long userId);

  Long getTenantByUser(Long userId);

  //check ton tai dia ban trong db k theo phan quyen
  public boolean checkExitsLocation(String locationCode);

  //  check ton tai don vi trong db k theo phan quyen
  public boolean checkExitsDept(String deptCode);

}
