package com.viettel.nims.commons.util;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@NoArgsConstructor
@Service
public class LanguageBundleUtils {

  private static final String RESOURCE = "Language_vi_VN";
  private static final String RESOURCE_FIXED_SERVICE = "FixService";
  private static final String RESOURCE_TASK = "Task";
  private static final String RESOURCE_VSMART_NEW = "VsmartMessage";
  private static Locale local = null;
  private static ResourceBundle languageRb = null;

  @Autowired
  private MessageSource messageSource;

  public static synchronized String getString(String key) {
    try {
      if (local != null) {
        languageRb = ResourceBundle.getBundle(RESOURCE, local);
      } else {
        languageRb = ResourceBundle.getBundle(RESOURCE);
      }
      return languageRb.getString(key);
    } catch (Exception ex) {
      log.error("Exception", ex);
      if (key != null) {
        return key;
      }
    }
    return "";
  }

  public static synchronized String getString(Locale locale, String key) {
    try {
      if (locale != local) {
        try {
          local = locale;
          languageRb = ResourceBundle
              .getBundle(RESOURCE + "_" + local.getLanguage() + "_" + local.getCountry());
        } catch (Exception ex) {
          log.error("Exception", ex);
          languageRb = ResourceBundle.getBundle(RESOURCE);
        }
      }
      return languageRb.getString(key);
    } catch (Exception ex) {
      log.error("Exception", ex);
      if (key != null) {
        return key;
      }
    }
    return "";
  }

  public static synchronized String getStringVsmart(String key) {
    try {
      languageRb = ResourceBundle.getBundle(
          RESOURCE_VSMART_NEW + "_" + Constant.LANGUAGE.LANGUAGE_VI + "_"
              + Constant.LANGUAGE.COUNTRY_VN);
      return languageRb.getString(key);
    } catch (Exception ex) {
      log.error("error", ex);
      if (key != null) {
        return key;
      }
    }
    return "";
  }

  public static synchronized String getStringVsmart(HttpServletRequest req, String key) {
    try {
      String locale = req.getHeader("locale");
      if (locale != null) {
        if (locale.contains(Constant.LANGUAGE.LANGUAGE_VI)) {
          languageRb = ResourceBundle.getBundle(
              RESOURCE_VSMART_NEW + "_" + Constant.LANGUAGE.LANGUAGE_VI + "_"
                  + Constant.LANGUAGE.COUNTRY_VN);
        } else {
          languageRb = ResourceBundle.getBundle(RESOURCE_VSMART_NEW);
        }
      } else {
        languageRb = ResourceBundle.getBundle(RESOURCE_VSMART_NEW);
      }
      return languageRb.getString(key);
    } catch (Exception ex) {
      log.error("error", ex);
      if (key != null) {
        return key;
      }
    }
    return "";
  }

  public static synchronized String getStringTask(String key) {
    try {
      if (local != null) {
        languageRb = ResourceBundle.getBundle(RESOURCE_TASK, local);
      } else {
        languageRb = ResourceBundle.getBundle(RESOURCE_TASK);
      }
      return languageRb.getString(key);
    } catch (Exception e) {
      log.error("Exception", e);
      return key;
    }
  }

  public static synchronized String getStringFixedService(String key) {
    try {
      if (local != null) {
        languageRb = ResourceBundle.getBundle(RESOURCE_FIXED_SERVICE, local);
      } else {
        languageRb = ResourceBundle.getBundle(RESOURCE_FIXED_SERVICE);
      }
      return languageRb.getString(key);
    } catch (Exception e) {
      log.error("Exception", e);
      return key;
    }
  }

  public static synchronized String getStringFixedService(String key, String codeBug) {
    StringBuilder message = new StringBuilder();
    if (codeBug != null && !"".equals(codeBug)) {
      message.append(codeBug);
    }
    try {
      if (local != null) {
        languageRb = ResourceBundle.getBundle(RESOURCE_FIXED_SERVICE, local);
      } else {
        languageRb = ResourceBundle.getBundle(RESOURCE_FIXED_SERVICE);
      }
      message.append(languageRb.getString(key));
    } catch (Exception e) {
      log.error("Exception", e);
      message.append(key);
    }
    return message.toString();
  }

  public synchronized String getStringResouceTest(String key) {
    Locale currentLocale = LocaleContextHolder.getLocale();
    log.info("{} - {}", currentLocale.getLanguage(), currentLocale.getDisplayCountry());
    log.info(currentLocale.toString());
    String welcome = messageSource
        .getMessage(key, new Object[]{"John Doe"}, currentLocale);
    return welcome;
  }
}
