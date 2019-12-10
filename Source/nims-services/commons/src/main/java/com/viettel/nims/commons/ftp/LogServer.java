package com.viettel.nims.commons.ftp;

import com.viettel.nims.commons.util.PassVtSecurity;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
public class LogServer {

  @Value("${application.server-up-url}")
  private String url;

  @Value("${application.server-up-ip}")
  private String ip;

  @Value("${application.server-up-port:23}")
  private int port;

  @Value("${application.server-connect-timeout:10000}")
  private int connectTimeout;

  @Value("${application.server-up-user}")
  private String encryptedUsername;

  @Value("${application.server-up-pass}")
  private String encryptedPassword;

  private String username;
  private String password;

  private PassVtSecurity passVtSecurity;

  @Autowired
  public LogServer(PassVtSecurity passVtSecurity) {
    this.passVtSecurity = passVtSecurity;
  }

  @PostConstruct
  public void decrypt() {
    this.username = passVtSecurity.decryptVtSaltValue(encryptedUsername);
    this.password = passVtSecurity.decryptVtSaltValue(encryptedPassword);
  }
}
