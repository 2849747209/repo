package services;

import beans.LoanDataVo;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utils.ClassRowMapper;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by admin on 2016/8/19.
 */
@Service
public class Services {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(Services.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<LoanDataVo> loanDataVoRowMapper = new ClassRowMapper<LoanDataVo>(){};

	public List<LoanDataVo> queryLoan() {
		StringBuilder builder = new StringBuilder("");
		builder.append("select");
		builder.append(" loan_application_manifest_history.loan_id as loanId,");//1
		builder.append(" loan_application_manifest_history.protocol_number as protocolNumber,");//2
		builder.append(" loan_user.name as name,");//3
		builder.append(" loan_user.id_no as idNo,");//4
		builder.append(" loan_user.phone_no as phoneNo,");//5
		builder.append(" loan_application_manifest_history.amount as amount,");//6
		builder.append(" loan_application_manifest_history.loan_paid_at as loanPaidAt,");//7
		builder.append(" loan_application_manifest_history.term as termCnt,");//8
		builder.append(" loan_application_manifest_history.monthly_repayment as monCnt,");//9
		builder.append(" loan_application_manifest_history.service_fee_per_term as serviceFee");//10

		builder.append(" from");
		builder.append(" loan_user inner join loan_application_manifest_history ").append("on loan_user.id = loan_application_manifest_history.loan_user_id");

		String sql = builder.toString();
		List<LoanDataVo> list = jdbcTemplate.query(sql, loanDataVoRowMapper);

		return list;
	}

	public void serviceSayHello() {
		System.out.println("this is Services");
	}

	/**
	 * @Async不能用于有返回值的函数.例如调用这个函数就会报错.
	 */
	@Async
	public int romdomInt(CountDownLatch latch) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Thread currentThread = Thread.currentThread();

		int val = random.nextInt(1, 10);
		System.out.println(currentThread.getId() + ":" + val);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}

		return val;
	}

	//@Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
	public void scheduler() {
		logger.info("定时器执行任务 scheduled ... ");
	}
}
