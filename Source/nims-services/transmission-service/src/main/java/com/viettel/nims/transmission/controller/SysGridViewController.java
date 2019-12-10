package com.viettel.nims.transmission.controller;

import net.sf.json.JSONObject;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.commom.ResponseEntityBase;
import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.view.ViewSysGridBO;
import com.viettel.nims.transmission.service.SysGridViewService;
import com.viettel.nims.transmission.service.TransmissionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/sysGridView")
public class SysGridViewController extends BaseNimsController {

  JSONObject jsonObject = new JSONObject();

  @Autowired
  SysGridViewService sysGirdViewService;
  @Autowired
  TransmissionService transmissionService;

  @PostMapping("/getGridView")
  public ResponseEntity<?> getGridView(@RequestBody ViewSysGridBO viewSysGridBO) {
    return sysGirdViewService.getGridView(viewSysGridBO);
  }

  @PostMapping("/setGridView")
  public ResponseEntity<?> setGridView(@RequestBody List<ViewSysGridBO> viewSysGridBO, HttpServletRequest request) {
    return sysGirdViewService.setGridView(viewSysGridBO, request);
  }

  @GetMapping("/getUserInfor")
  public ResponseEntity<?> getDeptByUserId(HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    List<Long> listDept = transmissionService.getDeptIdByUser(userId);
    List<Long> listLocation = transmissionService.getLocationIdByDeptIds(listDept);
    JSONObject result = new JSONObject();
    result.put("deptIds", listDept);
    result.put("locationIds", listLocation);
    return ResponseBase.createResponse(result, "success", HttpStatus.OK);
  }
}
