/**
 * @author Chnyge Lin
 */
package com.mktech.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mktech.dao.SnUserDao;
import com.mktech.dao.SnUserTicketDao;
import com.mktech.entity.SnUser;
import com.mktech.entity.SnUserTicket;
import com.mktech.service.SnUserService;
import com.mktech.utils.MD5Util;

/**
 * @author Chnyge Lin
 * 
 */
@Service
public class SnUserServiceImpl implements SnUserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SnUserServiceImpl.class);

	@Resource
	private SnUserDao snUserMapper;
	
	@Resource
	private SnUserTicketDao snUserTicketMapper;

	@Override
	public Map<String, String> register(String username, String password) {

		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(username)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if (StringUtils.isBlank(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		// 判断是否已经存在相同用户
		SnUser user = snUserMapper.selectByUsername(username);
		if (user != null) {
			map.put("msg", "用户名已存在");
			return map;
		}

		SnUser snUser = new SnUser();
		snUser.setUsername(username);
		snUser.setSalt(UUID.randomUUID().toString().substring(0, 5));
		snUser.setPassword(MD5Util.MD5(password + snUser.getSalt()));
		try {
			snUserMapper.insert(snUser);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "创建失败");
			return map;
		}

	}

	@Override
	public Map<String, String> login(String username, String password) {

		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(username)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if (StringUtils.isBlank(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		// 判断是否存在用户
		SnUser user = snUserMapper.selectByUsername(username);
		if (user == null) {
			map.put("msg", "该用户不存在");
			return map;
		}
		
		if(!MD5Util.MD5(password+user.getSalt()).equals(user.getPassword())){
			map.put("msg", "密码错误");
			return map;
		}
		String ticket = addLoginTicket(user.getUserid());
		map.put("ticket", ticket);
		return map;
	}


	@Override
	public String addLoginTicket(int userId) {
		SnUserTicket sut = new SnUserTicket();
		sut.setUserid(userId);
		Date now = new Date();
		now.setTime(3600*24*100+now.getTime());
		sut.setExipred(now);
		sut.setStatus(0);
		sut.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		snUserTicketMapper.insert(sut);
		return sut.getTicket();
		
	}

}
