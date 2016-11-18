package utils.convertor.test;

/**
 * Created by admin on 2016/11/17.
 */
public enum Color {
	Red("color is red"),
	Green("color is green"),
	Blue("color is blue");

	private String describe;

	private Color(String describe) {
		this.describe = describe;
	}

	public String getDescribe() {
		return describe;
	}
}
