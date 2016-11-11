package utils.excel.type;

/**
 * Created by admin on 2016/10/31.
 */
public class Dat implements Type {

	private String format;

	public Dat(String format) {
		if (null != format)
			this.format = format;
		else
			this.format = "yyyy-MM-dd HH:mm:ss";
	}

	public String getVal() {
		return format;
	}
}
