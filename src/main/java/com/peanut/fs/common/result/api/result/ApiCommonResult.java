package com.peanut.fs.common.result.api.result;

import com.peanut.fs.common.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @description:
 * @author:peanutfs
 * @date:created in 15:03 2019/9/07
 */
@Getter
@Setter
@ToString
public class ApiCommonResult {

    /**
     * 返回code
     */
    private String code;

    /**
     * 返回msg
     */
    private String msg;

    /**
     * 返回的body
     */
    private Object body;

    /**
     * 构建通用返回信息
     * @param code
     * @param msg
     */
    public void buildCommResultInfo(String code, String msg){
        this.setCode(code);
        this.setMsg(msg);
    }

    public ApiCommonResult() {
        this.code = "200";
        this.msg = "成功";
    }

    public ApiCommonResult(Object body) {
        this();
        this.body = body;
    }

    public ApiCommonResult(String msg) {
        this.code = "E999";
        this.msg = msg;
    }

    public ApiCommonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiCommonResult(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }

}
