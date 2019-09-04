package com.peanut.fs.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Getter
@Setter
@ToString
public class UserInfoModel {

    private int userId;

    private String username;

    private String password;
}
