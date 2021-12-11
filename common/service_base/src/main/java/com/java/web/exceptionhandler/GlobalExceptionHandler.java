package com.java.web.exceptionhandler;

import com.java.commonutils.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 全局异常处理
 * @date 2021/10/26 16:04
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult error(Exception e){
        e.printStackTrace();
        return AjaxResult.error().code(9999).message("执行了全局异常处理");
    }

    //自定义异常类
    @ExceptionHandler(FkException.class)
    @ResponseBody
    public AjaxResult error(FkException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return AjaxResult.error().code(e.getCode()).message(e.getMsg());
    }

}
