package com.peanut.fs.dao.mapper.user;

import com.peanut.fs.dao.model.user.UserInfoModel;

import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
public interface UserInfoModelMapper {
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
}
