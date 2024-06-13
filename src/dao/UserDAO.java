package dao;

import model.User;
import util.FileConnector;
import util.HashPassword;

import java.io.IOException;
import java.util.List;

public class UserDAO {
	private final String FILE_PATH = "/src/db/users.bin";
	private final FileConnector<User> fileConnector = new FileConnector<User>();

	public boolean isUserExist(String username, String email) throws ClassNotFoundException, IOException {
		List<User> users = fileConnector.readFromFile(FILE_PATH);
		return users.stream().anyMatch(user -> user.getUsername().equals(email) || user.getEmail().equals(email));
	}

	public boolean addUser(User user) throws ClassNotFoundException, IOException {
		if (isUserExist(user.getUsername(), user.getEmail())) {
			return false;
		}
		user.setPassword(HashPassword.hashPassword(user.getPassword()));
		fileConnector.appendObject(FILE_PATH, user);
		return true;

	}

	public List<User> getAllUsers() throws ClassNotFoundException, IOException {
		return fileConnector.readFromFile(FILE_PATH);
	}

	public User getUserByUsername(String username) throws IOException, ClassNotFoundException {
		List<User> users = fileConnector.readFromFile(FILE_PATH);
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User getUserByEmail(String email) throws IOException, ClassNotFoundException {
		List<User> users = fileConnector.readFromFile(FILE_PATH);
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	public void updateUser(User updatedUser) {
		try {
			updatedUser.setPassword(HashPassword.hashPassword(updatedUser.getPassword()));
			List<User> users = fileConnector.readFromFile(FILE_PATH);
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getEmail().equals(updatedUser.getEmail())) {
					users.set(i, updatedUser);
					break;
				}
			}
			fileConnector.writeToFile(FILE_PATH, users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String username) {
		try {
			List<User> users = fileConnector.readFromFile(FILE_PATH);
			users.removeIf(user -> user.getUsername().equals(username));
			fileConnector.writeToFile(FILE_PATH, users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
