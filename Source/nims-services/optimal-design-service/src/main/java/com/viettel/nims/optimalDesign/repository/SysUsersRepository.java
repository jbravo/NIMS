/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SysUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
@Repository
public interface SysUsersRepository extends JpaRepository<SysUsers,Long> {
      Optional<SysUsers> findByUserName(String userName);

  @Query(value = "select d.deptId from SysUsers s inner join s.deptId d where upper(s.userName) = upper(:username)")
  public BigInteger getIdDepartmentByUsername(@Param("username") String username);

  @Query(value = "select upper(s.userName) from SysUsers s where s.deptId.deptId in (:listDept) and upper(s.userName) like upper(concat('%',:input,'%')) ")
  public List<String> getAllUserLikeInput(@Param("input") String input,@Param("listDept") List<Long> listDept);
}
