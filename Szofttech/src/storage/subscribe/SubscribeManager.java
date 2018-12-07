package storage.subscribe;

import src.back.FileManager;
import src.common.Subscription;
import src.storage.Manager;

import java.util.List;

public class SubscribeManager implements Manager {

    private static final String filePath = "";
    private FileManager fileManager;

    public SubscribeManager(FileManager fileManager) {
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
    public void add(Object content) {

    }

    @Override
    public List<Subscription> list() {
        return null;
    }
}
