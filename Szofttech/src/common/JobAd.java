package src.common;

public class JobAd {

    private String id;
    private String companyId;
    private String name;
    private String jobDescription;

    public JobAd(String id, String companyId, String name, String jobDescription) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.jobDescription = jobDescription;
    }

    public String getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    @Override
    public String toString() {
        return "JobAd{" +
                "id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", name='" + name + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                '}';
    }
}
