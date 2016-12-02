package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class StringAdapter implements Adapter {

	private final ThreadLocal<String> targetHolder;
	
	public StringAdapter() {
		this.targetHolder = new ThreadLocal<String>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (String.class.isInstance(target)) {
				this.targetHolder.set((String) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.String");
			}
		}
	}
	
	@Override
	public boolean toboolean() throws ClassCastException {
		String target = targetHolder.get();

		switch (target) {
			case "0":
				return false;
			case "1":
				return true;
			case "f":
			case "F":
			case "false":
			case "False":
			case "FALSE":
				return false;
			case "n":
			case "N":
			case "no":
			case "No":
			case "NO":
				return false;
			case "t":
			case "T":
			case "true":
			case "True":
			case "TRUE":
				return true;
			case "y":
			case "Y":
			case "yes":
			case "Yes":
			case "YES":
				return true;
			default:
				throw new ClassCastException(target + " cannot be cast to boolean");
		}
	}

	@Override
	public Boolean toBoolean() throws ClassCastException {
		String target = targetHolder.get();

		if (null != target) {
			switch (target) {
				case "0":
					return false;
				case "1":
					return true;
				case "f":
				case "F":
				case "false":
				case "False":
				case "FALSE":
					return false;
				case "n":
				case "N":
				case "no":
				case "No":
				case "NO":
					return false;
				case "t":
				case "T":
				case "true":
				case "True":
				case "TRUE":
					return true;
				case "y":
				case "Y":
				case "yes":
				case "Yes":
				case "YES":
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
		String target = targetHolder.get();

		try {
			return Byte.parseByte(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to byte");
		}
	}

	@Override
	public Byte toByte() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Byte.parseByte(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Byte");
			}
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		String target = targetHolder.get();

		try {
			return Short.parseShort(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Short.parseShort(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Short");
			}
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			if (target.length() == 1) {
				return target.charAt(0);
			} else {
				throw new ClassCastException(target + " cannot be cast to char");
			}
		} else {
			throw new ClassCastException("null cannot be cast to char");
		}
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			if (target.length() == 1) {
				return target.charAt(0);
			} else {
				throw new ClassCastException(target + " cannot be cast to java.lang.Character");
			}
		} else {
			return null;
		}
	}
	
	@Override
	public int toint() throws ClassCastException {
		String target = targetHolder.get();

		try {
			return Integer.parseInt(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Integer.parseInt(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Integer");
			}
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		String target = targetHolder.get();

		try {
			return Long.parseLong(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Long.parseLong(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Long");
			}
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		String target = targetHolder.get();

		try {
			return Float.parseFloat(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Float.parseFloat(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Float");
			}
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		String target = targetHolder.get();

		try {
			return Double.parseDouble(target);
		} catch (NumberFormatException e) {
			throw new ClassCastException(target + " cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Double.parseDouble(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.lang.Double");
			}
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return new BigInteger(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.math.BigInteger");
			}
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return new BigDecimal(target);
			} catch (NumberFormatException e) {
				throw new ClassCastException(target + " cannot be cast to java.math.BigDecimal");
			}
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		return toBigDecimal();
	}

	@Override
	public String toString() throws ClassCastException {
		String target = targetHolder.get();
		return target;
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseDateTime(target);
		} else {
			return null;
		}
	}

	@Override
	public LocalDateTime toLocalDateTime() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseLocalDateTime(target);
		} else {
			return null;
		}
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseDate(target);
		} else {
			return null;
		}
	}

	@Override
	public LocalDate toLocalDate() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseLocalDate(target);
		} else {
			return null;
		}
	}

	@Override
	public Time toTime() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseTime(target);
		} else {
			return null;
		}
	}

	@Override
	public LocalTime toLocalTime() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseLocalTime(target);
		} else {
			return null;
		}
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			return Formatter.parseTimestamp(target);
		} else {
			return null;
		}
	}
	
	@Override
	public Object toObject() throws ClassCastException {
		String target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		String target = targetHolder.get();
		if (null != target) {
			try {
				return Enum.valueOf(cls, target);
			} catch (IllegalArgumentException illegalArgumentException) {
				throw new ClassCastException(illegalArgumentException.getMessage());
			} catch (NullPointerException nullPointerException) {
				throw new ClassCastException(nullPointerException.getMessage());
			}
		} else {
			return null;
		}
	}
}
