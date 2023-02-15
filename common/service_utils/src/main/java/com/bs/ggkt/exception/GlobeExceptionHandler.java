package com.bs.ggkt.exception;

import com.bs.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobeExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error() {
        return Result.fail(null).message("执行了全局异常处理");
    }

    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result error(GgktException e) {
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }

}
