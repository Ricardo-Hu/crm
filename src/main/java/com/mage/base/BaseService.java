package com.mage.base;

import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import java.util.List;

public abstract class BaseService<T,ID> {

    @Resource
    private BaseMapper<T,ID> baseMapper;

    /**
     * 添加记录返回行数
     * */
    public Integer insertSesect(T entity) throws DataAccessException{
        return baseMapper.insertSesect(entity);
    }

    /**
     * 添加记录返回主键
     * */
    public ID inserHasKey(T entity) throws DataAccessException{
        baseMapper.insertSesect(entity);
        try {
            return (ID)entity.getClass().getMethod("getID").invoke(entity);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 批量添加
     * */
    public Integer insertBatch(List<T> entities) throws DataAccessException{
        return baseMapper.insertBatch(entities);
    }

    /**
     * 根据id查询
     * */
    public T selectByPrimaryKey(ID id) throws  DataAccessException{
        return baseMapper.selectByPrimaryKey(id);
    }
    /**
     * 多条件查询
     * */
    public List<T> selectByParams(BaseQuery baseQuery) throws DataAccessException{
        return baseMapper.selectByParams(baseQuery);
    }

    /**
     * 更新单条记录
     * */
    public Integer updateByPrimaryKeySelective(T entity) throws DataAccessException{
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 批量更新
     * */
    public Integer updateBatch(List<T> entities) throws DataAccessException{
        return updateBatch(entities);
    }

    /**
     * 删除单条记录
     * */
    public Integer deleteByPrimaryKey(ID id) throws DataAccessException{
        return deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * */
    public Integer deleteBath(ID[] ids) throws DataAccessException{
        return deleteBath(ids);
    }

}
