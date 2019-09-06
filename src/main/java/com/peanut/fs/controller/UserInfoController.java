package com.peanut.fs.controller;

import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.service.user.UserInfoService;
import com.peanut.fs.service.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/all")
    public String helloUser(Model model) {
        return "/hello";
    }

    @RequestMapping("/addUser")
    public CommonResult addUser(UserInfoDto userInfoDto){
        log.info("[UserInfoController.addUser]新增用户开始userInfoDto:{}", userInfoDto);
        CommonResult commonResult = userInfoService.insert(userInfoDto);
        log.info("[UserInfoController.addUser]新增用户结束commonResult:{}", commonResult);
        return commonResult;
    }
}
