package com.viettel.nims.transmission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "SYS_USER")
public class SysUserBO implements Serializable {

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long userId;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "FULLNAME", length = 100)
    private String fullname;

    @Column(name = "DEPT_ID", precision = 22, scale = 0)
    private Long deptId;

    @Column(name = "EMAIL", length = 30)
    private String email;

    @Column(name = "PHONE", length = 33)
    private String phone;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "USER_ROLE")
    private Long userRole;

}
