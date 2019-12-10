package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SysEventLogDTO;

public interface SystemLogService {
  public void writeLog(Object oldObject, Object newObject, SysEventLogDTO logDTO);
  public SysEventLogDTO createLogDTO(String userName,String note, String ipAddress, String objCode, int objType, Long objId, short action);
}
