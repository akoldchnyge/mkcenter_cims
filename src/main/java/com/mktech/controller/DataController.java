/**
 * @author Chnyge Lin
 */
package com.mktech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.util.logging.resources.logging;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mktech.entity.DbLimo;
import com.mktech.service.impl.DbLimoServiceImpl;
import com.mktech.service.impl.DbShaochengServiceImpl;
import com.mktech.service.impl.SnUserServiceImpl;
import com.mktech.utils.CommonUtil;

/**
 * controller 4 index
 * 
 * @author Chnyge Lin
 * 
 */
@Controller
@RequestMapping(value = "/data")
public class DataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

	@Resource
	private SnUserServiceImpl snUserService;
	
	@Resource
	private DbLimoServiceImpl dbLimoService;
	
	@Resource
	private DbShaochengServiceImpl dbShaochengService;
	
	
	public String Chart4Limo(int o){
		int min = Math.max(0, o-500);
		List<Map<String,Object>> list = dbLimoService.selectByKeyRangeIntoMap(min,o);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		Gson gson = new Gson();
		String s = gson.toJson(map);
		System.out.println("读取成功");
		return s;
	}
	
	/**
	 * 返回不大于当前时间的最近记录的key，根据记录回溯20*10条记录（即10分钟）
	 * @param timestamp
	 * @return
	 */
	public int findNearestRecord(String timestamp){
		return dbLimoService.selectNearestRecord(timestamp).getId(); 
	}
	
	@RequestMapping(value = "/limo")
	public String getData(HttpServletRequest request,
						HttpServletResponse response,
						@RequestParam("timestamp") String timestamp) throws IOException{
		String result = null;
		try {
			int o = this.findNearestRecord(timestamp);
			System.out.println(o);
			result = this.Chart4Limo(o);
		} catch (Exception e) {
			if(LOGGER.isErrorEnabled()){
				LOGGER.error("查询失败",e);
			}
			result = null;
		}
		CommonUtil.writeToWeb(result, "html", response);
		return null;
	}
	
	@RequestMapping(value = "/update_limo")
	public String updateDataCell(HttpServletRequest request,
							HttpServletResponse response,
							@RequestParam("timestamp") String timestamp) throws IOException {
		String result = null;	
		try {
			DbLimo dbLimo = dbLimoService.selectNearestRecord(timestamp);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", dbLimo);
			Gson gson = new Gson();
			result= gson.toJson(map);
		} catch (Exception e) {
			if(LOGGER.isErrorEnabled()){
				LOGGER.error("查询失败",e);
			}
			result = null;
			
		}
		CommonUtil.writeToWeb(result, "html", response);
		return null;
	}
	
	@RequestMapping(value = "/update_limo_test")
	public String updateDataCell_test(HttpServletRequest request,
							HttpServletResponse response,
							@RequestParam("timestamp") String timestamp) throws IOException {
		String result = null;	
		try {
			DbLimo dbLimo = dbLimoService.selectNearestRecord(timestamp);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", dbLimo);
			Gson gson = new Gson();
			result= gson.toJson(map);
		} catch (Exception e) {
			if(LOGGER.isErrorEnabled()){
				LOGGER.error("查询失败",e);
			}
			result = null;
			
		}
		CommonUtil.writeToWeb(result, "html", response);
		return null;
	}
	
}
