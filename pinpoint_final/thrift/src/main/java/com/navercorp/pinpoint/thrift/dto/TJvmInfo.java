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
public class TJvmInfo implements org.apache.thrift.TBase<TJvmInfo, TJvmInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TJvmInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TJvmInfo");

  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.I16, (short)1);
  private static final org.apache.thrift.protocol.TField VM_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("vmVersion", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField GC_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("gcType", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TJvmInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TJvmInfoTupleSchemeFactory());
  }

  private short version; // required
  private String vmVersion; // optional
  private TJvmGcType gcType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VERSION((short)1, "version"),
    VM_VERSION((short)2, "vmVersion"),
    /**
     * 
     * @see TJvmGcType
     */
    GC_TYPE((short)3, "gcType");

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
        case 2: // VM_VERSION
          return VM_VERSION;
        case 3: // GC_TYPE
          return GC_TYPE;
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
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.VM_VERSION,_Fields.GC_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.VM_VERSION, new org.apache.thrift.meta_data.FieldMetaData("vmVersion", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.GC_TYPE, new org.apache.thrift.meta_data.FieldMetaData("gcType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TJvmGcType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TJvmInfo.class, metaDataMap);
  }

  public TJvmInfo() {
    this.version = (short)0;

    this.gcType = com.navercorp.pinpoint.thrift.dto.TJvmGcType.UNKNOWN;

  }

  public TJvmInfo(
    short version)
  {
    this();
    this.version = version;
    setVersionIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TJvmInfo(TJvmInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.version = other.version;
    if (other.isSetVmVersion()) {
      this.vmVersion = other.vmVersion;
    }
    if (other.isSetGcType()) {
      this.gcType = other.gcType;
    }
  }

  public TJvmInfo deepCopy() {
    return new TJvmInfo(this);
  }

  @Override
  public void clear() {
    this.version = (short)0;

    this.vmVersion = null;
    this.gcType = com.navercorp.pinpoint.thrift.dto.TJvmGcType.UNKNOWN;

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

  public String getVmVersion() {
    return this.vmVersion;
  }

  public void setVmVersion(String vmVersion) {
    this.vmVersion = vmVersion;
  }

  public void unsetVmVersion() {
    this.vmVersion = null;
  }

  /** Returns true if field vmVersion is set (has been assigned a value) and false otherwise */
  public boolean isSetVmVersion() {
    return this.vmVersion != null;
  }

  public void setVmVersionIsSet(boolean value) {
    if (!value) {
      this.vmVersion = null;
    }
  }

  /**
   * 
   * @see TJvmGcType
   */
  public TJvmGcType getGcType() {
    return this.gcType;
  }

  /**
   * 
   * @see TJvmGcType
   */
  public void setGcType(TJvmGcType gcType) {
    this.gcType = gcType;
  }

  public void unsetGcType() {
    this.gcType = null;
  }

  /** Returns true if field gcType is set (has been assigned a value) and false otherwise */
  public boolean isSetGcType() {
    return this.gcType != null;
  }

  public void setGcTypeIsSet(boolean value) {
    if (!value) {
      this.gcType = null;
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

    case VM_VERSION:
      if (value == null) {
        unsetVmVersion();
      } else {
        setVmVersion((String)value);
      }
      break;

    case GC_TYPE:
      if (value == null) {
        unsetGcType();
      } else {
        setGcType((TJvmGcType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VERSION:
      return Short.valueOf(getVersion());

    case VM_VERSION:
      return getVmVersion();

    case GC_TYPE:
      return getGcType();

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
    case VM_VERSION:
      return isSetVmVersion();
    case GC_TYPE:
      return isSetGcType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TJvmInfo)
      return this.equals((TJvmInfo)that);
    return false;
  }

  public boolean equals(TJvmInfo that) {
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

    boolean this_present_vmVersion = true && this.isSetVmVersion();
    boolean that_present_vmVersion = true && that.isSetVmVersion();
    if (this_present_vmVersion || that_present_vmVersion) {
      if (!(this_present_vmVersion && that_present_vmVersion))
        return false;
      if (!this.vmVersion.equals(that.vmVersion))
        return false;
    }

    boolean this_present_gcType = true && this.isSetGcType();
    boolean that_present_gcType = true && that.isSetGcType();
    if (this_present_gcType || that_present_gcType) {
      if (!(this_present_gcType && that_present_gcType))
        return false;
      if (!this.gcType.equals(that.gcType))
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

    boolean present_vmVersion = true && (isSetVmVersion());
    list.add(present_vmVersion);
    if (present_vmVersion)
      list.add(vmVersion);

    boolean present_gcType = true && (isSetGcType());
    list.add(present_gcType);
    if (present_gcType)
      list.add(gcType.getValue());

    return list.hashCode();
  }

  @Override
  public int compareTo(TJvmInfo other) {
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
    lastComparison = Boolean.valueOf(isSetVmVersion()).compareTo(other.isSetVmVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVmVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vmVersion, other.vmVersion);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGcType()).compareTo(other.isSetGcType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGcType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.gcType, other.gcType);
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
    StringBuilder sb = new StringBuilder("TJvmInfo(");
    boolean first = true;

    sb.append("version:");
    sb.append(this.version);
    first = false;
    if (isSetVmVersion()) {
      if (!first) sb.append(", ");
      sb.append("vmVersion:");
      if (this.vmVersion == null) {
        sb.append("null");
      } else {
        sb.append(this.vmVersion);
      }
      first = false;
    }
    if (isSetGcType()) {
      if (!first) sb.append(", ");
      sb.append("gcType:");
      if (this.gcType == null) {
        sb.append("null");
      } else {
        sb.append(this.gcType);
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

  private static class TJvmInfoStandardSchemeFactory implements SchemeFactory {
    public TJvmInfoStandardScheme getScheme() {
      return new TJvmInfoStandardScheme();
    }
  }

  private static class TJvmInfoStandardScheme extends StandardScheme<TJvmInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TJvmInfo struct) throws org.apache.thrift.TException {
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
          case 2: // VM_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.vmVersion = iprot.readString();
              struct.setVmVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // GC_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.gcType = com.navercorp.pinpoint.thrift.dto.TJvmGcType.findByValue(iprot.readI32());
              struct.setGcTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TJvmInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(VERSION_FIELD_DESC);
      oprot.writeI16(struct.version);
      oprot.writeFieldEnd();
      if (struct.vmVersion != null) {
        if (struct.isSetVmVersion()) {
          oprot.writeFieldBegin(VM_VERSION_FIELD_DESC);
          oprot.writeString(struct.vmVersion);
          oprot.writeFieldEnd();
        }
      }
      if (struct.gcType != null) {
        if (struct.isSetGcType()) {
          oprot.writeFieldBegin(GC_TYPE_FIELD_DESC);
          oprot.writeI32(struct.gcType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TJvmInfoTupleSchemeFactory implements SchemeFactory {
    public TJvmInfoTupleScheme getScheme() {
      return new TJvmInfoTupleScheme();
    }
  }

  private static class TJvmInfoTupleScheme extends TupleScheme<TJvmInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TJvmInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetVersion()) {
        optionals.set(0);
      }
      if (struct.isSetVmVersion()) {
        optionals.set(1);
      }
      if (struct.isSetGcType()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetVersion()) {
        oprot.writeI16(struct.version);
      }
      if (struct.isSetVmVersion()) {
        oprot.writeString(struct.vmVersion);
      }
      if (struct.isSetGcType()) {
        oprot.writeI32(struct.gcType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TJvmInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.version = iprot.readI16();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(1)) {
        struct.vmVersion = iprot.readString();
        struct.setVmVersionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.gcType = com.navercorp.pinpoint.thrift.dto.TJvmGcType.findByValue(iprot.readI32());
        struct.setGcTypeIsSet(true);
      }
    }
  }

}

