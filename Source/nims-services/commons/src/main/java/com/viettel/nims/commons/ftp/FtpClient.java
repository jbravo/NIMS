package com.viettel.nims.commons.ftp;

import com.viettel.nims.commons.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FtpClient {

  private FTPClient ftpClient = null;

  public static void writeFileLocalString(String fileLocal, String fileName,
      InputStream inputStream) {
    File dir = new File(fileLocal);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String lastPath = fileLocal + "/" + fileName;
    File file = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      file = new File(lastPath);
      if (!file.exists()) {
        file.createNewFile();
      }
      byte[] buffer = new byte[1024];
      int len;
      while ((len = inputStream.read(buffer)) > -1) {
        baos.write(buffer, 0, len);
      }
      baos.flush();
    } catch (IOException ex) {
      log.error("IOException", ex);
    }
    try (
        OutputStream out = new FileOutputStream(file, false);
        InputStream ip = new ByteArrayInputStream(baos.toByteArray())
    ) {
      int read;
      byte[] bytes = new byte[1024];
      while ((read = ip.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      baos.close();
    } catch (Exception ex) {
      log.error("Exception", ex);
    }
  }

  public void Connect(LogServer logServer) {
    try {
      ftpClient = new FTPClient();
      ftpClient.setConnectTimeout(logServer.getConnectTimeout());
      if (logServer.getPort() > 0) {
        ftpClient.connect(logServer.getIp(), logServer.getPort());
      } else {
        ftpClient.connect(logServer.getIp());
      }
      String password = logServer.getPassword();
      boolean bStatus = ftpClient.login(logServer.getUsername(), password);
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
      if (!bStatus) {
        String err = "Fail to login ftp server: " + logServer.getIp();
        log.info(err);
      }
    } catch (Exception ex) {
      log.error("Exception", ex);
    }
  }

  public InputStream getFileStream(String folder, String fileName) throws Exception {
    FTPFile[] files;
    ftpClient.enterLocalPassiveMode();
    fileName = StringUtils.getSafeFileName(fileName);
    InputStream inputStreamReturn = null;
    if (folder != null && folder.length() > 0) {
      files = ftpClient.listFiles(folder);
    } else {
      files = ftpClient.listFiles();
    }
    if (null == files || files.length < 1) {
      return null;
    } else {
      for (FTPFile file : files) {
        if (file.isFile() && file.getName().toUpperCase().contains(fileName.toUpperCase())) {
          InputStream inputStream = ftpClient.retrieveFileStream(folder + "/" + fileName);
          inputStreamReturn = copyInputStream(inputStream);
          inputStream.close();
          ftpClient.completePendingCommand();
          break;
        }
      }
    }
    return inputStreamReturn;
  }

  public void makeFullDirectory(String directory) throws Exception {
    if (directory != null) {
      String[] paths = directory.split("\\/");
      for (String folder : paths) {
        if (folder.length() > 0) {
          ftpClient.makeDirectory(folder);
          ftpClient.changeWorkingDirectory(folder);
        }
      }
    }
  }

  public boolean uploadFile(File f, String directory) {
    try {
      ftpClient.enterLocalPassiveMode();
      if (directory != null) {
        ftpClient.changeWorkingDirectory(directory);
      } else {
        return false;
      }
      if (f.exists() && f.isFile()) {
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        try (InputStream inputStream = new FileInputStream(f)) {
          ftpClient.storeFile(f.getName() + ".tmp", inputStream);
          ftpClient.rename(f.getName() + ".tmp", f.getName());
        } catch (IOException ex) {
          log.error("IOException", ex);
        }
      } else {
        return false;
      }
      return true;
    } catch (Exception e) {
      log.error("Exception", e);
      return false;
    }
  }

  public boolean uploadFile(InputStream inputStream, String directory, String fileName) {
    try {
      ftpClient.enterLocalPassiveMode();
      if (directory != null) {
        ftpClient.changeWorkingDirectory(directory);
      } else {
        return false;
      }
      ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
      try {
        ftpClient.storeFile(fileName + ".tmp", inputStream);
        ftpClient.rename(fileName + ".tmp", fileName);
      } catch (IOException ex) {
        log.error("IOException", ex);
      }
      return true;
    } catch (Exception e) {
      log.error("Exception", e);
      return false;
    }
  }

  public InputStream copyInputStream(InputStream input) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;
    while ((len = input.read(buffer)) > -1) {
      baos.write(buffer, 0, len);
    }
    baos.flush();
    return new ByteArrayInputStream(baos.toByteArray());
  }
}
