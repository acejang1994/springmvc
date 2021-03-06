package com.stl.api.util;

/**
 * User: jordan
 * Date: 1/2/15
 * Time: 10:20 PM
 */
public enum StatusCodeType {
    OK(StatusCodes.OK_ERROR_CODE, null),
    UNKNOWN_INTERNAL_SERVER_ERROR(StatusCodes.UNKNOWN_INTERNAL_ERROR_CODE, "warehouse.api.error.unknown"),
    BAD_REQUEST_CANNOT_PARSE_BODY(StatusCodes.UNPARSABLE_REQUEST_CODE, "warehouse.api.error.bodyunparsable"),
    MODEL_NOT_FOUND(StatusCodes.ENTITY_NOT_FOUND_CODE, "warehouse.api.error.model.notfound"),
    UNSUPPORTED_ASSIGNMENT_TYPE(StatusCodes.UNSUPPORTED_VALUE_CODE, ""),
    ENTITY_INVALID_IN_CONTEXT(StatusCodes.INVALID_ENTITY, "warehouse.api.error.invalidcontext");

    private int code;
    private String key;
    StatusCodeType(int code, String key) {
        this.code = code;
        this.key = key;
    }

    public int getCode() { return code; }
    public String getKey() { return key; }
}
