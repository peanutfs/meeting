package com.peanut.fs.common.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PageParam implements Serializable {

    /**
     * 页数
     */
    private int page;

    /**
     * 行数
     */
    private int limit;
}
