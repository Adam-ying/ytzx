package com.yingtao.ytzx.common.exception;

import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author Adam
 * @create 2024-04-13 20:16
 */
@Data
public class YtException extends RuntimeException{

    private Integer code;
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public YtException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public YtException(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
