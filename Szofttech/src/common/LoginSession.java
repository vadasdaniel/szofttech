package src.common;

public class LoginSession<T> {

    private String id;
    private T user;

    public LoginSession(String id, T user) {
        this.id = id;
        this.user = user;
    }

    public boolean isUserLoggedIn() {
        return id != null;
    }

    public String getId() {
        return id;
    }

    public T getUser() {
        return user;
    }
}
