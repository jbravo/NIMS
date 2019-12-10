package com.viettel.nims.commons.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
    public static <K, T> T convertObjToObj(K src, T desc) {
        Class srcClass = src.getClass();
        Class descClass = desc.getClass();

        Field[] fieldsDesc = descClass.getDeclaredFields();

        if (fieldsDesc != null) {
            for (Field field : fieldsDesc) {
                try {
                    Object objSrc = srcClass.getMethod("get" + StringUtils.capitalize(field.getName())).invoke(src);
                    descClass.getMethod("set" + StringUtils.capitalize(field.getName()), field.getType()).invoke(desc, objSrc);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                }
            }
        }
        return desc;
    }

    public static String getCapitalizeFromProperties(String str) {
        return StringUtils.capitalize(StringUtils.join(str.split("(?=\\p{Upper})"), " "));
    }

    public static String getStrDate(Long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static Timestamp getTimeStamp1(Timestamp timestamp) {
        Timestamp result;
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        cal.add(Calendar.DAY_OF_WEEK, 1);
        result = new Timestamp(cal.getTime().getTime());
        return result;
    }

    public static Timestamp getTimeStamp(Timestamp timestamp) {
        Timestamp result;
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        cal.add(Calendar.SECOND, 86399);
        result = new Timestamp(cal.getTime().getTime());
        return result;
    }

    public static String getLikeCondition(String str) {
        if (str == null) {
            str = "";
        }
        if (!str.trim().isEmpty()) {
            String newStr =
                    str.trim()
                            .replace("\\", "\\\\")
                            .replace("\\t", "\\\\t")
                            .replace("\\n", "\\\\n")
                            .replace("\\r", "\\\\r")
                            .replace("\\z", "\\\\z")
                            .replace("\\b", "\\\\b")
                            .replaceAll("_", "\\\\_")
                            .replaceAll("%", "\\\\%");
            str = "%".concat(newStr.trim()).concat("%");
        }
        return str;
    }

	public static char stripUTF8Char(char ch) {
		// source array, order ascending UTF-8 code
		char[] DESTINATION_CHARACTERS = { 'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U',
			'U', 'Y', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A', 'a', 'D',
			'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
			'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
			'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
			'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U',
			'u', 'U', 'u', 'U', 'u', 'U', 'u', 'Y', 'y', 'Y', 'y', 'Y', 'y', 'Y', 'y' };

		char[] SOURCE_CHARACTERS 	= 	{ 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù',
			'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ',
			'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ',
			'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề',
			'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ',
			'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ',
			'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ỳ', 'ỳ', 'Ỵ', 'ỵ', 'Ỷ', 'ỷ', 'Ỹ', 'ỹ' };
		char result = ch;
		int index = java.util.Arrays.binarySearch(SOURCE_CHARACTERS, result);
		if (index >= 0) {
			result = DESTINATION_CHARACTERS[index];
		}
		return result;
	}

	public static String stripUTF8Char(String text) {
		StringBuilder sb = new StringBuilder(text);
		for (int i = 0; i < sb.length(); i++) {
			sb.setCharAt(i, stripUTF8Char(sb.charAt(i)));
		}
		return sb.toString();
	}

	public static String nvl(String str, String defaultStr) {
		if (!StringUtils.isEmpty(str)) {
			return str;
		}
		return defaultStr;
	}

    /**
     * Check empty
     *
     * @param h
     * @return
     */
    public static boolean isEmpty(Hashtable<?, ?> h) {
        if (h == null || h.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] o) {
        if (o == null || o.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Vector<?> v) {
        if (v == null || v.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.trim().equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List<?> l) {
        if (l == null || l.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Set<?> s) {
        if (s == null || s.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map<?, ?> m) {
        if (m == null || m.size() == 0) {
            return true;
        }
        return false;
    }
    public static String getSafeParameter(String value) {
        if (value == null||"".equals(value))
            return "";
        return value.replace("/", "//").replace("_", "/_")
        	    .replace("%", "/%");
    }
//
//    public static File saveFile(MultipartFile file) throws IOException {
//        String path = PropertiesConfig.getApplicationKey("dir.file.in") + file.getOriginalFilename();
//        File dest = new File(path);
//        FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
//        return dest;
//    }

  /**
   * Cast BigDemical to Long
   *
   * @param value
   * @return
   */
  public static Long demicalToLong(BigDecimal value) {
    if (value == null) {
      return 0L;
    } else {
      return value.longValue();
    }
  }

  /**
   * Check string is null.
   *
   * @param str
   * @return
   */
  public static boolean isNullOrEmpty(String str) {
    return (str == null || str.trim().isEmpty());
  }

  /**
   * Check list object is null.
   *
   * @param data
   * @return
   */
  public static boolean isNullOrEmpty(List data) {
    return (data == null || data.isEmpty());
  }

  /**
   * Chuyen doi tuong String thanh doi tuong Date.
   *
   * @param date
   *            Xau ngay, co dinh dang duoc quy trinh trong file Constants
   * @return Doi tuong Date
   * @throws Exception
   *             Exception
   */
  public static Date convertStringToDate(String date) throws Exception {
    if (date == null || date.trim().isEmpty()) {
      return null;
    } else {
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      dateFormat.setLenient(false);
      return dateFormat.parse(date);
    }
  }

  /**
   * Chuyen doi tuong Date thanh doi tuong String.
   *
   * @param date
   *            Doi tuong Date
   * @return Xau ngay, co dang dd/MM/yyyy
   */
  public static String convertDateToString(Date date) {
    if (date == null) {
      return "";
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      return dateFormat.format(date);
    }
  }

  /**
   * Chuyen doi tuong Date thanh doi tuong String.
   *
   * @param date
   *            Doi tuong Date
   * @return Xau ngay, co dang dd/MM/yyyy
   */
  public static String convertDateToString(Date date, String pattern) {
    if (date == null) {
      return "";
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      return dateFormat.format(date);
    }
  }

  /**
   * Conver tu string sang date theo dinh dang mong muon
   *
   * @param date
   * @param pattern
   *            : kieu dinh dang vd: "dd/MM/yyyy hh:MM"
   * @return
   * @throws Exception
   */
  public static Date convertStringToDateTime(String date, String pattern) throws Exception {
    if (date == null || date.trim().isEmpty()) {
      return null;
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      dateFormat.setLenient(false);
      try {
        return dateFormat.parse(date);
      } catch (Exception ex) {
        LOGGER.error("convertStringToDateTime", ex);
        return null;
      }
    }
  }

  /**
   * Lay gia tri tu file config.properties.
   *
   * @param key
   *            Khoa
   * @return Gia tri
   */
  public static String getConfig(String key) {
    ResourceBundle rb = ResourceBundle.getBundle("config");
    return rb.getString(key);
  }

  public static Object NVL(Object value, Object defaultValue) {
    return value == null ? defaultValue : value;
  }

  public static Double NVL(Double value) {

    return NVL(value, new Double(0));
  }

  public static Integer NVL(Integer value) {
    return value == null ? new Integer(0) : value;
  }
  public static Integer NVL(Integer value, Integer defaultValue) {
    return value == null ? defaultValue : value;
  }

  public static BigDecimal NVL(BigDecimal value) {
    return value == null ? new BigDecimal(0) : value;
  }

  public static Double NVL(Double value, Double defaultValue) {
    return value == null ? defaultValue : value;
  }

  public static Long NVL(Long value, Long defaultValue) {
    return value == null ? defaultValue : value;
  }

  public static String NVL(String value, String nullValue, String notNullValue) {

    return value == null ? nullValue : notNullValue;
  }

  public static String NVL(String value, String defaultValue) {

    return NVL(value, defaultValue, value);
  }

  public static String NVL(String value) {

    return NVL(value, "");
  }

  public static Long NVL(Long value) {

    return NVL(value, 0L);
  }

  public static Long checkBoxValue(Long value) {
    if (value != null && value.equals(1L)) {
      return 1L;
    } else {
      return 0L;
    }
  }

  public static String getClientIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-FORWARDED-FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * Convert String to list
   *
   * @param sourceString
   * @param pattern
   * @return
   */
  public static List<String> toList(String sourceString, String pattern) {
    List<String> results = new LinkedList<String>();
    String[] sources = NVL(sourceString).split(pattern);
    for (String source : sources) {
      results.add(source);
    }
    return results;
  }

  /**
   * Convert String to list
   *
   * @param sourceString
   * @param pattern
   * @return
   */
  public static List<Long> toLongList(String sourceString, String pattern) {
    List<Long> results = new LinkedList<Long>();
    String[] sources = NVL(sourceString).split(pattern);
    for (String source : sources) {
      results.add(Long.valueOf(source));
    }
    return results;
  }

  /**
   *
   * @param endDate
   * @param startDate
   * @return
   */
  public static double monthsBetween(Date endDate, Date startDate) {
    Calendar endCalendar = Calendar.getInstance();
    Calendar startCalendar = Calendar.getInstance();
    endCalendar.setTime(endDate);
    startCalendar.setTime(startDate);
    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    return diffMonth;
  }

  /**
   *
   * @param date
   * @return
   * @throws Exception
   */
  public static Date getLastDayOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    return c.getTime();
  }

  /**
   *
   * @param date
   * @return
   * @throws Exception
   */
  public static Date getFirstDayOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, 1);
    return c.getTime();
  }

  /**
   * Format Double theo he thong kieu Phap
   *
   * @param itemValue
   * @return
   */
  public static String formatFrenchNumber(Double itemValue) {
    if (itemValue != null) {
      try {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);

        return df.format(itemValue);
      } catch (Exception ex) {
        LOGGER.error("formatFrenchNumber", ex);
        return String.valueOf(itemValue);
      }
    } else {
      return "";
    }
  }

  /**
   * Convert image to base64 code.
   *
   * @param imageFile
   * @return
   * @throws IOException
   */
  public static String getBase64StringOfImage(File imageFile) throws IOException {
    String imgString;
    BufferedImage buffImage = ImageIO.read(imageFile);
    try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
      ImageIO.write(buffImage, "jpg", bout);
      byte[] imageBytes = bout.toByteArray();
      imgString = Base64.getEncoder().encodeToString(imageBytes);
    }
    return imgString;
  }

  /**
   * Trunc date.
   *
   * @param inputDate
   * @return
   */
  public static Date TRUNC(Date inputDate) {
    if (inputDate == null) {
      return null;
    } else {
      Calendar cal = Calendar.getInstance();
      cal.setTime(inputDate);
      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.HOUR, 0);
      return cal.getTime();
    }
  }

  /**
   * kiem tra 1 xau rong hay null khong
   *
   * @param str : xau
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filter(String str, StringBuilder queryString, List<Object> paramList, String field) {
    if ((str != null) && !"".equals(str.trim())) {
      queryString.append(" AND LOWER(").append(field).append(") LIKE ? ESCAPE '/'");
      str = str.replace("  ", " ");
      str = "%" + str.trim().toLowerCase().replace("/", "//").replace("_", "/_").replace("%", "/%") + "%";
      paramList.add(str);
    }
  }

  /**
   * kiem tra 1 so rong hay null khong
   *
   * @param n So
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filter(Long n, StringBuilder queryString, List<Object> paramList, String field) {
    if ((n != null) && (n > 0L)) {
      queryString.append(" AND ").append(field).append(" = ? ");
      paramList.add(n);
    }
  }

  /**
   * kiem tra 1 so rong hay null khong
   *
   * @param n So
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filter(Boolean n, StringBuilder queryString, List<Object> paramList, String field) {
    if (n != null) {
      queryString.append(" AND ").append(field).append(" = ? ");
      paramList.add(n);
    }
  }

  /**
   * kiem tra 1 Integer rong hay null khong
   *
   * @param n So
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filter(Integer n, StringBuilder queryString, List<Object> paramList, String field) {
    if ((n != null) && (n > 0)) {
      queryString.append(" AND ").append(field).append(" = ? ");
      paramList.add(n);
    }
  }

  /**
   * kiem tra 1 xau rong hay null khong
   *
   * @param date So
   * @param queryString
   * @param field
   * @param paramList
   */
  public static void filter(Date date, StringBuilder queryString, List<Object> paramList, String field) {
    if ((date != null)) {
      queryString.append(" AND ").append(field).append(" = ? ");
      paramList.add(date);
    }
  }

  /**
   * kiem tra 1 xau rong hay null khong
   *
   * @param arrIds
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filterSelectInL(String arrIds, StringBuilder queryString, List<Object> paramList, String field) {
    if (!isNullOrEmpty(arrIds)) {
      queryString.append(" AND ").append(field).append(" IN (-1 ");
      String[] ids = arrIds.split(",");
      for (String strId : ids) {
        queryString.append(", ?");
        paramList.add(Long.parseLong(strId.trim()));
      }
      queryString.append(" ) ");
    }
  }

  /**
   * Kiem tra lon hon hoac bang.
   *
   * @param obj
   *            So
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filterGe(Object obj, StringBuilder queryString, List<Object> paramList, String field) {
    if (obj != null && !"".equals(obj)) {
      queryString.append(" AND ").append(field).append(" >= ? ");
      paramList.add(obj);
    }
  }

  /**
   * Kiem tra nho hon hoac bang.
   *
   * @param obj
   *            So
   * @param queryString
   * @param paramList
   * @param field
   */
  public static void filterLe(Object obj, StringBuilder queryString, List<Object> paramList, String field) {
    if (obj != null && !"".equals(obj)) {
      queryString.append(" AND ").append(field).append(" <= ? ");
      paramList.add(obj);
    }
  }

  /**
   * filter for inserting preparedStatement
   *
   * @param value
   * @param index
   * @param preparedStatement
   * @throws Exception
   */
  public static void filter(String value, PreparedStatement preparedStatement, int index) throws Exception {

    if (value != null) {
      preparedStatement.setString(index, value.trim());
    } else {
      preparedStatement.setNull(index, java.sql.Types.NULL);
    }
  }

  /**
   *
   * @param value
   * @param preparedStatement
   * @param index
   * @throws Exception
   */
  public static void filter(Double value, PreparedStatement preparedStatement, int index) throws Exception {

    if (value != null) {
      preparedStatement.setDouble(index, value);
    } else {
      preparedStatement.setNull(index, java.sql.Types.NULL);
    }
  }

  /**
   *
   * @param value
   * @param preparedStatement
   * @param index
   * @throws Exception
   */
  public static void filter(Long value, PreparedStatement preparedStatement, int index) throws Exception {

    if (value != null) {
      preparedStatement.setLong(index, value);
    } else {
      preparedStatement.setNull(index, java.sql.Types.NULL);
    }
  }

  /**
   *
   * @param value
   * @param preparedStatement
   * @param index
   * @throws Exception
   */
  public static void filter(Object value, PreparedStatement preparedStatement, int index) throws Exception {
    if (value != null) {
      if (value instanceof Date) {
        Date temp = (Date) value;
        preparedStatement.setObject(index, new java.sql.Timestamp(temp.getTime()));
      } else {
        preparedStatement.setObject(index, value);
      }

    } else {
      preparedStatement.setNull(index, java.sql.Types.NULL);
    }
  }

  /**
   *
   * @param value
   * @param preparedStatement
   * @param index
   * @throws Exception
   */
  public static void filter(java.sql.Date value, PreparedStatement preparedStatement, int index) throws Exception {

    if (value != null) {
      preparedStatement.setDate(index, value);
    } else {
      preparedStatement.setNull(index, java.sql.Types.NULL);
    }
  }

  /**
   * kiem tra mot chuoi co chua ky tu Unicode khong
   *
   * @param str
   * @return
   */
  public static boolean containUnicode(String str) {
    String signChars = "ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵý";
    if ("".equals(str)) {
      return false;
    } else {
      int count = 0;
      String subStr;
      while (count < str.length()) {
        subStr = str.substring(count, count + 1);
        if (signChars.contains(subStr)) {
          return true;
        }
        count++;
      }
    }
    return false;
  }

  /**
   * kiem tra mot chuoi co chua ky tu Unicode khong
   *
   * @param str
   * @return
   */
  public static boolean containPhoneNumber(String str) {
    String signChars = "0123456789";
    if ("".equals(str)) {
      return false;
    } else {
      int count = 0;
      String subStr;
      while (count < str.length()) {
        subStr = str.substring(count, count + 1);
        if (!signChars.contains(subStr)) {
          return false;
        } else {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * replaceSpecialKeys
   *
   * @param str
   *            String
   * @return String
   */
  public static String replaceSpecialKeys(String str) {
    str = str.replace("  ", " ");
    str = "%" + str.trim().toLowerCase().replace("/", "//").replace("_", "/_").replace("%", "/%") + "%";
    return str;
  }


  /**
   * Format so.
   *
   * @param d
   *            So
   * @return Xau
   */
  public static String formatNumber(Double d) {
    if (d == null) {
      return "";
    } else {
      DecimalFormat format = new DecimalFormat("######.#####");
      return format.format(d);
    }
  }

  /**
   * Format so.
   *
   * @param d
   *            So
   * @return Xau
   */
  public static String formatNumber(Long d) {
    if (d == null) {
      return "";
    } else {
      DecimalFormat format = new DecimalFormat("######");
      return format.format(d);
    }
  }

  /**
   * Chuyen string -> List Long
   *
   * @param inpuString
   * @param separator
   * @return
   */
  public static List<Long> string2ListLong(String inpuString, String separator) {
    List<Long> outPutList = new ArrayList<Long>();

    if (inpuString != null && !"".equals(inpuString.trim()) && separator != null && !"".equals(separator.trim())) {
      String[] idArr = inpuString.split(separator);
      for (String idArr1 : idArr) {
        if (idArr1 != null && !"".equals(idArr1.trim())) {
          outPutList.add(Long.parseLong(idArr1.trim()));
        }
      }
    }

    return outPutList;
  }

  /**
   * chuyen list string ve chuỗi phuc vu tim kiem
   *
   * @param lstObject
   *            lst
   * @param separator
   * @return ket qua
   * @throws Exception
   *             ex
   */
  public static String convertListToString(List lstObject, String separator) throws Exception {
    try {
      if (lstObject != null && !lstObject.isEmpty()) {
        StringBuilder result = new StringBuilder("");
        int size = lstObject.size();
        result.append("'").append(lstObject.get(0)).append("'");
        for (int i = 1; i < size; i++) {
          result.append(separator);
          result.append("'");
          result.append(lstObject.get(i));
          result.append("'");
        }
        return result.toString();
      } else {
        return "";
      }
    } catch (Exception e) {
      throw e;
    }
  }

  public static Date subDayFromDate(Date date, int day) {
    if (date != null) {
      Calendar cld = Calendar.getInstance();
      cld.setTime(date);
      cld.add(Calendar.DATE, -day);
      return cld.getTime();
    }
    return null;
  }

  /**
   * Convert Object To String Json
   *
   * @param object
   * @return
   */
  public static String convertObjectToStringJson(Object object) {
    String strMess = "";
    try {
      Gson gson = new Gson();
      strMess = gson.toJson(object);
    } catch (Exception e) {
      LOGGER.error("Error! Convert object to json", e);
    }
    return strMess;
  }

  /**
   * Get long parameter.
   *
   * @param req
   * @param name
   * @return
   */
  public static Long getParameterLong(HttpServletRequest req, String name) {
    try {
      return Long.parseLong(req.getParameter(name));
    } catch (Exception ex) {
      LOGGER.error("Get parameter long from: " + name + " ERROR: ", ex);
      return null;
    }
  }

  /**
   * Get long parameter.
   *
   * @param req
   * @param name
   * @return
   */
  public static Integer getParameterInt(HttpServletRequest req, String name) {
    try {
      return Integer.parseInt(req.getParameter(name));
    } catch (Exception ex) {
      LOGGER.error("Get parameter long from: " + name + " ERROR: ", ex);
      return null;
    }
  }

  /**
   * Checks if is collection empty.
   *
   * @param collection
   *            the collection
   * @return true, if is collection empty
   */
  private static boolean isCollectionEmpty(Collection<?> collection) {
    if (collection == null || collection.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Checks if is object empty.
   *
   * @param object
   *            the object
   * @return true, if is object empty
   */
  public static boolean isEmpty(Object object) {
    if (object == null) {
      return true;
    } else if (object instanceof String) {
      if (((String) object).trim().length() == 0) {
        return true;
      }
    } else if (object instanceof Collection) {
      return isCollectionEmpty((Collection<?>) object);
    }
    return false;
  }

  /**
   * <p>Escapes the characters in a <code>String</code> to be suitable to pass to
   * an SQL query.</p>
   *
   * <p>For example,
   * <pre>statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" +
   *   StringEscapeUtils.escapeSql("McHale's Navy") +
   *   "'");</pre>
   * </p>
   *
   * <p>At present, this method only turns single-quotes into doubled single-quotes
   * (<code>"McHale's Navy"</code> => <code>"McHale''s Navy"</code>). It does not
   * handle the cases of percent (%) or underscore (_) for use in LIKE clauses.</p>
   *
   * see http://www.jguru.com/faq/view.jsp?EID=8881
   * @param str  the string to escape, may be null
   * @return a new String, escaped for SQL, <code>null</code> if null string input
   */
  public static String escapeSql(String str) {
    if (str == null) {
      return null;
    }
    return str.replace("'", "''");
  }
}
