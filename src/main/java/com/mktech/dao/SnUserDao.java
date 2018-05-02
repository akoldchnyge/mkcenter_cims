package com.mktech.dao;

import org.springframework.stereotype.Repository;

import com.mktech.entity.SnUser;

@Repository
public interface SnUserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(SnUser record);

    int insertSelective(SnUser record);

    SnUser selectByPrimaryKey(Integer userid);
    
    SnUser selectByUsername(String username);

    int updateByPrimaryKeySelective(SnUser record);

    int updateByPrimaryKey(SnUser record);
}