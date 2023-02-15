package com.bs.ggkt.result;

import lombok.Data;

import static com.bs.ggkt.constant.CommonErrorString.FATAL_FAIL;
import static com.bs.ggkt.constant.CommonErrorString.SUCCESS;

/**
 * 统一返回结果类
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static<T> Result<T> ok(T data) {
        return new Result<>(20000, SUCCESS, data);
    }

    public static<T> Result<T> fail(T data) {
        return new Result<>(500, FATAL_FAIL, data);
    }

    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

}
