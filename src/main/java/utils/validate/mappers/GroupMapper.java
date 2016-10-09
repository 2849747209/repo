package utils.validate.mappers;

import utils.validate.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/12.
 */
public class GroupMapper implements Mapper {

	private final Mapper[] mappers;

	public GroupMapper(Mapper... mappers) {
		if (null != mappers) {
			for (Mapper mapper : mappers) {
				if (null == mapper)
					throw new NullPointerException("mappers must be not null.");
			}

			this.mappers = mappers;
		} else
			throw new NullPointerException("mappers must be not null.");
	}

	public Object process(Object target) {
		Map<String, Object> mapVal = new HashMap<String, Object>();
		for (Mapper mapper : mappers)
			mapVal.put(mapper.getClass().getSimpleName(), mapper.process(target));

		return mapVal;
	}
}
