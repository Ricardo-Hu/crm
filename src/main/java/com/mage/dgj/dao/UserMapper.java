package com.mage.dgj.dao;

import com.mage.base.BaseMapper;
import com.mage.dgj.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {
    @Override
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(User record);

    Integer insertSelective(User record);

    @Override
    User selectByPrimaryKey(Integer id);

    @Override
    Integer updateByPrimaryKeySelective(User record);

    Integer updateByPrimaryKey(User record);


    User queryUserByUserName(String userName);
}