package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本息还款,也称定期付息,即借款人每月按相等的金额偿还贷款本息,其中每月贷款利息按月初剩余贷款本金计算并逐月结清.
 * 把按揭贷款的本金总额与利息总额相加,然后平均分摊到还款期限的每个月中.
 * 作为还款人,每个月还给银行固定金额,但每月还款额中的本金比重逐月递增,利息比重逐月递减.
 */
public class ACPIUtils {

	//金额 默认保留小数点后多少位(精度)
	private static int defaultCurrencyScale = 2;
	//金额超出精度时的舍入方式
	private static RoundingMode defaultCurrencyRoundingMode = RoundingMode.HALF_EVEN;
	//利率 默认保留小数点后多少位(精度)
	private static int defaultInterestRateScale = 6;
	//利率超出精度时的舍入方式
	private static RoundingMode defaultInterestRateRoundingMode = RoundingMode.HALF_EVEN;

	/**
	 * 等额本息的每月偿还 本金和利息
	 *
	 * 公式: 每月偿还本息 = [贷款本金x月利率x(1+月利率)^还款月数]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金和利息
	 */
	public static double getPerMonPclIst(double invest, double yearRate, int totalMonth) {
		BigDecimal monPclIst = getPerMonPclIst(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		return monPclIst.doubleValue();
	}

	/**
	 * 等额本息的每月偿还 本金和利息
	 *
	 * 公式: 每月偿还本息 = [贷款本金x月利率x(1+月利率)^还款月数]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金和利息
	 */
	public static BigDecimal getPerMonPclIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);

		//1+月利率
		BigDecimal rate = monthRate.add(BigDecimal.ONE);
		//(1+月利率)^还款月数
		BigDecimal ratePow = rate.pow(totalMonth);

		//被除数
		BigDecimal dividend = invest.multiply(monthRate).multiply(ratePow);
		//除数
		BigDecimal divisor = ratePow.subtract(BigDecimal.ONE);

