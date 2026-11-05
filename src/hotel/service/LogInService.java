package hotel.service;

import hotel.model.User;

public class LogInService {

    private User admin;

    public LogInService() {
        admin = new User("admin", "admin123");
    }

    public boolean login(String username, String password) {

        if (username.equals(admin.getUsername()) &&
            password.equals(admin.getPassword())) {

            return true;
        }

        return false;
    }
}
