package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class LongAdapter implements Adapter {

	private final ThreadLocal<Long> targetHolder;
	
	public LongAdapter() {
		this.targetHolder = new ThreadLocal<Long>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Long.class.isInstance(target)) {
				this.targetHolder.set((Long) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Long");
			}
		}
	}
	
	@Override
	public boolean toboolean() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			if (target <= Integer.MAX_VALUE) {
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
		Long target = targetHolder.get();
		if (null != target) {
			if (target <= Integer.MAX_VALUE) {
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
		Long target = targetHolder.get();
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
		Long target = targetHolder.get();
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
		Long target = targetHolder.get();
		if (null != target) {
			if (target > Short.MAX_VALUE)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (target < Short.MIN_VALUE)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return target.shortValue();
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			if (target > Short.MAX_VALUE)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (target < Short.MIN_VALUE)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return target.shortValue();
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			if (target <= Short.MAX_VALUE) {
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
		Long target = targetHolder.get();
		if (null != target) {
			if (target <= Short.MAX_VALUE) {
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
		Long target = targetHolder.get();
		if (null != target) {
			if (target > Integer.MAX_VALUE)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (target < Integer.MIN_VALUE)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return target.intValue();
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			if (target > Integer.MAX_VALUE)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (target < Integer.MIN_VALUE)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return target.intValue();
		} else {
			return null;
		}
	}
	
	@Override
	public long tolong() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Long target = targetHolder.get();
		return target;
	}

	@Override 
	public String toString() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target.toString();
		} else {
			return "";
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target.floatValue();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target.floatValue();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target.doubleValue();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return target.doubleValue();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target);
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target);
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Long target = targetHolder.get();
		return target;
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return new Date(target);
		} else {
			throw new ClassCastException("null cannot be cast to java.util.Date");
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return new java.sql.Date(target);
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Date");
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return new Time(target);
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Time");
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Long target = targetHolder.get();
		if (null != target) {
			return new Timestamp(target);
		} else {
			throw new ClassCastException("null cannot be cast to java.sql.Timestamp");
		}
	}

	@Override
	public Object toObject() throws ClassCastException {
		Long target = targetHolder.get();
		return target;
	}
}
