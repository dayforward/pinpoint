package com.navercorp.pinpoint.common.server.bo.stat;

public enum BusinessLogType {
	UNKNOWN(0, "Unknown"),
	BUSINESS_LOG_V1((byte) 1, "business_log_v1");
	
	public static final int TYPE_CODE_BYTE_LENGTH = 1;

    private final byte typeCode;
    private final String name;

    BusinessLogType(int typeCode, String name) {
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
    
    public static BusinessLogType fromTypeCode(byte typeCode) {
        for (BusinessLogType businessLogType : BusinessLogType.values()) {
            if (businessLogType.typeCode == typeCode) {
                return businessLogType;
            }
        }
        return UNKNOWN;
    }
}
