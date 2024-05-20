package com.yingtao.ytzx.common.exception;

import com.yingtao.ytzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Adam
 * @create 2024-04-13 20:12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null, 201, "出现了异常");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(YtException exception){
        return Result.build(null, exception.getResultCodeEnum());
    }
}
