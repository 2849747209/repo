package utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/9/23.
 */
public class NumberUtils {
	//是否是数字
	private final static Pattern numPtn = Pattern.compile("^[+-]?\\d+(?:\\.\\d+)?$");
	//是否是正数
	private final static Pattern positiveNumPtn = Pattern.compile("^[+]?\\d+(?:\\.\\d+)?$");
	//是否是负数
	private final static Pattern negativeNumPtn = Pattern.compile("^-\\d+(?:\\.\\d+)?$");

	//是否是整数
	private final static Pattern intPtn = Pattern.compile("^[+-]?\\d+$");
	//是否是正整数
	private final static Pattern positiveIntPtn = Pattern.compile("^[+]?\\d+$");
	//是否是负整数
	private final static Pattern negativeIntPtn = Pattern.compile("^-\\d+$");

	//是否是小数
	private final static Pattern decPtn = Pattern.compile("^[+-]?\\d+\\.(\\d+)$");
	//是否是正小数
	private final static Pattern positiveDecPtn = Pattern.compile("^[+]?\\d+\\.(\\d+)$");
	//是否是负正小数
	private final static Pattern negativeDecPtn = Pattern.compile("^-\\d+\\.(\\d+)$");

	final static char[] digits = {
		'0' , '1' , '2' , '3' , '4' , '5' ,
		'6' , '7' , '8' , '9' , 'a' , 'b' ,
		'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
		'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
		'o' , 'p' , 'q' , 'r' , 's' , 't' ,
		'u' , 'v' , 'w' , 'x' , 'y' , 'z'
	};

	private final static char [] DigitTens = {
		'0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
		'1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
		'2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
		'3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
		'4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
		'5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
		'6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
		'7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
		'8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
		'9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
	};

