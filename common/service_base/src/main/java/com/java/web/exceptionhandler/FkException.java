package com.java.web.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 曹广福
 * @date 2021/10/27 9:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FkException extends RuntimeException{

    public Integer code;//状态码
    public String msg;//异常信息

}
