package storage.subscribe;

import back.FileManager;
import common.Subscription;
import common.enums.SubscriptionStateType;
import storage.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscribeManager implements Manager<Subscription> {

    private static final String filePath = "/storage/resources/subscriptions.dat";
    private FileManager fileManager;
    private List<Subscription> subscriptions;

    public SubscribeManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(filePath);
            subscriptions = new ArrayList<>();
            for ( String data: datas ) {
                String[] dataColumn = data.split(",");

                SubscriptionStateType state = SubscriptionStateType.valueOf(dataColumn[3]);

                if ( state == SubscriptionStateType.ACTIVE ) {
                    Subscription subscription = new Subscription(
                            dataColumn[0],
                            dataColumn[1],
                            dataColumn[2],
                            state
                    );
                    subscriptions.add(subscription);
                }
            }
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public void delete(String id) {
        subscriptions.removeIf(subscription -> subscription.getId().equals(id));
        fileManager.remove(filePath, id);
    }

    @Override
    public Subscription get(String id) {
        return subscriptions
                .stream()
                .filter(subscription -> subscription.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void add(Subscription content) {
        subscriptions.add(content);
        fileManager.add(filePath, content);
    }

    @Override
    public List<Subscription> list() {
        return subscriptions;
    }
}
