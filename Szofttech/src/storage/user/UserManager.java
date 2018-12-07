package src.storage.user;

import src.back.FileManager;
import src.storage.Manager;

import java.util.List;

public class UserManager implements Manager {

    private static final String filePath = "";
    private FileManager fileManager;

    public UserManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public void add(Object content) {

    }

    @Override
    public List list() {
        return null;
    }
}
