/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOff4g;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionCallOff4gRepository extends JpaRepository<SuggestionCallOff4g,Long> {

  public List<SuggestionCallOff4g> findAllBySuggestId(Long suggestId);

  public Optional<SuggestionCallOff4g> findBySuggestId(Long suggestId);
}
