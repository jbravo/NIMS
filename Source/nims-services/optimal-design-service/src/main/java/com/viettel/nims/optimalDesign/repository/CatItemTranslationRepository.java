/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.CatItemTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
@Repository
public interface CatItemTranslationRepository extends JpaRepository<CatItemTranslation,BigDecimal> {
  @Query(value = "select * " +
      "from CAT_ITEM_TRANSLATION " +
      " where item_id = ?1 and language_code = ?2", nativeQuery = true)
  CatItemTranslation findItemNameByCatItemIdAndLanguageCode(Long itemId, String languageCode);
}