	private final static char [] DigitOnes = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	};

	private final static long[] sizeTable = {
		10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L,
		10000000L, 100000000L, 1000000000L, 10000000000L,
		100000000000L, 1000000000000L, 10000000000000L,
		100000000000000L, 1000000000000000L, 10000000000000000L,
		100000000000000000L, 1000000000000000000L, Long.MAX_VALUE
	};

	/**
	 * 获取x表示的数值的字符数组长度
	 * @param x
	 * @return
	 */
	public static int getCharsLength(long x) {
		if (x < 0) {
			if (x == Long.MIN_VALUE) {
				return 20;
			} else {
				x = -x;
				for (int i = 0; i < 19; i++) {
					if (x < sizeTable[i])
						return i + 2;
				}
				return 20;
			}
		} else {
			for (int i = 0; i < 19; i++) {
				if (x < sizeTable[i])
					return i + 1;
			}
			return 19;
		}
	}

	/**
	 * 把val转换并填充到buf上
	 * @param val
	 * @param from
	 * @param buf
	 */
	public static void longToChars(long val, int from, char[] buf) {
		if (null != buf) {
			if (val == Long.MIN_VALUE) {
				char[] chars = {'-', '9', '2', '2', '3', '3', '7', '2', '0', '3', '6', '8', '5', '4', '7', '7', '5', '8', '0', '8'};
				System.arraycopy(chars, 0, buf, from, 20);
			} else {
				int pos = from + getCharsLength(val);
				if (pos <= buf.length) {
					int r;
					int charPos = pos;
					char sign = 0;

					if (val < 0) {
						sign = '-';
						val = -val;
					}

					// Get 2 digits/iteration using longs until quotient fits into an int
					while (val > Integer.MAX_VALUE) {
						long q = val / 100;
						// really: r = i - (q * 100);
						r = (int) (val - ((q << 6) + (q << 5) + (q << 2)));
						val = q;
						buf[--charPos] = DigitOnes[r];
						buf[--charPos] = DigitTens[r];
					}

					// Get 2 digits/iteration using ints
					int q;
					int i = (int) val;
					while (i >= 65536) {
						q = i / 100;
						// really: r = i - (q * 100);
						r = i - ((q << 6) + (q << 5) + (q << 2));
						i = q;
						buf[--charPos] = DigitOnes[r];
						buf[--charPos] = DigitTens[r];
					}

					// Fall thru to fast mode for smaller numbers
					// assert(i <= 65536, i);
					for (;;) {
						q = (i * 52429) >>> (16+3);
						r = i - ((q << 3) + (q << 1));  // r = i - (q * 10) ...
						buf[--charPos] = digits[r];
						i = q;
						if (i == 0)
							break;
					}
					if (sign != 0) {
						buf[--charPos] = sign;
					}
				} else {
					throw new ArrayIndexOutOfBoundsException("array length not enough from " + from + " to " + buf.length);
				}
			}
		} else
			throw new NullPointerException("buf must be not null.");
	}

	/**
	 * 把val转换并填充到buf上
	 * @param val
	 * @param from
	 * @param buf
	 */
	public static void intToChars(int val, int from, char[] buf) {
		if (null != buf) {
			if (val == Integer.MIN_VALUE) {
				char[] chars = {'-', '2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};
				System.arraycopy(chars, 0, buf, from, 11);
			} else {
				int pos = from + getCharsLength(val);
				if (pos <= buf.length) {
					int q, r;
					int charPos = pos;
					char sign = 0;

					if (val < 0) {
						sign = '-';
						val = -val;
					}

					// Generate two digits per iteration
					while (val >= 65536) {
						q = val / 100;
						// really: r = i - (q * 100);
						r = val - ((q << 6) + (q << 5) + (q << 2));
						val = q;
						buf [--charPos] = DigitOnes[r];
						buf [--charPos] = DigitTens[r];
					}

					// Fall thru to fast mode for smaller numbers
					// assert(i <= 65536, i);
					for (;;) {
						q = (val * 52429) >>> (16+3);
						r = val - ((q << 3) + (q << 1));  // r = i - (q * 10) ...
						buf [--charPos] = digits [r];
						val = q;
						if (val == 0)
							break;
					}
					if (sign != 0) {
						buf [--charPos] = sign;
					}
				} else {
					throw new ArrayIndexOutOfBoundsException("array length not enough from " + from + " to " + buf.length);
				}
			}
		} else
			throw new NullPointerException("buf must be not null.");
	}

	/**
	 * 判断字符串是否是数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		if (null != number)
			if (numPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是正数
	 * @param number
	 * @return
	 */
	public static boolean isPosNumber(String number) {
		if (null != number)
			if (positiveNumPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是负数
	 * @param number
	 * @return
	 */
	public static boolean isNegNumber(String number) {
		if (null != number)
			if (negativeNumPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是整数
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		if (null != number)
			if (intPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是正整数
	 * @param number
	 * @return
	 */
	public static boolean isPosInteger(String number) {
		if (null != number)
			if (positiveIntPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是负整数
	 * @param number
	 * @return
	 */
	public static boolean isNegInteger(String number) {
		if (null != number)
			if (negativeIntPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是小数
	 * @param number
	 * @return
	 */
	public static boolean isDecimal(String number) {
		if (null != number)
			if (decPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是正小数
	 * @param number
	 * @return
	 */
	public static boolean isPosDecimal(String number) {
		if (null != number)
			if (positiveDecPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是正小数
	 * @param number
	 * @return
	 */
	public static boolean isNegDecimal(String number) {
		if (null != number)
			if (negativeDecPtn.matcher(number).matches())
				return true;
		return false;
	}

	/**
	 * 判断字符串是否是小数且精度等于scale
	 * @param number
	 * @return
	 */
	public static boolean isScaleEq(String number, int scale) {
		if (null != number) {
			Matcher matcher = decPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() == scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度等于scale
	 * @param number
	 * @return
	 */
	public static boolean isPosAndScaleEq(String number, int scale) {
		if (null != number) {
			Matcher matcher = positiveDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() == scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是负小数且精度等于scale
	 * @param number
	 * @return
	 */
	public static boolean isNegAndScaleEq(String number, int scale) {
		if (null != number) {
			Matcher matcher = negativeDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() == scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是小数且精度大于scale
	 * @param number
	 * @return
	 */
	public static boolean isScaleGt(String number, int scale) {
		if (null != number) {
			Matcher matcher = decPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() > scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度大于scale
	 * @param number
	 * @return
	 */
	public static boolean isPosAndScaleGt(String number, int scale) {
		if (null != number) {
			Matcher matcher = positiveDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() > scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度大于scale
	 * @param number
	 * @return
	 */
	public static boolean isNegAndScaleGt(String number, int scale) {
		if (null != number) {
			Matcher matcher = negativeDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() > scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是小数且精度小于scale
	 * @param number
	 * @return
	 */
	public static boolean isScaleLt(String number, int scale) {
		if (null != number) {
			Matcher matcher = decPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() < scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度小于scale
	 * @param number
	 * @return
	 */
	public static boolean isPosAndScaleLt(String number, int scale) {
		if (null != number) {
			Matcher matcher = positiveDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() < scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度小于scale
	 * @param number
	 * @return
	 */
	public static boolean isNegAndScaleLt(String number, int scale) {
		if (null != number) {
			Matcher matcher = negativeDecPtn.matcher(number);
			if (matcher.matches()) {
				if (matcher.group(1).length() < scale)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是小数且精度在lower和highter之间
	 * @param number
	 * @param lower
	 * @param highter
	 * @return
	 */
	public static boolean isScaleBtw(String number, int lower, int highter) {
		if (null != number) {
			Matcher matcher = decPtn.matcher(number);
			if (matcher.matches()) {
				int len = matcher.group(1).length();
				if (len >= lower && len <= highter)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是正小数且精度在lower和highter之间
	 * @param number
	 * @param lower
	 * @param highter
	 * @return
	 */
	public static boolean isPosAndScaleBtw(String number, int lower, int highter) {
		if (null != number) {
			Matcher matcher = positiveDecPtn.matcher(number);
			if (matcher.matches()) {
				int len = matcher.group(1).length();
				if (len >= lower && len <= highter)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断字符串是否是负小数且精度在lower和highter之间
	 * @param number
	 * @param lower
	 * @param highter
	 * @return
	 */
	public static boolean isNegAndScaleBtw(String number, int lower, int highter) {
		if (null != number) {
			Matcher matcher = negativeDecPtn.matcher(number);
			if (matcher.matches()) {
				int len = matcher.group(1).length();
				if (len >= lower && len <= highter)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}
}
