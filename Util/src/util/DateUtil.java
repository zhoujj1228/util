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
	 * ����ģ������ʱ���ַ���
	 * @param pattern 
	 * yyyy������
	 * MM������
	 * dd������
	 * hh����Сʱ
	 * mm�����
	 * ss������
	 * SSS�������
	 * @return ��Ӧʱ��
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
	 * ��2017/6/30ת��Ϊ2017/06/30���ָ�ʽ������
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
	 * �õ�dateNum�����������
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
	 * ����ģ����ʱ���ַ�������Date����
	 * @param pattern 
	 * yyyy������
	 * MM������
	 * dd������
	 * hh����Сʱ
	 * mm�����
	 * ss������
	 * SSS�������
	 * @return ��Ӧʱ��
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
	 * ����ģ������ʱ���ַ���
	 * yyyy������
	 * MM������
	 * dd������
	 * hh����Сʱ
	 * mm�����
	 * ss������
	 * SSS�������
	 * @return ��Ӧʱ��
	 */
	public static String parseDate(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = null;
		dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 1 Ϊ������
	 * 2 Ϊ����һ
	 * 3 Ϊ���ڶ�
	 * 4 Ϊ������
	 * 5 Ϊ������
	 * 6 Ϊ������
	 * 7 Ϊ������
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
	 * ��ȡ�¼�������������,������ĩ
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
