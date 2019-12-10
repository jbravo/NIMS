/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionCallOffRepository extends JpaRepository<SuggestionCallOff,Long> {
  @Query("SELECT sc FROM SuggestionCallOff sc WHERE sc.suggestId = ?1")
  SuggestionCallOff findBySuggestId(Long suggestId);

  @Query("SELECT sc FROM SuggestionCallOff sc WHERE sc.suggestId = ?1")
  public List<SuggestionCallOff> findListCallOffBySuggestId(Long suggestId);
}
