package com.viettel.nims.optimalDesign.utils;

import java.util.List;

public class QueryParam {
  public static Object queryByCondition(List whereCause, String and) {

    if (whereCause.size() == 2) {
      return whereCause.get(0) + and + whereCause.get(1);
    }
    if (whereCause.size() == 3) {
      return whereCause.get(0) + and + whereCause.get(1) + and + whereCause.get(2);
    }
    if (whereCause.size() == 4) {
      return whereCause.get(0) + and + whereCause.get(1) + and + whereCause.get(2) + and + whereCause.get(3);
    }
    if (whereCause.size() == 5) {
      return whereCause.get(0) + and + whereCause.get(1) + and + whereCause.get(2) + and + whereCause.get(3) + and + whereCause.get(4);
    }
    if (whereCause.size() == 6) {
      return whereCause.get(0) + and + whereCause.get(1) + and + whereCause.get(2) + and + whereCause.get(3) + and + whereCause.get(4) + and + whereCause.get(5);
    }
    if (whereCause.size() == 3) {
      return whereCause.get(0) + and + whereCause.get(1) + and + whereCause.get(2);
    }

    if (whereCause.size() == 1) {
      return whereCause.get(0);
    }
    return "";
  }

}
