/**
 * @author Chnyge Lin
 */
package com.mktech.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Chnyge Lin
 *
 */
public class CommonUtil {
	
	public static void isToOs(InputStream is,OutputStream os) throws IOException{
		byte[] bufferBytes = new byte[8192];
		int n = 0;
		while((n = is.read(bufferBytes))>0){
			os.write(bufferBytes,0,n);
		}
	}
	
	/**
	 * 毫秒级timestamp -> date格式
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2Date(String timestamp){
		long time = Long.parseLong(timestamp);
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(time));
	}
	
	/**
	 * 根据格式传入转换成时间戳"yyyy-MM-dd'T'HH:mm:ss"
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String Date2Timestamp(String time) throws ParseException{
		Format format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = (Date)format.parseObject(time);
		long t = date.getTime()/1000;		
		return String.valueOf(t);
	}
	
	/**
	 * 数据库内时间戳比北京时间快8小时，通过该方法进行转化(-8h)
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2BJTime(String timestamp){
		String timestamp_new = String.valueOf(Long.parseLong(timestamp)-8*3600*1000);
		return timestamp_new;
	}
	
	/**
	 * 用于将查询时间戳与数据内进行比对，通过该方法进行转化(+8h)
	 * @param time
	 * @return
	 */
	public static String BJTime2Timestamp(String time){
		String timestamp_new = String.valueOf(Long.parseLong(time)+8*3600*1000);
		return timestamp_new;
	}
	
	/**
	 * 打印错误信息，返回字符串
	 * @param e
	 * @return
	 */
	public static String getErrorInfoFromException(Exception e){
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e2) {
			// TODO: handle exception
			return "Error : cannot get error information!";
		}
		
	}
	
	public static void main(String[] args) throws ParseException {
//		long time = System.currentTimeMillis()/1000;
//		System.out.println(time);
//		System.out.println(timestamp2Date(time));
		String time = "1523439006089";
		System.out.println(timestamp2Date(time));
		System.out.println(timestamp2Date(timestamp2BJTime(time)));
		System.out.println(timestamp2Date(BJTime2Timestamp(time)));
	}
}
