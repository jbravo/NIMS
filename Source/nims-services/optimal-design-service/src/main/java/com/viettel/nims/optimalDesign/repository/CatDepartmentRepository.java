/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.repository.custom.CatDepartmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CatDepartmentRepository extends JpaRepository<CatDepartment, Long>, CatDepartmentRepositoryCustom {

  List<CatDepartment> findByParentId(Long parenId);

}
