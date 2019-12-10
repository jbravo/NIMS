package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.commons.ItemComboboxDTO;

import java.util.List;

/**
 * @author rabbit on 8/28/2019.
 */
public interface CatItemService {
  List<ItemComboboxDTO> findAllByCatCategoryCode(String catCategoryCode, String lang);
}
