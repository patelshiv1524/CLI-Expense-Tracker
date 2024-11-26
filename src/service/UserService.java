package service;

import model.User;
import utils.FileManager;

import java.util.List;

public class UserService {
    private final FileManager fileManager = new FileManager();
    private User loggedInUser = null;

    public boolean registerUser(String username, String password) {
        List<User> users = fileManager.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // User already exists
            }
        }
        users.add(new User(username, password));
        fileManager.saveUsers(users);
        return true;
    }

    public boolean loginUser(String username, String password) {
        List<User> users = fileManager.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
