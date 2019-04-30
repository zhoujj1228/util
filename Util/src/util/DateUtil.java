package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;


public class DateUtil {

	public static void main(String[] args) {
		
	}
	public static String getDateWithDhms(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");
        String result = sdf.format(date);
        return result;
    }
	
	/**
	 * 根据模板生成时间字符串
	 * @param pattern 
	 * yyyy代表年
	 * MM代表月
	 * dd代表天
	 * hh代表小时
	 * mm代表分
	 * ss代表秒
	 * SSS代表毫秒
	 * @return 对应时间
	 */
	public static String getDateByPattern(String pattern){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String result = sdf.format(date);
		return result;
	}
	
	
	public static String changeDateFormat(String sourceFormat, String targetFormat, String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
		Date date = sdf.parse(dateStr);
		SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat);
		String result =  sdf2.format(date);
		return result;
	}
	
	public static Date changeUnStandardDateToDate(String regex, String dateStr) throws ParseException{
		String standardDateStr = changNumberDate(dateStr, regex, "");
		Date date = parseDate(standardDateStr, "yyyyMMdd");
		return date;
	}
	
	/**
	 * 把2017/6/30转化为2017/06/30这种格式的日期
	 * @param dateStr
	 * @param regex
	 * @param newRegex
	 * @return
	 */
	public static String changNumberDate(String dateStr, String regex, String newRegex){
		String[] split = dateStr.split(regex);
		String month = split[1];
		String day = split[2];
		if(month.length() < 2){
			month = "0" + month;
		}
		if(day.length() < 2){
			day = "0" + day;
		}
		return split[0] + newRegex + month + newRegex + day;
	}
	
	public static String changeDateToNumberDate(Date date, String regex){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + regex + "MM" + regex + "dd");
        String dateStr = sdf.format(date);
        if(regex.equals(".")){
            dateStr = dateStr.replaceAll("\\.0", regex);
        }else{
            dateStr = dateStr.replaceAll(regex + "0", regex);
        }
        
        return dateStr;
    }
	
	/**
	 * 得到dateNum天数后的日期
	 * @param date
	 * @param dateNum
	 * @return
	 */
	public static Date getAfterDate(Date date, int dateNum){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, dateNum);
		Date resultDate = c.getTime();
		return resultDate;
	}
	
	/**
	 * 根据模板与时间字符串生成Date对象
	 * @param pattern 
	 * yyyy代表年
	 * MM代表月
	 * dd代表天
	 * hh代表小时
	 * mm代表分
	 * ss代表秒
	 * SSS代表毫秒
	 * @return 对应时间
	 */
	public static Date parseDate(String dateStr, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 根据模板生成时间字符串
	 * yyyy代表年
	 * MM代表月
	 * dd代表天
	 * hh代表小时
	 * mm代表分
	 * ss代表秒
	 * SSS代表毫秒
	 * @return 对应时间
	 */
	public static String parseDate(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = null;
		dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 1 为星期天
	 * 2 为星期一
	 * 3 为星期二
	 * 4 为星期三
	 * 5 为星期四
	 * 6 为星期五
	 * 7 为星期六
	 * @param date
	 * @return
	 */
	@Test
	public static int getWeekDateInt(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int i = c.get(Calendar.DAY_OF_WEEK);
		return i;
	}
	
	public static Date getNextWeekDate(Date date){
		Date nextDate = DateUtil.getAfterDate(date, 1);
		while(DateUtil.getWeekDateInt(nextDate) == 7 || DateUtil.getWeekDateInt(nextDate) ==1){
			nextDate = DateUtil.getAfterDate(nextDate, 1);
		}
		return nextDate;
	}
	
	/**
	 * 获取下几个工作日日期,跳过周末
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getAfterWorkDate(Date date, int num) {
		Date result = date;
		for (int i = 0; i < num; i++) {
			result = getNextWeekDate(result);
		}
		return result;
	}
}
