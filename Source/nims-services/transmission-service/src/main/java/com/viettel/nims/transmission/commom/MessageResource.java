package com.viettel.nims.transmission.commom;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;

@Component
public class MessageResource {
  @Autowired
  ReloadableResourceBundleMessageSource messageSource;

  public String getMessage(String key, String langCode, List<?> args) {
    try {
      if (CommonUtils.isNullOrEmpty(args)) {
        return messageSource.getMessage(key, null, Locale.forLanguageTag(langCode));
      }
      Object[] o = new Object[args.size()];
      for (int i = 0; i < args.size(); i++) {
        o[i] = args.get(i);
      }
      return messageSource.getMessage(key, o, Locale.forLanguageTag(langCode));
    } catch (Exception e) {
      return null;
    }

  }

  public String getMessage(String key, String langCode) {
    try {
      return messageSource.getMessage(key, null, Locale.forLanguageTag(langCode));
    } catch (Exception e) {
      return null;
    }
  }

  public String getMessage(String key) {
    try {
      return messageSource.getMessage(key, null, null);
    } catch (Exception e) {
      return null;
    }
  }

  public boolean checkEqualHeader(Object header, String langCode) {
    try {
      Class<?> clazz = header.getClass();
      Class<?> c = Class.forName(header.getClass().getName());
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      for (Field field : clazz.getDeclaredFields()) {
        Element element = field.getAnnotation(Element.class);
        if (element.index() != -1) {
          try {
            Object objSrc = clazz.getMethod("get" + StringUtils.capitalize(field.getName())).invoke(header);
            if (!objSrc.toString().trim().equalsIgnoreCase(getMessage(element.header(),langCode))){
              return false;
            }
          } catch (Exception e) {
            return false;
          }
        }
      }
    } catch (ClassNotFoundException e) {
      return false;
    }
    return true;
  }

}
