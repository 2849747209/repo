package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class CharacterAdapter implements Adapter {

	private final ThreadLocal<Character> targetHolder;
	
	public CharacterAdapter() {
		this.targetHolder = new ThreadLocal<Character>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			if (Character.class.isInstance(target)) {
				this.targetHolder.set((Character) target);
			} else {
				throw new ClassCastException(target + " is not instance of java.lang.Character");
			}
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			switch (target) {
				case '0':
					return false;
				case '1':
					return true;
				case 'f':
				case 'F':
					return false;
				case 'n':
				case 'N':
					return false;
				case 't':
				case 'T':
					return true;
				case 'y':
				case 'Y':
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
		Character target = targetHolder.get();
		if (null != target) {
			switch (target) {
				case '0':
					return false;
				case '1':
					return true;
				case 'f':
				case 'F':
					return false;
				case 'n':
				case 'N':
					return false;
				case 't':
				case 'T':
					return true;
				case 'y':
				case 'Y':
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
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to byte");
			}

			return (byte) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to byte");
		}
	}
	
	@Override
	public Byte toByte() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Byte");
			}

			return (byte) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public short toshort() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to short");
			}

			return (short) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to short");
		}
	}

	@Override
	public Short toShort() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Short");
			}

			return (short) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public char tochar() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			return target;
		} else {
			throw new ClassCastException("null cannot be cast to char");
		}
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		Character target = targetHolder.get();
		return target;
	}

	@Override
	public int toint() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to int");
			}

			return (int) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to int");
		}
	}

	@Override
	public Integer toInteger() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Integer");
			}

			return (int) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public long tolong() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to long");
			}

			return (long) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to long");
		}
	}

	@Override
	public Long toLong() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Long");
			}

			return (long) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public float tofloat() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to float");
			}

			return (float) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to float");
		}
	}

	@Override
	public Float toFloat() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Float");
			}

			return (float) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public double todouble() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to double");
			}

			return (double) (target - '0');
		} else {
			throw new ClassCastException("null cannot be cast to double");
		}
	}

	@Override
	public Double toDouble() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.lang.Double");
			}

			return (double) (target - '0');
		} else {
			return null;
		}
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.math.BigInteger");
			}

			return BigInteger.valueOf(target - '0');
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			if (target < '0' || target > '9') {
				throw new ClassCastException(target + " cannot be cast to java.math.BigDecimal");
			}

			return BigDecimal.valueOf(target - '0');
		} else {
			return null;
		}
	}

	@Override
	public Number toNumber() throws ClassCastException {
		return tobyte();
	}

	@Override
	public String toString() throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			return target.toString();
		} else {
			return "";
		}
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.util.Date");
	}

	@Override
	public LocalDateTime toLocalDateTime() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.time.LocalDateTime");
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Date");
	}

	@Override
	public LocalDate toLocalDate() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.time.LocalDate");
	}

	@Override
	public Time toTime() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Time");
	}

	@Override
	public LocalTime toLocalTime() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.time.LocalTime");
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		Character target = targetHolder.get();
		throw new ClassCastException(target + " cannot be cast to java.sql.Timestamp");
	}

	@Override
	public Object toObject() throws ClassCastException {
		Character target = targetHolder.get();
		return target;
	}

	@Override
	public <T extends Enum<T>> T toEnum(Class<T> cls) throws ClassCastException {
		Character target = targetHolder.get();
		if (null != target) {
			throw new ClassCastException(target + " cannot be cast to java.lang.Enum");
		} else {
			return null;
		}
	}
}
