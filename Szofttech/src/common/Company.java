package common;

public class Company {

    private String id;
    private String name;
    private String userId;

    public Company(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + userId;
    }
}
