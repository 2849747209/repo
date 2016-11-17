package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class NullAdapter implements Adapter {

	//private final ThreadLocal<Object> targetHolder;
	
	public NullAdapter() {
		//this.targetHolder = new ThreadLocal<Object>();
	}
	
	@Override
	public void setTarget(Object target) {
		if (null != target) {
			throw new ClassCastException(target + " is not equal null");
		}
	}

	@Override
	public boolean toboolean() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to boolean");
	}
	
	@Override
	public Boolean toBoolean() throws ClassCastException {
		return null;
	}

	@Override
	public byte tobyte() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to byte");
	}

	@Override
	public Byte toByte() throws ClassCastException {
		return null;
	}

	@Override
	public short toshort() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to short");
	}

	@Override
	public Short toShort() throws ClassCastException {
		return null;
	}

	@Override
	public char tochar() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to char");
	}

	@Override
	public Character toCharacter() throws ClassCastException {
		return null;
	}

	@Override
	public int toint() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to int");
	}
	
	@Override
	public Integer toInteger() throws ClassCastException {
		return null;
	}

	@Override
	public long tolong() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to long");
	}

	@Override
	public Long toLong() throws ClassCastException {
		return null;
	}

	@Override
	public float tofloat() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to float");
	}

	@Override
	public Float toFloat() throws ClassCastException {
		return null;
	}

	@Override
	public double todouble() throws ClassCastException {
		throw new ClassCastException("null cannot be cast to double");
	}

	@Override
	public Double toDouble() throws ClassCastException {
		return null;
	}

	@Override
	public BigInteger toBigInteger() throws ClassCastException {
		return null;
	}

	@Override
	public BigDecimal toBigDecimal() throws ClassCastException {
		return null;
	}

	@Override
	public Number toNumber() throws ClassCastException {
		return null;
	}

	@Override
	public String toString() throws ClassCastException {
		return "";
	}

	@Override
	public Date toDateTime() throws ClassCastException {
		return null;
	}

	@Override
	public java.sql.Date toDate() throws ClassCastException {
		return null;
	}

	@Override
	public Time toTime() throws ClassCastException {
		return null;
	}

	@Override
	public Timestamp toTimestamp() throws ClassCastException {
		return null;
	}
	
	@Override
	public Object toObject() throws ClassCastException {
		return null;
	}
}
