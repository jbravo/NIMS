package com.viettel.nims.transmission.commom;

import com.viettel.nims.transmission.model.CntDepartmentLocationBO;
import com.viettel.nims.transmission.model.SysUserDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

  public static Long NVL(Long value, Long defaultValue) {
    return value == null ? defaultValue : value;
  }

  public static Long NVL(Long value) {
    return NVL(value, 0L);
  }

  public static Long getUserId(HttpServletRequest request) {
    String id = request.getHeader("userId");
    try {
      return Long.parseLong(id);
    } catch (Exception e) {
      return null;
    }
  }

  public static String sqlAppendDepIds() {
    StringBuilder sql = new StringBuilder();
    sql.append("select cd.deptId from ");
    sql.append(SysUserDepartmentBO.class.getName());
    sql.append(" su , ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd  ");
    sql.append(" where  cd.path like concat('%/',su.deptId,'/%') and su.userId = :userId ");
    return sql.toString();
  }

  public static String sqlAppendLocationIds() {
    StringBuilder sql = new StringBuilder();
    sql.append("select dl.locationId from ");
    sql.append(SysUserDepartmentBO.class.getName());
    sql.append(" su , ");
    sql.append(ViewCatDepartmentBO.class.getName());
    sql.append(" cd , ");
    sql.append(CntDepartmentLocationBO.class.getName());
    sql.append(" dl ");
    sql.append(" where  cd.path like concat('%/',su.deptId,'/%') and cd.deptId = dl.deptId and su.userId = :userId ");
    return sql.toString();
  }

}
