package utils.excel.type;

/**
 * Created by admin on 2016/10/31.
 */
public class Num implements Type {

	private int scale;

	public Num(int scale) {
		this.scale = scale;
	}

	public int getVal() {
		return scale;
	}
}
