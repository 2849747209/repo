package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAdapter implements Adapter
{
	private final ThreadLocal<Time> targetHolder;

	private final String Time_Format;
	private final ThreadLocal<SimpleDateFormat> tFmt;

	public TimeAdapter() {
		this.targetHolder = new ThreadLocal<Time>();

		this.Time_Format = Const.Time_Format;

		this.tFmt = new ThreadLocal<SimpleDateFormat>();
	}

	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Date.class.isInstance(target)) {
				this.targetHolder.set((Time) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.sql.Time");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Time cannot be cast to boolean");
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		throw new ClassCastException("java.sql.Time cannot be cast to java.lang.Boolean");
	}

	@Override
	public byte tobyte() throws ClassCastException {
		Time target = targetHolder.get();
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
		Time target = targetHolder.get();
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
		Time target = targetHolder.get();
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
		Time target = targetHolder.get();
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
		throw new ClassCastException("java.sql.Time cannot be cast to char");
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		throw new ClassCastException("java.sql.Time cannot be cast to java.lang.Character");
	}

	@Override
	public int toint() throws ClassCastException {
		Time target = targetHolder.get();
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
		Time target = targetHolder.get();
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
		Time target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return (float) target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return (double) target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return BigInteger.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return BigDecimal.valueOf(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return target.getTime();
		} else {
			return null;
		}
	}

	@Override
	public String toString() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			SimpleDateFormat sdf = tFmt.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(Time_Format);
				tFmt.set(sdf);
			}

			return sdf.format(target);
		} else {
			return "";
		}
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return new Date(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return new java.sql.Date(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		Time target = targetHolder.get();
		return target;
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return new Timestamp(target.getTime());
		} else {
			return null;
		}
	}

	@Override
	public Object toObject() throws ClassCastException {
		Time target = targetHolder.get();
		if (null != target) {
			return target.toString();
		} else {
			return null;
		}
	}
}
