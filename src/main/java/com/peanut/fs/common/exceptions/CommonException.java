package com.peanut.fs.common.exceptions;

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
public class CommonException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public CommonException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

}
