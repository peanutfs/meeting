package com.peanut.fs.controller.api;

import com.peanut.fs.common.result.api.result.ApiCommonResult;
import com.peanut.fs.common.result.api.result.CommonResultTemplate;
import com.peanut.fs.service.meeting.MeetingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/7
 */
@Slf4j
@RestController
@RequestMapping("/v1/meeting")
public class MeetingInfoForApiController {

    @Autowired
    private MeetingInfoService meetingInfoService;

    @PostMapping(value = "/queryMeetingInfo")
    public ApiCommonResult queryMeetingInfo(HttpServletRequest httpServletRequest){
        log.info("[MeetingInfoForApiController.queryMeetingInfo]查询会议信息开始");
        return CommonResultTemplate.execute(() -> meetingInfoService.selectEffectiveMeeting());
    }

}
