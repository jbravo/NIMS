/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.SuggestionNewSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Admin
 */
@Repository
public interface SuggestionNewSiteRepository extends JpaRepository<SuggestionNewSite,Long> {

  @Query("SELECT s FROM SuggestionNewSite s WHERE s.stationCode = ?1 and s.rowStatus = 1")
  Optional<SuggestionNewSite> findByStationCode(String stateCode);

  @Query("SELECT s FROM SuggestionNewSite s WHERE s.suggestNewSiteId = ?1 OR s.stationCode = ?2")
  Optional<SuggestionNewSite> findByIdOrStationCode(Long id, String stateCode);

  @Query("SELECT s FROM SuggestionNewSite s WHERE s.suggestNewSiteId <> ?1 AND s.stationCode = ?2 and s.rowStatus = 1")
  Optional<SuggestionNewSite> findByStationCodeIdNot(Long id, String stateCode);

}
