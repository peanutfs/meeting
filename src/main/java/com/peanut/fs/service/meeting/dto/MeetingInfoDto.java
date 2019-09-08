package com.peanut.fs.service.meeting.dto;

import com.peanut.fs.common.page.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/6
 */
@Getter
@Setter
@ToString
public class MeetingInfoDto {

    /**
     * id
     */
    private long id;

    /**
     * 会议标题
     */
    private String meetingTitle;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 签到地点
     */
    private String checkInLocation;

    /**
     * 签到范围
     */
    private int checkInRange;

    /**
     * 签到地点经度
     */
    private String locationLongitude;

    /**
     * 签到地点纬度
     */
    private String locationLatitude;

    /**
     * 是否需要填写个人信息
     */
    private String isNeedRegister;

    /**
     * 主办方
     */
    private String organizer;

    /**
     * 签到规则
     */
    private String checkInRule;

    /**
     * 备注
     */
    private String remark;

    /**
     * 主题图片地址
     */
    private String themeImageUrl;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 签到统计
     */
    private int checkInCount;

    /**
     * 是否生效
     */
    private String isEffective;
}
