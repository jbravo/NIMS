package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.CatDepartmentDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;

import java.util.List;

public interface CatDepartmentService {
  CatDepartmentDTO getTreeCatDepartmentByUserName(String userName);

  List<CatDepartmentDTO> getTreeCatDepartmentByDeptId(Long deptId);

  List<Long> getListDepartment(String userName);

  List<Long> getListDepartment(Long deptId, String userName);


}
