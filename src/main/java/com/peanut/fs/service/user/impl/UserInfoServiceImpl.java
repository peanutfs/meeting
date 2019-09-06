package com.peanut.fs.service.user.impl;

import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.common.util.ValidateUtil;
import com.peanut.fs.dao.mapper.user.UserInfoModelMapper;
import com.peanut.fs.dao.model.user.UserInfoModel;
import com.peanut.fs.service.user.UserInfoService;
import com.peanut.fs.service.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.peanut.fs.common.util.DateUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Slf4j
@Service("userInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired(required = false)
    private UserInfoModelMapper userInfoModelMapper;

    @Override
    public UserInfoDto selectByUserId(long userId) {
        log.info("[UserInfoServiceImpl.selectByUserId]根据用户id查询用户信息开始userId:{}", userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        UserInfoModel userInfoModel = userInfoModelMapper.selectById(userId);
        log.info("[UserInfoServiceImpl.selectByUserId]根据用户id查询用户信息结束userInfoModel:{}", userInfoModel);
        if(null != userInfoModel){
            userInfoDto = assembleUserInfoDto(userInfoModel);
        }
        return userInfoDto;
    }

    private UserInfoDto assembleUserInfoDto(UserInfoModel userInfoModel) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfoModel.getId());
        userInfoDto.setUsername(userInfoModel.getUsername());
        userInfoDto.setTransport(userInfoModel.getTransport());
        userInfoDto.setSex(userInfoModel.getSex());
        userInfoDto.setRoomNo(userInfoModel.getRoomNo());
        userInfoDto.setPhoneNo(userInfoModel.getPhoneNo());
        userInfoDto.setMeetingId(userInfoModel.getMeetingId());
        userInfoDto.setLeaveTime(DateUtils.formatDateTime(userInfoModel.getLeaveTime()));
        userInfoDto.setCompanyName(userInfoModel.getCompanyName());
        userInfoDto.setCompanyAddress(userInfoModel.getCompanyAddress());
        userInfoDto.setArriveTime(DateUtils.formatDateTime(userInfoModel.getArriveTime()));
        userInfoDto.setAge(userInfoModel.getAge());
        userInfoDto.setIsCheckIn(userInfoModel.getIsCheckIn());
        return userInfoDto;
    }

    @Override
    public List<UserInfoDto> selectByMeetingId(long meetingId) {
        log.info("[UserInfoServiceImpl.selectByMeetingId]根据会议id查询用户列表开始meetingId:{}", meetingId);
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<UserInfoModel> userInfoModelList = userInfoModelMapper.selectByMeetingId(meetingId);
        if(CollectionUtils.isNotEmpty(userInfoModelList)){
            log.info("[UserInfoServiceImpl.selectByMeetingId]根据会议id查询用户列表结束userInfoModelList:{}", userInfoModelList.size());
            for(UserInfoModel userInfoModel: userInfoModelList){
                userInfoDtoList.add(assembleUserInfoDto(userInfoModel));
            }
        }
        return userInfoDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(UserInfoDto userInfoDto) {
        log.info("[UserInfoServiceImpl.insert]新增用户信息开始userInfoDto:{}", userInfoDto);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.insert(buildUserInfoModel(userInfoDto)))){
            log.error("[UserInfoServiceImpl.insert]新增用户失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    private UserInfoModel buildUserInfoModel(UserInfoDto userInfoDto) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setAge(userInfoDto.getAge());
        userInfoModel.setUsername(userInfoDto.getUsername());
        userInfoModel.setUpdateTime(new Date());
        userInfoModel.setTransport(userInfoDto.getTransport());
        userInfoModel.setSex(userInfoDto.getSex());
        userInfoModel.setRoomNo(userInfoDto.getRoomNo());
        userInfoModel.setPhoneNo(userInfoDto.getPhoneNo());
        userInfoModel.setMeetingId(userInfoDto.getMeetingId());
        userInfoModel.setLeaveTime(DateUtils.parseDateTime(userInfoDto.getLeaveTime()));
        userInfoModel.setIsCheckIn(userInfoDto.getIsCheckIn());
        userInfoModel.setCreateTime(new Date());
        userInfoModel.setCompanyName(userInfoDto.getCompanyName());
        userInfoModel.setCompanyAddress(userInfoDto.getCompanyAddress());
        userInfoModel.setArriveTime(DateUtils.parseDateTime(userInfoDto.getArriveTime()));
        return userInfoModel;
    }

    @Override
    public CommonResult delete(long userId) {
        log.info("[UserInfoServiceImpl.delete]删除用户信息开始userId:{}", userId);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.delete(userId))){
            log.error("[UserInfoServiceImpl.delete]删除用户信息失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }
}
