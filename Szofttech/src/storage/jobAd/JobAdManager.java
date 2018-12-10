package storage.jobAd;

import back.FileManager;
import common.JobAd;
import storage.Manager;
import storage.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobAdManager implements Manager<JobAd> {

    private static final String fileName = "ads.dat";
    private FileManager fileManager;
    private List<JobAd> jobAds;
    Logger logger = new Logger(this.getClass().getName());

    public JobAdManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            jobAds = new ArrayList<>();
            for (String data : datas) {
                String[] dataColumn = data.split(",");
                JobAd job = new JobAd(
                        dataColumn[0],
                        dataColumn[1],
                        dataColumn[2],
                        dataColumn[3]
                );
                jobAds.add(job);
            }
        } catch (IOException e) {
            logger.error(logger.DATABASE_NOT_FOUND);
        }
    }

    @Override
    public void delete(List<JobAd> content) {
        String[] deleteContent = new String[content.size()];
        content.forEach(remove -> jobAds.removeIf(existing -> existing.getId().equals(remove.getId())));

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
    public JobAd get(String id) {
        try {
            return jobAds
                    .stream()
                    .filter(jobAd -> jobAd.getId().equals(id))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (Exception e) {
            logger.error(logger.NOT_FOUND);
            return null;
        }
    }

    @Override
    public void add(JobAd content) {
        jobAds.add(content);
        try {
            fileManager.add(fileName, content.toString());
            logger.info(logger.ADDED);
        } catch (IOException e) {
            logger.error(logger.FAILED_ADD);
        }
    }

    @Override
    public List<JobAd> list() {
        return jobAds;
    }
}
