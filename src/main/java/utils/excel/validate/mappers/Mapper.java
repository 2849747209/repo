package utils.excel.validate.mappers;

/**
 * Created by admin on 2016/9/12.
 */
public interface Mapper<K, V> {
	public V process(K target);
}
