package beans;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/10.
 */
@Data
public class LoanDataVo implements Serializable {
	private static final long serialVersionUID = -1L;

	private String loanId; //申请单编号

	private String protocolNumber; //协议编号

	private String name; //姓名

	private String idNo; //身份证号

	private String phoneNo; //手机号

	private BigDecimal amount; //贷款金额

	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date loanPaidAt; //放款时间

	private Integer termCnt; //贷款期数

	private BigDecimal monCnt; //每期还款金额

	private BigDecimal monPclIst; //每期还款本息和

	private BigDecimal serviceFee; //服务费

	private Date oneRepaymentTime; //一期实际还款时间

	//private Date oneDeadline; //一期应还款时间

	private BigDecimal onePrincipal; //一期还款本金

	private BigDecimal oneInterest; //一期还款利息

	private Date twoRepaymentTime; //二期实际还款时间

	//private Date twoDeadline; //二期应还款时间

	private BigDecimal twoPrincipal; //二期还款本金

	private BigDecimal twoInterest; //二期还款利息

	private Date threeRepaymentTime; //三期实际还款时间

	//private Date threeDeadline; //三期应还款时间

	private BigDecimal threePrincipal; //三期还款本金

	private BigDecimal threeInterest; //三期还款利息
}
