package com.peanut.fs.service.user;

import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.dao.command.UserInfoCommand;
import com.peanut.fs.service.user.dto.UserInfoDto;

import java.util.List;
import java.util.Map;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
public interface UserInfoService {

    /**
     * 分页查询用户列表
     * @param userInfoCommand
     * @return
     */
    PageInfo<UserInfoDto> selectUserInfoByPage(UserInfoCommand userInfoCommand);


    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserInfoDto selectByUserId(long userId);

    /**
     * 根据会议id查询用户列表
     * @param meetingId
     * @return
     */
    List<UserInfoDto> selectByMeetingId(long meetingId);

    /**
     * 新增用户信息
     * @param userInfoDto
     * @return
     */
    CommonResult insert(UserInfoDto userInfoDto);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    CommonResult delete(long userId);

    /**
     * 更新用户信息
     * @param userInfoDto
     * @return
     */
    CommonResult update(UserInfoDto userInfoDto);

    /**
     * 根据会议id删除
     * @param meetingId
     * @return
     */
    int deleteByMeetingId(long meetingId);

    /**
     * 更新签到状态
     * @param meetingId
     * @param phoneNo
     * @return
     */
    int updateCheckInStatus(long meetingId, String phoneNo);

    /**
     * 判端用户是否存在
     * @param meetingId
     * @param phoneNo
     * @return
     */
    Map<String, String> checkUserExist(long meetingId, String phoneNo);
}
