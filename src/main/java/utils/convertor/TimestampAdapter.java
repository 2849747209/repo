package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TimestampAdapter implements Adapter {

	private final ThreadLocal<Timestamp> targetHolder;
	
	public TimestampAdapter() {
		this.targetHolder = new ThreadLocal<Timestamp>();
	}

	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Timestamp.class.isInstance(target)) {
				this.targetHolder.set((Timestamp) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.sql.Timestamp");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Timestamp cannot be cast to boolean");
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Timestamp cannot be cast to java.lang.Boolean");
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Byte.MAX_VALUE)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (time < Byte.MIN_VALUE)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return (byte) time;
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Byte.MAX_VALUE)
				throw new ClassCastException(target + " greater than Byte.MAX_VALUE");

			if (time < Byte.MIN_VALUE)
				throw new ClassCastException(target + " less than Byte.MIN_VALUE");

			return (byte) time;
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Short.MAX_VALUE)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (time < Short.MIN_VALUE)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return (short) time;
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Short.MAX_VALUE)
				throw new ClassCastException(target + " greater than Short.MAX_VALUE");

			if (time < Short.MIN_VALUE)
				throw new ClassCastException(target + " less than Short.MIN_VALUE");

			return (short) time;
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		throw new ClassCastException("java.sql.Timestamp cannot be cast to char");
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		throw new ClassCastException("java.sql.Timestamp cannot be cast to java.lang.Character");
	}

	@Override
	public int toint() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Integer.MAX_VALUE)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (time < Integer.MIN_VALUE)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return (int) time;
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			long time = target.getTime();

			if (time > Integer.MAX_VALUE)
				throw new ClassCastException(target + " greater than Integer.MAX_VALUE");

			if (time < Integer.MIN_VALUE)
				throw new ClassCastException(target + " less than Integer.MIN_VALUE");

			return (int) time;
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return (float) target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return (double) target.getTime();
		} else {
			return null;
		}
	}

	public BigInteger toBigInteger() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public String toString() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return Formatter.toTimestamp(target);
		} else {
			return "";
		}
	}
	
	@Override
	public Date toDateTime() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			return null;
		}
	}

	@Override
	public LocalDateTime toLocalDateTime() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.toLocalDateTime();
		} else {
			return null;
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return new java.sql.Date(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public LocalDate toLocalDate() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.toLocalDateTime().toLocalDate();
		} else {
			return null;
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return new Time(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public LocalTime toLocalTime() throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			return target.toLocalDateTime().toLocalTime();
		} else {
			return null;
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Timestamp target = targetHolder.get();
		return target;
	}

	@Override
	public Object toObject() throws ClassCastException {
		Timestamp target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Timestamp target = targetHolder.get();
		if (null != target) {
			throw new ClassCastException(target + " cannot be cast to java.lang.Enum");
		} else {
			return null;
		}
	}
}
