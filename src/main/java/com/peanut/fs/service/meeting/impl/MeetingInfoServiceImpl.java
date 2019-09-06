package com.peanut.fs.service.meeting.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.common.util.DateUtils;
import com.peanut.fs.common.util.ValidateUtil;
import com.peanut.fs.dao.command.MeetingInfoCommand;
import com.peanut.fs.dao.mapper.meeting.MeetingInfoModelMapper;
import com.peanut.fs.dao.model.meeting.MeetingInfoModel;
import com.peanut.fs.service.meeting.MeetingInfoService;
import com.peanut.fs.service.meeting.dto.MeetingInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<MeetingInfoModel> selectByPageWithCheckIn(MeetingInfoCommand meetingInfoCommand) {
        log.info("[MeetingInfoServiceImpl.selectByPageWithCheckIn]查看会议列表开始meetingInfoCommand:{}", meetingInfoCommand);
        PageHelper.startPage(meetingInfoCommand.getPage(), meetingInfoCommand.getLimit());
        List<MeetingInfoModel> meetingInfoModelList = meetingInfoModelMapper.selectByPageWithCheckIn(meetingInfoCommand);
        log.info("[MeetingInfoServiceImpl.selectByPageWithCheckIn]查看会议列表结束");
        return new PageInfo<>(meetingInfoModelList);
    }

    @Override
    public CommonResult insert(MeetingInfoDto meetingInfoDto) {
        log.info("[MeetingInfoServiceImpl.insert]新增会议开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(meetingInfoModelMapper.insert(buildMeetingInfoModel(meetingInfoDto)))){
            commonResult.setSuccess(CommonResult.failureCode);
            log.error("[MeetingInfoServiceImpl.insert]新增会议失败");
        }
        return commonResult;
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
        meetingInfoModel.setCheckInRule(meetingInfoDto.getCheckInRule());
        meetingInfoModel.setCheckInRange(meetingInfoDto.getCheckInRange());
        return meetingInfoModel;
    }

    @Override
    public CommonResult delete(long id) {
        log.info("[MeetingInfoServiceImpl.delete]删除会议开始id:{}", id);
        CommonResult commonResult = new CommonResult();
        if(!ValidateUtil.isOperationSuccess(meetingInfoModelMapper.delete(id))){
            log.error("[MeetingInfoServiceImpl.delete]删除会议结束");
            commonResult.setSuccess(CommonResult.failureCode);
        }
        return commonResult;
    }
}
