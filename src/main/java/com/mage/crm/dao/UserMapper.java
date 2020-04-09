package com.mage.crm.dao;

import com.mage.base.BaseQuery;
import com.mage.crm.vo.User;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * 添加记录返回行数
     * @param entity
     * @return
     */
    public Integer insertSelective(User entity) throws DataAccessException;

    /**
     * 添加记录返回主键
     * @param entity
     * @return
     */
    public Integer insertHasKey(User entity) throws DataAccessException;

    /**
     * 批量添加
     * @param entities
     * @return
     */
    public Integer insertBatch(List<User> entities) throws DataAccessException;


    /**
     * 根据id 查询详情
     * @param id
     * @return
     */
    public User selectByPrimaryKey(Integer id) throws DataAccessException;


    /**
     * 多条件查询
     * @param baseQuery
     * @return
     */
    public List<User> selectByParams(BaseQuery baseQuery) throws DataAccessException;


    /**
     * 更新单条记录
     * @param entity
     * @return
     */
    public Integer updateByPrimaryKeySelective(User entity) throws DataAccessException;


    /**
     * 批量更新
     * @param entities
     * @return
     */
    public Integer updateBatch(List<User> entities) throws DataAccessException;

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    public Integer deleteByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Integer deleteBatch(Integer[] ids) throws DataAccessException;

    User queryUserByUserName(String username);

    List<Map<String,Object>> queryAllCustomerManager();
}