package utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2016/12/28.
 */
public class DateTimeDiff {

	/**
	 * The number of days in a 400 year cycle.
	 */
	private static final int DAYS_PER_CYCLE = 146097;
	/**
	 * The number of days from year zero to year 1970.
	 * There are five 400 year cycles from year zero to 2000.
	 * There are 7 leap years from 1970 to 2000.
	 */
	static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

	private static final int  ONE_SECOND = 1000;
	private static final int  ONE_MINUTE = 60*ONE_SECOND;
	private static final int  ONE_HOUR   = 60*ONE_MINUTE;
	private static final long ONE_DAY    = 24*ONE_HOUR;
	private static final long ONE_WEEK   = 7*ONE_DAY;

	private long begin_millis;
	private int begin_year;
	private int begin_month;
	private int begin_day;
	private int begin_hour;
	private int begin_minute;
	private int begin_second;
	private int begin_millisecond;

	private long end_millis;
	private int end_year;
	private int end_month;
	private int end_day;
	private int end_hour;
	private int end_minute;
	private int end_second;
	private int end_millisecond;

	//private int millis_diff;
	private int year_diff;
	private int month_diff;
	private int day_diff;
	private int hour_diff;
	private int minute_diff;
	private int second_diff;
	private int millisecond_diff;

	private int millis_diff_all;
	private int year_diff_all;
	private int month_diff_all;
	private int day_diff_all;
	private int hour_diff_all;
	private int minute_diff_all;
	private int second_diff_all;
	private int millisecond_diff_all;

	public DateTimeDiff(long begin, long end) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTimeInMillis(begin);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTimeInMillis(end);

