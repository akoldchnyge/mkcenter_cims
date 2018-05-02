/**
 * @author Chnyge Lin
 */
package com.mktech.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mktech.dao.DbDaying1Dao;
import com.mktech.dao.DbDaying2Dao;
import com.mktech.dao.DbLimoDao;
import com.mktech.dao.DbShaochengDao;
import com.mktech.dao.ILineDao;
import com.mktech.dao.SystemSummaryDao;
import com.mktech.entity.DbDaying1;
import com.mktech.entity.DbDaying2;
import com.mktech.entity.DbLimo;
import com.mktech.entity.DbShaocheng;
import com.mktech.entity.Line;
import com.mktech.entity.SystemSummary;
import com.mktech.service.SystemSummaryService;
import com.mktech.utils.CommonUtil;

/**
 * @author Chnyge Lin
 *
 */
@Service
public class SystemSummaryServviceImpl implements SystemSummaryService{
	
	@Resource
	private SystemSummaryDao systemSummaryMapper;
	
	@Resource
	private DbLimoDao dbLimoMapper;
	
	@Resource
	private DbShaochengDao dbShaochengMapper;
	
	@Resource
	private DbDaying1Dao dbDaying1Mapper;
	
	@Resource
	private DbDaying2Dao dbDaying2Mapper;

	@Override
	public SystemSummary CheckIfExist(String deviceid) {
		// TODO Auto-generated method stub
		return systemSummaryMapper.checkIfExist(deviceid);
	}


	@Override
	public String insertByDeviceId(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		String deviceid = jsonObject.getString("deviceid");
		SystemSummary ss = systemSummaryMapper.checkIfExist(deviceid);
		
		try {
			if(ss == null){
				return "Warnning : cannot find responed system! ";
			}else{
				JSONObject messageObject = jsonObject.getJSONObject("message");
				JSONObject dataObject = messageObject.getJSONObject("data");
				String systemName = ss.getName();
				if(systemName.equals("LIMO")){
					//插入方法----
					DbLimo dbLimo = (DbLimo) JSONObject.toJavaObject(dataObject, DbLimo.class);
					dbLimo.setTimestamp(jsonObject.getString("timestamp"));
					dbLimoMapper.insert(dbLimo);
					//更新summary表最近更新时间
					systemSummaryMapper.updateLatestUpdateTimeByPrimaryKey(ss.getSystemId(), jsonObject.getString("timestamp"));
					return systemName;
				}else if (systemName.equals("SHAOCHENG")){
					DbShaocheng dbShaocheng = (DbShaocheng)JSONObject.toJavaObject(dataObject, DbShaocheng.class);
					dbShaocheng.setTimestamp(jsonObject.getString("timestamp"));
					dbShaochengMapper.insert(dbShaocheng);
					systemSummaryMapper.updateLatestUpdateTimeByPrimaryKey(ss.getSystemId(), jsonObject.getString("timestamp"));	
					return systemName;
				}else if (systemName.equals("DAYING_1")){
					DbDaying1 dbDaying1  = (DbDaying1) JSONObject.toJavaObject(dataObject, DbDaying1.class);
					dbDaying1.setTimestamp(jsonObject.getString("timestamp"));
					dbDaying1Mapper.insert(dbDaying1);
					systemSummaryMapper.updateLatestUpdateTimeByPrimaryKey(ss.getSystemId(), jsonObject.getString("timestamp"));
					return systemName;
				}else if (systemName.equals("DAYING_2")){
					DbDaying2 dbDaying2 = (DbDaying2) JSONObject.toJavaObject(dataObject,DbDaying2.class);
					dbDaying2.setTimestamp(jsonObject.getString("timestamp"));
					dbDaying2Mapper.insert(dbDaying2);
					systemSummaryMapper.updateLatestUpdateTimeByPrimaryKey(ss.getSystemId(), jsonObject.getString("timestamp"));
					return systemName;
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return null;

	}
	
	
}
