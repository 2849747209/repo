package utils.excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/7.
 */
public class Bean {
	private String name;
	private Date date;
	private BigDecimal currency;
	private Number number;

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}
}
