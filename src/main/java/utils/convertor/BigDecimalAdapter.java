package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class BigDecimalAdapter implements Adapter {

	private final ThreadLocal<BigDecimal> targetHolder;
	
	private final BigDecimal ZERO = Const.BigDecimal_ZERO;
	private final BigDecimal ONE = Const.BigDecimal_ONE;
	/*private final BigDecimal TWO = new BigDecimal("2");
	private final BigDecimal THREE = new BigDecimal("3");
	private final BigDecimal FOUR = new BigDecimal("4");
	private final BigDecimal FIVE = new BigDecimal("5");
	private final BigDecimal SIX = new BigDecimal("6");
	private final BigDecimal SEVEN = new BigDecimal("7");
	private final BigDecimal EIGHT = new BigDecimal("8");
	private final BigDecimal NINE = new BigDecimal("9");
	private final BigDecimal TEN = BigDecimal.TEN;*/
	
	private final BigDecimal BYTE_MAX_VALUE = Const.BigDecimal_BYTE_MAX_VALUE;
	private final BigDecimal BYTE_MIN_VALUE = Const.BigDecimal_BYTE_MIN_VALUE;
	
	private final BigDecimal SHORT_MAX_VALUE = Const.BigDecimal_SHORT_MAX_VALUE;
	private final BigDecimal SHORT_MIN_VALUE = Const.BigDecimal_SHORT_MIN_VALUE;
	
	private final BigDecimal INTEGER_MAX_VALUE = Const.BigDecimal_INTEGER_MAX_VALUE;
	private final BigDecimal INTEGER_MIN_VALUE = Const.BigDecimal_INTEGER_MIN_VALUE;
	
	private final BigDecimal LONG_MAX_VALUE = Const.BigDecimal_LONG_MAX_VALUE;
	private final BigDecimal LONG_MIN_VALUE = Const.BigDecimal_LONG_MIN_VALUE;
	
	private final BigDecimal FLOAT_MAX_VALUE = Const.BigDecimal_FLOAT_MAX_VALUE;
	private final BigDecimal FLOAT_MIN_VALUE = Const.BigDecimal_FLOAT_MIN_VALUE;
	
	private final BigDecimal DOUBLE_MAX_VALUE = Const.BigDecimal_DOUBLE_MAX_VALUE;
	private final BigDecimal DOUBLE_MIN_VALUE = Const.BigDecimal_DOUBLE_MIN_VALUE;
	
	public BigDecimalAdapter() {
		this.targetHolder = new ThreadLocal<BigDecimal>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (BigDecimal.class.isInstance(target)) {
				this.targetHolder.set((BigDecimal) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.BigDecimal");
			}
		}
	}
	
	@Override
	public boolean toboolean() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(ZERO) == 0) {
				return false;
			} else if (target.compareTo(ONE) == 0) {
				return true;
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to boolean");
			}
		} else {
			throw new ClassCastException("null cannot be cast to boolean");
		}
	}
	
	@Override
	public Boolean toBoolean() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(BigDecimal.ZERO) == 0) {
				return false;
			} else if (target.compareTo(BigDecimal.ONE) == 0) {
				return true;
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Boolean");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public byte tobyte() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(BYTE_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Byte.MAX_VALUE");
				
				if (target.compareTo(BYTE_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Byte.MIN_VALUE");
				
				return target.byteValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to byte");
			}
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(BYTE_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Byte.MAX_VALUE");
				
				if (target.compareTo(BYTE_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Byte.MIN_VALUE");
				
				return target.byteValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Byte");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public short toshort() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(SHORT_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Short.MAX_VALUE");
				
				if (target.compareTo(SHORT_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Short.MIN_VALUE");
				
				return target.shortValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to short");
			}
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(SHORT_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Short.MAX_VALUE");
				
				if (target.compareTo(SHORT_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Short.MIN_VALUE");
				
				return target.shortValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to short");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public char tochar() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
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
							throw new ClassCastException(target.toPlainString() + " cannot be cast to char");
					}
				} else {
					throw new ClassCastException(target.toPlainString() + " cannot be cast to char");
				}
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to char");
			}
		} else {
			throw new ClassCastException("null cannot be cast to char");
		}
	}
	
	@Override
	public Character toCharacter() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
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
							throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Character");
					}
				} else {
					throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Character");
				}
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Character");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public int toint() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(INTEGER_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Integer.MAX_VALUE");
				
				if (target.compareTo(INTEGER_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Integer.MIN_VALUE");
				
				return target.intValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to int");
			}
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(INTEGER_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Integer.MAX_VALUE");
				
				if (target.compareTo(INTEGER_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Integer.MIN_VALUE");
				
				return target.intValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Integer");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public long tolong() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Long.MIN_VALUE");
				
				return target.longValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to long");
			}
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}
	
	@Override
	public Long toLong() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Long.MIN_VALUE");
				
				return target.longValue();
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.lang.Long");
			}
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(FLOAT_MAX_VALUE) == 1)
				throw new ClassCastException(target.toPlainString() + " greater than Float.MAX_VALUE");
			
			if (target.compareTo(FLOAT_MIN_VALUE) == -1)
				throw new ClassCastException(target.toPlainString() + " less than Float.MIN_VALUE");
			
			return target.floatValue();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}
	
	@Override
	public Float toFloat() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(FLOAT_MAX_VALUE) == 1)
				throw new ClassCastException(target.toPlainString() + " greater than Float.MAX_VALUE");
			
			if (target.compareTo(FLOAT_MIN_VALUE) == -1)
				throw new ClassCastException(target.toPlainString() + " less than Float.MIN_VALUE");
			
			return target.floatValue();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(DOUBLE_MAX_VALUE) == 1)
				throw new ClassCastException(target.toPlainString() + " greater than Double.MAX_VALUE");
			
			if (target.compareTo(DOUBLE_MIN_VALUE) == -1)
				throw new ClassCastException(target.toPlainString() + " less than Double.MIN_VALUE");
			
			return target.doubleValue();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}
	
	@Override
	public Double toDouble() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.compareTo(DOUBLE_MAX_VALUE) == 1)
				throw new ClassCastException(target.toPlainString() + " greater than Double.MAX_VALUE");
			
			if (target.compareTo(DOUBLE_MIN_VALUE) == -1)
				throw new ClassCastException(target.toPlainString() + " less than Double.MIN_VALUE");
			
			return target.doubleValue();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			return target.toBigInteger();
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		return target;
	}

	@Override
	public Number toNumber() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		return target;
	}

	@Override
	public String toString() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target)
			return target.toPlainString();
		else
			return "";
	}
	
	@Override
	public Date toDateTime() throws ClassCastException {
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target.toPlainString() + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target.toPlainString() + " less than Long.MIN_VALUE");
				
				return new Date(target.longValue());
			} else {
				throw new ClassCastException(target.toPlainString() + " cannot be cast to java.util.Date");
			}
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
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");
				
				return new java.sql.Date(target.longValue());
			} else {
				throw new ClassCastException(target + " cannot be cast to java.sql.Date");
			}
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
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");
				
				return new Time(target.longValue());
			} else {
				throw new ClassCastException(target + " cannot be cast to java.sql.Time");
			}
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
		BigDecimal target = targetHolder.get();
		if (null != target) {
			if (target.remainder(ONE).compareTo(ZERO) == 0) {
				if (target.compareTo(LONG_MAX_VALUE) == 1)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");
				
				if (target.compareTo(LONG_MIN_VALUE) == -1)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");
				
				return new Timestamp(target.longValue());
			} else {
				throw new ClassCastException(target + " cannot be cast to java.sql.Timestamp");
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Timestamp");
		}
	}
	
	@Override
	public Object toObject() throws ClassCastException {
		BigDecimal target = targetHolder.get();
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
