package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
@Data
public class SuggestionSearchDTO implements Serializable {

    private Long suggestId;
    private Integer suggestType;
    private String suggestCode;
    private Integer deptId;
    private CatDepartmentDTO catDepartmentDTO;
    private String deptName;
    private Integer suggestStatus;
    @NotBlank(message = "access.deny")
    private String userName;
    private Date createTime;
    @NotNull(message = "beforeDate.empty")
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",message = "before.date.valid")
    private String beforeDate;
    @NotNull(message = "afterDate.empty")
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",message = "after.date.valid")
    private String afterDate;
    private Date updateTime;
    private Integer rowStatus;
    private String type;
    private String userSearch;

}

