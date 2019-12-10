package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SysEventLogDTO;
import com.viettel.nims.optimalDesign.entity.SysEventLog;
import com.viettel.nims.optimalDesign.entity.SysEventLogDetail;
import com.viettel.nims.optimalDesign.entity.SysUsers;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SysEventLogDetailRepository;
import com.viettel.nims.optimalDesign.repository.SysEventLogRepository;
import com.viettel.nims.optimalDesign.repository.SysUsersRepository;
import com.viettel.nims.optimalDesign.service.SystemLogService;
import com.viettel.nims.optimalDesign.utils.DateUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class SystemLogServiceImpl implements SystemLogService {

  private final static Logger logger = LoggerFactory.getLogger(SystemLogService.class);

  private transient final BaseMapper<SysEventLog, SysEventLogDTO> mapper = new BaseMapper<>(SysEventLog.class, SysEventLogDTO.class);

  @Autowired
  private SysEventLogRepository sysEventLogRepository;

  @Autowired
  private SysEventLogDetailRepository sysEventLogDetailRepository;

  @Autowired
  private SysUsersRepository sysUsersRepository;

  @Override
  public void writeLog(Object oldObject, Object newObject, SysEventLogDTO logDTO) {
    Class modelClass = getModelClass(newObject);
    Long idValue = getIdValue(newObject, modelClass.getDeclaredFields());
    SysEventLog sysEventLog = mapper.toPersistenceBean(logDTO);
    sysEventLog.setSysEventLogDetailList(createSysLogDetail(oldObject,newObject,idValue));
    sysEventLog = sysEventLogRepository.save(sysEventLog);
    for(SysEventLogDetail detail : sysEventLog.getSysEventLogDetailList()){
      detail.setIdSysEventLog(sysEventLog.getSysEventLogId());
      sysEventLogDetailRepository.save(detail);
    }
  }

  @Override
  public SysEventLogDTO createLogDTO(String userName,String note, String ipAddress, String objCode, int objType, Long objId, short action){
    Optional<SysUsers> sysUsers = sysUsersRepository.findByUserName(userName);
    if(!sysUsers.isPresent())
      return null;
    SysEventLogDTO sysEventLogDTO = new SysEventLogDTO();
    sysEventLogDTO.setAction(action);
    sysEventLogDTO.setNote(note);
    sysEventLogDTO.setObjectCode(objCode);
    sysEventLogDTO.setObjectId(objId);
    sysEventLogDTO.setObjectType(objType);
    sysEventLogDTO.setUserName(userName);
//    sysEventLogDTO.setUserDeptId(sysUsers.get().getDeptId().getDeptId());
//    sysEventLogDTO.setUserName(sysUsers.get().getDeptId().getDeptName());
    return sysEventLogDTO;
  }

  private Long getIdValue(Object oldObject, Field[] fields) {
    for (Field field : fields) {
      Annotation idFieldAnnotation = field.getAnnotation(Id.class);
      if (idFieldAnnotation != null) {
        try {
          return Long.valueOf(FieldUtils.readDeclaredField(oldObject, field.getName(), true).toString());
        } catch (Exception e) {
          logger.error("model class does not have ID value", e);
          return null;
        }
      }
    }
    return null;
  }

  private List<SysEventLogDetail> createSysLogDetail(Object oldObject, Object newObject, Long idValue) {

    if (oldObject == null) {
      //xu ly them moi toan bo
      return createSysEventLogForBrandNewObject(newObject, idValue);

    }
    Class modelClass = getModelClass(oldObject);
    Field[] fields = modelClass.getDeclaredFields();
    //get ID fileds
    List<SysEventLogDetail> sysEventLogDetails = new ArrayList<>();
    for (Field field : fields) {
      Annotation annotation = field.getAnnotation(Column.class);
      if (annotation != null) {
        try {
          Object oldValueObj = null;
          Object newValueObj = null;
          try {
            oldValueObj = FieldUtils.readDeclaredField(oldObject, field.getName(), true);
            newValueObj = FieldUtils.readDeclaredField(newObject, field.getName(), true);
          } catch (Exception e) {
            logger.error(e.getMessage(),e);
            oldValueObj = FieldUtils.readField(oldObject, field.getName(), true);
            newValueObj = FieldUtils.readField(newObject, field.getName(), true);
          }
          if (newValueObj != null) {
            String oldValue = oldValueObj == null ? "" : (oldValueObj instanceof Date)
                ? DateUtils.date2ddMMyyyyHHMMss((Date) oldValueObj)
                : String.valueOf(oldValueObj == null ? "" : oldValueObj);
            String newValue = (newValueObj instanceof Date)
                ? DateUtils.date2ddMMyyyyHHMMss((Date) newValueObj)
                : String.valueOf(newValueObj == null ? "" : newValueObj);
            if (!oldValue.equals(newValue)) {
              SysEventLogDetail sysEventLogDetail = new SysEventLogDetail();
              sysEventLogDetail.setFieldName(field.getName());
              sysEventLogDetail.setNewValue(newValue);
              sysEventLogDetail.setOldValue(oldValue);
              sysEventLogDetails.add(sysEventLogDetail);
            }
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
    return sysEventLogDetails;
  }

  public List<SysEventLogDetail> createSysEventLogForBrandNewObject(Object newObject, Long idValue) {
    if (newObject == null) {
      return new ArrayList<>();
    }
    Class modelClass = getModelClass(newObject);
    Field[] fields = modelClass.getDeclaredFields();
    //get ID fileds
    List<SysEventLogDetail> sysEventLogDetails = new ArrayList<>();
    for (Field field : fields) {
      Annotation f = field.getAnnotation(Column.class);
      if (f != null) {
        try {
          String newValue = String.valueOf(FieldUtils.readDeclaredField(newObject, field.getName(), true));
          if (!"null".equals(newValue)) {
            SysEventLogDetail sysEventLogDetail = new SysEventLogDetail();
            sysEventLogDetail.setFieldName(field.getName());
            sysEventLogDetail.setNewValue(newValue);
            sysEventLogDetails.add(sysEventLogDetail);
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
    return sysEventLogDetails;
  }

  private Class getModelClass(Object newObject) {
    return newObject.getClass();
  }
}
