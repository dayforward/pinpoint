/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.navercorp.pinpoint.thrift.dto;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-07-20")
public class TBussinessLogBatch implements org.apache.thrift.TBase<TBussinessLogBatch, TBussinessLogBatch._Fields>, java.io.Serializable, Cloneable, Comparable<TBussinessLogBatch> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TBussinessLogBatch");

  private static final org.apache.thrift.protocol.TField AGENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("agentId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField START_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("startTimestamp", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField BUSSINESS_LOGS_FIELD_DESC = new org.apache.thrift.protocol.TField("bussinessLogs", org.apache.thrift.protocol.TType.LIST, (short)10);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TBussinessLogBatchStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TBussinessLogBatchTupleSchemeFactory();

  private java.lang.String agentId; // required
  private long startTimestamp; // required
  private java.util.List<TBussinessLog> bussinessLogs; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    AGENT_ID((short)1, "agentId"),
    START_TIMESTAMP((short)2, "startTimestamp"),
    BUSSINESS_LOGS((short)10, "bussinessLogs");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // AGENT_ID
          return AGENT_ID;
        case 2: // START_TIMESTAMP
          return START_TIMESTAMP;
        case 10: // BUSSINESS_LOGS
          return BUSSINESS_LOGS;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __STARTTIMESTAMP_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.AGENT_ID, new org.apache.thrift.meta_data.FieldMetaData("agentId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("startTimestamp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.BUSSINESS_LOGS, new org.apache.thrift.meta_data.FieldMetaData("bussinessLogs", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TBussinessLog.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TBussinessLogBatch.class, metaDataMap);
  }

  public TBussinessLogBatch() {
  }

  public TBussinessLogBatch(
    java.lang.String agentId,
    long startTimestamp,
    java.util.List<TBussinessLog> bussinessLogs)
  {
    this();
    this.agentId = agentId;
    this.startTimestamp = startTimestamp;
    setStartTimestampIsSet(true);
    this.bussinessLogs = bussinessLogs;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TBussinessLogBatch(TBussinessLogBatch other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetAgentId()) {
      this.agentId = other.agentId;
    }
    this.startTimestamp = other.startTimestamp;
    if (other.isSetBussinessLogs()) {
      java.util.List<TBussinessLog> __this__bussinessLogs = new java.util.ArrayList<TBussinessLog>(other.bussinessLogs.size());
      for (TBussinessLog other_element : other.bussinessLogs) {
        __this__bussinessLogs.add(new TBussinessLog(other_element));
      }
      this.bussinessLogs = __this__bussinessLogs;
    }
  }

  public TBussinessLogBatch deepCopy() {
    return new TBussinessLogBatch(this);
  }

  @Override
  public void clear() {
    this.agentId = null;
    setStartTimestampIsSet(false);
    this.startTimestamp = 0;
    this.bussinessLogs = null;
  }

  public java.lang.String getAgentId() {
    return this.agentId;
  }

  public void setAgentId(java.lang.String agentId) {
    this.agentId = agentId;
  }

  public void unsetAgentId() {
    this.agentId = null;
  }

  /** Returns true if field agentId is set (has been assigned a value) and false otherwise */
  public boolean isSetAgentId() {
    return this.agentId != null;
  }

  public void setAgentIdIsSet(boolean value) {
    if (!value) {
      this.agentId = null;
    }
  }

  public long getStartTimestamp() {
    return this.startTimestamp;
  }

  public void setStartTimestamp(long startTimestamp) {
    this.startTimestamp = startTimestamp;
    setStartTimestampIsSet(true);
  }

  public void unsetStartTimestamp() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __STARTTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field startTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetStartTimestamp() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __STARTTIMESTAMP_ISSET_ID);
  }

  public void setStartTimestampIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __STARTTIMESTAMP_ISSET_ID, value);
  }

  public int getBussinessLogsSize() {
    return (this.bussinessLogs == null) ? 0 : this.bussinessLogs.size();
  }

  public java.util.Iterator<TBussinessLog> getBussinessLogsIterator() {
    return (this.bussinessLogs == null) ? null : this.bussinessLogs.iterator();
  }

  public void addToBussinessLogs(TBussinessLog elem) {
    if (this.bussinessLogs == null) {
      this.bussinessLogs = new java.util.ArrayList<TBussinessLog>();
    }
    this.bussinessLogs.add(elem);
  }

  public java.util.List<TBussinessLog> getBussinessLogs() {
    return this.bussinessLogs;
  }

  public void setBussinessLogs(java.util.List<TBussinessLog> bussinessLogs) {
    this.bussinessLogs = bussinessLogs;
  }

  public void unsetBussinessLogs() {
    this.bussinessLogs = null;
  }

  /** Returns true if field bussinessLogs is set (has been assigned a value) and false otherwise */
  public boolean isSetBussinessLogs() {
    return this.bussinessLogs != null;
  }

  public void setBussinessLogsIsSet(boolean value) {
    if (!value) {
      this.bussinessLogs = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case AGENT_ID:
      if (value == null) {
        unsetAgentId();
      } else {
        setAgentId((java.lang.String)value);
      }
      break;

    case START_TIMESTAMP:
      if (value == null) {
        unsetStartTimestamp();
      } else {
        setStartTimestamp((java.lang.Long)value);
      }
      break;

    case BUSSINESS_LOGS:
      if (value == null) {
        unsetBussinessLogs();
      } else {
        setBussinessLogs((java.util.List<TBussinessLog>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case AGENT_ID:
      return getAgentId();

    case START_TIMESTAMP:
      return getStartTimestamp();

    case BUSSINESS_LOGS:
      return getBussinessLogs();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case AGENT_ID:
      return isSetAgentId();
    case START_TIMESTAMP:
      return isSetStartTimestamp();
    case BUSSINESS_LOGS:
      return isSetBussinessLogs();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TBussinessLogBatch)
      return this.equals((TBussinessLogBatch)that);
    return false;
  }

  public boolean equals(TBussinessLogBatch that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_agentId = true && this.isSetAgentId();
    boolean that_present_agentId = true && that.isSetAgentId();
    if (this_present_agentId || that_present_agentId) {
      if (!(this_present_agentId && that_present_agentId))
        return false;
      if (!this.agentId.equals(that.agentId))
        return false;
    }

    boolean this_present_startTimestamp = true;
    boolean that_present_startTimestamp = true;
    if (this_present_startTimestamp || that_present_startTimestamp) {
      if (!(this_present_startTimestamp && that_present_startTimestamp))
        return false;
      if (this.startTimestamp != that.startTimestamp)
        return false;
    }

    boolean this_present_bussinessLogs = true && this.isSetBussinessLogs();
    boolean that_present_bussinessLogs = true && that.isSetBussinessLogs();
    if (this_present_bussinessLogs || that_present_bussinessLogs) {
      if (!(this_present_bussinessLogs && that_present_bussinessLogs))
        return false;
      if (!this.bussinessLogs.equals(that.bussinessLogs))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetAgentId()) ? 131071 : 524287);
    if (isSetAgentId())
      hashCode = hashCode * 8191 + agentId.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(startTimestamp);

    hashCode = hashCode * 8191 + ((isSetBussinessLogs()) ? 131071 : 524287);
    if (isSetBussinessLogs())
      hashCode = hashCode * 8191 + bussinessLogs.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TBussinessLogBatch other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetAgentId()).compareTo(other.isSetAgentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAgentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.agentId, other.agentId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetStartTimestamp()).compareTo(other.isSetStartTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startTimestamp, other.startTimestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBussinessLogs()).compareTo(other.isSetBussinessLogs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBussinessLogs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bussinessLogs, other.bussinessLogs);
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
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TBussinessLogBatch(");
    boolean first = true;

    sb.append("agentId:");
    if (this.agentId == null) {
      sb.append("null");
    } else {
      sb.append(this.agentId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("startTimestamp:");
    sb.append(this.startTimestamp);
    first = false;
    if (!first) sb.append(", ");
    sb.append("bussinessLogs:");
    if (this.bussinessLogs == null) {
      sb.append("null");
    } else {
      sb.append(this.bussinessLogs);
    }
    first = false;
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TBussinessLogBatchStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TBussinessLogBatchStandardScheme getScheme() {
      return new TBussinessLogBatchStandardScheme();
    }
  }

  private static class TBussinessLogBatchStandardScheme extends org.apache.thrift.scheme.StandardScheme<TBussinessLogBatch> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TBussinessLogBatch struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // AGENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.agentId = iprot.readString();
              struct.setAgentIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // START_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.startTimestamp = iprot.readI64();
              struct.setStartTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // BUSSINESS_LOGS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list48 = iprot.readListBegin();
                struct.bussinessLogs = new java.util.ArrayList<TBussinessLog>(_list48.size);
                TBussinessLog _elem49;
                for (int _i50 = 0; _i50 < _list48.size; ++_i50)
                {
                  _elem49 = new TBussinessLog();
                  _elem49.read(iprot);
                  struct.bussinessLogs.add(_elem49);
                }
                iprot.readListEnd();
              }
              struct.setBussinessLogsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TBussinessLogBatch struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.agentId != null) {
        oprot.writeFieldBegin(AGENT_ID_FIELD_DESC);
        oprot.writeString(struct.agentId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(START_TIMESTAMP_FIELD_DESC);
      oprot.writeI64(struct.startTimestamp);
      oprot.writeFieldEnd();
      if (struct.bussinessLogs != null) {
        oprot.writeFieldBegin(BUSSINESS_LOGS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bussinessLogs.size()));
          for (TBussinessLog _iter51 : struct.bussinessLogs)
          {
            _iter51.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TBussinessLogBatchTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TBussinessLogBatchTupleScheme getScheme() {
      return new TBussinessLogBatchTupleScheme();
    }
  }

  private static class TBussinessLogBatchTupleScheme extends org.apache.thrift.scheme.TupleScheme<TBussinessLogBatch> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TBussinessLogBatch struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetAgentId()) {
        optionals.set(0);
      }
      if (struct.isSetStartTimestamp()) {
        optionals.set(1);
      }
      if (struct.isSetBussinessLogs()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetAgentId()) {
        oprot.writeString(struct.agentId);
      }
      if (struct.isSetStartTimestamp()) {
        oprot.writeI64(struct.startTimestamp);
      }
      if (struct.isSetBussinessLogs()) {
        {
          oprot.writeI32(struct.bussinessLogs.size());
          for (TBussinessLog _iter52 : struct.bussinessLogs)
          {
            _iter52.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TBussinessLogBatch struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.agentId = iprot.readString();
        struct.setAgentIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.startTimestamp = iprot.readI64();
        struct.setStartTimestampIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list53 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bussinessLogs = new java.util.ArrayList<TBussinessLog>(_list53.size);
          TBussinessLog _elem54;
          for (int _i55 = 0; _i55 < _list53.size; ++_i55)
          {
            _elem54 = new TBussinessLog();
            _elem54.read(iprot);
            struct.bussinessLogs.add(_elem54);
          }
        }
        struct.setBussinessLogsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

