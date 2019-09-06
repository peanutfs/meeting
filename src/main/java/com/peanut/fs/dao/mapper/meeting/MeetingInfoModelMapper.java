package com.peanut.fs.dao.mapper.meeting;

import com.peanut.fs.dao.command.MeetingInfoCommand;
import com.peanut.fs.dao.model.meeting.MeetingInfoModel;

import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/6
 */
public interface MeetingInfoModelMapper {

    /**
     * 分页查询会议列表
     * @return
     */
    List<MeetingInfoModel> selectByPageWithCheckIn(MeetingInfoCommand meetingInfoCommand);

    /**
     * 根据逐渐查询
     * @param id
     * @return
     */
    MeetingInfoModel selectById(long id);

    /**
     * 新增会议
     * @param meetingInfoModel
     * @return
     */
    int insert(MeetingInfoModel meetingInfoModel);

    /**
     * 删除会议
     * @param id
     * @return
     */
    int delete(long id);
}
