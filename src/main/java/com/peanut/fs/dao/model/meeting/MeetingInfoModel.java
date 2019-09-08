package com.peanut.fs.dao.model.meeting;

import com.peanut.fs.dao.model.user.UserInfoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Getter
@Setter
@ToString
public class MeetingInfoModel {

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
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

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
    private Date createTime;

    /**
     * 是否生效
     */
    private String isEffective;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 关联用户列表
     */
    private List<UserInfoModel> userInfoModelList;
}
