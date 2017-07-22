package com.navercorp.pinpoint.common.server.bo.stat;

public enum BussinessLogType {
	UNKNOWN(0, "Unknown"),
	BUSSINESS_LOG_V1((byte) 1, "bussiness_log_v1");
	
	public static final int TYPE_CODE_BYTE_LENGTH = 1;

    private final byte typeCode;
    private final String name;

    BussinessLogType(int typeCode, String name) {
        if (typeCode < 0 || typeCode > 255) {
            throw new IllegalArgumentException("type code out of range (0~255)");
        }
        this.typeCode = (byte) (typeCode & 0xFF);
        this.name = name;
    }

    public int getTypeCode() {
        return this.typeCode & 0xFF;
    }

    public byte getRawTypeCode() {
        return typeCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    public static BussinessLogType fromTypeCode(byte typeCode) {
        for (BussinessLogType bussinessLogType : BussinessLogType.values()) {
            if (bussinessLogType.typeCode == typeCode) {
                return bussinessLogType;
            }
        }
        return UNKNOWN;
    }
}
