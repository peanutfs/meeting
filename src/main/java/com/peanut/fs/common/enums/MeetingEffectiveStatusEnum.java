package com.peanut.fs.common.enums;

import lombok.Getter;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/7
 */
@Getter
public enum MeetingEffectiveStatusEnum {


    /**
     * 1：生效
     */
    IS_EFFECTIVE("1", "生效"),

    /**
     * 0：未生效
     */
    NOT_EFFECTIVE("0", "未生效");

    MeetingEffectiveStatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static MeetingEffectiveStatusEnum getByCode(String code) {
        for (MeetingEffectiveStatusEnum meetingEffectiveStatusEnum : MeetingEffectiveStatusEnum.values()) {
            if (meetingEffectiveStatusEnum.getCode().equals(code)) {
                return meetingEffectiveStatusEnum;
            }
        }
        return MeetingEffectiveStatusEnum.NOT_EFFECTIVE;
    }

    private String code;

    private String msg;
}
