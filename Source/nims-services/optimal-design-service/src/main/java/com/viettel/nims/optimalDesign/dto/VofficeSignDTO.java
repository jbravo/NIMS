/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@Data
public class VofficeSignDTO implements Serializable {

  private Long vofficeSignId;
  private String transCode;
  private Integer signStatus;
  private String signSource;
  private String docTitle;
  private String publishOganizationCode;
  private String lastSignEmail;
  private String signComment;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;
  private List<VofficeSignLogDTO> vofficeSignLogList;

}