		BigDecimal quotient = dividend.divide(divisor, defaultCurrencyScale, defaultInterestRateRoundingMode);
		return quotient;
	}

	/**
	 * 等额本息的每月偿还 利息
	 *
	 * 公式: 每月偿还利息 = 贷款本金×月利率×[(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
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
	 * 等额本息的每月偿还 利息
	 *
	 * 公式: 每月偿还利息 = 贷款本金×月利率×[(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, BigDecimal> getPerMonIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);

		//1+月利率
		BigDecimal rate = monthRate.add(BigDecimal.ONE);
		//(1+月利率)^还款月数
		BigDecimal ratePow = rate.pow(totalMonth);
		//贷款本金×月利率
		BigDecimal investMultiplyMonthRate = invest.multiply(monthRate);

		BigDecimal divisor = rate.pow(totalMonth).subtract(BigDecimal.ONE);
		Map<Integer, BigDecimal> monthInterest = new HashMap<Integer, BigDecimal>(totalMonth);
		for (int i=1; i<=totalMonth; i++) {
			BigDecimal dividend = investMultiplyMonthRate.multiply(ratePow.subtract(rate.pow(i - 1)));
			BigDecimal quotient = dividend.divide(divisor, defaultCurrencyScale, defaultInterestRateRoundingMode);
			monthInterest.put(i, quotient);
		}

		/*BigDecimal perMonPclIst = getPerMonPclIst(invest, yearRate, totalMonth);
		Map<Integer, BigDecimal> monthPrincipal = getPerMonPcl(invest, yearRate, totalMonth);
		Map<Integer, BigDecimal> monthInterest = new HashMap<Integer, BigDecimal>(totalMonth);
		for (int i=1; i<=totalMonth; i++) {
			BigDecimal principal = monthPrincipal.get(i);
			monthInterest.put(i, perMonPclIst.subtract(principal));
		}*/

		return monthInterest;
	}

	/**
	 * 等额本息的每月偿还 本金
	 *
	 * 公式: 每月偿还本息 = [贷款本金×月利率×(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金
	 */
	public static Map<Integer, Double> getPerMonPcl(double invest, double yearRate, int totalMonth) {
		Map<Integer, BigDecimal> map = getPerMonPcl(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		Map<Integer, Double> monthPrincipal = new HashMap<Integer, Double>(totalMonth);

		for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
			monthPrincipal.put(entry.getKey(), entry.getValue().doubleValue());
		}

		return monthPrincipal;
	}

	/**
	 * 等额本息的每月偿还 本金
	 *
	 * 公式: 每月偿还本息 = [贷款本金×月利率×(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest
	 * @param yearRate
	 * @param totalMonth
	 * @return
	 */
	public static Map<Integer, BigDecimal> getPerMonPcl(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);
		//1+月利率
		BigDecimal rate = monthRate.add(BigDecimal.ONE);
		//(1+月利率)^还款月数
		BigDecimal ratePow = rate.pow(totalMonth);
		//贷款本金×月利率
		BigDecimal investMultiplyMonthRate = invest.multiply(monthRate);

		BigDecimal divisor = rate.pow(totalMonth).subtract(BigDecimal.ONE);
		Map<Integer, BigDecimal> monthPrincipal = new HashMap<Integer, BigDecimal>(totalMonth);
		/*BigDecimal accumulate = BigDecimal.ZERO;
		for (int i=1, len=totalMonth-1; i<=len; i++) {
			BigDecimal dividend = investMultiplyMonthRate.multiply(rate.pow(i - 1));
			BigDecimal quotient = dividend.divide(divisor, defaultCurrencyScale, RoundingMode.HALF_EVEN);
			monthPrincipal.put(i, quotient);

			accumulate = accumulate.add(quotient);
		}
		monthPrincipal.put(totalMonth, invest.subtract(accumulate));*/

		for (int i=1; i<=totalMonth; i++) {
			BigDecimal dividend = investMultiplyMonthRate.multiply(rate.pow(i - 1));
			BigDecimal quotient = dividend.divide(divisor, defaultCurrencyScale, defaultCurrencyRoundingMode);
			monthPrincipal.put(i, quotient);
		}

		return monthPrincipal;
	}

	/**
	 * 等额本息的 总利息
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
	 * 等额本息的 总利息
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal count = BigDecimal.ZERO;

		Map<Integer, BigDecimal> monthInterest = getPerMonIst(invest, yearRate, totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : monthInterest.entrySet()) {
			count = count.add(entry.getValue());
		}

		return count;
	}

	/**
	 * 等额本息的 总本金
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 总利息
	 */
	public static double getPrincipalCount(double invest, double yearRate, int totalMonth) {
		BigDecimal count = getPrincipalCount(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth);

		return count.doubleValue();
	}

	/**
	 * 等额本息的 总本金
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getPrincipalCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal count = BigDecimal.ZERO;

		Map<Integer, BigDecimal> monthPrincipal = getPerMonPcl(invest, yearRate, totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : monthPrincipal.entrySet()) {
			count = count.add(entry.getValue());
		}

		return count;
	}

	/**
	 * 等额本息的 本息总和
	 * 公式: 贷款本金×贷款本金×月利率×(1+月利率)^还款月数/[(1+月利率)^还款月数-1]
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本息总和
	 */
	public static double getPclIstCount(double invest, double yearRate, int totalMonth) {
		return getPclIstCount(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth).doubleValue();
	}

	/**
	 * 等额本息的 本息总和
	 * 公式: 贷款本金×月利率×还款月数×(1+月利率)^还款月数/[(1+月利率)^还款月数-1]
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本息总和
	 */
	public static BigDecimal getPclIstCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode);

		//1+月利率
		BigDecimal rate = monthRate.add(BigDecimal.ONE);
		//(1+月利率)^还款月数
		BigDecimal ratePow = rate.pow(totalMonth);
		//贷款本金×月利率
		BigDecimal investMultiplyMonthRateMultiplyTotalMonth = invest.multiply(monthRate).multiply(new BigDecimal(totalMonth));

		//被除数
		BigDecimal dividend = investMultiplyMonthRateMultiplyTotalMonth.multiply(ratePow);
		//除数
		BigDecimal divisor = ratePow.subtract(BigDecimal.ONE);

		BigDecimal totalPclIst = dividend.divide(divisor, defaultCurrencyScale, defaultCurrencyRoundingMode);
		return totalPclIst;
	}

	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal("6000"); // 本金
		int month = 3;
		BigDecimal yearRate = new BigDecimal("0.1008"); // 年利率
		Map<Integer, BigDecimal> mapInterest = getPerMonIst(invest, yearRate, month);
		System.out.println("等额本息---每月还款利息：" + mapInterest);
		Map<Integer, BigDecimal> mapPrincipal = getPerMonPcl(invest, yearRate, month);
		System.out.println("等额本息---每月还款本金：" + mapPrincipal);
		BigDecimal perMonPclIst = getPerMonPclIst(invest, yearRate, month);
		System.out.println("等额本息---每月还款本息：" + perMonPclIst);
		BigDecimal interestCount = getInterestCount(invest, yearRate, month);
		System.out.println("等额本息---总利息：" + interestCount);
		BigDecimal principalCount = getPrincipalCount(invest, yearRate, month);
		System.out.println("等额本息---总本金：" + principalCount);
		BigDecimal principalInterestCount = getPclIstCount(invest, yearRate, month);
		System.out.println("等额本息---本息总和：" + principalInterestCount);
		System.out.println("月利率:" + yearRate.divide(new BigDecimal(12), defaultInterestRateScale, defaultInterestRateRoundingMode));
	}
}
