package com.peanut.fs.dao.command;

import com.peanut.fs.common.page.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/7
 */
@Getter
@Setter
@ToString
public class UserInfoCommand extends PageParam {

    /**
     * 会议id
     */
    private long meetingId;
}
