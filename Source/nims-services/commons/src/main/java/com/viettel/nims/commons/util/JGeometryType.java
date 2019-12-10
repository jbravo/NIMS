//package com.viettel.nims.commons.util;
//
//import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.Arrays;
//import javax.sql.PooledConnection;
//import lombok.extern.slf4j.Slf4j;
//import oracle.jdbc.OracleConnection;
//import oracle.spatial.geometry.JGeometry;
//import oracle.sql.STRUCT;
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.usertype.UserType;
//
//@Slf4j
//public class JGeometryType implements UserType, Serializable {
//
//  private static final long serialVersionUID = 1L;
//  private static final int[] SQL_TYPES = {Types.OTHER};
//  private JGeometry geometryInstance;
//
//  public JGeometryType() {
//    geometryInstance = new JGeometry(0, 0, 0);
//  }
//
//  public JGeometryType(JGeometry geometryInstance) {
//    this.geometryInstance = geometryInstance;
//  }
//
//  public JGeometryType(JGeometryType geometryType) {
//    this.geometryInstance = geometryType.getJGeometry();
//  }
//
//  public JGeometryType(double minX, double minY, double maxX, double maxY, int srid) {
//    geometryInstance = new JGeometry(minX, minY, maxX, maxY, srid);
//  }
//
//  public JGeometryType(double x, double y, double z, int srid) {
//    geometryInstance = new JGeometry(x, y, z, srid);
//  }
//
//  public JGeometryType(double x, double y, int srid) {
//    geometryInstance = new JGeometry(x, y, srid);
//  }
//
//  public JGeometryType(int gtype, int srid, double x, double y, double z, int[] elemInfo,
//      double[] ordinates) {
//    geometryInstance = new JGeometry(gtype, srid, x, y, z, elemInfo, ordinates);
//  }
//
//  public JGeometryType(int gtype, int srid, int[] elemInfo, double[] ordinates) {
//    geometryInstance = new JGeometry(gtype, srid, elemInfo, ordinates);
//  }
//
//  static public JGeometryType createCircle(double x1, double y1, double x2, double y2, double x3,
//      double y3, int srid) {
//    return new JGeometryType(JGeometry.createCircle(x1, y1, x2, y2, x3, y3, srid));
//  }
//
//  static public JGeometryType createCircle(double x, double y, double radius, int srid) {
//    return new JGeometryType(JGeometry.createCircle(x, y, radius, srid));
//  }
//
//  static public JGeometryType createLinearLineString(double[] coords, int dim, int srid) {
//    return new JGeometryType(JGeometry.createLinearLineString(coords, dim, srid));
//  }
//
//  static public JGeometryType createLinearMultiLineString(Object[] coords, int dim, int srid) {
//    return new JGeometryType(JGeometry.createLinearMultiLineString(coords, dim, srid));
//  }
//
//  static public JGeometryType createLinearPolygon(double[] coords, int dim, int srid) {
//    return new JGeometryType(JGeometry.createLinearPolygon(coords, dim, srid));
//  }
//
//  static public JGeometryType createLinearPolygon(Object[] coords, int dim, int srid) {
//    return new JGeometryType(JGeometry.createLinearPolygon(coords, dim, srid));
//  }
//
//  static public JGeometryType createMultiPoint(Object[] coords, int dim, int srid) {
//    return new JGeometryType(JGeometry.createMultiPoint(coords, dim, srid));
//  }
//
//  static public JGeometryType createPoint(double[] coord, int dim, int srid) {
//    return new JGeometryType(JGeometry.createPoint(coord, dim, srid));
//  }
//
//  static public double[] computeArc(double x1, double y1, double x2, double y2, double x3,
//      double y3) {
//    return JGeometry.computeArc(x1, y1, x2, y2, x3, y3);
//  }
//
//  public static double[] linearizeArc(double x1, double y1, double x2, double y2, double x3,
//      double y3) {
//    return JGeometry.linearizeArc(x1, y1, x2, y2, x3, y3);
//  }
//
//  public static double[] linearizeArc(double x1, double y1, double x2, double y2, double x3,
//      double y3, int numPoints) {
//    return JGeometry.linearizeArc(x1, y1, x2, y2, x3, y3, numPoints);
//  }
//
//  public int[] sqlTypes() {
//    return SQL_TYPES;
//  }
//
//  public Class returnedClass() {
//    return JGeometryType.class;
//  }
//
//  @Override
//  public boolean equals(Object arg0) {
//    return equals(this, arg0);
//  }
//
//  @Override
//  public int hashCode() {
//    return super.hashCode();
//  }
//
//  public boolean equals(Object obj1, Object obj2) throws HibernateException {
//    if (obj1 instanceof JGeometryType && obj2 instanceof JGeometryType) {
//      JGeometry geo1 = ((JGeometryType) obj1).getJGeometry();
//      JGeometry geo2 = ((JGeometryType) obj2).getJGeometry();
//      if (geo1 == null && geo2 == null) {
//        return true;
//      }
//      if (geo1 == null || geo2 == null || geo1.getType() != geo2.getType()) {
//        return false;
//      }
//      switch (geo1.getType()) {
//        case JGeometry.GTYPE_POINT:
//          try {
//            if (geo1.getJavaPoint() != null) {
//              return geo1.getJavaPoint().equals(geo2.getJavaPoint());
//            } else {
//              return false;
//            }
//          } catch (NullPointerException npe) {
//            log.error("NullPointerException", npe);
//            return false;
//          }
//        case JGeometry.GTYPE_MULTIPOINT:
//          try {
//            if (geo1.getJavaPoints() != null) {
//              return Arrays.equals(geo1.getJavaPoints(), geo1.getJavaPoints());
//            }
//            return false;
//          } catch (NullPointerException npe) {
//            log.error("NullPointerException", npe);
//            return false;
//          }
//        case JGeometry.GTYPE_MULTICURVE:
//        case JGeometry.GTYPE_MULTIPOLYGON:
//        case JGeometry.GTYPE_POLYGON:
//        case JGeometry.GTYPE_CURVE:
//          return Arrays.equals(geo1.getOrdinatesOfElements(), geo2.getOrdinatesOfElements());
//        default:
//          return false;
//      }
//    } else {
//      return false;
//    }
//  }
//
//  public int hashCode(Object o) throws HibernateException {
//    return ((JGeometryType) o).hashCode();
//  }
//
//  @Override
//  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
//      Object owner) throws HibernateException, SQLException {
//    STRUCT geometry = (STRUCT) rs.getObject(names[0]);
//    if (geometry != null) {
//      JGeometry jg = JGeometry.load(geometry);
//      return rs.wasNull() ? null : new JGeometryType(jg);
//    } else {
//      return null;
//    }
//  }
//
//  @Override
//  public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i,
//      SharedSessionContractImplementor session) throws HibernateException, SQLException {
//    if (o == null) {
//      preparedStatement.setNull(i, Types.STRUCT, "MDSYS.SDO_GEOMETRY");
//    } else {
//      if (o instanceof JGeometryType) {
//        JGeometryType gt = (JGeometryType) o;
//        final Connection hlp = preparedStatement.getConnection().getMetaData().getConnection();
//        OracleConnection oc = null;
//        if (hlp instanceof OracleConnection) {
//          oc = (OracleConnection) hlp;
//        } else if (hlp instanceof PooledConnection) {
//          oc = (OracleConnection) ((PooledConnection) hlp).getConnection();
//        }
//        if (gt.getJGeometry() == null) {
//          preparedStatement.setNull(i, Types.STRUCT, "MDSYS.SDO_GEOMETRY");
//        } else {
//          preparedStatement.setObject(i, JGeometry.store((JGeometry) (gt).getJGeometry(), oc));
//        }
//      }
//    }
//  }
//
//  public Object deepCopy(Object o) throws HibernateException {
//    if (o == null) {
//      return null;
//    }
//    if (o instanceof JGeometryType) {
//      return new JGeometryType(((JGeometryType) o).getJGeometry());
//    } else {
//      return null;
//    }
//  }
//
//  public boolean isMutable() {
//    return false;
//  }
//
//  public Serializable disassemble(Object o) throws HibernateException {
//    return (Serializable) deepCopy(o);
//  }
//
//  public Object assemble(Serializable serializable, Object o) throws HibernateException {
//    return deepCopy(serializable);
//  }
//
//  public Object replace(Object o, Object o1, Object o2) throws HibernateException {
//    return (JGeometryType) o;
//  }
//
//  public JGeometry getJGeometry() {
//    return geometryInstance;
//  }
//
//  public java.awt.Shape createShape() {
//    return geometryInstance.createShape();
//  }
//
//  public int getDimensions() {
//    return geometryInstance.getDimensions();
//  }
//
//  public int[] getElemInfo() {
//    return geometryInstance.getElemInfo();
//  }
//
//  public double[] getFirstPoint() {
//    return geometryInstance.getFirstPoint();
//  }
//
//  public java.awt.geom.Point2D getJavaPoint() {
//    return geometryInstance.getJavaPoint();
//  }
//
//  public java.awt.geom.Point2D[] getJavaPoints() {
//    return geometryInstance.getJavaPoints();
//  }
//
//  public java.awt.geom.Point2D getLabelPoint() {
//    return geometryInstance.getLabelPoint();
//  }
//
//  public double[] getLastPoint() {
//    return geometryInstance.getLastPoint();
//  }
//
//  public double[] getMBR() {
//    return geometryInstance.getMBR();
//  }
//
//  public int getNumPoints() {
//    return geometryInstance.getNumPoints();
//  }
//
//  public double[] getOrdinatesArray() {
//    return geometryInstance.getOrdinatesArray();
//  }
//
//  public Object[] getOrdinatesOfElements() {
//    return geometryInstance.getOrdinatesOfElements();
//  }
//
//  public double[] getPoint() {
//    return geometryInstance.getPoint();
//  }
//
//  public long getSize() {
//    return geometryInstance.getSize();
//  }
//
//  public int getSRID() {
//    return geometryInstance.getSRID();
//  }
//
//  public void setSRID(int srid) {
//    geometryInstance.setSRID(srid);
//  }
//
//  public int getType() {
//    return geometryInstance.getType();
//  }
//
//  public void setType(int gt) {
//    geometryInstance.setType(gt);
//  }
//
//  public boolean hasCircularArcs() {
//    return geometryInstance.hasCircularArcs();
//  }
//
//  public boolean isCircle() {
//    return geometryInstance.isCircle();
//  }
//
//  public boolean isGeodeticMBR() {
//    return geometryInstance.isGeodeticMBR();
//  }
//
//  public boolean isLRSGeometry() {
//    return geometryInstance.isLRSGeometry();
//  }
//
//  public boolean isMultiPoint() {
//    return geometryInstance.isMultiPoint();
//  }
//
//  public boolean isPoint() {
//    return geometryInstance.isPoint();
//  }
//
//  public boolean isRectangle() {
//    return geometryInstance.isRectangle();
//  }
//}
