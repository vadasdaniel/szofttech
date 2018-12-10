package common;

import common.enums.SubscriptionStateType;

public class Subscription {

    private String id;
    private String jobAdId;
    private String userId;
    private SubscriptionStateType state;

    public Subscription(String id, String jobAdId, String userId, SubscriptionStateType state) {
        this.id = id;
        this.jobAdId = jobAdId;
        this.userId = userId;
        this.state = state;
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

    public SubscriptionStateType getState() {
        return state;
    }

    @Override
    public String toString() {
        return id + ", " + jobAdId + ", " + userId + "," + state.toString();
    }
}
