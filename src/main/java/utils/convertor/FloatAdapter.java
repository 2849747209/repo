package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class FloatAdapter implements Adapter {

	private final ThreadLocal<Float> targetHolder;
	
	public FloatAdapter() {
		this.targetHolder = new ThreadLocal<Float>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Float.class.isInstance(target)) {
				this.targetHolder.set((Float) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Float");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				switch (target.intValue()) {
					case 0:
						return false;
					case 1:
						return true;
					default:
						throw new ClassCastException(target + " cannot be cast to boolean");
				}
			} else {
				throw new ClassCastException(target + " cannot be cast to boolean");
			}
		} else {
			throw new ClassCastException("null cannot be cast to boolean");
		}
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				switch (target.intValue()) {
					case 0:
						return false;
					case 1:
						return true;
					default:
						throw new ClassCastException(target + " cannot be cast to java.lang.Boolean");
				}
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Boolean");
			}
		} else {
			return null;
		}
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Byte.MAX_VALUE)
					throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

				if (target < Byte.MIN_VALUE)
					throw new ClassCastException(target + " less than Byte.MIN_VALUE");

				return target.byteValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to byte");
			}
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Byte.MAX_VALUE)
					throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

				if (target < Byte.MIN_VALUE)
					throw new ClassCastException(target + " less than Byte.MIN_VALUE");

				return target.byteValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Byte");
			}
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Short.MAX_VALUE)
					throw new ClassCastException(target + " greater than Short.MAX_VALUE");

				if (target < Short.MIN_VALUE)
					throw new ClassCastException(target + " less than Short.MIN_VALUE");

				return target.shortValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to short");
			}
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Short.MAX_VALUE)
					throw new ClassCastException(target + " greater than Short.MAX_VALUE");

				if (target < Short.MIN_VALUE)
					throw new ClassCastException(target + " less than Short.MIN_VALUE");

				return target.shortValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Short");
			}
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
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
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
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
			return null;
		}
	}

	@Override
	public int toint() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Integer.MAX_VALUE)
					throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

				if (target < Integer.MIN_VALUE)
					throw new ClassCastException(target + " less than Integer.MIN_VALUE");

				return target.intValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to int");
			}
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Integer.MAX_VALUE)
					throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

				if (target < Integer.MIN_VALUE)
					throw new ClassCastException(target + " less than Integer.MIN_VALUE");

				return target.intValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Integer");
			}
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");

				return target.longValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to long");
			}
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");

				return target.longValue();
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Long");
			}
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Float target = targetHolder.get();
		return target;
	}

	@Override
	public double todouble() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			return target.doubleValue();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			return target.doubleValue();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				return BigDecimal.valueOf(target.longValue()).toBigInteger();
			} else {
				throw new ClassCastException(target + " cannot be cast to java.math.BigInteger");
			}
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target);
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Float target = targetHolder.get();
		return target;
	}

	@Override
	public String toString() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			return target.toString();
		} else {
			return "";
		}
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
					throw new ClassCastException(target + " less than Long.MIN_VALUE");

				return new Date(target.longValue());
			} else {
				throw new ClassCastException(target + " cannot be cast to java.util.Date");
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
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
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
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
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
		Float target = targetHolder.get();
		if (null != target) {
			if (target % 1 == 0) {
				if (target > Long.MAX_VALUE)
					throw new ClassCastException(target + " greater than Long.MAX_VALUE");

				if (target < Long.MIN_VALUE)
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
		Float target = targetHolder.get();
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
