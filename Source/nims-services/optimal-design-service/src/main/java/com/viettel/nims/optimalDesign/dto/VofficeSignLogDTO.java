/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 *
 * @author Admin
 */
@Data
public class VofficeSignLogDTO implements Serializable {

  private Long id;
  private String userCreate;
  private Integer action;
  private String note;
  private VofficeSignDTO vofficeSignId;
}
