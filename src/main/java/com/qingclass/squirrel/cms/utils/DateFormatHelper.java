package com.qingclass.squirrel.cms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatHelper {
	public static final String FORMAT_TIME_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_STR = "yyyy-MM-dd";

	public static String getTimeStr(String formatStr, Date d) {
		DateFormat df = new SimpleDateFormat(formatStr);

		return df.format(d);
	}

	public static String getTimeStr(Date d) {
		if (d == null)
			return "";
		return getTimeStr("yyyy-MM-dd HH:mm:ss", d);
	}

	public static Date getLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	public static Date getLastWeekSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	public static Date getMonthFirst() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	public static Date getMonthLast() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	public static String getNowTimeStr(String formatStr) {
		DateFormat df = new SimpleDateFormat(formatStr);

		return df.format(new Date());
	}

	public static String getDateStr(String date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		try {
			date1 = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return df.format(date1);
	}

	public static String getNowDateStr() {
		return getNowTimeStr("yyyy-MM-dd");
	}

	public static String getNowTimeStr() {
		return getNowTimeStr("yyyy-MM-dd HH:mm:ss");
	}

	public static String getNowTimeStamp() {
		long time = new Date().getTime();
		return String.valueOf(time / 1000);
	}

	public static void main(String[] args) {
		String s = DateFormatHelper.strtodatespetimestamp("2018-10-25 19:00");

	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * spe 我的课程表
	 * 
	 * @param time String
	 * @return
	 */
	public static long dateToyearTimestamp(String year) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = simpleDateFormat.parse(year);
			long ts = date.getTime() / 1000;
			return ts;
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 获取一个月的最后一天
	 *
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 判断是否润年
	 *
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * spe 我的课程表
	 * 
	 * @param time String
	 * @return
	 */
	public static long dateTomonthTimestamp(String month) {
		SimpleDateFormat simpleDateFormat = null;
		String month1 = month.substring(5, 7);
		int mon = Integer.parseInt(month1);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			Date date = simpleDateFormat.parse(month);
			long ts = date.getTime() / 1000;
			return ts;
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 日期转时间戳
	 * 
	 * @param time String
	 * @return
	 */
	public static String dateToMonth(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		try {
			Date date = simpleDateFormat.parse(time);
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 日期转时间戳
	 * 
	 * @param time String
	 * @return
	 */
	public static long dateToTimestamp(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse(time);
			long ts = date.getTime() / 1000;
			return ts;
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 *
	 * @param time date
	 * @return
	 */
	public static long dateToTimestamp(Date date) {
		try {
			long ts = date.getTime() / 1000;
			return ts;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 *
	 * @param time 2018-10-14 10:00:00转2018-10-14时间戳
	 * @return
	 */
	public static String strtodateday(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.format(simpleDateFormat.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转日期stamp
	 * 
	 * @param time
	 * @return
	 */
	public static String strtodatedaystamp(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(time).getTime() / 1000 + "";
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 转日期stamp
	 * 
	 * @param time
	 * @return
	 */
	public static String strtodatespetimestamp(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(time).getTime() / 1000 + "";
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer strtodatehour(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		try {
			return Integer.parseInt(dateFormat.format(simpleDateFormat.parse(time)));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 05月05日
	 * 
	 * @param time
	 * @return
	 */
	public static String strtodate(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
		try {
			return dateFormat.format(simpleDateFormat.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 05月05日 星期五 12:22
	 * 
	 * @param time
	 * @return
	 */
	public static String strtospedate(Date time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		try {
			return dateToWeek2(format.format(time)) + " " + dateFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int datesubtrct(String start, String end) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(start);
			mydate = myFormatter.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int hour = (int) ((date.getTime() - mydate.getTime()) / (60 * 60 * 1000));
		return hour;
	}

	public static String dateToWeek1(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(long lt) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDateMM(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long lt = new Long(s);
		Date date = new Date(lt * 1000);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDateHH(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		long lt = new Long(s);
		Date date = new Date(lt * 1000);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDateHour(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long lt = new Long(s);
		Date date = new Date(lt * 1000);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static String dateToWeekDate(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		String time = null;
		try {
			datet = f.parse(datetime);
			time = f.format(datet);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return time + " " + weekDays[w];
	}

	public static String dateToWeek2(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fff = new SimpleDateFormat("MM月dd日");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		String time = null;
		try {
			datet = f.parse(datetime);
			time = fff.format(datet);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return time + " " + weekDays[w];
	}

	public static String dateToWeek3(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static long getNowTimestamp() {
		try {
			long ts = new Date().getTime() / 1000;
			return ts;
		} catch (Exception e) {
			return 0;
		}
	}

	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(5, now.get(5) - day);
		return now.getTime();
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(5, now.get(5) + day);
		return now.getTime();
	}

	public static Date toDate(String sDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = null;
		try {
			dt = formatter.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			dt = null;
		}
		return dt;
	}

	public static int stamptodatehour(String key) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
		long lt = new Long(key);
		Date date = new Date(lt * 1000);
		res = simpleDateFormat.format(date);
		return Integer.parseInt(res);
	}

	public static Date getBeforeThirty() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -30);
		return now.getTime();
	}
}
