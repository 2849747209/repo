package utils.convertor.test;

/**
 * Created by admin on 2016/11/17.
 */
public enum Season {
	Spring("Spring is coming"),
	Summer("Summer is coming"),
	Autumn("Autumn is coming"),
	Winter("Winter is coming");

	private String describe;

	private Season(String describe) {
		this.describe = describe;
	}
}
