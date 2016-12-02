package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class BooleanAdapter implements Adapter {
	private final ThreadLocal<Boolean> targetHolder;
	
	public BooleanAdapter() {
		this.targetHolder = new ThreadLocal<Boolean>();
	}
	
	public void setTarget(Object target) throws ClassCastException {
		if (null != target) {
			if (Boolean.class.isInstance(target)) {
				this.targetHolder.set((Boolean) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Boolean");
			}
		}
	}
	
	@Override
	public boolean toboolean() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			return target.booleanValue();
		} else {
			throw new ClassCastException("null cannot be cast to boolean");
		}
	}
	
	@Override
	public Boolean toBoolean() throws ClassCastException {
		Boolean target = targetHolder.get();
		return target;
	}
	
	@Override
	public byte tobyte() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}
	
	@Override
	public Byte toByte() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Byte");
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}
	
	@Override
	public Short toShort() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Short");
		}
	}
	
	@Override
	public char tochar() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to char");
	}
	
	@Override
	public Character toCharacter() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.lang.Character");
	}
	
	@Override
	public int toint() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Integer");
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}
	
	@Override
	public Long toLong() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1L;
			} else {
				return 0L;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Long");
		}
	}
	
	@Override 
	public String toString() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return "true";
			} else {
				return "false";
			}
		} else {
			return "";
		}
	}
	
	@Override
	public float tofloat() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1f;
			} else {
				return 0f;
			}
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1f;
			} else {
				return 0f;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Float");
		}
	}
	
	@Override
	public double todouble() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1d;
			} else {
				return 0d;
			}
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1d;
			} else {
				return 0d;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Double");
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return BigInteger.ONE;
			} else {
				return BigInteger.ZERO;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.math.BigInteger");
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return BigDecimal.ONE;
			} else {
				return BigDecimal.ZERO;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.math.BigDecimal");
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			if (target) {
				return 1;
			} else {
				return 0;
			}
		} else {
			throw new ClassCastException("null cannot be cast to java.lang.Number");
		}
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.util.Date");
	}

	@Override
	public LocalDateTime toLocalDateTime() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.time.LocalDateTime");
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.sql.Date");
	}

	@Override
	public LocalDate toLocalDate() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.time.LocalDate");
	}

	@Override
	public Time toTime() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.sql.Time");
	}

	@Override
	public LocalTime toLocalTime() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.time.LocalTime");
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		throw new ClassCastException("java.lang.Boolean cannot be cast to java.sql.Timestamp");
	}
	
	@Override
	public Object toObject() throws ClassCastException {
		Boolean target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Boolean target = targetHolder.get();
		if (null != target) {
			throw new ClassCastException(target + " cannot be cast to java.lang.Enum");
		} else {
			return null;
		}
	}
}
