package com.viettel.nims.commons.util;

import com.viettel.nims.commons.client.form.ConfigSmsGateWayForm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendSmsMT {

  private final Object lock = new Object();
  private HttpClientUtils httpClientUtils;

  @Autowired
  public SendSmsMT(HttpClientUtils httpClientUtils) {
    this.httpClientUtils = httpClientUtils;
  }

  private static String encode(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte aByte : bytes) {
      sb.append(convertDigit(aByte >> 4));
      sb.append(convertDigit(aByte & 0xF));
    }
    return sb.toString();
  }

  private static char convertDigit(int value) {
    value &= 15;
    if (value >= 10) {
      return (char) (value - 10 + 97);
    }
    return (char) (value + 48);
  }

  public int sendMT(String receiver, byte[] content, ConfigSmsGateWayForm configSmsGateWayForm) {
    synchronized (this.lock) {
      String sessionId = String.valueOf(System.currentTimeMillis());
      int error = 0;
      StringBuilder responseString = new StringBuilder();
      try {
        String reqContent =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + " <soap:Envelope "
                + "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "   xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "   xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> "
                + " <soap:Header> "
                + "   <AuthHeader xmlns=\"" + configSmsGateWayForm.getSms_service_xmlns() + "\">"
                + "     <Username>" + configSmsGateWayForm.getSms_service_username() + "</Username>"
                + "     <Password>" + configSmsGateWayForm.getSms_service_password() + "</Password>"
                + "   </AuthHeader>" + ""
                + "  </soap:Header>" + ""
                + "  <soap:Body>" + ""
                + "    <sendMT xmlns=\"" + configSmsGateWayForm.getSms_service_xmlns() + "\">"
                + "       <SessionId>" + sessionId + "</SessionId>"
                + "       <ServiceId>" + configSmsGateWayForm.getSms_service_name() + "</ServiceId>"
                + "       <Sender>" + configSmsGateWayForm.getSms_service_sender() + "</Sender>"
                + "       <Receiver>" + receiver + "</Receiver>" + ""
                + "       <ContentType>" + configSmsGateWayForm.getContentType() + "</ContentType>"
                + "       <Content>" + encode(content) + "</Content>" + ""
                + "       <Status>" + configSmsGateWayForm.getStatus() + "</Status>" + ""
                + "    </sendMT>"
                + "  </soap:Body>"
                + "</soap:Envelope>";
        HttpPost httpPost = new HttpPost(configSmsGateWayForm.getSms_service_url());
        httpPost.setEntity(new ByteArrayEntity(reqContent.getBytes("UTF-8")));
        PoolingHttpClientConnectionManager connManager = httpClientUtils.getConnectionManager();
        CloseableHttpClient httpclient = HttpClients.custom()
            .setConnectionManager(connManager)
            .setDefaultRequestConfig(httpClientUtils.getRequestConfig())
            .build();
        CloseableHttpResponse rs = httpclient.execute(httpPost);
        int statusCode = rs.getStatusLine().getStatusCode();
        int HTTP_OK = 200;
        if (HTTP_OK == statusCode) {
          HttpEntity responseHttpEntity = rs.getEntity();
          InputStream contentResponse = responseHttpEntity.getContent();
          BufferedReader buffer = new BufferedReader(new InputStreamReader(contentResponse));
          String line;
          while ((line = buffer.readLine()) != null) {
            responseString.append(line);
          }
          Pattern ptn = Pattern.compile("<sendMTResult>(\\d)</sendMTResult>");
          Matcher matcher = ptn.matcher(responseString.toString());
          while (matcher.find()) {
            error = Integer.parseInt(matcher.group(1));
          }
          //release all resources held by the responseHttpEntity
          EntityUtils.consume(responseHttpEntity);
          //close the stream
          contentResponse.close();
        } else {
          log.info("session {} http error {} ", sessionId, statusCode);
          error = statusCode;
        }
        // Close the connection manager.
        connManager.close();
      } catch (Exception ex) {
        log.error("session {} soap message error: {} ", sessionId, ex.getMessage());
        log.error("session {} response content: {}", sessionId, responseString);
      }
      return error;
    }
  }


}

