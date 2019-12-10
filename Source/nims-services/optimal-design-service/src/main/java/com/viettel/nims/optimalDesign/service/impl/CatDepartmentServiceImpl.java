package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.CatDepartmentDTO;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.entity.SysUsers;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.CatDepartmentRepository;
import com.viettel.nims.optimalDesign.repository.SysUsersRepository;
import com.viettel.nims.optimalDesign.service.CatDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatDepartmentServiceImpl implements CatDepartmentService {

  BaseMapper<CatDepartment, CatDepartmentDTO> mapper = new BaseMapper<>(CatDepartment.class, CatDepartmentDTO.class);
  @Autowired
  private CatDepartmentRepository catDepartmentRepository;
  @Autowired
  private SysUsersRepository sysUsersRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public CatDepartmentDTO getTreeCatDepartmentByUserName(String userName) {
    CatDepartmentDTO catDepartmentDTO = new CatDepartmentDTO();
    Optional<SysUsers> sysUsers = sysUsersRepository.findByUserName(userName.trim());
    if (sysUsers.isPresent()) {
      Long catId = catDepartmentRepository.getCatDepartmentByUserSys(sysUsers.get().getUserId());
      Optional<CatDepartment> optionalCatDepartment = catDepartmentRepository.findById(catId);
      if (optionalCatDepartment.isPresent()) {
        catDepartmentDTO = mapper.toDtoBean(optionalCatDepartment.get());
        return catDepartmentDTO;
      }
    }
    return null;
  }

  @Override
  public List<CatDepartmentDTO> getTreeCatDepartmentByDeptId(Long deptId) {
    List<CatDepartmentDTO> departmentDTOList = new ArrayList<>();
    return catDepartmentRepository.findAllByParentId(deptId);

  }

  @Override
  public List<Long> getListDepartment(String userName) {
    BigInteger id = sysUsersRepository.getIdDepartmentByUsername(userName);
    if (id == null)
      return new ArrayList<>();
    return catDepartmentRepository.getListIdDepartmentByParent(id.longValue());
  }

  @Override
  public List<Long> getListDepartment(Long deptId, String userName) {
    List<Long> ids = getListDepartment(userName);
    if (!ids.contains(deptId))
      return new ArrayList<>();
    return catDepartmentRepository.getListIdDepartmentByParent(deptId);
  }


}
