package com.peanut.fs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {

    @RequestMapping("/all")
    public String helloUser(Model model) {
        return "/hello";
    }
}
