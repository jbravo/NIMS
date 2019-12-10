package com.viettel.nims.commons.client.form;

import lombok.Data;

@Data
public class ProfileUserForm {

  private Long userId;
  private String username;
  private String fullname;
  private String phone;
  private String identityCard;
  private String deployCode;
  private String email;
  private String deptName;
  private int roleId;
  private String typeUser;
  private String major;
  private String company;
}
