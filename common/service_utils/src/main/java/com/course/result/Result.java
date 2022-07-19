package com.course.result;
/* Providing the same returning interface */


import lombok.Data;

/*Converting to JSON*/

@Data /*Get & Set method*/
public class Result<T> {
    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = new Result<>();
        if (body != null) {
            result.setData(body);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS.getCode(), "Success");
    }

    public static <T> Result<T> fail(T data) {
        return build(data, ResultCodeEnum.FAIL.getCode(), "Failure");

    }


    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }
}
