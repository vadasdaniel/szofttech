package common;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
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
