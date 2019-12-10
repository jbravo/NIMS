package com.viettel.nims.commons.util;

import com.viettel.nims.commons.client.form.ValidateForm;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserTokenVsmart {

  private PassVtSecurity passVtSecurity;

  @Autowired
  public UserTokenVsmart(PassVtSecurity passVtSecurity) {
    this.passVtSecurity = passVtSecurity;
  }

  public ValidateForm validateToken(String tokenUser, String userLogin) {
    ValidateForm vf = new ValidateForm();
    try {
      String strDecrypt = this.passVtSecurity.decryptVtSaltValue(tokenUser);
      log.info("Decrypt token: {}", strDecrypt);
      if (compareDate(strDecrypt) && compareUsername(strDecrypt, userLogin)) {
        vf.setResult(Constant.VALIDATE_TOKEN.RESULT_OK);
        vf.setMessage(getUserId(strDecrypt));
      } else {
        vf.setResult(Constant.VALIDATE_TOKEN.RESULT_NOK);
      }
    } catch (Exception e) {
      log.error("Exception", e);
      vf.setResult(Constant.VALIDATE_TOKEN.RESULT_NOK);
      vf.setMessage("Exception: " + e.getMessage());
    }
    return vf;
  }

  public String getToken(String userName, Long userId) {
    Date dateTime = new Date();
    return getStringEncrypt(buildString(userName, userId, dateTime));
  }

  private String getStringEncrypt(String value) {
    return this.passVtSecurity.encryptVtSalt(value);
  }

  private String buildString(String userName, Long userId, Date dateTime) {
    return String.valueOf(userId) + "#" + userName + "#" + convertDateTime(dateTime);
  }

  private String convertDateTime(Date dateTime) {
    String formatTime = "dd-MM-yyyy HH:mm:ss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(formatTime);
    return dateFormat.format(dateTime);
  }

  private boolean compareDate(String strDecrypt) {
    String dateInToken = strDecrypt.substring(strDecrypt.lastIndexOf("#") + 1).substring(0, 10);
    String nowDate = convertDateTime(new Date()).substring(0, 10);
    log.info("dateInToken=" + dateInToken + "[nowDate]" + nowDate);
    return dateInToken.equalsIgnoreCase(nowDate);
  }


  private boolean compareUsername(String strDecrypt, String userName) {
    if (userName == null || "".equals(userName)) {
      return false;
    }
    String userInToken = strDecrypt
        .substring(strDecrypt.indexOf("#") + 1, strDecrypt.lastIndexOf("#"));
    return userName.equalsIgnoreCase(userInToken);

  }

  private Object getUserId(String strDecrypt) {
    return strDecrypt.subSequence(0, strDecrypt.indexOf("#"));
  }
}
