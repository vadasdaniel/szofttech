package common;

public class LoginSession {

    private String id;
    private User user;

    public LoginSession(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public boolean isUserLoggedIn() {
        return id != null;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
