package storage.handledSubscribtions;

import back.FileManager;
import common.Subscription;
import common.enums.SubscriptionStateType;
import storage.Manager;
import storage.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandledSubscriptionsManager implements Manager<Subscription> {

    private static final String fileName = "subscriptions.dat";
    private FileManager fileManager;
    private List<Subscription> handledSubscriptions;
    Logger logger = new Logger(this.getClass().getName());

    public HandledSubscriptionsManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            handledSubscriptions = new ArrayList<>();
            for (String data : datas) {
                String[] dataColumns = data.split(", ");
                SubscriptionStateType state = SubscriptionStateType.valueOf(dataColumns[3]);

                if (state != SubscriptionStateType.ACTIVE) {
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
            logger.error(logger.DATABASE_NOT_FOUND);
        }
    }

    @Override
    public void delete(Subscription content) {
        handledSubscriptions.removeIf(subscription -> subscription.getId().equals(content.getId()));
        try {
            fileManager.remove(fileName, content.toString());
            logger.info(logger.DELETED);
        } catch (IOException e) {
            logger.error(logger.FAILED_DELETE);
        }
    }

    @Override
    public Subscription get(String id) {
        try {
            return handledSubscriptions
                    .stream()
                    .filter(subscription -> subscription.getId().equals(id))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (Exception e) {
            logger.error(logger.NOT_FOUND);
            return null;
        }
    }

    @Override
    public void add(Subscription handledSubscription) {
        handledSubscriptions.add(handledSubscription);

        try {
            fileManager.add(fileName, handledSubscription.toString());
            logger.info(logger.ADDED);
        } catch (IOException e) {
            logger.error(logger.FAILED_ADD);
        }
    }

    @Override
    public List<Subscription> list() {
        return handledSubscriptions;
    }
}
