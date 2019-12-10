package com.viettel.nims.transmission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.nims.commons.util.Response;
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
import com.viettel.nims.transmission.service.TransmissionService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VTN-PTPM-NV64 on 8/2/2019.
 */
@RestController
@RequestMapping(value = "/transmission")
public class TransmissionController {

  @Autowired
  TransmissionService transmissionService;


  // Tra cuu dia ban
  @PostMapping("/location")
  public ResponseEntity<?> findCatLocation(@RequestBody CatLocationBO catLocationBO, HttpServletRequest request) {
    return transmissionService.findCatLocation(catLocationBO,request);
  }

  // Loc dia ban(autocomplete)
  @GetMapping("/location/filter/{locationName}")
  public ResponseEntity<?> findLocation(@PathVariable("locationName") String locationName , HttpServletRequest request) {
    return transmissionService.filterLocation(locationName,request);
  }

  // tra cuu don vi
  @PostMapping("/department")
  public ResponseEntity<?> findDepartment(@RequestBody CatDepartmentEntity entity, HttpServletRequest request) {
    return transmissionService.findDepartment(entity,request);
  }

  // Loc don vi(autocomplete)
  @GetMapping(path = {"/department/filter", "/department/filter/{deptName}"})
  public ResponseEntity<?> findDepartment(@PathVariable(name = "deptName", required = false) String deptName, HttpServletRequest request) {
    final String strDeptName = deptName == null ? null : deptName;
    return transmissionService.filterDepartment(strDeptName,request);
  }

  @GetMapping("/item/find/{catName}")
  public @ResponseBody
  List<ViewCatItemBO> findItem(@PathVariable("catName") String catName) {
    return transmissionService.findCatItemByCategoryId(catName);
  }

  /**
   * @author ThieuNV
   * @date 26/8/2019
   */
  @GetMapping("/poolType/list")
  public ResponseEntity<?> getPoolTypeList() {
    Response<List<CatPoolTypeBO>> response = new Response<>();
    response.setContent(transmissionService.getPoolTypeList());
    response.setStatus(HttpStatus.OK.toString());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * @author DungPH
   * @Param infraStationsBO
   *
   */
  @PostMapping("/stations")
  public ResponseEntity<?> findStation(@RequestBody InfraStationsBO infraStationsBO,HttpServletRequest request) {
    return transmissionService.findStaion(infraStationsBO,request);
  }


  @PostMapping("/getAllOwner")
  public @ResponseBody
  List<CfgMapOwnerBO> getAllOwner() {
    return transmissionService.getAllCfgOwner();
  }

  @GetMapping("/findCatLocationById/{id}")
  public @ResponseBody
  ViewTreeCatLocationBO findCatLocationById(@PathVariable("id") Long id,HttpServletRequest request) {
    return transmissionService.findCatLocationById(id,request);
  }

  @GetMapping("/findDepartmentById/{id}")
  public @ResponseBody
  ViewCatDepartmentBO findDepartmentById(@PathVariable("id") Long id,HttpServletRequest request) {
    return transmissionService.findDepartmentById(id,request);
  }

  @GetMapping("/getAllCableType")
  public @ResponseBody
  List<CatOpticalCableTypeBO> getAllCableType() {
    return transmissionService.getAllCableType();
  }

  @PostMapping("/cables/index")
  public @ResponseBody
  Integer getNumberOfCable(@RequestBody InfraCablesBO infraCablesBO) {
    return transmissionService.getNumberOfCable(infraCablesBO);
  }

  /**
   * @param deptId
   * @return list tree node cat department
   * @author toannd iist
   */
  @GetMapping(path = {"/treeNodeCatDepartment", "/treeNodeCatDepartment/{deptId}"})
  public ResponseEntity<?> treeNodeCatDepartment(
      @PathVariable(name = "deptId", required = false) Long deptId,HttpServletRequest request) {
    final Long id = deptId == null ? null : deptId;
    return transmissionService.treeNodeCatDepartment(id,request);
  }

  /**
   * @return list tree node cat location
   * @author toannd iist
   */
  @PostMapping(path = "/treeNodeCatLocation")
  public ResponseEntity<?> treeNodeCatLocation(@RequestBody ViewTreeCatLocationBO viewTreeCatLocationBO , HttpServletRequest request) {
    return transmissionService.treeNodeCatLocation(viewTreeCatLocationBO,request);
  }

  /**
   * get all Cat Odf Type
   * @return getOdfIndexByOdfId
   * @author DungPH
   */
  @GetMapping("/getCatOdfTypes")
  public ResponseEntity<?> getAllCatOdfType() {
    return transmissionService.getAllCatOdfType();
  }
}
