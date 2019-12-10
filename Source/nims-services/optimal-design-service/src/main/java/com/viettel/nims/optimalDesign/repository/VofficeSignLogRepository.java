/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.repository;

import com.viettel.nims.optimalDesign.entity.VofficeSignLog;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
@Repository
public interface VofficeSignLogRepository extends JpaRepository<VofficeSignLog,BigDecimal> {

}
