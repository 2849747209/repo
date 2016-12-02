package utils.convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public interface Adapter
{
	public abstract void			setTarget(Object object) throws ClassCastException;
	
	public abstract Object					toObject() throws ClassCastException;
	public abstract boolean					toboolean() throws ClassCastException;
	public abstract Boolean					toBoolean() throws ClassCastException;
	public abstract byte					tobyte() throws ClassCastException;
	public abstract Byte					toByte() throws ClassCastException;
	public abstract short					toshort() throws ClassCastException;
	public abstract Short					toShort() throws ClassCastException;
	public abstract char					tochar() throws ClassCastException;
	public abstract Character				toCharacter() throws ClassCastException;
	public abstract int						toint() throws ClassCastException;
	public abstract Integer					toInteger() throws ClassCastException;
	public abstract long					tolong() throws ClassCastException;
	public abstract Long					toLong() throws ClassCastException;
	public abstract float					tofloat() throws ClassCastException;
	public abstract Float					toFloat() throws ClassCastException;
	public abstract double					todouble() throws ClassCastException;
	public abstract Double					toDouble() throws ClassCastException;
	public abstract BigInteger				toBigInteger() throws ClassCastException;
	public abstract BigDecimal				toBigDecimal() throws ClassCastException;
	public abstract Number					toNumber() throws ClassCastException;
	public abstract Date					toDateTime() throws ClassCastException;
	public abstract LocalDateTime			toLocalDateTime() throws ClassCastException;
	public abstract java.sql.Date			toDate() throws ClassCastException;
	public abstract LocalDate				toLocalDate() throws ClassCastException;
	public abstract Time					toTime() throws ClassCastException;
	public abstract LocalTime				toLocalTime() throws ClassCastException;
	public abstract Timestamp				toTimestamp() throws ClassCastException;
	public abstract String					toString() throws ClassCastException;
	public abstract <T extends Enum<T>> T	toEnum(Class<T> cls) throws ClassCastException;
}
