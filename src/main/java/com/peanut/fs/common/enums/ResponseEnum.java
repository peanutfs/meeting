package com.peanut.fs.common.enums;

import lombok.Getter;

/**
 * @description:
 * @author:zfs
 * @date:created in 14:26 2019/6/27
 */
@Getter
public enum ResponseEnum {

    /**
     * 成功
     */
    SUCCESS("0000", "成功"),

    /**
     * 系统异常
     */
    SERVER_ERROR("9999", "系统异常"),

    /**
     * 更新签到状态失败
     */
    UPDATE_CHECK_IN_STATUS_ERROR("U001", "更新签到状态失败"),

    /**
     * 查询有效会议失败
     */
    QUERY_EFFECTIVE_MEETING_ERROR("S101", "查询有效会议失败");


    private String code;

    private String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseEnum getByCode(String code) {
        for (ResponseEnum responseEnum : ResponseEnum.values()) {
            if (responseEnum.getCode().equals(code)) {
                return responseEnum;
            }
        }
        return null;
    }

}
