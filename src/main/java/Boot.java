import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by admin on 2016/8/12.
 */
@SpringBootApplication(scanBasePackages = {"controllers", "services", "utils"})
public class Boot {
	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}
}
