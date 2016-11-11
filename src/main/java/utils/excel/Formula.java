package utils.excel;

public enum Formula {
	SUM("SUM", "总计:", ""),
	AVERAGE("AVERAGE", "平均:", ""),
	MAX("MAX", "最大:", "");
	
	private final static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	private String prefix;
	private String formula;
	private String suffix;
	
	private Formula(String formula, String prefix, String suffix) {
		this.prefix = prefix;
		this.formula = formula;
		this.suffix = suffix;
	}
	
	public static char getCharacter(int index) {
		return alphabet[index];
	}
	
	public String getVal(int fromColumn, int fromRow, int toColumn, int toRow) {
		StringBuilder builder = new StringBuilder("");
		if (null != prefix)
			builder.append('"').append(prefix).append('"').append('&');
		builder.append(formula).append('(').append(getCharacter(fromColumn)).append(fromRow).append(':').append(getCharacter(toColumn)).append(toRow).append(')');
		if (null != suffix)
			builder.append('&').append('"').append(suffix).append('"');
		return builder.toString();
	}
}
