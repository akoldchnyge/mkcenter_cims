/**
 * @author Chnyge Lin
 */
package com.mktech.service;

import java.util.Map;

import com.mktech.entity.SnUser;

/**
 * @author Chnyge Lin
 *
 */
public interface SnUserService {

//	int deleteByPrimaryKey(Integer userid);

//    int insert(SnUser record);
//
//    int insertSelective(SnUser record);
//
//    SnUser selectByPrimaryKey(Integer userid);
//
//    int updateByPrimaryKeySelective(SnUser record);
//
//    int updateByPrimaryKey(SnUser record);
	
	Map<String, String> register(String username,String password);
	
	Map<String, String> login(String username,String password);
	
	String addLoginTicket(int userId);
}
