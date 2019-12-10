/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;


import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_RADIO")
public class SuggestionRadio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUGGEST_ID")
    private Long suggestId;
    @Column(name = "STATION_TECH_TYPE")
    private Integer stationTechType;
    @Column(name = "STATION_SOLUTION_TYPE")
    private Long stationSolutionType;
    @Column(name = "RADIO_LOCATION_TYPE")
    private Integer radioLocationType;
    @Column(name = "RADIO_PILLAR_TYPE")
    private Integer radioPillarType;
    @Column(name = "RADIO_PILLAR_HEIGHT")
    private Long radioPillarHeight;
    @Column(name = "HAS_CONCAVE_POINT")
    private Integer hasConcavePoint;
    @Column(name = "HEIGHT_WITH_CONCAVE_POINT")
    private Long heightWithConcavePoint;
    @Column(name = "CONCAVE_POINT_POPULATION_2G")
    private Integer concavePointPopulation2g;
    @Column(name = "CONCAVE_POINT_POPULATION_3G_VOICE")
    private Integer concavePointPopulation3gVoice;
    @Column(name = "CONCAVE_POINT_POPULATION_3G_DATA")
    private Integer concavePointPopulation3gData;
    @Column(name = "CONCAVE_POINT_POPULATION_4G")
    private Integer concavePointPopulation4g;
    @Column(name = "SPECIAL_AREA")
    private Integer specialArea;
    @Column(name = "SPECIAL_AREA_TYPE")
    private Long specialAreaType;
    @Column(name = "SPECIAL_AREA_POPULATION_2G")
    private Integer specialAreaPopulation2g;
    @Column(name = "SPECIAL_AREA_POPULATION_3G_VOICE")
    private Integer specialAreaPopulation3gVoice;
    @Column(name = "SPECIAL_AREA_POPULATION_3G_DATA")
    private Integer specialAreaPopulation3gData;
    @Column(name = "SPECIAL_AREA_POPULATION_4G")
    private Integer specialAreaPopulation4g;
    @Column(name = "AVG_DBM_2G")
    private String avgDbm2g;
    @Column(name = "AVG_DBM_3G")
    private String avgDbm3g;
    @Column(name = "AVG_DBM_4G")
    private String avgDbm4g;
    @Column(name = "AVG_DBM_COMPETITOR_2G")
    private Integer avgDbmCompetitor2g;
    @Column(name = "AVG_DBM_COMPETITOR_3G")
    private Integer avgDbmCompetitor3g;
    @Column(name = "AVG_DBM_COMPETITOR_4G")
    private Integer avgDbmCompetitor4g;
    @Column(name = "COMPARE_WITH_COMPETITOR_2G")
    private String compareWithCompetitor2g;
    @Column(name = "COMPARE_WITH_COMPETITOR_3G")
    private String compareWithCompetitor3g;
    @Column(name = "COMPARE_WITH_COMPETITOR_4G")
    private String compareWithCompetitor4g;
    @Column(name = "SUGGEST_REASON")
    private String suggestReason;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;

    public SuggestionRadio() {
    }

    public SuggestionRadio(Long suggestId) {
        this.suggestId = suggestId;
    }

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }

    public Integer getStationTechType() {
        return stationTechType;
    }

    public void setStationTechType(Integer stationTechType) {
        this.stationTechType = stationTechType;
    }

    public Long getStationSolutionType() {
        return stationSolutionType;
    }

    public void setStationSolutionType(Long stationSolutionType) {
        this.stationSolutionType = stationSolutionType;
    }

    public Integer getRadioLocationType() {
        return radioLocationType;
    }

    public void setRadioLocationType(Integer radioLocationType) {
        this.radioLocationType = radioLocationType;
    }

    public Integer getRadioPillarType() {
        return radioPillarType;
    }

    public void setRadioPillarType(Integer radioPillarType) {
        this.radioPillarType = radioPillarType;
    }

    public Long getRadioPillarHeight() {
        return radioPillarHeight;
    }

    public void setRadioPillarHeight(Long radioPillarHeight) {
        this.radioPillarHeight = radioPillarHeight;
    }

    public Integer getHasConcavePoint() {
        return hasConcavePoint;
    }

    public void setHasConcavePoint(Integer hasConcavePoint) {
        this.hasConcavePoint = hasConcavePoint;
    }

    public Long getHeightWithConcavePoint() {
        return heightWithConcavePoint;
    }

    public void setHeightWithConcavePoint(Long heightWithConcavePoint) {
        this.heightWithConcavePoint = heightWithConcavePoint;
    }

    public Integer getConcavePointPopulation2g() {
        return concavePointPopulation2g;
    }

    public void setConcavePointPopulation2g(Integer concavePointPopulation2g) {
        this.concavePointPopulation2g = concavePointPopulation2g;
    }

    public Integer getConcavePointPopulation3gVoice() {
        return concavePointPopulation3gVoice;
    }

    public void setConcavePointPopulation3gVoice(Integer concavePointPopulation3gVoice) {
        this.concavePointPopulation3gVoice = concavePointPopulation3gVoice;
    }

    public Integer getConcavePointPopulation3gData() {
        return concavePointPopulation3gData;
    }

    public void setConcavePointPopulation3gData(Integer concavePointPopulation3gData) {
        this.concavePointPopulation3gData = concavePointPopulation3gData;
    }

    public Integer getConcavePointPopulation4g() {
        return concavePointPopulation4g;
    }

    public void setConcavePointPopulation4g(Integer concavePointPopulation4g) {
        this.concavePointPopulation4g = concavePointPopulation4g;
    }

    public Integer getSpecialArea() {
        return specialArea;
    }

    public void setSpecialArea(Integer specialArea) {
        this.specialArea = specialArea;
    }

    public Long getSpecialAreaType() {
        return specialAreaType;
    }

    public void setSpecialAreaType(Long specialAreaType) {
        this.specialAreaType = specialAreaType;
    }

    public Integer getSpecialAreaPopulation2g() {
        return specialAreaPopulation2g;
    }

    public void setSpecialAreaPopulation2g(Integer specialAreaPopulation2g) {
        this.specialAreaPopulation2g = specialAreaPopulation2g;
    }

    public Integer getSpecialAreaPopulation3gVoice() {
        return specialAreaPopulation3gVoice;
    }

    public void setSpecialAreaPopulation3gVoice(Integer specialAreaPopulation3gVoice) {
        this.specialAreaPopulation3gVoice = specialAreaPopulation3gVoice;
    }

    public Integer getSpecialAreaPopulation3gData() {
        return specialAreaPopulation3gData;
    }

    public void setSpecialAreaPopulation3gData(Integer specialAreaPopulation3gData) {
        this.specialAreaPopulation3gData = specialAreaPopulation3gData;
    }

    public Integer getSpecialAreaPopulation4g() {
        return specialAreaPopulation4g;
    }

    public void setSpecialAreaPopulation4g(Integer specialAreaPopulation4g) {
        this.specialAreaPopulation4g = specialAreaPopulation4g;
    }

    public String getAvgDbm2g() {
        return avgDbm2g;
    }

    public void setAvgDbm2g(String avgDbm2g) {
        this.avgDbm2g = avgDbm2g;
    }

    public String getAvgDbm3g() {
        return avgDbm3g;
    }

    public void setAvgDbm3g(String avgDbm3g) {
        this.avgDbm3g = avgDbm3g;
    }

    public String getAvgDbm4g() {
        return avgDbm4g;
    }

    public void setAvgDbm4g(String avgDbm4g) {
        this.avgDbm4g = avgDbm4g;
    }

    public Integer getAvgDbmCompetitor2g() {
        return avgDbmCompetitor2g;
    }

    public void setAvgDbmCompetitor2g(Integer avgDbmCompetitor2g) {
        this.avgDbmCompetitor2g = avgDbmCompetitor2g;
    }

    public Integer getAvgDbmCompetitor3g() {
        return avgDbmCompetitor3g;
    }

    public void setAvgDbmCompetitor3g(Integer avgDbmCompetitor3g) {
        this.avgDbmCompetitor3g = avgDbmCompetitor3g;
    }

    public Integer getAvgDbmCompetitor4g() {
        return avgDbmCompetitor4g;
    }

    public void setAvgDbmCompetitor4g(Integer avgDbmCompetitor4g) {
        this.avgDbmCompetitor4g = avgDbmCompetitor4g;
    }

    public String getCompareWithCompetitor2g() {
        return compareWithCompetitor2g;
    }

    public void setCompareWithCompetitor2g(String compareWithCompetitor2g) {
        this.compareWithCompetitor2g = compareWithCompetitor2g;
    }

    public String getCompareWithCompetitor3g() {
        return compareWithCompetitor3g;
    }

    public void setCompareWithCompetitor3g(String compareWithCompetitor3g) {
        this.compareWithCompetitor3g = compareWithCompetitor3g;
    }

    public String getCompareWithCompetitor4g() {
        return compareWithCompetitor4g;
    }

    public void setCompareWithCompetitor4g(String compareWithCompetitor4g) {
        this.compareWithCompetitor4g = compareWithCompetitor4g;
    }

    public String getSuggestReason() {
        return suggestReason;
    }

    public void setSuggestReason(String suggestReason) {
        this.suggestReason = suggestReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(Integer rowStatus) {
        this.rowStatus = rowStatus;
    }

  @Override
    public int hashCode() {
        int hash = 0;
        hash += (suggestId != null ? suggestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuggestionRadio)) {
            return false;
        }
        SuggestionRadio other = (SuggestionRadio) object;
        if ((this.suggestId == null && other.suggestId != null) || (this.suggestId != null && !this.suggestId.equals(other.suggestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SuggestionRadio[ suggestId=" + suggestId + " ]";
    }
    
}
