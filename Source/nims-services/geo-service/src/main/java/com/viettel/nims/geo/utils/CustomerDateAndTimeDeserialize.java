package com.viettel.nims.geo.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomerDateAndTimeDeserialize extends JsonDeserializer<Date> {

  private SimpleDateFormat dateFormat = new SimpleDateFormat(
      "MMM dd, yyyy, hh:mm:ss a");

  @Override
  public Date deserialize(JsonParser paramJsonParser,
      DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException {
    String str = paramJsonParser.getText().trim();
    try {
      return dateFormat.parse(str);
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
    }
    return paramDeserializationContext.parseDate(str);
  }
}
