/**
 * @author Chnyge Lin
 */
package com.mktech.service;

import com.alibaba.fastjson.JSONObject;
import com.mktech.entity.SystemSummary;

/**
 * @author Chnyge Lin
 *
 */
public interface SystemSummaryService {
	SystemSummary CheckIfExist(String deviceid);
	
	String insertByDeviceId(JSONObject jsonObject);
}
