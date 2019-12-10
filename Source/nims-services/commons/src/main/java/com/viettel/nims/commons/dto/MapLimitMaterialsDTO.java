package com.viettel.nims.commons.dto;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "lstMaterials",
    propOrder = {
        "accType",
        "areaGroupCode",
        "districtCode",
        "districtName",
        "endDate",
        "id",
        "limiTechnicalBelow",
        "limitWarning",
        "limitFree",
        "limitTechnical",
        "materialCode",
        "materialName",
        "nodeCode",
        "productCode",
        "productName",
        "provinceCode",
        "provinceName",
        "startDate",
        "status",
        "technology",
        "telServiceId",
        "telServiceName",
        "technologyName",
        "description",
        "areaCodes",
        "areaGroupCodes",
        "mappingAreaType",
        "accTypeName",
        "productCodes",
        "productOfferingId",
        "checkSerial",
        "unit",
        "unitName",
        "price",
        "groupCode",
        "groupName"
    }
)
public class MapLimitMaterialsDTO {

  private Short accType;
  private String areaGroupCode;
  private String districtCode;
  private String districtName;
  private Date endDate;
  private Long id;
  private Long limiTechnicalBelow;
  private Long limitWarning;
  private Long limitFree;
  private Long limitTechnical;
  private String materialCode;
  private String materialName;
  private String nodeCode;
  private String productCode;
  private String productName;
  private String provinceCode;
  private String provinceName;
  private Date startDate;
  private Short status;
  private String technology;
  private Long telServiceId;
  private String telServiceName;
  private String technologyName;
  private String description;
  private List<String> areaCodes;
  private List<String> areaGroupCodes;
  private String mappingAreaType = "0";
  private String accTypeName;
  private List<String> productCodes;
  private Long productOfferingId;
  private String checkSerial;
  private String unit;
  private String unitName;
  private Long price;
  private String groupCode;
  private String groupName;
}
