package com.viettel.nims.transmission.model.view;
import com.viettel.nims.transmission.model.SysUserDepartmentBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by VAN-BA on 26/09/2019.
 */
@Data
@Entity
@Table(name = "view_hang_lanecode_has_sleeve")
public class ViewHangLaneCodeHasSleeveBO implements Serializable {

  @Id
  @Column(name = "HOLDER_ID")
  private Long holderId;

  @Id
  @Column(name = "LANE_CODE")
  private String laneCode;

  @Basic
  @Column(name = "PATH_NAME")
  private String pathName;

  @Basic
  @Column(name = "PATH_LOCAL_NAME")
  private String pathLocalName;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

}
