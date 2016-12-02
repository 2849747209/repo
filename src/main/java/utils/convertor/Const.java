package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/11/17.
 */
public class Const {
	public static final BigDecimal BigDecimal_ZERO = BigDecimal.ZERO;
	public static final BigDecimal BigDecimal_ONE = BigDecimal.ONE;
	public static final BigDecimal BigDecimal_TWO = BigDecimal.valueOf(2l);
	public static final BigDecimal BigDecimal_THREE = BigDecimal.valueOf(3l);
	public static final BigDecimal BigDecimal_FOUR = BigDecimal.valueOf(4l);
	public static final BigDecimal BigDecimal_FIVE = BigDecimal.valueOf(5l);
	public static final BigDecimal BigDecimal_SIX = BigDecimal.valueOf(6);
	public static final BigDecimal BigDecimal_SEVEN = BigDecimal.valueOf(7);
	public static final BigDecimal BigDecimal_EIGHT = BigDecimal.valueOf(8);
	public static final BigDecimal BigDecimal_NINE = BigDecimal.valueOf(9);
	public static final BigDecimal BigDecimal_TEN = BigDecimal.TEN;

	public static final BigDecimal BigDecimal_BYTE_MAX_VALUE = new BigDecimal(Byte.MAX_VALUE);
	public static final BigDecimal BigDecimal_BYTE_MIN_VALUE = new BigDecimal(Byte.MIN_VALUE);

	public static final BigDecimal BigDecimal_SHORT_MAX_VALUE = new BigDecimal(Short.MAX_VALUE);
	public static final BigDecimal BigDecimal_SHORT_MIN_VALUE = new BigDecimal(Short.MIN_VALUE);

	public static final BigDecimal BigDecimal_INTEGER_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
	public static final BigDecimal BigDecimal_INTEGER_MIN_VALUE = new BigDecimal(Integer.MIN_VALUE);

	public static final BigDecimal BigDecimal_LONG_MAX_VALUE = new BigDecimal(Long.MAX_VALUE);
	public static final BigDecimal BigDecimal_LONG_MIN_VALUE = new BigDecimal(Long.MIN_VALUE);

	public static final BigDecimal BigDecimal_FLOAT_MAX_VALUE = new BigDecimal(Float.MAX_VALUE);
	public static final BigDecimal BigDecimal_FLOAT_MIN_VALUE = new BigDecimal(Float.MIN_VALUE);

	public static final BigDecimal BigDecimal_DOUBLE_MAX_VALUE = new BigDecimal(Double.MAX_VALUE);
	public static final BigDecimal BigDecimal_DOUBLE_MIN_VALUE = new BigDecimal(Double.MIN_VALUE);

	public static final BigInteger BigInteger_ZERO = BigInteger.ZERO;
	public static final BigInteger BigInteger_ONE = BigInteger.ONE;
	public static final BigInteger BigInteger_TWO = BigInteger.valueOf(2l);
	public static final BigInteger BigInteger_THREE = BigInteger.valueOf(3l);
	public static final BigInteger BigInteger_FOUR = BigInteger.valueOf(4l);
	public static final BigInteger BigInteger_FIVE = BigInteger.valueOf(5l);
	public static final BigInteger BigInteger_SIX = BigInteger.valueOf(6l);
	public static final BigInteger BigInteger_SEVEN = BigInteger.valueOf(7l);
	public static final BigInteger BigInteger_EIGHT = BigInteger.valueOf(8l);
	public static final BigInteger BigInteger_NINE = BigInteger.valueOf(9l);
	public static final BigInteger BigInteger_TEN = BigInteger.TEN;

	public static final BigInteger BigInteger_BYTE_MAX_VALUE = new BigInteger(new byte[]{0x7f});
	public static final BigInteger BigInteger_BYTE_MIN_VALUE = new BigInteger(new byte[]{-0x80});

	public static final BigInteger BigInteger_SHORT_MAX_VALUE = new BigInteger(new byte[]{0x7f, -0x1});
	public static final BigInteger BigInteger_SHORT_MIN_VALUE = new BigInteger(new byte[]{-0x80, 0x0});

	public static final BigInteger BigInteger_INTEGER_MAX_VALUE = new BigInteger(new byte[]{0x7f, -0x1, -0x1, -0x1});
	public static final BigInteger BigInteger_INTEGER_MIN_VALUE = new BigInteger(new byte[]{-0x80, 0x0, 0x0, 0x0});

	public static final BigInteger BigInteger_LONG_MAX_VALUE = new BigInteger(new byte[]{0x7f, -0x1, -0x1, -0x1, -0x1, -0x1, -0x1, -0x1});
	public static final BigInteger BigInteger_LONG_MIN_VALUE = new BigInteger(new byte[]{-0x80, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0});

	public static final BigInteger BigInteger_FLOAT_MAX_VALUE = new BigInteger(new byte[]{0x0, -0x1, -0x1, -0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0});
	public static final BigInteger BigInteger_FLOAT_MIN_VALUE = new BigInteger(new byte[]{0x0});

	public static final BigInteger BigInteger_DOUBLE_MAX_VALUE = BigDecimal_DOUBLE_MAX_VALUE.toBigInteger();
	public static final BigInteger BigInteger_DOUBLE_MIN_VALUE = new BigInteger(new byte[]{0x0});

	public static final Map<Class<? extends Enum>, Enum<? extends Enum>[]> enumConstants = new HashMap<>();
}
