package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本金是指一种贷款的还款方式,是在还款期内把贷款数总额等分,
 * 每月偿还同等数额的本金和剩余贷款在该月所产生的利息,这样由于每月的还款本金额固定,
 * 而利息越来越少,借款人起初还款压力较大,但是随时间的推移每月还款数也越来越少.
 */
public class ACUtils {

	//金额 默认保留小数点后多少位(精度)
	private static int defaultCurrencyScale = 2;
	//金额超出精度时的舍入方式
	private static RoundingMode defaultCurrencyRoundingMode = RoundingMode.HALF_EVEN;
	//利率 默认保留小数点后多少位(精度)
	private static int defaultInterestRateScale = 6;
	//利率超出精度时的舍入方式
	private static RoundingMode defaultInterestRateRoundingMode = RoundingMode.HALF_EVEN;

	/**
	 * 每月偿还 本息
	 * 公式: (贷款本金/还款月数)+(贷款本金-贷款本金/还款月数x(还款月序号-1))x月利率
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本息
	 */
	public static Map<Integer, Double> getPerMonPclIst(double invest, double yearRate, int totalMonth) {
		Map<Integer, BigDecimal> map = getPerMonPclIst(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		Map<Integer, Double> monPclIst = new HashMap<>(totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
			monPclIst.put(entry.getKey(), entry.getValue().doubleValue());
		}

		return monPclIst;
	}

	/**
	 * 每月偿还 本息
	 * 公式: (贷款本金/还款月数)+(贷款本金-贷款本金/还款月数x(还款月序号-1))x月利率
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本息
	 */
	public static Map<Integer, BigDecimal> getPerMonPclIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//贷款本金/还款月数
		BigDecimal monthPrincipal = getPerMonPcl(invest, totalMonth);

		Map<Integer, BigDecimal> monthInterest = getPerMonIst(invest, yearRate, totalMonth);
		Map<Integer, BigDecimal> monPclIst = new HashMap<Integer, BigDecimal>(totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : monthInterest.entrySet()) {
			monPclIst.put(entry.getKey(), monthPrincipal.add(entry.getValue()));
		}

		return monPclIst;
	}

	/**
	 * 每月偿还 利息
	 * 公式: (贷款本金-贷款本金/还款月数x(还款月序号-1))x月利率
	 * 或者   贷款本金x(还款月数-还款月序号+1)x月利率/还款月数
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, Double> getPerMonIst(double invest, double yearRate, int totalMonth) {
		Map<Integer, BigDecimal> map = getPerMonIst(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		Map<Integer, Double> monthInterest = new HashMap<Integer, Double>(totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
			monthInterest.put(entry.getKey(), entry.getValue().doubleValue());
		}

		return monthInterest;
	}

	/**
	 * 每月偿还 利息
	 * 公式: (贷款本金-贷款本金/还款月数x(还款月序号-1))x月利率
	 * 或者   贷款本金x(还款月数-还款月序号+1)x月利率/还款月数
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, BigDecimal> getPerMonIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);

		BigDecimal month = new BigDecimal(totalMonth);
		Map<Integer, BigDecimal> monthInterest = new HashMap<Integer, BigDecimal>(totalMonth);
		for (int i=1; i<=totalMonth; i++) {
			BigDecimal multiplier = new BigDecimal(totalMonth - i + 1);
			BigDecimal quotient = invest.multiply(multiplier).multiply(monthRate);
			BigDecimal interest = quotient.divide(month, defaultCurrencyScale, defaultCurrencyRoundingMode);

			monthInterest.put(i, interest);
		}

		return monthInterest;
	}

	/**
	 * 每月偿还 本金
	 * 公式: 贷款本金/还款月数
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金
	 */
	public static double getPerMonPcl(double invest, int totalMonth) {
		BigDecimal monthPrincipal = getPerMonPcl(new BigDecimal(invest), totalMonth);

		return monthPrincipal.doubleValue();
	}

	/**
	 * 每月偿还 本金
	 * 公式: 贷款本金/还款月数
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金
	 */
	public static BigDecimal getPerMonPcl(BigDecimal invest, int totalMonth) {
		BigDecimal quotient = invest.divide(new BigDecimal(totalMonth), defaultCurrencyScale, defaultCurrencyRoundingMode);

		return quotient;
	}

	/**
	 * 等额本金的 总利息
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 总利息
	 */
	public static double getInterestCount(double invest, double yearRate, int totalMonth) {
		BigDecimal count = getInterestCount(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		return count.doubleValue();
	}

	/**
	 * 等额本金的 总利息
	 * 公式: 贷款本金x(还款月数+1)x月利率/2
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);

		BigDecimal monthAddOne = new BigDecimal(totalMonth + 1);
		//贷款本金x(还款月数+1)x月利率
		BigDecimal dividend = invest.multiply(monthAddOne).multiply(monthRate);

		BigDecimal count = dividend.divide(new BigDecimal(2), defaultCurrencyScale, defaultCurrencyRoundingMode);
		return count;
	}

	/**
	 * 等额本金的 总本金
	 *
	 * @param invest 总借款额(贷款本金)
	 * @return 总利息
	 */
	public static double getPrincipalCount(double invest) {
		BigDecimal count = getPrincipalCount(new BigDecimal(invest));

		return count.doubleValue();
	}

	/**
	 * 等额本金的 总本金
	 *
	 * @param invest 总借款额(贷款本金)
	 * @return 总利息
	 */
	public static BigDecimal getPrincipalCount(BigDecimal invest) {
		return invest.setScale(defaultCurrencyScale, defaultCurrencyRoundingMode);
	}

	/**
	 * 等额本金的 本息总和
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本息总和
	 */
	public static double getPclIstCount(double invest, double yearRate, int totalMonth) {
		BigDecimal count = getPclIstCount(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		return count.doubleValue();
	}

	/**
	 * 等额本金的 本息总和
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本息总和
	 */
	public static BigDecimal getPclIstCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal interestCount = getInterestCount(invest, yearRate, totalMonth);
		BigDecimal count = invest.add(interestCount);

		return count.setScale(defaultCurrencyScale, defaultCurrencyRoundingMode);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal("6000"); // 本金
		int month = 7;
		BigDecimal yearRate = new BigDecimal("0.1008"); // 年利率
		BigDecimal perMonPrincipal = getPerMonPcl(invest, month);
		System.out.println("等额本金---每月还款本金:" + perMonPrincipal);
		Map<Integer, BigDecimal> mapInterest = getPerMonIst(invest, yearRate, month);
		System.out.println("等额本金---每月还款利息:" + mapInterest);
		Map<Integer, BigDecimal> mapPclIst = getPerMonPclIst(invest, yearRate, month);
		System.out.println("等额本金---每月还款本息：" + mapPclIst);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("等额本金---总利息：" + count);
		BigDecimal principalCount = getPrincipalCount(invest);
		System.out.println("等额本息---总本金：" + principalCount);
		BigDecimal principalInterestCount = getPclIstCount(invest, yearRate, month);
		System.out.println("等额本息---本息总和：" + principalInterestCount);
		System.out.println("月利率:" + yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode));
	}
}
