package com.peanut.fs.controller.api;

import com.peanut.fs.common.result.api.result.ApiCommonResult;
import com.peanut.fs.common.result.api.result.CommonResultTemplate;
import com.peanut.fs.service.user.UserInfoService;
import com.peanut.fs.service.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/7
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserInfoForApiController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ApiCommonResult addUser(UserInfoDto userInfoDto){
        log.info("[UserInfoForApiController.addUser]新增用户信息开始userInfoDto:{}", userInfoDto);
        return CommonResultTemplate.execute(()-> userInfoService.insert(userInfoDto));
    }

    @PostMapping(value = "/checkIn")
    @ResponseBody
    public ApiCommonResult checkIn(long meetingId, String phoneNo){
        log.info("[UserInfoForApiController.checkIn]更新用户签到状态开始meetingId:{}, phoneNo:{}", meetingId, phoneNo);
        return CommonResultTemplate.execute(()-> userInfoService.updateCheckInStatus(meetingId, phoneNo));
    }

    @PostMapping(value = "/isUserExist")
    @ResponseBody
    public ApiCommonResult isUserExist(long meetingId, String phoneNo){
        log.info("[UserInfoForApiController.checkIn]判断用户信息是否存在开始meetingId:{}, phoneNo:{}", meetingId, phoneNo);
        return CommonResultTemplate.execute(()-> userInfoService.updateCheckInStatus(meetingId, phoneNo));
    }
}
