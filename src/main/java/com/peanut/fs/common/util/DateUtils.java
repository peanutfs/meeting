package com.peanut.fs.common.util;

import com.peanut.fs.common.constants.SysConstants;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Peanutfs
 * @description:
 * @date 2019/9/5
 */
@Slf4j
public class DateUtils {

	/**
	 * 标准日期格式
	 * */
	private final static String NORM_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 标准日期时间格式，精确到秒
	 * */
	private final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 标准日期格式 无连接线
	 * */
	private final static String NO_LINE_NORM_DATE_PATTERN = "yyyyMMdd";
	
	/**
	 * 标准日期（不含时间）格式化器
	 * */
	private static ThreadLocal<SimpleDateFormat> NORM_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>(){
		@Override
		synchronized protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(NORM_DATE_PATTERN);
		};
	};

	/**
	 * 标准日期时间格式化器
	 * */
	private static ThreadLocal<SimpleDateFormat> NORM_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>(){
		@Override
		synchronized protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(NORM_DATETIME_PATTERN);
		};
	};


	/**
	 * 标准日期（不含时间）格式化器 无连接线
	 * */
	private static ThreadLocal<SimpleDateFormat> NO_LINE_NORM_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>(){
		@Override
		synchronized protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(NO_LINE_NORM_DATE_PATTERN);
		};
	};
	

	/**
	 * 根据特定格式格式化日期
	 * 
	 * @param date 被格式化的日期
	 * @param format 格式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String format) {
		if(null == date){
			return SysConstants.EMPTY_STRING;
		}else{
			return new SimpleDateFormat(format).format(date);
		}
	}

	/**
	 * 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 被格式化的日期
	 * @return 格式化后的日期
	 */
	public static String formatDateTime(Date date) {
		if(null == date){
			return SysConstants.EMPTY_STRING;
		}else{
			return NORM_DATETIME_FORMAT.get().format(date);
		}
	}

	/**
	 * 格式 yyyy-MM-dd
	 * 
	 * @param date 被格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date) {
		if(null == date){
			return SysConstants.EMPTY_STRING;
		}else{
			return NORM_DATE_FORMAT.get().format(date);
		}
	}

	/**
	 * 构建Date对象
	 * 
	 * @param dateStr Date字符串
	 * @param simpleDateFormat 格式化器
	 * @return DateTime对象
	 */
	private static Date parse(String dateStr, SimpleDateFormat simpleDateFormat) {
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			log.error("[DateUtils.parse]时间转换出错e:{}", e);
			return null;
		}
	}

	/**
	 * 将特定格式的日期转换为Date对象
	 * 
	 * @param dateString 特定格式的日期
	 * @param format 格式，例如yyyy-MM-dd
	 * @return 日期对象
	 */
	public static Date parse(String dateString, String format) {
		return parse(dateString, new SimpleDateFormat(format));
	}

	/**
	 * 格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateString 标准形式的时间字符串
	 * @return 日期对象
	 */
	public static Date parseDateTime(String dateString) {
		return parse(dateString, NORM_DATETIME_FORMAT.get());
	}

	/**
	 * 格式yyyy-MM-dd
	 * 
	 * @param dateString 标准形式的日期字符串
	 * @return 日期对象
	 */
	public static Date parseDate(String dateString) {
		return parse(dateString, NORM_DATE_FORMAT.get());
	}

	/**
	 * 格式 yyyyMMdd
	 *
	 * @param date 被格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateNoLine(Date date) {
		if(null == date){
			return SysConstants.EMPTY_STRING;
		}else{
			return NO_LINE_NORM_DATE_FORMAT.get().format(date);
		}
	}
}
