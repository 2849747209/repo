import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/8/12.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"configurations", "controllers", "services", "utils"})
//@SpringBootApplication(scanBasePackages = {"configurations", "controllers", "services", "utils"})
//@EnableScheduling
public class Boot {
	public static void main(String[] args) throws Exception {
		//SpringApplication.run(Boot.class, args);
		long s = 1478854912000L;
		Timestamp paytime = new Timestamp(s);
		System.out.println(paytime);
	}
}
