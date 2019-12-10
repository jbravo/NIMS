package com.viettel.nims.optimalDesign.repository.custom;

import com.viettel.nims.optimalDesign.dto.CatDepartmentDTO;
import com.viettel.nims.optimalDesign.entity.CatDepartment;

import java.util.List;

public interface CatDepartmentRepositoryCustom {
  List<Long> getListIdDepartmentByParent(Long deptId);

  Long getCatDepartmentByUserSys(Long idUser);

  List<CatDepartmentDTO> findAllByParentId(Long deptId);

  String findDeptNameById(Long deptId);
}
