package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by admin on 2016/11/17.
 */
public class EnumAdapter implements Adapter {

	private final ThreadLocal<Enum> targetHolder;

	public EnumAdapter() {
		this.targetHolder = new ThreadLocal<Enum>();
	}

	@Override
	public void setTarget(Object target) throws ClassCastException {
		if (null != target) {
			if (Enum.class.isInstance(target)) {
				this.targetHolder.set((Enum) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Enum");
			}
		}
	}

	@Override
	public Object toObject() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		return target;
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to boolean");
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.lang.Boolean");
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (byte) target.ordinal();
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (byte) target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (short) target.ordinal();
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (short) target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to char");
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.lang.Character");
	}

	@Override
	public int toint() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return target.ordinal();
		} else {
			throw new ClassCastException(target + " cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (long) target.ordinal();
		} else {
			throw new ClassCastException(target + " cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (long) target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (float) target.ordinal();
		} else {
			throw new ClassCastException(target + " cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (float) target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (double) target.ordinal();
		} else {
			throw new ClassCastException(target + " cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return (double) target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target.ordinal());
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target.ordinal());
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return target.ordinal();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		Enum<?> target = targetHolder.get();
		if (null != target) {
			return target.name();
		} else {
			return null;
		}
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.util.Date");
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Date");
	}

	@Override
	public Time toTime() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Time");
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Enum<?> target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Timestamp");
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Enum<T> target = targetHolder.get();
		return (T) target;
	}
}
