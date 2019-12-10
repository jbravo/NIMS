/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOff2g;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionCallOff2gRepository extends JpaRepository<SuggestionCallOff2g,Long> {
  List<SuggestionCallOff2g> findAllBySuggestId(Long suggestId);

  @Query(value = "select s from SuggestionCallOff2g  s where s.suggestId = :id")
  public SuggestionCallOff2g findBySuggestId(@Param("id") Long suggestId);
}
