package storage.subscribe;

import back.FileManager;
import common.Subscription;
import common.enums.SubscriptionStateType;
import storage.Manager;
import storage.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscribeManager implements Manager<Subscription> {

    private static final String fileName = "subscriptions.dat";
    private FileManager fileManager;
    private List<Subscription> subscriptions;
    Logger logger = new Logger(this.getClass().getName());

    public SubscribeManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            subscriptions = new ArrayList<>();
            for (String data : datas) {
                String[] dataColumn = data.split(",");

                SubscriptionStateType state = SubscriptionStateType.valueOf(dataColumn[3]);

                if (state == SubscriptionStateType.ACTIVE) {
                    Subscription subscription = new Subscription(
                            dataColumn[0].trim(),
                            dataColumn[1].trim(),
                            dataColumn[2].trim(),
                            state
                    );
                    subscriptions.add(subscription);
                }
            }
        } catch (IOException e) {
            logger.error(logger.DATABASE_NOT_FOUND);
        }
    }

    @Override
    public void delete(List<Subscription> content) {
        String[] deleteContent = new String[content.size()];
        content.forEach(remove -> subscriptions.removeIf(existing -> existing.getId().equals(remove.getId())));

        for ( int i = 0; i < deleteContent.length; i++ ) {
            deleteContent[i] = content.get(0).toString();
        }

        try {
            fileManager.remove(fileName, deleteContent);
            logger.info(logger.DELETED);
        } catch (IOException e) {
            logger.error(logger.FAILED_DELETE);
        }
    }

    @Override
    public Subscription get(String id) {
        try {
            return subscriptions
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
    public void add(Subscription content) {
        subscriptions.add(content);
        try {
            fileManager.add(fileName, content.toString());
            logger.info(logger.ADDED);
        } catch (IOException e) {
            logger.error(logger.FAILED_ADD);
        }
    }

    @Override
    public List<Subscription> list() {
        return subscriptions;
    }
}
