package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.commom.JSONResponse;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.dao.SysGridViewDao;
import com.viettel.nims.transmission.model.SysGridViewColumnBO;
import com.viettel.nims.transmission.model.SysGridViewUserBO;
import com.viettel.nims.transmission.model.view.ViewSysGridBO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(transactionManager = "globalTransactionManager")
public class SysGridViewServiceImpl implements SysGridViewService {

  @Autowired
  SysGridViewDao sysGridViewDao;

  JSONObject jsonObject = new JSONObject();

  @Override
  public ResponseEntity<?> getGridView(ViewSysGridBO viewSysGridBO) {
    List<ViewSysGridBO> data = sysGridViewDao.getGridView(viewSysGridBO);
    jsonObject = JSONResponse.buildResultJson(0, "success", data);
    return new ResponseEntity<>(jsonObject, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> setGridView(List<ViewSysGridBO> viewSysGridBO, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    Long gridUserId = sysGridViewDao.getGridIdByName(viewSysGridBO.get(0).getGridViewName());
    List<SysGridViewColumnBO> columns = sysGridViewDao.getAllColumns(gridUserId);
//    list all colums by user include hide and show columns
    List<SysGridViewUserBO> userView = sysGridViewDao.getAllColumsByUser(userId, gridUserId);
//    check if new user then add all
    if (CommonUtils.isNullOrEmpty(userView)) {
      for (SysGridViewColumnBO bo : columns) {
        SysGridViewUserBO userBO = new SysGridViewUserBO();
        userBO.setColumnId(bo.getColumnId());
        userBO.setUserId(userId);
        userBO.setIsShow(1L);
        sysGridViewDao.saveSysGridUser(userBO);
      }
      userView = sysGridViewDao.getAllColumsByUser(userId, gridUserId);
    }

    List<SysGridViewColumnBO> hideColumns = new ArrayList<>();

    for (SysGridViewColumnBO columnBO : columns) {
      ViewSysGridBO viewUserBO = viewSysGridBO.stream().filter(x -> x.getColumnName().equals(columnBO.getColumnName())).findAny().orElse(null);
      if (viewUserBO != null) {
        SysGridViewUserBO userBO = userView.stream().filter(x -> x.getColumnId().longValue() == columnBO.getColumnId().longValue()).findAny().orElse(null);
//        if ̣(userBO!= null)
        userBO.setIsShow(1L);
//        sysGridViewDao.saveSysGridUser(userBO);
      } else {
        hideColumns.add(columnBO);
      }
    }
    for (SysGridViewColumnBO bo : hideColumns) {
      SysGridViewUserBO userBO = userView.stream().filter(x -> x.getColumnId().longValue() == bo.getColumnId().longValue()).findAny().orElse(null);
      userBO.setIsShow(0L);
//      sysGridViewDao.saveSysGridUser(userBO);
    }

    return ResponseBase.createResponse(null, "SUCCESS", HttpStatus.OK);
  }
}
