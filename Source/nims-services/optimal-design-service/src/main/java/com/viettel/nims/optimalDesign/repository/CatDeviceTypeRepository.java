/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.CatDeviceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CatDeviceTypeRepository extends JpaRepository<CatDeviceType,BigDecimal> {
  List<CatDeviceType> findAllByRowStatus(Integer rowStatus);
}
