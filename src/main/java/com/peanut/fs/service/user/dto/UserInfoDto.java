package com.peanut.fs.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Getter
@Setter
@ToString
public class UserInfoDto {

    /**
     * id
     */
    private long id;

    /**
     * 会议id
     */
    private long meetingId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private int age;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 到达时间
     */
    private String arriveTime;

    /**
     * 离开时间
     */
    private String leaveTime;

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 交通方式
     */
    private String transport;

    /**
     * 住宿房号
     */
    private String roomNo;

    /**
     * 是否签到
     */
    private String isCheckIn;

}
