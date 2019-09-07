package com.peanut.fs.dao.mapper.user;

import com.peanut.fs.dao.command.UserInfoCommand;
import com.peanut.fs.dao.model.user.UserInfoModel;

import java.util.List;
import java.util.Map;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
public interface UserInfoModelMapper {

    /**
     * 分页查询
     * @param userInfoCommand
     * @return
     */
    List<UserInfoModel> selectByPage(UserInfoCommand userInfoCommand);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    UserInfoModel selectById(long id);

    /**
     * 根据会议id查询用户
     * @param meetingId
     * @return
     */
    List<UserInfoModel> selectByMeetingId(long meetingId);

    /**
     * 新增
     * @param userInfoModel
     * @return
     */
    int insert(UserInfoModel userInfoModel);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(long id);

    /**
     * 更新
     * @param userInfoModel
     * @return
     */
    int update(UserInfoModel userInfoModel);

    /**
     * 根据手机号和会议id查询非当前id的用户信息
     * @param selectMap
     * @return
     */
    UserInfoModel selectByPhoneNoAndMeetingIdExId(Map<String, Object> selectMap);

    /**
     * 根据手机号和会议id查询
     * @param selectMap
     * @return
     */
    UserInfoModel selectByPhoneNoAndMeetingId(Map<String, Object> selectMap);

    /**
     * 根据会议id删除
     * @param meetingId
     * @return
     */
    int deleteByMeetingId(long meetingId);

    /**
     * 更新签到状态
     * @param userInfoModel
     * @return
     */
    int updateCheckInStatus(UserInfoModel userInfoModel);
}
