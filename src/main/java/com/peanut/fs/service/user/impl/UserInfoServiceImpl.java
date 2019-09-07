package com.peanut.fs.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.constants.BizConstants;
import com.peanut.fs.common.enums.ResponseEnum;
import com.peanut.fs.common.enums.UserCheckInStatusEnum;
import com.peanut.fs.common.exceptions.UpdateCheckInStatusException;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.common.util.DateUtils;
import com.peanut.fs.common.util.ValidateUtil;
import com.peanut.fs.dao.command.UserInfoCommand;
import com.peanut.fs.dao.mapper.user.UserInfoModelMapper;
import com.peanut.fs.dao.model.user.UserInfoModel;
import com.peanut.fs.service.user.UserInfoService;
import com.peanut.fs.service.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public PageInfo<UserInfoDto> selectUserInfoByPage(UserInfoCommand userInfoCommand) {
        log.info("[UserInfoServiceImpl.selectUserInfoByPage]分页查询用户信息列表开始userInfoCommand:{}", userInfoCommand);
        PageHelper.startPage(userInfoCommand.getPage(), userInfoCommand.getLimit(), true, false);
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<UserInfoModel> userInfoModelList = userInfoModelMapper.selectByPage(userInfoCommand);
        log.info("[UserInfoServiceImpl.selectUserInfoByPage]分页查询用户信息列表结束");
        if(CollectionUtils.isNotEmpty(userInfoModelList)){
            for(UserInfoModel userInfoModel: userInfoModelList){
                userInfoDtoList.add(buildUserInfoDto(userInfoModel));
            }
        }
        PageInfo userInfoPageInfo = new PageInfo<>(userInfoModelList);
        userInfoPageInfo.setList(userInfoDtoList);
        return userInfoPageInfo;
    }

    private UserInfoDto buildUserInfoDto(UserInfoModel userInfoModel) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setIsCheckIn(userInfoModel.getIsCheckIn());
        userInfoDto.setAge(userInfoModel.getAge());
        userInfoDto.setLeaveTime(DateUtils.formatDateTime(userInfoModel.getLeaveTime()));
        userInfoDto.setArriveTime(DateUtils.formatDateTime(userInfoModel.getArriveTime()));
        userInfoDto.setId(userInfoModel.getId());
        userInfoDto.setUsername(userInfoModel.getUsername());
        userInfoDto.setTransport(userInfoModel.getTransport());
        userInfoDto.setSex(userInfoModel.getSex());
        userInfoDto.setRoomNo(userInfoModel.getRoomNo());
        userInfoDto.setPhoneNo(userInfoModel.getPhoneNo());
        userInfoDto.setMeetingId(userInfoModel.getMeetingId());
        userInfoDto.setCompanyName(userInfoModel.getCompanyName());
        userInfoDto.setCompanyAddress(userInfoModel.getCompanyAddress());
        return userInfoDto;
    }

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
        if(null != userInfoModelMapper.selectByPhoneNoAndMeetingId(buildQueryMap(userInfoDto.getMeetingId(), userInfoDto.getPhoneNo()))){
            log.error("[UserInfoServiceImpl.insert]已存在相同手机号用户");
            commonResult.setSuccess(CommonResult.failureCode);
            commonResult.setMessage(BizConstants.PHONE_NO_EXIST);
            return commonResult;
        }
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.insert(buildUserInfoModel(userInfoDto)))){
            log.error("[UserInfoServiceImpl.insert]新增用户失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    private Map<String, Object> buildQueryMap(long meetingId, String phoneNo) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("meetingId",meetingId);
        queryMap.put("phoneNo",phoneNo);
        return queryMap;
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
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(long userId) {
        log.info("[UserInfoServiceImpl.delete]删除用户信息开始userId:{}", userId);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.delete(userId))){
            log.error("[UserInfoServiceImpl.delete]删除用户信息失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(UserInfoDto userInfoDto) {
        log.info("[UserInfoServiceImpl.update]更新用户信息开始userInfoDto:{}", userInfoDto);
        CommonResult commonResult = new CommonResult();
        UserInfoModel  userInfoModel = userInfoModelMapper.selectByPhoneNoAndMeetingIdExId(buildSelMap(userInfoDto.getPhoneNo(), userInfoDto.getMeetingId(), userInfoDto.getId()));
        if(userInfoModel != null){
            log.error("[UserInfoServiceImpl.update]手机号与同会议下的用户重复");
            commonResult.setSuccess(CommonResult.failureCode);
            commonResult.setMessage(BizConstants.PHONE_NO_EXIST);
            return commonResult;
        }
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.update(buildUserInfoModelForUpdate(userInfoDto)))){
            log.error("[UserInfoServiceImpl.update]更新用户信息失败");
            commonResult.setMessage(BizConstants.UPDATE_FAILED);
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByMeetingId(long meetingId) {
        log.info("[UserInfoServiceImpl.delete]删除用户信息开始meetingId:{}", meetingId);
        return userInfoModelMapper.deleteByMeetingId(meetingId);
    }

    @Override
    public int updateCheckInStatus(long meetingId, String phoneNo) {
        log.info("[UserInfoServiceImpl.updateCheckInStatus]更新签到状态开始meetingId:{}, phoneNo:{}", meetingId, phoneNo);
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setMeetingId(meetingId);
        userInfoModel.setPhoneNo(phoneNo);
        userInfoModel.setIsCheckIn(UserCheckInStatusEnum.CHECK_IN.getCode());
        if(!ValidateUtil.isOperationSuccess(userInfoModelMapper.updateCheckInStatus(userInfoModel))){
            log.error("[UserInfoServiceImpl.updateCheckInStatus]更新签到状态失败");
            throw new UpdateCheckInStatusException(ResponseEnum.UPDATE_CHECK_IN_STATUS_ERROR.getCode(), ResponseEnum.UPDATE_CHECK_IN_STATUS_ERROR.getMsg());
        }
        return 1;
    }

    @Override
    public Map<String, String> checkUserExist(long meetingId, String phoneNo) {
        Map<String, String> returnMap = new HashMap<>();
        if(null != userInfoModelMapper.selectByPhoneNoAndMeetingId(buildQueryMap(meetingId, phoneNo))){
            returnMap.put("isUserExist", BizConstants.YES);
        }else {
            returnMap.put("isUserExist", BizConstants.NO);
        }
        return returnMap;
    }

    private UserInfoModel buildUserInfoModelForUpdate(UserInfoDto userInfoDto) {
        UserInfoModel userInfoModel = buildUserInfoModel(userInfoDto);
        userInfoModel.setId(userInfoDto.getId());
        return userInfoModel;
    }

    private Map<String, Object> buildSelMap(String phoneNo, long meetingId, long id) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("phoneNo", phoneNo);
        returnMap.put("meetingId", meetingId);
        returnMap.put("id", id);
        return returnMap;
    }
}
