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

	Map<String, String> register(String username,String password);
	
	Map<String, String> login(String username,String password);
	
	String addLoginTicket(int userId);
	
	void logout(String ticket);
}
