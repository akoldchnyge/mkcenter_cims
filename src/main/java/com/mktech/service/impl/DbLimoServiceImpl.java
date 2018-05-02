/**
 * @author Chnyge Lin
 */
package com.mktech.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mktech.dao.DbLimoDao;
import com.mktech.entity.DbLimo;
import com.mktech.service.DbLimoService;
import com.mktech.utils.CommonUtil;

/**
 * @author Chnyge Lin
 * 
 */
@Service
public class DbLimoServiceImpl implements DbLimoService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DbLimoServiceImpl.class);

	@Resource
	private DbLimoDao dbLimoMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dbLimoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DbLimo record) {
		// TODO Auto-generated method stub
		return dbLimoMapper.insert(record);
	}

	@Override
	public int insertSelective(DbLimo record) {
		// TODO Auto-generated method stub
		return dbLimoMapper.insertSelective(record);
	}

	@Override
	public DbLimo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dbLimoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DbLimo record) {
		// TODO Auto-generated method stub
		return dbLimoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DbLimo record) {
		// TODO Auto-generated method stub
		return dbLimoMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertDataByJson(String json) {

		JSONObject jsonObject = JSON.parseObject(json);
		try {
			JSONObject messageObject = jsonObject.getJSONObject("message");
			JSONObject dataObject = messageObject.getJSONObject("data");
			DbLimo dbLimo = (DbLimo) JSONObject.toJavaObject(dataObject,
					DbLimo.class);
			dbLimo.setTimestamp(jsonObject.getString("timestamp"));
			this.insert(dbLimo);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<DbLimo> selectByKeyRange(Integer min, Integer max) {
		// TODO Auto-generated method stub
		return dbLimoMapper.selectByKeyRange(min, max);
	}

	@Override
	public List<Map<String, Object>> selectByKeyRangeIntoMap(Integer min,
			Integer max) {
		// TODO Auto-generated method stub
		return dbLimoMapper.selectByKeyRangeIntoMap(min, max);
	}

	/**
	 * 导出至excel文件,需调用DownloadUtil方法，含本地持久化，到时需修改路径
	 */
	@Deprecated
	@Override
	public int export2Excel(Integer min, Integer max) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		SimpleDateFormat smp = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = smp.format(new Date());
		String outputFileName = "F://output/" + date + ".xlsx";

		int index = 1;
		// poi 3.9+ export .xlsx
		SXSSFWorkbook book = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) book.createSheet("立磨");

		List<Map<String, Object>> list = dbLimoMapper.selectByKeyRangeIntoMap(
				min, max);
		Row row_0 = sheet.createRow(0);
		sheet.autoSizeColumn(1, true);
		for (Map<String, Object> map : list) {
			int columnNum = 0;
			Row row = sheet.createRow(index);
			for (Entry<String, Object> entry : map.entrySet()) {
				// System.out.println(entry.getKey().toString());
				if (index == 1) {
					Cell cell = row_0.createCell(columnNum);
					cell.setCellValue(entry.getKey().toString());
					if (columnNum == 1) {
						Cell cell2 = row.createCell(columnNum++);
						cell2.setCellValue(CommonUtil.timestamp2Date(entry
								.getValue().toString()));
					} else {
						Cell cell2 = row.createCell(columnNum++);
						cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell2.setCellValue(Double.parseDouble(entry.getValue()
								.toString()));
					}
				} else if (index > 1) {
					if (columnNum == 1) {
						Cell cell = row.createCell(columnNum++);
						cell.setCellValue(CommonUtil.timestamp2Date(entry
								.getValue().toString()));
					} else {
						Cell cell = row.createCell(columnNum++);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(entry.getValue()
								.toString()));
					}

				}
			}
			index++;
		}
		try {
			FileOutputStream fos = new FileOutputStream(
					new File(outputFileName));
			book.write(fos);
			book.close();
			fos.close();
			long end = System.currentTimeMillis();
			float exc = (float) (end - start) / 1000;
			System.out.println("创建成功!用时" + exc + "s");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 导出至excel文件,需调用DownloadUtil方法，无本地持久化需求，返回Byte[]
	 */
	@Override
	public byte[] export2ExcelNotPernament(Integer min, Integer max) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();

		ByteArrayOutputStream out = null;
		SimpleDateFormat smp = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = smp.format(new Date());

		int index = 1;
		// poi 3.9+ export .xlsx
		SXSSFWorkbook book = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) book.createSheet("立磨");

		List<Map<String, Object>> list = dbLimoMapper.selectByKeyRangeIntoMap(
				min, max);
		Row row_0 = sheet.createRow(0);
		sheet.autoSizeColumn(1, true);
		for (Map<String, Object> map : list) {
			int columnNum = 0;
			Row row = sheet.createRow(index);
			for (Entry<String, Object> entry : map.entrySet()) {
				// System.out.println(entry.getKey().toString());
				if (index == 1) {
					Cell cell = row_0.createCell(columnNum);
					cell.setCellValue(entry.getKey().toString());
					if (columnNum == 1) {
						Cell cell2 = row.createCell(columnNum++);
						cell2.setCellValue(CommonUtil.timestamp2Date(CommonUtil
								.timestamp2BJTime(entry.getValue().toString())));
					} else {
						Cell cell2 = row.createCell(columnNum++);
						cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell2.setCellValue(Double.parseDouble(entry.getValue()
								.toString()));
					}
				} else if (index > 1) {
					if (columnNum == 1) {
						Cell cell = row.createCell(columnNum++);
						cell.setCellValue(CommonUtil.timestamp2Date(CommonUtil
								.timestamp2BJTime(entry.getValue().toString())));
					} else {
						Cell cell = row.createCell(columnNum++);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(entry.getValue()
								.toString()));
					}

				}
			}
			index++;
		}
		try {
			out = new ByteArrayOutputStream();
			book.write(out);
			long end = System.currentTimeMillis();
			float exc = (float) (end - start) / 1000;
			System.out.println("创建成功!用时" + exc + "s");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		return out.toByteArray();

	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		SimpleDateFormat smp = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println((smp.format(new Date())).toString());
	}

	@Override
	public int inserDataByJsonNew(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return 0;
	}
}
