package utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import utils.convertor.ClassCast;
import org.springframework.jdbc.core.RowMapper;

public abstract class ClassRowMapper<T> implements RowMapper<T> {
	protected final Class<T> cls;
	protected final Map<String, Method> setMethods;

	protected final ThreadLocal<Map<String, Method>> methodMapper;
	
	@SuppressWarnings("unchecked")
	public ClassRowMapper() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type types[] = type.getActualTypeArguments();
		cls = (Class<T>) types[0];

		Map<String, Method> mapper = new HashMap<String, Method>();
		
		Method methods[] = cls.getMethods();
		for(Method method : methods) {
			if (method.getName().startsWith("set")) {
				char c[] = method.getName().toCharArray();
				c[3] = Character.toLowerCase(c[3]);
				String fieldName = new String(c, 3, c.length - 3);

				mapper.put(fieldName, method);
			}
		}

		this.setMethods = Collections.unmodifiableMap(mapper);

		this.methodMapper = new ThreadLocal<Map<String, Method>>();
	}
	
	@Override
	public T mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		if (resultSet == null)
			throw new SQLException("resultSet is null");

		Map<String, Method> methods = this.methodMapper.get();
		if (null == methods) {
			methods = new HashMap<String, Method>();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			for (int i = 1, cnt = rsmd.getColumnCount(); i <= cnt; i++) {
				String name = rsmd.getColumnLabel(i);
				Method method = setMethods.get(rsmd.getColumnLabel(i));
				if (null != method)
					methods.put(name, method);
			}

			this.methodMapper.set(methods);
		}

		resultSet.absolute(rowNum + 1);
		try {
			T object = cls.newInstance();
			Set<Map.Entry<String, Method>> entrySet = methods.entrySet();
			for (Map.Entry<String, Method> entry : entrySet) {
				Method method = entry.getValue();
				Object value = resultSet.getObject(entry.getKey());

				Class<?>[] cls = method.getParameterTypes();

				method.invoke(object, ClassCast.cast(value, cls[0]));
			}

			return object;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
