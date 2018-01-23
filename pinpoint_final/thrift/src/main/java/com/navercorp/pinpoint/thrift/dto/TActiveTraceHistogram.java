/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.navercorp.pinpoint.thrift.dto;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-24")
public class TActiveTraceHistogram implements org.apache.thrift.TBase<TActiveTraceHistogram, TActiveTraceHistogram._Fields>, java.io.Serializable, Cloneable, Comparable<TActiveTraceHistogram> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TActiveTraceHistogram");

  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.I16, (short)1);
  private static final org.apache.thrift.protocol.TField HISTOGRAM_SCHEMA_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("histogramSchemaType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ACTIVE_TRACE_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("activeTraceCount", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TActiveTraceHistogramStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TActiveTraceHistogramTupleSchemeFactory());
  }

  private short version; // required
  private int histogramSchemaType; // optional
  private List<Integer> activeTraceCount; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VERSION((short)1, "version"),
    HISTOGRAM_SCHEMA_TYPE((short)2, "histogramSchemaType"),
    ACTIVE_TRACE_COUNT((short)3, "activeTraceCount");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // VERSION
          return VERSION;
        case 2: // HISTOGRAM_SCHEMA_TYPE
          return HISTOGRAM_SCHEMA_TYPE;
        case 3: // ACTIVE_TRACE_COUNT
          return ACTIVE_TRACE_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VERSION_ISSET_ID = 0;
  private static final int __HISTOGRAMSCHEMATYPE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.HISTOGRAM_SCHEMA_TYPE,_Fields.ACTIVE_TRACE_COUNT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.HISTOGRAM_SCHEMA_TYPE, new org.apache.thrift.meta_data.FieldMetaData("histogramSchemaType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACTIVE_TRACE_COUNT, new org.apache.thrift.meta_data.FieldMetaData("activeTraceCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TActiveTraceHistogram.class, metaDataMap);
  }

  public TActiveTraceHistogram() {
    this.version = (short)0;

  }

  public TActiveTraceHistogram(
    short version)
  {
    this();
    this.version = version;
    setVersionIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TActiveTraceHistogram(TActiveTraceHistogram other) {
    __isset_bitfield = other.__isset_bitfield;
    this.version = other.version;
    this.histogramSchemaType = other.histogramSchemaType;
    if (other.isSetActiveTraceCount()) {
      List<Integer> __this__activeTraceCount = new ArrayList<Integer>(other.activeTraceCount);
      this.activeTraceCount = __this__activeTraceCount;
    }
  }

  public TActiveTraceHistogram deepCopy() {
    return new TActiveTraceHistogram(this);
  }

  @Override
  public void clear() {
    this.version = (short)0;

    setHistogramSchemaTypeIsSet(false);
    this.histogramSchemaType = 0;
    this.activeTraceCount = null;
  }

  public short getVersion() {
    return this.version;
  }

  public void setVersion(short version) {
    this.version = version;
    setVersionIsSet(true);
  }

  public void unsetVersion() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VERSION_ISSET_ID);
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return EncodingUtils.testBit(__isset_bitfield, __VERSION_ISSET_ID);
  }

  public void setVersionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VERSION_ISSET_ID, value);
  }

  public int getHistogramSchemaType() {
    return this.histogramSchemaType;
  }

  public void setHistogramSchemaType(int histogramSchemaType) {
    this.histogramSchemaType = histogramSchemaType;
    setHistogramSchemaTypeIsSet(true);
  }

  public void unsetHistogramSchemaType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HISTOGRAMSCHEMATYPE_ISSET_ID);
  }

  /** Returns true if field histogramSchemaType is set (has been assigned a value) and false otherwise */
  public boolean isSetHistogramSchemaType() {
    return EncodingUtils.testBit(__isset_bitfield, __HISTOGRAMSCHEMATYPE_ISSET_ID);
  }

  public void setHistogramSchemaTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HISTOGRAMSCHEMATYPE_ISSET_ID, value);
  }

  public int getActiveTraceCountSize() {
    return (this.activeTraceCount == null) ? 0 : this.activeTraceCount.size();
  }

  public java.util.Iterator<Integer> getActiveTraceCountIterator() {
    return (this.activeTraceCount == null) ? null : this.activeTraceCount.iterator();
  }

  public void addToActiveTraceCount(int elem) {
    if (this.activeTraceCount == null) {
      this.activeTraceCount = new ArrayList<Integer>();
    }
    this.activeTraceCount.add(elem);
  }

  public List<Integer> getActiveTraceCount() {
    return this.activeTraceCount;
  }

  public void setActiveTraceCount(List<Integer> activeTraceCount) {
    this.activeTraceCount = activeTraceCount;
  }

  public void unsetActiveTraceCount() {
    this.activeTraceCount = null;
  }

  /** Returns true if field activeTraceCount is set (has been assigned a value) and false otherwise */
  public boolean isSetActiveTraceCount() {
    return this.activeTraceCount != null;
  }

  public void setActiveTraceCountIsSet(boolean value) {
    if (!value) {
      this.activeTraceCount = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((Short)value);
      }
      break;

    case HISTOGRAM_SCHEMA_TYPE:
      if (value == null) {
        unsetHistogramSchemaType();
      } else {
        setHistogramSchemaType((Integer)value);
      }
      break;

    case ACTIVE_TRACE_COUNT:
      if (value == null) {
        unsetActiveTraceCount();
      } else {
        setActiveTraceCount((List<Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VERSION:
      return Short.valueOf(getVersion());

    case HISTOGRAM_SCHEMA_TYPE:
      return Integer.valueOf(getHistogramSchemaType());

    case ACTIVE_TRACE_COUNT:
      return getActiveTraceCount();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VERSION:
      return isSetVersion();
    case HISTOGRAM_SCHEMA_TYPE:
      return isSetHistogramSchemaType();
    case ACTIVE_TRACE_COUNT:
      return isSetActiveTraceCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TActiveTraceHistogram)
      return this.equals((TActiveTraceHistogram)that);
    return false;
  }

  public boolean equals(TActiveTraceHistogram that) {
    if (that == null)
      return false;

    boolean this_present_version = true;
    boolean that_present_version = true;
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (this.version != that.version)
        return false;
    }

    boolean this_present_histogramSchemaType = true && this.isSetHistogramSchemaType();
    boolean that_present_histogramSchemaType = true && that.isSetHistogramSchemaType();
    if (this_present_histogramSchemaType || that_present_histogramSchemaType) {
      if (!(this_present_histogramSchemaType && that_present_histogramSchemaType))
        return false;
      if (this.histogramSchemaType != that.histogramSchemaType)
        return false;
    }

    boolean this_present_activeTraceCount = true && this.isSetActiveTraceCount();
    boolean that_present_activeTraceCount = true && that.isSetActiveTraceCount();
    if (this_present_activeTraceCount || that_present_activeTraceCount) {
      if (!(this_present_activeTraceCount && that_present_activeTraceCount))
        return false;
      if (!this.activeTraceCount.equals(that.activeTraceCount))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_version = true;
    list.add(present_version);
    if (present_version)
      list.add(version);

    boolean present_histogramSchemaType = true && (isSetHistogramSchemaType());
    list.add(present_histogramSchemaType);
    if (present_histogramSchemaType)
      list.add(histogramSchemaType);

    boolean present_activeTraceCount = true && (isSetActiveTraceCount());
    list.add(present_activeTraceCount);
    if (present_activeTraceCount)
      list.add(activeTraceCount);

    return list.hashCode();
  }

  @Override
  public int compareTo(TActiveTraceHistogram other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHistogramSchemaType()).compareTo(other.isSetHistogramSchemaType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHistogramSchemaType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.histogramSchemaType, other.histogramSchemaType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActiveTraceCount()).compareTo(other.isSetActiveTraceCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiveTraceCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activeTraceCount, other.activeTraceCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TActiveTraceHistogram(");
    boolean first = true;

    sb.append("version:");
    sb.append(this.version);
    first = false;
    if (isSetHistogramSchemaType()) {
      if (!first) sb.append(", ");
      sb.append("histogramSchemaType:");
      sb.append(this.histogramSchemaType);
      first = false;
    }
    if (isSetActiveTraceCount()) {
      if (!first) sb.append(", ");
      sb.append("activeTraceCount:");
      if (this.activeTraceCount == null) {
        sb.append("null");
      } else {
        sb.append(this.activeTraceCount);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TActiveTraceHistogramStandardSchemeFactory implements SchemeFactory {
    public TActiveTraceHistogramStandardScheme getScheme() {
      return new TActiveTraceHistogramStandardScheme();
    }
  }

  private static class TActiveTraceHistogramStandardScheme extends StandardScheme<TActiveTraceHistogram> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TActiveTraceHistogram struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.version = iprot.readI16();
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HISTOGRAM_SCHEMA_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.histogramSchemaType = iprot.readI32();
              struct.setHistogramSchemaTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ACTIVE_TRACE_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.activeTraceCount = new ArrayList<Integer>(_list24.size);
                int _elem25;
                for (int _i26 = 0; _i26 < _list24.size; ++_i26)
                {
                  _elem25 = iprot.readI32();
                  struct.activeTraceCount.add(_elem25);
                }
                iprot.readListEnd();
              }
              struct.setActiveTraceCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TActiveTraceHistogram struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(VERSION_FIELD_DESC);
      oprot.writeI16(struct.version);
      oprot.writeFieldEnd();
      if (struct.isSetHistogramSchemaType()) {
        oprot.writeFieldBegin(HISTOGRAM_SCHEMA_TYPE_FIELD_DESC);
        oprot.writeI32(struct.histogramSchemaType);
        oprot.writeFieldEnd();
      }
      if (struct.activeTraceCount != null) {
        if (struct.isSetActiveTraceCount()) {
          oprot.writeFieldBegin(ACTIVE_TRACE_COUNT_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.activeTraceCount.size()));
            for (int _iter27 : struct.activeTraceCount)
            {
              oprot.writeI32(_iter27);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TActiveTraceHistogramTupleSchemeFactory implements SchemeFactory {
    public TActiveTraceHistogramTupleScheme getScheme() {
      return new TActiveTraceHistogramTupleScheme();
    }
  }

  private static class TActiveTraceHistogramTupleScheme extends TupleScheme<TActiveTraceHistogram> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TActiveTraceHistogram struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetVersion()) {
        optionals.set(0);
      }
      if (struct.isSetHistogramSchemaType()) {
        optionals.set(1);
      }
      if (struct.isSetActiveTraceCount()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetVersion()) {
        oprot.writeI16(struct.version);
      }
      if (struct.isSetHistogramSchemaType()) {
        oprot.writeI32(struct.histogramSchemaType);
      }
      if (struct.isSetActiveTraceCount()) {
        {
          oprot.writeI32(struct.activeTraceCount.size());
          for (int _iter28 : struct.activeTraceCount)
          {
            oprot.writeI32(_iter28);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TActiveTraceHistogram struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.version = iprot.readI16();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(1)) {
        struct.histogramSchemaType = iprot.readI32();
        struct.setHistogramSchemaTypeIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.activeTraceCount = new ArrayList<Integer>(_list29.size);
          int _elem30;
          for (int _i31 = 0; _i31 < _list29.size; ++_i31)
          {
            _elem30 = iprot.readI32();
            struct.activeTraceCount.add(_elem30);
          }
        }
        struct.setActiveTraceCountIsSet(true);
      }
    }
  }

}

