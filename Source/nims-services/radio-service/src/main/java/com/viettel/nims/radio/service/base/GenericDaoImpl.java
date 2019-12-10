package com.viettel.nims.radio.service.base;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

@Log4j2
@Repository
public abstract class GenericDaoImpl<T, PK extends Serializable> implements
    GenericDaoService<T, PK> {

  @Autowired
  protected
  EntityManager entityManager;
  private static final String EXAC = "EXAC";
  private static final String EXAC_IGNORE_CASE = "EXAC_IGNORE_CASE";
  private static final String EXAC_IGNORE_CASE2 = "EXAC_IGNORE_CASE2";
  private static final String GE = "GE";
  private static final String GT = "GT";
  private static final String LE = "LE";
  private static final String LT = "LT";
  private static final String ISNOTNULL = "ISNOTNULL";
  private static final String ISNULL = "ISNULL";
  public static final String NEQ = "NEQ";
  private String pkName = "id";
  protected Class<T> domainClass = getDomainClass();

  public GenericDaoImpl() {
    super();
    try {
      java.lang.reflect.Type genericSuperclass = getClass().getGenericSuperclass();
      this.domainClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    Field[] fields = this.domainClass.getDeclaredFields();
    for (Field f : fields) {
      if (f.isAnnotationPresent(Id.class)) {
        this.pkName = f.getName();
      }
    }
    Assert.notNull(pkName, "[Assertion failed] - Argument pkName is required; it must not be null");
    Assert.notNull(domainClass,"[Assertion failed] - Argument domainClass is required; it must not be null");
  }

  @Override
  public T findById(PK id) {
    return entityManager.find(domainClass, id);
  }

  @Override
  @Transactional
  public void delete(T object) {
    entityManager.remove(object);
  }

  @Override
  @Transactional
  public void delete(List<T> objects) {
    for (Iterator<T> i$ = objects.iterator(); i$.hasNext(); ) {
      Object object = i$.next();
      entityManager.remove(object);
    }
  }

  @Override
  public T saveOrUpdate(T object) throws Exception{
    Transaction tx = null;
    try (Session session = entityManager.unwrap(Session.class)) {
      tx = session.beginTransaction();
      session.saveOrUpdate(object);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      log.error(e.getMessage(), e);
      throw new Exception();
    }
    return object;
  }

  @Override
  public PK save(T object) throws Exception {
    Transaction tx = null;
    Serializable o;
    try (Session session = entityManager.unwrap(Session.class)) {
      tx = session.beginTransaction();
      o = session.save(object);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      log.error(e.getMessage(), e);
      throw new Exception();
    }
    return (PK) o;
  }

  @Override
  public void saveOrUpdate(List<T> objects) throws Exception{
    Transaction tx = null;
    try (Session session = entityManager.unwrap(Session.class)) {
      tx = session.beginTransaction();
      for (T object : objects) {
        session.saveOrUpdate(object);
      }
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      log.error(e.getMessage(), e);
      throw new Exception();
    }
  }

  @Override
  public int count() {
    CriteriaBuilder qb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    cq.select(qb.count(cq.from(domainClass)));
    return entityManager.createQuery(cq).getSingleResult().intValue();
  }

  @Override
  public int count(Map<String, Object> filters, Map<String, Object> sqlRes) throws Exception {
    Session session = null;
    try {
      session = entityManager.unwrap(Session.class);
      Criteria criteria = session.createCriteria(getDomainClass());
      criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
      setCriteriaRestrictions2(criteria, filters);
      setCriteriaRestrictions3(criteria, sqlRes);
      criteria.setProjection(Projections.rowCount());
      int count = 0;
      try {
        count = ((Number) criteria.uniqueResult()).intValue();
      } catch (Exception e) {
        throw e;
      }
      return count;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public List<T> findList() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(domainClass);
    Root<T> rootEntry = cq.from(domainClass);
    CriteriaQuery<T> all = cq.select(rootEntry);
    TypedQuery<T> allQuery = entityManager.createQuery(all);
    return allQuery.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> findList(int first, int pageSize, Map<String, Object> filters,
      Map<String, Object> sqlRes, LinkedHashMap<String, String> orders) throws Exception {
    Session session = null;
    List<T> objects;
    try {
      session = entityManager.unwrap(Session.class);
      Criteria criteria = session.createCriteria(getDomainClass());
      criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
      setCriteriaRestrictions2(criteria, filters);
      setCriteriaRestrictions3(criteria, sqlRes);
      setCriteriaOrders(criteria, orders);
      if (first != -1) {
        criteria.setFirstResult(first);
      }
      if (pageSize != -1) {
        criteria.setMaxResults(pageSize);
      }
      objects = (List<T>) criteria.list();
    } catch (HibernateException | ParseException e) {
      log.error(e.getMessage(), e);
      throw e;
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return objects;
  }

  protected Map<String, String> getFields() {
    Map<String, String> result = new HashMap<>();
    try {
      PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(this.domainClass)
          .getPropertyDescriptors();

      String fieldName;
      String fieldType;
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        try {
          fieldName = propertyDescriptor.getName();
          if (propertyDescriptor.getPropertyType() == null) {
            continue;
          }
          fieldType = propertyDescriptor.getPropertyType().getCanonicalName();
          if ("byte[]".equalsIgnoreCase(fieldType)) {
            continue;
          } else if ("long".equalsIgnoreCase(fieldType)) {
            result.put(fieldName, "java.lang.Long");
          } else if ("boolean".equalsIgnoreCase(fieldType)) {
            result.put(fieldName, "java.lang.Boolean");
          } else if ("int".equalsIgnoreCase(fieldType)) {
            result.put(fieldName, "java.lang.Integer");
          } else if (ClassUtils.isPrimitiveOrWrapper(Class.forName(fieldType))) {
            result.put(fieldName, fieldType);
          } else if ("java.lang.String".equalsIgnoreCase(fieldType)) {
            result.put(fieldName, fieldType);
          } else if ("java.util.Date".equalsIgnoreCase(fieldType)) {
            result.put(fieldName, fieldType);
          } else if (!"java.lang.Class".equalsIgnoreCase(fieldType)) {
            result = getSubField(fieldName, fieldType, result);
          }
        } catch (ClassNotFoundException e) {
          log.error(e.getMessage(), e);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return result;
  }

  private Map<String, String> getSubField(
      String fieldName, String fieldType, Map<String, String> result) {
    try {
      PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Class.forName(fieldType))
          .getPropertyDescriptors();

      String subFieldName;
      String subFieldType;
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        subFieldName = fieldName.concat("." + propertyDescriptor.getName());
        if (propertyDescriptor.getPropertyType() == null) {
          continue;
        }
        subFieldType = propertyDescriptor.getPropertyType().getCanonicalName();
        try {
          if ("byte[]".equalsIgnoreCase(subFieldType)) {
            continue;
          } else if ("long".equalsIgnoreCase(subFieldType)) {
            result.put(subFieldName, "java.lang.Long");
          } else if ("boolean".equalsIgnoreCase(subFieldType)) {
            result.put(subFieldName, "java.lang.Boolean");
          } else if ("int".equalsIgnoreCase(subFieldType)) {
            result.put(subFieldName, "java.lang.Integer");
          } else if (ClassUtils.isPrimitiveOrWrapper(Class.forName(subFieldType))) {
            result.put(subFieldName, subFieldType);
          } else if ("java.lang.String".equalsIgnoreCase(subFieldType)) {
            result.put(subFieldName, subFieldType);
          } else if ("java.util.Date".equalsIgnoreCase(subFieldType)) {
            result.put(subFieldName, subFieldType);
          } else if (!"java.lang.Class".equalsIgnoreCase(subFieldType)) {
            result = getSubField(subFieldName, subFieldType, result);
          }
        } catch (ClassNotFoundException ex) {
          log.error(ex.getMessage(), ex);
        }
      }
    } catch (ClassNotFoundException | IntrospectionException e) {
      log.error(e.getMessage(), e);
    }

    return result;
  }

  protected Class<T> getDomainClass() {
    return this.domainClass;
  }


  protected void setCriteriaRestrictions2(
      Criteria criteria, Map<String, ?> filters) throws ParseException {
    if (filters == null) {
      return;
    }
    Map<String, String> properties = getFields();
    String type;
    String colFieldName;
    Object fieldValue;
    for (Iterator<String> itFilter = filters.keySet().iterator(); itFilter.hasNext(); ) {
      colFieldName = itFilter.next();
      String[] colFieldNames = colFieldName.split("-", -1);
      String fieldName = colFieldNames[0];
      String expr = "";
      if (colFieldNames.length == 2) {
        expr = colFieldNames[1];
      }
      fieldValue = filters.get(colFieldName);

      if (fieldName.contains(".")) {
        if (fieldName.startsWith(pkName + ".")) {

        } else {
          String alias = fieldName.split("\\.", -1)[0];
          if (!criteria.getAlias().contains(alias)) {
            criteria.createAlias(alias, alias + "-alias");
          }
          fieldName = fieldName.replaceAll("^" + alias, alias + "-alias");
        }
      }
      type = properties.get(colFieldNames[0]);
      if (type == null) {
        throw new IllegalArgumentException(
            "Không tìm thấy kiểu dữ liệu của trường tìm kiếm: " + fieldName);
      }
      switch (type) {
        case "java.lang.String":
          switch (expr) {
            case EXAC:
              if (fieldValue instanceof Object[]) {
                criteria.add(Restrictions.in(fieldName, (Object[]) fieldValue));
              } else if (fieldValue instanceof List<?>) {
                criteria.add(Restrictions.in(fieldName, (List<?>) fieldValue));
              } else if (fieldValue instanceof String) {
                criteria.add(Restrictions.eq(fieldName, (fieldValue)));
              }
              break;
            case EXAC_IGNORE_CASE:
              if (fieldValue instanceof Object[]) {
                fieldValue = Arrays.asList(fieldValue);
              }
              if (fieldValue instanceof List<?>) {
                List<Criterion> cers = new ArrayList<>();
                for (Object value : (List<?>) fieldValue) {
                  if (value instanceof String) {
                    Criterion cer = Restrictions
                        .ilike(fieldName, value.toString().trim());
                    cers.add(cer);
                  }
                }
                criteria.add(Restrictions.or(cers.toArray(new Criterion[cers.size()])));
              } else if (fieldValue instanceof String) {
                criteria.add(Restrictions.ilike(fieldName, (fieldValue).toString().trim()));
              }
              break;
            case EXAC_IGNORE_CASE2:
              if (fieldValue instanceof Object[]) {
                criteria.add(Restrictions.in(fieldName, (Object[]) fieldValue));
              } else if (fieldValue instanceof List<?>) {
                criteria.add(Restrictions.in(fieldName, (List<?>) fieldValue));
              } else if (fieldValue instanceof String) {
                criteria.add(Restrictions.eq(fieldName, (fieldValue)).ignoreCase());
              }
              break;
            case NEQ:
              if (fieldValue instanceof String) {
                criteria.add(Restrictions.ne(fieldName, (fieldValue)));
              } else if (fieldValue instanceof Object[]) {
                criteria.add(Restrictions.not(Restrictions.in(fieldName, (Object[]) fieldValue)));
              } else if (fieldValue instanceof List<?>) {
                criteria.add(Restrictions.not(Restrictions.in(fieldName, (List<?>) fieldValue)));
              }else if (fieldValue == null) {
                criteria.add(Restrictions.isNotNull(fieldName));
              }
              break;
            default:
              criteria.add(Restrictions
                  .ilike(fieldName, ((String) fieldValue).toLowerCase().trim(),
                      MatchMode.ANYWHERE));
              break;
          }
          break;
        case "java.lang.Integer":
        case "java.lang.Long":
        case "java.lang.Double":
        case "java.lang.Boolean":
        case "java.lang.Short":
          if (Arrays.asList(new String[]{LT, GT, LE, GE}).contains(expr)) {
            if (fieldValue instanceof String) {
              Number a = NumberFormat.getInstance().parse((String) fieldValue);
              if ("java.lang.Integer".equals(type)) {
                fieldValue = a.intValue();
              } else {
                fieldValue = a;
              }
            }
          } else {
            if (fieldValue instanceof String) {
              if ("java.lang.Boolean".equals(type)) {
                fieldValue = new Boolean((String) fieldValue);
              } else {
                Number a = NumberFormat.getInstance().parse((String) fieldValue);
                switch (type) {
                  case "java.lang.Integer":
                    fieldValue = a.intValue();
                    break;
                  case "java.lang.Short":
                    fieldValue = a.shortValue();
                    break;
                  case "java.lang.Long":
                    fieldValue = a.longValue();
                    break;
                }
              }
            } else if (fieldValue instanceof List<?>) {
              List<Object> fieldValueConverted = new ArrayList<>();
              for (Object value : (List<?>) fieldValue) {
                if (value instanceof String) {
                  Number value2 = NumberFormat.getInstance().parse((String) value);
                  if ("java.lang.Integer".equals(type)) {
                    fieldValueConverted.add(value2.intValue());
                  } else {
                    fieldValueConverted.add(value2);
                  }
                } else {
                  fieldValueConverted.add(value);
                }
              }
              fieldValue = fieldValueConverted;
            } else if (fieldValue instanceof String[]) {
              List<Object> fieldValueConverted = new ArrayList<>();
              for (Object value : (String[]) fieldValue) {
                if (value instanceof String) {
                  Number value2 = NumberFormat.getInstance().parse((String) value);
                  if ("java.lang.Integer".equals(type)) {
                    fieldValueConverted.add(value2.intValue());
                  } else {
                    fieldValueConverted.add(value2);
                  }
                }
              }
              fieldValue = fieldValueConverted;
            }
          }
          switch (expr) {
            case LT:
              if (fieldValue instanceof Number) {
                criteria.add(Restrictions.lt(fieldName, (fieldValue)));
              }
              break;
            case GT:
              if (fieldValue instanceof Number) {
                criteria.add(Restrictions.gt(fieldName, (fieldValue)));
              }
              break;
            case LE:
              if (fieldValue instanceof Number) {
                criteria.add(Restrictions.le(fieldName, (fieldValue)));
              }
              break;
            case GE:
              if (fieldValue instanceof Number) {
                criteria.add(Restrictions.ge(fieldName, (fieldValue)));
              }
              break;
            case NEQ:
              if (fieldValue instanceof Number) {
                criteria.add(Restrictions.ne(fieldName, (fieldValue)));
              } else if (fieldValue == null) {
                criteria.add(Restrictions.isNotNull(fieldName));
              }
              break;
            case ISNOTNULL:
              criteria.add(Restrictions.isNotNull(fieldName));
              break;
            case ISNULL:
              criteria.add(Restrictions.isNull(fieldName));
              break;
            case EXAC:
            default:
              if (fieldValue instanceof Object[]) {
                criteria.add(Restrictions.in(fieldName, (Object[]) fieldValue));
              } else if (fieldValue instanceof List<?>) {
                criteria.add(Restrictions.in(fieldName, (List<?>) fieldValue));
              } else if (fieldValue instanceof Number) {
                criteria.add(Restrictions.eq(fieldName, (fieldValue)));
              } else if (fieldValue instanceof Boolean) {
                criteria.add(Restrictions.eq(fieldName, (fieldValue)));
              }
              break;
          }
          break;
        case "java.util.Date":
          if (fieldValue instanceof String) {
            String fieldValue2 = ((String) fieldValue).trim();
            Pattern.compile("\\s{2}").matcher(fieldValue2).replaceAll("\\s");
            Pattern.compile("\\s-\\s").matcher(fieldValue2).replaceAll("-");
            String[] times = fieldValue2.split("-", -1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH");
            SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
            Date startTime = null;
            Date endTime = null;
            if (!"".equals(times[0].trim())) {
              if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")
                  .matcher(times[0].trim()).matches()) {
                startTime = formatter.parse(times[0].trim());
              } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}")
                  .matcher(times[0].trim()).matches()) {
                startTime = formatter1.parse(times[0].trim());
              } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}")
                  .matcher(times[0].trim()).matches()) {
                startTime = formatter2.parse(times[0].trim());
              } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}").matcher(times[0].trim())
                  .matches()) {
                startTime = formatter3.parse(times[0].trim());
              }
            }
            if (times.length >= 2) {
              if (!"".equals(times[1].trim())) {
                if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")
                    .matcher(times[1].trim()).matches()) {
                  endTime = formatter.parse(times[1].trim());
                } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}")
                    .matcher(times[1].trim()).matches()) {
                  endTime = formatter1.parse(times[1].trim());
                } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}")
                    .matcher(times[1].trim()).matches()) {
                  endTime = formatter2.parse(times[1].trim());
                } else if (Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}").matcher(times[1].trim())
                    .matches()) {
                  endTime = formatter3.parse(times[1].trim());
                }
              }
            }
            fieldValue = new Date[]{startTime, endTime};
          }
          if (fieldValue instanceof List<?>) {
            if (((List<?>) fieldValue).size() > 0 && ((List<?>) fieldValue)
                .get(0) instanceof Date) {
              fieldValue = ((List<?>) fieldValue).toArray(new Date[((List<?>) fieldValue).size()]);
            }
          }
          if (fieldValue instanceof Date[]) {
            Date startTime = null;
            Date endTime = null;
            if (((Date[]) fieldValue).length == 2) {
              if (((Date[]) fieldValue)[0] != null && ((Date[]) fieldValue)[0] instanceof Date) {
                startTime = ((Date[]) fieldValue)[0];
              }
              if (((Date[]) fieldValue)[1] != null && ((Date[]) fieldValue)[1] instanceof Date) {
                endTime = ((Date[]) fieldValue)[1];
              }
            }
            if (startTime != null && endTime != null) {
              criteria.add(Restrictions.between(fieldName, startTime, endTime));

            } else if (startTime != null && endTime == null) {
              if (expr != null && expr.equals(GT)) {
                criteria.add(Restrictions.gt(fieldName, startTime));
              } else {
                criteria.add(Restrictions.ge(fieldName, startTime));
              }

            } else if (startTime == null && endTime != null) {
              if (expr != null && expr.equals(LT)) {
                criteria.add(Restrictions.lt(fieldName, endTime));
              } else {
                criteria.add(Restrictions.le(fieldName, endTime));
              }
            }
          } else if (fieldValue instanceof Date) {
            criteria.add(Restrictions.eq(fieldName, new Timestamp(((Date) fieldValue).getTime())));
          }
          break;
      }
    }
  }

  /**
   * @author tuevc Bo sung sql restriction
   */
  protected void setCriteriaRestrictions3(
      Criteria criteria, Map<String, ?> sqlRes) {
    if (sqlRes == null) {
      return;
    }
    Object value;
    for (Entry<String, ?> entry : sqlRes.entrySet()) {
      String sql = entry.getKey();
      value = entry.getValue();
      if (value instanceof Number) {
        criteria.add(Restrictions.sqlRestriction(sql, value, StandardBasicTypes.LONG));
      } else if (value instanceof String) {
        criteria.add(Restrictions.sqlRestriction(sql, value, StandardBasicTypes.TEXT));
      } else if (value instanceof Date) {
        criteria.add(Restrictions.sqlRestriction(sql, value, StandardBasicTypes.DATE));
      } else if (value == null){
        criteria.add(Restrictions.sqlRestriction(sql));
      }
    }
  }

  protected Criteria setCriteriaOrders(Criteria criteria, Map<String, String> orders) {
    if (orders == null) {
      return criteria;
    }
    String propertyName;
    String orderType;
    for (Iterator<String> it = orders.keySet().iterator(); it.hasNext(); ) {
      propertyName = it.next();
      orderType = orders.get(propertyName);
      switch (orderType.toUpperCase()) {
        case "ASC":
          criteria.addOrder(Order.asc(propertyName));
          break;
        case "DESC":
          criteria.addOrder(Order.desc(propertyName));
          break;
        default:
          criteria.addOrder(Order.asc(propertyName));
      }
    }
    return criteria;
  }
}
