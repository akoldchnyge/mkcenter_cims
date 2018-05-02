/**
 * @author Chnyge Lin
 */
package com.mktech.webservices;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mktech.service.impl.DbLimoServiceImpl;
import com.mktech.service.impl.DbShaochengServiceImpl;
import com.mktech.service.impl.SystemSummaryServviceImpl;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * 用于所有企业数据传输，根据deviceid判断相应表归属
 * @author Chnyge Lin
 *
 */
@Component("DataReceiverServer")
public class DataReceiverServer {
	

	
	@Resource
	private SystemSummaryServviceImpl systemSummaryServvice;

	public String ReceiveData(String json) {
		try {
			JSONObject jsonObject = JSON.parseObject(json);
			String system_name=systemSummaryServvice.insertByDeviceId(jsonObject); 
			if(system_name!=null){
				return "Success : insert data into  -- > "+system_name;
			}else {
				return "Error : nothing had been changed!";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "failure";
		}

	}
}
