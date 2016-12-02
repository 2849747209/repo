package utils.convertor;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by admin on 2016/12/2.
 */
public class Formatter {

	private static String Date_Format;
	private static String Time_Format;
	private static String Datetime_Format;
	private static String Timestamp_Format;

	private final static ThreadLocal<SimpleDateFormat> dFmt = new ThreadLocal<SimpleDateFormat>();
	private final static ThreadLocal<SimpleDateFormat> tFmt = new ThreadLocal<SimpleDateFormat>();
	private final static ThreadLocal<SimpleDateFormat> dtFmt = new ThreadLocal<SimpleDateFormat>();
	private final static ThreadLocal<SimpleDateFormat> tsFmt = new ThreadLocal<SimpleDateFormat>();

	private static DateTimeFormatter ldFmt;
	private static DateTimeFormatter ltFmt;
	private static DateTimeFormatter ldtFmt;
	private static DateTimeFormatter ltsFmt;

	static void init() {
		initDateFormatter("yyyy-MM-dd");
		initTimeFormatter("HH:mm:ss");
		initDateTimeFormatter("yyyy-MM-dd HH:mm:ss");
		initTimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS");
	}

	static void initDateTimeFormatter(String pattern) {
		if (null != pattern) {
			Datetime_Format = pattern;
			ldtFmt = DateTimeFormatter.ofPattern(Datetime_Format);
		}
	}

	static void initDateFormatter(String pattern) {
		if (null != pattern) {
			Date_Format = pattern;
			ldFmt = DateTimeFormatter.ofPattern(Date_Format);
		}
	}

	static void initTimeFormatter(String pattern) {
		if (null != pattern) {
			Time_Format = pattern;
			ltFmt = DateTimeFormatter.ofPattern(Time_Format);
		}
	}

	static void initTimestampFormatter(String pattern) {
		if (null != pattern) {
			Timestamp_Format = pattern;
			ltsFmt = DateTimeFormatter.ofPattern(Timestamp_Format);
		}
	}

	public static String toDateTime(Date target) {
		SimpleDateFormat sdf = dtFmt.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(Datetime_Format);
			dtFmt.set(sdf);
		}

		return sdf.format(target);
	}

	public static Date parseDateTime(String target) {
		try {
			SimpleDateFormat sdf = dtFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Datetime_Format);
				dtFmt.set(sdf);
			}

			return sdf.parse(target);
		} catch (ParseException e) {
			throw new ClassCastException(target + " cannot be cast to java.util.Date");
		}
	}

	public static String toDate(java.sql.Date target) {
		SimpleDateFormat sdf = dFmt.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(Date_Format);
			dFmt.set(sdf);
		}

		return sdf.format(target);
	}

	public static java.sql.Date parseDate(String target) {
		try {
			SimpleDateFormat sdf = dFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Date_Format);
				dFmt.set(sdf);
			}

			Date date = sdf.parse(target);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			throw new ClassCastException(target + " cannot be cast to java.sql.Date");
		}
	}

	public static String toTime(Time target) {
		SimpleDateFormat sdf = tFmt.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(Time_Format);
			tFmt.set(sdf);
		}

		return sdf.format(target);
	}

	public static Time parseTime(String target) {
		try {
			SimpleDateFormat sdf = tFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Time_Format);
				tFmt.set(sdf);
			}

			Date date = sdf.parse(target);
			return new Time(date.getTime());
		} catch (ParseException e) {
			throw new ClassCastException(target + " cannot be cast to java.sql.Time");
		}
	}

	public static String toTimestamp(Timestamp target) {
		SimpleDateFormat sdf = tsFmt.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(Timestamp_Format);
			tsFmt.set(sdf);
		}

		return sdf.format(target);
	}

	public static Timestamp parseTimestamp(String target) {
		try {
			SimpleDateFormat sdf = tsFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Timestamp_Format);
				tsFmt.set(sdf);
			}

			Date date = sdf.parse(target);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new ClassCastException(target + " cannot be cast to java.sql.Timestamp");
		}
	}

	public static String toLocalDateTime(LocalDateTime target) {
		return ldtFmt.format(target);
	}

	public static LocalDateTime parseLocalDateTime(String target){
		return LocalDateTime.parse(target, ldtFmt);
	}

	public static String toLocalDate(LocalDate target) {
		return ldFmt.format(target);
	}

	public static LocalDate parseLocalDate(String target) {
		return LocalDate.parse(target, ldFmt);
	}

	public static String toLocalTime(LocalTime target) {
		return ltFmt.format(target);
	}

	public static LocalTime parseLocalTime(String target) {
		return LocalTime.parse(target, ltFmt);
	}
}
