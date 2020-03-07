package com.mage.dgj.dao;

import com.mage.base.BaseMapper;
import com.mage.dgj.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(User record);

    Integer insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    @Override
    Integer updateByPrimaryKeySelective(User record);

    Integer updateByPrimaryKey(User record);
}