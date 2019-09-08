package com.peanut.fs.service.meeting.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.constants.BizConstants;
import com.peanut.fs.common.enums.MeetingEffectiveStatusEnum;
import com.peanut.fs.common.enums.ResponseEnum;
import com.peanut.fs.common.exceptions.QueryEffectiveMeetingException;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.common.util.DateUtils;
import com.peanut.fs.common.util.GMapUtil;
import com.peanut.fs.common.util.ValidateUtil;
import com.peanut.fs.dao.command.MeetingInfoCommand;
import com.peanut.fs.dao.mapper.meeting.MeetingInfoModelMapper;
import com.peanut.fs.dao.model.meeting.MeetingInfoModel;
import com.peanut.fs.service.meeting.MeetingInfoService;
import com.peanut.fs.service.meeting.dto.MeetingInfoDto;
import com.peanut.fs.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/6
 */
@Slf4j
@Service("meetingInfoServiceImpl")
public class MeetingInfoServiceImpl implements MeetingInfoService {

    @Autowired(required = false)
    private MeetingInfoModelMapper meetingInfoModelMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public PageInfo<MeetingInfoDto> selectByPageWithCheckIn(MeetingInfoCommand meetingInfoCommand) {
        log.info("[MeetingInfoServiceImpl.selectByPageWithCheckIn]查看会议列表开始meetingInfoCommand:{}", meetingInfoCommand);
        PageHelper.startPage(meetingInfoCommand.getPage(), meetingInfoCommand.getLimit());
        List<MeetingInfoDto> meetingInfoDtoList = new ArrayList<>();
        List<MeetingInfoModel> meetingInfoModelList = meetingInfoModelMapper.selectByPageWithCheckIn(meetingInfoCommand);
        log.info("[MeetingInfoServiceImpl.selectByPageWithCheckIn]查看会议列表结束");
        if(CollectionUtils.isNotEmpty(meetingInfoModelList)){
            for(MeetingInfoModel meetingInfoModel: meetingInfoModelList){
                meetingInfoDtoList.add(buildMeetingInfoDto(meetingInfoModel));
            }
        }
        PageInfo meetingInfoDtoPageInfo = new PageInfo<>(meetingInfoModelList);
        meetingInfoDtoPageInfo.setList(meetingInfoDtoList);
        return meetingInfoDtoPageInfo;
    }

    @Override
    public MeetingInfoModel selectById(long id) {
        log.info("[MeetingInfoServiceImpl.selectById]根据id查询会议信息开始id:{}", id);
        return meetingInfoModelMapper.selectById(id);
    }