		init(beginCalendar, endCalendar);
	}

	public DateTimeDiff(Date begin, Date end) {
		if (null == begin)
			throw new NullPointerException("begin must be not null");

		if (null == end)
			throw new NullPointerException("end must be not null");

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(begin);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);

		init(beginCalendar, endCalendar);
	}

	public DateTimeDiff(Calendar begin, Calendar end) {
		if (null == begin)
			throw new NullPointerException("begin must be not null");

		if (null == end)
			throw new NullPointerException("end must be not null");

		init(begin, end);
	}

	private void init(Calendar beginCalendar, Calendar endCalendar) {

		this.begin_millis = beginCalendar.getTimeInMillis();

		this.begin_year = beginCalendar.get(Calendar.YEAR);
		this.begin_month = beginCalendar.get(Calendar.MONTH) + 1;
		this.begin_day = beginCalendar.get(Calendar.DAY_OF_MONTH);
		this.begin_hour = beginCalendar.get(Calendar.HOUR_OF_DAY);
		this.begin_minute = beginCalendar.get(Calendar.MINUTE);
		this.begin_second = beginCalendar.get(Calendar.SECOND);
		//this.begin_millisecond = beginCalendar.get(Calendar.MILLISECOND);
		this.begin_millisecond = (int) (this.begin_millis % 1000);

		this.end_millis = endCalendar.getTimeInMillis();

		this.end_year = endCalendar.get(Calendar.YEAR);
		this.end_month = endCalendar.get(Calendar.MONTH) + 1;
		this.end_day = endCalendar.get(Calendar.DAY_OF_MONTH);
		this.end_hour = endCalendar.get(Calendar.HOUR_OF_DAY);
		this.end_minute = endCalendar.get(Calendar.MINUTE);
		this.end_second = endCalendar.get(Calendar.SECOND);
		//this.end_millisecond = beginCalendar.get(Calendar.MILLISECOND);
		this.end_millisecond = (int) (this.end_millis % 1000);

		int second_borrow = 0;
		if (end_millisecond < begin_millisecond) {
			millisecond_diff = 1000 + end_millisecond - begin_millisecond;
			second_borrow += 1;
		} else
			millisecond_diff = end_millisecond - begin_millisecond;

		int minute_borrow = 0;
		int cal_second = end_second - second_borrow;
		if (cal_second < begin_second) {
			second_diff = 60 + cal_second - begin_second;
			minute_borrow += 1;
		} else
			second_diff = cal_second - begin_second;

		int hour_borrow = 0;
		int cal_minute = end_minute - minute_borrow;
		if (cal_minute < begin_minute) {
			minute_diff = 60 + cal_minute - begin_minute;
			hour_borrow += 1;
		} else
			minute_diff = cal_minute - begin_minute;

		int day_borrow = 0;
		int cal_hour = end_hour - hour_borrow;
		if (cal_hour < begin_hour) {
			hour_diff = 24 + cal_hour - begin_hour;
			day_borrow += 1;
		} else
			hour_diff = cal_hour - begin_hour;

		int month_borrow = 0;
		int cal_day = end_day - day_borrow;
		if (cal_day < begin_day) {
			if (end_month != 1)
				day_diff = getDaysofMonth(end_year, end_month - 1) + cal_day - begin_day;
			else
				day_diff = getDaysofMonth(end_year - 1, 12) + cal_day - begin_day;
			month_borrow += 1;
		} else {
			day_diff = cal_day - begin_day;
			if (day_diff == 0) {
				if (end_month != 1)
					day_diff = getDaysofMonth(end_year, end_month - 1);
				else
					day_diff = getDaysofMonth(end_year - 1, 12);

				month_borrow += 1;
			}
		}

		int year_borrow = 0;
		int cal_month = end_month - month_borrow;
		if (cal_month < begin_month) {
			month_diff = 12 + cal_month - begin_month;
			year_borrow += 1;
		} else {
			month_diff = cal_month - begin_month;
			if (month_diff == 0) {
				month_diff = 12;
				year_borrow += 1;
			}
		}

		year_diff = end_year - year_borrow - begin_year;
	}

	public int yearDiff() {
		return year_diff;
	}

	public int yearDiffInAll() {
		int yearDiff;
		if (end_month >= begin_month)
			yearDiff = end_year - begin_year;
		else
			yearDiff = end_year - begin_year - 1;

		return yearDiff;
	}

	public int monthDiff() {
		return month_diff;
	}

	public int monthDiffInAll() {
		int monthDiff;
		if (end_month < begin_month)
			monthDiff = 12 + end_month - begin_month;
		else
			monthDiff = end_month - begin_month;
		monthDiff += yearDiffInAll() * 12;

		return monthDiff;
	}

	public int dayDiff() {
		return day_diff;
	}

	public int dayDiffInAll() {
		int dayDiff = (int) ((end_millis - begin_millis) / ONE_DAY);
		return dayDiff;
	}

	public int hourDiff() {
		return hour_diff;
	}

	public long hourDiffInAll() {
		long hourDiff = (end_millis - begin_millis) / ONE_HOUR;
		return hourDiff;
	}

	public int minuteDiff() {
		return minute_diff;
	}

	public long minuteDiffInAll() {
		long minuteDiff = (end_millis - begin_millis) / ONE_MINUTE;
		return minuteDiff;
	}

	public int secondDiff() {
		return second_diff;
	}

	public long secondDiffInAll() {
		long secondDiff = (end_millis - begin_millis) / ONE_SECOND;
		return secondDiff;
	}

	public int millisecondDiff() {
		return millisecond_diff;
	}

	public long millisecondDiffInAll() {
		long millisecondDiff = end_millis - begin_millis;
		return millisecondDiff;
	}

	/**
	 * 计算时间差(年), isInclude=true表示计算时包含月,天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略月,天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long yearsBetween(Date begin, Date end, boolean isInclude) {
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(begin);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		return yearsBetween(beginCal, endCal, isInclude);
	}

	/**
	 * 计算时间差(年), isInclude=true表示计算时包含月,天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略月,天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long yearsBetween(Temporal begin, Temporal end, boolean isInclude) {
		long yearsBetween = 0;

		if (isInclude) {
			yearsBetween = ChronoUnit.YEARS.between(begin, end);
		} else {
			Year beginYearMonth = Year.from(begin);
			Year endYearMonth = Year.from(end);

			yearsBetween = ChronoUnit.YEARS.between(beginYearMonth, endYearMonth);
		}

		return yearsBetween;
	}

	/**
	 * 计算时间差(年), isInclude=true表示计算时包含月,天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略月,天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long yearsBetween(Calendar begin, Calendar end, boolean isInclude) {
		long yearsBetween = 0;

		Calendar beginCal;
		Calendar endCal;

		if (begin.after(end)) {
			beginCal = begin;
			endCal = (Calendar) end.clone(); //复制

			while (endCal.before(beginCal)) {
				endCal.add(Calendar.YEAR, 1);
				yearsBetween++;
			}

			yearsBetween = -yearsBetween;
		} else {
			beginCal = (Calendar) begin.clone(); //复制
			endCal = end;

			while (beginCal.before(endCal)) {
				beginCal.add(Calendar.YEAR, 1);
				yearsBetween++;
			}
		}

		if (isInclude) {
			if (beginCal.equals(endCal))
				return yearsBetween;
			else
				return yearsBetween - 1;
		} else {
			return yearsBetween;
		}
	}

	/**
	 * 计算时间差(月), isInclude=true表示计算时包含天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long monthsBetween(Date begin, Date end, boolean isInclude) {
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(begin);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		return monthsBetween(beginCal, endCal, isInclude);
	}

	/**
	 * 计算时间差(月), isInclude=true表示计算时包含天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long monthsBetween(Temporal begin, Temporal end, boolean isInclude) {
		long monthsBetween = 0;

		if (isInclude) {
			monthsBetween = ChronoUnit.MONTHS.between(begin, end);
		} else {
			YearMonth beginYearMonth = YearMonth.from(begin);
			YearMonth endYearMonth = YearMonth.from(end);

			monthsBetween = ChronoUnit.MONTHS.between(beginYearMonth, endYearMonth);
		}

		return monthsBetween;
	}

	/**
	 * 计算时间差(月), isInclude=true表示计算时包含天,时,分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略天,时,分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long monthsBetween(Calendar begin, Calendar end, boolean isInclude) {
		long monthsBetween = 0;

		Calendar beginCal;
		Calendar endCal;

		if (begin.after(end)) {
			beginCal = begin;
			endCal = (Calendar) end.clone(); //复制

			while (endCal.before(beginCal)) {
				endCal.add(Calendar.MONTH, 1);
				monthsBetween++;
			}

			monthsBetween = -monthsBetween;
		} else {
			beginCal = (Calendar) begin.clone(); //复制
			endCal = end;

			while (beginCal.before(endCal)) {
				beginCal.add(Calendar.MONTH, 1);
				monthsBetween++;
			}
		}

		if (isInclude) {
			if (beginCal.equals(endCal))
				return monthsBetween;
			else
				return monthsBetween - 1;
		} else {
			return monthsBetween;
		}
	}

	/**
	 * 计算时间差(天), isInclude=true表示计算时包含时间部分,
	 * 				 isInclude=false表示计算时忽略时间部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long daysBetween(Date begin, Date end, boolean isInclude) {
		long daysBetween = 0;
		if (isInclude) {
			daysBetween = (end.getTime() - begin.getTime()) / ONE_DAY;
		} else {
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(begin);
			beginCal.set(Calendar.HOUR, 0);
			beginCal.set(Calendar.MINUTE, 0);
			beginCal.set(Calendar.SECOND, 0);
			beginCal.set(Calendar.MILLISECOND, 0);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(end);
			endCal.set(Calendar.HOUR, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.MILLISECOND, 0);

			daysBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_DAY;
		}
		return daysBetween;
	}

	/**
	 * 计算时间差(天), isInclude=true表示计算时包含时间部分,
	 * 				 isInclude=false表示计算时忽略时间部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long daysBetween(Temporal begin, Temporal end, boolean isInclude) {
		long daysBetween = 0;
		if (isInclude) {
			daysBetween = ChronoUnit.DAYS.between(begin, end);
		} else {
			LocalDate beginDate = LocalDate.from(begin);
			LocalDate endDate = LocalDate.from(end);
			daysBetween = ChronoUnit.DAYS.between(beginDate, endDate);
		}
		return daysBetween;
	}

	/**
	 * 计算时间差(天), isInclude=true表示计算时包含时间部分,
	 * 				 isInclude=false表示计算时忽略时间部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long daysBetween(Calendar begin, Calendar end, boolean isInclude) {
		long daysBetween = 0;

		if (isInclude) {
			Calendar beginCal = begin;
			Calendar endCal = end;

			daysBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_DAY;
		} else {
			Calendar beginCal = (Calendar) begin.clone(); //复制
			beginCal.set(Calendar.HOUR, 0);
			beginCal.set(Calendar.MINUTE, 0);
			beginCal.set(Calendar.SECOND, 0);
			beginCal.set(Calendar.MILLISECOND, 0);
			Calendar endCal = (Calendar) end.clone();
			endCal.set(Calendar.HOUR, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.MILLISECOND, 0);

			daysBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_DAY;
		}

		return daysBetween;
	}

	/**
	 * 计算时间差(小时), isInclude=true表示计算时包含分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long hoursBetween(Date begin, Date end, boolean isInclude) {
		long hoursBetween = 0;
		if (isInclude) {
			hoursBetween = (end.getTime() - begin.getTime()) / ONE_HOUR;
		} else {
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(begin);
			beginCal.set(Calendar.MINUTE, 0);
			beginCal.set(Calendar.SECOND, 0);
			beginCal.set(Calendar.MILLISECOND, 0);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(end);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.MILLISECOND, 0);

			hoursBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_HOUR;
		}
		return hoursBetween;
	}

	/**
	 * 计算时间差(小时), isInclude=true表示计算时包含分,秒,毫秒部分,
	 * 				 isInclude=false表示计算时忽略分,秒,毫秒部分.
	 * @param begin
	 * @param end
	 * @param isInclude
	 * @return
	 */
	public static long hoursBetween(Calendar begin, Calendar end, boolean isInclude) {
		long hoursBetween = 0;

		if (isInclude) {
			Calendar beginCal = begin;
			Calendar endCal = end;

			hoursBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_HOUR;
		} else {
			Calendar beginCal = (Calendar) begin.clone(); //复制
			beginCal.set(Calendar.MINUTE, 0);
			beginCal.set(Calendar.SECOND, 0);
			beginCal.set(Calendar.MILLISECOND, 0);
			Calendar endCal = (Calendar) end.clone();
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.MILLISECOND, 0);

			hoursBetween = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / ONE_HOUR;
		}

		return hoursBetween;
	}

	/**
	 * 默认返回1970年指定月份的天数
	 * @param month
	 * @return
	 */
	public static int getDaysofMonth(int month) {
		return getDaysofMonth(1970, month);
	}

	public static int getDaysofMonth(long year, int month) {
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				if (isLeapYear(year))
					return 29;
				else
					return 28;
			default:
				throw new IllegalArgumentException("month must between 1 and 12");
		}
	}

	public static boolean isLeapYear(long prolepticYear) {
		return ((prolepticYear & 3) == 0) && ((prolepticYear % 100) != 0 || (prolepticYear % 400) == 0);
	}

	@Override
	public String toString() {
		StringBuilder beginSb = new StringBuilder();
		beginSb.append(begin_year).append('-');
		beginSb.append(begin_month).append('-');
		beginSb.append(begin_day).append(' ');
		beginSb.append(begin_hour).append(':');
		beginSb.append(begin_minute).append(':');
		beginSb.append(begin_second).append('.');
		beginSb.append(begin_millisecond);

		StringBuilder endSb = new StringBuilder();
		endSb.append(end_year).append('-');
		endSb.append(end_month).append('-');
		endSb.append(end_day).append(' ');
		endSb.append(end_hour).append(':');
		endSb.append(end_minute).append(':');
		endSb.append(end_second).append('.');
		endSb.append(end_millisecond);

		StringBuilder builder = new StringBuilder();
		builder.append(beginSb).append('\t').append(endSb).append("\r\n");
		builder.append(yearDiff()).append('-');
		builder.append(monthDiff()).append('-');
		builder.append(dayDiff()).append(' ');
		builder.append(hourDiff()).append(':');
		builder.append(minuteDiff()).append(':');
		builder.append(secondDiff()).append('.');
		builder.append(millisecondDiff());

		builder.append("\r\n");
		builder.append("year:").append(yearDiffInAll()).append("\r\n");
		builder.append("month:").append(monthDiffInAll()).append("\r\n");
		builder.append("day:").append(dayDiffInAll()).append("\r\n");
		builder.append("hour:").append(hourDiffInAll()).append("\r\n");
		builder.append("minute:").append(minuteDiffInAll()).append("\r\n");
		builder.append("second:").append(secondDiffInAll()).append("\r\n");
		builder.append("millisecond:").append(millisecondDiffInAll());

		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date begin = sdf.parse("2016-02-28 00:00:00");
		Date end = sdf.parse("2016-03-28 00:00:00");

		LocalDateTime beginDateTime = LocalDateTime.ofInstant(begin.toInstant(), ZoneId.systemDefault());
		LocalDateTime endDateTime = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

		LocalDate beginDate = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		System.out.println("Date->daysBetween:" + DateTimeDiff.daysBetween(begin, end, false));
		System.out.println("LocalDateTime->daysBetween:" + DateTimeDiff.daysBetween(beginDateTime, endDateTime, false));
		System.out.println("LocalDate->daysBetween:" + DateTimeDiff.daysBetween(beginDate, endDate, false));

		System.out.println("Date->monthsBetween:" + DateTimeDiff.monthsBetween(begin, end, true));

		//DateTimeDiff diff = new DateTimeDiff(begin, end);
		//DateTimeDiff diff = new DateTimeDiff(sdf.parse("1970-11-01 08:00:00").getTime(), sdf.parse("2016-08-28 09:30:30").getTime());
		//DateTimeDiff diff = new DateTimeDiff(sdf.parse("2016-08-28 09:30:30").getTime(), sdf.parse("1970-11-01 08:00:00").getTime());

		//System.out.println(diff);
		beginDate = LocalDate.of(2017, 1, 10);
		endDate = LocalDate.of(2017, 1, 11);
		long daysBetween = ChronoUnit.DAYS.between(beginDate, endDate);
		System.out.println(daysBetween);
	}
}
