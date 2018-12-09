package storage.jobAd;

import back.FileManager;
import common.JobAd;
import storage.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobAdManager implements Manager<JobAd> {

    private static final String filePath = "/storage/resources/ads.dat";
    private FileManager fileManager;
    private List<JobAd> jobAds;

    public JobAdManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(filePath);
            jobAds = new ArrayList<>();
            for ( String data: datas ) {
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
            // TODO Logging
            /*e.printStackTrace();*/
        }
    }

    @Override
    public void delete(String id) {
        jobAds.removeIf(next -> next.getId().equals(id));
        fileManager.remove(filePath, id);
    }

    @Override
    public JobAd get(String id) {
        return jobAds
                .stream()
                .filter(jobAd -> jobAd.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void add(JobAd content) {
        jobAds.add(content);
        fileManager.add(filePath, content);
    }

    @Override
    public List<JobAd> list() {
        return jobAds;
    }
}
