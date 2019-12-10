/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionSector2g;
import com.viettel.nims.optimalDesign.entity.SuggestionSector3g;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionSector3gRepository extends JpaRepository<SuggestionSector3g,Long> {
  List<SuggestionSector3g> findAllBySuggestionCallOffId(Long suggestionCallOffId);
}
