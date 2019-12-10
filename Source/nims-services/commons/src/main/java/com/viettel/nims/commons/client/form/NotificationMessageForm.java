package com.viettel.nims.commons.client.form;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class NotificationMessageForm {

  private Long id;
  private Long importantLevel;
  private Date toDate;
  private String content;
  private Date fromDate;
  private Long type;
  private String title;
  private String importantStr;
  private String typeStr;
  private Long deptId;
  private String fromDateStr;
  private String toDateStr;
  private String objectCode;
  private String objectName;
  private Long objectId;
  private Long isUser;
  private List<Long> majorId;
  private String lstUnitId;
  private List<String> lstUnitStr;
  private List<String> lstMajorStr;
  private String majorName;
  private Long isRead;
  private Date createDate;
  private Date updateTime;
  private String createDateStr;
  private String updateTimeStr;
  private String userCreate;
  private String fullname;
  private Long createBy;
  private String articleId;
  private String imagePath;
  private String imagePathSquare;
  private String author;
  private String url;
}
