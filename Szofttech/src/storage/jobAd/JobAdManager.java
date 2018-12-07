package src.storage.jobAd;

import src.back.FileManager;
import src.common.JobAd;
import src.storage.Manager;

import java.util.List;

public class JobAdManager implements Manager {

    private static final String filePath = "";
    private FileManager fileManager;

    public JobAdManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public JobAd get(String id) {
        return null;
    }

    @Override
    public void add(Object content) {

    }

    @Override
    public List<JobAd> list() {
        return null;
    }
}
