package com.mage.base;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * BaseMapper基本定义
 * */
public interface BaseMapper<T,ID> {

    /**
     * 添加记录返回行数
     * */
    Integer insertSesect(T entity) throws DataAccessException;

    /**
     * 添加记录返回主键
     * */
    Integer inserHasKey(T entity) throws DataAccessException;

    /**
     * 批量添加
     * */
    Integer insertBatch(List<T> entities) throws DataAccessException;

    /**
     * 根据ID查询
     * */
    T selectByPrimaryKey(ID id) throws DataAccessException;

    /**
     * 多条件查询
     * */
    List<T> selectByParams(BaseQuery baseQuery) throws DataAccessException;

    /**
     * 更新单条记录
     * */
    Integer updateByPrimaryKeySelective(T entity) throws DataAccessException;

    /**
     * 批量更新
     * */
    Integer updateBatch(List<T> entities) throws DataAccessException;

    /**
     * 删除单条记录
     * */
    Integer deleteByPrimaryKey(ID id) throws DataAccessException;

    /**
     * 批量删除
     * */
    Integer deleteBath(ID[] ids) throws DataAccessException;
}
