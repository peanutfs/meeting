package com.peanut.fs.dao.model.user;

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
public class UserInfoModel {

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
    private Date arriveTime;

    /**
     * 离开时间
     */
    private Date leaveTime;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否签到
     * 0-未签到
     * 1-签到
     */
    private String isCheckIn;


}
