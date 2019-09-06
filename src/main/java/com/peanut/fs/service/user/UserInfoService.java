package com.peanut.fs.service.user;

import com.peanut.fs.service.user.dto.UserInfoDto;

import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
public interface UserInfoService {

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
    int insert(UserInfoDto userInfoDto);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    int delete(long userId);
}
