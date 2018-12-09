package storage.handledSubscribtions;

import back.FileManager;
import common.Subscription;
import common.enums.SubscriptionStateType;
import storage.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandledSubscriptionsManager implements Manager<Subscription> {

    private static final String fileName = "subscriptions.dat";
    private FileManager fileManager;
    private List<Subscription> handledSubscriptions;

    public HandledSubscriptionsManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            handledSubscriptions = new ArrayList<>();
            for ( String data: datas ) {
                String[] dataColumns = data.split(", ");
                SubscriptionStateType state = SubscriptionStateType.valueOf(dataColumns[3]);

                if ( state != SubscriptionStateType.ACTIVE ) {
                    Subscription handledSubscription = new Subscription(
                            dataColumns[0],
                            dataColumns[1],
                            dataColumns[2],
                            state
                    );
                    handledSubscriptions.add(handledSubscription);
                }

            }
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public void delete(Subscription content) {
        handledSubscriptions.removeIf(subscription -> subscription.getId().equals(content.getId()));

        try {
            fileManager.remove(fileName, content.toString());
        } catch (IOException e) {

        }
    }

    @Override
    public Subscription get(String id) {
        return handledSubscriptions
                .stream()
                .filter(subscription -> subscription.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void add(Subscription handledSubscription) {
        handledSubscriptions.add(handledSubscription);

        try {
            fileManager.add(fileName, handledSubscription.toString());
        } catch (IOException e) {

        }
    }

    @Override
    public List<Subscription> list() {
        return handledSubscriptions;
    }
}
