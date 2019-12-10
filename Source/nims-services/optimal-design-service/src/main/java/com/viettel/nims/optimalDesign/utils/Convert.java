package com.viettel.nims.optimalDesign.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rabbit on 9/8/2019.
 */
public class Convert {
  public static Long convertObjectToLong(Object o) {
    String stringToConvert = String.valueOf(o);
    Long convertedLong = Long.parseLong(stringToConvert);
    return convertedLong;

  }

  public static List<Long> convertListObjectsToListLong(List<Object> list) throws Exception {
    List<Long> castList = new ArrayList<>();
    for (Object item : list)
      castList.add(convertObjectToLong(item));

    return castList;
  }
}
