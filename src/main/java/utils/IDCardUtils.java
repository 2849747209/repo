package utils;

import java.util.regex.Pattern;

/**
 * Created by admin on 2016/9/14.
 */
public class IDCardUtils {

	private IDCardUtils() {
		throw new UnsupportedOperationException("IDCardUtils.class 不能被构造.");
	}

	private static Pattern fifteenIDNoPattern = Pattern.compile("\\d{15}");

	private static Pattern eighteenIDNoPattern = Pattern.compile("\\d{17}[0-9X]");

	private static char getVerifyCode(final String idNo) {
		char[] Ai = idNo.toCharArray();
		final int[] Wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
		int sum = 0;
		for(int i = 0; i < Wi.length; i++){
			sum += (Ai[i] - '0') * Wi[i];
		}

		final char[] verifyCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
		return verifyCode[sum % 11];
	}

	/**
	 * 判断传入的参数是否为合法的身份证号
	 * @param idNo
	 * @return
	 */
	public static boolean isIDNo(final String idNo) {
		if (null != idNo) {
			final char[] array = idNo.toCharArray();
			if (array.length == 18) {
				for (int i=0; i<17; i++) {
					char c = array[i];
					if (c < '0' && c > '9')
						return false;
				}
				if (array[17] != getVerifyCode(idNo))
					return false;
				return true;
			} else if (array.length == 15) {
				for (int i=0; i<15; i++) {
					char c = array[i];
					if (c < '0' && c > '9')
						return false;
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断传入的参数是否为合法的18位身份证号
	 * @param eighteenIDNo
	 * @return
	 */
	public static boolean is18IDNo(final String eighteenIDNo) {
		if (null != eighteenIDNo) {
			int len = eighteenIDNo.length();
			if (len == 18) {
				final char[] array = eighteenIDNo.toCharArray();
				for (int i=0; i<17; i++) {
					char c = array[i];
					if (c < '0' && c > '9')
						return false;
				}
				if (array[17] != getVerifyCode(eighteenIDNo))
					return false;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断传入的参数是否为合法的15位身份证号
	 * @param fifteenIDNo
	 * @return
	 */
	public static boolean is15IDNo(final String fifteenIDNo) {
		if (null != fifteenIDNo) {
			int len = fifteenIDNo.length();
			if (len == 15) {
				final char[] array = fifteenIDNo.toCharArray();
				for (int i=0; i<15; i++) {
					char c = array[i];
					if (c < '0' && c > '9')
						return false;
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 验证并返回合法的18位身份证号
	 * @param idIDNo
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String get18IDNo(final String idIDNo) throws IllegalArgumentException {
		if(isIDNo(idIDNo)) {
			if (idIDNo.length() == 15) {
				StringBuilder builder = new StringBuilder(18);
				builder.append(idIDNo).insert(6, "19");
				builder.append(getVerifyCode(builder.toString()));
				return builder.toString();
			} else {
				return idIDNo;
			}
		} else {
			throw new IllegalArgumentException(idIDNo + "不是身份证号.");
		}
	}

	/*public static void main(String[] args) throws Exception {
		String myIDNo = "450803870403917";
		System.out.println(IDCardUtils.is18IDNo("450803198704039177"));
		System.out.println("450803198704039177".equals(IDCardUtils.get18IDNo(myIDNo)));
		System.out.println(IDCardUtils.isIDNo(myIDNo));
	}*/
}
