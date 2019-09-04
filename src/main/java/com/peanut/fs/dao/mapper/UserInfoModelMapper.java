package com.peanut.fs.dao.mapper;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
public interface UserInfoModelMapper {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    int selectById(String id);
}
