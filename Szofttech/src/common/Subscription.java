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

    public void setId(String id) {
        this.id = id;
    }

    public String getJobAdId() {
        return jobAdId;
    }

    public void setJobAdId(String jobAdId) {
        this.jobAdId = jobAdId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