    private MeetingInfoDto buildMeetingInfoDto(MeetingInfoModel meetingInfoModel) {
        MeetingInfoDto meetingInfoDto = new MeetingInfoDto();
        meetingInfoDto.setCheckInLocation(meetingInfoModel.getCheckInLocation());
        meetingInfoDto.setStartTime(DateUtils.formatDateTime(meetingInfoModel.getStartTime()));
        meetingInfoDto.setRemark(meetingInfoModel.getRemark());
        meetingInfoDto.setOrganizer(meetingInfoModel.getOrganizer());
        meetingInfoDto.setMeetingTitle(meetingInfoModel.getMeetingTitle());
        meetingInfoDto.setIsNeedRegister(meetingInfoModel.getIsNeedRegister());
        meetingInfoDto.setId(meetingInfoModel.getId());
        meetingInfoDto.setEndTime(DateUtils.formatDateTime(meetingInfoModel.getEndTime()));
        meetingInfoDto.setCreateTime(DateUtils.formatDateTime(meetingInfoModel.getCreateTime()));
        meetingInfoDto.setCheckInRule(meetingInfoModel.getCheckInRule());
        meetingInfoDto.setCheckInRange(meetingInfoModel.getCheckInRange());
        meetingInfoDto.setCheckInCount(CollectionUtils.isNotEmpty(meetingInfoModel.getUserInfoModelList()) ? meetingInfoModel.getUserInfoModelList().size() : 0);
        meetingInfoDto.setIsEffective(meetingInfoModel.getIsEffective());
        meetingInfoDto.setLocationLongitude(meetingInfoModel.getLocationLongitude());
        meetingInfoDto.setLocationLatitude(meetingInfoModel.getLocationLatitude());
        return meetingInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(MeetingInfoDto meetingInfoDto) {
        log.info("[MeetingInfoServiceImpl.insert]新增会议开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(meetingInfoModelMapper.insert(buildMeetingInfoModelForInsert(meetingInfoDto)))){
            commonResult.setSuccess(CommonResult.failureCode);
            log.error("[MeetingInfoServiceImpl.insert]新增会议失败");
        }
        return commonResult;
    }

    private MeetingInfoModel buildMeetingInfoModelForInsert(MeetingInfoDto meetingInfoDto) {
        MeetingInfoModel meetingInfoModel = buildMeetingInfoModel(meetingInfoDto);
        meetingInfoModel.setIsEffective(MeetingEffectiveStatusEnum.NOT_EFFECTIVE.getCode());
        return meetingInfoModel;
    }

    private MeetingInfoModel buildMeetingInfoModel(MeetingInfoDto meetingInfoDto) {
        MeetingInfoModel meetingInfoModel = new MeetingInfoModel();
        meetingInfoModel.setCheckInLocation(meetingInfoDto.getCheckInLocation());
        meetingInfoModel.setThemeImageUrl(meetingInfoDto.getThemeImageUrl());
        meetingInfoModel.setStartTime(DateUtils.parseDateTime(meetingInfoDto.getStartTime()));
        meetingInfoModel.setRemark(meetingInfoDto.getRemark());
        meetingInfoModel.setOrganizer(meetingInfoDto.getOrganizer());
        meetingInfoModel.setMeetingTitle(meetingInfoDto.getMeetingTitle());
        meetingInfoModel.setIsNeedRegister(meetingInfoDto.getIsNeedRegister());
        meetingInfoModel.setEndTime(DateUtils.parseDateTime(meetingInfoDto.getEndTime()));
        meetingInfoModel.setCreateTime(new Date());
        meetingInfoModel.setUpdateTime(new Date());
        meetingInfoModel.setCheckInRule(meetingInfoDto.getCheckInRule());
        meetingInfoModel.setCheckInRange(meetingInfoDto.getCheckInRange());
        setLocationLonAndLat(meetingInfoModel, meetingInfoDto.getCheckInLocation());
        return meetingInfoModel;
    }

    private void setLocationLonAndLat(MeetingInfoModel meetingInfoModel, String checkInLocation) {
        if(StringUtils.isNotEmpty(checkInLocation)){
            log.info("[MeetingInfoServiceImpl.setLocationLonAndLat]获取位置经纬度开始checkInLocation:{}", checkInLocation);
            String longitudeAndLatitudeValue = GMapUtil.getLatitudeAndLongitudeByName(checkInLocation);
            if(StringUtils.isNotEmpty(longitudeAndLatitudeValue)){
                int listSize = 1;
                List<String> stringList = Arrays.asList(longitudeAndLatitudeValue.split(","));
                if(CollectionUtils.isNotEmpty(stringList) && stringList.size() > listSize){
                    meetingInfoModel.setLocationLongitude(stringList.get(0));
                    meetingInfoModel.setLocationLatitude(stringList.get(1));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(long id) {
        log.info("[MeetingInfoServiceImpl.delete]删除会议开始id:{}", id);
        CommonResult commonResult = new CommonResult();
        int delInt = meetingInfoModelMapper.delete(id);
        userInfoService.deleteByMeetingId(id);
        if(!ValidateUtil.isOperationSuccess(delInt)){
            log.error("[MeetingInfoServiceImpl.delete]删除会议失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(MeetingInfoDto meetingInfoDto) {
        log.info("[MeetingInfoServiceImpl.update]更新会议信息开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(meetingInfoModelMapper.update(buildMeetingInfoModelForUpdate(meetingInfoDto)))){
            log.error("[MeetingInfoServiceImpl.update]更新会议信息失败");
            commonResult.setSuccess(CommonResult.failureCode);
            commonResult.setMessage(BizConstants.UPDATE_FAILED);
        }
        return commonResult;
    }

    @Override
    public MeetingInfoDto selectEffectiveMeeting() {
        log.info("[MeetingInfoServiceImpl.selectEffectiveMeeting]查询生效会议开始");
        MeetingInfoModel meetingInfoModel = meetingInfoModelMapper.selectEffectiveMeeting(MeetingEffectiveStatusEnum.IS_EFFECTIVE.getCode());
        if(null == meetingInfoModel){
            log.error("[MeetingInfoServiceImpl.selectEffectiveMeeting]无生效会议");
            throw new QueryEffectiveMeetingException(ResponseEnum.QUERY_EFFECTIVE_MEETING_ERROR.getCode(), ResponseEnum.QUERY_EFFECTIVE_MEETING_ERROR.getMsg());
        }
        return buildMeetingInfoDto(meetingInfoModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateEffectiveStatus(long id, String isEffective) {
        log.info("[MeetingInfoServiceImpl.updateEffectiveStatus]更新会议生效状态开始id:{}, isEffective:{}", id, isEffective);
        CommonResult commonResult = new CommonResult();
        int updateInt = 1;
        if(StringUtils.equals(isEffective, MeetingEffectiveStatusEnum.IS_EFFECTIVE.getCode())){
            MeetingInfoModel meetingInfoModel = meetingInfoModelMapper.selectEffectiveMeeting(MeetingEffectiveStatusEnum.IS_EFFECTIVE.getCode());
            if(null != meetingInfoModel){
                log.info("[MeetingInfoServiceImpl.updateEffectiveStatus]当前存在生效会议meetingInfoModel:{}", meetingInfoModel);
                updateInt = meetingInfoModelMapper.updateEffectiveStatus(buildEffectiveMeetingInfoModel(meetingInfoModel.getId(), MeetingEffectiveStatusEnum.NOT_EFFECTIVE.getCode()));
            }
        }
        if(!ValidateUtil.isOperationSuccess(meetingInfoModelMapper.updateEffectiveStatus(buildEffectiveMeetingInfoModel(id, MeetingEffectiveStatusEnum.getByCode(isEffective).getCode()))) && !ValidateUtil.isOperationSuccess(updateInt)){
            log.info("[MeetingInfoServiceImpl.updateEffectiveStatus]更新失败");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }

    private MeetingInfoModel buildEffectiveMeetingInfoModel(long id, String effectiveStatus) {
        MeetingInfoModel meetingInfoModel = new MeetingInfoModel();
        meetingInfoModel.setIsEffective(effectiveStatus);
        meetingInfoModel.setUpdateTime(new Date());
        meetingInfoModel.setId(id);
        return meetingInfoModel;
    }

    private MeetingInfoModel buildMeetingInfoModelForUpdate(MeetingInfoDto meetingInfoDto) {
        MeetingInfoModel meetingInfoModel = buildMeetingInfoModel(meetingInfoDto);
        meetingInfoModel.setId(meetingInfoDto.getId());
        return meetingInfoModel;
    }
}
