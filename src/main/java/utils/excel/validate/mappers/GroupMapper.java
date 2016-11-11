package utils.excel.validate.mappers;

/**
 * Created by admin on 2016/9/12.
 */
public class GroupMapper<K, V> implements Mapper<K, V> {

	private final Mapper<K, V>[] mappers;

	public GroupMapper(Mapper<K, V>... mappers) {
		if (null != mappers) {
			for (Mapper mapper : mappers) {
				if (null == mapper)
					throw new NullPointerException("mappers must be not null.");
			}

			this.mappers = mappers;
		} else
			throw new NullPointerException("mappers must be not null.");
	}

	@Override
	public V process(K target) {
		return null;
	}
}
