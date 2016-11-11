package utils.excel.type;

import utils.excel.Formula;

/**
 * Created by admin on 2016/10/31.
 */
public class Str implements Type {

	private String prefix;
	private String suffix;

	public Str(String prefix, String suffix) {
		if (null != prefix)
			this.prefix = prefix;
		else
			this.prefix = "";

		if (null != suffix)
			this.suffix = suffix;
		else
			this.suffix = "";
	}

	public String getVal(String target) {
		StringBuilder builder = new StringBuilder(prefix);
		builder.append(target);
		builder.append(suffix);

		return builder.toString();
	}
}
