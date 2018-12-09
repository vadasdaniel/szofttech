package storage.handledSubscribtions;

import back.FileManager;
import common.Subscription;
import storage.Manager;

import java.util.List;

public class HandledSubscriptionsManager implements Manager<Subscription> {

    private static final String filePath = "";
    private FileManager fileManager;
    private List<Subscription> subscriptions;

    public HandledSubscriptionsManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Subscription get(String id) {
        return null;
    }

    @Override
    public void add(Subscription handledSubscription) {

    }

    @Override
    public List<Subscription> list() {
        return null;
    }
}
