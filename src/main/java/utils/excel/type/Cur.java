package utils.excel.type;

/**
 * Created by admin on 2016/10/31.
 */
public class Cur implements Type {

	private String format;

	public Cur(String format) {
		if (null != format)
			this.format = format;
		else
			this.format = "###,##0.00";
	}

	public String getVal() {
		return format;
	}
}
