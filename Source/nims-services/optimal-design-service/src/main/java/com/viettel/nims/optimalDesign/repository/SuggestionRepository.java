/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.Suggestion;
import com.viettel.nims.optimalDesign.repository.custom.SuggestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion,Long>, SuggestionRepositoryCustom {
  @Query(value = "SELECT COUNT(*) FROM SUGGESTION INNER JOIN SUGGESTION_NEW_SITE ON SUGGESTION.SUGGEST_ID = SUGGESTION_NEW_SITE.SUGGEST_ID WHERE STATION_CODE = ?1 AND DATE(SUGGESTION.CREATE_TIME) = DATE(?2)", nativeQuery = true)
  Long  findNextIndexByStationCodeAndInCreateDate(String stationCode, Date createDate);

  List<Suggestion> findBySuggestCode(String suggestCode);

  @Query(value = "SELECT nextval(SUGGESTION_SEQ)", nativeQuery = true)
  Long getNextValSeq();
}
