package common;

import common.enums.UserType;

public class User {

    private String id;
    private String name;
    private String username;
    private String password;
    private UserType userType;

    public User(String id, String name, String username, String password, UserType userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + username + ", " + password + ", " + userType;
    }
}
