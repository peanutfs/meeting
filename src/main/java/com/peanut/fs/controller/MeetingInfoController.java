package com.peanut.fs.controller;

import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.service.meeting.MeetingInfoService;
import com.peanut.fs.service.meeting.dto.MeetingInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/meeting")
public class MeetingInfoController {

    @Autowired
    private MeetingInfoService meetingInfoService;

    @PostMapping("/addMeeting")
    public CommonResult addMeeting(MeetingInfoDto meetingInfoDto){
        log.info("[MeetingInfoController.addMeeting]新增会议开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = meetingInfoService.insert(meetingInfoDto);
        log.info("[MeetingInfoController.addMeeting]新增会议结束commonResult:{}", commonResult);
        return commonResult;
    }
}
