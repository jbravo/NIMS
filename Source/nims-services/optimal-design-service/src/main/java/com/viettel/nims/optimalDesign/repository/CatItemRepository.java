/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.dto.CatItemDTO;
import com.viettel.nims.optimalDesign.entity.CatCategory;
import com.viettel.nims.optimalDesign.entity.CatItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CatItemRepository  extends JpaRepository<CatItem,BigDecimal> {
  List<CatItem> findAllByCatCategoryId(Long catCategoryId);
}
