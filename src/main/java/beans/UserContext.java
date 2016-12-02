package beans;

/**
 * Created by admin on 2016/12/1.
 */
public class UserContext implements AutoCloseable {

	static final ThreadLocal<User> current = new ThreadLocal<User>();

	public UserContext(User user) {
		current.set(user);
	}

	public static User getCurrentUser() {
		return current.get();
	}

	@Override
	public void close() throws Exception {
		current.remove();
	}
}
