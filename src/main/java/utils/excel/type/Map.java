package utils.excel.type;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by admin on 2016/10/31.
 */
public class Map implements Type {

	private java.util.Map<Object, String> map;

	private Map(java.util.Map<Object, String> map) {
		if(null != map)
			this.map = map;
		else
			this.map = Collections.emptyMap();
	}

	public String getVal(Object key) {
		return map.get(key);
	}
}
