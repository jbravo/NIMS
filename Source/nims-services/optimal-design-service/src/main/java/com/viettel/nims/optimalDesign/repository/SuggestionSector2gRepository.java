/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionCallOff2g;
import com.viettel.nims.optimalDesign.entity.SuggestionSector2g;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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
public interface SuggestionSector2gRepository extends JpaRepository<SuggestionSector2g,Long> {
  List<SuggestionSector2g> findAllBySuggestionCallOffId(Long suggestionCallOffId);
}
