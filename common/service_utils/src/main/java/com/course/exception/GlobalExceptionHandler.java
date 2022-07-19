package com.course.exception;

import com.course.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/* AOP*/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(null).message("Global Exception");
    }

    /* Handling specific exception */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail(null).message("Arithmetic Exception");
    }

    /* Handling customized exception */
    @ExceptionHandler(CourseException.class)
    @ResponseBody
    public Result error(CourseException e) {
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMessage());
    }
}
