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

	//默认保留小数点后多少位(精度)
	private static int defaultScale = 64;

	/**
	 * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
	 *
	 * 公式：每月偿还本息 = [贷款本金x月利率x(1+月利率)^还款月数]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金和利息
	 */
	public static double getPerMonPclIst(double invest, double yearRate, int totalMonth) {
		return getPerMonPclIst(new BigDecimal(invest), new BigDecimal(yearRate), totalMonth).doubleValue();
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
	 *
	 * 公式：每月偿还本息 = [贷款本金x月利率x(1+月利率)^还款月数]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还本金和利息
	 */
	public static BigDecimal getPerMonPclIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultScale, RoundingMode.DOWN);

		//1+月利率
		BigDecimal rate = monthRate.add(BigDecimal.ONE);
		//(1+月利率)^还款月数
		BigDecimal ratePow = rate.pow(totalMonth);

		//被除数
		BigDecimal dividend = invest.multiply(monthRate).multiply(ratePow);
		//除数
		BigDecimal divisor = ratePow.subtract(BigDecimal.ONE);

		BigDecimal principalAndInterest = dividend.divide(divisor, defaultScale, RoundingMode.HALF_EVEN);
		return principalAndInterest;
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的每月偿还利息
	 *
	 * 公式：每月偿还利息 = 贷款本金×月利率×[(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
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
	 * 等额本息计算获取还款方式为等额本息的每月偿还利息
	 *
	 * 公式：每月偿还利息 = 贷款本金×月利率×[(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)]/[(1+月利率)^还款月数-1]
	 *
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, BigDecimal> getPerMonIst(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultScale, RoundingMode.DOWN);

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
			monthInterest.put(i, dividend.divide(divisor, defaultScale, RoundingMode.HALF_EVEN));
		}

		return monthInterest;
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的每月偿还本金
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
	 * B=Mr(1+r)^(n-1)÷[(1+r)^N-1]
	 * BX=等额本息还贷每月所还本金和利息总额，
	 * B=等额本息还贷每月所还本金，
	 * M=贷款总金额
	 * r=贷款月利率（年利率除12），
	 * N=还贷总期数（即总月数）
	 * n=第n期还贷数
	 * ^=乘方计算（即X^12=X的12次方）
	 * @param invest
	 * @param yearRate
	 * @param totalMonth
	 * @return
	 */
	public static Map<Integer, BigDecimal> getPerMonPcl(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal principalAndInterest = getPerMonPclIst(invest, yearRate, totalMonth);
		Map<Integer, BigDecimal> monthInterest = getPerMonIst(invest, yearRate, totalMonth);

		Map<Integer, BigDecimal> monthPrincipal = new HashMap<Integer, BigDecimal>(totalMonth);
		for (Map.Entry<Integer, BigDecimal> entry : monthInterest.entrySet()) {
			monthPrincipal.put(entry.getKey(), principalAndInterest.subtract(entry.getValue()));
		}

		return monthPrincipal;
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的总利息
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
	 * 等额本息计算获取还款方式为等额本息的总利息
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
	 * 应还本金总和
	 * 公式:贷款本金×贷款本金×月利率×(1+月利率)^还款月数/[(1+月利率)^还款月数-1]
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本金总和
	 */
	public static double getPclIstCount(double invest, double yearRate, int totalMonth) {
		double monthRate = yearRate / 12;
		BigDecimal perMonthInterest = new BigDecimal(invest).multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalMonth))).divide(new BigDecimal(Math.pow(1 + monthRate, totalMonth) - 1), 2, BigDecimal.ROUND_DOWN);
		BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalMonth));
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count.doubleValue();
	}

	/**
	 * 应还本金总和
	 * 公式:贷款本金×月利率×还款月数×(1+月利率)^还款月数/[(1+月利率)^还款月数-1]
	 * @param invest 总借款额(贷款本金)
	 * @param yearRate 年利率
	 * @param totalMonth 还款总月数
	 * @return 应还本金总和
	 */
	public static BigDecimal getPclIstCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		//月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), defaultScale, RoundingMode.DOWN);

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

		BigDecimal totalPrincipalAndInterest = dividend.divide(divisor, defaultScale, RoundingMode.HALF_EVEN);
		return totalPrincipalAndInterest;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double invest = 20000; // 本金
		int month = 12;
		double yearRate = 0.15; // 年利率
		double perMonthPrincipalInterest = getPerMonPclIst(invest, yearRate, month);
		System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
		Map<Integer, Double> mapInterest = getPerMonIst(invest, yearRate, month);
		System.out.println("等额本息---每月还款利息：" + mapInterest);
		Map<Integer, Double> mapPrincipal = getPerMonPcl(invest, yearRate, month);
		System.out.println("等额本息---每月还款本金：" + mapPrincipal);
		double count = getInterestCount(invest, yearRate, month);
		System.out.println("等额本息---总利息：" + count);
		double principalInterestCount = getPclIstCount(invest, yearRate, month);
		System.out.println("等额本息---应还本息总和：" + principalInterestCount);

		System.out.println("月利率:" + yearRate/12);

		/**
		 等额本息---每月还款本息：1805.1662469031387
		 等额本息---每月还款利息：{1=249.99, 2=230.56, 3=210.87, 4=190.94, 5=170.77, 6=150.34, 7=129.65, 8=108.71, 9=87.50, 10=66.03, 11=44.29, 12=22.28}
		 等额本息---每月还款本金：{1=1555.17, 2=1574.60, 3=1594.29, 4=1614.22, 5=1634.39, 6=1654.82, 7=1675.51, 8=1696.45, 9=1717.66, 10=1739.13, 11=1760.87, 12=1782.88}
		 等额本息---总利息：1661.93
		 等额本息---应还本息总和：21661.92
		 月利率:0.012499999999999999
		 */
	}
}
