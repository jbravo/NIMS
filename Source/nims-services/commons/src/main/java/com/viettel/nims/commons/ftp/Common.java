package com.viettel.nims.commons.ftp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Common {

  public static boolean isEmpty(String text) {
    if (text == null) {
      return true;
    } else {
      return text.trim().length() <= 0;
    }
  }
}
