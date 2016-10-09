package beans;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
public class User {

	private String id;
	@Size(min = 1, max = 10)
	private String account;
	private String password;

	@NotEmpty
	@Valid
	private List<String> phone;

	public static final String CURRENT_USER_KEY = "current_user";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) {
		this.phone = phone;
	}
}
