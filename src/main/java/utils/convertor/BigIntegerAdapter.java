package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class BigIntegerAdapter implements Adapter {

	private final ThreadLocal<BigInteger> targetHolder;

	private final BigInteger ZERO = Const.BigInteger_ZERO;
	private final BigInteger ONE = Const.BigInteger_ONE;
	
	private final BigInteger BYTE_MAX_VALUE = Const.BigInteger_BYTE_MAX_VALUE;
	private final BigInteger BYTE_MIN_VALUE = Const.BigInteger_BYTE_MIN_VALUE;
	
	private final BigInteger SHORT_MAX_VALUE = Const.BigInteger_SHORT_MAX_VALUE;
	private final BigInteger SHORT_MIN_VALUE = Const.BigInteger_SHORT_MIN_VALUE;
	
	private final BigInteger INTEGER_MAX_VALUE = Const.BigInteger_INTEGER_MAX_VALUE;
	private final BigInteger INTEGER_MIN_VALUE = Const.BigInteger_INTEGER_MIN_VALUE;

	private final BigInteger LONG_MAX_VALUE = Const.BigInteger_LONG_MAX_VALUE;
	private final BigInteger LONG_MIN_VALUE = Const.BigInteger_LONG_MIN_VALUE;

	private final BigInteger FLOAT_MAX_VALUE = Const.BigInteger_FLOAT_MAX_VALUE;
	private final BigInteger FLOAT_MIN_VALUE = Const.BigInteger_FLOAT_MIN_VALUE;

	private final BigInteger DOUBLE_MAX_VALUE = Const.BigInteger_DOUBLE_MAX_VALUE;
	private final BigInteger DOUBLE_MIN_VALUE = Const.BigInteger_DOUBLE_MIN_VALUE;
	
	public BigIntegerAdapter() {
		this.targetHolder = new ThreadLocal<BigInteger>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (BigInteger.class.isInstance(target)) {
				this.targetHolder.set((BigInteger) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.BigDecimal");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(ZERO) == 0) {
				return false;
			} else if (target.compareTo(ONE) == 0) {
				return true;
			} else {
				throw new ClassCastException(target + " cannot be cast to boolean");
			}
		} else {
			throw new ClassCastException("null cannot be cast to boolean");
		}
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(ZERO) == 0) {
				return false;
			} else if (target.compareTo(ONE) == 0) {
				return true;
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Boolean");
			}
		} else {
			return null;
		}
	}

	@Override
	public byte tobyte() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(BYTE_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (target.compareTo(BYTE_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return target.byteValue();
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(BYTE_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (target.compareTo(BYTE_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return target.byteValue();
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(SHORT_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (target.compareTo(SHORT_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return target.shortValue();
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(SHORT_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (target.compareTo(SHORT_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return target.shortValue();
		} else {
			return null;
		}
	}
	
	@Override
	public char tochar() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(INTEGER_MAX_VALUE) <= 0) {
				switch (target.intValue()) {
					case 0:
						return '0';
					case 1:
						return '1';
					case 2:
						return '2';
					case 3:
						return '3';
					case 4:
						return '4';
					case 5:
						return '5';
					case 6:
						return '6';
					case 7:
						return '7';
					case 8:
						return '8';
					case 9:
						return '9';
					default:
						throw new ClassCastException(target + " cannot be cast to char");
				}
			} else {
				throw new ClassCastException(target + " cannot be cast to char");
			}
		} else {
			throw new ClassCastException("null cannot be cast to char");
		}
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(INTEGER_MAX_VALUE) <= 0) {
				switch (target.intValue()) {
					case 0:
						return '0';
					case 1:
						return '1';
					case 2:
						return '2';
					case 3:
						return '3';
					case 4:
						return '4';
					case 5:
						return '5';
					case 6:
						return '6';
					case 7:
						return '7';
					case 8:
						return '8';
					case 9:
						return '9';
					default:
						throw new ClassCastException(target + " cannot be cast to java.lang.Character");
				}
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Character");
			}
		} else {
			return null;
		}
	}

	@Override
	public int toint() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(INTEGER_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (target.compareTo(INTEGER_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return target.intValue();
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(INTEGER_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (target.compareTo(INTEGER_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return target.intValue();
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return target.longValue();
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return target.longValue();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(FLOAT_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Float.MAX_VALUE");

			if (target.compareTo(FLOAT_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Float.MIN_VALUE");

			return target.floatValue();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(FLOAT_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Float.MAX_VALUE");

			if (target.compareTo(FLOAT_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Float.MIN_VALUE");

			return target.floatValue();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(DOUBLE_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Double.MAX_VALUE");

			if (target.compareTo(DOUBLE_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Double.MIN_VALUE");

			return target.doubleValue();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(DOUBLE_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Double.MAX_VALUE");

			if (target.compareTo(DOUBLE_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Double.MIN_VALUE");

			return target.doubleValue();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		BigInteger target = targetHolder.get();
		return target;
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		BigInteger target = targetHolder.get();
		return new BigDecimal(target);
	}

	@Override
	public Number toNumber() throws ClassCastException {
		BigInteger target = targetHolder.get();
		return target;
	}
	
	@Override
	public String toString() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target)
			return target.toString();
		else
			return "";
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return new Date(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.util.Date");
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return new java.sql.Date(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Date");
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return new Time(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.util.Time");
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		BigInteger target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(LONG_MAX_VALUE) == 1)
				throw new ClassCastException(target + " greater than Long.MAX_VALUE");

			if (target.compareTo(LONG_MIN_VALUE) == -1)
				throw new ClassCastException(target + " less than Long.MIN_VALUE");

			return new Timestamp(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.util.Timestamp");
		}
	}

	@Override
	public Object toObject() throws ClassCastException {
		BigInteger target = targetHolder.get();
		return target;
	}
}
