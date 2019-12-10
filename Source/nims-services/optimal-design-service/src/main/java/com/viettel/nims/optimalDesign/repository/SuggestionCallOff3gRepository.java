/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOff2g;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff3g;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionCallOff3gRepository extends JpaRepository<SuggestionCallOff3g,Long> {
  List<SuggestionCallOff3g> findAllBySuggestId(Long suggestId);
  public SuggestionCallOff3g findBySuggestId(Long suggestId);
}
