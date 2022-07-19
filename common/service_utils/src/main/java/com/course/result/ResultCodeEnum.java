package com.course.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(20000, "Success"),
    FAIL(201, "Failure"),
    SERVICE_ERROR(2012, "Service Exception"),
    DATA_ERROR(204, "Invalid Data"),
    ILLEGAL_REQUEST(205, "Illegal Request"),
    REPEAT_SUBMIT(206, "Repeated Submission"),

    LOGIN_AUTH(208, "No Loggin"),
    PERMISSION(209, "No Authorization"),

    PHONE_CODE_ERROR(211, "Mobile verification code error"),

    MTCLOUD_ERROR(210, "Abnormal Live Interface "),

    COUPON_GET(220, "Coupon has been received"),
    COUPON_LIMIT_GET(221, "Coupon has been issued"),

    FILE_UPLOAD_ERROR(21004, "file upload error"),
    FILE_DELETE_ERROR(21005, "file deletion error"),

    VOD_PALY_ERROR(209, "Please watch after purchase"),
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
