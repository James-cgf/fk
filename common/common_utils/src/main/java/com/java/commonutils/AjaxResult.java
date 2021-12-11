package com.java.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曹广福
 * @date 2021/10/25 22:09
 */

@Data
public class AjaxResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有 不让外部类可以new
    private AjaxResult() {}

    //成功静态方法
    public static AjaxResult ok() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setCode(ResultCode.SUCCESS);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }

    //失败静态方法
    public static AjaxResult error() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        ajaxResult.setCode(ResultCode.ERROR);
        ajaxResult.setMessage("失败");
        return ajaxResult;
    }

    public AjaxResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public AjaxResult message(String message){
        this.setMessage(message);
        return this;
    }

    public AjaxResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public AjaxResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public AjaxResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
