package com.peanut.fs.common.enums;

import lombok.Getter;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/7
 */
@Getter
public enum UserCheckInStatusEnum {

    /**
     * 1：已签到
     */
    CHECK_IN("1", "已签到"),

    /**
     * 0：未签到
     */
    NOT_CHECK_IN("0", "未签到");

    UserCheckInStatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;
}
