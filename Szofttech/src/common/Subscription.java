package src.common;

public class Subscription {

    private String id;
    private String jobAdId;
    private String userId;

    public Subscription(String id, String jobAdId, String userId) {
        this.id = id;
        this.jobAdId = jobAdId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getJobAdId() {
        return jobAdId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", jobAdId='" + jobAdId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
