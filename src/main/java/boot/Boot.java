package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by admin on 2016/8/12.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"configurations", "controllers", "services", "utils"})
@SpringBootApplication(scanBasePackages = {"configurations", "controllers", "services", "utils"})
//@EnableScheduling
public class Boot {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Boot.class, args);
	}
}
