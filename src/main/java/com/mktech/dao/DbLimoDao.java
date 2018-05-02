package com.mktech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mktech.entity.DbLimo;

@Repository
public interface DbLimoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DbLimo record);

    int insertSelective(DbLimo record);

    DbLimo selectByPrimaryKey(Integer id);
    
    List<DbLimo> selectByKeyRange(@Param("min") Integer min,@Param("max") Integer max);
    
    List<Map<String, Object>> selectByKeyRangeIntoMap(@Param("min") Integer min,@Param("max") Integer max);

    int updateByPrimaryKeySelective(DbLimo record);

    int updateByPrimaryKey(DbLimo record);
}