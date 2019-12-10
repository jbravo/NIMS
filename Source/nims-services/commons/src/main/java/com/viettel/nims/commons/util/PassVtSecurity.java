package com.viettel.nims.commons.util;

//import com.viettel.security.PassTranformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@RefreshScope
@Service
public class PassVtSecurity {

  @Value("${application.vsmart2-salt:VSMART2_SALT}")
  private String salt;

  @Value("${application.vsmart2-key-salt:VSMART_KEY_SALT}")
  private String vsmartKeySalt;

  private Environment env;

  @Autowired
  public PassVtSecurity(Environment env) {
    this.env = env;
  }

  public String decryptVtSalt(String key) {
    String valueDecrypt;
    try {
//      PassTranformer.setInputKey(this.salt);
      String value = this.env.getProperty(key, String.class, "");
//      valueDecrypt = PassTranformer.decrypt(value);
    } catch (Exception e) {
      log.error("decryptVtSalt", e);
      valueDecrypt = this.env.getProperty(key, String.class, "");
    }
//    return valueDecrypt;
    return null;
  }

  public String decryptVtWithSaltAndKey(String saltKey, String key) {
    String valueDecrypt;
    String value = this.env.getProperty(key, String.class, "");
    try {
      String saltValue = this.env.getProperty(saltKey, String.class, "");
//      PassTranformer.setInputKey(saltValue);
//      valueDecrypt = PassTranformer.decrypt(value);
    } catch (Exception e) {
      log.error("decryptVtSalt", e);
      valueDecrypt = value;
    }
//    return valueDecrypt;
    return  null;
  }

  public String decryptVtSaltValue(String value) {
    String valueDecrypt;
    try {
//      PassTranformer.setInputKey(this.salt);
//      valueDecrypt = PassTranformer.decrypt(value);
    } catch (Exception e) {
      log.error("decryptVtSaltValue", e);
      valueDecrypt = value;
    }
//    return valueDecrypt;
    return null;
  }

  public String encryptVtSalt(String value) {
    String valueEncrypt = null;
    try {
//      PassTranformer.setInputKey(this.salt);
//      valueEncrypt = PassTranformer.encrypt(value);
    } catch (Exception e) {
      log.error("encryptVtSalt", e);
    }
//    return valueEncrypt;
    return null;
  }

  public String encryptSmartKeyVtSalt(String value) {
    String valueEncrypt;
    try {
//      PassTranformer.setInputKey(vsmartKeySalt);
//      valueEncrypt = PassTranformer.encrypt(value);
    } catch (Exception e) {
      log.error("encryptVtSalt", e);
      return null;
    }
//    return valueEncrypt;
    return null;
  }
}
