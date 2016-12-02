package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class ShortAdapter implements Adapter {

	private final ThreadLocal<Short> targetHolder;
	
	public ShortAdapter() {
		this.targetHolder = new ThreadLocal<Short>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Short.class.isInstance(target)) {
				this.targetHolder.set((Short) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Short");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			switch (target) {
				case 0:
					return false;
				case 1:
					return true;
				default:
					throw new ClassCastException(target + " cannot be cast to boolean");
			}
		} else {
			throw new ClassCastException("null cannot be cast to boolean");
		}
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			switch (target) {
				case 0:
					return false;
				case 1:
					return true;
				default:
					throw new ClassCastException(target + " cannot be cast to java.lang.Boolean");
			}
		} else {
			return null;
		}
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			if (target > Byte.MAX_VALUE)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (target < Byte.MIN_VALUE)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return target.byteValue();
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			if (target > Byte.MAX_VALUE)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (target < Byte.MIN_VALUE)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return target.byteValue();
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Short target = targetHolder.get();
		return target;
	}

	@Override
	public char tochar() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			switch (target) {
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
			throw new ClassCastException("null cannot be cast to char");
		}
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			switch (target) {
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
			return null;
		}
	}

	@Override
	public int toint() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target.intValue();
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target.longValue();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target.floatValue();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target.doubleValue();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target);
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target);
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			return null;
		}
	}

	@Override
	public String toString() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return target.toString();
		} else {
			return "";
		}
	}
	
	@Override
	public Date toDateTime() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return new Date(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.util.Date");
		}
	}

	@Override
	public LocalDateTime toLocalDateTime() throws ClassCastException {
		Timestamp target = toTimestamp();
		if (null != target) {
			return target.toLocalDateTime();
		} else {
			return null;
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return new java.sql.Date(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Date");
		}
	}

	@Override
	public LocalDate toLocalDate() throws ClassCastException {
		Timestamp target = toTimestamp();
		if (null != target) {
			return target.toLocalDateTime().toLocalDate();
		} else {
			return null;
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return new Time(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Time");
		}
	}

	@Override
	public LocalTime toLocalTime() throws ClassCastException {
		Timestamp target = toTimestamp();
		if (null != target) {
			return target.toLocalDateTime().toLocalTime();
		} else {
			return null;
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Short target = targetHolder.get();
		if (null != target) {
			return new Timestamp(target.longValue());
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Timestamp");
		}
	}
	
	@Override
	public Object toObject() throws ClassCastException {
		Short target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Enum<? extends Enum>[] constants = Const.enumConstants.get(cls);
		if (null == constants) {
			constants = cls.getEnumConstants();
			Const.enumConstants.put(cls, constants);
		}

		int ordinal = toint();

		return (T) constants[ordinal];
	}
}
