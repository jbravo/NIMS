package com.viettel.nims.commons.pool;

import com.viettel.nims.commons.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@Slf4j
public class MyServiceLogHandler implements SOAPHandler<SOAPMessageContext> {

  @Override
  public Set<QName> getHeaders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void close(MessageContext arg0) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean handleFault(SOAPMessageContext arg0) {
    SOAPMessage message = arg0.getMessage();
    try {
      message.writeTo(System.out);
    } catch (SOAPException | IOException e) {
      // TODO Auto-generated catch block
      log.error("exception SOAPMessageContext: ", e);
    }
    return false;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    try {
      boolean isOutboundMessage = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
      if (isOutboundMessage) {
        Date startDate = new Date();
        context.put("startDate", DateTimeUtils
            .convertDateTimeToString(startDate, "yyyy/MM/dd HH:mm:ss:SSS"));
        context.setScope("startDate", MessageContext.Scope.APPLICATION);
        String inputSoap = convertMessageToString(context);
        // TODO(phucng): comment log input/ouput when call webservice gnoc wfm
        // log.info("input : {}", inputSoap);
        context.put("inputSoap", inputSoap);
        context.setScope("inputSoap", MessageContext.Scope.APPLICATION);
      } else {
        // TODO(phucng): comment log input/ouput when call webservice gnoc wfm
        // Date endDate = new Date();
        // String startDateStr = (String) context.get("startDate");
        // Date startDate = DateTimeUtils.convertStringToTime(startDateStr, "yyyy/MM/dd HH:mm:ss:SSS");
        // assert startDate != null;
        // long duration = endDate.getTime() - startDate.getTime();
        // String sb = duration + "||"
        //     + context.get("startDate") + "||"
        //     + context.get("inputSoap") + "||"
        //     + DateTimeUtils.convertDateTimeToString(endDate, "yyyy/MM/dd HH:mm:ss:SSS") + "||"
        //     + convertMessageToString(context);
        // log.info(sb);
      }
      return true;
    } catch (Exception | Error e) {
      log.error("handleMessage", e);
      return true;
    }
  }

  private String convertMessageToString(SOAPMessageContext context) {
    ByteArrayOutputStream out = null;
    String result = "";
    try {
      SOAPMessage message = context.getMessage();
      out = new ByteArrayOutputStream();
      message.writeTo(out);
      result = new String(out.toByteArray());
    } catch (Exception ex) {
      log.info("exception write message", ex);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          log.info("exception close outputStream", e);
        }
      }
    }
    return result;
  }
}
