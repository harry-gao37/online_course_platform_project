package com.course.exception;

import com.commonutils.result.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CourseException extends RuntimeException{
    private Integer code;
    private String message;

    public CourseException (Integer code,String msg){
        super(msg);
        this.code = code;
        this.message = msg;
    }

    private CourseException (String msg){
        super(msg);
        this.message = msg;
    }

    public static  CourseException from(IResultCode resultCode){
        CourseException courseException = new CourseException(resultCode.getCode(), resultCode.getMsg());
        return courseException;
    }

    public static  CourseException from(String  msg){
        CourseException courseException = new CourseException(msg);
        return courseException;
    }
}
