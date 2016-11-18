package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateAdapter implements Adapter {

	private final ThreadLocal<Date> targetHolder;

	private final String Date_Format;
	private final ThreadLocal<SimpleDateFormat> dFmt;

	public DateAdapter() {
		this.targetHolder = new ThreadLocal<Date>();

		this.Date_Format = Const.Date_Format;

		this.dFmt = new ThreadLocal<SimpleDateFormat>();
	}

	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Date.class.isInstance(target)) {
				this.targetHolder.set((Date) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.sql.Date");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Date cannot be cast to boolean");
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Date cannot be cast to java.lang.Boolean");
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Date target = targetHolder.get();
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
		Date target = targetHolder.get();
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
		Date target = targetHolder.get();
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
		Date target = targetHolder.get();
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
		throw new ClassCastException("java.sql.Date cannot be cast to char");
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		throw new ClassCastException("java.sql.Date cannot be cast to java.lang.Character");
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
		Date target = targetHolder.get();
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
		Date target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return (float) target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return (double) target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public String toString() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			SimpleDateFormat sdf = dFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Date_Format);
				dFmt.set(sdf);
			}

			return sdf.format(target);
		} else {
			return "";
		}
	}

	@Override
	public java.util.Date toDateTime() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return new java.util.Date(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Date toDate() throws ClassCastException {
		Date target = targetHolder.get();
		return target;
	}

	@Override
	public Time toTime() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return new Time(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			return new Timestamp(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Object toObject() throws ClassCastException {
		Date target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Date target = targetHolder.get();
		if (null != target) {
			throw new ClassCastException(target + " cannot be cast to java.lang.Enum");
		} else {
			return null;
		}
	}
}
