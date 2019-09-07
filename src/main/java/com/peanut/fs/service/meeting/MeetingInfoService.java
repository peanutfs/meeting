package com.peanut.fs.service.meeting;

import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.dao.command.MeetingInfoCommand;
import com.peanut.fs.dao.model.meeting.MeetingInfoModel;
import com.peanut.fs.service.meeting.dto.MeetingInfoDto;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/6
 */
public interface MeetingInfoService {

    /**
     * 分页查询会议列表
     * @return
     */
    PageInfo<MeetingInfoDto> selectByPageWithCheckIn(MeetingInfoCommand meetingInfoCommand);

    /**
     * 根据id查询
     */
    MeetingInfoModel selectById(long id);

    /**
     * 新增
     * @param meetingInfoDto
     * @return
     */
    CommonResult insert(MeetingInfoDto meetingInfoDto);

    /**
     * 删除会议
     * @param id
     * @return
     */
    CommonResult delete(long id);

    /**
     * 更新会议信息
     * @param meetingInfoDto
     * @return
     */
    CommonResult update(MeetingInfoDto meetingInfoDto);

    /**
     * 查询生效会议
     * @return
     */
    MeetingInfoDto selectEffectiveMeeting();

    /**
     * 更新生效状态
     * @param id
     * @return
     */
    CommonResult updateEffectiveStatus(long id, String isEffective);

}
