/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTrans;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionCallOffTransRepository extends JpaRepository<SuggestionCallOffTrans,Long> {
  List<SuggestionCallOffTrans> findAllBySuggestId(Long suggestId);
}
