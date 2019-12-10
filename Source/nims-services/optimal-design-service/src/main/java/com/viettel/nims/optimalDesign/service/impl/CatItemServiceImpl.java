package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.commons.ItemComboboxDTO;
import com.viettel.nims.optimalDesign.dto.CatItemDTO;
import com.viettel.nims.optimalDesign.entity.CatCategory;
import com.viettel.nims.optimalDesign.entity.CatItem;
import com.viettel.nims.optimalDesign.entity.CatItemTranslation;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.CatCategoryRepository;
import com.viettel.nims.optimalDesign.repository.CatItemRepository;
import com.viettel.nims.optimalDesign.repository.CatItemTranslationRepository;
import com.viettel.nims.optimalDesign.service.CatItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author rabbit on 8/28/2019.
 */
@Service
public class CatItemServiceImpl implements CatItemService {
  @Autowired
  private CatCategoryRepository catCategoryRepository;

  @Autowired
  private CatItemRepository catItemRepository;

  @Autowired
  private CatItemTranslationRepository catItemTranslationRepository;

  private BaseMapper<CatItem, CatItemDTO> catItemMapper = new BaseMapper<>(CatItem.class, CatItemDTO.class);

  private BaseMapper<CatItem, ItemComboboxDTO> catItemComboboxMapper = new BaseMapper<>(CatItem.class, ItemComboboxDTO.class);
  /**
   * @author quangdv
   * @since 28-08-2019
   * @param catCategoryCode ma danh muc
   * @return danh sach item
   */
  @Override
  public List<ItemComboboxDTO> findAllByCatCategoryCode(String catCategoryCode, String lang) {
    Locale locale = lang == null ?  Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault():Locale.forLanguageTag(lang);

    CatCategory catCategory = catCategoryRepository.findByCategoryCode(catCategoryCode);
    List<CatItem> catItems = catItemRepository.findAllByCatCategoryId(catCategory.getCategoryId());

    if(locale == Locale.forLanguageTag("vi") || locale == Locale.getDefault()) {
      return catItemComboboxMapper.toDtoBean(catItems);
    }else{
      List<ItemComboboxDTO> itemComboboxDTOS = new ArrayList<>();

      for(CatItem catItem:catItems){
        ItemComboboxDTO comboboxDTO = new ItemComboboxDTO();
        comboboxDTO.setItemCode(catItem.getItemCode());

        CatItemTranslation catItemNameTrans = catItemTranslationRepository.findItemNameByCatItemIdAndLanguageCode(catItem.getItemId(), lang);
        comboboxDTO.setItemName(catItemNameTrans.getItemName());
        comboboxDTO.setItemId(catItemNameTrans.getItemId());

        itemComboboxDTOS.add(comboboxDTO);
      }

      return itemComboboxDTOS;
    }
  }
}
