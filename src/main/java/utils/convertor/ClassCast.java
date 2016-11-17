package utils.convertor;

import utils.convertor.test.Season;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClassCast {
	private final static ClassCast convertFactory = new ClassCast();
	
	private final Map<Class<?>, Method> methodMapper;
	private final Map<Class<?>, Adapter> adapterMapper;
	
	private ClassCast() {
		methodMapper = Collections.unmodifiableMap(getMethodMapper());
		adapterMapper = Collections.unmodifiableMap(getAdapterMapper());
	}
	
	private Map<Class<?>, Method> getMethodMapper() {
		Class<?> cls = Adapter.class;
		
		Map<Class<?>, Method> methodMapper = new HashMap<Class<?>, Method>();
		
		try {
			Method method = cls.getMethod("toObject");
			methodMapper.put(Object.class, method);
			
			method = cls.getMethod("toboolean");
			methodMapper.put(Boolean.TYPE, method);
			method = cls.getMethod("toBoolean");
			methodMapper.put(Boolean.class, method);
			
			method = cls.getMethod("tobyte");
			methodMapper.put(Byte.TYPE, method);
			method = cls.getMethod("toByte");
			methodMapper.put(Byte.class, method);
			
			method = cls.getMethod("toshort");
			methodMapper.put(Short.TYPE, method);
			method = cls.getMethod("toShort");
			methodMapper.put(Short.class, method);
			
			method = cls.getMethod("tochar");
			methodMapper.put(Character.TYPE, method);
			method = cls.getMethod("toCharacter");
			methodMapper.put(Character.class, method);
			
			method = cls.getMethod("toint");
			methodMapper.put(Integer.TYPE, method);
			method = cls.getMethod("toInteger");
			methodMapper.put(Integer.class, method);
			
			method = cls.getMethod("tolong");
			methodMapper.put(Long.TYPE, method);
			method = cls.getMethod("toLong");
			methodMapper.put(Long.class, method);
			
			method = cls.getMethod("tofloat");
			methodMapper.put(Float.TYPE, method);
			method = cls.getMethod("toFloat");
			methodMapper.put(Float.class, method);
			
			method = cls.getMethod("todouble");
			methodMapper.put(Double.TYPE, method);
			method = cls.getMethod("toDouble");
			methodMapper.put(Double.class, method);
			
			method = cls.getMethod("toBigInteger");
			methodMapper.put(BigInteger.class, method);
			
			method = cls.getMethod("toBigDecimal");
			methodMapper.put(BigDecimal.class, method);
			
			method = cls.getMethod("toNumber");
			methodMapper.put(Number.class, method);
			
			method = cls.getMethod("toDateTime");
			methodMapper.put(java.util.Date.class, method);
			method = cls.getMethod("toDate");
			methodMapper.put(java.sql.Date.class, method);
			method = cls.getMethod("toTime");
			methodMapper.put(Time.class, method);
			method = cls.getMethod("toTimestamp");
			methodMapper.put(Timestamp.class, method);
			
			method = cls.getMethod("toString");
			methodMapper.put(String.class, method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return methodMapper;
	}
	
	private Map<Class<?>, Adapter> getAdapterMapper() {
		Map<Class<?>, Adapter> adapterMapper = new HashMap<Class<?>, Adapter>();
		
		Adapter adapter = new NullAdapter();
		adapterMapper.put(null, adapter);
		adapterMapper.put(Object.class, adapter);
		
		adapter = new BooleanAdapter();
		adapterMapper.put(Boolean.class, adapter);
		adapterMapper.put(Boolean.TYPE, adapter);
		
		adapter = new ByteAdapter();
		adapterMapper.put(Byte.class, adapter);
		adapterMapper.put(Byte.TYPE, adapter);
		
		adapter = new ShortAdapter();
		adapterMapper.put(Short.class, adapter);
		adapterMapper.put(Short.TYPE, adapter);
		
		adapter = new CharacterAdapter();
		adapterMapper.put(Character.class, adapter);
		adapterMapper.put(Character.TYPE, adapter);
		
		adapter = new IntegerAdapter();
		adapterMapper.put(Integer.class, adapter);
		adapterMapper.put(Integer.TYPE, adapter);
		
		adapter = new LongAdapter();
		adapterMapper.put(Long.class, adapter);
		adapterMapper.put(Long.TYPE, adapter);
		
		adapter = new FloatAdapter();
		adapterMapper.put(Float.class, adapter);
		adapterMapper.put(Float.TYPE, adapter);
		
		adapter = new DoubleAdapter();
		adapterMapper.put(Double.class, adapter);
		adapterMapper.put(Double.TYPE, adapter);
		
		adapter = new BigIntegerAdapter();
		adapterMapper.put(BigInteger.class, adapter);
		
		adapter = new BigDecimalAdapter();
		adapterMapper.put(Number.class, adapter);
		
		adapter = new DateTimeAdapter();
		adapterMapper.put(Date.class, adapter);
		
		adapter = new DateAdapter();
		adapterMapper.put(java.sql.Date.class, adapter);
		
		adapter = new TimeAdapter();
		adapterMapper.put(Time.class, adapter);
		
		adapter = new TimestampAdapter();
		adapterMapper.put(Timestamp.class, adapter);
		
		adapter = new StringAdapter();
		adapterMapper.put(String.class, adapter);
		
		return adapterMapper;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object, Class<T> cls) {

		Method method = convertFactory.methodMapper.get(cls);
		if (null == method)
			throw new ClassCastException(object + " cannot be cast to " + cls);

		Adapter adapter;
		if (null != object) {
			adapter = convertFactory.adapterMapper.get(object.getClass());
			if (null != adapter) {
				adapter.setTarget(object);
			} else {
				throw new ClassCastException(object + " cannot be cast to " + cls);
			}
		} else {
			adapter = convertFactory.adapterMapper.get(null);
		}

		try {
			return (T) method.invoke(adapter);
			//return cls.cast(method.invoke(adapter));
		} catch (IllegalAccessException illegalAccessException) {
			throw new ClassCastException(object + " cannot be cast to " + cls);
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new ClassCastException(object + " cannot be cast to " + cls);
		} catch (InvocationTargetException invocationTargetException) {
			throw new ClassCastException(invocationTargetException.getTargetException().getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		/*Class<?> cls = Class.forName("utils.convertor.test.Season");
		System.out.println(cls.isEnum());
		Object[] season = cls.getEnumConstants();
		for (Object o : season) {
			Enum<?> e = (Enum<?>) o;
			System.out.println(e.getDeclaringClass());
		}

		System.out.println(Enum.valueOf(Season.class, "Winter"));
		System.out.println();*/
	}
}
