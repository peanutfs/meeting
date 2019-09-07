package com.peanut.fs.controller;

import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.enums.UserCheckInStatusEnum;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.dao.command.UserInfoCommand;
import com.peanut.fs.service.user.UserInfoService;
import com.peanut.fs.service.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */

@Slf4j
@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/addUser")
    @ResponseBody
    public CommonResult addUser(UserInfoDto userInfoDto){
        log.info("[UserInfoController.addUser]新增用户开始userInfoDto:{}", userInfoDto);
        userInfoDto.setIsCheckIn(UserCheckInStatusEnum.NOT_CHECK_IN.getCode());
        CommonResult commonResult = userInfoService.insert(userInfoDto);
        log.info("[UserInfoController.addUser]新增用户结束commonResult:{}", commonResult);
        return commonResult;
    }

    @RequestMapping("/addUserForm")
    public String addUserForm(Model model, long id) {
        model.addAttribute("id", id);
        return "user/user-add.ftl";
    }

    @RequestMapping("/userInfoForm")
    public String userInfoForm(Model model, long meetingId) {
        model.addAttribute("meetingId", meetingId);
        return "user/user-list.ftl";
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> userInfo(int page, int limit, long meetingId) {
        log.info("[UserInfoController.userInfo]查询app版本信息开始page:{}, limit:{}", page, limit);
        PageInfo<UserInfoDto> userInfoDtoPageInfo = userInfoService.selectUserInfoByPage(buildUserInfoCommand(page, limit, meetingId));
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("count",String.valueOf(userInfoDtoPageInfo.getTotal()));
        resultMap.put("data",userInfoDtoPageInfo.getList());
        log.info("[UserInfoController.userInfo]查询app版本信息结束resultMap:{}", resultMap);
        return resultMap;
    }

    private UserInfoCommand buildUserInfoCommand(int page, int limit, long meetingId) {
        UserInfoCommand userInfoCommand = new UserInfoCommand();
        userInfoCommand.setMeetingId(meetingId);
        userInfoCommand.setPage(page);
        userInfoCommand.setLimit(limit);
        return userInfoCommand;
    }

    @RequestMapping("/editUserInfoForm")
    public String editUserInfoForm(Model model, long id) {
        log.info("[UserInfoController.editUserInfoForm]编辑用户信息页面查询开始id:{}", id);
        model.addAttribute("userInfo", userInfoService.selectByUserId(id));
        return "user/user-edit.ftl";
    }

    @RequestMapping("/editUserInfo")
    @ResponseBody
    public CommonResult editUserInfo(UserInfoDto userInfoDto) {
        log.info("[UserInfoController.editUserInfo]更新用户信息开始userInfoDto:{}", userInfoDto);
        CommonResult commonResult = userInfoService.update(userInfoDto);
        log.info("[UserInfoController.editUserInfo]更新用户信息结束commonResult:{}", commonResult);
        return commonResult;
    }

    @RequestMapping("/delUserInfo")
    @ResponseBody
    public CommonResult delUserInfo(long id) {
        log.info("[UserInfoController.delUserInfo]删除用户信息开始id:{}", id);
        CommonResult commonResult = userInfoService.delete(id);
        log.info("[UserInfoController.delUserInfo]删除用户信息结束commonResult:{}", commonResult);
        return commonResult;
    }


}
