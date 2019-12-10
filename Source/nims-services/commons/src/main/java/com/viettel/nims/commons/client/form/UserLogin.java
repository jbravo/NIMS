package com.viettel.nims.commons.client.form;

import com.viettel.nims.commons.util.TaskUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class UserLogin {

  private String userName;
  private String ipAdress;
  private String deptCode;

//  public UserLogin(SysUsersBO usersBO, HttpServletRequest request) {
//    this.userName = usersBO.getUsername();
//    this.ipAdress = TaskUtil.getIpLocal(request);
//    this.deptCode = String.valueOf(usersBO.getDepartmentId());
//  }
//
//  public UserLogin(SysUsersBO usersBO) {
//    this.userName = usersBO.getUsername();
//    this.ipAdress = "0.0.0.0";
//    this.deptCode = String.valueOf(usersBO.getDepartmentId());
//  }
}
